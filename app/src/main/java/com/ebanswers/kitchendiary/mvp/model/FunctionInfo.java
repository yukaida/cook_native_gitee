package com.ebanswers.kitchendiary.mvp.model;

/**
 * Create by dongli
 * Create date 2019-09-23
 * desc：
 */
public class FunctionInfo {

    private String functionName;

    private int functionIv;

    public FunctionInfo(String functionName, int functionIv) {
        this.functionName = functionName;
        this.functionIv = functionIv;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public int getFunctionIv() {
        return functionIv;
    }

    public void setFunctionIv(int functionIv) {
        this.functionIv = functionIv;
    }
}
