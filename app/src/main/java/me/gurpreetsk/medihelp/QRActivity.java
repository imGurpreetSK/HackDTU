package me.gurpreetsk.medihelp;

import android.graphics.Bitmap;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.qrcode.encoder.QRCode;

import net.glxn.qrgen.core.scheme.VCard;

import org.json.JSONException;
import org.json.JSONObject;

import me.gurpreetsk.medihelp.model.UserData;

public class QRActivity extends AppCompatActivity {
    private Button btnPatient,btnVisitor;
    private ImageView qrImage;
    DatabaseReference refrence;
    private String username,address,bloodgroup,email,mac;
    private UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        btnPatient= (Button) findViewById(R.id.btnPatient);
        //btnVisitor= (Button) findViewById(R.id.btnVisitor);
        qrImage= (ImageView) findViewById(R.id.qr);
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        refrence=database.getReference("userDetails");
        btnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              refrence.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                      for(DataSnapshot data:dataSnapshot.getChildren()){
                          Log.d("DATAAAA","---> "+data.getValue().toString());
                          for(DataSnapshot d:data.getChildren()){
                              Log.d("TAAAAAAAAAG",d.getValue().toString());
                              for(DataSnapshot dataSnapshot1:d.getChildren()){
                                  if(dataSnapshot1.getKey().toString().equals("email")){
                                      email=dataSnapshot1.getValue().toString();
                                      Log.d("TAAAAA","email "+email);
                                  }
                                  if(dataSnapshot1.getKey().toString().equals("address")){
                                      address=dataSnapshot1.getValue().toString();
                                      Log.d("TAAAAA","address "+address);
                                  }
                                  if(dataSnapshot1.getKey().toString().equals("bloodGroup")){
                                      bloodgroup=dataSnapshot1.getValue().toString();
                                      Log.d("TAAAAA","bloodgroup "+bloodgroup);
                                  }
                                  if(dataSnapshot1.getKey().toString().equals("userName")){
                                      username=dataSnapshot1.getValue().toString();
                                      Log.d("TAAAAA","username "+username);
                                  }
                                  if(dataSnapshot1.getValue().toString().equals("mac")){
                                      mac=dataSnapshot1.getValue().toString();
                                      Log.d("TAAAAA","mac "+mac);
                                  }
                              }
                          }
                      }
                      userData=new UserData();
                      userData.setAddress(address);
                      userData.setBloodGroup(bloodgroup);
                      userData.setEmail(email);
                      userData.setMac(mac);
                      userData.setUserName(username);
                      String Userinfo=new String(username+"\n"+address+"\n"+email+"\n"+bloodgroup);
                      Bitmap bitmap=net.glxn.qrgen.android.QRCode.from(Userinfo).bitmap();
                      qrImage.setImageBitmap(bitmap);
                  }


                  @Override
                  public void onCancelled(DatabaseError databaseError) {

                  }

              });

            }
        });
    }
}
