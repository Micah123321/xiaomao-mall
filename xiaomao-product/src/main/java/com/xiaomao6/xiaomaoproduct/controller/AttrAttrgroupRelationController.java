package com.xiaomao6.xiaomaoproduct.controller;

import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.R;
import com.xiaomao6.xiaomaoproduct.entity.AttrAttrgroupRelationEntity;
import com.xiaomao6.xiaomaoproduct.service.AttrAttrgroupRelationService;
import com.xiaomao6.xiaomaoproduct.vo.AttrGroupRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;



/**
 * 属性&属性分组关联
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:10
 */
@RestController
@RequestMapping("xiaomaoproduct/attrattrgrouprelation")
public class AttrAttrgroupRelationController {
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
   // @RequiresPermissions("xiaomaoproduct:attrattrgrouprelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrAttrgroupRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("xiaomaoproduct:attrattrgrouprelation:info")
    public R info(@PathVariable("id") Long id){
		AttrAttrgroupRelationEntity attrAttrgroupRelation = attrAttrgroupRelationService.getById(id);

        return R.ok().put("attrAttrgroupRelation", attrAttrgroupRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("xiaomaoproduct:attrattrgrouprelation:save")
    public R save(@RequestBody AttrAttrgroupRelationEntity attrAttrgroupRelation){
		attrAttrgroupRelationService.save(attrAttrgroupRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
   // @RequiresPermissions("xiaomaoproduct:attrattrgrouprelation:update")
    public R update(@RequestBody AttrAttrgroupRelationEntity attrAttrgroupRelation){
		attrAttrgroupRelationService.updateById(attrAttrgroupRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //[{"attrId":1,"attrGroupId":2}]
    public R delete(@RequestBody AttrGroupRelationVo[] relationVos){
//		attrAttrgroupRelationService.removeByIds(Arrays.asList(ids));
		attrAttrgroupRelationService.deleteByRelationVos(relationVos);
        return R.ok();
    }

}
