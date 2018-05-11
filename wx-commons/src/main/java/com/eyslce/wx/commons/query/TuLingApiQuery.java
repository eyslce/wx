package com.eyslce.wx.commons.query;

import com.eyslce.wx.commons.entity.Perception;
import com.eyslce.wx.commons.entity.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TuLingApiQuery {
    //输入类型:0-文本(默认)、1-图片、2-音频
    private int reqType;
    //
    private Perception perception;
    //
    private UserInfo userInfo;

}
