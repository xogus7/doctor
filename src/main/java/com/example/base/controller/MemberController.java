package com.example.base.controller;

import com.example.base.domain.Member;
import com.example.base.service.MemberService;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "members/join";
    }

    @PostMapping("/members/new")
    public String create(@Valid @ModelAttribute("memberForm") MemberForm memberForm, BindingResult bindingResult){
        System.out.println("hi");
        System.out.println(memberForm.getName());
        System.out.println(memberForm.getPassword());
        if(bindingResult.hasErrors()){
            return "members/join";
        }

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setPassword(memberForm.getPassword());

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
