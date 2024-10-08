package project.saving_web_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.saving_web_service.domain.Member;
import project.saving_web_service.service.MemberService;

@Controller
@RequiredArgsConstructor
public class JoinMemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){
        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Member member = new Member();

        member.setLogin_id(form.getLogin_id());
        member.setPassword(form.getPassword());
        member.setStatus(form.getStatus());
        member.setField(form.getField());
        member.setPurpose(form.getPurpose());
        member.setPreferredCondition(form.getPreferredCondition());
        member.setPeriod(form.getPeriod());
        member.setImportant(form.getImportant());
        member.setAmount(form.getAmount());

        memberService.join(member);
        return "redirect:/";

    }


}
