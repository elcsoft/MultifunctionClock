package com.elclcd.multifunctionclock;

/**
 * Created by 123 on 2016/3/10.
 */
public class AlarmsConfig {

    public boolean enabled;
    public TimePoint powerOffTime;
    public TimePoint powerOnTime;
    public boolean[] dayWeek;

    public class TimePoint {
        int hour;
        int minute;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnablen(boolean enabled) {
        this.enabled = enabled;
    }

    public TimePoint getPowerOffTime() {
        return powerOffTime;
    }

    public void setPowerOffTime(TimePoint powerOffTime) {
        this.powerOffTime = powerOffTime;
    }

    public TimePoint getPowerOnTime() {
        return powerOnTime;
    }

    public void setPowerOnTime(TimePoint powerOnTime) {
        this.powerOnTime = powerOnTime;
    }

    public boolean[] getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(boolean[] dayWeek) {
        this.dayWeek = dayWeek;
    }
}

