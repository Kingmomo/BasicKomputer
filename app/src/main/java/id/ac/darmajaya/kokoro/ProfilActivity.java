package id.ac.darmajaya.kokoro;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.ac.darmajaya.kokoro.Model.User;

public class ProfilActivity extends AppCompatActivity {

    TextView email, nama, alamat, notelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_activity);

        email   = (TextView) findViewById(R.id.email);
        nama    = (TextView) findViewById(R.id.nama);
        alamat  = (TextView) findViewById(R.id.alamat);
        notelp  = (TextView) findViewById(R.id.notelp);

        dataprofil();

    }

    public void dataprofil(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(getIntent().getStringExtra("USERID"));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                email.setText(user.getEmail());
                nama.setText(user.getNama());
                alamat.setText(user.getAlamat());
                notelp.setText(user.getNotelp());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
