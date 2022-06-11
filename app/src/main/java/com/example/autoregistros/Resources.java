package com.example.autoregistros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class Resources extends AppCompatActivity {

    TextView info, t, t1, t2, t3, t4, t5, t6, t7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info = findViewById(R.id.infoTxt);
        t = findViewById(R.id.link);
        t.setMovementMethod(LinkMovementMethod.getInstance());
        t1 = findViewById(R.id.link1);
        t1.setMovementMethod(LinkMovementMethod.getInstance());

        t2 = findViewById(R.id.link2);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        t3 = findViewById(R.id.link3);
        t3.setMovementMethod(LinkMovementMethod.getInstance());

        t4 = findViewById(R.id.link4);
        t4.setMovementMethod(LinkMovementMethod.getInstance());

        t5 = findViewById(R.id.link5);
        t5.setMovementMethod(LinkMovementMethod.getInstance());

        t6 = findViewById(R.id.link6);
        t6.setMovementMethod(LinkMovementMethod.getInstance());

        t7 = findViewById(R.id.link7);
        t7.setMovementMethod(LinkMovementMethod.getInstance());

    }
}