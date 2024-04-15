package com.example.socius.models;

public class ModelGroups {
    String DailyHour,DailyMinute,Name,TotalTime;

    public ModelGroups() {
    }

    public ModelGroups(String dailyHour, String dailyMinute, String name, String totalTime) {
        this.DailyHour = dailyHour;
        this.DailyMinute = dailyMinute;
        this.Name = name;
        this.TotalTime = totalTime;
    }

    public String getDailyHour() {
        return DailyHour;
    }

    public void setDailyHour(String dailyHour) {
        DailyHour = dailyHour;
    }

    public String getDailyMinute() {
        return DailyMinute;
    }

    public void setDailyMinute(String dailyMinute) {
        DailyMinute = dailyMinute;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTotalTime() {
        return TotalTime;
    }

    public void setTotalTime(String totalTime) {
        TotalTime = totalTime;
    }
}
