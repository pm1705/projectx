package com.example.yakirhelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class temporary_screen extends AppCompatActivity {

    Intent login_intent, register_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporary_screen);

        login_intent = new Intent(this, com.example.yakirhelp.login_activity.class);
        //  register_intent = new Intent(this, com.example.yakirhelp.register_activity.class);
    }

    public void login_submit(View view) {
        startActivity(login_intent);
    }

    public void register_submit(View view) {
        startActivity(register_intent);
    }
}