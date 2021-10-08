package com.xiaomao6.xiaomaoware.controller;

import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.R;
import com.xiaomao6.xiaomaoware.entity.PurchaseEntity;
import com.xiaomao6.xiaomaoware.service.PurchaseService;
import com.xiaomao6.xiaomaoware.vo.MergePurchaseVo;
import com.xiaomao6.xiaomaoware.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;



/**
 * 采购信息
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:15:41
 */
@RestController
@RequestMapping("xiaomaoware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    //`/ware/purchase/done`
    @PostMapping("/done")
    public R done(@Validated @RequestBody PurchaseDoneVo purchaseDoneVo){
        purchaseService.done(purchaseDoneVo);
        return R.ok();
    }
    ///ware/purchase/merge
    @PostMapping("/merge")
    public R merge(@RequestBody MergePurchaseVo mergePurchaseVo){
        purchaseService.mergePurchase(mergePurchaseVo);
        return R.ok();
    }

    ///ware/purchase/received
    @PostMapping("/received")
    public R received(@RequestBody List<Long> ids){
        //领取采购单方法
        purchaseService.received(ids);
        return R.ok();
    }


    //unreceive/list
    /**
     * 新建和已分配列表
     */
    @GetMapping("unreceive/list")
    // @RequiresPermissions("xiaomaoware:purchase:list")
    public R unReceiveList(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPageUnReceive(params);

        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
   // @RequiresPermissions("xiaomaoware:purchase:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("xiaomaoware:purchase:info")
    public R info(@PathVariable("id") Long id){
		PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("xiaomaoware:purchase:save")
    public R save(@RequestBody PurchaseEntity purchase){
		purchaseService.save(purchase
                .setCreateTime(new Date())
                .setUpdateTime(new Date()));
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
   // @RequiresPermissions("xiaomaoware:purchase:update")
    public R update(@RequestBody PurchaseEntity purchase){
		purchaseService.updateById(purchase);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
   // @RequiresPermissions("xiaomaoware:purchase:delete")
    public R delete(@RequestBody Long[] ids){
		purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
