package com.eyslce.wx.mp.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class Account extends MpAccount {
    private String name;//名称
    private Long id;
    private Date createtime = new Date();//创建时间

}
