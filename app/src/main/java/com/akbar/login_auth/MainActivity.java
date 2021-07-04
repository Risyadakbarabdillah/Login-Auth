package com.akbar.login_auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btReg;
    String email, password;
    EditText eduser, edpass;
    TextView txBtLog;
    //private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //progressBar = new ProgressBar(this);


        firebaseAuth = firebaseAuth.getInstance();

        eduser = (EditText) findViewById(R.id.edUser);
        edpass = (EditText) findViewById(R.id.edPass);
        btReg = (Button) findViewById(R.id.btnRegister);
        txBtLog = (TextView) findViewById(R.id.txBtLogin);
        progressDialog = new ProgressDialog(this);

        btReg.setOnClickListener(this);
    }

    private void registerUser() {
        email = eduser.getText().toString().trim();
        password = edpass.getText().toString().trim();

        //mengecek jika email dan pasword kososng
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Silahkan masukkan email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Silahkan masukkan password", Toast.LENGTH_LONG).show();
            return;
        }

        //jika email dan password tidak kosong
        //tampilan dialog akan memproses

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //create user baru
        firebaseAuth.createUserWithEmailAndPassword("User email here", "user password here")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //untuk mengeek sukses
                        if (task.isSuccessful()) {
                            //tampilan berapa pesan disini
                            Toast.makeText(MainActivity.this, "Registrasi Sukses", Toast.LENGTH_LONG).show();
                        } else {
                            //tampilan pesan disini
                            Toast.makeText(MainActivity.this, "Registrasi Gagal!, cek koneksi anda", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }


    @Override
    public void onClick(View view) {
            registerUser();
        }
}
