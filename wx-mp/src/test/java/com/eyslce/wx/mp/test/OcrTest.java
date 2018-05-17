package com.eyslce.wx.mp.test;

import com.eyslce.wx.mp.WxMpApplication;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WxMpApplication.class)
public class OcrTest {

    @Test
    public void test() {
        ITesseract instance = new Tesseract();
        //如果未将tessdata放在根目录下需要指定绝对路径
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        instance.setDatapath(tessDataFolder.getAbsolutePath());
        // 我们需要指定识别语种
        instance.setLanguage("eng");
        // 指定识别图片
        File imgDir = new File("C:/workspace/wx/db.jpeg");

        long startTime = System.currentTimeMillis();
        String ocrResult = null;
        try {
            ocrResult = instance.doOCR(imgDir);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        // 输出识别结果
        System.out.println("OCR Result: \n" + ocrResult + "\n 耗时：" + (System.currentTimeMillis() - startTime) + "ms");
    }
}
