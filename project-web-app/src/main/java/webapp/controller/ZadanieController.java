package webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import webapp.model.Student;
import webapp.model.Zadanie;
import webapp.service.ZadanieService;

import javax.validation.Valid;

@Controller("/zadanie")
public class ZadanieController {
    private ZadanieService zadanieService;
    public static final Logger logger = LoggerFactory.getLogger(ProjektController.class);
    //@Autowired – przy jednym konstruktorze wstrzykiwanie jest zadaniem domyślnym, adnotacja nie jest potrzebny

    public ZadanieController(ZadanieService zadanieService) {
        this.zadanieService = zadanieService;
    }
    // metodę wywołamy wpisując w przeglądarce np. http://localhost:8081/projektList lub
    @GetMapping("/zadanieList") // http://localhost:8081/projektList?page=0&size=10&sort=dataCzasModyfikacji,desc
    public String zadanieList(Model model, Pageable pageable) {
        // za pomocą zmiennej model i metody addAttribute przekazywane są do widoku obiekty
        // zawierające dane projektów
        model.addAttribute("zadania", zadanieService.getZadania(pageable).getContent());
        return "zadanieList"; // metoda zwraca nazwę logiczną widoku, Spring dopasuje nazwę do odpowiedniego
    } // szablonu tj. project-web-app\src\main\resources\templates\projektList.html

    @GetMapping("/zadanieEdit")
    public String zadanieEdit(@RequestParam(required = false) Integer zadanieId, Model model) {
        if(zadanieId != null) {
            model.addAttribute("zadanie", zadanieService.getZadanie(zadanieId).get());
        }else {
            Zadanie zadanie = new Zadanie();
            model.addAttribute("zadanie", zadanie);
        }
        return "zadanieEdit"; // metoda zwraca nazwę logiczną widoku, Spring dopasuje nazwę do odpowiedniego
    } // szablonu tj. project-web-app\src\main\resources\templates\projektEdit.html
    @PostMapping(path = "/zadanieEdit")
    public String studentEditSave(@ModelAttribute @Valid Zadanie zadanie, BindingResult bindingResult) {
        // parametr BindingResult powinien wystąpić zaraz za parametrem opatrzonym adnotacją @Valid
        if (bindingResult.hasErrors()) {
            return "zadanieEdit"; // wracamy do okna edycji, jeżeli przesłane dane formularza zawierają błędy
        }
        try {
            zadanie = zadanieService.setZadanie(zadanie);
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(null, String.valueOf(e.getStatusCode().value()),
                    e.getStatusCode().getReasonPhrase());
            return "zadanieEdit";
        }
        return "redirect:/zadanieList"; // przekierowanie do listy projektów, po utworzeniu lub modyfikacji projektu
    }

    @PostMapping(params="cancel", path = "/zadanieEdit") // metoda zostanie wywołana, jeżeli przesłane
    public String studentEditCancel() { // żądanie będzie zawierało parametr 'cancel'
        return "redirect:/zadanieList";
    }

    @PostMapping(params="delete", path = "/zadanieEdit") // metoda zostanie wywołana, jeżeli przesłane
    public String projektEditDelete(@ModelAttribute Zadanie zadanie, @RequestBody String zadanieId) {// żądanie będzie zawierało parametr 'delete'
        logger.info("Deleting a student: ", zadanieId);
        zadanieService.deleteZadanie(zadanie.getZadanieId());
        return "redirect:/zadanieList";
    }
}
