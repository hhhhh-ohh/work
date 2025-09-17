package com.wanmi.sbc.setting.helpcenterarticlerecord.service;

import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.setting.helpcenterarticlerecord.repository.HelpCenterArticleRecordRepository;
import com.wanmi.sbc.setting.helpcenterarticlerecord.model.root.HelpCenterArticleRecord;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordQueryRequest;
import com.wanmi.sbc.setting.bean.vo.HelpCenterArticleRecordVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.List;

/**
 * <p>帮助中心文章记录业务逻辑</p>
 * @author 吕振伟
 * @date 2023-03-17 16:56:08
 */
@Service("HelpCenterArticleRecordService")
public class HelpCenterArticleRecordService {
	@Autowired
	private HelpCenterArticleRecordRepository helpCenterArticleRecordRepository;

	/**
	 * 新增帮助中心文章记录
	 * @author 吕振伟
	 */
	@Transactional
	public HelpCenterArticleRecord add(HelpCenterArticleRecord entity) {
		helpCenterArticleRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 修改帮助中心文章记录
	 * @author 吕振伟
	 */
	@Transactional
	public HelpCenterArticleRecord modify(HelpCenterArticleRecord entity) {
		helpCenterArticleRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除帮助中心文章记录
	 * @author 吕振伟
	 */
	@Transactional
	public void deleteById(HelpCenterArticleRecord entity) {
		helpCenterArticleRecordRepository.save(entity);
	}

	/**
	 * 批量删除帮助中心文章记录
	 * @author 吕振伟
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		helpCenterArticleRecordRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询帮助中心文章记录
	 * @author 吕振伟
	 */
	public HelpCenterArticleRecord getOne(Long id){
		return helpCenterArticleRecordRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
				.orElseThrow(() -> new SbcRuntimeException(SettingErrorCodeEnum.K070113));
	}

	/**
	 * 分页查询帮助中心文章记录
	 * @author 吕振伟
	 */
	public Page<HelpCenterArticleRecord> page(HelpCenterArticleRecordQueryRequest queryReq){
		return helpCenterArticleRecordRepository.findAll(
				HelpCenterArticleRecordWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询帮助中心文章记录
	 * @author 吕振伟
	 */
	public List<HelpCenterArticleRecord> list(HelpCenterArticleRecordQueryRequest queryReq){
		return helpCenterArticleRecordRepository.findAll(HelpCenterArticleRecordWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 吕振伟
	 */
	public HelpCenterArticleRecordVO wrapperVo(HelpCenterArticleRecord helpCenterArticleRecord) {
		if (helpCenterArticleRecord != null){
			HelpCenterArticleRecordVO helpCenterArticleRecordVO = KsBeanUtil.convert(helpCenterArticleRecord, HelpCenterArticleRecordVO.class);
			return helpCenterArticleRecordVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author 吕振伟
	 */
	public Long count(HelpCenterArticleRecordQueryRequest queryReq) {
		return helpCenterArticleRecordRepository.count(HelpCenterArticleRecordWhereCriteriaBuilder.build(queryReq));
	}
}

