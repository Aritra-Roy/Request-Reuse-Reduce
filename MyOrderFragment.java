package webtek.cse.uem.biswajit.com.requestresuereduce;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment implements UploadAndDownloadData {

    ListView listView;
    DatabaseReference databaseReference;
    ArrayList<DetailsFeeds> list;
    DetailsFeeds feeds = new DetailsFeeds();
    SharedPreferences sharedPreferences;
    String phn;
    CustomAdapter customAdapter;
    View rootview;


    public MyOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_my_order, container, false);

        initialize();
        return rootview;
    }

    private void initialize() {
        listView = (ListView) rootview.findViewById(R.id.myorder_list);
        sharedPreferences = getActivity().getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        phn = sharedPreferences.getString("mobno", "A");
        list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("feeds");
        customAdapter = new CustomAdapter(list, getActivity(), "Myorder");
        listView.setAdapter(customAdapter);
        DownloadData();


    }

    @Override
    public void UploadData() {

    }

    @Override
    public void DownloadData() {

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                feeds = dataSnapshot.getValue(DetailsFeeds.class);
                if ((feeds.getPhoneNo().equals(phn)))
                    list.add(feeds);
                customAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                feeds = dataSnapshot.getValue(DetailsFeeds.class);
                list.remove(feeds);
                customAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
