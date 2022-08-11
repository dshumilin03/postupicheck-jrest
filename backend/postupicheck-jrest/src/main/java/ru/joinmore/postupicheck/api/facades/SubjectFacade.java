package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.converters.SubjectConverter;
import ru.joinmore.postupicheck.api.converters.SubjectReverseConverter;
import ru.joinmore.postupicheck.api.dto.SubjectDto;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.services.SubjectService;

import java.util.List;

@Component
public class SubjectFacade {

    private final SubjectService subjectService;
    private final SubjectConverter converter;
    private final SubjectReverseConverter reverseConverter;

    public SubjectFacade(
            SubjectService subjectService,
            SubjectConverter converter,
            SubjectReverseConverter reverseConverter) {
        this.subjectService = subjectService;
        this.converter = converter;
        this.reverseConverter = reverseConverter;
    }

    public SubjectDto get(long id) {
        Subject subject = subjectService.get(id);

        return converter.convert(subject);
    }

    public List<SubjectDto> getAll() {
        List<Subject> allSubjects = subjectService.getAll();

        return converter.convert(allSubjects);
    }

    public SubjectDto create(SubjectDto newSubjectDto) {
        Subject newSubject = reverseConverter.convert(newSubjectDto);
        Subject createdSubject = subjectService.create(newSubject);

        return converter.convert(createdSubject);
    }

    public SubjectDto replace(SubjectDto updatedSubjectDto, long id) {
        Subject updatedSubject = reverseConverter.convert(updatedSubjectDto);
        Subject newSubject = subjectService.replace(updatedSubject, id);

        return converter.convert(newSubject);
    }

    public void delete(long id) {
        subjectService.delete(id);
    }
}
