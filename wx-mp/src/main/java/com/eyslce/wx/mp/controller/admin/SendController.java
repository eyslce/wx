package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.mp.controller.BaseController;
import com.eyslce.wx.mp.domain.MsgNews;
import com.eyslce.wx.mp.domain.MsgText;
import com.eyslce.wx.mp.service.IMsgNewsService;
import com.eyslce.wx.mp.service.IMsgTextService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 发送消息
 */
@RestController
@RequestMapping("admin/send")
public class SendController extends BaseController {
    @Autowired
    private IMsgTextService msgTextService;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private IMsgNewsService msgNewsService;

    /**
     * 发送文本消息
     *
     * @param textId
     * @param openIds
     * @return
     * @throws WxErrorException
     */
    @PostMapping("text")
    public HttpResult text(String textId, String openIds) throws WxErrorException {
        MsgText text = msgTextService.getById(textId);
        String[] openIdArray = openIds.split(",");
        WxMpMassOpenIdsMessage openIdsMessage = new WxMpMassOpenIdsMessage();
        openIdsMessage.setContent(text.getContent());
        openIdsMessage.setMsgType(WxConsts.MassMsgType.TEXT);
        openIdsMessage.setToUsers(Arrays.asList(openIdArray));
        WxMpMassSendResult result = wxMpService.getMassMessageService().massOpenIdsMessageSend(openIdsMessage);
        if (result.getErrorCode() != null && result.getErrorCode().equals("0")) {
            return success();
        }
        return error(result.getErrorMsg());
    }

    @PostMapping("tpl")
    public HttpResult tpl(String tplId, String content, String openIds) throws WxErrorException {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .templateId(tplId)
                .build();
        //todo
        //wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        return success();
    }

    //未使用
    @PostMapping("kefu")
    public HttpResult kefu(String id, String openid) throws WxErrorException {
        WxMpKefuMessage message = new WxMpKefuMessage();
        //todo
        message.setToUser(openid);
        boolean result = wxMpService.getKefuService().sendKefuMessage(message);
        if (result) {
            return success();
        }
        return error(Constant.ERROR_MSG);
    }

    @PostMapping("news")
    public HttpResult news(String newsId, String openIds) throws WxErrorException {
        MsgNews msgNews = this.msgNewsService.getById(newsId);
        String[] openIdArray = openIds.split(",");
        WxMpMassOpenIdsMessage openIdsMessage = new WxMpMassOpenIdsMessage();
        openIdsMessage.setMsgType(WxConsts.MassMsgType.MPNEWS);
        openIdsMessage.setMediaId(msgNews.getMediaId());
        openIdsMessage.setToUsers(Arrays.asList(openIdArray));
        WxMpMassSendResult result = wxMpService.getMassMessageService().massOpenIdsMessageSend(openIdsMessage);
        if (result.getErrorCode() != null && result.getErrorCode().equals("0")) {
            return success();
        }
        return error(result.getErrorMsg());
    }
}
