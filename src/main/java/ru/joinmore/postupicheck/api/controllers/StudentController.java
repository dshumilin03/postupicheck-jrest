package ru.joinmore.postupicheck.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.models.Student;
import ru.joinmore.postupicheck.api.services.StudentService;

import java.util.List;


@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService service) {
        this.studentService = service;
    }

    @GetMapping
    List<Student> getAllStudents() {
        return studentService.getAll();
    }

    @PostMapping
    Student createStudent(@RequestBody Student newStudent) {
       return studentService.create(newStudent);
    }

    @GetMapping("/{id}")
    Student getStudent(@PathVariable Long id) {
        return studentService.get(id);
    }

    @PutMapping("/{id}")
    Student replaceStudent(@RequestBody Student updatedStudent, Long id) {
        return studentService.replace(updatedStudent, id);
    }

    @DeleteMapping("/{id}")
    void deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
    }
}
