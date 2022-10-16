package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.services.SubjectService;

import java.util.List;

@Component
public class CourseRequiredSubjectsReverseConverter implements Converter<List<Long>, List<Subject>> {

    private final SubjectService subjectService;

    public CourseRequiredSubjectsReverseConverter(
            SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public List<Subject> convert(List<Long> subjectsIds) {

        return subjectsIds.stream().map(subjectService::get).toList();
    }
}
