package com.imooc.pojo.vo;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;

import java.util.List;

/**
 * 商品详情vo
 * ClassName SimpleItemVO
 * Description
 * Create by yanjie14
 * Date 2021/5/23 8:49 下午
 */
public class ItemInfoVO {

    private Items items;
    private List<ItemsImg> itemsImgList;
    private List<ItemsSpec> itemsSpecList;
    private ItemsParam itemsParam;

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public List<ItemsImg> getItemsImgList() {
        return itemsImgList;
    }

    public void setItemsImgList(List<ItemsImg> itemsImgList) {
        this.itemsImgList = itemsImgList;
    }

    public List<ItemsSpec> getItemsSpecList() {
        return itemsSpecList;
    }

    public void setItemsSpecList(List<ItemsSpec> itemsSpecList) {
        this.itemsSpecList = itemsSpecList;
    }

    public ItemsParam getItemsParam() {
        return itemsParam;
    }

    public void setItemsParam(ItemsParam itemsParam) {
        this.itemsParam = itemsParam;
    }
}
