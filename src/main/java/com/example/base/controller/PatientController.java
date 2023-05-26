package com.example.base.controller;

import com.example.base.domain.Patient;
import com.example.base.domain.SessionUser;
import com.example.base.repository.PatientSearch;
import com.example.base.service.PatientService;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("patients/new")
    public String createForm(Model model){
        model.addAttribute("form", new PatientForm());
        return "patients/createPatientForm";
    }

    @PostMapping("patients/new")
    public String create(@Valid @ModelAttribute("form") PatientForm patientForm, BindingResult result){

        if(result.hasErrors()){
            System.out.println(result.getAllErrors());
            return "patients/createPatientForm";
        }

        Patient patient = new Patient();
        patient.setPatientName(patientForm.getName());
        patient.setBirthday(patientForm.getBirthday());
        patient.setGender(patientForm.getGender());
        patient.setCorrectionTime(patientForm.getCorrectionTime());
        patient.setWearingTime(patientForm.getWearingTime());
        patient.setCorrectionDay(patientForm.getCorrectionDay());
        patient.setWearingDay(patientForm.getWearingDay());
        patient.setGuardianPhoneNumber(patientForm.getGuardianPhoneNumber());

        patientService.createPatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/patients")
    public String patientsList(@ModelAttribute("patientSearch")PatientSearch patientSearch, Model model,
                               HttpSession httpSession){
        SessionUser user = (SessionUser)httpSession.getAttribute("user");
        if(user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userEmail", user.getEmail());
            model.addAttribute("userRole", user.getRole());

        }
        List<Patient> patients = patientService.findPatients(patientSearch);
        model.addAttribute("patients", patients);
        System.out.println(patients);
        return "patients/patientList";
    }

    @GetMapping("patients/{patientId}/delete")
    public String deletePatients(@PathVariable("patientId") Long patientId,
                                 Model model){
        patientService.delete(patientId);
        return "redirect:/patients";
    }

    @GetMapping("patients/{patientId}/detail")
    public String detailForm(@PathVariable("patientId") Long patientId, Model model){
        Patient patient = patientService.findOne(patientId);
        model.addAttribute("form", patient);
        return "patients/detailPatient";
    }


    @GetMapping("patients/{patientId}/edit")
    public String updateItemForm(@PathVariable("patientId") Long patientId, Model model){

        Patient patient = patientService.findOne(patientId);

        PatientForm form = new PatientForm();

        form.setName(patient.getPatientName());
        form.setBirthday(patient.getBirthday());
        form.setGuardianPhoneNumber(patient.getGuardianPhoneNumber());
        form.setCorrectionTime(patient.getCorrectionTime());
        form.setWearingTime(patient.getWearingTime());
        form.setCorrectionDay(patient.getCorrectionDay());
        form.setWearingDay(patient.getWearingDay());
        form.setGender(patient.getGender());

        model.addAttribute("form", form);
        return "patients/updatePatientForm";
    }

    @PostMapping("patients/{patientId}/edit")
    public String updateItem(@Valid @PathVariable Long patientId, @ModelAttribute("form") PatientForm form,BindingResult result){
//        Book book = new Book();
//        book.setIsbn(form.getIsbn());
//        book.setId(form.getId());
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());
        if(result.hasErrors()){
            return "patients/updatePatientForm";
        }
        patientService.updatePatient(patientId,form.getName(),form.getBirthday(),form.getGender(),form.getGuardianPhoneNumber(),
                form.getCorrectionTime(),form.getWearingTime(),form.getCorrectionDay(),form.getWearingDay());
        return "redirect:/patients";
    }


}
