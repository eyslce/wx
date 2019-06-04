package com.eyslce.wx.mp.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.commons.util.WxConfigurationProperties;
import com.eyslce.wx.mp.controller.BaseController;
import com.eyslce.wx.mp.domain.MediaFiles;
import com.eyslce.wx.mp.domain.MsgArticle;
import com.eyslce.wx.mp.domain.MsgNews;
import com.eyslce.wx.mp.service.IMsgArticleService;
import com.eyslce.wx.mp.service.IMsgNewsService;
import com.github.pagehelper.PageInfo;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialArticleUpdate;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 图文管理
 */
@Controller
@RequestMapping("admin/msgnews")
public class MsgNewsController extends BaseController {
    @Autowired
    private IMsgNewsService msgNewsService;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxConfigurationProperties wxConfig;
    @Autowired
    private IMsgArticleService articleService;

    @GetMapping("listmultiple")
    public String listMultiple() {
        return "admin/media/document/list-multiple";
    }

    @GetMapping("listsingle")
    public String listSingle() {
        return "admin/media/document/list";
    }

    @GetMapping("addsingle")
    public String addSingle() {
        return "admin/media/document/addsingle";
    }

    @GetMapping("addmultiple")
    public String addMultiple() {
        return "admin/media/document/addmultiple";
    }

    @GetMapping("editsingle")
    public String editSingle() {
        return "admin/media/document/editsingle";
    }

    @GetMapping("editmultiple")
    public String editMultiple() {
        return "admin/media/document/editmultiple";
    }

    @PostMapping("list")
    @ResponseBody
    public HttpResult list(MsgNews searchEntity) {
        PageInfo<MsgNews> pageList = msgNewsService.getWebNewsListByPage(searchEntity);
        return success(pageList);
    }

    @PostMapping("deleteMaterial")
    @ResponseBody
    public HttpResult deleteMaterial(String id) throws WxErrorException {
        MsgNews news = msgNewsService.getById(id);
        boolean res = wxMpService.getMaterialService().materialDelete(news.getMediaId());
        if (res) {
            this.msgNewsService.delete(news);
            return success();
        }
        return error(Constant.ERROR_MSG);
    }

