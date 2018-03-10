package pl.vistula.mkedron.zlosliwybudzik;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import pl.vistula.mkedron.zlosliwybudzik.model.AlarmService;

public class MenuActivity extends AppCompatActivity {

    private AlarmService alarmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmService = AlarmService.getInstance();
        setContentView(R.layout.activity_menu);
    }

    public void addAlarm(View view) {
        Intent intent = new Intent(this, ConfigureAlarmActivity.class);
        startActivity(intent);
    }

    public void cancelAlarms(View view) {
        System.out.println("Alarm Cancel Action");
        alarmService.cancelAllAlarms(this);
        Toast.makeText(this, "Wszystkie alarmy zostały wyłączone", Toast.LENGTH_LONG).show();

    }

    public void showAlarms(View view) {
        Toast.makeText(this, alarmService.getAlarmString(this), Toast.LENGTH_LONG).show();

    }

}
