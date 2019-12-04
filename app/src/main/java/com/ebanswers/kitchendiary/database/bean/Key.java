package com.ebanswers.kitchendiary.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author caixd
 * @date 2019/9/3
 * PS:
 */
@Entity
public class Key {
    @Id(autoincrement = true)
    private Long id;
    private String key;

    @Generated(hash = 439209516)
    public Key(Long id, String key) {
        this.id = id;
        this.key = key;
    }

    @Generated(hash = 2076226027)
    public Key() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
