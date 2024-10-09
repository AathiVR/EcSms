package com.example.smssender;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    //Intialise Variable :

    EditText mobile,sms;
    MaterialButton b1;
    ImageButton clear1,clear2;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Assign variable :

        mobile = findViewById(R.id.mobile);
        sms = findViewById(R.id.sms);
        b1 = findViewById(R.id.b1);
        clear1 = findViewById(R.id.clear1);
        clear2 = findViewById(R.id.clear2);


        clear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sms.setText(" ");
            }
        });
        clear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile.setText(" ");
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check condition for permission :
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED)
                {
                    //When permission is Granted

                    //Creating method

                    sendSMS();
                }
                else {
                    //When Permission is Denied :
                    //request for Permission:
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},100);
                }


            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //check condition :


        if(requestCode==100 && grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            //permission is Granted :

            //call methed;

            sendSMS();
        }
        else {
            //when Permisson is denied :
            //Display Toaste msg :

            Toast.makeText(this, "Permission is Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSMS() {
        //Get value From Edit Text :

        String phone  = mobile.getText().toString();
        String msg = sms.getText().toString();
        //Checking if empty:
        if(!phone.isEmpty() && !msg.isEmpty())
        {
            //Intialise SMS Manager :
            SmsManager smsManager = SmsManager.getDefault();
            //send Message :

            smsManager.sendTextMessage(phone,null,msg,null,null);
            //Display Toast Msg :
            Toast.makeText(this, "Sent SuccessFully", Toast.LENGTH_SHORT).show();
        }
        else {
            //when String is Empty
            Toast.makeText(this, "Enter Text or Number First ", Toast.LENGTH_SHORT).show();
        }


    }
}