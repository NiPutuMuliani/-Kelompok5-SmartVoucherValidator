# Smart Voucher Validator

Aplikasi validator untuk kode voucher dengan aturan validasi kompleks. Sistem ini memvalidasi format kode dan tanggal transaksi untuk memastikan voucher hanya berlaku pada periode tertentu.

## 📋 Ketentuan Kode Voucher

Kode voucher harus memenuhi semua kriteria berikut:

| Posisi | Ketentuan | Contoh |
|--------|-----------|--------|
| **Karakter 1-3** | Wajib "EXT" | EXT... |
| **Karakter 4-6** | 3 digit angka dengan jumlah genap | 246 (2+4+6=12) ✓ |
| **Karakter 7-8** | 2 simbol khusus | @#, !$, %^, &* |
| **Total Panjang** | Tepat 8 karakter | EXT246@# |

### Simbol Khusus yang Diizinkan
```
! @ # $ % ^ & * ( ) _ + - = [ ] { } | ; : ' " < > ? / . ~ `
```

### Contoh Kode Valid
- `EXT246@#` → 2+4+6=12 (genap) ✓
- `EXT468!$` → 4+6+8=18 (genap) ✓
- `EXT200%-` → 2+0+0=2 (genap) ✓
- `EXT404!@` → 4+0+4=8 (genap) ✓

### Contoh Kode Tidak Valid
- `EXT135@#` → 1+3+5=9 (ganjil) ✗
- `ABC246@#` → Prefix bukan "EXT" ✗
- `EXT246ab` → Simbol bukan khusus ✗
- `EXT24@` → Panjang hanya 6 karakter ✗

## 📅 Validasi Tanggal Transaksi

Kode voucher hanya **berlaku jika tanggal transaksi berada di rentang tanggal 10-20 (inklusif)** pada setiap bulan.

- ✓ Valid: 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20
- ✗ Tidak Valid: 1-9 dan 21-31

Rentang ini berlaku untuk **semua bulan** (Januari-Desember).

## 🚀 Penggunaan

### 1. Validasi Kode dan Tanggal Bersamaan

```java
import java.time.LocalDate;

// Validasi kode dan tanggal
boolean isValid = SmartVoucherValidator.validateVoucher(
    "EXT246@#", 
    LocalDate.of(2026, 5, 15)
);
System.out.println(isValid); // true
```

### 2. Validasi Hanya Format Kode

```java
// Validasi format kode saja
boolean isValid = SmartVoucherValidator.isValidVoucherCode("EXT246@#");
System.out.println(isValid); // true

// Kode tidak valid (digit ganjil)
boolean isValid2 = SmartVoucherValidator.isValidVoucherCode("EXT135@#");
System.out.println(isValid2); // false
```

### 3. Validasi Hanya Tanggal Transaksi

```java
// Validasi tanggal saja
boolean isValid = SmartVoucherValidator.isValidTransactionDate(
    LocalDate.of(2026, 5, 15)
);
System.out.println(isValid); // true

// Tanggal tidak valid (hari 9)
boolean isValid2 = SmartVoucherValidator.isValidTransactionDate(
    LocalDate.of(2026, 5, 9)
);
System.out.println(isValid2); // false
```

### 4. Menggunakan VoucherValidationRequest

```java
// Membuat request object
VoucherValidationRequest request = new VoucherValidationRequest(
    "EXT468!$", 
    LocalDate.of(2026, 5, 20)
);

// Validasi
boolean isValid = SmartVoucherValidator.validateVoucher(request);
System.out.println(isValid); // true
```

## 🛡️ Penanganan Input

Aplikasi ini secara otomatis menangani input yang berpotensi error:

| Input | Hasil | Keterangan |
|-------|-------|-----------|
| `null` | `false` | Input null tidak diizinkan |
| `""` (kosong) | `false` | String kosong tidak valid |
| Kode < 8 karakter | `false` | Panjang tidak sesuai |
| Kode > 8 karakter | `false` | Panjang tidak sesuai |
| Tanggal `null` | `false` | Tanggal null tidak valid |
| Request `null` | `false` | Request null tidak valid |

## 📦 Struktur Project

```
voucher_engine/
├── pom.xml                                    # Maven configuration
├── src/
│   ├── main/java/voucherengine/
│   │   ├── Main.java                         # Demo aplikasi dengan 13 test case
│   │   ├── SmartVoucherValidator.java         # Kelas utama validator
│   │   └── VoucherValidationRequest.java      # POJO untuk request
│   └── test/java/voucherengine/
│       └── SmartVoucherValidatorTest.java     # Unit test (JUnit 5)
└── target/                                   # Build output
```

## 🔧 Kompilasi dan Menjalankan

### Kompilasi Project
```bash
mvn clean compile
```

