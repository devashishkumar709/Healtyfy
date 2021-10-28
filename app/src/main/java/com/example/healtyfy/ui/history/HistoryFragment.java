package com.example.healtyfy.ui.history;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healtyfy.MainActivity;
import com.example.healtyfy.R;
import com.example.healtyfy.databinding.FragmentHistoryBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class HistoryFragment extends Fragment {
    private LineChart lch;
    String email_ids;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);


        //Chart Initialize
        lch =(LineChart) root.findViewById(R.id.line_charter);
        lch.setDragEnabled(true);
        lch.setScaleEnabled(true);
        ArrayList<Entry> yValues =new ArrayList<>();
        //Chart Initialize--end

        //Database Code
        email_ids= MainActivity.getActivityInstance().getData();
        SQLiteDatabase myR = getActivity().openOrCreateDatabase(email_ids,android.content.Context.MODE_PRIVATE,null);
        myR.execSQL("CREATE TABLE IF NOT EXISTS data_base(product_name VARCHAR DEFAULT 'Item',cals Integer DEFAULT 0, pros Integer DEFAULT 0, carbs Integer DEFAULT 0, fats Integer DEFAULT 0,Meal VARCHAR,Date Date,Time Integer);");
        //Cursor D = myR.rawQuery("select * from data_base",null);
        Cursor D = myR.rawQuery("select sum(cals),Date from data_base group by Date",null);




        while(D.moveToNext()){
            String cal_count=(String) D.getString(0);
            String date_count=(String) D.getString(1);
            char[] ch = new char[date_count.length()];

            // Copy character by character into array
            for (int i = 0; i < date_count.length(); i++) {
                ch[i] = date_count.charAt(i);
            }
            String d1=String.valueOf(ch[5]);
            String d2=String.valueOf(ch[6]);
            String dd=d1+d2;

            int ddi= Integer.parseInt(dd);

            //System.out.println("goga"+ch[5]+ch[6]);



            //float cc=Float.parseFloat(cal_count);
            int cc=Integer.parseInt(cal_count);
            yValues.add(new Entry(ddi,cc));

        }
        XAxis xAxis = lch.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        LineDataSet set1 =new LineDataSet(yValues,"Calories(in kcal)");
        set1.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets =new ArrayList<>();
        dataSets.add(set1);
        LineData data=new LineData(dataSets);
        lch.getDescription().setEnabled(false);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);
        set1.setDrawFilled(true);
        set1.setDrawCircles(false);
        set1.setLineWidth(1);
        set1.setCircleRadius(4f);
        set1.setCircleColor(Color.BLACK);
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setColor(Color.BLACK);
        set1.setFillColor(Color.BLACK);
        set1.setFillAlpha(100);
        set1.setColor(Color.BLACK);
        lch.setData(data);








        return root;
    }
}