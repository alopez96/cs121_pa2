package com.example.arturolopez.hw2;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;


public class DetailActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);




        Intent i = getIntent();
        String title = i.getStringExtra("first");
        String description = i.getStringExtra("second");
        //String map = i.getStringExtra("third");
        //Double map = i.getDoubleExtra("third", 0);
        String time = i.getStringExtra("forth");
        String date = i.getStringExtra("fifth");
        Double map_long = i.getDoubleExtra("long_string",36.970628);
        Double map_lat = i.getDoubleExtra("lat_string", -122.028930);
        String map_long2 = String.valueOf(map_long);
        String map_lat2 = String.valueOf(map_lat);



        TextView t = (TextView)findViewById(R.id.textView3);
        TextView d = (TextView)findViewById(R.id.textView4);
        TextView c = (TextView) findViewById(R.id.textView7);
        TextView time1 = (TextView) findViewById(R.id.tv_time);
        TextView date1 = (TextView) findViewById(R.id.tv_date);
        TextView lg = (TextView) findViewById(R.id.tv_long);
        TextView lt = (TextView) findViewById(R.id.tv_lat);

        t.setText(title);
        d.setText(description);

        time1.setText(time);
        date1.setText(date);

        lg.setText(map_long2);
        lt.setText(map_lat2);
    }
}