    @PostMapping("addSingleNews")
    @ResponseBody
    public HttpResult addSingleNews(MsgNews msgNews)
            throws Exception {

        String filePath = wxConfig.getUpload_dir() + WxConsts.MediaFileType.IMAGE + "/";

        String description = msgNews.getDescription();
        String description2 = msgNews.getDescription();

        description = description.replaceAll("'", "\"");
        // 去多个img的src值
        String subFilePath = "";
        String subOldFilePath = "";
        if (description.contains("img")) {
            Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
            Matcher m = p.matcher(description);

            while (m.find()) {
                String imgSrc = m.group(1);
                subOldFilePath += imgSrc + ",";
                String[] split = imgSrc.split("/");
                int k = imgSrc.indexOf(split[split.length - 2]);
                String subImgSrc = imgSrc.substring(k, imgSrc.length());
                subFilePath += filePath + subImgSrc + ",";
            }
        }

        if (StringUtils.isNotBlank(subFilePath)) {

            subFilePath = subFilePath.substring(0, subFilePath.length() - 1);
            subOldFilePath = subOldFilePath.substring(0, subOldFilePath.length() - 1);

            // 本地图片地址
            String[] imgPathArry = subFilePath.split(",");
            String[] imgOldPathArry = subOldFilePath.split(",");

            String[] newPathArry = new String[imgPathArry.length];
            for (int i = 0; i < imgPathArry.length; i++) {
                String newFilePath = imgPathArry[i];
                // 将图片上传到微信，返回url
                WxMediaImgUploadResult imgUploadResult = wxMpService.getMaterialService().mediaImgUpload(new File(newFilePath));

                String contentContentUrl = "";
                if (imgUploadResult != null) {
                    // 图片url
                    contentContentUrl = imgUploadResult.getUrl();
                }
                newPathArry[i] = contentContentUrl;
            }

            for (int i = 0; i < imgPathArry.length; i++) {
                description = description.replace(imgOldPathArry[i], newPathArry[i]);
            }
        }

        // 内容保存
        msgNews.setDescription(description);
        // 封面图片媒体id
        String imgMediaId = msgNews.getThumbMediaId();

        WxMpMaterialNews news = new WxMpMaterialNews();
        WxMpMaterialNews.WxMpMaterialNewsArticle article = new WxMpMaterialNews.WxMpMaterialNewsArticle();
        article.setThumbMediaId(imgMediaId);
        article.setAuthor(msgNews.getAuthor());
        article.setTitle(msgNews.getTitle());
        article.setContentSourceUrl(msgNews.getFromurl());
        article.setDigest(msgNews.getBrief());
        article.setShowCoverPic(msgNews.getShowpic().equals(1));
        article.setNeedOpenComment(msgNews.getOpencomment().equals(1));
        article.setOnlyFansCanComment(msgNews.getFanscancomment().equals(1));
        article.setContent(msgNews.getDescription());
        news.addArticle(article);
        WxMpMaterialUploadResult result = wxMpService.getMaterialService().materialNewsUpload(news);

        if (result != null) {
            String newsMediaId = result.getMediaId();
            WxMpMaterialNews newsResult = wxMpService.getMaterialService().materialNewsInfo(newsMediaId);

            List<WxMpMaterialNews.WxMpMaterialNewsArticle> articles = newsResult.getArticles();
            WxMpMaterialNews.WxMpMaterialNewsArticle newsArticle = articles.get(0);
            MsgNews newsPo = new MsgNews();
            newsPo.setMultType(1);// 指定为1，代表单图文
            newsPo.setTitle(newsArticle.getTitle());
            newsPo.setAuthor(newsArticle.getAuthor());
            newsPo.setBrief(newsArticle.getDigest());
            newsPo.setDescription(description2);
            newsPo.setFromurl(newsArticle.getContentSourceUrl());
            newsPo.setUrl(newsArticle.getUrl());
            newsPo.setShowpic(newsArticle.isShowCoverPic() ? 1 : 0);
            newsPo.setPicpath(msgNews.getPicpath());
            newsPo.setMediaId(newsMediaId);
            newsPo.setThumbMediaId(imgMediaId);
            newsPo.setNewsIndex(0);

            MediaFiles entity = new MediaFiles();
            entity.setMediaId(newsMediaId);
            entity.setMediaType("news");
            entity.setCreateTime(newsResult.getCreatedTime());
            entity.setUpdateTime(newsResult.getUpdatedTime());

            int resultCount = this.msgNewsService.addSingleNews(newsPo, entity);

            if (resultCount > 0) {
                return success();
            }
            return error(Constant.ERROR_MSG);

        }
        return error(Constant.ERROR_MSG);

    }

