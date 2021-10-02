package com.xiaomao6.xiaomaoproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.Query;
import com.xiaomao6.xiaomaoproduct.dao.CategoryBrandRelationDao;
import com.xiaomao6.xiaomaoproduct.dao.CategoryDao;
import com.xiaomao6.xiaomaoproduct.entity.CategoryBrandRelationEntity;
import com.xiaomao6.xiaomaoproduct.entity.CategoryEntity;
import com.xiaomao6.xiaomaoproduct.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationDao categoryBrandRelationDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> getTreeList() {
        List<CategoryEntity> all = baseMapper.selectList(null);
        List<CategoryEntity> collect = all.stream()
                .filter(e -> e.getParentCid() == 0)
                .sorted(Comparator.comparingInt(e -> (e.getSort() == null ? 0 : e.getSort())))
                .map(e -> e.setChildren(getChildrenByRoot(e, all)))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public void removeByIdList(List<Long> asList) {
        //TODO 校验是否在其他表引用了该分类 如果有,则删除失败

        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] getCatelogPath(Long catId) {
        ArrayList<Long> list = new ArrayList<>();
        getCatePath(catId,list);
        Collections.reverse(list);
        return list.toArray(new Long[list.size()]);
    }

    @Override
    public void updateDetail(CategoryEntity category) {
        //TODO 可能存在的其他表的冗余字段,待更新
        this.updateById(category);
        categoryBrandRelationDao.update(
                new CategoryBrandRelationEntity().setCatelogName(category.getName()).setCatelogId(category.getCatId()),
                new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id",category.getCatId())
        );
    }

    private void getCatePath(Long catId,ArrayList<Long> list){
        list.add(catId);
        CategoryEntity byId = this.getById(catId);
        if (byId.getParentCid()!=0){
            getCatePath(byId.getParentCid(),list);
        }
    }

    private List<CategoryEntity> getChildrenByRoot(CategoryEntity root,List<CategoryEntity> all){
        List<CategoryEntity> collect = all.stream()
                .filter(e -> e.getParentCid().equals(root.getCatId()))
                .sorted(Comparator.comparingInt(e -> (e.getSort() == null ? 0 : e.getSort())))
                .map(e -> e.setChildren(getChildrenByRoot(e, all)))
                .collect(Collectors.toList());
        return collect;
    }



}
