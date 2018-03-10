package pl.vistula.mkedron.zlosliwybudzik;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.concurrent.ThreadLocalRandom;

import pl.vistula.mkedron.zlosliwybudzik.model.AlarmService;

public class DisableAlarmActivity extends AppCompatActivity {

    private MediaPlayer mp;

    private AlarmService alarmService;

    private String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmService = AlarmService.getInstance();
        setContentView(R.layout.activity_disable_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        askQuestion();

        mp = MediaPlayer.create(this, R.raw.alarm);
        mp.start();
    }

    public void disableAlarmListener(View view) {
        EditText numberAnswer = (EditText)findViewById(R.id.numberAnswer);
        if(numberAnswer.getText().toString().equals(answer)) {
            mp.stop();
            Toast.makeText(this,"Wspaniale ! \n Udało Ci się rozbroić alarm !", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
    }

    public void forceDisable(View view) {
        Toast.makeText(this,"Ha Ha Ha ! \n Nie da się ..", Toast.LENGTH_LONG).show();
    }

    private void askQuestion() {
        int randomNum1 = ThreadLocalRandom.current().nextInt(10, 40 + 1);
        int randomNum2 = ThreadLocalRandom.current().nextInt(10, 40 + 1);
        TextView questionTextView = (TextView) findViewById(R.id.question);
        questionTextView.setText(String.format("%d + %d = ?", randomNum1, randomNum2));
        answer = String.valueOf(randomNum1+randomNum2);
    }

}
