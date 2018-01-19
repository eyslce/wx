package com.eyslce.wx.mp.entity;

import lombok.Data;

import java.util.List;

/**
 * 快递类型实体类
 */
@Data
public class DeliveryType {

    private String comCode;

    /**
     * 单号
     */
    private long num;

    private List<DeliveryTypeAuto> auto;
}
