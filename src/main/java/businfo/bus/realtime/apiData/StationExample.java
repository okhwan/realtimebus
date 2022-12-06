package businfo.bus.realtime.apiData;

import businfo.bus.Repository.RouteNumberRepository;
import businfo.bus.entity.RouteNumber;
import businfo.bus.entity.StationNumber;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/request")
@RequiredArgsConstructor
public class StationExample{
    private final RouteNumberRepository routeNumberRepository;


    @GetMapping("/station")
    public void stationFind() throws IOException, ParseException{
        BusRouteAllFindInCheongJu busRouteAllFindInCheongJu = new BusRouteAllFindInCheongJu();
        List<CheongJuAllBusInfo> allBusInfos = busRouteAllFindInCheongJu.BusAllInfo();
        System.out.println("List size: " + allBusInfos.size());
        for (CheongJuAllBusInfo allBusInfo : allBusInfos) {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusRouteInfoInqireService/getRouteAcctoThrghSttnList"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=vGKwtPaagiEds81he%2FKXtCML7U%2FrDIRgoPen0mI3zSkmRWQKJqXezkm%2F8p8wod4TKBREHoM7LZ%2Fc9T7xrwcRQw%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("200", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
            urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode("33010", "UTF-8")); /*도시코드 [상세기능4. 도시코드 목록 조회]에서 조회 가능*/
            urlBuilder.append("&" + URLEncoder.encode("routeId","UTF-8") + "=" + URLEncoder.encode(allBusInfo.getRouteId(), "UTF-8")); /*노선ID [상세기능1. 노선번호목록 조회]에서 조회 가능*/
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
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(copy);
            JSONObject response = (JSONObject) jsonObject.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONArray item = (JSONArray) items.get("item");
            String nodeName = "";
            for (int i=0;i<item.size();i++){
                RouteNumber routeNumber = new RouteNumber();
                StationNumber stationNumber = new StationNumber();
                JSONObject item_nodeNm = (JSONObject) item.get(i);
                routeNumber.setRouteId(allBusInfo.getRouteNo());

                nodeName = item_nodeNm.get("nodeid") + "";
                stationNumber.setStationId(nodeName);
                routeNumber.setStationId(stationNumber);

                if (item_nodeNm.get("nodeno") == null){
                    routeNumber.setStationNumber(0L);
                }
                else{
                    nodeName = item_nodeNm.get("nodeno") + "";
                    routeNumber.setStationNumber(Long.parseLong(nodeName));
                }

                nodeName = item_nodeNm.get("nodenm") + "";
                routeNumber.setStationName(nodeName);
                nodeName = item_nodeNm.get("nodeord") + "";
                routeNumber.setSequenceNumber(Integer.parseInt(nodeName));
                nodeName = item_nodeNm.get("updowncd") + "";
                routeNumber.setUpDown(Integer.parseInt(nodeName));
                System.out.println(routeNumber.getRouteId() + " " + routeNumber.getStationName() + " " + routeNumber.getStationNumber() + " "
                        +routeNumber.getSequenceNumber() + " " + routeNumber.getUpDown());
                nodeName += item_nodeNm.get("nodenm") + ": " + item_nodeNm.get("nodeno") + " "+ item_nodeNm.get("nodeord")+ "번 ";
                routeNumberRepository.save(routeNumber); //여기서 오류
            }



            rd.close();
            conn.disconnect();
            System.out.println(sb.toString());
        }

    }
}
