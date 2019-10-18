package com.fly.myuiassemble;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fly.viewlibrary.statistics.line.LineCartView;
import com.fly.viewlibrary.statistics.line.StatisticsLineChartView;
import com.fly.viewlibrary.statistics.line.index.IndexLineCartView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class LineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);

        LineCartView lineCartView = findViewById(R.id.lineCart);
        String[] xArr = {"30", "40", "50", "60"};
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

        IndexLineCartView indexLineCartView = findViewById(R.id.indexLineCartView);

        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(0);
        hashSet.add(40);
        hashSet.add(60);
        hashSet.add(80);

        indexLineCartView.setLeftData(hashSet);

        List<IndexLineEntity> indexLineEntities = new ArrayList<>();

        IndexLineEntity entity1 = new IndexLineEntity();
        long time1 = System.currentTimeMillis();
        entity1.setTime(time1);
        List<Double> list1 = new ArrayList<>();
        list1.add(20d);
        list1.add(80d);
        entity1.setList(list1);
        indexLineEntities.add(entity1);

        IndexLineEntity entity2 = new IndexLineEntity();
        long time2 = System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000;
        entity2.setTime(time2);
        List<Double> list2 = new ArrayList<>();
        list2.add(40d);
        list2.add(50d);
        entity2.setList(list2);
        indexLineEntities.add(entity2);

        IndexLineEntity entity3 = new IndexLineEntity();
        long time3 = System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000;
        entity3.setTime(time3);
        List<Double> list3 = new ArrayList<>();
        list3.add(50d);
        list3.add(60d);
        entity3.setList(list3);
        indexLineEntities.add(entity3);

        IndexLineEntity entity4 = new IndexLineEntity();
        long time4 = System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000;
        entity4.setTime(time4);
        List<Double> list4 = new ArrayList<>();
        list4.add(60d);
        list4.add(80d);
        entity4.setList(list4);
        indexLineEntities.add(entity4);

        IndexLineEntity entity5 = new IndexLineEntity();
        long time5 = System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000;
        entity5.setTime(time5);
        List<Double> list5 = new ArrayList<>();
        list5.add(50d);
        list5.add(70d);
        entity5.setList(list5);
        indexLineEntities.add(entity5);

        IndexLineEntity entity6 = new IndexLineEntity();
        long time6 = System.currentTimeMillis() - 4 * 24 * 60 * 60 * 1000;
        entity6.setTime(time6);
        List<Double> list6 = new ArrayList<>();
        list6.add(50d);
        list6.add(80d);
        entity6.setList(list6);
        indexLineEntities.add(entity6);

        indexLineCartView.setData(indexLineEntities);
    }
}
