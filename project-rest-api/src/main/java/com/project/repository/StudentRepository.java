package com.project.repository;

import com.project.rest.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {


    Optional<Student> findByNrIndeksu(String nrIndeksu);

    //@Query("SELECT s FROM Student s WHERE s.nrIndeksu like :nrIndeksu")
    Page<Student> findByNrIndeksuStartsWith(String nrIndeksu, Pageable pageable);

    //@Query("SELECT s FROM Student s WHERE upper(s.nazwisko) like upper(:nazwisko)")
    Page<Student> findByNazwiskoStartsWithIgnoreCase(String nazwisko, Pageable pageable);
}
