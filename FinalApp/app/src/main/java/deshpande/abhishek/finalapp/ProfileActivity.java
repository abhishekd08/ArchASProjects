package deshpande.abhishek.finalapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private TextView shoulderText, chestText, waistText, usernameText;
    private Button measureBtn;
    private ConstraintLayout parentLayout;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameText = findViewById(R.id.profilenametextview);
        shoulderText = findViewById(R.id.profileshouldertextview);
        chestText = findViewById(R.id.profilechesttextview);
        waistText = findViewById(R.id.profilewaisttextview);
        measureBtn = findViewById(R.id.profilemeasurebutton);
        parentLayout = findViewById(R.id.profileparentlayout);
        frameLayout = findViewById(R.id.profileemptyframelayout);

        measureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Enter your Height (in CM)");
                final EditText editText = new EditText(ProfileActivity.this);
                builder.setView(editText);
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float height = Float.valueOf(editText.getText().toString());
                        PreferenceManager
                                .getDefaultSharedPreferences(getApplicationContext())
                                .edit()
                                .putFloat("height", height)
                                .commit();

                        Intent intent = new Intent(ProfileActivity.this, FrontViewActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPreferences.getBoolean("hasData", false)) {
            frameLayout.setVisibility(View.INVISIBLE);
            float shoulder = sharedPreferences.getFloat("shoulder", 0) + 10;
            float chest = sharedPreferences.getFloat("chest", 0) + 2;
            float waist = sharedPreferences.getFloat("waist", 0) + 3;
            String username = sharedPreferences.getString("username", "");

            usernameText.setText(username);
            shoulderText.setText("Shoulders : " + shoulder + " CM");
//            chestText.setText("Chest : " + chest);
            chestText.setText("Chest : \n" + chest / 2 + " Inch");
//            waistText.setText("Waist : " + waist);
            waistText.setText("Waist : \n" + waist / 2 + " Inch");
        } else {
            frameLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FirebaseAuth.getInstance().signOut();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().clear().commit();

        Intent intent = new Intent(ProfileActivity.this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        return true;
    }
}
