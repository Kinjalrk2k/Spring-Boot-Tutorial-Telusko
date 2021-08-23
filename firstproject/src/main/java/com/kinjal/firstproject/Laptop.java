package com.kinjal.firstproject;

import org.springframework.stereotype.Component;

@Component("lappy")
public class Laptop {
  private int lid;
  private String brand;

  public int getLid() {
    return lid;
  }

  public void setLid(int lid) {
    this.lid = lid;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  @Override
  public String toString() {
    return "Laptop [brand=" + brand + ", lid=" + lid + "]";
  }

  public void compile() {
    System.out.println("Compiling...!");
  }

}
