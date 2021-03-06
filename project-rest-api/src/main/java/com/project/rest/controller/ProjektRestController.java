package com.project.rest.controller;
import java.net.URI;
import java.util.Optional;
import javax.validation.Valid;

import com.project.rest.model.Student;
import com.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.project.rest.model.Projekt;
import com.project.service.ProjektService;

@RestController
@RequestMapping("/api")
public class ProjektRestController {

    private ProjektService projektService;
    private StudentService studentService;

    @Autowired
    public ProjektRestController(ProjektService projektService, StudentService studentService) {
        this.projektService = projektService;
        this.studentService = studentService;
    }

    //Przykład żądania wywołującego metodę: GET http://localhost:8080/api/projekty/1

    @GetMapping("/projekty/{projektId}")
    ResponseEntity<Projekt> getProjekt(@PathVariable Integer projektId) {// @PathVariable oznacza, że wartość
        return ResponseEntity.of(projektService.getProjekt(projektId)); // parametru przekazywana jest w ścieżce
    }
    // @Valid włącza automatyczną walidację na podstawie adnotacji zawartych
    // w modelu np. NotNull, Size, NotEmpty itp. (z javax.validation.constraints.*)
    @PostMapping(path = "/projekty")
    ResponseEntity<Void> createProjekt(@Valid @RequestBody Projekt projekt) {// @RequestBody oznacza, że dane
        // projektu (w formacie JSON) są
        Projekt createdProjekt = projektService.setProjekt(projekt); // przekazywane w ciele żądania
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // link wskazujący utworzony projekt
                .path("/{projektId}").buildAndExpand(createdProjekt.getProjektId()).toUri();
        return ResponseEntity.created(location).build(); // zwracany jest kod odpowiedzi 201 - Created

    } // z linkiem location w nagłówku

    @PutMapping("/projekty/{projektId}")
    public ResponseEntity<Void> updateProjekt(@Valid @RequestBody Projekt projekt,
                                              @PathVariable Integer projektId) {
        return projektService.getProjekt(projektId)
                .map(p -> {
                    projektService.setProjekt(projekt);
                    return new ResponseEntity<Void>(HttpStatus.OK); // 200 (można też zwracać 204 - No content)
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 - Not found
    }

    @DeleteMapping("/projekty/{projektId}")
    public ResponseEntity<Void> deleteProjekt(@PathVariable Integer projektId) {
        return projektService.getProjekt(projektId).map(p -> {
            projektService.deleteProjekt(projektId);
            return new ResponseEntity<Void>(HttpStatus.OK); // 200
        }).orElseGet(() -> ResponseEntity.notFound().build()); // 404 - Not found
    }
    //Przykład żądania wywołującego metodę: http://localhost:8080/api/projekty?page=0&size=10&sort=nazwa,desc
    @GetMapping(value = "/projekty")
    Page<Projekt> getProjekty(Pageable pageable) { // @RequestHeader HttpHeaders headers – jeżeli potrzebny
        return projektService.getProjekty(pageable); // byłby nagłówek, wystarczy dodać drugą zmienną z adnotacją
    }

    // Przykład żądania wywołującego metodę: GET http://localhost:8080/api/projekty?nazwa=webowa
    // Metoda zostanie wywołana tylko, gdy w żądaniu będzie przesyłana wartość parametru nazwa.
    @GetMapping(value = "/projekty", params="nazwa")
    Page<Projekt> getProjektyByNazwa(@RequestParam String nazwa, Pageable pageable) {
        return projektService.searchByNazwa(nazwa, pageable);
    }
    @GetMapping("/studenci/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Integer studentId){
        return ResponseEntity.of(studentService.getStudent(studentId)); // parametru przekazywana jest w ścieżce
    }

    @PutMapping("/studenci/{studentId}")
    public ResponseEntity<Void> updateStudent(@Valid @RequestBody Student student,
                                              @PathVariable Integer studentId) {
        return studentService.getStudent(studentId)
                .map(p -> {
                    studentService.setStudent(student);
                    return new ResponseEntity<Void>(HttpStatus.OK); // 200 (można też zwracać 204 - No content)
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 - Not found
    }


    /* student controller */
    @GetMapping("/studenci")
    Page<Student> getStudenci(Pageable pageable) {// @PathVariable oznacza, że wartość
        return studentService.getStudenci(pageable);
    }



}
