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
import webapp.service.StudentService;

import javax.validation.Valid;

@Controller("/student")
public class StudentController {
    private StudentService studentService;
    public static final Logger logger = LoggerFactory.getLogger(ProjektController.class);
    //@Autowired – przy jednym konstruktorze wstrzykiwanie jest zadaniem domyślnym, adnotacja nie jest potrzebny

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    // metodę wywołamy wpisując w przeglądarce np. http://localhost:8081/projektList lub

    @GetMapping("/studentList") // http://localhost:8081/projektList?page=0&size=10&sort=dataCzasModyfikacji,desc
    public String studentList(Model model, Pageable pageable) {
        // za pomocą zmiennej model i metody addAttribute przekazywane są do widoku obiekty
        // zawierające dane projektów
        model.addAttribute("studenci", studentService.getStudenci(pageable).getContent());
        return "studentList"; // metoda zwraca nazwę logiczną widoku, Spring dopasuje nazwę do odpowiedniego
    } // szablonu tj. project-web-app\src\main\resources\templates\projektList.html

    @GetMapping("/studentEdit")
    public String studentEdit(@RequestParam(required = false) Integer studentId, Model model) {
        if(studentId != null) {
            model.addAttribute("student", studentService.getStudent(studentId).get());
        }else {
            Student student = new Student();
            model.addAttribute("student", student);
        }
        return "studentEdit"; // metoda zwraca nazwę logiczną widoku, Spring dopasuje nazwę do odpowiedniego
    } // szablonu tj. project-web-app\src\main\resources\templates\projektEdit.html
    @PostMapping(path = "/studentEdit")
    public String studentEditSave(@ModelAttribute @Valid Student student, BindingResult bindingResult) {
        // parametr BindingResult powinien wystąpić zaraz za parametrem opatrzonym adnotacją @Valid
        if (bindingResult.hasErrors()) {
            return "studentEdit"; // wracamy do okna edycji, jeżeli przesłane dane formularza zawierają błędy
        }
        try {
            student = studentService.setStudent(student);
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(null, String.valueOf(e.getStatusCode().value()),
                    e.getStatusCode().getReasonPhrase());
            return "studentEdit";
        }
        return "redirect:/studentList"; // przekierowanie do listy projektów, po utworzeniu lub modyfikacji projektu
    }

    @PostMapping(params="cancel", path = "/studentEdit") // metoda zostanie wywołana, jeżeli przesłane
    public String studentEditCancel() { // żądanie będzie zawierało parametr 'cancel'
        return "redirect:/studentList";
    }

    @PostMapping(params="delete", path = "/studentEdit") // metoda zostanie wywołana, jeżeli przesłane
    public String projektEditDelete(@ModelAttribute Student student, @RequestBody String studentId) {// żądanie będzie zawierało parametr 'delete'
        logger.info("Deleting a student: ", studentId);
        studentService.deleteStudent(student.getStudent_id());
        return "redirect:/studentList";
    }
}
