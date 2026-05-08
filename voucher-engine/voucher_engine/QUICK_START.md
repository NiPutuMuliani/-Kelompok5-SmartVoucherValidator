## 🚀 Quick Start Guide - Smart Voucher Validator

### Struktur Project

```
c:\voucher_engine\
├── pom.xml                           # Maven configuration (Java 17, JUnit 5)
├── README.md                         # Dokumentasi lengkap (dalam Bahasa Indonesia)
├── build.bat                         # Build script untuk Command Prompt
├── build.ps1                         # Build script untuk PowerShell
│
├── src/
│   ├── main/java/voucherengine/
│   │   ├── Main.java                # Demo aplikasi + 13 test case
│   │   ├── SmartVoucherValidator.java # Kelas utama validator
│   │   └── VoucherValidationRequest.java # POJO request object
│   │
│   └── test/java/voucherengine/
│       └── SmartVoucherValidatorTest.java # Unit test (50+ test cases, JUnit 5)
│
└── target/
    └── classes/voucherengine/
        ├── Main.class
        ├── SmartVoucherValidator.class
        └── VoucherValidationRequest.class
```

### ⚡ Cara Menjalankan

#### 1. Jalankan Demo Aplikasi
```powershell
cd c:\voucher_engine
java -cp target\classes voucherengine.Main
```

#### 2. Menggunakan Build Script
```powershell
# PowerShell
cd c:\voucher_engine
.\build.ps1

# Command Prompt
cd c:\voucher_engine
build.bat
```

### 📝 Contoh Penggunaan dalam Kode

#### Validasi Kode dan Tanggal Bersamaan
```java
import java.time.LocalDate;
import voucherengine.SmartVoucherValidator;

boolean isValid = SmartVoucherValidator.validateVoucher(
    "EXT246@#", 
    LocalDate.of(2026, 5, 15)
);
System.out.println(isValid); // Output: true
```

#### Validasi Hanya Format Kode
```java
boolean isValid = SmartVoucherValidator.isValidVoucherCode("EXT246@#");
System.out.println(isValid); // Output: true (2+4+6=12, genap)

boolean isInvalid = SmartVoucherValidator.isValidVoucherCode("EXT135@#");
System.out.println(isInvalid); // Output: false (1+3+5=9, ganjil)
```

#### Validasi Hanya Tanggal
```java
boolean isValid = SmartVoucherValidator.isValidTransactionDate(
    LocalDate.of(2026, 5, 15)
);
System.out.println(isValid); // Output: true (hari 15 dalam rentang 10-20)
```

#### Menggunakan Request Object
```java
VoucherValidationRequest request = new VoucherValidationRequest(
    "EXT468!$", 
    LocalDate.of(2026, 5, 20)
);
boolean isValid = SmartVoucherValidator.validateVoucher(request);
System.out.println(isValid); // Output: true
```

### ✅ Validasi Rules

#### Format Kode (8 karakter total)
| Posisi | Ketentuan | Contoh |
|--------|-----------|--------|
| 1-3 | Harus "EXT" | **EXT**... |
| 4-6 | 3 digit dengan jumlah **genap** | ...2**4**6... (2+4+6=12) |
| 7-8 | 2 simbol khusus | ...@# |

#### Simbol Khusus Valid
`! @ # $ % ^ & * ( ) _ + - = [ ] { } | ; : ' " < > ? / . ~ `` `

#### Rentang Tanggal Valid
- **Hari 10-20** (inklusif) pada setiap bulan
- Berlaku untuk semua bulan (Januari-Desember)
- Tahun apapun

### 🧪 Test Cases yang Tersedia

Demo aplikasi (Main.java) mencakup 13 test case:
1. ✓ Valid voucher pada tanggal valid
2. ✓ Valid voucher pada tanggal tidak valid
3. ✓ Invalid prefix (bukan EXT)
4. ✓ Digit sum ganjil
5. ✓ Invalid symbols
6. ✓ Null input
7. ✓ Empty string
8. ✓ Wrong length
9. ✓ Using VoucherValidationRequest
10. ✓ Tanggal awal rentang valid (10)
11. ✓ Tanggal akhir rentang valid (20)
12. ✓ Validasi hanya format kode
13. ✓ Validasi hanya tanggal transaksi

