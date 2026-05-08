package voucherengine;

import java.time.LocalDate;

/**
 * Aplikasi Smart Voucher Validator
 * Demonstrasi penggunaan validator dengan berbagai skenario
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Smart Voucher Validator ===\n");
        
        // Test Case 1: Valid voucher pada tanggal yang valid
        System.out.println("Test Case 1: Valid Voucher pada Tanggal Valid");
        String validCode = "EXT246@#";
        LocalDate validDate = LocalDate.of(2026, 5, 15); // Tanggal 15
        boolean result1 = SmartVoucherValidator.validateVoucher(validCode, validDate);
        System.out.println("Kode: " + validCode + " | Tanggal: " + validDate);
        System.out.println("Hasil Validasi: " + result1);
        System.out.println("Penjelasan: 2+4+6=12 (genap), tanggal 15 dalam rentang 10-20\n");
        
        // Test Case 2: Valid voucher tapi tanggal di luar rentang
        System.out.println("Test Case 2: Valid Voucher pada Tanggal Tidak Valid");
        String validCode2 = "EXT135!@";
        LocalDate invalidDate = LocalDate.of(2026, 5, 25); // Tanggal 25 (di luar rentang)
        boolean result2 = SmartVoucherValidator.validateVoucher(validCode2, invalidDate);
        System.out.println("Kode: " + validCode2 + " | Tanggal: " + invalidDate);
        System.out.println("Hasil Validasi: " + result2);
        System.out.println("Penjelasan: 1+3+5=9 (ganjil), tanggal 25 di luar rentang 10-20\n");
        
        // Test Case 3: Invalid prefix (bukan EXT)
        System.out.println("Test Case 3: Invalid Prefix");
        String invalidCode1 = "ABC246@#";
        LocalDate validDate2 = LocalDate.of(2026, 5, 12);
        boolean result3 = SmartVoucherValidator.validateVoucher(invalidCode1, validDate2);
        System.out.println("Kode: " + invalidCode1 + " | Tanggal: " + validDate2);
        System.out.println("Hasil Validasi: " + result3);
        System.out.println("Penjelasan: Prefix bukan EXT\n");
        
        // Test Case 4: Valid prefix tapi digit tidak genap
        System.out.println("Test Case 4: Digit Sum Ganjil");
        String invalidCode2 = "EXT135##";
        LocalDate validDate3 = LocalDate.of(2026, 5, 10);
        boolean result4 = SmartVoucherValidator.validateVoucher(invalidCode2, validDate3);
        System.out.println("Kode: " + invalidCode2 + " | Tanggal: " + validDate3);
        System.out.println("Hasil Validasi: " + result4);
        System.out.println("Penjelasan: 1+3+5=9 (ganjil)\n");
        
        // Test Case 5: Invalid symbols (huruf bukan simbol khusus)
        System.out.println("Test Case 5: Invalid Symbols");
        String invalidCode3 = "EXT246ab";
        LocalDate validDate4 = LocalDate.of(2026, 5, 14);
        boolean result5 = SmartVoucherValidator.validateVoucher(invalidCode3, validDate4);
        System.out.println("Kode: " + invalidCode3 + " | Tanggal: " + validDate4);
        System.out.println("Hasil Validasi: " + result5);
        System.out.println("Penjelasan: 'ab' bukan simbol khusus\n");
        
        // Test Case 6: Null input
        System.out.println("Test Case 6: Null Input");
        boolean result6 = SmartVoucherValidator.validateVoucher(null, validDate);
        System.out.println("Kode: null | Tanggal: " + validDate);
        System.out.println("Hasil Validasi: " + result6);
        System.out.println("Penjelasan: Input null tidak diizinkan\n");
        
        // Test Case 7: Empty string
        System.out.println("Test Case 7: Empty String");
        boolean result7 = SmartVoucherValidator.validateVoucher("", validDate);
        System.out.println("Kode: '' | Tanggal: " + validDate);
        System.out.println("Hasil Validasi: " + result7);
        System.out.println("Penjelasan: Input kosong tidak diizinkan\n");
        
        // Test Case 8: Wrong length
        System.out.println("Test Case 8: Wrong Length");
        String invalidCode4 = "EXT24@";
        boolean result8 = SmartVoucherValidator.validateVoucher(invalidCode4, validDate);
        System.out.println("Kode: " + invalidCode4 + " | Tanggal: " + validDate);
        System.out.println("Hasil Validasi: " + result8);
        System.out.println("Penjelasan: Panjang hanya 6 karakter, harus 8\n");
        
        // Test Case 9: Using VoucherValidationRequest
        System.out.println("Test Case 9: Menggunakan VoucherValidationRequest");
        VoucherValidationRequest request = new VoucherValidationRequest("EXT468!$", LocalDate.of(2026, 5, 20));
        boolean result9 = SmartVoucherValidator.validateVoucher(request);
        System.out.println("Request: " + request);
        System.out.println("Hasil Validasi: " + result9);
        System.out.println("Penjelasan: 4+6+8=18 (genap), tanggal 20 dalam rentang 10-20\n");
        
        // Test Case 10: Tanggal awal rentang valid
        System.out.println("Test Case 10: Tanggal Awal Rentang Valid (10)");
        String validCode3 = "EXT200%-";
        LocalDate validDate5 = LocalDate.of(2026, 5, 10);
        boolean result10 = SmartVoucherValidator.validateVoucher(validCode3, validDate5);
        System.out.println("Kode: " + validCode3 + " | Tanggal: " + validDate5);
        System.out.println("Hasil Validasi: " + result10);
        System.out.println("Penjelasan: 2+0+0=2 (genap), tanggal 10 adalah awal rentang valid\n");
        
        // Test Case 11: Tanggal akhir rentang valid
        System.out.println("Test Case 11: Tanggal Akhir Rentang Valid (20)");
        String validCode4 = "EXT808^&";
        LocalDate validDate6 = LocalDate.of(2026, 5, 20);
        boolean result11 = SmartVoucherValidator.validateVoucher(validCode4, validDate6);
        System.out.println("Kode: " + validCode4 + " | Tanggal: " + validDate6);
        System.out.println("Hasil Validasi: " + result11);
        System.out.println("Penjelasan: 8+0+8=16 (genap), tanggal 20 adalah akhir rentang valid\n");
        
        // Test Case 12: Validasi hanya kode
        System.out.println("Test Case 12: Validasi Hanya Format Kode");
        System.out.println("Kode 'EXT404!@' valid: " + SmartVoucherValidator.isValidVoucherCode("EXT404!@"));
        System.out.println("Kode 'EXT405!@' valid: " + SmartVoucherValidator.isValidVoucherCode("EXT405!@"));
        System.out.println("Penjelasan: 4+0+4=8 (genap), 4+0+5=9 (ganjil)\n");
        
        // Test Case 13: Validasi hanya tanggal
        System.out.println("Test Case 13: Validasi Hanya Tanggal Transaksi");
        System.out.println("Tanggal 10 Mei valid: " + SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2026, 5, 10)));
        System.out.println("Tanggal 9 Mei valid: " + SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2026, 5, 9)));
        System.out.println("Tanggal 21 Mei valid: " + SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2026, 5, 21)));
        System.out.println("Penjelasan: Hanya tanggal 10-20 yang valid\n");
    }
}