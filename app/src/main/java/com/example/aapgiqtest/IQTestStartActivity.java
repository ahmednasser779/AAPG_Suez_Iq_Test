package com.example.aapgiqtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IQTestStartActivity extends AppCompatActivity {

    Button mStartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iqtest_start);

        mStartBtn = findViewById(R.id.start_btn);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(IQTestStartActivity.this, TestActivity.class);
                startActivity(startIntent);
            }
        });
    }
}