Unit test (SmartVoucherValidatorTest.java) mencakup 50+ test case termasuk:
- Valid/invalid format
- Edge cases (null, empty)
- Digit sum calculation
- Date range validation
- Integration tests

### 🔑 Key Methods

```java
// Validasi kode dan tanggal bersamaan
public static boolean validateVoucher(String voucherCode, LocalDate transactionDate)

// Validasi hanya format kode
public static boolean isValidVoucherCode(String voucherCode)

// Validasi hanya tanggal transaksi
public static boolean isValidTransactionDate(LocalDate transactionDate)

// Validasi menggunakan request object
public static boolean validateVoucher(VoucherValidationRequest request)
```

### 📦 Dependencies

- **Java:** 21 (compatible dengan 17+)
- **JUnit:** 5.9.3 (untuk unit testing)
- **Build Tool:** Maven (optional, bisa compile manual)

### 🎯 Contoh Output

```
=== Smart Voucher Validator ===

Test Case 1: Valid Voucher pada Tanggal Valid
Kode: EXT246@# | Tanggal: 2026-05-15
Hasil Validasi: true
Penjelasan: 2+4+6=12 (genap), tanggal 15 dalam rentang 10-20

Test Case 2: Valid Voucher pada Tanggal Tidak Valid
Kode: EXT135!@ | Tanggal: 2026-05-25
Hasil Validasi: false
Penjelasan: 1+3+5=9 (ganjil), tanggal 25 di luar rentang 10-20

[... 11 test cases lebih ...]
```

### 🐛 Troubleshooting

#### Error: Could not find or load main class
**Solusi:** Pastikan sudah compile ke `target/classes`
```powershell
javac -d target/classes "c:/voucher_engine/src/main/java/voucherengine/Main.java" `
    "c:/voucher_engine/src/main/java/voucherengine/SmartVoucherValidator.java" `
    "c:/voucher_engine/src/main/java/voucherengine/VoucherValidationRequest.java"
```

#### Error: Java version mismatch
**Solusi:** Cek versi Java yang digunakan
```powershell
java -version
```

#### Error: JUnit not found (saat menjalankan unit test)
**Solusi:** Gunakan Maven untuk menjalankan test
```bash
mvn test
```

### 📚 Dokumentasi Lengkap

Lihat [README.md](README.md) untuk dokumentasi lengkap dalam Bahasa Indonesia yang mencakup:
- Ketentuan kode voucher detail
- Validasi tanggal transaksi
- Penanganan input null/kosong
- 13 contoh skenario penggunaan
- Penjelasan implementasi detail
- Coverage unit test

### 💡 Tips & Tricks

1. **Kombinasi Validasi**
   ```java
   // Cek format kode dulu, baru cek tanggal
   if (isValidVoucherCode(code)) {
       if (isValidTransactionDate(date)) {
           // Process voucher
       }
   }
   ```

2. **Generate Valid Codes**
   ```java
   // Format: EXT + 3 digit genap sum + 2 special symbols
   String[] validCodes = {
       "EXT000@#",  // 0+0+0=0 (genap)
       "EXT246@#",  // 2+4+6=12 (genap)
       "EXT468!$",  // 4+6+8=18 (genap)
       "EXT888%^"   // 8+8+8=24 (genap)
   };
   ```

3. **Batch Validation**
   ```java
   List<String> codes = Arrays.asList("EXT246@#", "EXT135@#", "EXT468!$");
   LocalDate validDate = LocalDate.of(2026, 5, 15);
   
   codes.forEach(code -> {
       boolean isValid = SmartVoucherValidator.validateVoucher(code, validDate);
       System.out.println(code + ": " + (isValid ? "✓ Valid" : "✗ Invalid"));
   });
   ```

---

**Versi:** 1.0 | **Dibuat:** 8 Mei 2026 | **Bahasa:** Indonesian
