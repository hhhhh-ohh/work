package com.wanmi.sbc.empower.miniprogramset.service;

import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetQueryRequest;
import com.wanmi.sbc.empower.bean.vo.MiniProgramSetVO;
import com.wanmi.sbc.empower.miniprogramset.model.root.MiniProgramSet;
import com.wanmi.sbc.empower.miniprogramset.repository.MiniProgramSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>小程序配置业务逻辑</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@Service("MiniProgramSetService")
public class MiniProgramSetService {
	@Autowired
	private MiniProgramSetRepository miniProgramSetRepository;

	@Autowired
	private RedisUtil redisService;

	/**
	 * 新增小程序配置
	 * @author zhanghao
	 */
	@Transactional
	public MiniProgramSet add(MiniProgramSet entity) {
		miniProgramSetRepository.save(entity);
		return entity;
	}

	/**
	 * 修改小程序配置
	 * @author zhanghao
	 */
	@Transactional
	public MiniProgramSet modify(MiniProgramSet entity) {
		//清空accessToken的缓存
		String redisKey = "get_access_token_PUBLIC";
		redisService.delete(redisKey);
		//清空image的缓存
		redisService.delete(RedisKeyConstant.QR_CODE_CACHE);
		MiniProgramSet miniProgramSet = getOneByType(entity.getType());
		entity.setId(miniProgramSet.getId());
		if(entity.getDelFlag() == null){
			entity.setDelFlag(miniProgramSet.getDelFlag());
		}
		if(entity.getCreateTime() == null){
			entity.setCreateTime(miniProgramSet.getCreateTime());
		}
		if(entity.getUpdateTime() == null){
			entity.setUpdateTime(miniProgramSet.getUpdateTime());
		}
		if(entity.getCreatePerson() == null){
			entity.setCreatePerson(miniProgramSet.getCreatePerson());
		}
		if(entity.getUpdatePerson() == null){
			entity.setUpdatePerson(miniProgramSet.getUpdatePerson());
		}
		miniProgramSetRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除小程序配置
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(MiniProgramSet entity) {
		miniProgramSetRepository.save(entity);
	}

	/**
	 * 批量删除小程序配置
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<MiniProgramSet> infos) {
		miniProgramSetRepository.saveAll(infos);
	}

	/**
	 * 单个查询小程序配置
	 * @author zhanghao
	 */
	public MiniProgramSet getOneById(Integer id){
		return miniProgramSetRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "小程序配置不存在"));
	}

	/**
	 * 单个查询小程序配置
	 * @author zhanghao
	 */
	public MiniProgramSet getOneByType(Integer type){
		return miniProgramSetRepository.findByTypeAndDelFlag(type, DeleteFlag.NO)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "小程序配置不存在"));
	}

	/**
	 * 分页查询小程序配置
	 * @author zhanghao
	 */
	public Page<MiniProgramSet> page(MiniProgramSetQueryRequest queryReq){
		return miniProgramSetRepository.findAll(
				MiniProgramSetWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询小程序配置
	 * @author zhanghao
	 */
	public List<MiniProgramSet> list(MiniProgramSetQueryRequest queryReq){
		return miniProgramSetRepository.findAll(MiniProgramSetWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public MiniProgramSetVO wrapperVo(MiniProgramSet miniProgramSet) {
		if (miniProgramSet != null){
			MiniProgramSetVO miniProgramSetVO = KsBeanUtil.convert(miniProgramSet, MiniProgramSetVO.class);
			return miniProgramSetVO;
		}
		return null;
	}
}

