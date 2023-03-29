package com.blipay.merchant.demo.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.blipay.merchant.demo.constant.BlipayAPIConstant;
import com.blipay.merchant.demo.dto.APIResultObject;
import com.blipay.merchant.demo.entity.TWithdrawOrder;
import com.blipay.merchant.demo.enums.WithdrawOrderStatus;
import com.blipay.merchant.demo.service.ITWithdrawOrderService;
import com.blipay.merchant.demo.service.BlipayAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Controller
public class WithdrawController {

    @Autowired
    private BlipayAPIService tppsService;

    @Autowired
    private ITWithdrawOrderService withdrawOrderService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "withdraw")
    public APIResultObject<?> withdrawRequest(@RequestBody Map<String, Object> params) {

        APIResultObject<TWithdrawOrder> result = new APIResultObject<>();
        try {
            String newOrderId = IdUtil.simpleUUID();
            String amount = params.get("amount").toString();
            String receiverAddress = params.get("receiverAddress").toString();

            TWithdrawOrder withdrawOrder = new TWithdrawOrder();
            withdrawOrder.setId(newOrderId);
            withdrawOrder.setAmount(new BigDecimal(amount));
            withdrawOrder.setCreateTime(new Date());
            withdrawOrder.setUpdateTime(new Date());
            withdrawOrder.setStatus(WithdrawOrderStatus.CREATED.getCode());
            withdrawOrder.setReceiverAddress(receiverAddress);

            withdrawOrder.setChainType(BlipayAPIConstant.ChainTypeTron);
            withdrawOrder.setCoinName(BlipayAPIConstant.CoinNameUSDT);

            withdrawOrder.setBlipayOrderId("");
            withdrawOrder.setTxBlockNumber("");
            withdrawOrder.setTxHash("");

            withdrawOrderService.save(withdrawOrder);

            result.code = 0;
            result.data = withdrawOrder;
            result.message = "success";

        } catch (Exception e) {
            result.data = null;
            result.code = 1001;
            result.message = e.getLocalizedMessage();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "withdrawAllow")
    public APIResultObject<?> withdrawAllow(@RequestBody Map<String, Object> params) {

        APIResultObject<TWithdrawOrder> result = new APIResultObject<>();
        try {
            String orderId = params.get("orderId").toString();


            TWithdrawOrder order = withdrawOrderService.getById(orderId);

            String json = tppsService.withdraw(BlipayAPIConstant.ChainTypeTron, BlipayAPIConstant.CoinNameUSDT, order.getId(), order.getAmount().toString(), order.getReceiverAddress());

            if (json != null) {
                JSONObject blipayPayOrder = JSONObject.parseObject(json);


                order.setBlipayOrderId(blipayPayOrder.getString("tradeOrder"));

                order.setStatus(WithdrawOrderStatus.WAITING_TRANSFER.getCode());

                withdrawOrderService.updateById(order);

                result.code = 0;
                result.data = order;
                result.message = "success";
            } else {
                result.code = 1002;
                result.data = null;
                result.message = "tpps error";
            }
        } catch (Exception e) {
            result.data = null;
            result.code = 1001;
            result.message = e.getLocalizedMessage();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "withdrawReject")
    public APIResultObject<?> withdrawReject(@RequestBody Map<String, Object> params) {

        APIResultObject<TWithdrawOrder> result = new APIResultObject<>();
        try {
            String orderId = params.get("orderId").toString();
            String rejectReason = params.get("rejectReason").toString();

            TWithdrawOrder order = withdrawOrderService.getById(orderId);


            order.setStatus(WithdrawOrderStatus.REJECTED.getCode());
            order.setRejectReason(rejectReason);

            withdrawOrderService.updateById(order);

            result.code = 0;
            result.data = order;
            result.message = "success";
        } catch (Exception e) {
            result.data = null;
            result.code = 1001;
            result.message = e.getLocalizedMessage();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "inquiryWithdraw")
    public APIResultObject<?> inquiryWithdraw(@RequestBody Map<String, Object> params) {

        APIResultObject<TWithdrawOrder> result = new APIResultObject<>();
        try {
            String orderId = params.get("orderId").toString();
            TWithdrawOrder order = withdrawOrderService.getById(orderId);

            result.code = 0;
            result.data = order;
            result.message = "success";
        } catch (Exception e) {
            result.data = null;
            result.code = 1001;
            result.message = e.getLocalizedMessage();
        }
        return result;
    }

}
