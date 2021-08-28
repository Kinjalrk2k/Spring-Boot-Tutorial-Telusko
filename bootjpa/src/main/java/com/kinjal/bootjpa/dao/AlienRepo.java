package com.kinjal.bootjpa.dao;

import com.kinjal.bootjpa.model.Alien;

import org.springframework.data.repository.CrudRepository;

public interface AlienRepo extends CrudRepository<Alien, Integer> {

}
