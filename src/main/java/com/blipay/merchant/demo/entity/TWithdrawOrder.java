package com.blipay.merchant.demo.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

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

    @TableField("create_time")
    private Date createTime;

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

    @TableField("tx_hash")
    private String txHash;

    @TableField("tx_block_number")
    private String txBlockNumber;

    @TableField("reject_reason")
    private String rejectReason;

    @TableField("reject_type")
    private Integer rejectType;

    @TableField("tpps_order_id")
    private String tppsOrderId;


}
