package com.eyslce.wx.mp.handler;

import com.eyslce.wx.commons.result.TulingApiResult;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.commons.util.TuLingApi;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 处理菜单事件
 */
@Component
public class MenuHandler extends AbstractHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        String msg = String.format("type:%s, event:%s, key:%s",
                wxMessage.getMsgType(), wxMessage.getEvent(),
                wxMessage.getEventKey());
        if (WxConsts.MenuButtonType.VIEW.equals(wxMessage.getEvent())) {
            return null;
        }
        //点击类型菜单
        if (WxConsts.MenuButtonType.CLICK.equals(wxMessage.getEvent())) {
            switch (Constant.Menu.getMenu(wxMessage.getEventKey())) {
                case Delivery:
                    TulingApiResult result = TuLingApi.call(Constant.Menu.Delivery.getName());
                    return result.toWxMsg(wxMessage);
                default:
                    break;
            }
        }

        return WxMpXmlOutMessage.TEXT().content(msg)
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .build();
    }
}
