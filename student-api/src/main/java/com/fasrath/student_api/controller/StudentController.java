package com.fasrath.student_api.controller;

import com.fasrath.student_api.entity.Student;
import com.fasrath.student_api.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    // CREATE (POST API)
    @PostMapping
    public ResponseEntity<Student> createStudent(
            @Valid @RequestBody Student student
    ) {
        Student savedStudent = studentRepository.save(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedStudent);
    }

    // READ ALL (GET)
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        return ResponseEntity.ok(students);
    }

    // READ ONE (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(
            @PathVariable Long id
    ) {
        Optional<Student> student = studentRepository.findById(id);

        return student
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }






    // UPDATE (PUT request)
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody Student updatedStudent
    ) {
        Optional<Student> existingStudentOptional =
                studentRepository.findById(id);

        if (existingStudentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student existingStudent = existingStudentOptional.get();

        existingStudent.setName(updatedStudent.getName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setDepartment(updatedStudent.getDepartment());
        existingStudent.setAge(updatedStudent.getAge());

        Student savedStudent =
                studentRepository.save(existingStudent);

        return ResponseEntity.ok(savedStudent);
    }


    // DELETE (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id
    ) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        studentRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}















