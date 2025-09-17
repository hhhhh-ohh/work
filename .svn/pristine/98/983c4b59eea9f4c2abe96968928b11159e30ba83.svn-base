package com.wanmi.sbc.setting.helpcenterarticlecate.service;

import com.wanmi.sbc.setting.api.request.helpcenterarticle.HelpCenterArticleAddRequest;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.HelpCenterArticleCateAddRequest;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.HelpCenterArticleCateModifyRequest;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.HelpCenterArticleCateSortRequest;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.setting.helpcenterarticlecate.repository.HelpCenterArticleCateRepository;
import com.wanmi.sbc.setting.helpcenterarticlecate.model.root.HelpCenterArticleCate;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.HelpCenterArticleCateQueryRequest;
import com.wanmi.sbc.setting.bean.vo.HelpCenterArticleCateVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.List;

/**
 * <p>帮助中心文章信息业务逻辑</p>
 * @author 吕振伟
 * @date 2023-03-16 09:44:52
 */
@Service("HelpCenterArticleCateService")
public class HelpCenterArticleCateService {
	@Autowired
	private HelpCenterArticleCateRepository helpCenterArticleCateRepository;

	/**
	 * 新增帮助中心文章信息
	 * @author 吕振伟
	 */
	@Transactional
	public HelpCenterArticleCate add(HelpCenterArticleCate entity) {
		helpCenterArticleCateRepository.save(entity);
		return entity;
	}

	/**
	 * 批量新增帮助中心文章信息
	 * @author 吕振伟
	 */
	@Transactional
	public void addList(List<HelpCenterArticleCate> entityList) {
		helpCenterArticleCateRepository.saveAll(entityList);
	}

	@Transactional
	public void updateCateSort(List<HelpCenterArticleCateSortRequest> helpCenterArticleCateSortRequestList){
		helpCenterArticleCateSortRequestList.forEach(helpCenterArticleCateModifyRequest -> {
			helpCenterArticleCateRepository.updateCateSort(helpCenterArticleCateModifyRequest.getId(),helpCenterArticleCateModifyRequest.getCateSort());
		});
	}

	/**
	 * 修改帮助中心文章信息
	 * @author 吕振伟
	 */
	@Transactional
	public HelpCenterArticleCate modify(HelpCenterArticleCate entity) {
		helpCenterArticleCateRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除帮助中心文章信息
	 * @author 吕振伟
	 */
	@Transactional
	public void deleteById(Long id) {
		helpCenterArticleCateRepository.deleteById(id);
	}

	/**
	 * 批量删除帮助中心文章信息
	 * @author 吕振伟
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		helpCenterArticleCateRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询帮助中心文章信息
	 * @author 吕振伟
	 */
	public HelpCenterArticleCate getOne(Long id){
		return helpCenterArticleCateRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(SettingErrorCodeEnum.K070108));
	}

	/**
	 * 分页查询帮助中心文章信息
	 * @author 吕振伟
	 */
	public Page<HelpCenterArticleCate> page(HelpCenterArticleCateQueryRequest queryReq){
		return helpCenterArticleCateRepository.findAll(
				HelpCenterArticleCateWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询帮助中心文章信息
	 * @author 吕振伟
	 */
	public List<HelpCenterArticleCate> list(HelpCenterArticleCateQueryRequest queryReq){
		return helpCenterArticleCateRepository.findAll(HelpCenterArticleCateWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * @description 查询分类列表数据
	 * @author  lvzhenwei
	 * @date 2023/3/17 3:12 下午
	 * @return java.util.List<com.wanmi.sbc.setting.helpcenterarticlecate.model.root.HelpCenterArticleCate>
	 **/
	public List<HelpCenterArticleCate> getCateList(){
		return helpCenterArticleCateRepository.getHelpCenterArticleCateList();
	}

	/**
	 * 将实体包装成VO
	 * @author 吕振伟
	 */
	public HelpCenterArticleCateVO wrapperVo(HelpCenterArticleCate helpCenterArticleCate) {
		if (helpCenterArticleCate != null){
			HelpCenterArticleCateVO helpCenterArticleCateVO = KsBeanUtil.convert(helpCenterArticleCate, HelpCenterArticleCateVO.class);
			return helpCenterArticleCateVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author 吕振伟
	 */
	public Long count(HelpCenterArticleCateQueryRequest queryReq) {
		return helpCenterArticleCateRepository.count(HelpCenterArticleCateWhereCriteriaBuilder.build(queryReq));
	}
}

