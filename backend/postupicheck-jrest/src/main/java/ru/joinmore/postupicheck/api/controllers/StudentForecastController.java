package ru.joinmore.postupicheck.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.dto.StudentForecastDto;
import ru.joinmore.postupicheck.api.facades.StudentForecastFacade;

import java.util.List;

@RestController
@RequestMapping("/forecasts")
public class StudentForecastController {

    private final StudentForecastFacade studentForecastFacade;

    public StudentForecastController(StudentForecastFacade studentForecastFacade) {
        this.studentForecastFacade = studentForecastFacade;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    List<StudentForecastDto> getAll() {
        return studentForecastFacade.getAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    StudentForecastDto createStudentForecast(@RequestBody StudentForecastDto newStudentForecastDto) {
        return studentForecastFacade.create(newStudentForecastDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    StudentForecastDto getStudentForecast(@PathVariable Long id) {
        return studentForecastFacade.get(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    StudentForecastDto replaceStudentForecast(
            @RequestBody StudentForecastDto updatedStudentForecastDto,
            @PathVariable Long id) {
        return studentForecastFacade.replace(updatedStudentForecastDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void deleteStudentForecast(@PathVariable Long id) {
        studentForecastFacade.delete(id);
    }
}
