package com.example.autoregistros;

import static com.example.autoregistros.conectaAPI.Urls.URL_GET_EMOTION_FECHA_TIPO;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.autoregistros.entidades.Emotion;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Graphic extends AppCompatActivity {

    String emotion_date;
    int id_user;
    int miedo,alegria,enfado,tristeza,asco;
    int countEmotion;
    String user_name, password, email_address, date_of_birth;
    Date start_date, end_date;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    ImageButton mostrarGraphic;
    EditText dateGraphic;

    public ImageButton graphicButton, dayButton, listButton, resourcesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        getIntent();
        id_user = getIntent().getExtras().getInt("id_user");
        user_name = getIntent().getStringExtra("user_name");
        password = getIntent().getStringExtra("password");
        email_address = getIntent().getStringExtra("email_address");
        date_of_birth = getIntent().getStringExtra("date_of_birth");

        System.out.println("ID USUARIO     " + id_user);

        dayButton = findViewById(R.id.buttonDia);
        graphicButton = findViewById(R.id.buttonGraphic);
        resourcesButton = findViewById(R.id.buttonResources);
        listButton = findViewById(R.id.buttonEmotions);
        mostrarGraphic = findViewById(R.id.buttonMostrarGraphic);
        dateGraphic = findViewById(R.id.editDateGraphic);
        //init compoments graphic_day
        BarChart barChart = (BarChart) findViewById(R.id.barChart);

        //MENU BOTONES
        dayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graphic.this, Dia.class);
                intent.putExtra("id_user", id_user);
                intent.putExtra("user_name" , user_name);
                intent.putExtra("password", password);
                intent.putExtra("email_address" , email_address);
                intent.putExtra("date_of_birth" , date_of_birth);
                startActivity(intent);
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graphic.this, ListEmotions.class);
                intent.putExtra("id_user", id_user);
                intent.putExtra("user_name" , user_name);
                intent.putExtra("password", password);
                intent.putExtra("email_address" , email_address);
                intent.putExtra("date_of_birth" , date_of_birth);
                startActivity(intent);
            }
        });

        resourcesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graphic.this, Resources.class);
                intent.putExtra("id_user", id_user);
                intent.putExtra("user_name" , user_name);
                intent.putExtra("password", password);
                intent.putExtra("email_address" , email_address);
                intent.putExtra("date_of_birth" , date_of_birth);
                startActivity(intent);
            }
        });

        mostrarGraphic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    start_date = new java.sql.Date(format.parse(dateGraphic.getText().toString() + "T00:00:00").getTime());
                    end_date = new java.sql.Date(format.parse(dateGraphic.getText().toString() + "T23:59:59").getTime());

                    Log.i("fecha" , start_date + " " + end_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                getByTypeAndRange(id_user, "Miedo" , start_date, end_date);
                getByTypeAndRange(id_user, "Alegria", start_date, end_date);
                getByTypeAndRange(id_user, "Enfado", start_date, end_date);
                getByTypeAndRange(id_user, "Tristeza", start_date, end_date);
                getByTypeAndRange(id_user, "Asco", start_date, end_date);


                ArrayList<BarEntry> emociones = new ArrayList<>();

                emociones.add(new BarEntry(0, miedo));
                emociones.add(new BarEntry(1, alegria));
                emociones.add(new BarEntry(2, enfado));
                emociones.add(new BarEntry(3, tristeza));
                emociones.add(new BarEntry(4, asco));

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
        });


    }
    public void getByTypeAndRange(int id_usuario, String emotion_type, Date start_date, Date end_date){
        Log.i("START DATE metodo ", start_date.toString());

        ArrayList<Emotion> arrayListEmociones = new ArrayList<>();

        final ProgressDialog loading = new ProgressDialog(Graphic.this);
        loading.setMessage("Please Wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        String url = "http://192.168.1.31:8086/app/emotions/emotion/type_and_range?user_id=" + id_usuario + "&emotion_type=" + emotion_type + "&start_date=" + start_date.toString() + "T00:00:00&end_date=" + end_date.toString() + "T23:59:59";
        System.out.println(url);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        StringRequest peticionApi = new StringRequest(Request.Method.GET , url,
                (response -> {
                    Log.i("TAG", "TODO BIEN");

                    try {
                        JSONArray arrayEmociones = new JSONArray(response);

                        for (int i = 0; i < arrayEmociones.length(); i++) {

                            loading.dismiss();

                            Emotion emotion = new Emotion();

                            emotion.setId_emocion(arrayEmociones.getJSONObject(i).getInt("id_emocion"));
                            emotion.setId_usuario(arrayEmociones.getJSONObject(i).getInt("id_usuario"));
                            emotion.setEmotion_type(arrayEmociones.getJSONObject(i).getString("emotion_type"));
                            emotion.setEmotion_reason(arrayEmociones.getJSONObject(i).getString("emotion_reason"));
                            emotion.setEmotion_date(format.parse(arrayEmociones.getJSONObject(i).getString("emotion_date")));
                            System.out.println(emotion.toString());
                            arrayListEmociones.add(emotion);
                        }

                        arrayEmociones.length();
                        System.out.println("METODO " + emotion_type + " " + countEmotion);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }),
                (error ->{
                    Log.e("error", "Error al pedir los datos." + error.getMessage());
                    loading.dismiss();
                    countEmotion = 0;

                    System.out.println("METODO.LENGTH" + miedo);
                }));
        Volley.newRequestQueue(this).add(peticionApi);
    }

}