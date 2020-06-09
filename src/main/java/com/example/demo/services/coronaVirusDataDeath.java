package com.example.demo.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import com.example.demo.models.LocationStats;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class coronaVirusDataDeath {

  private static String VIRUS_DEATH_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv"
  
  private List<DeathStats> allDeathStats = new ArrayList<>();

  public List<DeathStsts> getAllDeathStats() {
    return allDeathStats;
  }

  @PostConstruct
  @Scheduled
  public void fetchVirusData() throws IOException, InterruptedException{

    List<DeathStats> newDeathSTats = new ArrayList<>();
    HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(VIRUS_DEATH_DATA_URL))
        .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
    StringReader csvBodyReader = new StringReader(httpResponse.body());
    Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

    for(CSVRecord record : records){
      LocationStats locationStat = new LocationStats();
      locationStat.setState(record.get("Province/State"));
      locationStat.setCountry(record.get("Country/Region"));

      int latestCases = Integer.parseInt(record.get(record.size() - 1));
      //-2は前日までの情報
      int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
      locationStat.setLatestTotalCases(latestCases);
      locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
      newDeathStats.add(locationStat);
    }
    this.allDeathStats = newDeathStats;
    }    
  }