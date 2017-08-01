package webtek.cse.uem.biswajit.com.requestresuereduce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.AuthConfig;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements UploadAndDownloadData {

    SharedPreferences sharedPreferences, sharedPreferences1;
    int k;
    TextView tv;
    Toolbar mtool;
    Boolean val;
    Button btn_login;
    String phone;
    EditText etlogin;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TwitterAuth.IntializeTwitter(Login.this);
        initialize();
        if (val == true) {
            Intent i = new Intent(Login.this, FeedsActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phone = etlogin.getText().toString();

                if (!(phone.length() == 10)) {
                    etlogin.setError("Enter Mobile No. Correctly");
                } else {
                    DownloadData();
                }

            }
        });

    }


    private void initialize() {
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        sharedPreferences1 = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        k = sharedPreferences.getInt("value", 0);
        val = sharedPreferences1.getBoolean("Login", false);
        tv = (TextView) findViewById(R.id.textview_registerlink);
        btn_login = (Button) findViewById(R.id.button_login);
        etlogin = (EditText) findViewById(R.id.edt_login_mob);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mtool = (Toolbar) findViewById(R.id.toolbar_login);
        mtool.setTitle("Login");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void UploadData() {

    }

    @Override
    public void DownloadData() {

        Digits.clearActiveSession();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("register");
        databaseReference.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Toast.makeText(getApplicationContext(), "Register Your Mobile No. First", Toast.LENGTH_SHORT).show();
                } else {
                    AuthConfig.Builder builder = new AuthConfig.Builder().withAuthCallBack(new AuthCallback() {
                        @Override
                        public void success(DigitsSession session, String phoneNumber) {

                            SharedPreferences.Editor editor = sharedPreferences1.edit();
                            editor.putBoolean("Login", true);
                            editor.putString("mobno", phone);
                            editor.apply();
                            Intent i = new Intent(Login.this, FeedsActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        }

                        @Override
                        public void failure(DigitsException error) {
                            Toast.makeText(getApplicationContext(), String.valueOf(error.getErrorCode()), Toast.LENGTH_SHORT).show();
                        }
                    }).withPhoneNumber("+91" + phone);
                    Digits.authenticate(builder.build());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
