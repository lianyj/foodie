package com.imooc.service;

import com.imooc.pojo.Carousel;

import java.util.List;

/**
 * ClassName CarouselService
 * Description
 * Create by yanjie14
 * Date 2021/5/23 2:30 下午
 */
public interface CarouselService {

    /**
     * 查询所有轮播图列表
     * @param isShow
     * @return
     */
    public List<Carousel> queryAll(Integer isShow);


}
