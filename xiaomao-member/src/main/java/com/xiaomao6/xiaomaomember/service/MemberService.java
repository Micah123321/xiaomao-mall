package com.xiaomao6.xiaomaomember.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaomember.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:06:59
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

