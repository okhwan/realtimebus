package businfo.bus.realTimeController;

import businfo.bus.Repository.StationNumberRepository;
import businfo.bus.entity.StationNumber;
import businfo.bus.mongodb.LogService;
import businfo.bus.realtime.BusArriveDto;
import businfo.bus.realtime.BusArriveList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/realtime")
@RequiredArgsConstructor
@Slf4j
public class StationEnterBusController {
    private final StationNumberRepository stationNumberRepository;
    private final LogService logService;
    private final BusArriveList busArriveList;
    @GetMapping("/stationEnterBus")
    public String stationEnterBusController(Model model) throws UnknownHostException {
        InetAddress ipAddress = InetAddress.getLocalHost();
        logService.mongoInsert(ipAddress.getHostAddress(), "Enter the stationEnterBusPage", log.getName());
        List<StationNumber> stationNumbers = stationNumberRepository.findAll();
        List<String> stringStation= new ArrayList<String>();
        for (StationNumber stationNumber : stationNumbers) {
            String str = stationNumber.getStationNumber() + " " + stationNumber.getStationName();
            stringStation.add(str);
        }
        model.addAttribute("stringStation", stringStation);

        return "/realTime/stationEnterBusFind";
    }

    @GetMapping("/stationEnterBusModal")
    @ResponseBody
    public List<BusArriveDto> stationEnterBusModalController(String stationId) throws ParseException, IOException {
        InetAddress ipAddress = InetAddress.getLocalHost();
        logService.mongoInsert(ipAddress.getHostAddress(), "Enter the stationEnterBusModalPage", log.getName());
        List<BusArriveDto> busArriveModal = busArriveList.stationArriveBusInfo(stationId);
        return busArriveModal;
    }
}
