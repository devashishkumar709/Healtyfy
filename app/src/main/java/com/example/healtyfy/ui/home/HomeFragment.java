package com.example.healtyfy.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healtyfy.MainActivity;
import com.example.healtyfy.foodrecognizerexample.CameraActivity;
import com.example.healtyfy.ManualActivity;
import com.example.healtyfy.R;
import com.example.healtyfy.databinding.FragmentHomeBinding;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;


public class HomeFragment extends Fragment {
    String email_ids;

    PieChart pieChart;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button manual = root.findViewById(R.id.button_manual);
        email_ids=MainActivity.getActivityInstance().getData();
        TextView namer=(TextView) root.findViewById(R.id.email_bar);
        namer.setText(email_ids);
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), ManualActivity.class);
                getActivity().startActivity(myIntent);
            }
        });

        Button camera = root.findViewById(R.id.button_camera);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), CameraActivity.class);
                getActivity().startActivity(myIntent);
            }
        });


        //Database Code
        SQLiteDatabase myR = getActivity().openOrCreateDatabase(email_ids,android.content.Context.MODE_PRIVATE,null);
        myR.execSQL("CREATE TABLE IF NOT EXISTS data_base(product_name VARCHAR DEFAULT 'Item',cals Integer DEFAULT 0, pros Integer DEFAULT 0, carbs Integer DEFAULT 0, fats Integer DEFAULT 0,Meal VARCHAR,Date Date,Time Integer);");
        Cursor D = myR.rawQuery("select * from data_base",null);
        int lunches=0;
        int breakfasts=0;
        int dinners=0;
        int lcal=0;
        int bcal=0;
        int dcal=0;
        while(D.moveToNext()){
            String bld=(String) D.getString(5);
            if(bld.equals("Lunch")){
                lunches+=1;
                String goli = D.getString(1);
                lcal+=Integer.parseInt(goli);
            }
            if(bld.equals("Breakfast")){
                breakfasts+=1;
                String goli = D.getString(1);
                bcal+=Integer.parseInt(goli);
            }
            if(bld.equals("Dinner")){
                dinners+=1;
                String goli = D.getString(1);
                dcal+=Integer.parseInt(goli);
            }
        }
        //System.out.println("Gamma"+" "+bcal+" "+lcal+" "+dcal);

        //pie chart data

        pieChart = root.findViewById(R.id.piechart);
        pieChart.addPieSlice(new PieModel("Breakfast", bcal, Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(new PieModel("Lunch", lcal, Color.parseColor("#EF5350")));
        pieChart.addPieSlice(new PieModel("Dinner", dcal, Color.parseColor("#29B6F6")));
        // To animate the pie chart
        pieChart.startAnimation();



        return root;
    }
}