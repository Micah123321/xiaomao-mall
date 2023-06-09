package com.xiaomao6.xiaomaoware.controller;

import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.R;
import com.xiaomao6.xiaomaoware.entity.WareSkuEntity;
import com.xiaomao6.xiaomaoware.service.WareSkuService;
import com.xiaomao6.common.to.StockTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;



/**
 * 商品库存
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:15:40
 */
@RestController
@RequestMapping("xiaomaoware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    //根据ids返回库存信息
    @PostMapping("/stock")
    public R checkStock(@RequestBody List<Long> ids){
        List<StockTo> list=wareSkuService.selectStock(ids);
        return R.ok().setData(list);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
   // @RequiresPermissions("xiaomaoware:waresku:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("xiaomaoware:waresku:info")
    public R info(@PathVariable("id") Long id){
		WareSkuEntity wareSku = wareSkuService.getById(id);

        return R.ok().put("wareSku", wareSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("xiaomaoware:waresku:save")
    public R save(@RequestBody WareSkuEntity wareSku){
		wareSkuService.save(wareSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
   // @RequiresPermissions("xiaomaoware:waresku:update")
    public R update(@RequestBody WareSkuEntity wareSku){
		wareSkuService.updateById(wareSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
   // @RequiresPermissions("xiaomaoware:waresku:delete")
    public R delete(@RequestBody Long[] ids){
		wareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
