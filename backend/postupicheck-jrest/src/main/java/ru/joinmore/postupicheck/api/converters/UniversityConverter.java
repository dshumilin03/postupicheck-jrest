package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.entities.University;

import java.util.ArrayList;
import java.util.List;

@Component
public class UniversityConverter implements Converter<University, UniversityDto>, ListConverter<University, UniversityDto> {
    @Override
    public UniversityDto convert(University university) {

        long universityId = university.getId();
        String universityName = university.getName();

        return new UniversityDto(universityId, universityName);
    }

    @Override
    public List<UniversityDto> convert(List<University> universities) {
        List<UniversityDto> universityDtoList = new ArrayList<>();
        universities.
                forEach(university -> {
                    UniversityDto universityDto = convert(university);
                    universityDtoList.add(universityDto);
                });
        return universityDtoList;
    }
}
