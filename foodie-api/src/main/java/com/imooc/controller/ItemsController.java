package com.imooc.controller;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommnetLevelCountVO;
import com.imooc.pojo.vo.ItemInfoVO;
import com.imooc.service.ItemsService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName IndexController
 * Description
 * Create by yanjie14
 * Date 2021/5/23 2:37 下午
 */
@Api(value = "商品接口", tags = {"商品信息展示展示的相关接口"})
@RestController
@RequestMapping("/items")
public class ItemsController extends BaseController{

    @Autowired
    private ItemsService itemsService;


    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @ApiParam(name = "itemId",value = "商品id",required = true)
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult info(@PathVariable String itemId) {
        if(StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }
        Items items = itemsService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemsService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemsService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemsService.queryItemsParam(itemId);
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItems(items);
        itemInfoVO.setItemsImgList(itemsImgList);
        itemInfoVO.setItemsSpecList(itemsSpecList);
        itemInfoVO.setItemsParam(itemsParam);
        return IMOOCJSONResult.ok(itemInfoVO);
    }



    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @ApiParam(name = "itemId",value = "商品id",required = true)
    @GetMapping("/commentLevel")
    public IMOOCJSONResult commentLevel(@RequestParam String itemId) {
        if(StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }
        CommnetLevelCountVO countVO = itemsService.queryCommentCounts(itemId);
        return IMOOCJSONResult.ok(countVO);
    }

    @ApiOperation(value = "查询商品评价", notes = "查询商品评价", httpMethod = "GET")
    @ApiParam(name = "itemId",value = "商品id",required = true)
    @GetMapping("/comments")
    public IMOOCJSONResult comments(@RequestParam(name = "itemId",required = true) String itemId,
                                    @RequestParam(name = "level",required = false) Integer level,
                                    @RequestParam(name = "page",required = false) Integer page,
                                    @RequestParam(name = "pageSize",required = false) Integer pageSize) {
        if(StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }
        if(page==null){
            page =1 ;
        }
        if(pageSize==null){
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult result = itemsService.queryPagedComments(itemId,level,page,pageSize);
        return IMOOCJSONResult.ok(result);
    }
}
