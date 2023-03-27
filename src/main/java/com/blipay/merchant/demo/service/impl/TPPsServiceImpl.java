package com.blipay.merchant.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.blipay.merchant.demo.service.TPPsService;
import com.blipay.merchant.demo.utils.SOLEasyHttp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TPPsServiceImpl implements TPPsService {

    @Value("${tpps.url}")
    private String tppsUrl;

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
    public String pay(String chainType, String coinName, String amount, String outOrderId) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("merchantId", merchantAppKey);
            params.put("secretKey", merchantSecretKey);
            params.put("chainType", chainType);
            params.put("coinName", coinName);
            params.put("amount", amount);
            params.put("outTradeOrder", outOrderId);
            params.put("notifyUrl", payCallbackUrl);
            params.put("timeoutNotifyUrl",payTimeoutCallbackUrl);

            JSONObject paramsJson = new JSONObject(params);

            return SOLEasyHttp.postWithJson(tppsUrl + "order/pay", paramsJson);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String inquiryPayment(String payOrderId) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("payOrderId", payOrderId);


            JSONObject paramsJson = new JSONObject(params);

            return SOLEasyHttp.postWithJson(tppsUrl + "order/inquiryPayment", paramsJson);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String withdraw(String chainType, String coinName, String outOrderId, String amount, String receiverAddress) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("merchantId", merchantAppKey);
            params.put("secretKey", merchantSecretKey);
            params.put("chainType", chainType);
            params.put("coinName", coinName);
            params.put("amount", amount);
            params.put("outTradeOrder", outOrderId);
            params.put("address", receiverAddress);
            params.put("notifyUrl", withdrawCallbackUrl);

            JSONObject paramsJson = new JSONObject(params);

            return SOLEasyHttp.postWithJson(tppsUrl + "order/withdraw", paramsJson);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String inquiryWithdraw(String withdrawOrderId) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("withdrawOrderId", withdrawOrderId);

            JSONObject paramsJson = new JSONObject(params);

            return SOLEasyHttp.postWithJson(tppsUrl + "order/inquiryWithdraw", paramsJson);
        } catch (Exception e) {
            return null;
        }
    }
}
