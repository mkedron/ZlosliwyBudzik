package pl.vistula.mkedron.zlosliwybudzik.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pl.vistula.mkedron.zlosliwybudzik.DisableAlarmActivity;

/**
 * Created by mkked on 06.02.2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Alarm !!");
        Intent i = new Intent(context, DisableAlarmActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
