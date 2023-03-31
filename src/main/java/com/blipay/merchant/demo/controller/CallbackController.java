package com.blipay.merchant.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.blipay.merchant.demo.entity.TPayOrder;
import com.blipay.merchant.demo.entity.TWithdrawOrder;
import com.blipay.merchant.demo.enums.BlipayOrderStatusEnum;
import com.blipay.merchant.demo.enums.PayOrderStatus;
import com.blipay.merchant.demo.enums.WithdrawOrderStatus;
import com.blipay.merchant.demo.service.ITPayOrderService;
import com.blipay.merchant.demo.service.ITWithdrawOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Controller
public class CallbackController {

    @Autowired
    private ITPayOrderService payOrderService;

    @Autowired
    private ITWithdrawOrderService withdrawOrderService;

    @Value("${merchant.appKey}")
    private String merchantAppKey;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "payCallback")
    public String payCallback(HttpServletRequest request) {
        try {
            String body = IOUtils.toString(request.getInputStream(), "UTF-8");
            log.info("pay callback:" + body);
            JSONObject orderData = JSONObject.parseObject(body);
            BigDecimal amount = orderData.getBigDecimal("amount");
            String blipayOrderId = orderData.getString("blipayOrderId");
            String outTradeOrder = orderData.getString("outTradeOrder");
            String blockNumber = orderData.getString("blockNumber");
            String sendAddress = orderData.getString("sendAddress");
            String receiverAddress = orderData.getString("receiverAddress");
            String type = orderData.getString("type");
            String contractAddress = orderData.getString("contractAddress");
            String txHash = orderData.getString("hash");
            String status = orderData.getString("status");
            String realConfirmedBlockCount = orderData.getString("realConfirmedBlockCount");
            String careConfirmedBlockCount = orderData.getString("careConfirmedBlockCount");

            TPayOrder order = payOrderService.getById(outTradeOrder);
            if (order == null) {
                return "continue";
            }
            Integer statucCode = Integer.parseInt(status);
            if (statucCode == BlipayOrderStatusEnum.TIMEOUT.getCode()) {
                order.setStatus(PayOrderStatus.TIMEOUT.getCode());
                order.setUpdateTime(new Date());
                payOrderService.updateById(order);
            } else {
                //校验订单金额 和 收款地址
                if (order.getAmount().compareTo(amount) != 0) {
                    log.error("order amount not equals real receive amount");
                }

                if (!receiverAddress.equals(order.getReceiverAddress())) {
                    log.error("order receive address equals real receive address");
                }

                switch (statucCode) {
                    case 0:
                        order.setStatus(PayOrderStatus.WAITING_TRANSFER.getCode());
                        break;
                    case 1:
                        order.setStatus(PayOrderStatus.WAITING_BLOCK_CONFIRMS.getCode());
                        break;
                    case 2:
                        order.setStatus(PayOrderStatus.SUCCESS.getCode());
                        break;
                    case 3:
                        order.setStatus(PayOrderStatus.CANCELED.getCode());
                        break;
                    case 4:
                        order.setStatus(PayOrderStatus.FAIL.getCode());
                        break;
                }
                order.setSendAddress(sendAddress);
                order.setTxHash(txHash);
                order.setTxBlockNumber(blockNumber);
                order.setUpdateTime(new Date());
                order.setConfirmedBlockCount(realConfirmedBlockCount);
                payOrderService.updateById(order);

            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return "success";
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "payTimeoutCallback")
    public String payTimeoutCallback(HttpServletRequest request) {
        try {
            String body = IOUtils.toString(request.getInputStream(), "UTF-8");

            log.info("pay timeout callback:" + body);

            JSONObject orderData = JSONObject.parseObject(body);
            String outTradeOrder = orderData.getString("outTradeOrder");
            String merchantId = orderData.getString("merchantId");

            if (!merchantId.equals(merchantAppKey)) {
                log.error("not my merchant");
                return "fail";
            }

            TPayOrder order = payOrderService.getById(outTradeOrder);

            if (order != null) {
                order.setStatus(PayOrderStatus.TIMEOUT.getCode());
                payOrderService.updateById(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "withdrawCallback")
    public String withdrawCallback(HttpServletRequest request) {
        try {
            String body = IOUtils.toString(request.getInputStream(), "UTF-8");
            log.info("withdraw callback:" + body);

            JSONObject orderData = JSONObject.parseObject(body);
            BigDecimal amount = orderData.getBigDecimal("amount");
            String blipayOrderId = orderData.getString("blipayOrderId");
            String outTradeOrder = orderData.getString("outTradeOrder");
            String blockNumber = orderData.getString("blockNumber");
            String sendAddress = orderData.getString("sendAddress");
            String receiverAddress = orderData.getString("receiverAddress");
            String type = orderData.getString("type");
            String contractAddress = orderData.getString("contractAddress");
            String txHash = orderData.getString("hash");
            String status = orderData.getString("status");
            String realConfirmedBlockCount = orderData.getString("realConfirmedBlockCount");
            String careConfirmedBlockCount = orderData.getString("careConfirmedBlockCount");

            TWithdrawOrder order = withdrawOrderService.getById(outTradeOrder);
            if (order != null) {
                if (order.getAmount().compareTo(amount) == 0 && order.getReceiverAddress().equals(receiverAddress)) {
                    switch (Integer.parseInt(status)) {
                        case 0:
                            order.setStatus(WithdrawOrderStatus.WAITING_TRANSFER.getCode());
                            break;
                        case 1:
                        case 2:
                            order.setStatus(WithdrawOrderStatus.SUCCESS.getCode());
                            break;
                        case 3:
                            order.setStatus(WithdrawOrderStatus.CANCELED.getCode());
                            break;
                        case 4:
                            order.setStatus(WithdrawOrderStatus.FAIL.getCode());
                            break;
                    }

                    order.setSendAddress(sendAddress);
                    order.setTxHash(txHash);
                    order.setTxBlockNumber(blockNumber);
                    order.setUpdateTime(new Date());
                    order.setConfirmedBlockCount(realConfirmedBlockCount);
                    withdrawOrderService.updateById(order);
                } else {
                    //TODO error amount
                    log.error("order amount not equals real receive amount");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