    @PostMapping("addMoreNews")
    @ResponseBody
    public HttpResult addMoreNews(String rows) throws Exception {

        String filePath = wxConfig.getUpload_dir() + WxConsts.MediaFileType.IMAGE + "/";

        List<MsgArticle> listArts = new ArrayList<>();// 数据库所有图文集合
        JSONArray arrays = JSONArray.parseArray(rows);
        MsgNews msgNew = new MsgNews();
        WxMpMaterialNews news = new WxMpMaterialNews();
        for (int i = 0; i < arrays.size(); i++) {
            JSONObject obj = arrays.getJSONObject(i);

            WxMpMaterialNews.WxMpMaterialNewsArticle article = new WxMpMaterialNews.WxMpMaterialNewsArticle();
            article.setTitle(obj.getString("title"));
            article.setAuthor(obj.getString("author"));
            article.setThumbMediaId(obj.getString("thumbMediaId") == null ? "" : obj.getString("thumbMediaId"));
            article.setDigest(obj.getString("brief") == null ? "" : obj.getString("brief"));
            article.setShowCoverPic(obj.getIntValue("showpic") == 1);
            article.setContentSourceUrl(obj.getString("fromurl") == null ? "" : obj.getString("fromurl"));

            MsgArticle art = new MsgArticle();
            art.setNewsIndex(i);
            art.setTitle(obj.getString("title"));
            art.setAuthor(obj.getString("author") == null ? "" : obj.getString("author"));
            art.setContentSourceUrl(obj.getString("fromurl") == null ? "" : obj.getString("fromurl"));
            art.setDigest(obj.getString("brief") == null ? "" : obj.getString("brief"));
            art.setPicUrl(obj.getString("picpath") == null ? "" : obj.getString("picpath"));
            art.setShowCoverPic(obj.getIntValue("showpic"));
            art.setThumbMediaId(obj.getString("thumbMediaId") == null ? "" : obj.getString("thumbMediaId"));
            art.setContent(obj.getString("description") == null ? "" : obj.getString("description"));
            if (i == 0) {
                msgNew.setAuthor(art.getAuthor());
                msgNew.setBrief(art.getDigest());
                msgNew.setDescription(art.getContent());
                msgNew.setFromurl(art.getContentSourceUrl());
                msgNew.setMultType(2);
                msgNew.setPicpath(art.getPicUrl());
                msgNew.setShowpic(art.getShowCoverPic());
                msgNew.setTitle(art.getTitle());
                msgNew.setThumbMediaId(art.getThumbMediaId());
            }
            // 注意这是图文正文部分
            String description = obj.getString("description") == null ? "" : obj.getString("description");
            description = description.replaceAll("'", "\"");
            // 去多个img的src值
            String subFilePath = "";
            String subOldFilePath = "";
            if (description.contains("img")) {
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher m = p.matcher(description);

                while (m.find()) {
                    String imgSrc = m.group(1);
                    subOldFilePath += imgSrc + ",";
                    String[] split = imgSrc.split("/");
                    int k = imgSrc.indexOf(split[split.length - 2]);
                    String subImgSrc = imgSrc.substring(k, imgSrc.length());
                    subFilePath += filePath + subImgSrc + ",";
                }
            }
            if (StringUtils.isNotBlank(subFilePath)) {
                subFilePath = subFilePath.substring(0, subFilePath.length() - 1);
                subOldFilePath = subOldFilePath.substring(0, subOldFilePath.length() - 1);
                // 本地图片地址
                String[] imgPathArry = subFilePath.split(",");
                String[] imgOldPathArry = subOldFilePath.split(",");

                String[] newPathArry = new String[imgPathArry.length];
                for (int j = 0; j < imgPathArry.length; j++) {
                    String newFilePath = imgPathArry[j];
                    // 将图片上传到微信，返回url
                    WxMediaImgUploadResult imgUploadResult = wxMpService.getMaterialService().mediaImgUpload(new File(newFilePath));

                    String contentContentUrl = "";
                    if (imgUploadResult != null) {
                        // 图片url
                        contentContentUrl = imgUploadResult.getUrl();
                    }
                    newPathArry[j] = contentContentUrl;
                }

                for (int j = 0; j < imgPathArry.length; j++) {
                    description = description.replace(imgOldPathArry[j], newPathArry[j]);
                }
            }
            article.setContent(description);
            news.addArticle(article);
            listArts.add(art);
        }
        // 添加多图文永久素材
        WxMpMaterialUploadResult result = wxMpService.getMaterialService().materialNewsUpload(news);
        // 数据库存储使用
        List<MsgArticle> listArticles = new ArrayList<MsgArticle>();// 数据库所有图文集合
        if (result != null) {
            String newsMediaId = result.getMediaId();
            msgNew.setMediaId(newsMediaId);
            WxMpMaterialNews newsResult = wxMpService.getMaterialService().materialNewsInfo(newsMediaId);
            List<WxMpMaterialNews.WxMpMaterialNewsArticle> articles = newsResult.getArticles();

            for (int i = 0; i < articles.size(); i++) {
                WxMpMaterialNews.WxMpMaterialNewsArticle article = articles.get(i);
                if (i == 0) {
                    msgNew.setUrl(article.getUrl());
                }
                MsgArticle msgart = listArts.get(i);
                msgart.setUrl(article.getUrl());
                msgart.setMediaId(newsMediaId);
                listArticles.add(msgart);
            }
            msgNew.setArticles(listArticles);

            int bl = this.msgNewsService.addMoreNews(msgNew);
            if (bl == 1) {
                return success();
            }
        }
        return error(Constant.ERROR_MSG);
    }

    @PostMapping("toUpdateSingleNews")
    @ResponseBody
    public HttpResult toUpdateSingleNews(String id) {
        MsgNews newsObj = msgNewsService.getById(id);
        return success(newsObj, Constant.SUCCESS_MSG);
    }

