package ru.joinmore.postupicheck.api.controller;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.model.Student;
import ru.joinmore.postupicheck.api.service.StudentService;

import java.util.List;


@RestController
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/students")
    List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    @PostMapping("/students")
    Student createStudent(@RequestBody Student newStudent) {
       return service.createStudent(newStudent);
    }

    @GetMapping("/students/{id}")
    Student getStudent(@PathVariable Long id) {
        return service.getStudent(id);
    }

    @PutMapping("/students/{id}")
    Student replaceStudent(@RequestBody Student updatedStudent, Long id) {
        return service.replaceStudent(updatedStudent, id);
    }

    @DeleteMapping("/students/{id}")
    void deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
    }
}
