package com.basri.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="TAG" ;
    TextView tvdata;

    EditText edtKegiatan, edtDesk;
    Button btnSimpan;
    RecyclerView rvData;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtDesk = findViewById(R.id.edt_deskripsi);
        edtKegiatan = findViewById(R.id.edt_kegiatan);
        btnSimpan = findViewById(R.id.btn_simpan);
        rvData = findViewById(R.id.rv_data);
        progressBar = findViewById(R.id.progress_circular);

    // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.e(TAG, "Value is: " + value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });

        rvData.setLayoutManager(new LinearLayoutManager(this));

        inputData();
        getData();
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("todo");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Kegiatan> dataArrayKegiatan = new ArrayList<>();

               //perulangan
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                  Kegiatan  dataKegiatan = snapshot.getValue(Kegiatan.class);

                  dataKegiatan.setKey(snapshot.getKey());
                    dataArrayKegiatan.add(dataKegiatan);
                }


                progressBar.setVisibility(View.GONE);
                //buat objek adapter data
                AdapterData adapterData = new AdapterData(dataArrayKegiatan,
                        MainActivity.this);
                rvData.setAdapter(adapterData);






            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void inputData() {
        //button klik
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kegiatan = edtKegiatan.getText().toString();
                String desk = edtDesk.getText().toString();

                //validasi
                if (kegiatan.equals("") || desk.equals("")){
                    Toast.makeText(MainActivity.this,
                            "data tidak bisa kosong", Toast.LENGTH_SHORT).show();
                }else {

                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("todo");

                    myRef.push().setValue(
                            new Kegiatan(kegiatan,desk)
                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            edtDesk.setText("");
                            edtKegiatan.setText("");

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this,
                                    "data berhasil di isi", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this,
                                    "data gagal di isi", Toast.LENGTH_SHORT).show();

                        }
                    })
                    ;


                }

            }
        });
    }
}