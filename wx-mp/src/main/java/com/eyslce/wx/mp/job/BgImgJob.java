package com.eyslce.wx.mp.job;

import com.eyslce.wx.commons.util.FileOperation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BgImgJob {

    /**
     *
     */
    @Scheduled(cron = "0 8 * * * ?")
    public void cronJob() {
        FileOperation.downBingImg();
    }
}
