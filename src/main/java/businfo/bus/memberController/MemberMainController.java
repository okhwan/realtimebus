package businfo.bus.memberController;

import businfo.bus.mongodb.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class MemberMainController { //welcome page 처리 (바로 로그인, 회원가입, 비회원 사용, 아이디 찾기, 비밀번호 찾기
    private final LogService logService;

    @GetMapping("/")
    public String loadPage() throws UnknownHostException {
        InetAddress ipAddress = InetAddress.getLocalHost();
        logService.mongoInsert(ipAddress.getHostAddress(), "Enter the MainPage", log.getName());
        return "/realTime/welcome_ver1";

    }

    @GetMapping("/member/mainLogin")
    public String mainLoginPage(){
        return "/member/main";
    }



}
