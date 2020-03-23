package com.wy.battery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.wy.view.BatteryView;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private BatteryView battery2View, verticalBattery, battery3View, battery4View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        verticalBattery = (BatteryView) findViewById(R.id.verticalBattery);
        battery2View = (BatteryView) findViewById(R.id.Battery2View);
        battery3View = (BatteryView) findViewById(R.id.Battery3View);
        battery4View = (BatteryView) findViewById(R.id.Battery4View);
        battery2View.setPower(1, false);
        verticalBattery.setPower(100, false);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    progress = 1;
                }
                Log.i("MainActivity", "progress : " + progress);
                battery2View.setPower(progress, true);
                verticalBattery.setPower(progress, true);
                battery3View.setPower(progress, true);
                battery4View.setPower(progress, true);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
