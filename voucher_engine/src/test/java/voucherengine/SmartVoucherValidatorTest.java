package voucherengine;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit Test untuk SmartVoucherValidator
 */
@DisplayName("Smart Voucher Validator Tests")
class SmartVoucherValidatorTest {
    
    // ===== Test Validasi Kode Voucher =====
    
    @Test
    @DisplayName("Valid code with valid date should return true")
    void testValidCodeWithValidDate() {
        String validCode = "EXT246@#";
        LocalDate validDate = LocalDate.of(2026, 5, 15);
        assertTrue(SmartVoucherValidator.validateVoucher(validCode, validDate));
    }
    
    @Test
    @DisplayName("Valid code with invalid date should return false")
    void testValidCodeWithInvalidDate() {
        String validCode = "EXT246@#";
        LocalDate invalidDate = LocalDate.of(2026, 5, 25);
        assertFalse(SmartVoucherValidator.validateVoucher(validCode, invalidDate));
    }
    
    @Test
    @DisplayName("Invalid code with valid date should return false")
    void testInvalidCodeWithValidDate() {
        String invalidCode = "ABC246@#";
        LocalDate validDate = LocalDate.of(2026, 5, 15);
        assertFalse(SmartVoucherValidator.validateVoucher(invalidCode, validDate));
    }
    
    @Test
    @DisplayName("Null voucher code should return false")
    void testNullVoucherCode() {
        LocalDate validDate = LocalDate.of(2026, 5, 15);
        assertFalse(SmartVoucherValidator.validateVoucher(null, validDate));
    }
    
    @Test
    @DisplayName("Empty voucher code should return false")
    void testEmptyVoucherCode() {
        LocalDate validDate = LocalDate.of(2026, 5, 15);
        assertFalse(SmartVoucherValidator.validateVoucher("", validDate));
    }
    
    @Test
    @DisplayName("Null transaction date should return false")
    void testNullTransactionDate() {
        String validCode = "EXT246@#";
        assertFalse(SmartVoucherValidator.validateVoucher(validCode, null));
    }
    
    // ===== Test Format Kode =====
    
    @Test
    @DisplayName("Code with valid format EXT + even sum + special symbols should be valid")
    void testValidCodeFormat() {
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246@#"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT468!$"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT200%-"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT808^&"));
    }
    
    @Test
    @DisplayName("Code with wrong prefix should be invalid")
    void testWrongPrefix() {
        assertFalse(SmartVoucherValidator.isValidVoucherCode("ABC246@#"));
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EX246@#"));
        assertFalse(SmartVoucherValidator.isValidVoucherCode("ext246@#"));
    }
    
    @Test
    @DisplayName("Code with wrong length should be invalid")
    void testWrongLength() {
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXT24@#"));   // 7 chars
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXT246@##")); // 9 chars
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXT"));       // 3 chars
    }
    
    @Test
    @DisplayName("Code with odd digit sum should be invalid")
    void testOddDigitSum() {
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXT135@#")); // 1+3+5=9 (odd)
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT147@#")); // 1+4+7=12 (even)
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXT159@#")); // 1+5+9=15 (odd)
    }
    
    @Test
    @DisplayName("Code with non-numeric digits should be invalid")
    void testNonNumericDigits() {
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXTabc@#"));
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXT2a6@#"));
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXT24_@#"));
    }
    
    @Test
    @DisplayName("Code with non-special symbols should be invalid")
    void testNonSpecialSymbols() {
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXT246ab")); // letters
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXT24612")); // numbers
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXT246 #")); // space
    }
    
    @Test
    @DisplayName("Valid special symbols should be accepted")
    void testValidSpecialSymbols() {
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246@#"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246!$"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246%^"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246&*"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246()"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246_+"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246-="));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246[]"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246{}"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246|;"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246:'"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246\"<"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246>?"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246/."));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT246~`"));
    }
    
    // ===== Test Digit Sum Validation =====
    
    @Test
    @DisplayName("Digit sum of 0 should be even")
    void testEvenDigitSum0() {
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT000@#"));
    }
    
    @Test
    @DisplayName("Digit sum of 2 should be even")
    void testEvenDigitSum2() {
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT002@#"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT011@#"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT200@#"));
    }
    
    @Test
    @DisplayName("Digit sum of 18 should be even")
    void testEvenDigitSum18() {
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXT999@#")); // 9+9+9=27 (odd)
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT888@#")); // 8+8+8=24 (even)
    }
    
