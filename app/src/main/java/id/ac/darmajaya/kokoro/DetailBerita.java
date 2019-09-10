package id.ac.darmajaya.kokoro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailBerita extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        String gambarr = getIntent().getStringExtra("GAMBAR");
        String judul = getIntent().getStringExtra("JUDUL");

        String deskripsi = getIntent().getStringExtra("DESKRIPSI");
        TextView deskripsii = (TextView) findViewById(R.id.deskripsi);
        ImageView gambar = (ImageView) findViewById(R.id.gambar);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(judul);

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deskripsii.setText(deskripsi);


        Picasso.get()
                .load(gambarr)
                .into(gambar);

    }
}
