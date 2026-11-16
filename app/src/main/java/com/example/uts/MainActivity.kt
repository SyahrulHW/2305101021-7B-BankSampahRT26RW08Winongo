package com.example.uts

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "jadwal_sampah_rt26"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()
        requestNotificationPermission()

        val sharedPref = getSharedPreferences("BankSampahPrefs", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("userName", "Warga RT 26") ?: "Warga RT 26"
        val userRole = sharedPref.getString("userRole", "Warga") ?: "Warga"

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val tvRoleBadge = findViewById<TextView>(R.id.tvRoleBadge)
        val tvJadwalTanggal = findViewById<TextView>(R.id.tvJadwalTanggal)
        val tvJadwalJamLokasi = findViewById<TextView>(R.id.tvJadwalJamLokasi)
        val tvStatusAlur = findViewById<TextView>(R.id.tvStatusAlur)
        val layoutAdminPanel = findViewById<LinearLayout>(R.id.layoutAdminPanel)

        val etInputTanggal = findViewById<EditText>(R.id.etInputTanggal)
        val etInputJam = findViewById<EditText>(R.id.etInputJam)
        val btnSimpanJadwal = findViewById<Button>(R.id.btnSimpanJadwal)
        val btnUpdateStatusPengepul = findViewById<Button>(R.id.btnUpdateStatusPengepul)
        val btnTestNotifWarga = findViewById<Button>(R.id.btnTestNotifWarga)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        tvWelcome.text = "Halo, $userName"
        tvRoleBadge.text = "Role: $userRole • RT 26 RW 08 Kel. Winongo"

        if (userRole == "Admin") {
            layoutAdminPanel.visibility = View.VISIBLE
        } else {
            layoutAdminPanel.visibility = View.GONE
        }

        fun loadJadwal() {
            val tanggal = sharedPref.getString("jadwalTanggal", "Minggu, 22 November 2026")
            val jam = sharedPref.getString("jadwalJam", "08.00 - 10.00 WIB")
            val status = sharedPref.getString(
                "statusAlur",
                "Status: Menunggu Pengumpulan di Gudang Rumah Pak RT"
            )

            tvJadwalTanggal.text = tanggal
            tvJadwalJamLokasi.text = "Pukul $jam • Gudang Sementara Rumah Pak RT 26"
            tvStatusAlur.text = status
        }

        loadJadwal()

        btnSimpanJadwal.setOnClickListener {
            val tanggalBaru = etInputTanggal.text.toString().trim()
            val jamBaru = etInputJam.text.toString().trim()

            if (tanggalBaru.isEmpty() || jamBaru.isEmpty()) {
                Toast.makeText(this, "Isi tanggal dan jam terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sharedPref.edit().apply {
                putString("jadwalTanggal", tanggalBaru)
                putString("jadwalJam", jamBaru)
                putString("statusAlur", "Status: Terjadwal (Pengingat H-1 Aktif)")
                apply()
            }

            loadJadwal()
            etInputTanggal.text.clear()
            etInputJam.text.clear()

            kirimNotifikasiH1(tanggalBaru, jamBaru)
            Toast.makeText(this, "Jadwal diperbarui & notifikasi H-1 dikirim!", Toast.LENGTH_SHORT).show()
        }

        btnUpdateStatusPengepul.setOnClickListener {
            sharedPref.edit().apply {
                putString("statusAlur", "Status: Selesai Dipilah & Sudah Dijual ke Pengepul")
                apply()
            }
            loadJadwal()
            Toast.makeText(this, "Status diperbarui: Selesai dijual ke pengepul", Toast.LENGTH_SHORT).show()
        }

        btnTestNotifWarga.setOnClickListener {
            val tanggal = sharedPref.getString("jadwalTanggal", "Minggu, 22 November 2026") ?: ""
            val jam = sharedPref.getString("jadwalJam", "08.00 WIB") ?: ""
            kirimNotifikasiH1(tanggal, jam)
        }

        btnLogout.setOnClickListener {
            sharedPref.edit().putBoolean("isLoggedIn", false).apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun kirimNotifikasiH1(tanggal: String, jam: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Pengingat H-1 Pengumpulan Sampah RT 26")
            .setContentText("Besok ($tanggal, $jam) bawa sampah anorganik terpilah ke Gudang Rumah Pak RT.")
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    "Besok ($tanggal pukul $jam) jadwal pengumpulan sampah anorganik RT 26 RW 08 Winongo. Pastikan plastik, kardus, dan kaleng sudah dipilah dari sampah organik sebelum dibawa ke Gudang Sementara Rumah Pak RT."
                )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(this).notify(101, builder.build())
        } else {
            Toast.makeText(this, "Izinkan notifikasi pada pengaturan aplikasi", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Jadwal Sampah RT 26 Winongo"
            val descriptionText = "Notifikasi pengingat H-1 pengumpulan sampah anorganik"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
        }
    }
}