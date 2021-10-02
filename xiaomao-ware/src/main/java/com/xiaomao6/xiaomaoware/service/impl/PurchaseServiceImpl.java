package com.xiaomao6.xiaomaoware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomao6.common.constant.WareConstant;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.Query;
import com.xiaomao6.xiaomaoware.dao.PurchaseDao;
import com.xiaomao6.xiaomaoware.entity.PurchaseDetailEntity;
import com.xiaomao6.xiaomaoware.entity.PurchaseEntity;
import com.xiaomao6.xiaomaoware.service.PurchaseDetailService;
import com.xiaomao6.xiaomaoware.service.PurchaseService;
import com.xiaomao6.xiaomaoware.vo.MergePurchaseVo;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Resource
    PurchaseDetailService purchaseDetailService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurchaseEntity> queryWrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if (!StringUtil.isEmpty(key)) {
            queryWrapper.and(e -> {
                e.eq("id", key)
                        .or().like("assignee_name", key)
                        .or().eq("assignee_id",key);
            });
        }

        String status = (String) params.get("status");
        if (!StringUtil.isEmpty(status)) {
            queryWrapper.eq("status",status);
        }


        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnReceive(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
                        .eq("status", WareConstant.PurchaseStatusConstant.ASSIGN.getCode())
                        .or().eq("status",WareConstant.PurchaseStatusConstant.NEW.getCode())
        );
        return new PageUtils(page);
    }

    /**
     * 将采购需求添加到采购单
     * @param mergePurchaseVo 存放采购单id和采购需求id集合
     */
    @Override
    @Transactional
    public void mergePurchase(MergePurchaseVo mergePurchaseVo) {
        //首先判断purchaseId存不存在,不存在则添加一个新的purchase
        Long purchaseId = mergePurchaseVo.getPurchaseId();
        if (purchaseId==null){
            PurchaseEntity purchaseEntity = new PurchaseEntity()
                    .setStatus(WareConstant.PurchaseStatusConstant.NEW.getCode()).setPriority(0)
                    .setCreateTime(new Date()).setUpdateTime(new Date());
            this.save(purchaseEntity);
            purchaseId=purchaseEntity.getId();
        }
        //判断当前采购单status是否位0或者1 不然不允许修改
        PurchaseEntity byId = this.getById(purchaseId);
        if (!(byId.getStatus().equals(WareConstant.PurchaseStatusConstant.NEW.getCode())||
        byId.getStatus().equals(WareConstant.PurchaseStatusConstant.ASSIGN.getCode()))){
            throw new RuntimeException("当前采购单不允许再修改");
        }else {
        //获取所有的需求id集合,map一下 生成一个 List<PurchaseDetailEntity> 集合,再进行批量修改 Status PurchaseId
        List<Long> items = mergePurchaseVo.getItems();
        Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> collect = items.stream().map(e -> new PurchaseDetailEntity()
                .setPurchaseId(finalPurchaseId)
                .setStatus(WareConstant.PurchaseDetailStatusConstant.ASSIGN.getCode())
                .setId(e))
                .collect(Collectors.toList());
        purchaseDetailService.updateBatchById(collect);

        //修改了之后更新下 UpdateTime
        PurchaseEntity purchaseEntity = new PurchaseEntity()
                .setId(purchaseId)
                .setUpdateTime(new Date());
        this.updateById(purchaseEntity);
        }




    }

    @Override
    public void received(List<Long> ids) {
        //status 为 0或1才能被领取
        //领取之后修改为2
        //修改updatetime
        List<PurchaseEntity> purchaseEntities = ids.stream().map(this::getById)
                .filter(e -> e.getStatus().equals(WareConstant.PurchaseStatusConstant.NEW.getCode())
                        || e.getStatus().equals(WareConstant.PurchaseStatusConstant.ASSIGN.getCode()))
                .map(e -> new PurchaseEntity()
                        .setId(e.getId())
                        .setStatus(WareConstant.PurchaseStatusConstant.RECEIVE.getCode())
                        .setUpdateTime(new Date()))
                .collect(Collectors.toList());

        //wms_purchase_detail的status也要被修改
        //根据ids遍历出的purchase_id查出对应的wms_purchase_detail 然后修改status位2
        if (purchaseEntities.size()>0)
        purchaseEntities.forEach(e->{
            List<PurchaseDetailEntity> purchaseDetailEntities = purchaseDetailService.list(new QueryWrapper<PurchaseDetailEntity>().eq("purchase_id", e.getId()));
            List<PurchaseDetailEntity> collect = purchaseDetailEntities.stream().map(item ->
                    new PurchaseDetailEntity().setId(item.getId()).setStatus(WareConstant.PurchaseDetailStatusConstant.DOING.getCode())
            ).collect(Collectors.toList());
            if (collect.size()>0)
            purchaseDetailService.updateBatchById(collect);
        });

        this.updateBatchById(purchaseEntities);
    }

}
