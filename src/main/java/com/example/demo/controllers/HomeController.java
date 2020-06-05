package com.example.demo.controllers;

import java.util.List;
import com.example.demo.models.LocationStats;
import com.example.demo.services.CoronaVirusDataGlobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  //@AutowiredでCoronavirusDataGlobalのフィールド単位で付与する。
  //付与されたフィールドの型と合うBeanを自動的にDIしてくれる。型と合うBeanが複数ある場合は、@Qualifierアノテーションを使用して一意に識別させる。
  @Autowired
  CoronaVirusDataGlobal coronaVirusDataGlobal;

  @GetMapping("/")
  //Modelの情報をviewへ送る関数
  //@ModelAttributでDRYもできる https://kazkn.com/post/2017/use-model-attribute/
  public String home(Model Model){

    //servicecoronaVirusDataGlobalで取得したデータをgetしてリストに代入
    List<LocationStats> allStats = coronaVirusDataGlobal.getAllStats();

    //stream()でデータに対する処理（集計、変換）を行う。
    //今回はmapToIntの中間処理で、Stringから、int に変換してからsum()で合計を返している
    int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
    int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();

    //addAttributeでviewに渡す。 usage  addAttribute("変数名", オブジェクト)
    Model.addAttribute("locationStats", allStats);
    Model.addAttribute("totalReportedCases", totalReportedCases);
    Model.addAttribute("totalNewCases", totalNewCases);

    //最後はviewのhomeへ
    return "home";
  }
  

}