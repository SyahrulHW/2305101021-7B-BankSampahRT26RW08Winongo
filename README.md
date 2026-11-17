# Pengumpulan UTS Pemrograman Mobile

## Identitas Mahasiswa

- Nama: Syahrul Haris Wijaya
- NIM: 2305101021
- Kelas: 7B
- Program Studi: Teknik Informatika (Universitas PGRI Madiun)
- Mata Kuliah: Pemrograman Bergerak
- Tahun Akademik: 2026

## Deskripsi

Repository ini berisi source code, dokumentasi, dan hasil implementasi
aplikasi mobile untuk pengerjaan Ujian Tengah Semester (UTS)
mata kuliah Pemrograman Mobile.

## Deskripsi Pengerjaan UTS

Aplikasi mobile **Bank Sampah RT 26 RW 08 Kelurahan Winongo** dikembangkan sebagai solusi digital untuk mempermudah koordinasi dan pengelolaan kegiatan bank sampah di lingkungan warga RT 26 RW 08 Kelurahan Winongo, Kota Madiun. Aplikasi ini membantu pengurus (Admin) dalam mengatur serta memperbarui jadwal pengambilan sampah anorganik secara terpusat, sekaligus memudahkan warga untuk memantau jadwal penjemputan, mendapatkan simulasi pengingat notifikasi H-1, serta mengakses panduan pemilahan jenis sampah anorganik langsung melalui perangkat Android.

## Teknologi yang Digunakan

- Bahasa Pemrograman: Kotlin
- Framework: Android SDK (Native Android dengan XML Layout & Material Components)
- Tools: Android Studio, Gradle, Git & GitHub
- Database: Penyimpanan Lokal menggunakan `SharedPreferences` (manajemen sesi login dan data jadwal)

## Fitur yang Diimplementasikan

1. **Autentikasi & Manajemen Role Pengguna (`LoginActivity`)**: Sistem login dengan pemisahan hak akses antara peran **Warga** dan **Admin (Pengurus Bank Sampah)** menggunakan penyimpanan sesi lokal.
2. **Dashboard & Informasi Jadwal Pengambilan Sampah (`MainActivity`)**: Menampilkan jadwal operasional pengambilan sampah anorganik di lingkungan RT 26 RW 08 Kelurahan Winongo secara terstruktur.
3. **Pembaruan Jadwal oleh Admin**: Fitur khusus bagi Admin untuk mengubah atau memperbarui tanggal dan keterangan jadwal penjemputan sampah yang langsung tersimpan di perangkat.
4. **Simulasi Notifikasi Pengingat H-1**: Fitur pengingat notifikasi bagi warga sebelum jadwal pengambilan sampah berlangsung agar sampah anorganik siap disetorkan.
5. **Panduan Pemilahan Sampah Anorganik**: Informasi edukatif mengenai kategori dan cara memilah sampah anorganik (plastik, kertas/kardus, logam, kaca) sebelum proses penjemputan.

## Struktur Folder

- `2305101021-7B-BankSampahRT26RW08Winongo/`: Source code dan file pendukung UTS (berisi proyek Android Studio beserta modul `app/`, `LoginActivity.kt`, `MainActivity.kt`, layout XML, serta konfigurasi Gradle).
- `screenshots/`: Bukti visual hasil implementasi aplikasi (tampilan halaman login, dashboard warga, dashboard admin, notifikasi, dan panduan pemilahan sampah).

## Persyaratan Menjalankan Aplikasi

- **Sistem Operasi**: Windows 10/11, macOS, atau Linux (64-bit)
- **IDE**: Android Studio (versi Iguana / Jellyfish / Ladybug atau yang lebih baru)
- **JDK**: Java Development Kit (JDK) 17
- **Android SDK**:
  - Minimum SDK: API 24 (Android 7.0 Nougat)
  - Target / Compile SDK: API 34+
- **Perangkat Pengujian**: Android Virtual Device (Emulator) atau perangkat fisik Android dengan fitur *USB Debugging* aktif.

## Cara Menjalankan Aplikasi

1. **Clone Repository**:
   ```bash
   git clone [https://github.com/SyahrulHW/2305101021-7B-BankSampahRT26RW08Winongo.git](https://github.com/SyahrulHW/2305101021-7B-BankSampahRT26RW08Winongo.git) 
2. Buka Android Studio, pilih File > Open, lalu pilih folder project di dalam `2305101021-7B-BankSampahRT26RW08Winongo/`.
3. Tunggu proses Gradle sync selesai (klik Sync Now jika diminta).
4. Pilih perangkat di bagian atas: emulator (Device Manager) atau HP dengan USB Debugging aktif.
5. Klik tombol Run (segitiga hijau).
