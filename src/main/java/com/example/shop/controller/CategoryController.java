package com.example.shop.controller;

import com.example.shop.common.result.Result;
import com.example.shop.entity.Category;
import com.example.shop.service.CategoryService;
import com.example.shop.vo.CategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
@Tag(name = "分类管理")
@RestController
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @GetMapping("index")
    @Operation(summary = "首页-商品分类")
    public Result<List<Category>> getIndexCategoryList() {
        List<Category> list = categoryService.getIndexCategoryList();
        return Result.ok(list);
    }
    @GetMapping("top")
    @Operation(summary = "tab分类页-商品分类")
    public Result<List<CategoryVO>> getList() {
        List<CategoryVO> list = categoryService.getCategoryList();
        return Result.ok(list);
    }

}
