package com.example.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.shop.entity.IndexCarousel;
import com.example.shop.mapper.IndexCarouselMapper;
import com.example.shop.service.IndexCarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
@Service
public class IndexCarouselServiceImpl extends ServiceImpl<IndexCarouselMapper, IndexCarousel> implements IndexCarouselService {

    @Override
    public List<IndexCarousel> getlist(Integer distributionSite) {
        LambdaQueryWrapper<IndexCarousel>wrapper = new LambdaQueryWrapper<>();
        if(distributionSite != null){
            wrapper.eq(IndexCarousel::getType,distributionSite);
        }
        wrapper.orderByDesc(IndexCarousel::getCreateTime);
        List<IndexCarousel> list=baseMapper.selectList(wrapper);
        return  list;
    }

    @Override
    public List<IndexCarousel> getList(Integer distributionSite) {
        return null;
    }
}
