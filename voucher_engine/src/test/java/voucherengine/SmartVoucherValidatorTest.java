package voucherengine;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit Test untuk SmartVoucherValidator
 */
@DisplayName("Smart Voucher Validator Tests")
class SmartVoucherValidatorTest {

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

    @ParameterizedTest
    @ValueSource(strings = {
        "EXT246@#", 
        "EXT468!$", 
        "EXT404!@", 
        "EXT808^&"  
    })
    @DisplayName("Valid voucher codes should return true")
    void testValidVoucherCodes(String code) {
        assertTrue(SmartVoucherValidator.isValidVoucherCode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "ABC246@#", 
        "EX246@#",  
        "ext246@#"  
    @DisplayName("Wrong prefix should return false")
    void testWrongPrefix(String code) {
        assertFalse(SmartVoucherValidator.isValidVoucherCode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "EXT24@#",   
        "EXT246@##", 
        "EXT"        
    })
    @DisplayName("Wrong length should return false")
    void testWrongLength(String code) {
        assertFalse(SmartVoucherValidator.isValidVoucherCode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "EXT135@#", 
        "EXT159@#", 
        "EXT010@#", 
        "EXT100@#"  
    })
    @DisplayName("Odd digit sum should return false")
    void testOddDigitSum(String code) {
        assertFalse(SmartVoucherValidator.isValidVoucherCode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "EXT147@#"  
    })
    @DisplayName("Even digit sum should return true")
    void testEvenDigitSum(String code) {
        assertTrue(SmartVoucherValidator.isValidVoucherCode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "EXTabc@#", 
        "EXT2a6@#", 
        "EXT24_@#"  
    })
    @DisplayName("Non-numeric digits at positions 4-6 should return false")
    void testNonNumericDigits(String code) {
        assertFalse(SmartVoucherValidator.isValidVoucherCode(code));
    }

    @ParameterizedTest
    @CsvSource({
        "EXT246@#, true",
        "EXT246!$, true",
        "EXT246%^, true",
        "EXT246&*, true",
        "EXT246(), true",
        "EXT246_+, true",
        "EXT246-=, true",
        "EXT246ab, false", 
        "EXT24612, false", 
        "EXT246 #, false"  
    })
    @DisplayName("Symbol validation at positions 7-8")
    void testSymbolValidation(String code, boolean expected) {
        assertEquals(expected, SmartVoucherValidator.isValidVoucherCode(code));
    }


    @Test
    @DisplayName("Digit sum of 0 (EXT000) should be even - valid")
    void testEvenDigitSum0() {
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT000@#"));
    }

    @Test
    @DisplayName("Digit sum of 2 should be even - valid")
    void testEvenDigitSum2() {
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT002@#"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT011@#"));
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT200@#"));
    }

    @Test
    @DisplayName("EXT888 digit sum 24 (even) valid, EXT999 digit sum 27 (odd) invalid")
    void testEvenDigitSum18() {
        assertFalse(SmartVoucherValidator.isValidVoucherCode("EXT999@#")); // 9+9+9=27 (ganjil)
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT888@#"));  // 8+8+8=24 (genap)
    }


    @ParameterizedTest
    @ValueSource(ints = {10, 11, 15, 19, 20})
    @DisplayName("Dates 10-20 should be valid")
    void testValidDates(int day) {
        LocalDate date = LocalDate.of(2026, 5, day);
        assertTrue(SmartVoucherValidator.isValidTransactionDate(date));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 9, 21, 25, 31})
    @DisplayName("Dates outside 10-20 should be invalid")
    void testInvalidDates(int day) {
        LocalDate date = LocalDate.of(2026, 5, day);
        assertFalse(SmartVoucherValidator.isValidTransactionDate(date));
    }

    @Test
    @DisplayName("Null date should be invalid")
    void testNullDate() {
        assertFalse(SmartVoucherValidator.isValidTransactionDate(null));
    }

    @Test
    @DisplayName("Valid date range works for any month")
    void testValidDateRangeAnyMonth() {
        assertTrue(SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2026, 1, 15)));
        
        assertTrue(SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2024, 2, 15)));
        
        assertTrue(SmartVoucherValidator.isValidTransactionDate(LocalDate.of(2026, 12, 15)));
    }


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


    @Test
    @DisplayName("Multiple valid codes with different even sums")
    void testMultipleValidCodes() {
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT404!@")); // 4+0+4=8
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT262#$")); // 2+6+2=10
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT080%^")); // 0+8+0=8
        assertTrue(SmartVoucherValidator.isValidVoucherCode("EXT444&*")); // 4+4+4=12
    }

    @Test
    @DisplayName("End-to-end validation with boundary dates")
    void testEndToEndWithBoundaryDates() {
        String validCode = "EXT468!$";

      
        assertTrue(SmartVoucherValidator.validateVoucher(validCode, LocalDate.of(2026, 5, 10)));
        
        assertTrue(SmartVoucherValidator.validateVoucher(validCode, LocalDate.of(2026, 5, 15)));
        
        assertTrue(SmartVoucherValidator.validateVoucher(validCode, LocalDate.of(2026, 5, 20)));
        
        assertFalse(SmartVoucherValidator.validateVoucher(validCode, LocalDate.of(2026, 5, 9)));
        
        assertFalse(SmartVoucherValidator.validateVoucher(validCode, LocalDate.of(2026, 5, 21)));
    }
}