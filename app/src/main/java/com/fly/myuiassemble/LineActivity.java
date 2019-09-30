package com.fly.myuiassemble;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fly.viewlibrary.statistics.line.LineCartView;
import com.fly.viewlibrary.statistics.line.StatisticsLineChartView;

import java.util.Arrays;
import java.util.List;

public class LineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);

        LineCartView lineCartView = findViewById(R.id.lineCart);
        String[] xArr = {"0", "10", "20", "30", "40", "50", "60"};
        List<String> xValues = Arrays.asList(xArr);

        String[] xLong = {"50", "55", "40", "30", "35", "45", "48"};
        List<String> longs = Arrays.asList(xLong);

        Long[] xxLong = {1563208000l, 1564460800l, 1564633600l, 1564979200l, 1565411200l, 1565851164l, 1565886400l};
        List<Long> xlongs = Arrays.asList(xxLong);

        lineCartView.setData(xValues, longs, xlongs);

        StatisticsLineChartView statisticsLineChartView = findViewById(R.id.statisticsLine);

        Integer[] xInt = {50, 100, 200, 150, 100, 50, 180, 140, 130, 80, 90, 120, 160, 100, 170, 200, 180, 170};
        List<Integer> points = Arrays.asList(xInt);
        statisticsLineChartView.setLines(points);
        statisticsLineChartView.setNums(xValues);
    }
}
