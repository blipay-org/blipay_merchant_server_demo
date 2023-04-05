package com.blipay.merchant.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.blipay.merchant.demo.service.BlipayAPIService;
import com.blipay.merchant.demo.utils.SOLEasyHttp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
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

    @Override
    public String queryAddressList(String token, Integer pageNo, Integer pageSize) {


        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("pageNo", pageNo);
            params.put("pageSize", pageSize);

            JSONObject paramsJson = new JSONObject(params);

            return SOLEasyHttp.postWithJson(blipayApiServiceUrl + "admin/addressList", paramsJson);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String queryOrderList(String token, Integer pageNo, Integer pageSize, String outTradeOrder, String startDate, String endDate, Integer status, Integer type) {

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("pageNo", pageNo);
            params.put("pageSize", pageSize);
            params.put("outTradeOrder", outTradeOrder);
            params.put("startDate", startDate);
            params.put("endDate", endDate);
            if (status >= 0) params.put("status", status);
            if (type >= 0) params.put("type", type);

            JSONObject paramsJson = new JSONObject(params);

            return SOLEasyHttp.postWithJson(blipayApiServiceUrl + "admin/orderList", paramsJson);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String summary(String token, Integer pageNo, Integer pageSize, String outTradeOrder, String startDate, String endDate, Integer status, Integer type) {
        try {
            if (status < 0)
                status = null;
            if (type < 0)
                type = null;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("pageNo", pageNo);
            params.put("pageSize", pageSize);
            params.put("outTradeOrder", outTradeOrder);
            params.put("startDate", startDate);
            params.put("endDate", endDate);

            params.put("status", status);
            params.put("type", type);

            JSONObject paramsJson = new JSONObject(params);

            return SOLEasyHttp.postWithJson(blipayApiServiceUrl + "admin/sum", paramsJson);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String collect(String token) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            JSONObject paramsJson = new JSONObject(params);

            return SOLEasyHttp.postWithJson(blipayApiServiceUrl + "admin/collect", paramsJson);
        } catch (Exception e) {
            return null;
        }
    }
}
