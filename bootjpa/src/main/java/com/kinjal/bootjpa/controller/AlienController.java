package com.kinjal.bootjpa.controller;

import com.kinjal.bootjpa.dao.AlienRepo;
import com.kinjal.bootjpa.model.Alien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AlienController {

  @Autowired
  AlienRepo repo;

  @RequestMapping("/")
  public String home() {
    return "home.jsp";
  }

  @RequestMapping("/addAlien")
  public String addAlien(Alien alien) {
    repo.save(alien);
    return "home.jsp";
  }
}
