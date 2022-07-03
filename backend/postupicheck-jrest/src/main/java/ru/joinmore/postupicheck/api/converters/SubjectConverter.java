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

        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(subjectId);
        subjectDto.setName(subjectName);

        return subjectDto;

    }
}
