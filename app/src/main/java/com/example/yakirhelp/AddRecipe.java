package com.example.yakirhelp;

import static com.example.yakirhelp.FBRefs.refRecipes;
import static com.example.yakirhelp.FBRefs.refUsers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity {

    EditText name, description, cal, ingredients, instructions, topping;
    Switch kosher;
    String str_name, str_description, str_cal, str_ingredients, str_instructions, str_topping;
    TextView errors;

    ArrayList Recipes;

    int next_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        name = (EditText)findViewById(R.id.name_recipe);
        description = (EditText)findViewById(R.id.description_recipe);
        cal = (EditText)findViewById(R.id.calories_recipe);
        ingredients = (EditText)findViewById(R.id.ingredients_recipe);
        instructions = (EditText)findViewById(R.id.instructions_recipe);
        topping = (EditText)findViewById(R.id.topping_recipe);
        kosher = (Switch)findViewById(R.id.kosher_recipe);

        Recipes = new ArrayList();

        refRecipes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                Recipes.clear();
                for(DataSnapshot data : dS.getChildren()) {
                    Recipes.add("" + data.getKey());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private boolean valid_name(){
        str_name = name.getText().toString();
        if (str_name.length() >= 3){
            return true;
        }
        return false;
    }

    private boolean valid_description(){
        str_description = description.getText().toString();
        return true;
    }

    private boolean valid_cal(){
        str_cal = cal.getText().toString();
        if (Double.parseDouble(str_cal) > 0){
            return true;
        }
        return false;
    }

    private boolean valid_ingredients(){
        str_ingredients = ingredients.getText().toString();
        if (str_ingredients.length() > 0){
            return true;
        }
        return false;
    }

    private boolean valid_instructions(){
        str_instructions = instructions.getText().toString();
        if (str_instructions.length() > 0){
            return true;
        }
        return false;
    }

    private boolean valid_topping(){
        str_topping = topping.getText().toString();
        return true;
    }

    public void submit_recipe(View view) {
        String errors = "";
        if (!valid_name()){errors += "Name is too short.\n";}
        if (!valid_description()){errors += "\n";}
        if (!valid_cal()){errors += "Invalid number of calories.\n";}
        if (!valid_ingredients()){errors += "must be at least one ingredient.\n";}
        if (!valid_instructions()){errors += "must be at least one instruction.\n";}
        if (!valid_topping()){errors += "\n";}
        if (errors == ""){
            next_key = Support.generate_recipe_key();
            String key = String.valueOf(next_key);
            refRecipes.child(key).setValue("");
            refRecipes.child(key).child("name").setValue(str_name);
            refRecipes.child(key).child("description").setValue(str_description);
            refRecipes.child(key).child("cal").setValue(str_cal);
            refRecipes.child(key).child("ingredients").setValue(str_ingredients);
            refRecipes.child(key).child("instructions").setValue(str_instructions);
            refRecipes.child(key).child("topping").setValue(str_topping);
            refRecipes.child(key).child("kosher").setValue(kosher.isChecked());
            // add to user
        }
    }
}