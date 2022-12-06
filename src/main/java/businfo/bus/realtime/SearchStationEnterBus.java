package businfo.bus.realtime;

import businfo.bus.Repository.StationNumberRepository;
import businfo.bus.entity.StationNumber;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/realtime")
@RequiredArgsConstructor
public class SearchStationEnterBus {
    private final BusArriveList busArriveList;
    private final StationNumberRepository stationNumberRepository;

    @GetMapping("/enterBus")
    public String selectBookmark(Model model) {
        List<StationNumber> stationNumbers = stationNumberRepository.findAll();
        List<String> stringStation= new ArrayList<String>();
        for (StationNumber stationNumber : stationNumbers) {
            String str = stationNumber.getStationNumber() + " " + stationNumber.getStationName();
            stringStation.add(str);
        }
        model.addAttribute("stringStation", stringStation);
        return "realTime/stationEnterBusFind";
    }

    @PostMapping("/enterBus")
    public String stationEnterBus(String StationId, Model model) throws ParseException, IOException {
        List<BusArriveDto> enterBus = busArriveList.stationArriveBusInfo(StationId);
        model.addAttribute(enterBus);
        model.addAttribute(StationId);

        return "/realTime/stationEnterBusFind";
    }

}
