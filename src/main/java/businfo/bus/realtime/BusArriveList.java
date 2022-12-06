package businfo.bus.realtime;

import businfo.bus.Repository.StationNumberRepository;
import businfo.bus.entity.StationNumber;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusArriveList {
    private final StationNumberRepository stationNumberRepository;

    public List<BusArriveDto> stationArriveBusInfo(String stationId) throws ParseException, IOException {
        String str = stationId.replaceAll("[^0-9]", "");
        StationNumber stationNumber = stationNumberRepository.findByStationNumber(Long.parseLong(str));
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/ArvlInfoInqireService/getSttnAcctoArvlPrearngeInfoList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=vGKwtPaagiEds81he%2FKXtCML7U%2FrDIRgoPen0mI3zSkmRWQKJqXezkm%2F8p8wod4TKBREHoM7LZ%2Fc9T7xrwcRQw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode("33010", "UTF-8")); /*도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능*/
        urlBuilder.append("&" + URLEncoder.encode("nodeId","UTF-8") + "=" + URLEncoder.encode(stationNumber.getStationId(), "UTF-8")); /*정류소ID [국토교통부(TAGO)_버스정류소정보]에서 조회가능*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        String copy = sb.toString();
        if (copy == null || copy.trim().isEmpty()){
            return new ArrayList<>();
        }
        else {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(copy);
            JSONObject response = (JSONObject) jsonObject.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONArray item = (JSONArray) items.get("item");
            String nodeName = "";
            List<BusArriveDto> arriveList = new ArrayList<>();
            for (int i = 0; i < item.size(); i++) {
                JSONObject item_nodeNm = (JSONObject) item.get(i);
                BusArriveDto busArriveDto = new BusArriveDto();
                nodeName = item_nodeNm.get("routeno") + "";
                busArriveDto.setRouteNo(nodeName);
                nodeName = item_nodeNm.get("routetp") + "";
                busArriveDto.setRouteTp(nodeName);
                nodeName = item_nodeNm.get("vehicletp") + "";
                busArriveDto.setVehicleTp(nodeName);
                nodeName = item_nodeNm.get("arrprevstationcnt") + "";
                busArriveDto.setArriveCount(Long.parseLong(nodeName));
                nodeName = item_nodeNm.get("arrtime") + "";
                busArriveDto.setArriveTimeSec(Long.parseLong(nodeName));
                busArriveDto.setArriveTimeMin(Long.parseLong(nodeName)/60L);

                arriveList.add(busArriveDto);
            }
            rd.close();
            conn.disconnect();
            for (BusArriveDto busArriveDto : arriveList) {
                System.out.println("버스 번호:" +busArriveDto.getRouteNo() + " 버스종류:" + busArriveDto.getRouteTp()
                        + " 차량 종류:" + busArriveDto.getVehicleTp() + " 남은 정류장 수:" + busArriveDto.getArriveCount()
                        + " 남은 분:" + busArriveDto.getArriveTimeMin() + ": " + stationNumber.getStationName());
            }
            return arriveList;
        }

    }


}
