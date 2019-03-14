package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.util.Constant;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpDataCubeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeArticleResult;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserCumulate;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserSummary;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 工作台
 */
@Controller
@RequestMapping("admin/workbench")
public class WorkBenchController extends BaseController {
    @Autowired
    private WxMpService wxMpService;

    @GetMapping
    public String workbench() {
        return "admin/workbench/index";
    }

    @PostMapping("dataCube")
    @ResponseBody
    public HttpResult dataCube(String type, String start, String end) throws WxErrorException, ParseException {
        Date beginDate = DateUtils.parseDate(start, Locale.CHINA, "yyyy-MM-dd");
        Date endDate = DateUtils.parseDate(end, Locale.CHINA, "yyyy-MM-dd");
        WxMpDataCubeService cubeService = wxMpService.getDataCubeService();
        switch (Constant.CubeMethod.getEnumMethod(type)) {
            case GET_USER_SUMMARY:
                List<WxDataCubeUserSummary> userSummaryList = cubeService.getUserSummary(beginDate, endDate);
                return success(userSummaryList, Constant.SUCCESS_MSG);
            case GET_USER_CUMULATE:
                List<WxDataCubeUserCumulate> userCumulateList = cubeService.getUserCumulate(beginDate, endDate);
                return success(userCumulateList, Constant.SUCCESS_MSG);
            case GET_ARTICLE_READ:
                List<WxDataCubeArticleResult> articleResultList = cubeService.getUserRead(beginDate, endDate);
                return success(articleResultList, Constant.SUCCESS_MSG);
            case GET_ARTICLE_SHARE:
                List<WxDataCubeArticleResult> articleResults = cubeService.getUserShare(beginDate, endDate);
                return success(articleResults, Constant.SUCCESS_MSG);
            default:
                return error("method not support");
        }

    }
}
