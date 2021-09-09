package edu.unipr.valdrin.finalandroidassignment;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.unipr.valdrin.finalandroidassignment.TourPlaces.Events;
import edu.unipr.valdrin.finalandroidassignment.TourPlaces.Hotels;
import edu.unipr.valdrin.finalandroidassignment.TourPlaces.Restaurants;
import edu.unipr.valdrin.finalandroidassignment.TourPlaces.Shops;

public class Dashboard extends AppCompatActivity implements LocationListener {

    private static final int GPS_REQ_CODE = 385;
    LocationManager manager;
    Location userLocation;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private Spinner choiceSpinner;
    int categoryPosition = 0;
    TextView textView,textView11;
    MyTTS myTTS;
    private final int VOICE_REC_CODE = 654;
    public double latitude, longitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //myTTS = new MyTTS(getApplicationContext());
        ArrayList<String> choices = new ArrayList<>();
        textView11 = findViewById(R.id.textView11);
        textView = findViewById(R.id.textView2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText().equals("Press to show Events")) {
                    Intent intent = new Intent(Dashboard.this, Events.class);
                    startActivity(intent);
                } else if (textView.getText().equals("Press to show Restaurants")) {
                    Intent intent = new Intent(Dashboard.this, Restaurants.class);
                    startActivity(intent);
                } else if (textView.getText().equals("Press to show Hotels")) {
                    Intent intent = new Intent(Dashboard.this, Hotels.class);
                    startActivity(intent);
                } else if (textView.getText().equals("Press to show Shops")) {
                    Intent intent = new Intent(Dashboard.this, Shops.class);
                    startActivity(intent);
                }
            }
        });
        choiceSpinner = findViewById(R.id.choiceSpinner);
        choices.add("0.   Events ");
        choices.add("1.   Restaurants ");
        choices.add("2.   Hotels ");
        choices.add("3.   Shops ");

        ArrayAdapter<String> choicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, choices);
        choiceSpinner.setAdapter(choicesAdapter);
        choiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        textView.setText("Press to show Events");
                        break;
                    case 1:
                        textView.setText("Press to show Restaurants");
                        break;
                    case 2:
                        textView.setText("Press to show Hotels");
                        break;
                    case 3:
                        textView.setText("Press to show Shops");
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), String.valueOf(categoryPosition), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Please select a category in order to send a message!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void locate(View view){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_REQ_CODE);
        }else {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

    }
    public void stopgps(View view){
        manager.removeUpdates(this);

    }

    public void maps(View view) {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        intent.putExtra("userLocation", new double[]{userLocation.getLatitude(),userLocation.getLongitude()});
        startActivity(intent);

        //System.out.println(userLocation.getLatitude(),userLocation.getLongitude());
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        /*latitude = location.getLatitude();
        longitude = location.getLongitude();*/
        Toast.makeText(this,"User successfully located ",Toast.LENGTH_SHORT).show();
        userLocation = location;
        location.getLatitude();
        location.getLongitude();
        manager.removeUpdates(this);
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GPS_REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Thank you!", Toast.LENGTH_LONG).show();
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } else {
                showMessage("User info", "I really need the GPS permission in order to send a special code...");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_REC_CODE && resultCode == RESULT_OK) {
            ArrayList<String> recognized = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //showMessage("Recognized",recognized.toString());
            if (recognized.contains("events")) {
                Intent intent = new Intent(Dashboard.this, Events.class);
                startActivity(intent);
                myTTS.speak("Screen to Events");
            }
            if (recognized.contains("restaurants")) {
                Intent intent = new Intent(Dashboard.this, Restaurants.class);
                startActivity(intent);
                myTTS.speak("Screen to Restaurants");
            }
            if (recognized.contains("shops")) {
                Intent intent = new Intent(Dashboard.this, Shops.class);
                startActivity(intent);
                myTTS.speak("Screen to Shops");
            }
            if (recognized.contains("hotels")) {
                Intent intent = new Intent(Dashboard.this, Hotels.class);
                startActivity(intent);
                myTTS.speak("Screen to Hotels");
            }
            if (recognized.contains("go to map")) {
                Intent intent = new Intent(Dashboard.this, MapsActivity.class);
                startActivity(intent);
                myTTS.speak("Screen to to your location on map!");
            }

            if (recognized.contains("red")) {
                getWindow().getDecorView().setBackgroundColor(Color.RED);
                myTTS.speak("I just changed the screen color to red!");
            }
            if (recognized.contains("green"))
                getWindow().getDecorView().setBackgroundColor(Color.GREEN);
            if (recognized.contains("yellow"))
                getWindow().getDecorView().setBackgroundColor(Color.YELLOW);

        }
    }
    public void read(View view){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textView11.setText(textView11.getText().toString()+"\n"+snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void hear(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please say something!");
        startActivityForResult(intent, VOICE_REC_CODE);
    }

    public void speak(View view) {
        myTTS.speak(textView.getText().toString());
    }

    private void showMessage(String title, String message) {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}
