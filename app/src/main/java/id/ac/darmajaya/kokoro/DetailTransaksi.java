package id.ac.darmajaya.kokoro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;

public class DetailTransaksi extends AppCompatActivity {
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaksi_detail);
        String namaa = getIntent().getStringExtra("NAMA");
        String emaill = getIntent().getStringExtra("EMAIL");
        String telponn = getIntent().getStringExtra("TELPON");
        String barangg = getIntent().getStringExtra("BARANG");
        String detaill = getIntent().getStringExtra("DETAIL");
        String keterangann = getIntent().getStringExtra("KETERANGAN");
        final String setuju = getIntent().getStringExtra("SETUJU");

        TextView nama = (TextView) findViewById(R.id.nama);
        TextView email  = (TextView) findViewById(R.id.email);
        TextView telp = (TextView) findViewById(R.id.notelp);
        TextView jenis = (TextView) findViewById(R.id.jenis);
        TextView deskripsi = (TextView) findViewById(R.id.deskripsi);
        TextView keterangan = (TextView) findViewById(R.id.status);
        Button btnkonfirmasi = (Button) findViewById(R.id.konfirmasi);

        nama.setText(namaa);
        email.setText(emaill);
        telp.setText(telponn);
        jenis.setText(barangg);
        deskripsi.setText(detaill);

        if(!keterangann.equals("1")){
            keterangan.setText("Masih Dalam Proses Perbaikan/Pemeriksaaan");
        }
        else {
            keterangan.setText("Barang Sudah Bisa Diambil Di Toko");

        }


        btnkonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perintah(Integer.parseInt(setuju));
            }
        });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        token = task.getResult().getToken();


//                       Toast.makeText(TransaksiActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });



    }

    private void perintah (int input){
        switch (input) {
            case 0:
                String notransaksi = getIntent().getStringExtra("NOTRANSAKSI");
                showUpdateDeleteDialog(notransaksi);
                break;
            case 1:
                Toasty.success(DetailTransaksi.this, "Konfirmasi Setuju Sudah Di Lakukan", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toasty.error(DetailTransaksi.this, "Konfirmasi Pembatalan Sudah Di Lakukan", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    private void showUpdateDeleteDialog(final String notransaksi) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonKembali = (Button) dialogView.findViewById(R.id.buttonKembali);
        final Button btnSetuju = (Button) dialogView.findViewById(R.id.buttonsetuju);
        final Button btnBatal = (Button) dialogView.findViewById(R.id.buttonbatal);

        dialogBuilder.setTitle("-Pilih Konfirmasi Servis-");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.cancel();
            }
        });

        btnSetuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TransaksiServices").child(notransaksi).child("setuju");
                databaseReference.setValue("1");
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("TransaksiServices").child(notransaksi).child("token");
                databaseReference1.setValue(token);
                finish();

            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TransaksiServices").child(notransaksi).child("setuju");
                databaseReference.setValue("2");
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("TransaksiServices").child(notransaksi).child("token");
                databaseReference1.setValue(token);
                finish();
            }
        });
    }

}