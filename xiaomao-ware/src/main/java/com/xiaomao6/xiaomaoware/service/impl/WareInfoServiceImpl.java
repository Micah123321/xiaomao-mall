package com.xiaomao6.xiaomaoware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.Query;
import com.xiaomao6.xiaomaoware.dao.WareInfoDao;
import com.xiaomao6.xiaomaoware.entity.WareInfoEntity;
import com.xiaomao6.xiaomaoware.service.WareInfoService;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                new QueryWrapper<WareInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据参数来模糊查询
     * @param params 参数
     * @return 仓库分页集合
     */
    @Override
    public PageUtils queryPageByParams(Map<String, Object> params) {
        QueryWrapper<WareInfoEntity> queryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (!StringUtil.isEmpty(key)) {
            queryWrapper.and(e -> {
                e.eq("id", key)
                        .or().like("name", key)
                        .or().like("address",key)
                        .or().eq("areacode",key);
            });
        }
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}
