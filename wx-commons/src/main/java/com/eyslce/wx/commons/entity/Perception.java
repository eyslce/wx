package com.eyslce.wx.commons.entity;

import com.eyslce.wx.commons.entity.InputImage;
import com.eyslce.wx.commons.entity.InputText;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Perception {
    //文本消息
    private InputText inputText;
    //图片
    private InputImage inputImage;
    //音频
    private InputMedia inputMedia;
    //地理位置信息
//     "selfInfo": {
//        "location": {
//            "city": "北京",
//                    "province": "北京",
//                    "street": "信息路"
//        }
//    }
}
