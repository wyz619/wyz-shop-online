package com.example.shop.service;

import com.example.shop.entity.IndexCarousel;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
public interface IndexCarouselService extends IService<IndexCarousel> {
        List<IndexCarousel> getlist(Integer distributionSite);

    List<IndexCarousel> getList(Integer distributionSite);
}
