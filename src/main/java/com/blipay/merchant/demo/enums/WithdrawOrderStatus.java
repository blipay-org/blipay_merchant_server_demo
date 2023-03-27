package com.blipay.merchant.demo.enums;

public enum WithdrawOrderStatus {
    SUCCESS(0, "SUCCESS"),
    FAIL(1, "FAIL"),
    CANCELED(2, "CANCELED"),
    CREATED(3, "CREATED"),
    REJECTED(4, "REJECTED"),
    WAITING_TRANSFER(5, "WAITING_TRANSFER"),
    ;

    private Integer code;
    private String value;

    WithdrawOrderStatus(Integer code, String value) {
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
