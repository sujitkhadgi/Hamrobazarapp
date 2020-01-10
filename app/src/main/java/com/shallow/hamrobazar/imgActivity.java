package com.shallow.hamrobazar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class imgActivity extends AppCompatActivity {
int img1= R.drawable.ab;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        textView = findViewById(R.id.img1);
        textView.setText(img1+"");
    }
}
