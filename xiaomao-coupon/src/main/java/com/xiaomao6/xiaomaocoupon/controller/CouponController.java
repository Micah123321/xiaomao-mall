package com.xiaomao6.xiaomaocoupon.controller;

import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.R;
import com.xiaomao6.xiaomaocoupon.entity.CouponEntity;
import com.xiaomao6.xiaomaocoupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * 优惠券信息
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:00:41
 */
@RestController
@RequestMapping("xiaomaocoupon/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    /**
     * 得到一个虚假的优惠券
     * @return 虚假的优惠券
     */
    @RequestMapping("test")
    public R test() {
        return R.ok().put("coupon", new CouponEntity().setCouponName("满10减1"));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("xiaomaocoupon:coupon:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("xiaomaocoupon:coupon:info")
    public R info(@PathVariable("id") Long id) {
        CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("xiaomaocoupon:coupon:save")
    public R save(@RequestBody CouponEntity coupon) {
        couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("xiaomaocoupon:coupon:update")
    public R update(@RequestBody CouponEntity coupon) {
        couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("xiaomaocoupon:coupon:delete")
    public R delete(@RequestBody Long[] ids) {
        couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
