package com.benrobbins.herokugimballogic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String TAG = "MainActivity";

        final EditText nameInput = (EditText) findViewById(R.id.nameInput);
        final Button viewAllButton = (Button) findViewById(R.id.viewAllButton);
        final Button button = (Button) findViewById(R.id.button);
        final TextView textDisplay = (TextView) findViewById(R.id.textDisplay);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://damp-sands-97847.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final PersonService service = retrofit.create(PersonService.class);

        viewAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<String> queryCall = service.getNames();
                queryCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String s = response.body();
                        textDisplay.setText(s);
                        Log.i("Response", " = " + s);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.i("Error Occurred", t.getLocalizedMessage());
                    }
                });
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameInput.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter A Username", Toast.LENGTH_SHORT).show();
                } else {
                    Person person = new Person(nameInput.getText().toString());
                    Call<String> createCall = service.insertUser(person.name);
                    Log.i(TAG, "Person.name = " + person.name);
                    createCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            Toast.makeText(MainActivity.this,
                                    "User Added Successfully:\n\n" + response.body(),
                                    Toast.LENGTH_LONG).show();

                            nameInput.getText().clear();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.i(TAG, "An Error Occurred : " + Arrays.toString(t.getStackTrace()));
                        }
                    });
                }
            }
        });
    }
}