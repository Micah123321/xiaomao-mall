package com.xiaomao6.xiaomaoproduct.controller;

import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.R;
import com.xiaomao6.xiaomaoproduct.entity.AttrEntity;
import com.xiaomao6.xiaomaoproduct.entity.AttrGroupEntity;
import com.xiaomao6.xiaomaoproduct.service.AttrAttrgroupRelationService;
import com.xiaomao6.xiaomaoproduct.service.AttrGroupService;
import com.xiaomao6.xiaomaoproduct.service.AttrService;
import com.xiaomao6.xiaomaoproduct.service.CategoryService;
import com.xiaomao6.xiaomaoproduct.vo.AttrGroupRelationVo;
import com.xiaomao6.xiaomaoproduct.vo.AttrGroupWithAttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * 属性分组
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:10
 */
@RestController
@RequestMapping("xiaomaoproduct/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private CategoryService categoryService;
    @Resource
    AttrService attrService;

    @Resource
    AttrAttrgroupRelationService attrAttrgroupRelationService;


    ///product/attrgroup/{catelogId}/withattr
    @GetMapping("/{catelogId}/withattr")
    public R getGroupWithAttr(@PathVariable("catelogId") Long catelogId) {
        List<AttrGroupWithAttrVo> list=attrGroupService.getGroupWithAttrByCatelogId(catelogId);
        return R.ok().put("data", list);
    }


    ///product/attrgroup/attr/relation
    //AttrGroupRelationVo

    /**
     * 添加属性关联方法
     * @param attrGroupRelationVo 属性关联数组vo
     * @return resp
     */
    @PostMapping("/attr/relation")
    public R relation(@RequestBody AttrGroupRelationVo[] attrGroupRelationVo) {
        attrAttrgroupRelationService.relation(attrGroupRelationVo);
        return R.ok();
    }

    ///product/attrgroup/{attrgroupId}/noattr/relation

    /**
     * 获取可以关联的属性列表
     * @param attrgroupId 当前属性分组id
     * @param params 参数
     * @return resp 属性列表分页
     */
    @GetMapping("{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrgroupId") Long attrgroupId,
                            @RequestParam Map<String, Object> params) {

        PageUtils page = attrService.getNoRelationPage(params, attrgroupId);
//        List<AttrEntity> list= attrGroupService.getAttrListByGroupId(attrgroupId);

        return R.ok().put("page", page);
    }


    ///product/attrgroup/{attrgroupId}/attr/relation

    @GetMapping("{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {

        List<AttrEntity> list = attrGroupService.getAttrListByGroupId(attrgroupId);

        return R.ok().put("data", list);
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    // @RequiresPermissions("xiaomaoproduct:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catelogId) {
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPageByCateId(params, catelogId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("xiaomaoproduct:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        attrGroup.setCatelogPath(categoryService.getCatelogPath(attrGroup.getCatelogId()));
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("xiaomaoproduct:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("xiaomaoproduct:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("xiaomaoproduct:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
