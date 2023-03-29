package com.blipay.merchant.demo.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Author
 * @since 2022-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_withdraw_order")
public class TWithdrawOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @JsonIgnore
    @TableField("create_time")
    private Date createTime;

    @JsonIgnore
    @TableField("update_time")
    private Date updateTime;

    @TableField("status")
    private Integer status;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("chain_type")
    private String chainType;

    @TableField("coin_name")
    private String coinName;

    @TableField("receiver_address")
    private String receiverAddress;

    @TableField("send_address")
    private String sendAddress;

    @TableField("expired_time")
    private Long expiredTime;

    @TableField("tx_hash")
    private String txHash;

    @TableField("tx_block_number")
    private String txBlockNumber;

    @TableField("confirmed_block_count")
    private String confirmedBlockCount;

    @TableField("blipay_order_id")
    private String blipayOrderId;

    @TableField("reject_reason")
    private String rejectReason;

    @TableField("reject_type")
    private Integer rejectType;


}
