package com.blipay.merchant.demo.service;


public interface BlipayAPIService {

    String pay(String token, String amount, String outOrderId);

    String inquiryPayment(String payOrderId);

    String withdraw(String token, String outOrderId, String amount, String receiverAddress);

    String inquiryWithdraw(String withdrawOrderId);

    String queryAddressList(String token, Integer pageNo, Integer pageSize);

    String queryOrderList(String token, Integer pageNo, Integer pageSize, String outTradeOrder, String startDate, String endDate, Integer status, Integer type);

    String summary(String token, Integer pageNo, Integer pageSize, String outTradeOrder, String startDate, String endDate, Integer status, Integer type);

    String collect(String token);
}