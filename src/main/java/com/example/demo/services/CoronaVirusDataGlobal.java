package com.example.demo.services;

import com.example.demo.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


@Service
public class CoronaVirusDataGlobal {

  private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

  //allStatsインスタンス精製
  private List<LocationStats> allStats = new ArrayList<>();
  //getAllStats
  public List<LocationStats> getAllStats() {
        return allStats;
  }

             
  @PostConstruct
  //更新時間
  @Scheduled(cron = "* * 2 * * *")
  public void fetchVirusData() throws IOException, InterruptedException{
    //スクレイピング
    List<LocationStats> newStats = new ArrayList<>();

    //HttpClientで外部のcsvデータを読み込み
    HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
    HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
    //StringReaderクラスをimportし文字列を同期的または非同期的に読み取る
    StringReader csvBodyReader = new StringReader(httpResponse.body());
    //CSVデータの読み込み、箇所の指定 参考 http://commons.apache.org/proper/commons-csv/user-guide.html
    Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
    
    for(CSVRecord record : records){
      LocationStats locationStat = new LocationStats();
      locationStat.setState(record.get("Province/State"));
      locationStat.setCountry(record.get("Country/Region"));
      //parseIntは引数に指定された文字列を整数の値として解析しint型の値として返します(この場合recordは日付)
      //-1は最新日の情報
      int latestCases = Integer.parseInt(record.get(record.size() - 1));
      //-2は前日までの情報
      int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
      locationStat.setLatestTotalCases(latestCases);
      locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
      newStats.add(locationStat);
    }
    this.allStats = newStats;
    }    
  }
