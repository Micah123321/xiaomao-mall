package com.xiaomao6.xiaomaoproduct.controller;

import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.R;
import com.xiaomao6.common.valid.SaveGroup;
import com.xiaomao6.common.valid.UpdateGroup;
import com.xiaomao6.common.valid.UpdateStatusGroup;
import com.xiaomao6.xiaomaoproduct.entity.BrandEntity;
import com.xiaomao6.xiaomaoproduct.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * 品牌
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:10
 */
@RestController
@RequestMapping("xiaomaoproduct/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("xiaomaoproduct:brand:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("xiaomaoproduct:brand:info")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("xiaomaoproduct:brand:save")
    public R save(@Validated(SaveGroup.class) @RequestBody BrandEntity brand) {
        //, BindingResult bindingResult
//        if (bindingResult.hasErrors()) {
//            HashMap<String, String> stringStringHashMap = new HashMap<>();
//            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//            fieldErrors.forEach(e -> {
//                stringStringHashMap.put(e.getField(), e.getDefaultMessage());
//            });
//            return R.error(400, "数据校验错误").put("data", stringStringHashMap);
//        } else {
//        }
        brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("xiaomaoproduct:brand:update")
    public R update(@Validated(UpdateGroup.class) @RequestBody BrandEntity brand) {
        brandService.updateById(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update/status")
    // @RequiresPermissions("xiaomaoproduct:brand:update")
    public R updateStatus(@Validated(UpdateStatusGroup.class) @RequestBody BrandEntity brand) {
        brandService.updateById(new BrandEntity().setShowStatus(brand.getShowStatus()).setBrandId(brand.getBrandId()));
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("xiaomaoproduct:brand:delete")
    public R delete(@RequestBody Long[] brandIds) {
        brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
