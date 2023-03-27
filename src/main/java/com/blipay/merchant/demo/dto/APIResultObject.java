package com.blipay.merchant.demo.dto;

public class APIResultObject<T> {
    public Integer code;
    public String message;
    public T data;
}
