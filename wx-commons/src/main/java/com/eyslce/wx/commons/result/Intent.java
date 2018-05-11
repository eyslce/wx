package com.eyslce.wx.commons.result;

import lombok.Data;

import java.util.Map;

@Data
public class Intent {
    private int code;
    //意图名称
    private String intentName;
    //意图动作名称
    private String actionName;
    //功能相关参数
    private Map<String,String> parameters;
}
