package com.example.yakirhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import static com.example.yakirhelp.FBRefs.refUsers;

public class main_screen_activity extends AppCompatActivity {

    Intent peronal_page_intent, new_recipe_intent, my_recipes_intent, back_to_temp;

    int logged_user_id; // logged_user_id
    String log; //logged_user_id_to_str

    TextView info_display;
    public static User current_user;



    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_activity);

        SharedPreferences logged_information = getSharedPreferences("INFO",MODE_PRIVATE);
        editor = logged_information.edit();

        logged_user_id = logged_information.getInt("key_id",0);
        log = Integer.toString(logged_user_id);

        info_display = (TextView) findViewById(R.id.userinfo);

        refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                for(DataSnapshot data : dS.getChildren()) {
                    if (data.getKey().equals(log)){

                        System.out.println(log);

                        current_user = new User(
                                data.child("username").getValue().toString(),
                                data.child("email").getValue().toString(),
                                data.child("pass").getValue().toString(),
                                data.child("location").getValue().toString(),
                                Integer.valueOf(data.child("gender").getValue().toString()),
                                Integer.valueOf(data.child("weight").getValue().toString()),
                                Integer.valueOf(data.child("height").getValue().toString()),
                                Integer.valueOf(data.child("age").getValue().toString()),
                                Integer.valueOf(data.child("activityLevel").getValue().toString()),
                                logged_user_id);
                        info_display.setText("Logged in as: " + current_user.getName());
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        peronal_page_intent = new Intent(this, personal_page.class);
        //new_recipe_intent = new Intent(this, new_recipe.class);
        //my_recipes_intent = new Intent(this, my_recipes.class);
        back_to_temp = new Intent(this, temp_screen.class);
    }

    public void personal_page(View view) {
        startActivity(peronal_page_intent);
    }

    public void new_recipe(View view) {
        //startActivity(new_recipe_intent);
    }

    public void my_recipes(View view) {
        //startActivity(my_recipes_intent);
    }

    public void log_out(View view) {
        editor.putBoolean("logged_in",false);
        editor.putInt("key_id",-1);
        editor.commit();
        startActivity(back_to_temp);
    }
}