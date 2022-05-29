package webapp.controller;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import webapp.model.Projekt;
import webapp.service.ProjektService;

@Controller("/projekt")
public class ProjektController {
    private ProjektService projektService;
    public static final Logger logger = LoggerFactory.getLogger(ProjektController.class);
     // przy jednym konstruktorze wstrzykiwanie jest zadaniem domyślnym, adnotacja nie jest potrzebny
    public ProjektController(ProjektService projektService) {
        this.projektService = projektService;
    }
    // metodę wywołamy wpisując w przeglądarce np. http://localhost:8081/projektList lub
    @GetMapping("/projektList") // http://localhost:8081/projektList?page=0&size=10&sort=dataCzasModyfikacji,desc
    public String projektList(Model model, Pageable pageable) {
        // za pomocą zmiennej model i metody addAttribute przekazywane są do widoku obiekty
        // zawierające dane projektów
        model.addAttribute("projekty", projektService.getProjekty(pageable).getContent());
        return "projektList"; // metoda zwraca nazwę logiczną widoku, Spring dopasuje nazwę do odpowiedniego
    } // szablonu tj. project-web-app\src\main\resources\templates\projektList.html

    @GetMapping("/projektEdit")
    public String projektEdit(@RequestParam(required = false) Integer projektId, Model model) {
        if(projektId != null) {
            model.addAttribute("projekt", projektService.getProjekt(projektId).get());
        }else {
            Projekt projekt = new Projekt();
            model.addAttribute("projekt", projekt);
        }
        return "projektEdit"; // metoda zwraca nazwę logiczną widoku, Spring dopasuje nazwę do odpowiedniego
    } // szablonu tj. project-web-app\src\main\resources\templates\projektEdit.html
    @PostMapping(path = "/projektEdit")
    public String projektEditSave(@ModelAttribute @Valid Projekt projekt, BindingResult bindingResult) {
        // parametr BindingResult powinien wystąpić zaraz za parametrem opatrzonym adnotacją @Valid
        if (bindingResult.hasErrors()) {
            return "projektEdit"; // wracamy do okna edycji, jeżeli przesłane dane formularza zawierają błędy
        }
        try {
            projekt = projektService.setProjekt(projekt);
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(null, String.valueOf(e.getStatusCode().value()),
                    e.getStatusCode().getReasonPhrase());
            return "projektEdit";
        }
        return "redirect:/projektList"; // przekierowanie do listy projektów, po utworzeniu lub modyfikacji projektu
    }

    @PostMapping(params="cancel", path = "/projektEdit") // metoda zostanie wywołana, jeżeli przesłane
    public String projektEditCancel() { // żądanie będzie zawierało parametr 'cancel'
        return "redirect:/projektList";
    }

    @PostMapping(params="delete", path = "/projektEdit") // metoda zostanie wywołana, jeżeli przesłane
    public String projektEditDelete(@ModelAttribute Projekt projekt, @RequestBody String projektId) {// żądanie będzie zawierało parametr 'delete'
        logger.info("Deleting a project: ", projektId);
        projektService.deleteProjekt(projekt.getProjektId());
        return "redirect:/projektList";
    }

}
