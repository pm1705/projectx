package com.example.yakirhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import static com.example.yakirhelp.FBRefs.refUsers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class login_activity extends AppCompatActivity {

    EditText email, pass;
    String tempMail, tempPass, currentMail, currentPass;
    TextView errors;
    ArrayList usersEmails,usersPasswords;
    boolean email_exist;
    int userId;
    Intent main_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        main_screen = new Intent(this, main_screen_activity.class);

        email = (EditText)findViewById(R.id.emailInput);
        pass = (EditText)findViewById(R.id.passInput);
        errors = (TextView)findViewById(R.id.error);

        usersEmails = new ArrayList();
        usersPasswords = new ArrayList();

        refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                usersEmails.clear();
                usersPasswords.clear();
                for(DataSnapshot data : dS.getChildren()) {
                    currentMail = "" + data.child("email").getValue();
                    currentPass = "" + data.child("pass").getValue();
                    usersEmails.add(currentMail);
                    usersPasswords.add(currentPass);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    public void submitLogin(View view) {
        tempMail = email.getText().toString();
        tempPass = pass.getText().toString();
        email_exist = false;

        for (int i = 0; i < usersEmails.size(); i++){
            if (tempMail.equals(usersEmails.get(i))){
                email_exist = true;
                userId = i;
            }
        }
        if (email_exist){
            if (tempPass.equals(usersPasswords.get(userId))){
                //next screen
                errors.setText("succesful login!");
                startActivity(main_screen);
            }
            else{
                errors.setText("Password isn't correct.");
            }
        }
        else {
            errors.setText("there is no user associated with this email address.");
        }

    }


}