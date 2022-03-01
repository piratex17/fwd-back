package com.fwd.FWD;

public class Drum implements Instrument {
  private int numberDrum;

  Drum() {
    assembly();
  }

  Drum(int numberDrum) {
    new Drum();
    this.numberDrum = numberDrum;
  }

  @Override
  public void play() {
    System.out.println("I play drum" + this.numberDrum);
  }

  public void assembly() {
    System.out.println("I enjoy the assembly");
  }

  public int getNumberDrum() {
    return numberDrum;
  }

  public void setNumberDrum(int numberDrum) {
    this.numberDrum = numberDrum;
  }
}
