package com.blipay.merchant.demo.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.blipay.merchant.demo.constant.BlipayAPIConstant;
import com.blipay.merchant.demo.dto.APIResultObject;
import com.blipay.merchant.demo.entity.TPayOrder;
import com.blipay.merchant.demo.enums.PayOrderStatus;
import com.blipay.merchant.demo.service.ITPayOrderService;
import com.blipay.merchant.demo.service.BlipayAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

@Controller
public class PayController {

    @Autowired
    private BlipayAPIService blipayAPIService;

    @Autowired
    private ITPayOrderService payOrderService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "pay")
    public APIResultObject<?> pay(@RequestBody Map<String, Object> params) {

        APIResultObject<TPayOrder> result = new APIResultObject<>();
        try {
            String newOrderId = IdUtil.simpleUUID();
            String amount = params.get("amount").toString();

            String json = blipayAPIService.pay(BlipayAPIConstant.Token_USDTTRC20, amount, newOrderId);
            if (json != null) {

                JSONObject blipayApiResult = JSONObject.parseObject(json);

                int code = blipayApiResult.getIntValue("rst");
                String message = blipayApiResult.getString("msg");
                if (code == 200) {
                    JSONObject blipayPayOrder = blipayApiResult.getJSONObject("data");
                    TPayOrder payOrder = new TPayOrder();
                    payOrder.setId(newOrderId);
                    payOrder.setCreateTime(new Date());
                    payOrder.setUpdateTime(new Date());
                    payOrder.setStatus(PayOrderStatus.WAITING_TRANSFER.getCode());

                    payOrder.setToken(BlipayAPIConstant.Token_USDTTRC20);
                    payOrder.setAmount(blipayPayOrder.getBigDecimal("amount"));
                    payOrder.setBlipayOrderId(blipayPayOrder.getString("tradeOrder"));
                    payOrder.setExpiredTime(blipayPayOrder.getLong("expireTime"));

                    payOrder.setReceiverAddress(blipayPayOrder.getString("address"));
                    payOrder.setTxBlockNumber("");
                    payOrder.setTxHash("");

                    payOrderService.save(payOrder);

                    result.code = 0;
                    result.data = payOrder;
                    result.message = "success";
                } else {
                    result.code = code;
                    result.data = null;
                    result.message = message;
                }

            } else {
                result.code = 1002;
                result.data = null;
                result.message = "blipay service error";
            }
        } catch (Exception e) {
            result.data = null;
            result.code = 1001;
            result.message = e.getLocalizedMessage();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "inquiryPayment")
    public APIResultObject<?> inquiryPayment(@RequestBody Map<String, Object> params) {

        APIResultObject<TPayOrder> result = new APIResultObject<>();
        try {
            String orderId = params.get("orderId").toString();
            TPayOrder order = payOrderService.getById(orderId);

            result.code = 0;
            result.data = order;
            result.message = "success";
        } catch (Exception e) {
            e.printStackTrace();
            result.data = null;
            result.code = 1001;
            result.message = e.getLocalizedMessage();
        }
        return result;
    }

}
