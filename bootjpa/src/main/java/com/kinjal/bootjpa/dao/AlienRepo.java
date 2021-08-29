package com.kinjal.bootjpa.dao;

import java.util.List;

import com.kinjal.bootjpa.model.Alien;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AlienRepo extends CrudRepository<Alien, Integer> {
  List<Alien> findByTech(String tech);

  List<Alien> findByAidGreaterThan(int aid);

  @Query("from Alien where tech=?1 order by aname")
  List<Alien> findByTechSorted(String tech);
}
