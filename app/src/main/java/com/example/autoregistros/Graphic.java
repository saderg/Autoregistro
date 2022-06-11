package com.example.autoregistros;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Graphic extends AppCompatActivity {

    String emotion_date;
    int id_usuario;
    Date start_date, end_date;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        getIntent();
        emotion_date = getIntent().getStringExtra("date");
        System.out.println("EMOTIONDATE      " + emotion_date);
        id_usuario = getIntent().getIntExtra("id_usuario", 0);
        System.out.println("ID USUARIO     " + id_usuario);

        try {
            start_date = new java.sql.Date(format.parse(emotion_date + "T00:00:00").getTime());
            end_date = new java.sql.Date(format.parse(emotion_date + "T23:59:59").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //init compoments graphic_day
        BarChart barChart = (BarChart) findViewById(R.id.barChart);

        //FRAGMENT GRAPHIC
        ArrayList<BarEntry> emociones = new ArrayList<>();
        emociones.add(new BarEntry(0, 3));
        emociones.add(new BarEntry(1, 1));
        emociones.add(new BarEntry(2, 2));
        emociones.add(new BarEntry(3, 1));
        emociones.add(new BarEntry(4, 2));

        BarDataSet barDataSet = new BarDataSet(emociones, "(Miedo, Alegría, Enfado, Tristeza, Asco");
        barDataSet.setValueTextSize(100f);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(18f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);
        barChart.animateX(1000);

    }

}