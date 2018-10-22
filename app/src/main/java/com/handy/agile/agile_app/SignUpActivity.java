package com.handy.agile.agile_app;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends Activity {

    EditText firstNameNewUser;
    EditText lastNameNewUser;
    EditText emailNewUser;
    EditText passwordNewUser;
    EditText confirmNewUserPassword;
    EditText phoneNumberNewUser;
    EditText addressNewUser;
    Spinner spinnerRole;

    DatabaseReference databaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

         firstNameNewUser = (EditText) findViewById(R.id.firstNameNewUser);
         lastNameNewUser = (EditText) findViewById(R.id.lastNameNewUser);
         emailNewUser = (EditText) findViewById(R.id.emailNewUser);
         passwordNewUser = (EditText) findViewById(R.id.passwordNewUser);
         confirmNewUserPassword = (EditText) findViewById(R.id.confirmNewUserPassword);
         phoneNumberNewUser = (EditText) findViewById(R.id.phoneNumberNewUser);
         addressNewUser = (EditText) findViewById(R.id.addressNewUser);
         spinnerRole = (Spinner) findViewById(R.id.spinnerRole);


        databaseUser = FirebaseDatabase.getInstance().getReference();

         final Button bRegister = (Button) findViewById(R.id.bRegister);
         bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //First check if username is available
                checkUsername(emailNewUser.getText().toString().trim());
            }
        });
    }


    private void addUsertoDB() {
        //1. get inputs
        String name = firstNameNewUser.getText().toString().trim();
        String lastName = lastNameNewUser.getText().toString().trim();
        String email = emailNewUser.getText().toString().trim();
        String password = passwordNewUser.getText().toString().trim();
        String confirmPassword = confirmNewUserPassword.getText().toString().trim();
        String phoneNumber = phoneNumberNewUser.getText().toString().trim();
        String address = addressNewUser.getText().toString().trim();
        String role = spinnerRole.getSelectedItem().toString().trim();

        //If the rest of the users information is valid, proceed to adding them to the database
        if(verifyInfo(name, lastName, email, password, confirmPassword, phoneNumber, address)) {


            String id = databaseUser.push().getKey();
            User newUser;
            if (role.equals("Home Owner")) {
                newUser = new HomeOwner(name, lastName, email, password, phoneNumber, address, role, id);
            } else {
                newUser = new ServiceProvider(name, lastName, email, password, phoneNumber, address, role, id);
            }

            //4. Add user to database
            databaseUser.child(id).setValue(newUser);

            //Redirect to log in
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("registered", true);
            startActivity(intent);
        }


    }

    private boolean verifyInfo(String name, String lastName, String email, String password,
                                String confirmPassword, String phoneNumber, String address) {
        //if name is empty
        if (name.isEmpty()) {
            firstNameNewUser.setError("Name is required");
            firstNameNewUser.requestFocus();
            return false;
        }

        //validate name
        if (!name.matches("[A-Z][a-zA-Z]*")) {
            firstNameNewUser.setError("Please enter a valid name");
            firstNameNewUser.requestFocus();
            return false;
        }

        //if lastName is empty
        if (lastName.isEmpty()) {
            lastNameNewUser.setError("last name is required");
            lastNameNewUser.requestFocus();
            return false;
        }

        //validate lastName
        if (!lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
            lastNameNewUser.setError("Please enter a valid last name");
            lastNameNewUser.requestFocus();
            return false;
        }

        //if email is empty
        if (email.isEmpty()) {
            emailNewUser.setError("email is required");
            emailNewUser.requestFocus();
            return false;
        }

        //validate email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailNewUser.setError("Please enter a valid email");
            emailNewUser.requestFocus();
            return false;
        }

        //if password is empty
        if (password.isEmpty()) {
            passwordNewUser.setError("password is required");
            passwordNewUser.requestFocus();
            return false;
        }

        //validate password
        if (password.length()<6) {
            passwordNewUser.setError("Minimum length of password should b 8 ");
            passwordNewUser.requestFocus();
            return false;
        }

        //if confirmPassword is empty
        if (confirmPassword.isEmpty()) {
            confirmNewUserPassword.setError("You need to confirm your password");
            confirmNewUserPassword.requestFocus();
            return false;
        }

        //validate confirmPassword
        if (!confirmPassword.equals(password)) {
            confirmNewUserPassword.setError("passwords do not match");
            confirmNewUserPassword.requestFocus();
            return false;
        }

        //if phoneNumber is empty
        if (phoneNumber.isEmpty()) {
            phoneNumberNewUser.setError("phone number is required");
            phoneNumberNewUser.requestFocus();
            return false;
        }

        //validate phoneNumber
        if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            phoneNumberNewUser.setError("Please enter a valid phone number");
            phoneNumberNewUser.requestFocus();
            return false;
        }

        //if address is empty
        if (address.isEmpty()) {
            addressNewUser.setError("Name is required");
            addressNewUser.requestFocus();
            return false;
        }

        //validate address
        if (!address.matches("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)")) {
            addressNewUser.setError("Please enter a valid address");
            addressNewUser.requestFocus();
            return false;
        }

        return true;
    }

    public void checkUsername(String username){

        //Search in database for any user with the same email
        databaseUser.orderByChild("email").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Check ff the dataSnapshot contains non-null value, if it does not then the username is available and we will
                //proceed to checking the user input and add the user to the db
                if(!dataSnapshot.exists()){
                    addUsertoDB();
                }else{
                    //Username unavailable, show warning
                    emailNewUser.setError("Username unavailable");
                    emailNewUser.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }








}
