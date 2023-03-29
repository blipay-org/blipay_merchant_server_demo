package com.blipay.merchant.demo.dto;

public class BlipayAPIResultObject<T> {
    public Integer rst;
    public String msg;
    public Long timestamp;
    public T data;
}
