package com.example.shop.service;

import com.example.shop.entity.IndexRecommend;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shop.vo.IndexRecommendVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
public interface IndexRecommendService extends IService<IndexRecommend> {
        List<IndexRecommendVO> getList();
}
