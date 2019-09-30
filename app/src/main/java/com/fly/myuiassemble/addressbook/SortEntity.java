package com.fly.myuiassemble.addressbook;

import com.fly.viewlibrary.sortlist.SortModel;

/**
 * 包    名 : com.fly.myuiassemble.adapter
 * 作    者 : FLY
 * 创建时间 : 2019/9/26
 * 描述:
 */
public class SortEntity extends SortModel {

    private String name;   //显示的文本
    private String sort;  //显示数据拼音的首字母
    private String photo;  //显示数据拼音的首字母

    public SortEntity() {
    }

    public SortEntity(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }

    @Override
    public String getExhibition() {
        return name;
    }

    @Override
    public String getSort() {
        return sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
