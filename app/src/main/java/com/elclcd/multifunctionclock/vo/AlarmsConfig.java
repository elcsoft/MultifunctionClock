package com.elclcd.multifunctionclock.vo;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by 123 on 2016/3/10.
 */
public class AlarmsConfig implements Serializable{

    public boolean enabled;
    public TimePoint powerOffTime;
    public TimePoint powerOnTime;
    public boolean[] dayWeek;

    public static class TimePoint {
        int hour;
        int minute;

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getMinute() {
            return minute;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        @Override
        public String toString() {
            return "TimePoint{" +
                    "hour=" + hour +
                    ", minute=" + minute +
                    '}';
        }
    }

    public boolean isEnabled() {
        return enabled;
    }//开关是否能用，是否可以调用命令

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

    @Override
    public String toString() {
        return "AlarmsConfig{" +
                "enabled=" + enabled +
                ", powerOffTime=" + powerOffTime +
                ", powerOnTime=" + powerOnTime +
                ", dayWeek=" + Arrays.toString(dayWeek) +
                '}';
    }
}

