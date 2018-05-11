package com.eyslce.wx.commons.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TulingApiResult {
    private Intent intent;
    private List<Result> results;
}
