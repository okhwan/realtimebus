package businfo.bus.memberController;

import businfo.bus.Repository.MemberRepository;
import businfo.bus.entity.MemberForm;
import businfo.bus.mongodb.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberFindPwController {

    private final MemberRepository memberRepository;
    private final LogService logService;

    @GetMapping("/findPw")
    public String findPwForm() throws UnknownHostException {
        InetAddress ipAddress = InetAddress.getLocalHost();
        logService.mongoInsert(ipAddress.getHostAddress(), "Enter the findPwFormPage", log.getName());
        return "/member/findByPw";
    }

    @PostMapping("/findPw")
    public String findByPw(String id, String email, Model model) throws UnknownHostException {
        MemberForm findMember = memberRepository.findByIdAndEmail(id, email);
        InetAddress ipAddress = InetAddress.getLocalHost();
        logService.mongoInsert(ipAddress.getHostAddress(), "Enter the findPwPage", log.getName());
        if(findMember instanceof MemberForm){
            model.addAttribute("member", findMember);
            return "/member/findPw";
        }
        else{
            return "/member/error";
        }

    }
}
