# Architecture & Design Documentation

## 📐 System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                  Smart Voucher Validator                     │
└─────────────────────────────────────────────────────────────┘
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
        ▼                  ▼                  ▼
  ┌────────────┐  ┌──────────────┐  ┌──────────────┐
  │   Input    │  │  Validator   │  │    Output    │
  │  Handling  │  │   Engine     │  │   Response   │
  └────────────┘  └──────────────┘  └──────────────┘
        │                  │                  │
        │         ┌────────┴────────┐         │
        │         │                 │         │
        │    ┌────▼────┐  ┌────────▼─┐      │
        └───►│ Kode    │  │ Tanggal  │◄─────┘
            │Validator│  │Validator │
            └─────────┘  └──────────┘
                 │              │
        ┌────────┴──────────────┴────────┐
        │                                │
        ▼                                ▼
    ┌────────────┐            ┌──────────────┐
    │ Digit Sum  │            │  Date Range  │
    │ Validator  │            │  Validator   │
    │ (Even/Odd) │            │  (10-20)     │
    └────────────┘            └──────────────┘
```

## 🏗️ Class Diagram

```
┌─────────────────────────────────────────────────┐
│        SmartVoucherValidator                     │
├─────────────────────────────────────────────────┤
│ - VALID_CODE_LENGTH: int = 8                    │
│ - PREFIX: String = "EXT"                        │
│ - SPECIAL_SYMBOLS_PATTERN: Pattern              │
│ - MIN_VALID_DAY: int = 10                       │
│ - MAX_VALID_DAY: int = 20                       │
├─────────────────────────────────────────────────┤
│ + validateVoucher(String, LocalDate): boolean   │
│ + validateVoucher(Request): boolean             │
│ + isValidVoucherCode(String): boolean           │
│ + isValidTransactionDate(LocalDate): boolean    │
│ - isValidDigitPart(String): boolean             │
│ - isValidSymbolPart(String): boolean            │
└─────────────────────────────────────────────────┘
         ▲
         │ uses
         │
┌────────┴────────────────────────────────────┐
│     VoucherValidationRequest                 │
├──────────────────────────────────────────────┤
│ - voucherCode: String                        │
│ - transactionDate: LocalDate                 │
├──────────────────────────────────────────────┤
│ + getVoucherCode(): String                   │
│ + setVoucherCode(String): void               │
│ + getTransactionDate(): LocalDate            │
│ + setTransactionDate(LocalDate): void        │
│ + toString(): String                        │
└──────────────────────────────────────────────┘
```

## 🔄 Validation Flow

```
Input (Voucher Code + Date)
         │
         ▼
    ┌─────────────────┐
    │ Check Null?     │ ──► NO  ──► return false
    └────────────┬────┘
                 │ YES
                 ▼
    ┌─────────────────┐
    │ Check Empty?    │ ──► YES ──► return false
    └────────────┬────┘
                 │ NO
                 ▼
    ┌─────────────────────────┐
    │ Check Code Format       │
    │ ├─ Length = 8?          │ ──► NO  ──► return false
    │ ├─ Prefix = "EXT"?      │ ──► NO  ──► return false
    │ ├─ Digits 4-6 valid?    │
    │ │  ├─ All digits?       │ ──► NO  ──► return false
    │ │  └─ Sum even?         │ ──► NO  ──► return false
    │ └─ Symbols 7-8 valid?   │ ──► NO  ──► return false
    └────────────┬────────────┘
                 │ YES (Code Valid)
                 ▼
    ┌─────────────────────────┐
    │ Check Date Range        │
    │ ├─ Date not null?       │ ──► NO  ──► return false
    │ └─ Day 10-20?           │ ──► NO  ──► return false
    └────────────┬────────────┘
                 │ YES
                 ▼
            return true
```

## 🎯 Validation Rules Breakdown

### 1. Code Structure Validation (8 characters)

```
EXT 2 4 6 @ #
│   │ │ │ │ │
│   │ │ │ │ └─ Special Symbol 2 (Validated)
│   │ │ │ └─── Special Symbol 1 (Validated)
│   │ │ └───── Digit 3 (Must be numeric)
│   │ └─────── Digit 2 (Must be numeric)
│   └───────── Digit 1 (Must be numeric)
└───────────── Prefix (Fixed: "EXT")
```

### 2. Digit Sum Calculation

```
Digit Part: "246"
         │
         ├─ char[0] = '2' → int value = 2
         ├─ char[1] = '4' → int value = 4
         ├─ char[2] = '6' → int value = 6
         │
         └─ Sum = 2 + 4 + 6 = 12
            │
            └─ Check: 12 % 2 == 0 ✓ (Even)
```

### 3. Special Symbols Validation

```
Symbol Characters: "@#"
    │
    ├─ char[0] = '@' → Matches pattern ✓
    └─ char[1] = '#' → Matches pattern ✓

