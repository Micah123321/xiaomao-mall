package com.xiaomao6.xiaomaomember.controller;

import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.R;
import com.xiaomao6.xiaomaomember.entity.MemberEntity;
import com.xiaomao6.xiaomaomember.feign.CouponFeignService;
import com.xiaomao6.xiaomaomember.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * 会员
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:06:59
 */
@RestController
@RefreshScope
@RequestMapping("xiaomaomember/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponFeignService couponFeignService;

    /**
     * 得到一个账户下的虚假的优惠券
     *
     * @return 账户下的虚假的优惠券
     */
    @RequestMapping("testCoupon")
    public R test() {
        MemberEntity member = new MemberEntity().setNickname("小明");
        return Objects.requireNonNull(R.ok().put("coupon", couponFeignService.test().get("coupon"))).put("member", member);
    }

    @Value("${member.user.name}")
    private String name;
    @Value("${member.user.age}")
    private String age;

    @RequestMapping("test")
    public R test1() {
        return Objects.requireNonNull(Objects.requireNonNull(R.ok().put("name", name)).put("age", age));
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("xiaomaomember:member:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("xiaomaomember:member:info")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("xiaomaomember:member:save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("xiaomaomember:member:update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("xiaomaomember:member:delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
