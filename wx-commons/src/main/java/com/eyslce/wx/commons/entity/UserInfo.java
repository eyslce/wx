package com.eyslce.wx.commons.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
    //机器人标识
    private String apiKey;
    //用户唯一标识
    private String userId;
    //群聊唯一标识
    private String groupId;
    //群内用户昵称
    private String userIdName;
}
