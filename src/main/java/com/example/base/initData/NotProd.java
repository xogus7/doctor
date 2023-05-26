//package com.example.base.initData;
//
//import com.example.base.domain.Patient;
//import com.example.base.service.PatientService;
//import com.ll.gramgram.boundedContext.instaMember.service.InstaMemberService;
//import com.ll.gramgram.boundedContext.likeablePerson.service.LikeablePersonService;
//import com.ll.gramgram.boundedContext.member.entity.Member;
//import com.ll.gramgram.boundedContext.member.service.MemberService;
//import java.time.LocalDateTime;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//@Configuration
//@Profile({"dev", "test"})
//public class NotProd {
//    LocalDateTime localDateTime;
//    @Bean
//    CommandLineRunner initData(
//            PatientService patientService
//    ) {
//        return args -> {
//            Patient memberAdmin = patientService.join(new Patient(1L,"김재성","남자"))
//            Patient memberUser1 = patientService.join("user1", "1234").getData();
//            Patient memberUser2 = patientService.join("user2", "1234").getData();
//            Patient memberUser3 = patientService.join("user3", "1234").getData();
//            Patient memberUser4 = patientService.join("user4", "1234").getData();
//
//        };
//    }
//}
//
