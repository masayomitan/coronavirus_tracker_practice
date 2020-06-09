package com.example.demo.controllers;

import java.util.List;
import com.example.demo.models.DeathStats;
import com.example.demo.services.coronaVirusDataDeath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class DeathController {
  
  @Autowired
  coronaVirusDataDeath coronaVirusDataDeath;

  @GetMapping("/death")
  public String death(Model Model){

  List<DeathStats> allDeathStats = coronaVirusDataDeath.getAllDeathStats();

  int totalReportedDeathCases = allDeathStats.stream().mapToInt(stat -> stat.getLatestTotalDeathCases()).sum();
  int totalNewDeathCases = allDeathStats.stream().mapToInt(stat -> stat.getDiffDeathFromPrevDay()).sum();

  Model.addAttribute("deathStats", allDeathStats);
  Model.addAttribute("totalReportedDeathCases", totalReportedDeathCases + "人");
  Model.addAttribute("totalNewDeathCases", totalNewDeathCases + "人");

  return "death";
  }
}