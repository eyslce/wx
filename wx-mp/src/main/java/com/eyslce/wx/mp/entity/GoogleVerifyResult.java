package com.eyslce.wx.mp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoogleVerifyResult {
    private boolean success;
    private String challenge_ts;
    private String hostname;
    @JsonProperty("error-codes")
    private List<String> errorCodes;
}
