package com.example.yakirhelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class main_screen_activity extends AppCompatActivity {

    Intent peronal_page_intent, new_recipe_intent, my_recipes_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_activity);

        //peronal_page_intent = new Intent(this, personal_page.class);
        //new_recipe_intent = new Intent(this, new_recipe.class);
        //my_recipes_intent = new Intent(this, my_recipes.class);
    }

    public void personal_page(View view) {
        //startActivity(peronal_page_intent);
    }

    public void new_recipe(View view) {
        //startActivity(new_recipe_intent);
    }

    public void my_recipes(View view) {
        //startActivity(my_recipes_intent);
    }
}