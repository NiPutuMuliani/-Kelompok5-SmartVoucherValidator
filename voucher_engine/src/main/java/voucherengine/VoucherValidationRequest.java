package voucherengine;

import java.time.LocalDate;

/**
 * Request object untuk validasi voucher
 */
public class VoucherValidationRequest {
    private String voucherCode;
    private LocalDate transactionDate;
    
    public VoucherValidationRequest(String voucherCode, LocalDate transactionDate) {
        this.voucherCode = voucherCode;
        this.transactionDate = transactionDate;
    }
    
    public VoucherValidationRequest() {
    }
    
    public String getVoucherCode() {
        return voucherCode;
    }
    
    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }
    
    public LocalDate getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    @Override
    public String toString() {
        return "VoucherValidationRequest{" +
                "voucherCode='" + voucherCode + '\'' +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
