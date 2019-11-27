package com.fly.myuiassemble;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;

import com.fly.viewlibrary.progress.ArcProgressBar;
import com.fly.viewlibrary.progress.CirclePercentBar;
import com.fly.viewlibrary.progress.DecimalScaleRulerProgressBar;
import com.fly.viewlibrary.progress.SaleProgressBar;
import com.fly.viewlibrary.progress.UpdateProgressBar;

public class ProgressActivity extends AppCompatActivity {

    public float number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        UpdateProgressBar updateProgressBar = findViewById(R.id.pbNum);
        SaleProgressBar saleProgressBar = findViewById(R.id.spv);
        ArcProgressBar arcProgress = findViewById(R.id.arcProgress);
        CirclePercentBar circlePercentBar = findViewById(R.id.circlePercentBar);

        SeekBar seekBar = findViewById(R.id.seek);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateProgressBar.setProgress(i);
                saleProgressBar.setTotalAndCurrentCount(100, i);
                arcProgress.setProgress(i);
                circlePercentBar.setPercentage(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        DecimalScaleRulerProgressBar mRulerWeight = findViewById(R.id.rulerHeight);
        mRulerWeight.setValueChangeListener(value -> {
            number = value;
            Log.e("FLY110", "onCreate: " + number);
        });
    }
}
