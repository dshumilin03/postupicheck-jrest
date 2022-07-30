package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.SubjectDto;
import ru.joinmore.postupicheck.api.entities.Subject;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubjectConverter implements Converter<Subject, SubjectDto>, ListConverter<Subject, SubjectDto> {

    @Override
    public SubjectDto convert(Subject subject) {

        long subjectId = subject.getId();
        String subjectName = subject.getName();

        return new SubjectDto(subjectId, subjectName);

    }

    @Override
    public List<SubjectDto> convert(List<Subject> subjects) {
        List<SubjectDto> subjectDtoList = new ArrayList<>();
        subjects.
                forEach(subject -> {
                    SubjectDto subjectDto = convert(subject);
                    subjectDtoList.add(subjectDto);
                });
        return subjectDtoList;
    }
}
