package com.blipay.merchant.demo.service;


public interface TPPsService {

    String pay(String chainType, String coinName, String amount, String outOrderId);

    String inquiryPayment(String payOrderId);

    String withdraw(String chainType, String coinName, String outOrderId, String amount, String receiverAddress);

    String inquiryWithdraw(String withdrawOrderId);
}
