package voucherengine;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Smart Voucher Validator - Validasi kode voucher berdasarkan aturan tertentu
 * 
 * Ketentuan Kode:
 * - Karakter 1-3: "EXT"
 * - Karakter 4-6: Angka yang jika dijumlahkan hasilnya genap
 * - Karakter 7-8: Simbol khusus
 * - Panjang total: 8 karakter
 * - Berlaku jika tanggal transaksi antara 10-20 (inklusif) setiap bulan
 */
public class SmartVoucherValidator {
    
    private static final int VALID_CODE_LENGTH = 8;
    private static final String PREFIX = "EXT";
    private static final Pattern SPECIAL_SYMBOLS_PATTERN = Pattern.compile("^[!@#$%^&*()_+=\\-\\[\\]{}|;:'\",.<>?/~`]$");
    private static final int MIN_VALID_DAY = 10;
    private static final int MAX_VALID_DAY = 20;
    
    /**
     * Validasi kode voucher dengan tanggal transaksi
     * 
     * @param voucherCode Kode voucher yang akan divalidasi
     * @param transactionDate Tanggal transaksi
     * @return true jika kode valid dan tanggal berada dalam rentang yang diizinkan
     */
    public static boolean validateVoucher(String voucherCode, LocalDate transactionDate) {
        // Cek null dan input kosong
        if (voucherCode == null || voucherCode.isEmpty()) {
            return false;
        }
        
        if (transactionDate == null) {
            return false;
        }
        
        // Cek validitas kode
        if (!isValidVoucherCode(voucherCode)) {
            return false;
        }
        
        // Cek rentang tanggal
        if (!isValidTransactionDate(transactionDate)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Validasi format dan struktur kode voucher
     * 
     * @param voucherCode Kode voucher
     * @return true jika format kode sesuai ketentuan
     */
    public static boolean isValidVoucherCode(String voucherCode) {
        // Cek null dan input kosong
        if (voucherCode == null || voucherCode.isEmpty()) {
            return false;
        }
        
        // Cek panjang
        if (voucherCode.length() != VALID_CODE_LENGTH) {
            return false;
        }
        
        // Cek prefix "EXT" (karakter 1-3)
        if (!voucherCode.substring(0, 3).equals(PREFIX)) {
            return false;
        }
        
        // Cek karakter 4-6 harus angka dan jumlahnya genap
        String digitPart = voucherCode.substring(3, 6);
        if (!isValidDigitPart(digitPart)) {
            return false;
        }
        
        // Cek karakter 7-8 harus simbol khusus
        String symbolPart = voucherCode.substring(6, 8);
        if (!isValidSymbolPart(symbolPart)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Validasi bagian angka (karakter 4-6)
     * - Harus berisi 3 digit angka
     * - Jumlah ketiga angka harus genap
     * 
     * @param digitPart Bagian digit (3 karakter)
     * @return true jika valid
     */
    private static boolean isValidDigitPart(String digitPart) {
        if (digitPart == null || digitPart.length() != 3) {
            return false;
        }
        
        // Cek semua karakter adalah digit
        if (!digitPart.matches("\\d{3}")) {
            return false;
        }
        
        // Hitung jumlah digit
        int sum = digitPart.charAt(0) - '0' +
                  digitPart.charAt(1) - '0' +
                  digitPart.charAt(2) - '0';
        
        // Cek apakah jumlahnya genap
        return sum % 2 == 0;
    }
    
    /**
     * Validasi bagian simbol (karakter 7-8)
     * - Harus berisi 2 simbol khusus
     * 
     * @param symbolPart Bagian simbol (2 karakter)
     * @return true jika valid
     */
    private static boolean isValidSymbolPart(String symbolPart) {
        if (symbolPart == null || symbolPart.length() != 2) {
            return false;
        }
        
        // Cek setiap karakter adalah simbol khusus
        for (char c : symbolPart.toCharArray()) {
            if (!SPECIAL_SYMBOLS_PATTERN.matcher(String.valueOf(c)).matches()) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Validasi rentang tanggal transaksi (10-20 setiap bulan)
     * 
     * @param transactionDate Tanggal transaksi
     * @return true jika tanggal berada dalam rentang yang diizinkan
     */
    public static boolean isValidTransactionDate(LocalDate transactionDate) {
        if (transactionDate == null) {
            return false;
        }
        
        int dayOfMonth = transactionDate.getDayOfMonth();
        return dayOfMonth >= MIN_VALID_DAY && dayOfMonth <= MAX_VALID_DAY;
    }
    
    /**
     * Validasi dengan menggunakan VoucherValidationRequest
     * 
     * @param request Objek request berisi kode dan tanggal
     * @return true jika voucher valid
     */
    public static boolean validateVoucher(VoucherValidationRequest request) {
        if (request == null) {
            return false;
        }
        
        return validateVoucher(request.getVoucherCode(), request.getTransactionDate());
    }
}
