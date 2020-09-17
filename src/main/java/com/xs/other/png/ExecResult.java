package com.xs.other.png;

/**
 * @author xiongshun
 * create-time: 2020-09-15 14:11
 */
public class ExecResult {
    private String resultMsg;
    private Integer code; // 0=succ

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
