package pl.vistula.mkedron.zlosliwybudzik.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mkked on 06.02.2018.
 */

public class Configuration {
    
    private boolean repeatable = false;
    private Integer hour;
    private Integer minutes;
    private Map<DaysEnum, Boolean> daysEnabled;
    private Boolean alarmEnabled;
    private String alarmId;

    public Configuration() {
        daysEnabled = new HashMap<>();
        for(DaysEnum day : DaysEnum.values()) {
            daysEnabled.put(day,new Boolean(false));
        }
        alarmEnabled = new Boolean(true);
    }

    public Boolean getAlarmEnabled() {
        return alarmEnabled;
    }

    public void setAlarmEnabled(Boolean alarmEnabled) {
        this.alarmEnabled = alarmEnabled;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Map<DaysEnum,Boolean> getDaysEnabledMap() {
        return daysEnabled;
    }

    public Boolean getDayEnabled(DaysEnum day) {
        return daysEnabled.get(day);
    }

    public void setDayEnabled(DaysEnum day, Boolean bool) {
        daysEnabled.put(day, bool);
    }

    public void enableDay(DaysEnum day) {
        daysEnabled.put(day, new Boolean(true));
    }

    public void disableDay(DaysEnum day) {
        daysEnabled.put(day, new Boolean(false));
    }
}
