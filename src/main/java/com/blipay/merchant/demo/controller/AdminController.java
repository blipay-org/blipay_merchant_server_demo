package com.blipay.merchant.demo.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blipay.merchant.demo.constant.BlipayAPIConstant;
import com.blipay.merchant.demo.dto.APIResultObject;
import com.blipay.merchant.demo.dto.PagedAPIResultObject;
import com.blipay.merchant.demo.entity.TPayOrder;
import com.blipay.merchant.demo.enums.PayOrderStatus;
import com.blipay.merchant.demo.service.BlipayAPIService;
import com.blipay.merchant.demo.service.ITPayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private BlipayAPIService blipayAPIService;

    @Autowired
    private ITPayOrderService payOrderService;


    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "login")
    public APIResultObject<Void> login(@RequestBody Map<String, Object> params) {
        try {
            String userName = params.get("username").toString();
            String password = params.get("password").toString();
            if (userName.equals("admin") && password.equals("123456")) {
                APIResultObject<Void> result = new APIResultObject<>();
                result.code = 0;
                result.message = "login success";
                result.data = null;
                return result;
            } else {
                APIResultObject<Void> result = new APIResultObject<>();
                result.code = 3001;
                result.message = "login failed";
                result.data = null;
                return result;
            }
        } catch (Exception e) {
            APIResultObject<Void> result = new APIResultObject<>();
            result.data = null;
            result.code = 1001;
            result.message = e.getLocalizedMessage();
            return result;
        }

    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "addressList")
    public PagedAPIResultObject<JSONObject> queryAddressList(@RequestBody Map<String, Object> params) {

        PagedAPIResultObject<JSONObject> result = new PagedAPIResultObject<>();
        try {
            Integer pageNo = 1;
            Integer pageSize = 20;
            if (params.containsKey("pageNo")) {
                pageNo = Integer.parseInt(params.get("pageNo").toString());
            }
            if (params.containsKey("pageSize")) {
                pageSize = Integer.parseInt(params.get("pageSize").toString());
            }

            String json = blipayAPIService.queryAddressList(BlipayAPIConstant.Token_USDTTRC20, pageNo, pageSize);
            if (json != null) {

                JSONObject blipayApiResult = JSONObject.parseObject(json);

                int code = blipayApiResult.getIntValue("rst");
                String message = blipayApiResult.getString("msg");
                if (code == 200) {
                    result.code = 0;
                    result.data = blipayApiResult.getJSONObject("data");
                    result.message = "success";
                    result.total = blipayApiResult.getInteger("total");
                } else {
                    result.code = code;
                    result.data = null;
                    result.message = message;
                    result.total = 0;
                }

            } else {
                result.code = 1002;
                result.data = null;
                result.message = "blipay service error";
                result.total = 0;
            }
        } catch (Exception e) {
            result.data = null;
            result.code = 1001;
            result.message = e.getLocalizedMessage();
            result.total = 0;
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "orderList")
    public PagedAPIResultObject<JSONObject> queryOrderList(@RequestBody Map<String, Object> params) {

        PagedAPIResultObject<JSONObject> result = new PagedAPIResultObject<>();
        try {
            Integer pageNo = 1;
            Integer pageSize = 20;
            String outTradeOrder = null;
            String startDate = null;
            String endDate = null;
            //"订单状态：0-初始，1-确认中，2-成功，3-关闭，4-失败")
            Integer status = -1;
            // "订单类型：1-充值，2-提现，3-归集手续费，4-归集订单，5-异常订单")
            Integer type = -1;


            if (params.containsKey("pageNo")) {
                pageNo = Integer.parseInt(params.get("pageNo").toString());
            }
            if (params.containsKey("pageSize")) {
                pageSize = Integer.parseInt(params.get("pageSize").toString());
            }
            if (params.containsKey("outTradeOrder")) {
                outTradeOrder = params.get("outTradeOrder").toString();
            }
            if (params.containsKey("startDate")) {
                startDate = params.get("startDate").toString();
            }
            if (params.containsKey("endDate")) {
                endDate = params.get("endDate").toString();
            }

            if (params.containsKey("status")) {
                status = Integer.parseInt(params.get("status").toString());
            }
            if (params.containsKey("type")) {
                type = Integer.parseInt(params.get("type").toString());
            }

            String json = blipayAPIService.queryOrderList(BlipayAPIConstant.Token_USDTTRC20, pageNo, pageSize, outTradeOrder, startDate, endDate, status, type);
            if (json != null) {

                JSONObject blipayApiResult = JSONObject.parseObject(json);

                int code = blipayApiResult.getIntValue("rst");
                String message = blipayApiResult.getString("msg");
                if (code == 200) {
                    result.code = 0;
                    result.data = blipayApiResult.getJSONObject("data");
                    result.message = "success";
                    result.total = blipayApiResult.getInteger("total");
                } else {
                    result.code = code;
                    result.data = null;
                    result.message = message;
                    result.total = 0;
                }

            } else {
                result.code = 1002;
                result.data = null;
                result.message = "blipay service error";
                result.total = 0;
            }
        } catch (Exception e) {
            result.data = null;
            result.code = 1001;
            result.message = e.getLocalizedMessage();
            result.total = 0;
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "collect")
    public PagedAPIResultObject<JSONObject> collect(@RequestBody Map<String, Object> params) {

        PagedAPIResultObject<JSONObject> result = new PagedAPIResultObject<>();
        try {
            Integer pageNo = 1;
            Integer pageSize = 20;
            String outTradeOrder = null;
            String startDate = null;
            String endDate = null;
            //"订单状态：0-初始，1-确认中，2-成功，3-关闭，4-失败")
            Integer status = -1;
            // "订单类型：1-充值，2-提现，3-归集手续费，4-归集订单，5-异常订单")
            Integer type = -1;


            if (params.containsKey("pageNo")) {
                pageNo = Integer.parseInt(params.get("pageNo").toString());
            }
            if (params.containsKey("pageSize")) {
                pageSize = Integer.parseInt(params.get("pageSize").toString());
            }
            if (params.containsKey("outTradeOrder")) {
                outTradeOrder = params.get("outTradeOrder").toString();
            }
            if (params.containsKey("startDate")) {
                startDate = params.get("startDate").toString();
            }
            if (params.containsKey("endDate")) {
                endDate = params.get("endDate").toString();
            }

            if (params.containsKey("status")) {
                status = Integer.parseInt(params.get("status").toString());
            }
            if (params.containsKey("type")) {
                type = Integer.parseInt(params.get("type").toString());
            }

            String json = blipayAPIService.queryOrderList(BlipayAPIConstant.Token_USDTTRC20, pageNo, pageSize, outTradeOrder, startDate, endDate, status, type);
            if (json != null) {

                JSONObject blipayApiResult = JSONObject.parseObject(json);

                int code = blipayApiResult.getIntValue("rst");
                String message = blipayApiResult.getString("msg");
                if (code == 200) {
                    result.code = 0;
                    result.data = blipayApiResult.getJSONObject("data");
                    result.message = "success";
                    result.total = blipayApiResult.getInteger("total");
                } else {
                    result.code = code;
                    result.data = null;
                    result.message = message;
                    result.total = 0;
                }

            } else {
                result.code = 1002;
                result.data = null;
                result.message = "blipay service error";
                result.total = 0;
            }
        } catch (Exception e) {
            result.data = null;
            result.code = 1001;
            result.message = e.getLocalizedMessage();
            result.total = 0;
        }
        return result;
    }
}
