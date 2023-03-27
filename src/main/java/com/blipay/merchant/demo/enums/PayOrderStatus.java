package com.blipay.merchant.demo.enums;

public enum PayOrderStatus {
    SUCCESS(0, "SUCCESS"),
    FAIL(1, "FAIL"),
    CANCELED(2, "CANCELED"),
    WAITING_TRANSFER(3, "WAITING_TRANSFER"),
    WAITING_BLOCK_CONFIRMS(4, "WAITING_BLOCK_CONFIRMS"),
    TIMEOUT(5, "TIMEOUT"),
    ;

    private Integer code;
    private String value;

    PayOrderStatus(Integer code, String value) {
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
