import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
public class MainFrame {

    private JFrame frame;
    // ścieżka do katalogu z plikami tekstowymi
    private static final String DIR_PATH = FileSystems.getDefault().getPath("./files").toAbsolutePath().normalize().toString();
    // określa ile najczęściej występujących wyrazów bierzemy pod uwagę
    private final int liczbaWyrazowStatystyki;
    private final AtomicBoolean fajrant;
    private final int liczbaProducentow;
    private final int liczbaKonsumentow;
    // pula wątków – obiekt klasy ExecutorService, który zarządza tworzeniem
    // nowych oraz wykonuje 'recykling' zakończonych wątków
    private ExecutorService executor;
    // lista obiektów klasy Future, dzięki którym mamy możliwość nadzoru pracy wątków
    // producenckich np. sprawdzania czy wątek jest aktywny lub jego anulowania/przerywania
    private List<Future<?>> producentFuture;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame window = new MainFrame();
                    window.frame.pack();
                    window.frame.setAlwaysOnTop(true);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public MainFrame() {
        liczbaWyrazowStatystyki = 10;
        fajrant = new AtomicBoolean(false);
        liczbaProducentow = 1;
        liczbaKonsumentow = 2;
        executor = Executors.newFixedThreadPool(liczbaProducentow + liczbaKonsumentow);
        producentFuture = new ArrayList<>();
        initialize();
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                executor.shutdownNow();
            }
        });
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        JButton btnStop = new JButton("Stop");
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fajrant.set(true);
                for (Future<?> f : producentFuture) {
                    f.cancel(true);
                }
            }
        });
        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                multiThreadedStatistics();
            }
        });
        JButton btnZamknij = new JButton("Zamknij");
        btnZamknij.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executor.shutdownNow();
                System.exit(0);
            }
        });
        panel.add(btnStart);
        panel.add(btnStop);
        panel.add(btnZamknij);
    }
    /**
     * Statystyka wyrazów (wzorzec PRODUCENT - KONSUMENT korzystający z kolejki blokującej)
     */
    private void multiThreadedStatistics() {
        for (Future<?> f : producentFuture) {
            if (!f.isDone()) {
                JOptionPane.showMessageDialog(frame, "Nie można uruchomić nowego zadania! Przynajmniej jeden producent nadal działa!", "OSTRZEŻENIE", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        fajrant.set(false);
        producentFuture.clear();

        final BlockingQueue<Optional<Path>> kolejka = new LinkedBlockingQueue<>(liczbaKonsumentow);
        final int przerwa = 60;

        Runnable producent = () -> {
            final String name = Thread.currentThread().getName();
            String info = String.format("PRODUCENT %s URUCHOMIONY ...", name);
            System.out.println(info);

            while (!Thread.currentThread().isInterrupted()) {

                Path lookPath = FileSystems.getDefault().getPath(DIR_PATH); // ścieżka do katalogu szukania
                if (fajrant.get()) {

                    // toada przekazanie poison pills konsumentom i zakończenia działania
                    //give poison pill to all consumers and finish
                    for (int i = 0; i < liczbaKonsumentow; i++) {
                        kolejka.add(Optional.ofNullable(null));
                    }
                    break;
                } else {
                    try {
                        Files.walkFileTree(lookPath, new SimpleFileVisitor<>() {
                            @Override
                            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                                //distinct by file extension
                                if (file.toString().endsWith(".txt")) {
                                    Optional<Path> filePath = Optional.of(file.toRealPath());
                                    //put into queue
                                    try {

                                        kolejka.put(filePath);

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        //Thread.currentThread().interrupt();
                                    }
                                }


                                return FileVisitResult.CONTINUE;
                            }


                        });


                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }


                //Wyszukiwanie plików *.txt i wstawianie do kolejki ścieżki (w Optional) lub oczekiwanie jeśli kolejka
                // pełna, do wyszukiwania plików można użyć metody Files.walkFileTree oraz klasy SimpleFileVisitor<Path>

                info = String.format("Producent %s ponownie sprawdzi katalogi za %d sekund", name, przerwa);
                System.out.println(info);
                try {
                    TimeUnit.SECONDS.sleep(przerwa);
                } catch (InterruptedException e) {
                    info = String.format("Przerwa producenta %s przerwana!", name);
                    System.out.println(info);
                    if (!fajrant.get()) {
                        Thread.currentThread().interrupt();

                    }
                }
            }
            info = String.format("PRODUCENT %s SKOŃCZYŁ PRACĘ", name);
            System.out.println(info);
        };
        Runnable konsument = () -> {
            final String name = Thread.currentThread().getName();

            String info = String.format("KONSUMENT %s URUCHOMIONY ...", name);
            System.out.println(info);


            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // TODO pobieranie ścieżki i tworzenie statystyki wyrazów
                    // lub oczekiwanie jeśli kolejka jest pusta

                    Optional<Path> filePath = kolejka.take();
                    if (filePath.isPresent()) {

                        //  wyświetlanie statystyk
                        try {
                            System.out.println(getLinkedCountedWords(filePath.get(), 5));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //give the consumer posion pills
                        break;
                    }
                    //fajrant.set(true);


                } catch (InterruptedException e) {
                    info = String.format("Oczekiwanie konsumenta %s na nowy element z kolejki przerwane!", name);
                    System.out.println(info);
                    Thread.currentThread().interrupt();
                }
            }

            info = String.format("KONSUMENT %s ZAKOŃCZYŁ PRACĘ", name);
            System.out.println(info);

        };

        //uruchamianie wszystkich wątków-producentów
        for (int i = 0; i < liczbaProducentow; i++) {
            Future<?> pf = executor.submit(producent);
            producentFuture.add(pf);
        }
        //uruchamianie wszystkich wątków-konsumentów
        for (int i = 0; i < liczbaKonsumentow; i++) {
            executor.execute(konsument);
        }
    }

    /** Metoda zwraca najczęściej występujące słowa we wskazanym pliku tekstowym
     */
    private Map<String, Long> getLinkedCountedWords(Path path, int wordsLimit) throws IOException {
        //konstrukcja 'try-with-resources' - z automatycznym zamykaniem strumienia/źródła danych
        try (BufferedReader reader = Files.newBufferedReader(path)) {// wersja ze wskazaniem kodowania
             //Files.newBufferedReader(path, StandardCharsets.UTF_8);
            return reader.lines() // można też bez buforowania - Files.readAllLines(path)

                    //divide lines into words
                    .flatMap(line -> Arrays.stream(line.split(" ")))
                    //filter words by length  > 3
                    .filter(word -> word.matches("[a-zA-Z]{3,}"))
                    //convert words to lower case
                    .map(String::toLowerCase)
                    //group words by occurance times
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(wordsLimit)






                    // 1. podział linii na słowa, można skorzystać z wyrażeń regularnych np. "\\s+"

                    // 2. filtrowanie słów - tylko z przynajmniej trzema znakami, użyj wyrażenia
                    // regularnego np. "[a-zA-Z]{3,}" (nie uwzględnia polskich znaków)
                    // 3. konwersja do małych liter, aby porównywanie słów było niewrażliwe na wielkość liter
                    // 4. grupowanie słów względem liczebności ich występowania, można użyć
                    // metody collect z Collectors.groupingBy(Function.identity(), Collectors.counting()),
                    // po tej operacji należy zrobić konwersję na strumień tj. entrySet().stream()
                    // 5. sortowanie względem przechowywanych w mapie wartości, w kolejności malejącej,
                    // można użyć Map.Entry.comparingByValue(Comparator.reverseOrder())
                    // 6. ograniczenie liczby słów do wartości z wordsLimit

                    .collect(Collectors.toMap( //umieszczenie elementów strumienia w mapie zachowującej kolejność tj. LinkedHashMap
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (k,v) -> { throw new IllegalStateException(String.format("Błąd! Duplikat klucza %s.", k)); },
                            LinkedHashMap::new));
        } catch(IOException ioe){
            throw new IOException(ioe);
        }
    }
}