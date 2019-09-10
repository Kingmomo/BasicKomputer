package id.ac.darmajaya.kokoro;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;
import id.ac.darmajaya.kokoro.Model.Transaksi;

public class TransaksiActivity extends AppCompatActivity {

    TextView logout, profil;
    RecyclerView recyclerview;
    private ImageButton mSearchBtn;
    private EditText mSearchField;
    DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter<Transaksi, MyViewHolder> adapter;
    FirebaseAuth mAuth;
    String userid, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaksi_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerV);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }




        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();


//                        Toast.makeText(TransaksiActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });




        //SETUP RECYCLER
        recyclerview = (RecyclerView) findViewById(R.id.recyclerV);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);


        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();
                firebaseUserSearch(searchText);
                adapter.startListening();

            }
        });

    }

    private void firebaseUserSearch(String searchText) {
        Toast.makeText(TransaksiActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query query = FirebaseDatabase.getInstance()
                .getReference("TransaksiServices")
                .orderByChild("no_transaksi")
                .startAt(searchText)
                .endAt(searchText + "\uf8ff");


        FirebaseRecyclerOptions<Transaksi> options = new FirebaseRecyclerOptions.Builder<Transaksi>()
                .setQuery(query, Transaksi.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Transaksi, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder( MyViewHolder holder, int position,  final Transaksi model) {
                holder.setImage(model.getNo_transaksi());
                holder.setTitle(model.getBarang());
                holder.setNama(model.getNama());
                holder.setkategori("Servis");

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TransaksiActivity.this, DetailTransaksi.class);
                        intent.putExtra("BARANG", model.getBarang());
                        intent.putExtra("DETAIL", model.getDetail());
                        intent.putExtra("EMAIL", model.getEmail());
                        intent.putExtra("NAMA", model.getNama());
                        intent.putExtra("SETUJU", model.getSetuju());
                        intent.putExtra("TELPON", model.getTelpon());
                        intent.putExtra("NOTRANSAKSI", model.getNo_transaksi());
                        intent.putExtra("KETERANGAN", model.getKeterangan());
                        intent.putExtra("NOMINAL", model.getBiaya());
                        startActivity(intent);

                    }
                });
            }



            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.transaksi_content_rcv, parent, false);
                return new TransaksiActivity.MyViewHolder(view);
            }
        };
        recyclerview.setAdapter(adapter);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

        }



        public void setTitle(String name) {
            TextView title = mView.findViewById(R.id.judul);
            title.setText(name);
        }

        public void setDes(String desc) {
            TextView deskripsi = mView.findViewById(R.id.deskripsi);
            deskripsi.setText(desc);
        }

        public void setkategori(String kategori) {
            TextView deskripsi = mView.findViewById(R.id.kategori);
            deskripsi.setText(kategori);
        }

        public void setNama(String nama) {
            TextView deskripsi = mView.findViewById(R.id.nama);
            deskripsi.setText(nama);
        }

        public void setImage(String image) {
            ImageView gambar = mView.findViewById(R.id.media_image);
            Picasso.get()
                    .load(image)
                    .into(gambar);



        }


    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
