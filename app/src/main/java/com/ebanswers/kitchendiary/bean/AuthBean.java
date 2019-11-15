package com.ebanswers.kitchendiary.bean;

import java.util.List;

/**
 * Create by Administrator
 * Create date 2019-07-17
 * desc：
 */
public class AuthBean {

    private String content;

    private List<AuthItemBean> authItemBeans;

    public AuthBean(String s) {
        this.content = s;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<AuthItemBean> getAuthItemBeans() {
        return authItemBeans;
    }

    public void setAuthItemBeans(List<AuthItemBean> authItemBeans) {
        this.authItemBeans = authItemBeans;
    }
}
