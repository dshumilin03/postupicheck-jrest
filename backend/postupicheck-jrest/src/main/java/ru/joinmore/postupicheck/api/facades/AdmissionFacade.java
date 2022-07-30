package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.converters.AdmissionConverter;
import ru.joinmore.postupicheck.api.converters.AdmissionReverseConverter;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.entities.*;
import ru.joinmore.postupicheck.api.services.*;

import java.util.List;

@Component
public class AdmissionFacade {

    private final AdmissionService admissionService;
    private final AdmissionConverter converter;


    private final AdmissionReverseConverter reverseConverter;

    public AdmissionFacade(AdmissionService admissionService,
                           AdmissionConverter converter,
                           AdmissionReverseConverter reverseConverter) {

        this.admissionService = admissionService;
        this.converter = converter;
        this.reverseConverter = reverseConverter;

    }

    public AdmissionDto get(long id) {

        Admission admission = admissionService.get(id);

        return converter.convert(admission);
    }

    public List<AdmissionDto> getAll() {

        List<Admission> admissionList = admissionService.getAll();

        return converter.convert(admissionList);
    }

    public AdmissionDto create(AdmissionDto newAdmissionDto) {

        Admission newAdmission = reverseConverter.convert(newAdmissionDto);
        Admission createdAdmission = admissionService.create(newAdmission);

        return converter.convert(createdAdmission);
    }

    public AdmissionDto replace(AdmissionDto updatedAdmissionDto, long id) {

        Admission updatedAdmission = reverseConverter.convert(updatedAdmissionDto);
        Admission newAdmission = admissionService.replace(updatedAdmission, id);

        return converter.convert(newAdmission);
    }

    public void delete(long id) {
        admissionService.delete(id);
    }

    public void deleteAll() {
        admissionService.deleteAll();
    }

}
