package com.example.demo.services;

import com.example.demo.models.DeathStats;
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
public class coronaVirusDataDeath {

  private static String VIRUS_DEATH_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
  
  private List<DeathStats> allDeathStats = new ArrayList<>();

  public List<DeathStats> getAllDeathStats() {
    return allDeathStats;
  }

  @PostConstruct
  @Scheduled(cron = "* * 2 * * *")
  public void fetchVirusDataDeath() throws IOException, InterruptedException{

    List<DeathStats> newDeathStats = new ArrayList<>();
    HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(VIRUS_DEATH_DATA_URL))
        .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
    StringReader csvBodyReader = new StringReader(httpResponse.body());
    Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

    for(CSVRecord record : records){
      DeathStats deathStat = new DeathStats();
      deathStat.setState(record.get("Province/State"));
      deathStat.setCountry(record.get("Country/Region"));

      int latestCases = Integer.parseInt(record.get(record.size() - 1));
      //-2は前日までの情報
      int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
      deathStat.setLatestTotalDeathCases(latestCases);
      deathStat.setDiffDeathFromPrevDay(latestCases - prevDayCases);
      newDeathStats.add(deathStat);
    }
    this.allDeathStats = newDeathStats;
    }    
  }