package businfo.bus.memberController;

import businfo.bus.Repository.MemberRepository;
import businfo.bus.entity.MemberForm;
import businfo.bus.mongodb.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberSignUpController {
    private final MemberRepository memberRepository;
    private final LogService logService;

    @GetMapping("/signUp")
    private String addForm() throws UnknownHostException {
        InetAddress ipAddress = InetAddress.getLocalHost();
        logService.mongoInsert(ipAddress.getHostAddress(), "Enter the findPwFormPage", log.getName());
        return "member/signUp";
    }

    @PostMapping("/signUp")
    private String addMember(@ModelAttribute MemberForm memberForm, RedirectAttributes redirectAttributes){
        MemberForm saveMember = memberRepository.save(memberForm);
        redirectAttributes.addAttribute("memberId", saveMember.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/member/{memberId}";
    }

    @GetMapping("/{memberId}")
    public String member(@PathVariable String memberId, Model model){
        MemberForm memberForm = memberRepository.findById(memberId);
        model.addAttribute("member", memberForm);
        return "member/member";
    }


}
