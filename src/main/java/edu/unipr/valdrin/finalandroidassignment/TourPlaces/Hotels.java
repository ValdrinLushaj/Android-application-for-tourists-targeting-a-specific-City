package edu.unipr.valdrin.finalandroidassignment.TourPlaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import edu.unipr.valdrin.finalandroidassignment.MapsActivity;
import edu.unipr.valdrin.finalandroidassignment.R;

public class Hotels extends AppCompatActivity {
    ImageView image1,image2,image3,image4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        image1 = findViewById(R.id.imageView6);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("userLocation", new double[]{42.6630173,21.1631121});
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Location to Swiss Hotel Pristina",Toast.LENGTH_LONG).show();
            }
        });
        image2 = findViewById(R.id.imageView3);
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("userLocation", new double[]{42.6585832,21.1617173});
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Location to Grand Hotel Pristina",Toast.LENGTH_LONG).show();
            }
        });
        image3 = findViewById(R.id.imageView4);
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("userLocation", new double[]{42.6572419,221.152984});
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Location to  Hotel Pristina",Toast.LENGTH_LONG).show();
            }
        });
        image4 = findViewById(R.id.imageView2);
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("userLocation", new double[]{42.6428517,21.1381242});
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Location to Hotel Garden Pristina",Toast.LENGTH_LONG).show();
            }
        });
    }
}