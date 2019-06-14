package com.ena.managemenapk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity
{
    private Button BuatAkunButton;
    private EditText InputUsername, InputNip, InputPassword;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        BuatAkunButton = (Button) findViewById(R.id.main_register_btn);
        InputUsername = (EditText) findViewById(R.id.register_username_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputNip = (EditText) findViewById(R.id.register_nip_input);
        loadingBar = new ProgressDialog(this);

        BuatAkunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                BuatAkun();
            }
        });
    }

    private void BuatAkun()
    {
        String username = InputUsername.getText().toString();
        String nip = InputNip.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "Nama",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(nip))
        {
            Toast.makeText(this, "NIP",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Password",Toast.LENGTH_SHORT).show();
        }
        else 
        {
            loadingBar.setTitle("Buat Akun");
            loadingBar.setMessage("Tolong tunggu sebentar");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            
            Validatenip(username, nip, password);
        }

    }

    private void Validatenip(final String username, final String nip, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!(dataSnapshot.child("Users").child(username).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("username", username);
                    userdataMap.put("password", password);
                    userdataMap.put("nip", nip);

                    RootRef.child("Users").child(username).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this,"Congratulations, your account has been created",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this,"Network Error: Please try again...",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"This" + username + "already exists.",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this,"Please try again",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }
}
