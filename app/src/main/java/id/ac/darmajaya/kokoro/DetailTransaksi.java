package id.ac.darmajaya.kokoro;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.text.NumberFormat;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class DetailTransaksi extends AppCompatActivity {
    String token;
    private int currentNotificationID = 0;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private Bitmap icon;
    NotificationHelper notificationHelper;

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
        String money = getIntent().getStringExtra("NOMINAL");
        final String setuju = getIntent().getStringExtra("SETUJU");

        TextView nama = (TextView) findViewById(R.id.nama);
        TextView email = (TextView) findViewById(R.id.email);
        TextView telp = (TextView) findViewById(R.id.notelp);
        TextView jenis = (TextView) findViewById(R.id.jenis);
        TextView deskripsi = (TextView) findViewById(R.id.deskripsi);
        TextView keterangan = (TextView) findViewById(R.id.status);
        TextView nominal = (TextView) findViewById(R.id.nominal);
        Button btnkonfirmasi = (Button) findViewById(R.id.konfirmasi);
        Locale localeID = new Locale("in", "ID");

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        icon = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher_foreground);

        notificationHelper = new NotificationHelper(this);


        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        nominal.setText((formatRupiah.format(Integer.parseInt(String.valueOf(money)))));

        nama.setText(namaa);
        email.setText(emaill);
        telp.setText(telponn);
        jenis.setText(barangg);
        deskripsi.setText(detaill);

        if (!keterangann.equals("1")) {
            keterangan.setText("Masih Dalam Proses Perbaikan/Pemeriksaaan");
        } else {
            keterangan.setText("Barang Sudah Bisa Diambil Di Toko");

        }


        if (Integer.parseInt(setuju) == 1) {
            btnkonfirmasi.setEnabled(false);
            btnkonfirmasi.setBackgroundColor(Color.parseColor("#6abf69"));
            btnkonfirmasi.setText("Konfirmasi Setuju Sukses");
        } else if (Integer.parseInt(setuju) == 2) {
            btnkonfirmasi.setEnabled(false);
            btnkonfirmasi.setBackgroundColor(Color.parseColor("#d32f2f"));
            btnkonfirmasi.setText("Konfirmasi Batal Sukses");
        } else {
            btnkonfirmasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    perintah(Integer.parseInt(setuju));
                }
            });
        }


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

    private void perintah(int input) {
        switch (input) {
            case 0:
                String notransaksi = getIntent().getStringExtra("NOTRANSAKSI");
                showUpdateDeleteDialog(notransaksi);
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
                Toasty.success(DetailTransaksi.this, "Konfirmasi Setuju Sudah Di Lakukan", Toast.LENGTH_LONG).show();
                b.dismiss();
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
                Toasty.error(DetailTransaksi.this, "Konfirmasi Pembatalan Sudah Di Lakukan", Toast.LENGTH_LONG).show();
                notificationHelper.createNotification("Transaksi Dibatalkan", "Barang harap diambil dalam waktu 24 jam, jika terdapat kerusakan bukan tanggung jawab kami");
                b.dismiss();
                finish();
            }
        });
    }

    private void sendNotification() {
        Intent notificationIntent = new Intent(DetailTransaksi.this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(DetailTransaksi.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(contentIntent);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;

        currentNotificationID++;
        int notificationId = currentNotificationID;
        if (notificationId == Integer.MAX_VALUE - 1)
            notificationId = 0;

        notificationManager.notify(notificationId, notification);
    }



    private void setDataForExpandLayoutNotification() {
        String notificationTitle = getString(R.string.app_name);
        String notificationText = "Hello..This is a Notification Test";

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setLargeIcon(icon)
                .setContentTitle(notificationTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
                .setContentText(notificationText);

        sendNotification();
    }




}