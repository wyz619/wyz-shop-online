package com.example.shop.service;

import com.example.shop.common.result.PageResult;
import com.example.shop.entity.Goods;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shop.query.Query;
import com.example.shop.query.RecommendByTabGoodsQuery;
import com.example.shop.vo.GoodsVO;
import com.example.shop.vo.IndexTabRecommendVO;
import com.example.shop.vo.RecommendGoodsVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
public interface GoodsService extends IService<Goods> {
        PageResult<RecommendGoodsVO> getRecommendGoodsByPage(Query query);

        GoodsVO getGoodsDetail(Integer id);
        IndexTabRecommendVO getTabRecommendGoodsByTabId(RecommendByTabGoodsQuery query);
}
