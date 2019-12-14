package deshpande.abhishek.finalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    private EditText mail, password;
    private Button signInBtn;
    private ConstraintLayout parentLayout;
    private TextView signUpLinkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mail = findViewById(R.id.signinmailedittext);
        password = findViewById(R.id.signinpasswordedittext);
        signInBtn = findViewById(R.id.signinsigninbutton);
        parentLayout = findViewById(R.id.signinparentlayout);

        signUpLinkBtn = findViewById(R.id.signinsignuplinkbutton);
        signUpLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(mail.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        String id = task.getResult().getUser().getUid();

                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference reference = database.getReference("Users").child(id);
                                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String hasData = (String) dataSnapshot.child("hasData").getValue();
                                                String username = (String) dataSnapshot.child("username").getValue();
                                                if (hasData.equals("true")) {
                                                    String shoulder = (String) dataSnapshot.child("shoulder").getValue();
                                                    String chest = (String) dataSnapshot.child("chest").getValue();
                                                    String waist = (String) dataSnapshot.child("waist").getValue();

                                                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                                                    editor.putBoolean("hasData", true);
                                                    editor.putBoolean("isLoggedIn", true);
                                                    editor.putString("shoulder", shoulder);
                                                    editor.putString("chest", chest);
                                                    editor.putString("waist", waist);
                                                    editor.putString("username", username);
                                                    editor.commit();

                                                    Intent intent = new Intent(SignInActivity.this, ProfileActivity.class);
                                                    startActivity(intent);

                                                } else {

                                                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                                                    boolean isDataAvailable = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("hasData",false);
                                                    editor.putBoolean("hasData", isDataAvailable);
                                                    editor.putBoolean("isLoggedIn", true);
                                                    editor.putString("username", username);
                                                    editor.commit();

                                                    Intent intent = new Intent(SignInActivity.this, ProfileActivity.class);
                                                    startActivity(intent);

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Snackbar.make(parentLayout, "Some Error Occured !", Snackbar.LENGTH_SHORT).show();
                                            }
                                        });

                                    } else {
                                        Snackbar.make(parentLayout, "Error in Signing In !", Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Snackbar.make(parentLayout, "All fields are required !", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateData() {

        if (mail.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
