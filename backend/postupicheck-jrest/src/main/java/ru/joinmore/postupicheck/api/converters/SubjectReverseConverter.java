package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.SubjectDto;
import ru.joinmore.postupicheck.api.entities.Subject;

@Component
public class SubjectReverseConverter implements Converter<SubjectDto, Subject> {

    @Override
    public Subject convert(SubjectDto subjectDto) {

        String subjectName = subjectDto.getName();
        return new Subject(subjectName);
    }
}
