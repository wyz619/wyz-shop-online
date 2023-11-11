package com.example.shop.service;

import com.example.shop.entity.Category;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shop.vo.CategoryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
public interface CategoryService extends IService<Category> {
List<Category> getIndexCaregoryList();

    List<Category> getIndexCategoryList();

    List<CategoryVO> getCategoryList();
}