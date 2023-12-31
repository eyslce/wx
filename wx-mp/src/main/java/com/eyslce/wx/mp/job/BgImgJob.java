package com.eyslce.wx.mp.job;

import com.eyslce.wx.commons.util.FileOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BgImgJob {
    @Autowired
    private FileOperation fileOperation;


    @Scheduled(cron = "0 0 8 * * ?")
    public void cronJob() {
        fileOperation.downBingImg();
    }
}
