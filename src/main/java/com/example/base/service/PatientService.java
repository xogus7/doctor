package com.example.base.service;

import com.example.base.domain.Member;
import com.example.base.domain.Patient;
import com.example.base.repository.PatientRepository;
import com.example.base.repository.PatientSearch;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    @Transactional
    public void createPatient(Patient patient){
        patientRepository.save(patient);
    }

    @Transactional
    public void updatePatient(Long PatientId, String name, String birthday, String gender, String gurdianPhoneNumber, Long correctionTime,Long wearingTime, Long correctionDay, Long wearingDay){
        Patient findPatient = patientRepository.findOne(PatientId);

        findPatient.setPatientName(name);
        findPatient.setBirthday(birthday);
        findPatient.setGender(gender);
        findPatient.setCorrectionTime(correctionTime);
        findPatient.setGuardianPhoneNumber(gurdianPhoneNumber);
        findPatient.setCorrectionDay(correctionDay);
        findPatient.setWearingDay(wearingDay);
        findPatient.setWearingTime(wearingTime);
    }

    public List<Patient> findPatients(PatientSearch patientSearch){
        return patientRepository.findAll(patientSearch);
    }

    public Patient findOne(Long patientId){
        return patientRepository.findOne(patientId);
    }

    @Transactional
    public void delete(Long patientId){
        patientRepository.delete(patientId);
    }
}
