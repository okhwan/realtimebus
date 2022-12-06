package businfo.bus.realtime.ver0;

import businfo.bus.dto.RealTimeBusInfoDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class BusRouteCrawlingExtract {
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:\\Users\\vpdjf\\Desktop\\2022-2\\창프\\chromedriver_win32\\chromedriver.exe";


    public void realTimeService(String startStationNumber) {
        //크롬 드라이버 사용하기 위해 로딩
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        //크롬 브라우저를 열어줄 때 사용할 옵션 객체 만들기
        ChromeOptions options = new ChromeOptions();

//        options.addArguments("headless") 브라우저 창 안띄우기

        String url = "https://www.dcbis.go.kr/search/busStop.do";

//        아까 설정해준 옵션들을 기준으로 크롬 드라이버 실행하기
        WebDriver driver = new ChromeDriver(options);

//        실행된 드라이버로 주어진 url 접속시키기
        driver.get(url);

//        url 실행이 코드보다 느리게 실행될 수도 있으니까 thread 작성
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        버스 id 로 검색할 거라 버스 정류장 명 에서 id로 클릭해서 바꿈
        WebElement useBusId = driver.findElement(By.xpath("//*[@id=\"busSearchForm\"]/fieldset/select/option[2]"));
        useBusId.click();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //페이지 내에서 요소 찾기
        WebElement searchInput = driver.findElement(By.id("searchKeyword"));

        //검색창 클릭
        searchInput.click();
        //검색창 내에 검색할 값 입력하기 (여기에는 사용자로부터 받은 정류장 번호가 들어가야 한다.)
        searchInput.sendKeys(startStationNumber);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //driver에서 버스 검색 버튼 찾기 해당 정류장으로 오는 버스를 알기 위함
        WebElement searchBtn = driver.findElement(By.xpath("//*[@id=\"busSearchForm\"]/fieldset/button[1]"));

        searchBtn.click();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement productDiv = driver.findElement(By.className("station"));

        WebElement searchBusStation = productDiv.findElement(By.xpath("//*[@id=\"tbodyList\"]/tr/th"));
        searchBusStation.click();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        List<RealTimeBusInfoDto> realTimeBus = new ArrayList<>();

        WebElement webElement = driver.findElement(By.xpath("//*[@id=\"busPassView\"]/div[2]/table"));
        List<WebElement> rows_table = webElement.findElements(By.tagName("tr"));
        int rows_count = rows_table.size();
        for (int i = 1; i < rows_count; i++) {

            RealTimeBusInfoDto realTimeBusInfoDto = new RealTimeBusInfoDto();
            List<WebElement> columns_row = rows_table.get(i).findElements(By.tagName("td"));
            int columns_count = columns_row.size();

            realTimeBusInfoDto.setRoute(columns_row.get(0).getText());
            realTimeBusInfoDto.setRouteTime(columns_row.get(1).getText());
            realTimeBusInfoDto.setRouteStation(columns_row.get(2).getText());
            realTimeBus.add(realTimeBusInfoDto);

            for (RealTimeBusInfoDto timeBus : realTimeBus) {
                System.out.println("timeBus = " + timeBus);
            }
        }
    }
}
