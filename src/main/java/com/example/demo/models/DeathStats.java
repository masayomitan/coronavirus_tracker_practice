package com.example.demo.models;

public class DeathStats {
  private String state;
  private String country;
  private int latestTotalCases;
  private int diffFromPrevDay;

  public int getDiffFromPrevDay() {
      return diffFromPrevDay;
  }

  public void setDiffFromPrevDay(int diffFromPrevDay) {
      this.diffFromPrevDay = diffFromPrevDay;
  }

  public String getState() {
      return state;
  }

  public void setState(String state) {
      this.state = state;
  }

  public String getCountry() {
      return country;
  }

  public void setCountry(String country) {
      this.country = country;
  }

  public int getLatestTotalCases() {
      return latestTotalCases;
  }

  public void setLatestTotalCases(int latestTotalCases) {
      this.latestTotalCases = latestTotalCases;
  }

  //toString()メソッドの戻り値に表示したい文字列を返すように処理を記述します。https://www.kenschool.jp/blog/?p=4020
  @Override
  public String toString() {
      return "LocationStats{" +
              "state='" + state + '\'' +
              ", country='" + country + '\'' +
              ", latestTotalCases=" + latestTotalCases +
              '}';
  }
}