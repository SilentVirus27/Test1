package com.example.test1;

public class ViewTransactionStructure {
    public String subTransaction_type,subPayment_type,subCategory,subAmount,subNote,subDate;
    public ViewTransactionStructure(){};

    public ViewTransactionStructure(String subTransaction_type,String subPayment_type, String subCategory, String subAmount, String subNote, String subDate) {
        this.subTransaction_type=subTransaction_type;
        this.subPayment_type = subPayment_type;
        this.subCategory = subCategory;
        this.subAmount = subAmount;
        this.subNote = subNote;
        this.subDate = subDate;
    }
}
