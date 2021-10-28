package com.example.healtyfy.ui.settings;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healtyfy.MainActivity;
import com.example.healtyfy.ManualActivity;
import com.example.healtyfy.NaviActivity;
import com.example.healtyfy.R;
import com.example.healtyfy.databinding.FragmentSettingsBinding;
import com.example.healtyfy.foodrecognizerexample.CameraActivity;

public class SettingsFragment extends Fragment {
    String email_ids;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        email_ids= MainActivity.getActivityInstance().getData();
        EditText myEdit=(EditText) root.findViewById(R.id.age);
        EditText myEdit1=(EditText) root.findViewById(R.id.height);
        EditText myEdit2=(EditText) root.findViewById(R.id.weight);
        EditText myEdit3=(EditText) root.findViewById(R.id.gender);
        EditText myEdit4=(EditText) root.findViewById(R.id.target);







        SQLiteDatabase myR = getActivity().openOrCreateDatabase("details",android.content.Context.MODE_PRIVATE,null);
        myR.execSQL("CREATE TABLE IF NOT EXISTS data_base1(Email VARCHAR,Age Integer,Height Integer,Weight Integer,Gender VARCHAR,Target Integer);");


        Button manual = root.findViewById(R.id.sett_button);

        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String age= myEdit.getText().toString();
                String height= myEdit1.getText().toString();
                String weight= myEdit2.getText().toString();
                String gender= myEdit3.getText().toString();
                String target= myEdit4.getText().toString();

                if(age.isEmpty() || height.isEmpty() || weight.isEmpty() || gender.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),"Enter All Fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    ContentValues values = new ContentValues();
                    values.put("Email",email_ids);
                    values.put("Age",age);
                    values.put("Weight",weight);
                    values.put("Height",height);
                    values.put("Gender",gender);
                    values.put("Target",target);
                    SQLiteDatabase myR = getActivity().openOrCreateDatabase("details",android.content.Context.MODE_PRIVATE,null);
                    myR.insert("data_base1",null,values);
                    Intent myIntent = new Intent(getActivity(), NaviActivity.class);
                    getActivity().startActivity(myIntent);
                    Toast.makeText(getActivity().getApplicationContext(),"Details Saved Sucessfully",Toast.LENGTH_SHORT).show();

                }


            }
        });


        return root;
    }

}