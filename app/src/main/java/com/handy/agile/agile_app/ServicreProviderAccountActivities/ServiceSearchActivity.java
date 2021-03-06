package com.handy.agile.agile_app.ServicreProviderAccountActivities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.handy.agile.agile_app.DomainClasses.Service;
import com.handy.agile.agile_app.DomainClasses.ServiceProvider;
import com.handy.agile.agile_app.DomainClasses.User;
import com.handy.agile.agile_app.ListClasses.ServiceListForAdding;
import com.handy.agile.agile_app.R;

import java.util.ArrayList;
import java.util.List;

public class ServiceSearchActivity extends AppCompatActivity {

    TextView etServiceType;
    TextView etHourlyRate;


    List<Service> services;
    ListView listViewServices;

    DatabaseReference database;
    DatabaseReference databaseService;
    DatabaseReference databaseServiceProvider;

    User user;
    Service service;

    TimePickerDialog timePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        //Get info of the profile that started this activity
        user = (User) intent.getSerializableExtra("SP");
        setContentView(R.layout.activity_service_search);

        //Get reference to DB
        database = FirebaseDatabase.getInstance().getReference();
        databaseService = database.child("services");

        databaseServiceProvider = database.child("serviceProviders");


        listViewServices = findViewById(R.id.listViewServices);
        services = new ArrayList<>();

        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Extract the service object from the clicked item
                service = (Service) services.get(position);

                //check if user is already a provider of this service
                checkService(service);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDataBase();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }



    //Display table of the service DB
    private void displayDataBase() {
        databaseService.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                services.clear();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Service service = snapshot.getValue(Service.class);

                    services.add(service);
                }
                ServiceListForAdding userAdapter = new ServiceListForAdding(ServiceSearchActivity.this,services);
                listViewServices.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }



//Method checks if the user is not already providing the service
public void checkService(final Service service){
        databaseServiceProvider.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean found = false;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        ServiceProvider foundUser = snapshot1.getValue(ServiceProvider.class);
                        //if the key is the same as the sercvice type and the user name
                        // is the same as the user in the db, then the user is already providing this service.
                        if (snapshot.getKey().equals(service.getType()) && foundUser.getId().equals(user.getId())) {
                            //set flag to true
                            found = true;
                        }
                    }

                }
                    //If the user was not found, add them as a service provider for this service
                    if (!found) {
                        String key = databaseServiceProvider.child(service.getType()).push().getKey();
                        databaseServiceProvider.child(service.getType()).child(key).setValue(user);

                    //Otherwise the user was already a service provider so we will not add them
                    } else {
                        //Display message saying they are already a service provider
                        Toast toast = Toast.makeText(getApplicationContext(), "You already selected this service", Toast.LENGTH_LONG);
                        toast.show();
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


}
    public User getUser(){
        return user;
    }




}
