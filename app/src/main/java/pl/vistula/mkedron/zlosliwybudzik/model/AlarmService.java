package pl.vistula.mkedron.zlosliwybudzik.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import pl.vistula.mkedron.zlosliwybudzik.R;
import pl.vistula.mkedron.zlosliwybudzik.alarm.AlarmReceiver;

import static pl.vistula.mkedron.zlosliwybudzik.model.ConstsValues.ALARMS_SET_KEY;
import static pl.vistula.mkedron.zlosliwybudzik.model.ConstsValues.DAYS_ENABLED_SET_KEY;
import static pl.vistula.mkedron.zlosliwybudzik.model.ConstsValues.DISABLE_KEY;
import static pl.vistula.mkedron.zlosliwybudzik.model.ConstsValues.HOUR_KEY;
import static pl.vistula.mkedron.zlosliwybudzik.model.ConstsValues.MINUTE_KEY;
import static pl.vistula.mkedron.zlosliwybudzik.model.ConstsValues.REPEATABLE;
import static pl.vistula.mkedron.zlosliwybudzik.model.ConstsValues.REPEATABLE_KEY;

/**
 * Created by mkked on 10.02.2018.
 */

public class AlarmService {

    private AlarmService(){

    }

    private static AlarmService instance;

    public static AlarmService getInstance() {
        if(instance == null) {
            instance = new AlarmService();
        }

        return instance;
    }

    private AppCompatActivity setUpActivity;

    public void setUpAlarm(AppCompatActivity activity, DaysEnum daysEnum, Bundle bundle) {



        setUpActivity = activity;

        TimePicker timePicker = (TimePicker) activity.findViewById(R.id.timePicker);

        AlarmManager alarmMgr;
        alarmMgr = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);


        Calendar calendar = getCalendarFromTimePicker(timePicker);
        boolean addWeek = false;
        if(calendar.get(Calendar.DAY_OF_WEEK) > daysEnum.getDayIntValue()) {
            addWeek = true;
        }
        calendar.set(Calendar.DAY_OF_WEEK, daysEnum.getDayIntValue());
        if(addWeek) {
            calendar.add(Calendar.DAY_OF_WEEK,7);
        }
        System.out.println("Alarm dodany : "+calendar.getTime().toString());


        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7, getIntent(activity) );

        setProperties(activity,timePicker,bundle);

    }

    public void setUpAlarm(AppCompatActivity activity, Bundle bundle) {
        cancelAllAlarms(activity);
        AlarmManager alarmMgr;
        TimePicker timePicker = (TimePicker) activity.findViewById(R.id.timePicker);

        alarmMgr = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = getCalendarFromTimePicker(timePicker);


        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), getIntent(activity) );

        setProperties(activity, timePicker, bundle);
    }

    private void setProperties(AppCompatActivity activity, TimePicker timePicker, Bundle bundle) {
        SharedPreferences sharedPref = activity.getApplicationContext().getSharedPreferences(ALARMS_SET_KEY, Context.MODE_PRIVATE);

        Boolean repeatable = bundle.getBoolean(REPEATABLE);

        sharedPref.edit().putInt(HOUR_KEY, timePicker.getHour()).apply();
        sharedPref.edit().putInt(MINUTE_KEY, timePicker.getMinute()).apply();
        sharedPref.edit().putBoolean(REPEATABLE_KEY, repeatable).apply();
        Set<String> daysWeekSet = new LinkedHashSet<>();
        if(repeatable) {
            for(DaysEnum daysEnum : DaysEnum.values()) {
                if(bundle.getBoolean(daysEnum.toString())) {
                    daysWeekSet.add(daysEnum.toString());
                }
            }
        }
        sharedPref.edit().putStringSet(DAYS_ENABLED_SET_KEY, daysWeekSet).apply();
        sharedPref.edit().putBoolean(DISABLE_KEY,false).apply();
    }

    private PendingIntent getIntent(AppCompatActivity activity) {

        PendingIntent alarmIntent;

        Intent intent = new Intent(activity.getApplicationContext(), AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(activity.getApplicationContext(), 0, intent, 0);

        return alarmIntent;
    }

    @NonNull
    private Calendar getCalendarFromTimePicker(TimePicker timePicker) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());
        return calendar;
    }

    public String getAlarmString(AppCompatActivity activity) {
        SharedPreferences sharedPref = activity.getApplicationContext().getSharedPreferences(ALARMS_SET_KEY, Context.MODE_PRIVATE);
        if(sharedPref.getBoolean(DISABLE_KEY,false)) {
            return "Nie ma aktywnych alarmów";
        }
        int hour = sharedPref.getInt(HOUR_KEY, 0);
        int minute = sharedPref.getInt(MINUTE_KEY, 0);
        boolean repeatablePref = sharedPref.getBoolean(REPEATABLE_KEY, false);
        Set<String> days = sharedPref.getStringSet(DAYS_ENABLED_SET_KEY, Collections.<String>emptySet());
        return String.format("%d:%d %n Dni aktywne : %s ", hour, minute, days.toString() );
    }

    public void cancelAllAlarms(AppCompatActivity activity) {
        AlarmManager alarmManager =  (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getIntent(setUpActivity == null ? activity : setUpActivity));
        clearProperties(activity);
    }

    public void clearProperties(AppCompatActivity activity) {
        SharedPreferences sharedPref = activity.getApplicationContext().getSharedPreferences(ALARMS_SET_KEY, Context.MODE_PRIVATE);

        sharedPref.edit().remove(HOUR_KEY).apply();
        sharedPref.edit().remove(MINUTE_KEY).apply();
        sharedPref.edit().remove(REPEATABLE_KEY).apply();
        sharedPref.edit().remove(DAYS_ENABLED_SET_KEY).apply();
        sharedPref.edit().putBoolean(DISABLE_KEY,true).apply();

    }
}
