package com.blipay.merchant.demo.enums;

public enum BlipayOrderStatusEnum {
    INIT(0,"INIT"),
    SUCCESS(2,"SUCCESS"),
    CLOSED(3,"CLOSED"),
    FAIL(4,"FAIL"),
    TIMEOUT(5,"TIMEOUT"),
    ;

    private Integer code;
    private String value;

    BlipayOrderStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
