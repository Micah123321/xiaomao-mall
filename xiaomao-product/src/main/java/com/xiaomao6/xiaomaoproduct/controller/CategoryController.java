package com.xiaomao6.xiaomaoproduct.controller;

import com.xiaomao6.common.utils.R;
import com.xiaomao6.xiaomaoproduct.entity.CategoryEntity;
import com.xiaomao6.xiaomaoproduct.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

//import org.apache.shiro.authz.annotation.RequiresPermissions;



/**
 * 商品三级分类
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:10
 */
@RestController
@RequestMapping("xiaomaoproduct/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 分类树形列表获取
     */
    @RequestMapping("/list/tree")
   // @RequiresPermissions("xiaomaoproduct:category:list")
    public R list(){
        List<CategoryEntity> list=categoryService.getTreeList();
        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    //@RequiresPermissions("xiaomaoproduct:category:info")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("xiaomaoproduct:category:save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }
    /**
     * 批量修改排序 层级
     */
    @RequestMapping("/update/sort")
    public R updateSort(@RequestBody CategoryEntity[] categories){
        //updateBatchById 方法直接传递数组就可以批量更新
        categoryService.updateBatchById(Arrays.asList(categories));
        return R.ok();
    }
    /**
     * 修改
     */
    @RequestMapping("/update")
   // @RequiresPermissions("xiaomaoproduct:category:update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
   // @RequiresPermissions("xiaomaoproduct:category:delete")
    public R delete(@RequestBody Long[] catIds){
//		categoryService.removeByIds(Arrays.asList(catIds));

		categoryService.removeByIdList(Arrays.asList(catIds));

        return R.ok();
    }

}
