package ru.joinmore.postupicheck.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.facades.StudentFacade;

import java.util.List;


@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentFacade studentFacade;

    public StudentController(StudentFacade studentFacade) {
        this.studentFacade = studentFacade;
    }

    @GetMapping
    List<StudentDto> getAllStudents() {
        return studentFacade.getAll();
    }

    @PostMapping
    StudentDto createStudent(@RequestBody StudentDto newStudent) {
       return studentFacade.create(newStudent);
    }

    @GetMapping("/{id}")
    StudentDto getStudent(@PathVariable Long id) {
        return studentFacade.get(id);
    }

    @PutMapping("/{id}")
    StudentDto replaceStudent(@RequestBody StudentDto updatedStudent, Long id) {
        return studentFacade.replace(updatedStudent, id);
    }

    @DeleteMapping("/{id}")
    void deleteStudent(@PathVariable Long id) {
        studentFacade.delete(id);
    }
}
