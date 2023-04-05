package com.blipay.merchant.demo.dto;

public class PagedAPIResultObject<T> {
    public Integer code;
    public String message;
    public T data;
    public Integer total;
}