    /**
     * 更新单图文素材
     */
    @PostMapping("updateSingleNews")
    @ResponseBody
    public HttpResult updateSingleNews(MsgNews msgNews) throws Exception {
        String filePath = wxConfig.getUpload_dir() + WxConsts.MediaFileType.IMAGE + "/";

        String description = msgNews.getDescription();
        String description2 = msgNews.getDescription();
        description = description.replaceAll("'", "\"");
        // 去多个img的src值
        String subFilePath = "";
        String subOldFilePath = "";
        if (description.contains("img")) {
            Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
            Matcher m = p.matcher(description);

            while (m.find()) {
                String imgSrc = m.group(1);
                subOldFilePath += imgSrc + ",";
                String[] split = imgSrc.split("/");
                int k = imgSrc.indexOf(split[split.length - 2]);
                String subImgSrc = imgSrc.substring(k, imgSrc.length());
                subFilePath += filePath + subImgSrc + ",";
            }
        }
        if (StringUtils.isNotBlank(subFilePath)) {
            subFilePath = subFilePath.substring(0, subFilePath.length() - 1);
            subOldFilePath = subOldFilePath.substring(0, subOldFilePath.length() - 1);
            // 本地图片地址
            String[] imgPathArry = subFilePath.split(",");
            String[] imgOldPathArry = subOldFilePath.split(",");

            String[] newPathArry = new String[imgPathArry.length];
            for (int i = 0; i < imgPathArry.length; i++) {
                String newFilePath = imgPathArry[i];
                // 添加永久图片
                // 将图片上传到微信，返回url
                WxMediaImgUploadResult imgUploadResult = wxMpService.getMaterialService().mediaImgUpload(new File(newFilePath));

                String contentContentUrl = "";
                if (imgUploadResult != null) {
                    // 图片url
                    contentContentUrl = imgUploadResult.getUrl();
                }
                newPathArry[i] = contentContentUrl;
            }

            for (int i = 0; i < imgPathArry.length; i++) {
                description = description.replace(imgOldPathArry[i], newPathArry[i]);
            }
        }

        // 内容保存
        msgNews.setDescription(description);
        String mediaId = msgNews.getMediaId();

        WxMpMaterialArticleUpdate articleUpdate = new WxMpMaterialArticleUpdate();
        WxMpMaterialNews.WxMpMaterialNewsArticle article = new WxMpMaterialNews.WxMpMaterialNewsArticle();
        article.setThumbMediaId(msgNews.getThumbMediaId());
        article.setAuthor(msgNews.getAuthor());
        article.setTitle(msgNews.getTitle());
        article.setContentSourceUrl(msgNews.getFromurl());
        article.setDigest(msgNews.getBrief());
        article.setShowCoverPic(msgNews.getShowpic().equals(1));
        article.setNeedOpenComment(msgNews.getOpencomment().equals(1));
        article.setOnlyFansCanComment(msgNews.getFanscancomment().equals(1));
        article.setContent(description);

        articleUpdate.setArticles(article);
        articleUpdate.setMediaId(mediaId);
        articleUpdate.setIndex(0);

        boolean result = wxMpService.getMaterialService().materialNewsUpdate(articleUpdate);

        if (result) {
            msgNews.setDescription(description2);
            this.msgNewsService.updateSingleNews(msgNews);
            return success();
        }
        return error(Constant.ERROR_MSG);
    }


    @PostMapping("toUpdateMoreNews")
    @ResponseBody
    public HttpResult toUpdateMoreNews(String id) {
        MsgNews newsObj = msgNewsService.getById(id);
        return success(newsObj.getArticles(), Constant.SUCCESS_MSG);
    }

