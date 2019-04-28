package com.eyslce.wx.mp.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MpAccount {
    private String account;//账号
    private String appid;//appid
    private String appsecret;//appsecret
    private String url;//验证时用的url
    private String token;//token
    private String aeskey;
    private Integer msgcount;//自动回复消息条数;默认是5条


}
