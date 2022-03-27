package com.xiaomao6.xiaomaoproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.Query;
import com.xiaomao6.xiaomaoproduct.dao.ProductAttrValueDao;
import com.xiaomao6.xiaomaoproduct.entity.ProductAttrValueEntity;
import com.xiaomao6.xiaomaoproduct.service.ProductAttrValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void updateAttr(Long spuId, List<ProductAttrValueEntity> list) {
        //先删除原有的所有属性,然后重新添加
        this.remove(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id",spuId));

        //将所有属性重新添加,设定spuid
        List<ProductAttrValueEntity> collect = list.stream().map(e -> e.setSpuId(spuId)).collect(Collectors.toList());
        this.saveBatch(collect);

//        this.saveBatch()
    }

    @Override
    public List<ProductAttrValueEntity> selectByListId(List<Long> ids) {
        return baseMapper.selectByListId(ids);
    }

}
