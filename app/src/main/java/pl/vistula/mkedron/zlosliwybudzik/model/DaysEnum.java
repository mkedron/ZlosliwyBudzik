package pl.vistula.mkedron.zlosliwybudzik.model;

/**
 * Created by mkked on 06.02.2018.
 */

public enum DaysEnum {
    MONDAY(2,"Poniedziałek"),
    TUESDAY(3,"Wtorek"),
    WEDNESDAY(4,"Środa"),
    THURSDAY(5,"Czwartek"),
    FRIDAY(6,"Piątek"),
    SATURDAY(7,"Sobota"),
    SUNDAY(1,"Niedziela");

    int dayIntValue;
    String polishDayName;

    private DaysEnum(int dayValue, String dayName) {
        dayIntValue = dayValue;
        polishDayName = dayName;
    }

    public int getDayIntValue() {
        return dayIntValue;
    }

    @Override
    public String toString() {
        return polishDayName;
    }
}
