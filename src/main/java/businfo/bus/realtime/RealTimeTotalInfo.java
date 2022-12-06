package businfo.bus.realtime;

import businfo.bus.Repository.RouteNumberRepository;
import businfo.bus.Repository.StationNumberRepository;
import businfo.bus.entity.RouteNumber;
import businfo.bus.entity.StationNumber;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RealTimeTotalInfo {
    private final BusArriveList busArriveList;
    private final StationNumberRepository stationNumberRepository;
    private final RouteNumberRepository routeNumberRepository;

    public List<OneTransferDto> BusArriveList(String startStationId, String endStationId) throws ParseException, IOException { //출발 도착 정류장 정보를 통해 실시간 버스 정보를 받아온다.
        List<BusArriveDto> startArriveList = busArriveList.stationArriveBusInfo(startStationId); //출발 정류장으로 오는 버스들의 정보 리스트
        List<BusArriveDto> endArriveList = busArriveList.stationArriveBusInfo(endStationId); // 도착 정류장으로 오는 버스들의 정보 리스트
        List<BusArriveDto> noTransferList = NoTransferSearch(startArriveList, endArriveList); //환승 없이 가는 알고리즘
        List<BusArriveDto> endNoTransferList = endNoTransferSearch(startArriveList, endArriveList); //환승 없이 가는 알고리즘

        startArriveList.removeAll(noTransferList);

        endArriveList.removeAll(endNoTransferList);



        List<OneTransferDto> oneTransferDtoList = OneTransferSearch(startArriveList, endArriveList, startStationId, endStationId); // 1번 환승 하는 알고리즘
        List<OneTransferDto> allTransferList = shortestTime(noTransferList, oneTransferDtoList);
        for (OneTransferDto dto : allTransferList) {
            if (dto.getEndBusArriveDto() != null){
                System.out.println("출발 버스: " + dto.getStartBusArriveDto().getRouteNo());
                System.out.println("도착까지 남은 시간: "+ dto.getStartBusArriveDto().getArriveTimeMin());
                System.out.println("환승 버스 " + dto.getEndBusArriveDto().getRouteNo());
                System.out.println("환승 정류장 " + dto.getTransferStationName());
                System.out.println("환승번호 = " + dto.getTransferStationNumber());
            }
            else {
                System.out.println("환승없이 가는 버스: " + dto.getStartBusArriveDto().getRouteNo());
                System.out.println("도착까지 남은 시간: "+ dto.getStartBusArriveDto().getArriveTimeMin());

            }
            System.out.println();
        }

        return allTransferList;

    }


    public List<OneTransferDto> shortestTime(List<BusArriveDto> noTransferList, List<OneTransferDto> oneTransferList){
        List<OneTransferDto> shortTimeList = new ArrayList<>();
        for (BusArriveDto noTransfer : noTransferList) {
            OneTransferDto oneTransferDto = new OneTransferDto();
            oneTransferDto.setStartBusArriveDto(noTransfer);
            shortTimeList.add(oneTransferDto);
        }
        for (OneTransferDto oneTransferDto : oneTransferList) {
            shortTimeList.add(oneTransferDto);
        }
        Collections.sort(shortTimeList, new UserComparator()); //출발 정류장에 오는 순서대로 정렬
        Collections.sort(shortTimeList, new UserComparatorEnd()); //환승 정류장에 오는 버스  정렬
        removeDuplicateList(shortTimeList);
        return shortTimeList;

    }
    public List<BusArriveDto> endNoTransferSearch(List<BusArriveDto> startList, List<BusArriveDto> endList){
        List<BusArriveDto> noTransferList = new ArrayList<>();
        for (BusArriveDto start : startList) {
            for (BusArriveDto end : endList) {
                if(start.getRouteNo().equals(end.getRouteNo())){
                    noTransferList.add(end); //사용자는 도착정류장에서의 버스정보가아닌 현재 자신이 탈 곳인 출발정류장에서의 버스정보를 알아야 하므로 end 가 아닌 start 를 넣는다.
                }
            }
        }
        return noTransferList;
    }
    public List<BusArriveDto> NoTransferSearch(List<BusArriveDto> startList, List<BusArriveDto> endList){
        List<BusArriveDto> noTransferList = new ArrayList<>();
        for (BusArriveDto start : startList) {
            for (BusArriveDto end : endList) {
                if(start.getRouteNo().equals(end.getRouteNo())){
                    noTransferList.add(start); //사용자는 도착정류장에서의 버스정보가아닌 현재 자신이 탈 곳인 출발정류장에서의 버스정보를 알아야 하므로 end 가 아닌 start 를 넣는다.
                }
            }
        }
        return noTransferList;
    }
    public List<OneTransferDto> OneTransferSearch(List<BusArriveDto> startList, List<BusArriveDto> endList, String startStation, String endStation) throws ParseException, IOException {
        String startStationNum = startStation.replaceAll("[^0-9]", "");
        StationNumber startStationNumber = stationNumberRepository.findByStationNumber(Long.parseLong(startStationNum));
        String endStationNum = endStation.replaceAll("[^0-9]", "");
        StationNumber endStationNumber = stationNumberRepository.findByStationNumber(Long.parseLong(endStationNum));

        List<OneTransferDto> oneTransferList = new ArrayList<>();
        for (BusArriveDto start : startList) { //출발 정류장에 오는 버스 list
            for (BusArriveDto end : endList) { //도착 정류장에 오는 버스 list
                RouteNumber startRouteNumber = routeNumberRepository.findByRouteIdAndStationId(start.getRouteNo(), startStationNumber); //출발 정류장에 오는 버스 하나에 대한 컬럼 하나 가져온다.
                List<RouteNumber> startBusStationList = //출발 정류장에 오는 버스 하나에 대해 해당 버스가 종점까지의 정보를 가져온다.
                        routeNumberRepository.
                                findByRouteIdAndSequenceNumberGreaterThanAndUpDownEquals(start.getRouteNo(), startRouteNumber.getSequenceNumber(), startRouteNumber.getUpDown());


                RouteNumber endRouteNumber = routeNumberRepository.findByRouteIdAndStationId(end.getRouteNo(), endStationNumber); //도착 정류장에 오는 버스 하나에 대한 컬럼 하나를 가져온다.
                List<RouteNumber> endBusStationList = //도착 정류장에 오는 버스 하나에 대한 해당 버스가 종점까지의 정보를 가져온다.
                        routeNumberRepository.
                                findByRouteIdAndSequenceNumberLessThanAndUpDownEquals(end.getRouteNo(), endRouteNumber.getSequenceNumber(), endRouteNumber.getUpDown());

                Loop1 :
                for (RouteNumber startStationList : startBusStationList) { //출발 정류장에 오는 버스 하나의 종점까지의 RouteNumber 리스트
                    for (RouteNumber endStationList : endBusStationList) { // 도착 정류장에 오는 버스 하나의 종점까지의 RouteNumber 리스트
                        if(startStationList.getStationId().equals(endStationList.getStationId())){ //만약 두 버스가 겹치는 정류장이 존재하면 해당 정류장은 환승이 가능한 정류장임.
                            System.out.println("startStationList = " + startStationList.getStationName() + " " + startStationList.getStationNumber());
                            List<BusArriveDto> overLapStationBusList = busArriveList.stationArriveBusInfo(String.valueOf(startStationList.getStationNumber())); //환승정류장에 오는 버스list

                            int startCount = 0;
                            int endCount = 0;
                            for (BusArriveDto arriveDto : overLapStationBusList) {
                                if (arriveDto.getRouteNo().equals(start.getRouteNo())){
                                    startCount = overLapStationBusList.indexOf(arriveDto);
                                }
                                else if (arriveDto.getRouteNo().equals(end.getRouteNo())){
                                    endCount = overLapStationBusList.indexOf(arriveDto);
                                }
                            }
                            System.out.println("startCount = " + startCount);
                            System.out.println("endCount = " + endCount);

                            if (overLapStationBusList.get(startCount).getArriveTimeSec() <
                                    overLapStationBusList.get(endCount).getArriveTimeSec()) { //출발 버스가 환승 정류장에서 도착 버스보다 빨리 올 경우( 환승 가능한 경우)
                                OneTransferDto oneTransferDto = new OneTransferDto();
                                oneTransferDto.setStartBusArriveDto(start);
                                oneTransferDto.setEndBusArriveDto(end);
                                oneTransferDto.setTransferStationId(startStationList.getStationId().getStationId());
                                oneTransferDto.setTransferStationName(startStationList.getStationName());
                                oneTransferDto.setTransferStationNumber(startStationList.getStationNumber());
                                oneTransferList.add(oneTransferDto);
                            }
                            break Loop1;
                        }
                    }
                }

            }
        }

        return oneTransferList;
    }
//    public List<RealTimeBusInfoDto> realTimeCrawling(String stationId){
//        RealTimeBusInfoService realTimeBusInfoService1 = new RealTimeBusInfoService();
//        List<RealTimeBusInfoDto> realTimeBusInfoDto1 = realTimeBusInfoService1.realTimeService(stationId);
//        return realTimeBusInfoDto1;
//
//    }
    public List<OneTransferDto> removeDuplicateList(List<OneTransferDto> shortTimeList){
        for(int i=0;i<shortTimeList.size()-1;i++){
            for(int j=i+1; j<shortTimeList.size();j++){
                if(shortTimeList.get(i).getStartBusArriveDto().getRouteNo().equals(shortTimeList.get(j).getStartBusArriveDto().getRouteNo())){
                    shortTimeList.remove(j);
                }
            }
        }
        return shortTimeList;
    }

}
class UserComparator implements Comparator<OneTransferDto>{
    @Override
    public int compare(OneTransferDto a, OneTransferDto b){
        if (a.getStartBusArriveDto().getArriveTimeSec() > b.getStartBusArriveDto().getArriveTimeSec())
            return 1;
        if(a.getStartBusArriveDto().getArriveTimeSec() < b.getStartBusArriveDto().getArriveTimeSec())
            return -1;
        return 0;
    }
}
class UserComparatorEnd implements Comparator<OneTransferDto>{
    @Override
    public int compare(OneTransferDto a, OneTransferDto b){
        if(a.getStartBusArriveDto().getRouteNo().equals(b.getStartBusArriveDto().getRouteNo())) {
            if (a.getEndBusArriveDto().getArriveTimeSec() > b.getEndBusArriveDto().getArriveTimeSec())
                return 1;
            if(a.getEndBusArriveDto().getArriveTimeSec() < b.getEndBusArriveDto().getArriveTimeSec())
                return -1;
            return 0;
        }
        return 0;
    }
}

