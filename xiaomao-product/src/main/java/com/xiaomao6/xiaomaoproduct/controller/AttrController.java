package com.xiaomao6.xiaomaoproduct.controller;

import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.R;
import com.xiaomao6.xiaomaoproduct.entity.AttrEntity;
import com.xiaomao6.xiaomaoproduct.service.AttrService;
import com.xiaomao6.xiaomaoproduct.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * 商品属性
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:10
 */
@RestController
@RequestMapping("xiaomaoproduct/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("xiaomaoproduct:attr:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/{attrType}/list/{catelogId}")
    // @RequiresPermissions("xiaomaoproduct:attr:list")
    public R baseList(@RequestParam Map<String, Object> params,
                      @PathVariable("catelogId") Long catelogId,
                      @PathVariable("attrType") String attrType) {
        PageUtils page = attrService.queryPageByCatelog(params,catelogId,attrType);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("xiaomaoproduct:attr:info")
    public R info(@PathVariable("attrId") Long attrId) {
//        AttrEntity attr = attrService.getById(attrId);
        AttrEntity attr=attrService.getDetailById(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("xiaomaoproduct:attr:save")
    public R save(@RequestBody AttrVo attr) {
//		attrService.save(attr);
        attrService.saveInGroup(attr);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("xiaomaoproduct:attr:update")
    public R update(@RequestBody AttrVo attr) {
//        attrService.updateById(attr);
        attrService.updateDetail(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("xiaomaoproduct:attr:delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
