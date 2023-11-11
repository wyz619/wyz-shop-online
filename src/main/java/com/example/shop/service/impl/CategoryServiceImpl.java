package com.example.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.shop.convert.GoodsConvert;
import com.example.shop.entity.Category;
import com.example.shop.entity.Goods;
import com.example.shop.enums.CategoryRecommendEnum;
import com.example.shop.mapper.CategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shop.mapper.GoodsMapper;
import com.example.shop.service.CategoryService;
import com.example.shop.vo.CategoryChildrenGoodsVO;
import com.example.shop.vo.CategoryVO;
import com.example.shop.vo.RecommendGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired(required = false)
    private GoodsMapper goodsMapper;
    @Override
    public List<Category> getIndexCaregoryList() {
        LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper<>();

        wrapper.eq(Category::getIsRecommend, CategoryRecommendEnum.ALL_RECOMMEND);
        wrapper.orderByDesc(Category::getCreateTime);
        List<Category> list =baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<Category> getIndexCategoryList() {
        LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper<>();

        wrapper.eq(Category::getIsRecommend, CategoryRecommendEnum.ALL_RECOMMEND);
        wrapper.orderByDesc(Category::getCreateTime);
        List<Category> list =baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<CategoryVO> getCategoryList() {
        List<CategoryVO> list = new ArrayList<>();
//        1、查询配置在分类tab页上的 父级分类
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getIsRecommend, CategoryRecommendEnum.ALL_RECOMMEND.getValue()).or().eq(Category::getIsRecommend, CategoryRecommendEnum.CATEGORY_HOME_RECOMMEND.getValue());
        List<Category> categories = baseMapper.selectList(wrapper);
//        2、查询该分类下的自己分类
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        for (Category category : categories) {
            CategoryVO categoryVO = new CategoryVO();
            categoryVO.setId(category.getId());
            categoryVO.setName(category.getName());
            categoryVO.setIcon(category.getIcon());
            wrapper.clear();
            wrapper.eq(Category::getParentId, category.getId());
            List<Category> childCategories = baseMapper.selectList(wrapper);
            List<CategoryChildrenGoodsVO> categoryChildrenGoodsList = new ArrayList<>();
//            3、分类下的商品列表
            for (Category item : childCategories) {
                CategoryChildrenGoodsVO childrenGoodsVO = new CategoryChildrenGoodsVO();
                childrenGoodsVO.setId(item.getId());
                childrenGoodsVO.setName(item.getName());
                childrenGoodsVO.setIcon(item.getIcon());
                childrenGoodsVO.setParentId(category.getId());
                childrenGoodsVO.setParentName(category.getName());
                queryWrapper.clear();
                List<Goods> goodsList = goodsMapper.selectList(queryWrapper.eq(Goods::getCategoryId, item.getId()));
                List<RecommendGoodsVO> goodsVOList = GoodsConvert.INSTANCE.convertToRecommendGoodsVOList(goodsList);
                childrenGoodsVO.setGoods(goodsVOList);
                categoryChildrenGoodsList.add(childrenGoodsVO);

            }
            categoryVO.setChildren(categoryChildrenGoodsList);
            list.add(categoryVO);
        }

        return list;
    }
}