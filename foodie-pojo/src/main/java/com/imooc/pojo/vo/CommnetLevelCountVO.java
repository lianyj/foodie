package com.imooc.pojo.vo;

/**
 * 用于展示商品评价数量的vo
 * Description
 * Create by yanjie14
 * Date 2021/6/22 10:48 下午
 */

public class CommnetLevelCountVO {

    private Integer totalCounts;

    private Integer goodCounts;

    private Integer normalCounts;

    private Integer badCounts;

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }

    public Integer getGoodCounts() {
        return goodCounts;
    }

    public void setGoodCounts(Integer goodCounts) {
        this.goodCounts = goodCounts;
    }

    public Integer getNormalCounts() {
        return normalCounts;
    }

    public void setNormalCounts(Integer normalCounts) {
        this.normalCounts = normalCounts;
    }

    public Integer getBadCounts() {
        return badCounts;
    }

    public void setBadCounts(Integer badCounts) {
        this.badCounts = badCounts;
    }
}
