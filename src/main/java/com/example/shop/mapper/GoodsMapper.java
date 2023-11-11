package com.example.shop.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shop.entity.Goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
public interface  GoodsMapper extends BaseMapper<Goods> {

    Page<Goods> selectage(Page<Goods> page, LambdaQueryWrapper<Goods> eq);
}
