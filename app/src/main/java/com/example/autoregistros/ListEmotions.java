package com.example.autoregistros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.autoregistros.adapter.EmotionAdapter;
import com.example.autoregistros.entidades.Emotion;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListEmotions extends AppCompatActivity {

    public ImageButton graphicButton, dayButton, listButton, resourcesButton;
    RecyclerView listaEmotions;
    EmotionAdapter emotionAdapter;

    int id_user, id_emotion;
    String user_name, password, email_address, date_of_birth;
    String emotion_type, emotion_reason, emotion_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_emotions);

        getByUser(id_user);

        getIntent();
        id_user = getIntent().getExtras().getInt("id_user");
        id_emotion = getIntent().getExtras().getInt("id_emotion");
        emotion_type = getIntent().getStringExtra("emotion_type");
        emotion_reason = getIntent().getStringExtra("emotion_reason");


        listaEmotions.setAdapter(emotionAdapter);

        graphicButton = findViewById(R.id.buttonGraphic);
        dayButton = findViewById(R.id.buttonDia);
        graphicButton = findViewById(R.id.buttonGraphic);
        resourcesButton = findViewById(R.id.buttonResources);
        listaEmotions = findViewById(R.id.listEmotions);

        //MENU BOTONES
        graphicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListEmotions.this, Graphic.class);
                intent.putExtra("id_user", id_user);
                intent.putExtra("user_name" , user_name);
                intent.putExtra("password", password);
                intent.putExtra("email_address" , email_address);
                intent.putExtra("date_of_birth" , date_of_birth);
                startActivity(intent);
            }
        });

        dayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListEmotions.this, Dia.class);
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
                Intent intent = new Intent(ListEmotions.this, Resources.class);
                intent.putExtra("id_user", id_user);
                intent.putExtra("user_name" , user_name);
                intent.putExtra("password", password);
                intent.putExtra("email_address" , email_address);
                intent.putExtra("date_of_birth" , date_of_birth);
                startActivity(intent);
            }
        });

        //RECYCLERVIEW


    }

    public void getByUser(int id_usuario){

        ArrayList<Emotion> arrayListEmociones = new ArrayList<>();

        final ProgressDialog loading = new ProgressDialog(ListEmotions.this);
        loading.setMessage("Please Wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        String url = "http://192.168.1.31:8086/app/emotions/emotion/range?user_id=" + id_usuario + "&start_date=" + "1000-01-01T00:00:00&end_date=" + "2030-06-12T23:59:59";
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

                            if (arrayEmociones != null) {
                                Intent intent = new Intent(ListEmotions.this, ListEmotions.class);
                                intent.putExtra("id_usuario", emotion.getId_usuario());
                                intent.putExtra("id_emotion", emotion.getId_emocion());
                                intent.putExtra("emotion_type" , emotion.getEmotion_type());
                                intent.putExtra("emotion_reason", emotion.getEmotion_reason());
                                intent.putExtra("emotion_date" , emotion.getEmotion_date());
                                startActivity(intent);
                            }
                        }
                        emotionAdapter = new EmotionAdapter(arrayListEmociones, this);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }),
                (error ->{
                    Log.e("error", "Error al pedir los datos." + error.getMessage());
                    loading.dismiss();

                }));
        Volley.newRequestQueue(this).add(peticionApi);
    }


}