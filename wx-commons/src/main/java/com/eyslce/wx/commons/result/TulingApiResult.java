package com.eyslce.wx.commons.result;

import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import java.util.List;

@Setter
@Getter
public class TulingApiResult {
    private Intent intent;
    private List<Result> results;

    public WxMpXmlOutMessage toWxMsg(WxMpXmlMessage wxMessage) {
        for (Result result : results) {
            switch (result.getResultType()) {
                case "text":
                    return WxMpXmlOutMessage.TEXT()
                            .content(result.getValues().get("text"))
                            .fromUser(wxMessage.getToUser())
                            .toUser(wxMessage.getFromUser())
                            .build();
                case "url":
                    return WxMpXmlOutMessage.TEXT()
                            .content(result.getValues().get("url"))
                            .fromUser(wxMessage.getToUser())
                            .toUser(wxMessage.getFromUser())
                            .build();
                case "voice":
                case "video":
                case "image":
                case "news":
                default:
                    return WxMpXmlOutMessage.TEXT()
                            .content("机器人暂不支持该类型消息！")
                            .fromUser(wxMessage.getToUser())
                            .toUser(wxMessage.getFromUser())
                            .build();
            }
        }
        return WxMpXmlOutMessage.TEXT().content("")
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .build();
    }
}
