package com.an.qldhsp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class BarChart_1 extends AppCompatActivity {
    ArrayList<Integer> soLuongList = new ArrayList<>();
    ArrayList<String> tenSanPhamList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        Bundle bundle = getIntent().getExtras();
        soLuongList = bundle.getIntegerArrayList("soluong");
        tenSanPhamList =bundle.getStringArrayList("tenSp");
        PieChart pieChart= findViewById(R.id.pieChart);

        ArrayList<PieEntry> visitors = new ArrayList<>();
        for(int i=0;i<soLuongList.size();i++){
            visitors.add(new PieEntry(soLuongList.get(i) ,tenSanPhamList.get(i)));
        }

        PieDataSet pieDataSet=new PieDataSet(visitors,"Sản phẩm");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("BEST SELLER");
        pieChart.animate();
    }
}