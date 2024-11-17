package project.saving_web_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import project.saving_web_service.domain.Member;
import project.saving_web_service.service.MemberService;

@Controller
@RequiredArgsConstructor
public class JoinMemberController {
    private final MemberService memberService;

    private boolean duplicateChecked = false;
    private String checkedLoginId = "";

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        duplicateChecked = false;
        checkedLoginId = "";
        return "members/createMemberForm";
    }
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){

        if (!duplicateChecked || !form.getLogin_id().equals(checkedLoginId)) {
            // 중복 확인이 안 된 경우, 에러 메시지 추가
            result.reject("duplicateCheck", "아이디 중복 확인을 해주세요.");
            return "members/createMemberForm"; // 수정 필요: 중복 확인 후 페이지를 다시 로드할 때 데이터가 사라질 수 있음
        }


        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Member member = new Member();

        member.setLogin_id(form.getLogin_id());
        member.setPassword(form.getPassword());
        if (form.getStatus() != null) {
            member.setStatus(String.join(",", form.getStatus()));
        }
        member.setAge(form.getAge());
        member.setSex(form.getSex());
        member.setPurpose(form.getPurpose());
        member.setPreferredCondition(form.getPreferredCondition());
        member.setPeriod(form.getPeriod());
        if (form.getImportant() != null) {
            member.setImportant(String.join(",", form.getImportant()));
        }
        member.setAmount(form.getAmount());

        memberService.join(member);
        return "redirect:/";

    }

    @GetMapping("/api/members/check-duplicate")
    @ResponseBody
    public ResponseEntity<String> checkDuplicate(@RequestParam("login_id") String login_id) {
        // 중복된 아이디 체크
        if (memberService.isDuplicateLoginId(login_id)) {
            return ResponseEntity.ok("duplicate");
        } else {
            duplicateChecked = true;
            checkedLoginId = login_id;
            return ResponseEntity.ok("available");
        }
    }


}
