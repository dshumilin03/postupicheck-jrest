package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.SubjectDto;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.services.SubjectService;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubjectFacade {

    private final SubjectService subjectService;

    public SubjectFacade(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public SubjectDto get(long id) {

        Subject subject = subjectService.get(id);

        return setSubjectDto(subject);
    }

    public List<SubjectDto> getAll() {

        List<Subject> subjectList = subjectService.getAll();
        List<SubjectDto> subjectDtoList = new ArrayList<>();

        for (Subject subject : subjectList) {

            SubjectDto subjectDto = setSubjectDto(subject);
            subjectDtoList.add(subjectDto);
        }

        return subjectDtoList;
    }

    public SubjectDto create(SubjectDto newSubjectDto) {

        Subject newSubject = setSubject(newSubjectDto);
        Subject createdSubject = subjectService.create(newSubject);

        newSubjectDto.setId(createdSubject.getId());

        return newSubjectDto;
    }

    public SubjectDto replace(SubjectDto updatedSubjectDto, long id) {

        Subject updatedSubject = setSubject(updatedSubjectDto);
        Subject newSubject = subjectService.replace(updatedSubject, id);
        updatedSubjectDto.setId(newSubject.getId());

        return updatedSubjectDto;
    }

    public void delete(long id) {
        subjectService.delete(id);
    }

    private SubjectDto setSubjectDto(Subject subject) {

        long subjectId = subject.getId();
        String subjectName = subject.getName();

        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(subjectId);
        subjectDto.setName(subjectName);

        return subjectDto;
    }

    private Subject setSubject(SubjectDto SubjectDto) {

        String subjectName = SubjectDto.getName();
        return new Subject(subjectName);
    }
}