    @Test
    @DisplayName("Digit sum of 1 should be odd")
    void testOddDigitSum1() {
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXT001@#"));
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXT010@#"));
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXT100@#"));
    }
    
    // ===== Test Date Validation =====
    
    @Test
    @DisplayName("Date on day 10 should be valid")
    void testValidDateDay10() {
        assertTrue(SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2026, 5, 10)));
    }
    
    @Test
    @DisplayName("Date on day 15 should be valid")
    void testValidDateDay15() {
        assertTrue(SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2026, 5, 15)));
    }
    
    @Test
    @DisplayName("Date on day 20 should be valid")
    void testValidDateDay20() {
        assertTrue(SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2026, 5, 20)));
    }
    
    @Test
    @DisplayName("Date on day 9 should be invalid")
    void testInvalidDateDay9() {
        assertFalse(SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2026, 5, 9)));
    }
    
    @Test
    @DisplayName("Date on day 21 should be invalid")
    void testInvalidDateDay21() {
        assertFalse(SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2026, 5, 21)));
    }
    
    @Test
    @DisplayName("Date on day 1 should be invalid")
    void testInvalidDateDay1() {
        assertFalse(SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2026, 5, 1)));
    }
    
    @Test
    @DisplayName("Date on day 31 should be invalid")
    void testInvalidDateDay31() {
        assertFalse(SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2026, 5, 31)));
    }
    
    @Test
    @DisplayName("Null date should be invalid")
    void testNullDate() {
        assertFalse(SmartVoucherValidator.isValidTransactionDate(null));
    }
    
    @Test
    @DisplayName("Valid date range works for any month")
    void testValidDateRangeAnyMonth() {
        // January
        assertTrue(SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2026, 1, 15)));
        // February (leap year)
        assertTrue(SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2024, 2, 15)));
        // December
        assertTrue(SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2026, 12, 15)));
    }
    
    // ===== Test with VoucherValidationRequest =====
    
    @Test
    @DisplayName("Valid request should return true")
    void testValidRequest() {
        VoucherValidationRequest request = new VoucherValidationRequest("EXT246@#", LocalDate.of(2026, 5, 15));
        assertTrue(SmartVoucherValidator.validateVoucher(request));
    }
    
    @Test
    @DisplayName("Invalid request should return false")
    void testInvalidRequest() {
        VoucherValidationRequest request = new VoucherValidationRequest("ABC246@#", LocalDate.of(2026, 5, 15));
        assertFalse(SmartVoucherValidator.validateVoucher(request));
    }
    
    @Test
    @DisplayName("Null request should return false")
    void testNullRequest() {
        assertFalse(SmartVoucherValidator.validateVoucher((VoucherValidationRequest) null));
    }
    
    @Test
    @DisplayName("Request with null code should return false")
    void testRequestWithNullCode() {
        VoucherValidationRequest request = new VoucherValidationRequest(null, LocalDate.of(2026, 5, 15));
        assertFalse(SmartVoucherValidator.validateVoucher(request));
    }
    
    @Test
    @DisplayName("Request with null date should return false")
    void testRequestWithNullDate() {
        VoucherValidationRequest request = new VoucherValidationRequest("EXT246@#", null);
        assertFalse(SmartVoucherValidator.validateVoucher(request));
    }
    
    // ===== Integration Tests =====
    
    @Test
    @DisplayName("Multiple valid codes with different even sums")
    void testMultipleValidCodes() {
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT404!@"));   // 4+0+4=8
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT262#$"));   // 2+6+2=10
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT080%^"));   // 0+8+0=8
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT444&*"));   // 4+4+4=12
    }
    
    @Test
    @DisplayName("End-to-end validation with boundary dates")
    void testEndToEndWithBoundaryDates() {
        String validCode = "EXT468!$";
        
        // Start of valid range
        assertTrue(SmartVoucherValidator.validateVoucher(validCode, LocalDate.of(2026, 5, 10)));
        
        // Middle of valid range
        assertTrue(SmartVoucherValidator.validateVoucher(validCode, LocalDate.of(2026, 5, 15)));
        
        // End of valid range
        assertTrue(SmartVoucherValidator.validateVoucher(validCode, LocalDate.of(2026, 5, 20)));
        
        // Before valid range
        assertFalse(SmartVoucherValidator.validateVoucher(validCode, LocalDate.of(2026, 5, 9)));
        
        // After valid range
        assertFalse(SmartVoucherValidator.validateVoucher(validCode, LocalDate.of(2026, 5, 21)));
    }
}
