package com.project.service;

import com.project.repository.StudentRepository;
import com.project.rest.model.Student;
import com.project.rest.model.Zadanie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService implements StudentServiceInterface{

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Student> getStudent(Integer studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public Student setStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Integer studentId) {

        studentRepository.deleteById(studentId);
    }

    @Override
    public Page<Student> getStudenci(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Page<Student> searchByImie(String imie, Pageable pageable) {
        return null;
    }
}
