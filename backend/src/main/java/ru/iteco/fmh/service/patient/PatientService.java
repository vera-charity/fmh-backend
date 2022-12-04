package ru.iteco.fmh.service.patient;

import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRq;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRs;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

import java.util.List;

public interface PatientService {
    /**
     * возвращает список всех пациентов с активной госпитализацией
     *
     * @param patientStatusList список значений для фильтра по госпитализации
     * @return список всех пациентов в зависимости от статуса госпитализации
     */
    PatientAdmissionDto getAllPatientsByStatus(List<AdmissionsStatus> patientStatusList, int pages, int elements, boolean isActive);

    /**
     * создает новую карточку пациента
     * @param patientCreateInfoDtoRq информация по карточке пациента для создания
     * @return сущность
     */
    PatientCreateInfoDtoRs createPatient(PatientCreateInfoDtoRq patientCreateInfoDtoRq);


    /**
     * бновляет информацию о пациенте
     *
     * @param patientDto информация по карточке пациента для обновления
     * @return сущность
     */
    PatientDto updatePatient(PatientDto patientDto);

    /**
     * возвращает полную инфу по конкретному пациенту
     *
     * @param id ид пациента
     * @return полная инфа по конкретному пациенту
     */
    PatientDto getPatient(Integer id);
}