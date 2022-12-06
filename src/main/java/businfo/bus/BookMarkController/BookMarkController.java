package businfo.bus.BookMarkController;

import businfo.bus.Repository.EndBookmarkRepository;
import businfo.bus.Repository.MemberRepository;
import businfo.bus.Repository.StartBookmarkRepository;
import businfo.bus.Repository.StationNumberRepository;
import businfo.bus.entity.EndBookmark;
import businfo.bus.entity.MemberForm;
import businfo.bus.entity.StartBookmark;
import businfo.bus.entity.StationNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookMarkController {

    private final StationNumberRepository stationNumberRepository;
    private final MemberRepository memberRepository;
    private final StartBookmarkRepository startBookmarkRepository;
    private final EndBookmarkRepository endBookmarkRepository;

    @GetMapping("/startBookmark")
    public String selectBookmark(Model model) {
        List<StationNumber> stationNumbers = stationNumberRepository.findAll();
        List<String> stringStation= new ArrayList<String>();
        for (StationNumber stationNumber : stationNumbers) {
            String str = stationNumber.getStationNumber() + " " + stationNumber.getStationName();
            stringStation.add(str);
        }
        model.addAttribute("stringStation", stringStation);
        return "bookmark/startBookmark";
    }
    @GetMapping("/memberBookmark")
    public String memberBookmark(Model model){
        List<StationNumber> stationNumbers = stationNumberRepository.findAll();
        List<String> stringStation= new ArrayList<String>();
        for (StationNumber stationNumber : stationNumbers) {
            String str = stationNumber.getStationNumber() + " " + stationNumber.getStationName();
            stringStation.add(str);
        }
        model.addAttribute("stringStation", stringStation);
        return "bookmark/memberBookmark";
    }
    @GetMapping("/saveMemberBookmark")
    public String saveMemberBookmark(String startStation, String endStation, HttpSession session, Model model){
        if(startStation != ""){
            String startStr = startStation.replaceAll("[^0-9]", "");
            StationNumber startStationNumber = stationNumberRepository.findByStationNumber(Long.parseLong(startStr));
            MemberForm memberForm = memberRepository.findById(String.valueOf(session.getAttribute("MEMBERID")));
            System.out.println(memberForm.getId());
            StartBookmark startBookmark = new StartBookmark(
                    memberForm,startStationNumber, startStationNumber.getStationNumber(),startStationNumber.getStationName());
            startBookmarkRepository.save(startBookmark);
        }
        if (endStation != ""){
            String endStr = endStation.replaceAll("[^0-9]", "");
            StationNumber endStationNumber = stationNumberRepository.findByStationNumber(Long.parseLong(endStr));
            MemberForm memberForm = memberRepository.findById(String.valueOf(session.getAttribute("MEMBERID")));
            EndBookmark endBookmark = new EndBookmark(
                    memberForm,endStationNumber, endStationNumber.getStationNumber() ,endStationNumber.getStationName());
            endBookmarkRepository.save(endBookmark);
        }
        else if(startStation == "" && endStation == ""){
            model.addAttribute("status", false);
        }


        List<StationNumber> stationNumbers = stationNumberRepository.findAll();
        List<String> stringStation= new ArrayList<String>();
        for (StationNumber stationNumber : stationNumbers) {
            String str = stationNumber.getStationNumber() + " " + stationNumber.getStationName();
            stringStation.add(str);
        }
        model.addAttribute("stringStation", stringStation);

        return "/bookmark/bookmarkSaveSuccess";
    }

    @GetMapping("/findMemberBookmark")
    public String findMemberBookmark(Model model, HttpSession session){
        List<String> startStation= new ArrayList<String>();
        List<String> endStation= new ArrayList<String>();

        MemberForm findMember = memberRepository.findById(String.valueOf(session.getAttribute("MEMBERID")));

        List<StartBookmark> startBookmarks = startBookmarkRepository.findByMemberForm(findMember);
        List<EndBookmark> endBookmarks = endBookmarkRepository.findByMemberForm(findMember);

        for (StartBookmark bookmark : startBookmarks) {
            String str = bookmark.getStationNumber() + " " + bookmark.getStationName();
            startStation.add(str);
        }

        for (EndBookmark bookmark : endBookmarks) {
            String str = bookmark.getStationNumber() + " " + bookmark.getStationName();
            endStation.add(str);
        }

        model.addAttribute("startStation", startStation);
        model.addAttribute("endStation", endStation);
        return "bookmark/selectMemberBookmark";
    }

    @GetMapping("/findStationCoordinate")
    @ResponseBody
    public StationNumber findStationCoordinate(String stationId){
        String str = stationId.replaceAll("[^0-9]", "");
        StationNumber stationNumber = stationNumberRepository.findByStationNumber(Long.parseLong(str));
        return stationNumber;
    }



}
