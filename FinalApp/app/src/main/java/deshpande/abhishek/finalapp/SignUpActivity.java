package deshpande.abhishek.finalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private EditText username, mail, password, confirmpassword;
    private Button signUpBtn;
    private TextView signInLinkBtn;
    private ConstraintLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("hasData",false)){
            Intent intent = new Intent(SignUpActivity.this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        username = findViewById(R.id.signupnameedittext);
        mail = findViewById(R.id.signupmailedittext);
        password = findViewById(R.id.signuppasswordedittext);
        confirmpassword = findViewById(R.id.signupconfirmpasswordedittext);
        parentLayout = findViewById(R.id.signupparentlayout);

        signInLinkBtn = findViewById(R.id.signupsigninlinkbutton);
        signInLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        signUpBtn = findViewById(R.id.signupsignupbutton);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(mail.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        HashMap<String, String> data = new HashMap<>();
                                        data.put("hasData", "false");
                                        data.put("username", username.getText().toString());

                                        String id = task.getResult().getUser().getUid();
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference reference = database.getReference("Users");
                                        reference.child(id).setValue(data)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {
                                                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                                                            editor.putString("mail", mail.getText().toString());
                                                            editor.putString("username", username.getText().toString());
                                                            editor.putBoolean("isLoggedIn", true);
                                                            editor.putBoolean("hasData", false);
                                                            editor.commit();

                                                            Intent intent = new Intent(SignUpActivity.this, ProfileActivity.class);
                                                            startActivity(intent);

                                                        } else {
                                                            FirebaseAuth.getInstance().signOut();
                                                            Snackbar.make(parentLayout, "Some Error Occured !", Snackbar.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    } else {
                                        Snackbar.make(parentLayout, "Some Error in Signing Up !", Snackbar.LENGTH_SHORT).show();
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
        if (mail.getText().toString().isEmpty() || password.getText().toString().isEmpty() || username.getText().toString().isEmpty()
                || confirmpassword.getText().toString().isEmpty()) {
            return false;
        } else {
            if (password.getText().toString().equals(confirmpassword.getText().toString())) {
                return true;
            } else {
                return false;
            }
        }
    }
}
