package webapp.service;


import webapp.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StudentServiceInterface {
    Optional<Student> getStudent(Integer studentId);
    Student setStudent(Student student);
    void deleteStudent(Integer studentId);
    Page<Student> getStudenci(Pageable pageable);
    Page<Student> searchByNazwa(String nazwa, Pageable pageable);
}
