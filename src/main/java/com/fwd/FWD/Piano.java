package com.fwd.FWD;

public class Piano implements Instrument {
  private int numberPiano;

  Piano(){
    tune();
  }

  Piano(int number) {
    new Piano();
    this.numberPiano = number;
  }

  @Override
  public void play() {
    System.out.println("I play piano " + this.numberPiano);
  }

  public void tune() {
    System.out.println("I enjoy to tune");
  }

  public int getNumberPiano() {
    return numberPiano;
  }

  public void setNumberPiano(int numberPiano) {
    this.numberPiano = numberPiano;
  }
}
