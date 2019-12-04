package com.ebanswers.kitchendiary.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author caixd
 * @date 2019/9/27
 * PS:
 */
@Entity
public class AD {
    @Id
    private Long id;
    private String name;
    private String app;
    private String image;
    private String url;
    private String info;
    private int show_time;
    private String start_data;
    private String end_data;
    private boolean isRead;

    @Generated(hash = 1448363040)
    public AD(Long id, String name, String app, String image, String url,
            String info, int show_time, String start_data, String end_data,
            boolean isRead) {
        this.id = id;
        this.name = name;
        this.app = app;
        this.image = image;
        this.url = url;
        this.info = info;
        this.show_time = show_time;
        this.start_data = start_data;
        this.end_data = end_data;
        this.isRead = isRead;
    }

    @Generated(hash = 293030498)
    public AD() {
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getShow_time() {
        return show_time;
    }

    public void setShow_time(int show_time) {
        this.show_time = show_time;
    }

    public String getStart_data() {
        return start_data;
    }

    public void setStart_data(String start_data) {
        this.start_data = start_data;
    }

    public String getEnd_data() {
        return end_data;
    }

    public void setEnd_data(String end_data) {
        this.end_data = end_data;
    }

    public boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "AD{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", app='" + app + '\'' +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", info='" + info + '\'' +
                ", show_time=" + show_time +
                ", start_data='" + start_data + '\'' +
                ", end_data='" + end_data + '\'' +
                ", isRead=" + isRead +
                '}';
    }
}
