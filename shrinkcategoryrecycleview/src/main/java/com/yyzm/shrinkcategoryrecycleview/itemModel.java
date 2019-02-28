package com.yyzm.shrinkcategoryrecycleview;

/**
 * 作者 DGW
 * 创建时间 2018/1/5
 * 本类相关 在使用此自定view时一定要使用此model
 */

public class itemModel {
    public String word;//内容
    public String tag;//所属类型 ()
    public int drawableid;//放置图片的（可用于流布局菜单）
    public int singleline;//是否是单行[1:单行，2：非单行]

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getDrawableid() {
        return drawableid;
    }

    public void setDrawableid(int drawableid) {
        this.drawableid = drawableid;
    }

    public int getSingleline() {
        return singleline;
    }

    public void setSingleline(int singleline) {
        this.singleline = singleline;
    }
}
