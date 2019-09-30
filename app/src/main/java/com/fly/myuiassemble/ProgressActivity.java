package com.fly.myuiassemble;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.fly.viewlibrary.progress.ArcProgressBar;
import com.fly.viewlibrary.progress.SaleProgressBar;
import com.fly.viewlibrary.progress.UpdateProgressBar;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        UpdateProgressBar updateProgressBar = findViewById(R.id.pbNum);
        SaleProgressBar saleProgressBar = findViewById(R.id.spv);
        ArcProgressBar arcProgress = findViewById(R.id.arcProgress);

        SeekBar seekBar = findViewById(R.id.seek);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateProgressBar.setProgress(i);
                saleProgressBar.setTotalAndCurrentCount(100, i);
                arcProgress.setProgress(i);
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
