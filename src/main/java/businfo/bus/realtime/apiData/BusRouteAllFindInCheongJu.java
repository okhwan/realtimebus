package businfo.bus.realtime.apiData;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class BusRouteAllFindInCheongJu {

    public List<CheongJuAllBusInfo> BusAllInfo() throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusRouteInfoInqireService/getRouteNoList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=vGKwtPaagiEds81he%2FKXtCML7U%2FrDIRgoPen0mI3zSkmRWQKJqXezkm%2F8p8wod4TKBREHoM7LZ%2Fc9T7xrwcRQw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("200", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode("33010", "UTF-8")); /*도시코드 [상세기능4. 도시코드 목록 조회]에서 조회 가능*/
        urlBuilder.append("&" + URLEncoder.encode("routeNo","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*노선번호*/
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
        List<CheongJuAllBusInfo> RouteList = new ArrayList<>();

        String nodeBus = "";
        for (int i =0; i<item.size();i++){
            CheongJuAllBusInfo  cheongJuAllBusInfo = new CheongJuAllBusInfo();
            JSONObject nodeTotal = (JSONObject) item.get(i);
            nodeBus = nodeTotal.get("routeid") + "";
            cheongJuAllBusInfo.setRouteId(nodeBus);
            nodeBus = nodeTotal.get("routeno") + "";
            cheongJuAllBusInfo.setRouteNo(nodeBus);
            nodeBus = nodeTotal.get("routetp") + "";
            cheongJuAllBusInfo.setRouteType(nodeBus);
            RouteList.add(cheongJuAllBusInfo);
        }
        for (CheongJuAllBusInfo cheongJuAllBusInfo : RouteList) {
            System.out.println("1 = " + cheongJuAllBusInfo.getRouteId() + " 2 :" + cheongJuAllBusInfo.getRouteNo() + " 3 :" + cheongJuAllBusInfo.getRouteType() );
        }
        System.out.println(RouteList.size());
        rd.close();
        conn.disconnect();
        return RouteList;
    }

}
