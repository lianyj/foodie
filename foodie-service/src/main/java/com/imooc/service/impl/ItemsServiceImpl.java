package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.CommentLevel;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommnetLevelCountVO;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.service.ItemsService;
import com.imooc.utils.DesensitizationUtil;
import com.imooc.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName ItemsServiceImpl
 * Description
 * Create by yanjie14
 * Date 2021/5/23 9:10 下午
 */
@Service
public class ItemsServiceImpl implements ItemsService {

    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemsParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommnetLevelCountVO queryCommentCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId,CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId,CommentLevel.BAD.type);
        Integer totalCounts = goodCounts+normalCounts+badCounts;

        CommnetLevelCountVO commnetLevelCountVO = new CommnetLevelCountVO();
        commnetLevelCountVO.setGoodCounts(goodCounts);
        commnetLevelCountVO.setNormalCounts(normalCounts);
        commnetLevelCountVO.setBadCounts(badCounts);
        commnetLevelCountVO.setTotalCounts(totalCounts);

        return commnetLevelCountVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level,Integer page,Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("itemId",itemId);
        map.put("level",level);
        //mybatis-pagehelper
        PageHelper.startPage(page,pageSize);
        List<ItemCommentVO> list = itemsMapperCustom.queryItemComments(map);
        for (ItemCommentVO commentVO :list){
            commentVO.setNickname(DesensitizationUtil.commonDisplay(commentVO.getNickname()));
        }
        return setterPagedGrid(list,page);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer getCommentCounts(String itemId,Integer level){
        ItemsComments comments = new ItemsComments();
        comments.setItemId(itemId);
        if(level != null){
            comments.setCommentLevel(level);
        }
       return itemsCommentsMapper.selectCount(comments);
    }

    private PagedGridResult setterPagedGrid(List<?> list,Integer page){
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult result = new PagedGridResult();
        result.setPage(page);
        result.setRows(list);
        result.setTotal(pageList.getPages());
        result.setRecords(pageList.getTotal());
        return result;
    }
}
