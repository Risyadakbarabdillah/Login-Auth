package com.cahyonoz.login_auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener{

    Button btLog;
    String emailLog, passLog;
    EditText edEmail, edpassword;
    //TextView txBtLog;

    //private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //progressBar = new ProgressBar(this);


        firebaseAuth = firebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainMenu.class));
        }

        edEmail = (EditText) findViewById(R.id.edUserr);
        edpassword = (EditText) findViewById(R.id.edPassword);
        btLog = (Button) findViewById(R.id.btnLogin);
        //txBtLog = (TextView) findViewById(R.id.txBtLogin);
        progressDialog = new ProgressDialog(this);

        btLog.setOnClickListener(this);
    }

    private void LogUser() {
        emailLog = edEmail.getText().toString().trim();
        passLog = edpassword.getText().toString().trim();

        //mengecek jika email dan pasword kososng
        if (TextUtils.isEmpty(emailLog)) {
            Toast.makeText(this, "Silahkan masukkan email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(passLog)) {
            Toast.makeText(this, "Silahkan masukkan password", Toast.LENGTH_LONG).show();
            return;
        }

        //jika email dan password tidak kosong
        //tampilan dialog akan memproses

        progressDialog.setMessage("Login Please Wait...");
        progressDialog.show();

        //create user baru
        firebaseAuth.createUserWithEmailAndPassword(emailLog, passLog)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //tampilan berapa pesan disini
                            //Toast.makeText(Login.this, "Registrasi Sukses", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainMenu.class));
                        } else {
                            //tampilan pesan disini
                            Toast.makeText(Login.this, "email/password salah!", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }


    @Override
    public void onClick(View view) {
        if(view == btLog){
            LogUser();
        }
    }
}
