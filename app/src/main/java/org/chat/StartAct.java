package org.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/** Sab 12:23:27 26 Jan 2019
     todo 3 FirebaseUser
*/ 
public class StartAct extends AppCompatActivity {

    Button loginBtn, regBtn;

    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {//Sab 13:08:37 26 Januari 2019
        super.onStart();


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            Intent intent = new Intent(StartAct.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);


        loginBtn = findViewById(R.id.start_login_btn);
        regBtn = findViewById(R.id.start_reg_btn);
        
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartAct.this, LoginActivity.class));
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartAct.this, RegisterAct.class));
            }
        });
    }

}
