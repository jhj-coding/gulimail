package com.jhj.gulimall.member.dao;

import com.jhj.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author jhj
 * @email jhj@gmail.com
 * @date 2022-05-14 17:19:22
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
