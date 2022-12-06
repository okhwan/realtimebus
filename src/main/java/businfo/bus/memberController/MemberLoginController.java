package businfo.bus.memberController;

import businfo.bus.Repository.MemberRepository;
import businfo.bus.entity.MemberForm;
import businfo.bus.mongodb.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class MemberLoginController {
    private final HttpSession httpSession;
    private final LogService logService;
    private final MemberRepository memberRepository;
    private final String MEMBER_ID = "MEMBERID";

    @PostMapping("/member/mainLogin")
    public String accessId(String memberId, String memberPw, Model model, HttpSession session) throws UnknownHostException {
        MemberForm findMember = memberRepository.findByIdAndPw(memberId, memberPw);
        InetAddress ipAddress = InetAddress.getLocalHost();
        logService.mongoInsert(ipAddress.getHostAddress(), "Enter the LoginPage", log.getName());
        if(findMember instanceof MemberForm){
            httpSession.setAttribute(MEMBER_ID, findMember.getId());

            return "redirect:/bookmark/memberBookmark";
        }
        else {
            return "/member/error";
        }
    }


}
