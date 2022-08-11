package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.entities.University;

@Component
public class UniversityReverseConverter implements Converter<UniversityDto, University> {

    @Override
    public University convert(UniversityDto universityDto) {
        String universityName = universityDto.getName();
        long universityId = universityDto.getId();

        return new University(universityId, universityName);
    }
}
