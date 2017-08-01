package webtek.cse.uem.biswajit.com.requestresuereduce;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class donation extends AppCompatActivity {

    public String donor_name, receiver_token, phnno;
    public String Legacy_SERVER_KEY = "AIzaSyAJvLrNbrQT1ONCbLXjNHHfPrVYVzQZ6NE";
    String mob, post;
    DatabaseReference reference, reference2;
    DetailsFeeds detailsFeeds;
    SharedPreferences sharedPreferences;
    DetailsRegister detailsreg;
    TextView category, description, quantity, noquantity;
    SeekBar seekBar;
    EditText message;
    Button btn;
    String FCM_PUSH_URL = "https://fcm.googleapis.com/fcm/send";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_donation);
        category = (TextView) findViewById(R.id.layout_donation_category);
        description = (TextView) findViewById(R.id.layout_donation_description);
        quantity = (TextView) findViewById(R.id.layout_donation_quantity);
        noquantity = (TextView) findViewById(R.id.layout_donation_quantity_donate);
        seekBar = (SeekBar) findViewById(R.id.layout_donation_quantity_donate_bar);
        message = (EditText) findViewById(R.id.layout_donation_txt);
        btn = (Button) findViewById(R.id.layout_donation_done_btn);
        detailsFeeds = new DetailsFeeds();
        detailsreg = new DetailsRegister();
        Bundle b = getIntent().getExtras();
        mob = b.getString("mob");
        post = b.getString("post");
        mob = mob + post;
        Toast.makeText(getApplicationContext(), mob, Toast.LENGTH_SHORT).show();
        reference = FirebaseDatabase.getInstance().getReference().child("feeds");
        reference.child(mob).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                detailsFeeds = dataSnapshot.getValue(DetailsFeeds.class);
                category.setText("Category : " + detailsFeeds.getCategory());
                description.setText("description : " + detailsFeeds.getItemDonateName());
                quantity.setText("Quantity : " + detailsFeeds.getQuantity().toString());
                receiver_token = detailsFeeds.getReceiver_token();
                int pos = Integer.parseInt(detailsFeeds.getQuantity());
                seekBar.setMax(pos);
                seekBar.setProgress(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        phnno = sharedPreferences.getString("mobno", "A");

        reference2 = FirebaseDatabase.getInstance().getReference().child("register");
        reference2.child(phnno).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detailsreg = dataSnapshot.getValue(DetailsRegister.class);
                donor_name = detailsreg.get_name();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                noquantity.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), message.getText().toString() + noquantity.getText().toString(), Toast.LENGTH_SHORT).show();
                sendFCMPush();
            }
        });


    }

    private void sendFCMPush() {

        String msg = donor_name + " wants to donate.See details.?" + detailsFeeds.getCategory() + "/"
                + detailsFeeds.getItemDonateName() + "/" + detailsFeeds.getQuantity().toString() + "/" + detailsFeeds.getPhoneNo() + "/"+ noquantity.getText().toString()+"/"+message.getText().toString()+"/";
        String title = "RequestReuseReduce";
        String token = receiver_token; //Receiver token

        JSONObject obj = null;
        JSONObject objData = null;
        JSONObject dataobjData = null;

        try {
            obj = new JSONObject();
            objData = new JSONObject();
            objData.put("body", msg);
            objData.put("title", title);
            objData.put("sound", "default");
            objData.put("icon", "icon_name"); //   icon_name image must be there in drawable
            objData.put("tag", token);
            objData.put("priority", "high");

            dataobjData = new JSONObject();
            dataobjData.put("text", msg);
            dataobjData.put("title", title);

            obj.put("to", token);
            //obj.put("priority", "high");

            obj.put("notification", objData);
            obj.put("data", dataobjData);
            Log.e("!_@rj@_@@_PASS:>", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, FCM_PUSH_URL, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("!_@@_SUCESS", response + "");
                        Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("!_@@_Errors--", error + "");
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "key=" + Legacy_SERVER_KEY);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);


    }
}
