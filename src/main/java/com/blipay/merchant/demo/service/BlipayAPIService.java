package com.blipay.merchant.demo.service;


public interface BlipayAPIService {

    String pay(String token, String amount, String outOrderId);

    String inquiryPayment(String payOrderId);

    String withdraw(String token, String outOrderId, String amount, String receiverAddress);

    String inquiryWithdraw(String withdrawOrderId);
}
