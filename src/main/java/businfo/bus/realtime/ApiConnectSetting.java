package businfo.bus.realtime;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Getter
@Setter
@Component
public class ApiConnectSetting {
    private String url;
    private String serviceKey;
    private String pageNo;
    private String numOfRows;
    private String _type;
    private String cityCode;
    private String stationId;



    public void SettingApiInfo(String url, String serviceKey, String pageNo, String numOfRows, String _type, String cityCode, String stationId){
        this.url = url;
        this.serviceKey = serviceKey;
        this.pageNo = pageNo;
        this.numOfRows = numOfRows;
        this._type = _type;
        this.cityCode = cityCode;
        this.stationId  = stationId;
    }

    public StringBuilder BusArriveConnectApi() throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + url); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(serviceKey, "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode(_type, "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode(cityCode, "UTF-8")); /*도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능*/
        urlBuilder.append("&" + URLEncoder.encode("nodeId", "UTF-8") + "=" + URLEncoder.encode(stationId, "UTF-8")); /*정류소ID [국토교통부(TAGO)_버스정류소정보]에서 조회가능*/
        return urlBuilder;
    }

}
