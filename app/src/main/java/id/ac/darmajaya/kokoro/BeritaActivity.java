package id.ac.darmajaya.kokoro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;

import id.ac.darmajaya.kokoro.Model.News;


public class BeritaActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    private ImageButton mSearchBtn;
    private EditText mSearchField;
    private FirebaseRecyclerAdapter<News, BeritaActivity.MyViewHolder> adapter;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        
        //SETUP RECYCLER
        recyclerview = (RecyclerView) findViewById(R.id.recyclerV);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);


        starter();
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();
                firebaseUserSearch(searchText);
                adapter.startListening();

            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.apply();


    }

    private void firebaseUserSearch(String searchText) {
        Toast.makeText(BeritaActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query query = FirebaseDatabase.getInstance()
                .getReference("News")
                .orderByChild("judul")
                .startAt(searchText)
                .endAt(searchText + "\uf8ff");

        FirebaseRecyclerOptions<News> options = new FirebaseRecyclerOptions.Builder<News>()
                .setQuery(query, News.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<News, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder( MyViewHolder holder, int position,  final News model) {
                holder.setImage(model.getFoto());
                holder.setTitle(model.getJudul());
                holder.setDes(model.getDeskripsi());
                holder.setkategori("Berita");

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(BeritaActivity.this, DetailBerita.class);
                        intent.putExtra("GAMBAR", model.getFoto());
                        intent.putExtra("DESKRIPSI", model.getDeskripsi());
                        intent.putExtra("JUDUL", model.getJudul());
                        startActivity(intent);
                    }
                });
            }



            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_berita, parent, false);
                return new BeritaActivity.MyViewHolder(view);
            }
        };
        recyclerview.setAdapter(adapter);

    }




    public void starter(){
        Query query = FirebaseDatabase.getInstance()
                .getReference("News")
                .orderByChild("-iddata");

        FirebaseRecyclerOptions<News> options = new FirebaseRecyclerOptions.Builder<News>()
                .setQuery(query, News.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<News, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull final News model) {

                Long timestamp = model.getiddata();
                Calendar cal = Calendar.getInstance(Locale.getDefault());
                cal.setTimeInMillis(timestamp);
                String date = DateFormat.format("dd-MM-yyyy", cal).toString();
                holder.setImage(model.getFoto());
                holder.setTitle(model.getJudul());
                holder.setDes(model.getDeskripsi());
                holder.setkategori("Berita");
                holder.setTanggal(date);


                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String data = model.getFoto();
                        Intent intent = new Intent(BeritaActivity.this, DetailBerita.class);
                        intent.putExtra("GAMBAR", model.getFoto());
                        intent.putExtra("DESKRIPSI", model.getDeskripsi());
                        intent.putExtra("JUDUL", model.getJudul());
                        startActivity(intent);
                    }
                });

            }



            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_berita, parent, false);
                return new BeritaActivity.MyViewHolder(view);
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

        public void setTanggal(String tgl) {
            TextView deskripsi = mView.findViewById(R.id.nama);
            deskripsi.setText(tgl);
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
        adapter.startListening();
    }






}
