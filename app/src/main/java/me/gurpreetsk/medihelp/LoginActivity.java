package me.gurpreetsk.medihelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private TextView signUp;
    private Button login;
    private FirebaseAuth mAuth;
    private EditText emailField, passField;
    private String email, password;
    private FirebaseAuth.AuthStateListener mAuthListener;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        if (preferences.getBoolean("isLogin", false)) {
            startActivity(new Intent(LoginActivity.this, HeatMapActivity.class));
        } else {
            setContentView(R.layout.activity_login);

            signUp = (TextView) findViewById(R.id.signUp);
            login = (Button) findViewById(R.id.login);
            emailField = (EditText) findViewById(R.id.email);
            passField = (EditText) findViewById(R.id.password);
            mAuth = FirebaseAuth.getInstance();
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
// User is signed in
                        Log.d("FireAuth", "onAuthStateChanged:signed_in:" + user.getUid());
                        startActivity(new Intent(LoginActivity.this, HeatMapActivity.class));
                        //textView.setText("Already Logged in: "+user.getEmail());
                    } else {
// User is signed out
                        Log.d("FireAuth", "onAuthStateChanged:signed_out");
                    }
                }
            };
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    email = emailField.getText().toString();
                    password = passField.getText().toString();
                    logIn(email, password);
                }
            });
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            });
        }
    }


    private void logIn(final String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("FireAuth", "signInWithEmail:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.w("FireAuth", "signInWithEmail", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("HeartRateActivity", "Welcome " + email);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLogin", true);
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, HeatMapActivity.class));
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mAuth != null)
            mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
