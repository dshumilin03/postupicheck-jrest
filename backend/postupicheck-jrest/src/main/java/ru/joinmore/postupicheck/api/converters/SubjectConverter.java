package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.SubjectDto;
import ru.joinmore.postupicheck.api.entities.Subject;

@Component
public class SubjectConverter implements Converter<Subject, SubjectDto> {

    @Override
    public SubjectDto convert(Subject subject) {

        long subjectId = subject.getId();
        String subjectName = subject.getName();

        return new SubjectDto(subjectId, subjectName);

    }
}
