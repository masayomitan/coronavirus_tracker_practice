package com.example.demo.controllers;

import com.example.demo.services.CoronaVirusDataGlobal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


  //@AutowiredでCoronavirusDataGlobalのフィールド単位で付与する。
  //付与されたフィールドの型と合うBeanを自動的にDIしてくれる。型と合うBeanが複数ある場合は、@Qualifierアノテーションを使用して一意に識別させる。
  @Autowired
  CoronaVirusDataGlobal coronaVirusDataGlobal;

  @GetMapping("/")
  public String home(){
    return "home";
  }
  

}