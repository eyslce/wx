package com.eyslce.wx.commons.util;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import java.io.File;

public class Ocr {

    private static ITesseract instance = new Tesseract();

    public Ocr() {
        new Ocr(Constant.Lang.Eng);
    }

    public Ocr(Constant.Lang lang) {
        //如果未将tessdata放在根目录下需要指定绝对路径
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        instance.setDatapath(tessDataFolder.getAbsolutePath());
        // 我们需要指定识别语种
        instance.setLanguage(lang.getIndex());
    }

    public String doOcr(String file) throws TesseractException {
        // 指定识别图片
        File imgDir = new File(file);
        String ocrResult = instance.doOCR(imgDir);
        return ocrResult;
    }
}