### Menjalankan Demo
```bash
mvn exec:java -Dexec.mainClass="voucherengine.Main"
```

### Menjalankan Unit Test
```bash
mvn test
```

### Build JAR
```bash
mvn clean package
```

## 📊 Unit Test Coverage

Aplikasi dilengkapi dengan **50+ unit test** yang mengcover:

1. **Validasi Format Kode** (12 test)
   - Valid prefix "EXT"
   - Digit sum genap/ganjil
   - Valid/invalid simbol khusus
   - Panjang karakter

2. **Validasi Tanggal** (10 test)
   - Batas bawah (hari 10)
   - Batas atas (hari 20)
   - Di luar rentang (hari 9, 21)
   - Null handling

3. **Edge Cases** (8 test)
   - Null input
   - Empty string
   - Wrong format
   - Boundary conditions

4. **Integration Test** (5 test)
   - Validasi lengkap dengan berbagai kombinasi
   - Multiple valid codes
   - Multiple valid dates

### Menjalankan Test dengan Detail
```bash
mvn test -Dsurefire.argLine="-v"
```

### Melihat Test Report
Test report akan tersedia di `target/surefire-reports/`

## 💡 Contoh Skenario Penggunaan

### Skenario 1: Transaksi Valid
```java
String voucherCode = "EXT246@#";
LocalDate transactionDate = LocalDate.of(2026, 5, 15);

if (SmartVoucherValidator.validateVoucher(voucherCode, transactionDate)) {
    System.out.println("✓ Voucher berlaku! Promo aktif.");
} else {
    System.out.println("✗ Voucher tidak berlaku.");
}
// Output: ✓ Voucher berlaku! Promo aktif.
```

### Skenario 2: Kode Invalid
```java
String voucherCode = "EXT135@#";  // 1+3+5=9 (ganjil)
LocalDate transactionDate = LocalDate.of(2026, 5, 15);

if (SmartVoucherValidator.validateVoucher(voucherCode, transactionDate)) {
    System.out.println("✓ Voucher berlaku!");
} else {
    System.out.println("✗ Kode voucher tidak valid.");
}
// Output: ✗ Kode voucher tidak valid.
```

### Skenario 3: Tanggal Invalid
```java
String voucherCode = "EXT246@#";
LocalDate transactionDate = LocalDate.of(2026, 5, 25);  // Hari 25

if (SmartVoucherValidator.validateVoucher(voucherCode, transactionDate)) {
    System.out.println("✓ Voucher berlaku!");
} else {
    System.out.println("✗ Tanggal transaksi di luar periode promo (10-20).");
}
// Output: ✗ Tanggal transaksi di luar periode promo (10-20).
```

## 📝 Implementasi Detail

### Validasi Digit Sum
```java
// Mengubah karakter digit menjadi angka dan menjumlahkan
String digitPart = "246";
int sum = (digitPart.charAt(0) - '0') +  // 2
          (digitPart.charAt(1) - '0') +  // 4
          (digitPart.charAt(2) - '0');   // 6
// sum = 12, 12 % 2 == 0 ✓ (genap)
```

### Validasi Simbol Khusus
```java
// Menggunakan regex untuk validasi simbol
Pattern SPECIAL_SYMBOLS = Pattern.compile("^[!@#$%^&*()_+=\\-\\[\\]{}|;:',.<>?/~`]$");
// Setiap karakter harus cocok dengan pattern ini
```

## ✅ Checklist Fitur

- ✅ Validasi format kode (EXT + 3 digit + 2 simbol)
- ✅ Validasi digit sum genap
- ✅ Validasi simbol khusus
- ✅ Validasi tanggal transaksi (10-20)
- ✅ Penanganan null input
- ✅ Penanganan empty string
- ✅ Unit test comprehensive (50+ test)
- ✅ Dokumentasi lengkap
- ✅ Demo aplikasi dengan berbagai skenario
- ✅ Flexible API (metode terpisah untuk setiap validasi)

## 🔍 Troubleshooting

### Kode dikompilasi tapi test error
Pastikan JUnit dependency sudah di-download:
```bash
mvn clean install
```

### Main.java error: LocalDate tidak ditemukan
Pastikan menggunakan Java 8+. Cek versi Java:
```bash
java -version
```

### Pattern regex error untuk simbol khusus
Escape character sudah benar di regex. Jika masih error, gunakan versi dengan character class:
```java
Pattern.compile("[!@#$%^&*()_+=\\-\\[\\]{}|;:',.<>?/~`]")
```

## 📞 Support

Untuk pertanyaan atau improvement, silakan review kode atau jalankan unit test untuk memastikan semua fitur berfungsi dengan baik.

---

**Versi:** 1.0  
**Bahasa:** Java 17  
**Build Tool:** Maven  
**Testing Framework:** JUnit 5
