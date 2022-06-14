package com.example.yakirhelp;

import static com.example.yakirhelp.FBRefs.refRecipes;
import static com.example.yakirhelp.FBRefs.refRestaurants;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RecipePage extends AppCompatActivity {

    TextView name, description, ingredients, instructions, topping;
    String str_name, str_cal, str_description, str_ingredients, str_instructions, str_topping;
    String key = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        name = (TextView)findViewById(R.id.name_recpg);
        description = (TextView)findViewById(R.id.description_recpg);
        ingredients = (TextView)findViewById(R.id.ingredients_recpg);
        instructions = (TextView)findViewById(R.id.instructions_recpg);
        topping = (TextView)findViewById(R.id.topping_recpg);

        str_name = String.valueOf(refRecipes.child(key).child("name").get());
        str_cal = String.valueOf(refRecipes.child(key).child("cal").get());
        str_description = String.valueOf(refRecipes.child(key).child("description").get());
        str_ingredients = String.valueOf(refRecipes.child(key).child("ingredients").get());
        str_instructions = String.valueOf(refRecipes.child(key).child("instructions").get());
        str_topping = String.valueOf(refRecipes.child(key).child("topping").get());

        name.setText(str_name + "(" + str_cal + "):");
        description.setText(str_description);
        ingredients.setText(str_ingredients);
        instructions.setText(str_instructions);
        topping.setText(str_topping);



    }



    public void back(View view) {
        finish();
    }
}