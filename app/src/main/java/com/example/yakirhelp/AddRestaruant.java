package com.example.yakirhelp;

import static com.example.yakirhelp.FBRefs.refRecipes;
import static com.example.yakirhelp.FBRefs.refRestaurants;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class AddRestaruant extends AppCompatActivity {

    EditText res_name, res_link, res_location, pro_name, pro_description, pro_calories;
    Switch kosher;
    String str_res_name, str_res_link, str_res_location, str_pro_name, str_pro_description, str_pro_calories;
    TextView res_errors, pro_errors;

    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaruant);

        res_name = (EditText)findViewById(R.id.res_name_add_res);
        res_link = (EditText)findViewById(R.id.res_link_add_res);
        res_location = (EditText)findViewById(R.id.res_location_add_res);
        pro_name = (EditText)findViewById(R.id.pro_name_add_res);
        pro_description = (EditText)findViewById(R.id.pro_description_add_res);
        pro_calories = (EditText)findViewById(R.id.pro_calories_add_res);
        res_errors = (TextView)findViewById(R.id.res_errors_add_res);
        pro_errors = (TextView)findViewById(R.id.pro_errors_add_res);

        products = new ArrayList<Product>();

    }

    private boolean valid_res_name(){
        str_res_name = res_name.getText().toString();
        if (str_res_name.length() > 0){
            return true;
        }
        return false;
    }

    private boolean valid_res_link(){
        str_res_link = res_link.getText().toString();
        if (str_res_link.length() > 0){
            return true;
        }
        return false;
    }

    private boolean valid_res_location(){
        str_res_location = res_location.getText().toString();
        if (str_res_location.length() > 0){
            return true;
        }
        return false;
    }

    private boolean valid_pro_name(){
        str_pro_name = pro_name.getText().toString();
        if (str_pro_name.length() > 0){
            return true;
        }
        return false;
    }

    private boolean valid_pro_description(){
        str_pro_description = pro_description.getText().toString();
        return true;
    }

    private boolean valid_pro_calories(){
        str_pro_calories = pro_calories.getText().toString();
        if (Double.parseDouble(str_pro_calories) > 0){
            return true;
        }
        return false;
    }

    private boolean valid_products(){
        if (products.size() > 0){
            return true;
        }
        return false;
    }

    private ArrayList<String> locations(){
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(str_res_location.split(" , ")));
        return list;
    }


    public void add_pro_add_res(View view) {
        String errors = "";
        if (!valid_pro_name()){errors += "Invalid name of product.\n";}
        if (!valid_pro_description()){errors += "Invalid description.\n";}
        if (!valid_pro_calories()){errors += "Invalid number of calories.\n";}
        pro_errors.setText(errors);
        if (errors == ""){
            // add to product list
            String key = String.valueOf(Support.generate_recipe_key());
            Product temp = new Product(str_pro_name, str_pro_description,
                    Integer.parseInt(str_pro_calories), kosher.isChecked(), key);
            products.add(temp);
            // add to firebase
            refRecipes.child(key).setValue("");
            refRecipes.child(key).child("name").setValue(str_pro_name);
            refRecipes.child(key).child("description").setValue(str_pro_description);
            refRecipes.child(key).child("calories").setValue(Integer.parseInt(str_pro_calories));
            refRecipes.child(key).child("kosher").setValue(kosher.isChecked());
        }
    }

    public void submit(View view) {
        String errors = "";
        if (!valid_res_name()){errors += "Invalid name of restaurant.\n";}
        if (!valid_res_link()){errors += "Invalid link.\n";}
        if (!valid_res_location()){errors += "There must be at least one location.\n";}
        if (!valid_products()){errors += " There must be at least one product.\n";}
        res_errors.setText(errors);
        if (errors == ""){
            String key = String.valueOf(Support.generate_restaurant_key());
            refRestaurants.child(key).setValue("");
            refRestaurants.child(key).child("name").setValue(str_res_name);
            refRestaurants.child(key).child("link").setValue(str_res_link);
            refRestaurants.child(key).child("locations").setValue("");
            ArrayList<String> location_list = locations();
            for (int i = 0; i < location_list.size(); i++){
                refRestaurants.child(key).child("locations").child(location_list.get(i)).setValue("");
            }
            refRestaurants.child(key).child("products").setValue("");
            for (int i = 0; i < products.size(); i++){
                refRestaurants.child(key).child("locations").child(products.get(i).getKey()).setValue("");
            }

        }
    }
}