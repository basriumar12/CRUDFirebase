package com.basri.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditActivity extends AppCompatActivity {

    EditText edtKegiatan, edtDesk;
    Button btnEdit, btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edtDesk = findViewById(R.id.edt_deskripsi);
        edtKegiatan = findViewById(R.id.edt_kegiatan);
         btnDelete = findViewById(R.id.btn_delete);
         btnEdit = findViewById(R.id.btn_edit);

        final Intent ambilData = getIntent();
        edtKegiatan.setText(ambilData.getStringExtra("KEY_KEGIATAN"));
        edtDesk.setText(ambilData.getStringExtra("KEY_DESK"));

        final String key = ambilData.getStringExtra("KEY_KEY");
        Log.e("TAG","key "+key);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("todo")
                        .child(key)
                        ;

                myRef.removeValue() .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        edtDesk.setText("");
                        edtKegiatan.setText("");
                        Toast.makeText(EditActivity.this,
                                "data berhasil di hapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kegiatan = edtKegiatan.getText().toString();
                String desk = edtDesk.getText().toString();

                //validasi
                if (kegiatan.equals("") || desk.equals("")) {
                    Toast.makeText(EditActivity.this,
                            "data tidak bisa kosong", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("todo")
                            .child(key)
                            ;

                    myRef.setValue(new Kegiatan(kegiatan,desk))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    edtDesk.setText("");
                                    edtKegiatan.setText("");
                                    Toast.makeText(EditActivity.this,
                                            "data berhasil di edit", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditActivity.this,
                                            "data gagal di edit", Toast.LENGTH_SHORT).show();

                                }
                            })
                    ;

                }
            }
        });
    }
}