package webtek.cse.uem.biswajit.com.requestresuereduce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Main_Activity extends AppCompatActivity {
    Toolbar mtool;
    Button b_ngo, b_user;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        initialize();
        mtool.setTitle("Request Reuse Reduce");
        if (sharedPreferences.getInt("value", 0) >= 1) {
            Intent intent = new Intent(Main_Activity.this, Login.class);
            startActivity(intent);
        }
        b_ngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("value", 1);
                editor.apply();
                Intent i = new Intent(Main_Activity.this, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
        b_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("value", 2);
                editor.apply();
                Intent i = new Intent(Main_Activity.this, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
        });


    }

    private void initialize() {

        mtool = (Toolbar) findViewById(R.id.toolbar);
        b_ngo = (Button) findViewById(R.id.button_ngo);
        b_user = (Button) findViewById(R.id.button_user);
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
    }


}
