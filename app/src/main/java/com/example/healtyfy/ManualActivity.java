package com.example.healtyfy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ManualActivity extends AppCompatActivity {

    String email_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        email_ids=MainActivity.getActivityInstance().getData();
        //Toast.makeText(ManualActivity.this,"Data from first activity is"+email_ids, 1).show();

        SQLiteDatabase myR = openOrCreateDatabase(email_ids,MODE_PRIVATE,null);
        myR.execSQL("CREATE TABLE IF NOT EXISTS data_base(product_name VARCHAR,cals Integer, pros Integer, carbs Integer, fats Integer,Meal VARCHAR,Date Date,Time Integer);");

        Button saver =findViewById(R.id.save_button);
        saver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Insert(v);
            }
        });

    }

    public void Insert(View view) {
        EditText myEdit=(EditText) findViewById(R.id.ufood);
        String food_name= myEdit.getText().toString();
        myEdit=(EditText) findViewById(R.id.ucals);
        String calories= myEdit.getText().toString();
        myEdit=(EditText) findViewById(R.id.upros);
        String protiens= myEdit.getText().toString();
        myEdit=(EditText) findViewById(R.id.ucarbs);
        String carbs= myEdit.getText().toString();
        myEdit=(EditText) findViewById(R.id.ufats);
        String fats= myEdit.getText().toString();

        //Date currentTime = Calendar.getInstance().getTime();
        //String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        //String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HHmm", Locale.getDefault()).format(new Date());
        String currentDate = new SimpleDateFormat("yyyy-dd-MM", Locale.getDefault()).format(new Date());

        //System.out.println("lala");
        //System.out.println(currentTime);
        //System.out.println(currentDate);
        String meal_val;
        if(Integer.parseInt(currentTime)>=530 && Integer.parseInt(currentTime)<1200){
            meal_val="Breakfast";
        }
        else if(Integer.parseInt(currentTime)>=1200 && Integer.parseInt(currentTime)<1600){
            meal_val="Lunch";
        }
        else{
            meal_val="Dinner";
        }

        if(food_name.isEmpty() || calories.isEmpty() || protiens.isEmpty() || carbs.isEmpty() || fats.isEmpty()){
            Toast.makeText(getApplicationContext(),"Enter All Fields",Toast.LENGTH_SHORT).show();
        }
        else{
            ContentValues values = new ContentValues();
            values.put("product_name",food_name);
            values.put("cals",calories);
            values.put("pros",protiens);
            values.put("carbs",carbs);
            values.put("fats",fats);
            values.put("Meal",meal_val);
            values.put("Date",currentDate);
            values.put("Time",currentTime);
            SQLiteDatabase myR = openOrCreateDatabase(email_ids,MODE_PRIVATE,null);
            myR.insert("data_base",null,values);
            Intent Go_back = new Intent(getApplicationContext(),NaviActivity.class);
            startActivity(Go_back);
        }
    }
}