package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.services.UniversityService;

import java.util.ArrayList;
import java.util.List;

@Component
public class UniversityFacade {

    private final UniversityService universityService;

    public UniversityFacade(UniversityService universityService) {
        this.universityService = universityService;
    }

    public UniversityDto get(long id) {

        University university = universityService.get(id);

        return setUniversityDto(university);
    }

    public List<UniversityDto> getAll() {

        List<University> universityList = universityService.getAll();
        List<UniversityDto> universityDtoList = new ArrayList<>();

        for (University university : universityList) {

            UniversityDto universityDto = setUniversityDto(university);
            universityDtoList.add(universityDto);
        }

        return universityDtoList;
    }

    public UniversityDto create(UniversityDto newUniversityDto) {

        University newUniversity = setUniversity(newUniversityDto);
        University createdUniversity = universityService.create(newUniversity);
        newUniversityDto.setId(createdUniversity.getId());

        return newUniversityDto;
    }

    public UniversityDto replace(UniversityDto updatedUniversityDto, long id) {

        University updatedUniversity = setUniversity(updatedUniversityDto);
        University newUniversity = universityService.replace(updatedUniversity, id);
        updatedUniversityDto.setId(newUniversity.getId());

        return updatedUniversityDto;
    }

    public void delete(long id) {
        universityService.delete(id);
    }

    private UniversityDto setUniversityDto(University university) {

        long universityId = university.getId();
        String universityName = university.getName();

        UniversityDto universityDto = new UniversityDto();
        universityDto.setId(universityId);
        universityDto.setName(universityName);

        return universityDto;
    }

    private University setUniversity(UniversityDto universityDto) {

        String universityName = universityDto.getName();

        return new University(universityName);
    }
}
