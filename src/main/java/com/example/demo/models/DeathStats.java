package com.example.demo.models;

public class DeathStats {
  private String state;
  private String country;
  private int latestTotalDeathCases;
  private int diffDeathFromPrevDay;

  public int getDiffDeathFromPrevDay() {
      return diffDeathFromPrevDay;
  }

  public void setDiffDeathFromPrevDay(int diffFromPrevDay) {
      this.diffDeathFromPrevDay = diffFromPrevDay;
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

  public int getLatestTotalDeathCases() {
      return latestTotalDeathCases;
  }

  public void setLatestTotalDeathCases(int latestTotalDeathCases) {
      this.latestTotalDeathCases = latestTotalDeathCases;
  }

  //toString()メソッドの戻り値に表示したい文字列を返すように処理を記述します。https://www.kenschool.jp/blog/?p=4020
  @Override
  public String toString() {
      return "deathStats{" +
              "state='" + state + '\'' +
              ", country='" + country + '\'' +
              ", latestTotalDeathCases=" + latestTotalDeathCases +
              '}';
  }
}