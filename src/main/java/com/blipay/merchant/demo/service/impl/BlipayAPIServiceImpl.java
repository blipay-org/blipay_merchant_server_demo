package com.blipay.merchant.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.blipay.merchant.demo.service.BlipayAPIService;
import com.blipay.merchant.demo.utils.SOLEasyHttp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BlipayAPIServiceImpl implements BlipayAPIService {

    @Value("${blipay-api.url}")
    private String blipayApiServiceUrl;

    @Value("${callback.payUrl}")
    private String payCallbackUrl;

    @Value("${callback.payTimeoutUrl}")
    private String payTimeoutCallbackUrl;

    @Value("${callback.withdrawUrl}")
    private String withdrawCallbackUrl;

    @Value("${merchant.appKey}")
    private String merchantAppKey;

    @Value("${merchant.secretKey}")
    private String merchantSecretKey;

    @Override
    public String pay(String token, String amount, String outOrderId) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("merchantId", merchantAppKey);
            params.put("secretKey", merchantSecretKey);
            params.put("token", token);
            params.put("amount", amount);
            params.put("outTradeOrder", outOrderId);
            params.put("notifyUrl", payCallbackUrl);

            JSONObject paramsJson = new JSONObject(params);

            return SOLEasyHttp.postWithJson(blipayApiServiceUrl + "order/pay", paramsJson);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String inquiryPayment(String payOrderId) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("outTradeOrder", payOrderId);


            JSONObject paramsJson = new JSONObject(params);

            return SOLEasyHttp.postWithJson(blipayApiServiceUrl + "order/inquiryPayment", paramsJson);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String withdraw(String token, String outOrderId, String amount, String receiverAddress) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("merchantId", merchantAppKey);
            params.put("secretKey", merchantSecretKey);
            params.put("token", token);
            params.put("amount", amount);
            params.put("outTradeOrder", outOrderId);
            params.put("address", receiverAddress);
            params.put("notifyUrl", withdrawCallbackUrl);

            JSONObject paramsJson = new JSONObject(params);

            return SOLEasyHttp.postWithJson(blipayApiServiceUrl + "order/withdraw", paramsJson);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String inquiryWithdraw(String withdrawOrderId) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("outTradeOrder", withdrawOrderId);

            JSONObject paramsJson = new JSONObject(params);

            return SOLEasyHttp.postWithJson(blipayApiServiceUrl + "order/inquiryWithdraw", paramsJson);
        } catch (Exception e) {
            return null;
        }
    }
}
