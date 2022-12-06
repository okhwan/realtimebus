package businfo.bus.realTimeController;

import businfo.bus.Repository.RedisCacheRepository;
import businfo.bus.mongodb.LogService;
import businfo.bus.realtime.BusArriveDto;
import businfo.bus.realtime.DifferentiateTransferList;
import businfo.bus.realtime.OneTransferDto;
import businfo.bus.realtime.RealTimeTotalInfo;
import businfo.bus.redis.RedisFindBusDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Controller
@RequestMapping("/realtime")
@RequiredArgsConstructor
@Slf4j
public class realTimeFindBus {
    private final RealTimeTotalInfo realTimeTotalInfo;
    private final RedisCacheRepository redisCacheRepository;
    private final DifferentiateTransferList differentiateTransferList;
    private final LogService logService;

    @GetMapping("/findBus")
    public String realTimeFindBus(String startStation, String endStation, Model model) throws ParseException, IOException {
        InetAddress ipAddress = InetAddress.getLocalHost();
        logService.mongoInsert(ipAddress.getHostAddress(), "Enter the findBusPage", log.getName());
        List<OneTransferDto> totalTransferList = realTimeTotalInfo.BusArriveList(startStation, endStation);
        List<BusArriveDto> noTransferList = differentiateTransferList.noTransferListDiffer(totalTransferList);
        List<OneTransferDto> oneTransferList = differentiateTransferList.oneTransferListDiffer(totalTransferList);
        RedisFindBusDto redisFindBusDto = new RedisFindBusDto(noTransferList, oneTransferList, startStation, endStation,
                (noTransferList.get(0).getArriveTimeSec() > oneTransferList.get(0).getStartBusArriveDto().getArriveTimeSec())?oneTransferList.get(0).getStartBusArriveDto().getArriveTimeSec():noTransferList.get(0).getArriveTimeSec()); //redis 캐시 저장
        redisCacheRepository.save(redisFindBusDto);

        model.addAttribute("noTransferList", noTransferList);
        model.addAttribute("oneTransferList", oneTransferList);
        model.addAttribute("startStation", startStation.replaceAll("[0-9]", ""));
        model.addAttribute("endStation", endStation.replaceAll("[0-9]", ""));
        return "realTime/findBusInfo";
    }
    @GetMapping("/findBus2")
    public String realTimeFindBus2(Model model) throws UnknownHostException {
        InetAddress ipAddress = InetAddress.getLocalHost();
        logService.mongoInsert(ipAddress.getHostAddress(), "Enter the findBus2Page", log.getName());
        Iterable<RedisFindBusDto> redisFindBus = redisCacheRepository.findAll();
        if (redisFindBus == null){
            return "redirect:/realtime/findBus"; //look aside 패턴 사용 값을 다시 찾아온다.
        }
        else{
            for (RedisFindBusDto findBus : redisFindBus) {
                model.addAttribute("endStation", findBus.getEndStation());
                model.addAttribute("startStation", findBus.getStartStation());
                model.addAttribute("noTransferList", findBus.getNoTransferList());
                model.addAttribute("oneTransferList", findBus.getOneTransferList());
            }
        }


        return "realTime/findBusInfo2";
    }
}