    /**
     * 更新多图文
     */
    @PostMapping("updateSubMoreNews")
    @ResponseBody
    public HttpResult updateMoreNews(String rows, HttpServletRequest request) throws Exception {
        String filePath = wxConfig.getUpload_dir() + WxConsts.MediaFileType.IMAGE + "/";
        MsgArticle article = (MsgArticle) JSONObject.parseObject(rows, MsgArticle.class);
        String description = article.getContent();
        String description2 = article.getContent();
        description = description.replaceAll("'", "\"");
        // 去多个img的src值
        String subFilePath = "";
        String subOldFilePath = "";
        if (description.contains("img")) {
            Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
            Matcher m = p.matcher(description);

            while (m.find()) {
                String imgSrc = m.group(1);
                subOldFilePath += imgSrc + ",";
                String[] split = imgSrc.split("/");
                int k = imgSrc.indexOf(split[split.length - 2]);
                String subImgSrc = imgSrc.substring(k, imgSrc.length());
                subFilePath += filePath + subImgSrc + ",";
            }
        }
        if (StringUtils.isNotBlank(subFilePath)) {

            subFilePath = subFilePath.substring(0, subFilePath.length() - 1);
            subOldFilePath = subOldFilePath.substring(0, subOldFilePath.length() - 1);

            // 本地图片地址
            String[] imgPathArry = subFilePath.split(",");
            String[] imgOldPathArry = subOldFilePath.split(",");

            String[] newPathArry = new String[imgPathArry.length];
            for (int i = 0; i < imgPathArry.length; i++) {
                String newFilePath = imgPathArry[i];

                // 将图片上传到微信，返回url
                WxMediaImgUploadResult imgUploadResult = wxMpService.getMaterialService().mediaImgUpload(new File(newFilePath));

                String contentContentUrl = "";
                if (imgUploadResult != null) {
                    // 图片url
                    contentContentUrl = imgUploadResult.getUrl();
                }
                newPathArry[i] = contentContentUrl;
            }

            for (int i = 0; i < imgPathArry.length; i++) {
                description = description.replace(imgOldPathArry[i], newPathArry[i]);
            }
        }

        // 内容保存
        article.setContent(description);

        WxMpMaterialArticleUpdate articleUpdate = new WxMpMaterialArticleUpdate();
        WxMpMaterialNews.WxMpMaterialNewsArticle newsArticle = new WxMpMaterialNews.WxMpMaterialNewsArticle();
        newsArticle.setThumbMediaId(article.getThumbMediaId());
        newsArticle.setAuthor(article.getAuthor());
        newsArticle.setTitle(article.getTitle());
        newsArticle.setContentSourceUrl(article.getContentSourceUrl());
        newsArticle.setDigest(article.getDigest());
        newsArticle.setShowCoverPic(article.getShowCoverPic().equals(1));
        newsArticle.setContent(article.getContent());
        articleUpdate.setArticles(newsArticle);
        articleUpdate.setIndex(article.getNewsIndex());
        articleUpdate.setMediaId(article.getMediaId());

        boolean result = wxMpService.getMaterialService().materialNewsUpdate(articleUpdate);

        if (result) {
            article.setContent(description2);
            // 更新成功
            this.articleService.update(article);
            // 修改图文news表数据
            MsgNews msgNews = this.msgNewsService.getById(String.valueOf(article.getNewsId()));
            List<MsgArticle> newArticles = msgNews.getArticles();
            if (newArticles.get(0).getArId() == article.getArId()) {
                // 这里只修改title 为了模糊查询的时候可以查询到数据
                msgNews.setTitle(article.getTitle());
                this.msgNewsService.updateMediaId(msgNews);
            }
            return success();
        }
        return error(Constant.ERROR_MSG);

    }

    @ResponseBody
    @RequestMapping("uploadFile/{type}")
    public Map uploadFile(MultipartFile file, @PathVariable("type") String type) throws Exception {
        //原文件名称
        String trueName = file.getOriginalFilename();
        //文件后缀名
        String ext = FilenameUtils.getExtension(trueName);
        String path = wxConfig.getUpload_dir() + type;
        String name = System.currentTimeMillis() + "." + ext;

        //生成新名称
        File newFile = new File(path, name);
        if (!newFile.exists()) {
            FileUtils.forceMkdirParent(newFile);
        }
        file.transferTo(newFile);
        //构造返回参数
        Map<String, Object> map = new HashMap();
        Map<String, Object> mapData = new HashMap();
        map.put("code", 0);//0表示成功，1失败
        map.put("msg", "上传成功");//提示消息
        map.put("data", mapData);//提示消息
        mapData.put("src", "/admin/file/" + type + "/" + name);//图片url
        mapData.put("title", name);//图片名称，这个会显示在输入框里
        return map;
    }

}
