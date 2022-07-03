package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.entities.University;

@Component
public class UniversityConverter implements Converter<University, UniversityDto> {
    @Override
    public UniversityDto convert(University university) {

        long universityId = university.getId();
        String universityName = university.getName();

        UniversityDto universityDto = new UniversityDto();
        universityDto.setId(universityId);
        universityDto.setName(universityName);

        return universityDto;
    }
}
