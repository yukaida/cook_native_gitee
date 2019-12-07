/**
  * Copyright 2019 bejson.com 
  */
package com.ebanswers.kitchendiary.bean.Topics;
import java.util.List;

/**
 * Auto-generated: 2019-12-02 19:41:53
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Topics {

    private int code;
    private List<NormalTopics> data;
    private List<LastTopics> lastTopics;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setData(List<NormalTopics> data) {
         this.data = data;
     }
     public List<NormalTopics> getData() {
         return data;
     }

    public void setLastTopics(List<LastTopics> lastTopics) {
         this.lastTopics = lastTopics;
     }
     public List<LastTopics> getLastTopics() {
         return lastTopics;
     }


}