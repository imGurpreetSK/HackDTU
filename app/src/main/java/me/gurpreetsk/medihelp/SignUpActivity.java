package me.gurpreetsk.medihelp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import me.gurpreetsk.medihelp.model.UserData;

public class SignUpActivity extends AppCompatActivity {
    private EditText name, blood, email, pass, address;
    private String regname, regmail, regpass, regblood, userAddress, macAddress;
    private Button signUpButton;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (EditText) findViewById(R.id.name);
        blood = (EditText) findViewById(R.id.blood);
        email = (EditText) findViewById(R.id.signupmail);
        pass = (EditText) findViewById(R.id.signupass);
        address = (EditText) findViewById(R.id.address);
        signUpButton = (Button) findViewById(R.id.submit);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("userDetails");
        mAuth = FirebaseAuth.getInstance();

        macAddress = android.provider.Settings.Secure.getString(getContentResolver(), "bluetooth_address");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("FireAuth", "onAuthStateChanged:signed_in:" + user.getUid());
                    startActivity(new Intent(SignUpActivity.this, HeatMapActivity.class));
                    //textView.setText("Already Logged in: "+user.getEmail());
                } else {
                    Log.d("FireAuth", "onAuthStateChanged:signed_out");
                }
            }
        };

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regname = name.getText().toString();
                regblood = blood.getText().toString();
                regpass = pass.getText().toString();
                userAddress = address.getText().toString();
                regmail = email.getText().toString();
                doSignUp(regmail, regpass);
            }
        });

    }


    private void doSignUp(final String regmail, String regpass) {
        Log.d("SignUp", regmail + " " + regpass);
        mAuth.createUserWithEmailAndPassword(regmail, regpass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Auth", "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("SignUpActivity", "onComplete: Failed=" + task.getException().getMessage());
                        } else {
                            Log.d("SignUpActivity", "Done registration");
                            UserData user = new UserData(regname, userAddress, regblood, regmail, macAddress);
                            mDatabase.child(regname).push().setValue(user);
                            startActivity(new Intent(SignUpActivity.this, HeatMapActivity.class));
                        }
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
