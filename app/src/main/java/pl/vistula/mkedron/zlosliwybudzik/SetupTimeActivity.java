package pl.vistula.mkedron.zlosliwybudzik;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import pl.vistula.mkedron.zlosliwybudzik.model.AlarmService;
import pl.vistula.mkedron.zlosliwybudzik.model.ConstsValues;
import pl.vistula.mkedron.zlosliwybudzik.model.DaysEnum;


public class SetupTimeActivity extends AppCompatActivity {

    private Bundle bundle;
    private AlarmService alarmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmService = AlarmService.getInstance();
        setContentView(R.layout.activity_setup_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.bundle = getIntent().getExtras();

    }


    public void addAlarm(View view) {

        alarmService.cancelAllAlarms(this);

        if(bundle.getBoolean(ConstsValues.REPEATABLE)) {
            for(DaysEnum daysEnum : DaysEnum.values()) {
                if(bundle.getBoolean(daysEnum.toString())) {
                    alarmService.setUpAlarm(this, daysEnum, bundle);
                }
            }
        } else {
            alarmService.setUpAlarm(this,bundle);
        }

        Intent intent1 = new Intent(this, MenuActivity.class);
        startActivity(intent1);


    }

}
