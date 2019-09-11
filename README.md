#Start Here
Build Status
[![Build Status](https://circleci.com/gh/AminDhouib/AGILE.png?branch=master)](https://circleci.com/gh/AminDhouib/AGILE)
#End Here


# AGILE

Installation guide: 


(1) Install build: 
-On your Android phone, enable app installation from unknown source (temporarily)
-Connect Android to a laptop or PC
-Downloadl AGILE.apk, right click the build file > send to > locate your Android phone
-On your Android navigate to my Files > Installation files > Install the APK 



(2) Install from source:
-Download source code fron github
-Import the project to Android Studio 
-Run the Main Activity 

To sign in as admin:

Username: admin@admin.com
Password: 123456
To sign in as a Service Provider: 

UserName: sp@sp.com
Password: 123456


Group Project For SEG 2105.

Group members:

-Yesmine Zribi
-Amin Dhouib
-Gregory Ivo
-Jared Lueck

Purpose of the Project: This application allows home owners to connect with service providers.

****************************************************************************************

Note: If an admin account already exist please use the following credentials:
email: admin@admin.com
password:123456

Features which have currently been developed include: 

Deliverable 1
(1) Account creation: 
-One and only one Admin account can be created. 
-Home owners and service providers can create many accounts.
-Once the signup is successful users are taken back to the login screen
-Accounts are saved in a cloud based storage (Firebase)

(2) Authentication during account creation: 
-Every sign up field (name, last name, email, etc.) is validated. 
-Can validate if the entered password is the same as the confirmed password.
-One cannot create an account with a password that has less than 8 characters. 

(3) Login attempt:
-Users cannot log in if they do not have an account already. 

(4) Login:
-Users will see a welcome message with their name and their role. 
-Admin can log in and see a welcome message and a list of all users in the database.
-Home owners and service providers can only see a welcome message (for now).

Deliverable 2
(1) Admin can create, edit, delete services. 
(2) A service has a name and an hourly rate. 

Deliverable 3 
(1) A service provider can edit his/her account. He/she has to include their phone number, address, name, last name, and their company name. Description is optional. 
(2) A service provider may not edit their email. 
(3) A service provider can enter and edit his/her availability. 
(4) A service provider can search services added by the admin and add them to his/her account. 
(5) A service provider may delete services from his/her account. 
(6) A service provider cannot edit the service name or the rate of the service as that is governed by the admin. 
****************************************************************************************

We will add the following features to our android application in future deliverables:

(1) A homeowner can search for a service provider by type of services, time, rating. 
(2) A homeowner can book a service by selecting a preferred timeslot. 
(3) A homeowner can rate a service provider by providing a comment and a rate from 1 to 5. 


