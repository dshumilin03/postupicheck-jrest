package ru.joinmore.postupicheck.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
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
    @ResponseStatus(code = HttpStatus.OK)
    List<StudentDto> getAllStudents() {
        return studentFacade.getAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    StudentDto createStudent(@RequestBody StudentDto newStudent) {
       return studentFacade.create(newStudent);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    StudentDto getStudent(@PathVariable Long id) {
        return studentFacade.get(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    StudentDto replaceStudent(@RequestBody StudentDto updatedStudent,
                              @PathVariable Long id) {
        return studentFacade.replace(updatedStudent, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void deleteStudent(@PathVariable Long id) {
        studentFacade.delete(id);
    }

    @GetMapping("/{id}/admissions")
    @ResponseStatus(code = HttpStatus.OK)
    List<AdmissionDto> getStudentAdmissions(@PathVariable Long id) {
        return studentFacade.getStudentAdmissions(id);
    }

    @GetMapping("/{id}/available")
    @ResponseStatus(code = HttpStatus.OK)
    List<AdmissionDto> getStudentAvailableAdmissions(@PathVariable Long id) {
        return studentFacade.getStudentAvailableAdmissions(id);
    }

    @GetMapping("/{id}/forecast")
    @ResponseStatus(code = HttpStatus.OK)
    AdmissionDto getStudentForecastAdmission(@PathVariable Long id) {
        return studentFacade.getStudentForecastAdmission(id);
    }

    @GetMapping("/{id}/consent")
    @ResponseStatus(code = HttpStatus.OK)
    AdmissionDto getStudentConsentAdmission(@PathVariable Long id) {
        return studentFacade.getStudentConsentAdmission(id);
    }

}
