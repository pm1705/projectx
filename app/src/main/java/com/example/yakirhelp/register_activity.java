package com.example.yakirhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.yakirhelp.FBRefs.refUsers;

public class register_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText uname, email, pass, age, weight, height, location;
    Spinner gender, activity_level;
    String str_uname, str_email, str_pass, str_age, str_weight, str_height, str_gender, str_activity_level, str_location, currentMail;
    TextView errors;
    ArrayList usersEmails;
    int next_id;
    String[] sex_options = {"Male", "Female", "Other"};
    String[] activity_options = {"1", "2", "3", "4", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);

        uname = (EditText)findViewById(R.id.uname_register);
        email = (EditText)findViewById(R.id.email_register);
        pass = (EditText)findViewById(R.id.pass_register);
        age = (EditText)findViewById(R.id.age_register);
        weight = (EditText)findViewById(R.id.weight_register);
        height = (EditText)findViewById(R.id.height_register);
        gender = (Spinner)findViewById(R.id.gender_register);
        activity_level = (Spinner)findViewById(R.id.activity_level_register);
        location = (EditText)findViewById(R.id.location_register);
        errors = (TextView)findViewById(R.id.error);

        gender.setOnItemSelectedListener(this);
        str_gender = "none";
        ArrayAdapter<String> gender_adp = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,sex_options);
        gender.setAdapter(gender_adp);

        activity_level.setOnItemSelectedListener(this);
        str_activity_level = "none";
        ArrayAdapter<String> activity_adp = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,activity_options);
        activity_level.setAdapter(activity_adp);


        usersEmails = new ArrayList();

        refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                usersEmails.clear();
                for(DataSnapshot data : dS.getChildren()) {
                    currentMail = "" + data.child("email").getValue();
                    usersEmails.add(currentMail);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private boolean valid_uname(){
        str_uname = uname.getText().toString();
        if (str_uname.length() > 4) return true;
        return false;
    }

    private boolean valid_email(){
        str_email = email.getText().toString();
        if (str_email.contains("@")) return true;
        return false;
    }

    private boolean valid_pass(){
        str_pass = pass.getText().toString();
        if (str_pass.length() < 8) return false;
        return true;
    }

    private boolean valid_age(){
        str_age = age.getText().toString();
        if (Integer.parseInt(str_age) <= 0) return false;
        return true;
    }

    private boolean valid_weight(){
        str_weight = weight.getText().toString();
        if (Integer.parseInt(str_weight) <= 0) return false;
        return true;
    }

    private boolean valid_height(){
        str_height = height.getText().toString();
        if (Integer.parseInt(str_height) <= 0) return false;
        return true;
    }

    private boolean valid_location(){
        str_location = location.getText().toString();

        return true;
    }

    private boolean valid_gender(){
        if (str_gender == "none") return false;
        return true;
    }

    private boolean valid_activity_level(){
        if (str_activity_level == "none") return false;
        return true;
    }

    public void submitRegister(View view) {
        String error = "";
        if (!valid_uname()) error += "Username is too short.\n";
        if (!valid_email()) error += "Invalid email.\n";
        if (!valid_pass()) error += "Invalid password.\n";
        if (!valid_age()) error += "Invalid age.\n";
        if (!valid_weight()) error += "Invalid weight.\n";
        if (!valid_height()) error += "Invalid height.\n";
        if (!valid_location()) error += "Invalid location.\n";
        if (!valid_gender()) error += "Must choose gender.\n";
        if (!valid_activity_level()) error += "Must choose activity level.\n";
        errors.setText(error);
        if (error == ""){
            next_id = usersEmails.size();
            refUsers.child(Integer.toString(next_id)).setValue("");
            refUsers.child(Integer.toString(next_id)).child("username").setValue(str_uname);
            refUsers.child(Integer.toString(next_id)).child("email").setValue(str_email);
            refUsers.child(Integer.toString(next_id)).child("pass").setValue(str_pass);
            refUsers.child(Integer.toString(next_id)).child("age").setValue(str_age);
            refUsers.child(Integer.toString(next_id)).child("weight").setValue(str_weight);
            refUsers.child(Integer.toString(next_id)).child("height").setValue(str_height);
            refUsers.child(Integer.toString(next_id)).child("gender").setValue(str_gender);
            refUsers.child(Integer.toString(next_id)).child("activityLevel").setValue(str_activity_level);
            refUsers.child(Integer.toString(next_id)).child("location").setValue(str_location);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getResources().getResourceEntryName(adapterView.getId()).equals("gender_register")) {
            str_gender = sex_options[i];
        }
        if (adapterView.getResources().getResourceEntryName(adapterView.getId()).equals("activity_level_register")) {
            str_activity_level = activity_options[i];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}