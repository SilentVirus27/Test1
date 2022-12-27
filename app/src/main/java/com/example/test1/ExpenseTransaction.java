package com.example.test1;

public class ExpenseTransaction {
    public String transactionId,subTransaction_type,subPayment_type,subCategory,subAmount,subNote,subDate,subUid;
    public ExpenseTransaction(){}
    public ExpenseTransaction(String transactionId,String subTransaction_type, String subPayment_type, String subCategory, String subAmount, String subNote, String subDate,String subUid){
        this.transactionId=transactionId;
        this.subTransaction_type=subTransaction_type;
        this.subPayment_type=subPayment_type;
        this.subCategory=subCategory;
        this.subAmount=subAmount;
        this.subNote=subNote;
        this.subDate=subDate;
        this.subUid=subUid;
    }

    public String getSubUid() {
        return subUid;
    }

    public void setSubUid(String subUid) {
        this.subUid = subUid;
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

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
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
