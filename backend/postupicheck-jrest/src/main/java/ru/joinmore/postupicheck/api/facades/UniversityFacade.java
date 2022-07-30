package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.converters.UniversityConverter;
import ru.joinmore.postupicheck.api.converters.UniversityReverseConverter;
import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.services.UniversityService;

import java.util.List;

@Component
public class UniversityFacade {

    private final UniversityService universityService;
    private final UniversityConverter converter;
    private final UniversityReverseConverter reverseConverter;

    public UniversityFacade(UniversityService universityService,
                            UniversityConverter converter,
                            UniversityReverseConverter reverseConverter) {
        this.universityService = universityService;
        this.converter = converter;
        this.reverseConverter = reverseConverter;
    }

    public UniversityDto get(long id) {

        University university = universityService.get(id);

        return converter.convert(university);
    }

    public List<UniversityDto> getAll() {

        List<University> allUniversities = universityService.getAll();

        return converter.convert(allUniversities);
    }

    public UniversityDto create(UniversityDto newUniversityDto) {

        University newUniversity = reverseConverter.convert(newUniversityDto);
        University createdUniversity = universityService.create(newUniversity);

        return converter.convert(createdUniversity);
    }

    public UniversityDto replace(UniversityDto updatedUniversityDto, long id) {

        University updatedUniversity = reverseConverter.convert(updatedUniversityDto);
        University newUniversity = universityService.replace(updatedUniversity, id);

        return converter.convert(newUniversity);
    }

    public void delete(long id) {
        universityService.delete(id);
    }

}
