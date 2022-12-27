package com.example.test1;

public class IncomeTransaction {
    public String transactionId,subTransaction_type,subPayment_type,subAmount,subNote,subDate;
    public IncomeTransaction(){}
    public IncomeTransaction(String transactionId,String subTransaction_type, String subPayment_type, String subAmount, String subNote, String subDate){
        this.transactionId=transactionId;
        this.subTransaction_type=subTransaction_type;
        this.subPayment_type=subPayment_type;
        this.subAmount=subAmount;
        this.subNote=subNote;
        this.subDate=subDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSubTransaction_type() {
        return subTransaction_type;
    }

    public void setSubTransaction_type(String subTransaction_type) {
        this.subTransaction_type = subTransaction_type;
    }

    public String getSubPayment_type() {
        return subPayment_type;
    }

    public void setSubPayment_type(String subPayment_type) {
        this.subPayment_type = subPayment_type;
    }

    public String getSubAmount() {
        return subAmount;
    }

    public void setSubAmount(String subAmount) {
        this.subAmount = subAmount;
    }

    public String getSubNote() {
        return subNote;
    }

    public void setSubNote(String subNote) {
        this.subNote = subNote;
    }

    public String getSubDate() {
        return subDate;
    }

    public void setSubDate(String subDate) {
        this.subDate = subDate;
    }
}