Pattern: [!@#$%^&*()_+=\-\[\]{}|;:',.<>?/~`]
Includes: ! @ # $ % ^ & * ( ) _ + - = [ ] { } | ; : ' " < > ? / . ~ `
Excludes: Letters (A-Z, a-z), Digits (0-9), Spaces, Others
```

### 4. Date Range Validation

```
Transaction Date: 2026-05-15
         │
         └─ Extract day of month: 15
            │
            └─ Check: 15 >= 10 AND 15 <= 20 ✓ (Valid)

Valid Range: [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
Invalid Range: [1-9] and [21-31]
```

## 📊 State Diagram

```
                    ┌──────────────┐
                    │  Start       │
                    └───────┬──────┘
                            │
                            ▼
                    ┌──────────────┐
                    │ Null Check   │
                    └───────┬──────┘
                            │
                  ┌─────────┴─────────┐
                  │                   │
              Null?                No? (continue)
                  │                   │
                  ▼                   ▼
            ┌──────────┐       ┌──────────────┐
            │Invalid   │       │Empty Check   │
            │Return:   │       └───────┬──────┘
            │FALSE     │               │
            └──────────┘     ┌─────────┴─────────┐
                             │                   │
                         Empty?              No? (continue)
                             │                   │
                             ▼                   ▼
                        ┌──────────┐      ┌──────────────┐
                        │Invalid   │      │Format Check  │
                        │Return:   │      └───────┬──────┘
                        │FALSE     │              │
                        └──────────┘      ┌───────┴────────────┐
                                          │                    │
                                      Invalid?            Valid?
                                          │                    │
                                          ▼                    ▼
                                    ┌──────────┐        ┌──────────────┐
                                    │Invalid   │        │Date Range    │
                                    │Return:   │        │Check         │
                                    │FALSE     │        └───────┬──────┘
                                    └──────────┘                │
                                                       ┌────────┴────────┐
                                                       │                 │
                                                   Invalid?          Valid?
                                                       │                 │
                                                       ▼                 ▼
                                                  ┌──────────┐    ┌──────────┐
                                                  │Invalid   │    │Valid     │
                                                  │Return:   │    │Return:   │
                                                  │FALSE     │    │TRUE      │
                                                  └──────────┘    └──────────┘
```

## 🧬 Error Handling Matrix

| Input | Status | Code Valid | Date Valid | Result | Reason |
|-------|--------|-----------|-----------|--------|---------|
| null | Invalid | N/A | N/A | false | Null not allowed |
| "" | Invalid | N/A | N/A | false | Empty string |
| "ABC246@#" | Invalid | false | - | false | Wrong prefix |
| "EXT135@#" | Invalid | false | - | false | Digit sum odd (9) |
| "EXT246ab" | Invalid | false | - | false | Invalid symbols |
| "EXT246@" | Invalid | false | - | false | Wrong length (7) |
| "EXT246@#" | Valid | true | false | false | Date out of range |
| "EXT246@#" | Valid | true | true | true | Valid voucher |

## 📈 Complexity Analysis

### Time Complexity
- **validateVoucher():** O(1)
  - String checks: O(1) (fixed 8 chars)
  - Digit sum calculation: O(3) → O(1)
  - Date day extraction: O(1)

- **isValidVoucherCode():** O(1)
  - Substring operations: O(1) (fixed length)
  - Regex matching: O(2) (2 symbols)

- **isValidTransactionDate():** O(1)
  - Simple integer comparison

### Space Complexity
- **Overall:** O(1)
  - Fixed number of constants
  - No dynamic data structures
  - Regex pattern compiled once (cached)

## 🔐 Security Considerations

1. **Input Validation**
   - Null checks before processing
   - Length validation before substring operations
   - Type-safe using LocalDate class

2. **Pattern Matching**
   - Whitelist approach for special symbols
   - Regex pattern properly escaped
   - No code injection vulnerabilities

3. **Immutability**
   - LocalDate is immutable
   - String operations don't modify originals
   - Constants use private static final

## 🧪 Test Coverage

### Code Coverage Target: 95%+

```
SmartVoucherValidator.java:
├─ validateVoucher(String, LocalDate) - 10 tests
├─ validateVoucher(Request) - 4 tests
├─ isValidVoucherCode(String) - 15 tests
├─ isValidTransactionDate(LocalDate) - 10 tests
├─ isValidDigitPart(String) - 8 tests
└─ isValidSymbolPart(String) - 7 tests

Total Unit Tests: 54 test cases
Edge Cases: 12 tests
Integration Tests: 5 tests
```

## 🚀 Performance Optimization

1. **Regex Compilation**
   ```java
   // Compiled once at class load time (not per validation)
   private static final Pattern SPECIAL_SYMBOLS_PATTERN = 
       Pattern.compile("^[!@#$%^&*()_+=\\-\\[\\]{}|;:',.<>?/~`]$");
   ```

2. **Early Exit**
   ```java
   // Validation stops at first failure
   if (!voucherCode.substring(0, 3).equals(PREFIX)) {
       return false; // Early exit
   }
   ```

3. **Efficient String Operations**
   ```java
   // Direct character access instead of parsing
   int sum = digitPart.charAt(0) - '0' +
             digitPart.charAt(1) - '0' +
             digitPart.charAt(2) - '0';
   ```

## 📚 Related Documentation

- [README.md](README.md) - Full user documentation
- [QUICK_START.md](QUICK_START.md) - Quick reference guide
- [SmartVoucherValidator.java](src/main/java/voucherengine/SmartVoucherValidator.java) - Source code with comments
- [SmartVoucherValidatorTest.java](src/test/java/voucherengine/SmartVoucherValidatorTest.java) - Unit tests

---

**Document Version:** 1.0  
**Last Updated:** 8 Mei 2026  
**Status:** Complete
