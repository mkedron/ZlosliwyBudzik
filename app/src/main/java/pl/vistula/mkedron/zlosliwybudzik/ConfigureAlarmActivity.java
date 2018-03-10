package pl.vistula.mkedron.zlosliwybudzik;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.Map;

import pl.vistula.mkedron.zlosliwybudzik.model.AlarmService;
import pl.vistula.mkedron.zlosliwybudzik.model.DaysEnum;

import static pl.vistula.mkedron.zlosliwybudzik.model.ConstsValues.REPEATABLE;

public class ConfigureAlarmActivity extends AppCompatActivity {

    private boolean singleAlarmMode = false;
    private Map<DaysEnum, CheckBox> daysCheckBoxMapping;
    private AlarmService alarmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmService = AlarmService.getInstance();
        setContentView(R.layout.activity_configure_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpToogleListener();
        initializeDaysMapping();

    }

    private void initializeDaysMapping() {
        daysCheckBoxMapping = new HashMap<>();
        daysCheckBoxMapping.put(DaysEnum.MONDAY,getCheckBoxById(R.id.mondayCheckBox) );
        daysCheckBoxMapping.put(DaysEnum.TUESDAY,getCheckBoxById(R.id.tuesdayCheckBox) );
        daysCheckBoxMapping.put(DaysEnum.WEDNESDAY,getCheckBoxById(R.id.wednesdayCheckBox) );
        daysCheckBoxMapping.put(DaysEnum.THURSDAY,getCheckBoxById(R.id.thursdayCheckBox) );
        daysCheckBoxMapping.put(DaysEnum.FRIDAY,getCheckBoxById(R.id.fridayCheckBox) );
        daysCheckBoxMapping.put(DaysEnum.SATURDAY,getCheckBoxById(R.id.saturdayCheckBox) );
        daysCheckBoxMapping.put(DaysEnum.SUNDAY,getCheckBoxById(R.id.sundayCheckBox) );
    }

    private void setUpToogleListener() {
        ToggleButton singleToggleButton = getToggleButtonById(R.id.singleToogle);
        singleToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleButton toggle = (ToggleButton) v;
                    if(toggle.isChecked()) {
                        setSingleAlarmMode(false);
                    } else {
                        setSingleAlarmMode(true);
                    }
                }
            }
        );
    }

    public void goToTimeSetup(View view) {
        Intent intent = new Intent(this, SetupTimeActivity.class);
        Bundle bundle = new Bundle();

        ToggleButton singleToggleButton = getToggleButtonById(R.id.singleToogle);
        if(!singleToggleButton.isChecked()) {
            bundle.putBoolean(REPEATABLE, true);
            System.out.println("repeatable -> true");
            for(DaysEnum day : DaysEnum.values()) {
                bundle.putBoolean(day.toString(), daysCheckBoxMapping.get(day).isChecked());
            }
        } else {
            bundle.putBoolean(REPEATABLE, false);
            System.out.println("repeatable -> false");
        }

        intent.putExtras(bundle);

        startActivity(intent);
    }

    private ToggleButton getToggleButtonById(int id) {
        return (ToggleButton)findViewById(id);
    }

    private CheckBox getCheckBoxById(int id) {
        CheckBox checkBox = (CheckBox)findViewById(id);
        return checkBox;
    }

    private void setSingleAlarmMode(boolean enabled) {
        singleAlarmMode = enabled;
        ViewGroup viewGroup =  (ViewGroup)findViewById(R.id.daysLayout);
        for(int i = 0 ; i< viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if(!enabled) {
                view.setEnabled(false);
            } else {
                view.setEnabled(true);
            }
        }
    }

}
