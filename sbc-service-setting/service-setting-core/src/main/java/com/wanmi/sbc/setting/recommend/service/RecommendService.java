package com.wanmi.sbc.setting.recommend.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.request.recommend.RecommendByIdRequest;
import com.wanmi.sbc.setting.api.request.recommend.RecommendQueryRequest;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.vo.RecommendVO;
import com.wanmi.sbc.setting.recommend.model.root.Recommend;
import com.wanmi.sbc.setting.recommend.repository.RecommendRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * <p>种草信息表业务逻辑</p>
 * @author 黄昭
 * @date 2022-05-17 16:24:21
 */
@Service("RecommendService")
@Slf4j
public class RecommendService {
	@Autowired
	private RecommendRepository recommendRepository;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 新增种草信息表
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = Exception.class)
	public Recommend add(Recommend entity, String userId) {
		Optional<Recommend> opt = recommendRepository
				.findByPageCodeAndDelFlag(entity.getPageCode(), DeleteFlag.NO);
    	if (opt.isPresent()){
			//以存在种草,做修改操作
			Recommend recommend = opt.get();
			//保存状态 1:草稿 2:已发布 3:修改已发布
			String title = recommend.getTitle();
			Long cateId = recommend.getCateId();
			Integer saveStatus = recommend.getSaveStatus();
			recommend.setTitle(entity.getTitle());
			recommend.setCateId(entity.getCateId());
			recommend.setSaveStatus(entity.getSaveStatus());
			if (Objects.equals(Constants.TWO,saveStatus)){
				//当前种草为发布状态
				if (Objects.equals(Constants.ONE,entity.getSaveStatus())){
					//修改为修改未发布状态
					recommend.setNewTitle(entity.getTitle());
					recommend.setNewCateId(entity.getCateId());
					recommend.setTitle(title);
					recommend.setCateId(cateId);
					recommend.setSaveStatus(Constants.THREE);
				}
			}
			if (Objects.equals(Constants.THREE,saveStatus)){
				//当前种草为已修改未发布状态
				if (Objects.equals(Constants.ONE,entity.getSaveStatus())){
					recommend.setNewTitle(entity.getTitle());
					recommend.setNewCateId(entity.getCateId());
					recommend.setTitle(title);
					recommend.setCateId(cateId);
					recommend.setSaveStatus(Constants.THREE);
				}
				if (Objects.equals(Constants.TWO,entity.getSaveStatus())){
					recommend.setNewTitle(null);
					recommend.setNewCateId(null);
				}
			}
			recommend.setCoverImg(entity.getCoverImg());
			recommend.setVideo(entity.getVideo());
			recommend.setUpdateTime(LocalDateTime.now());
			recommend.setUpdatePerson(userId);
			recommend.setForwardType(entity.getForwardType());
			recommendRepository.save(recommend);
			return recommend;
		}else {
			//初次创建种草
			entity.setFabulousNum(0L);
			entity.setForwardNum(0L);
			entity.setVisitorNum(0L);
			entity.setReadNum(0L);
			entity.setIsTop(Constants.no);
			entity.setStatus(Constants.yes);
			entity.setDelFlag(DeleteFlag.NO);
			entity.setCreatePerson(userId);
			entity.setCreateTime(LocalDateTime.now());
			entity.setUpdatePerson(userId);
			entity.setUpdateTime(LocalDateTime.now());
			recommendRepository.save(entity);
			return entity;
		}
	}

	/**
	 * 修改种草信息表
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = Exception.class)
	public Recommend modify(Recommend entity) {
		recommendRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除种草信息表
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteById(Recommend entity) {
		recommendRepository.save(entity);
	}

	/**
	 * 批量删除种草信息表
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteByIdList(List<Long> ids) {
		recommendRepository.deleteByIdList(ids);
	}

	/**
	 * 种草统计数据同步mysql
	 * @author xufeng
	 */
	@Transactional(rollbackFor = Exception.class)
	public void recommendSync(String pageCode) {
		log.info("recommendSync start...");
		DealStatistics dealStatistics = new DealStatistics(pageCode).invoke();
		Long readNum = dealStatistics.getReadNum();
		Long visitorNum = dealStatistics.getVisitorNum();
		Long fabulousNum = dealStatistics.getFabulousNum();
		Long forwardNum = dealStatistics.getForwardNum();
		log.info("recommendSync end...");
		recommendRepository.recommendSync(readNum, visitorNum, fabulousNum, forwardNum, pageCode);
	}

	/**
	 * 单个查询种草信息表
	 * @author 黄昭
	 */
	public Recommend getOne(Long id){
		return recommendRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "种草信息表不存在"));
	}

	/**
	 * 根据pageCode查询种草信息表
	 * @author xufeng
	 */
	public Recommend getByPageCode(String pageCode){
		return recommendRepository.findByPageCodeAndDelFlag(pageCode, DeleteFlag.NO)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "种草内容不存在"));
	}

	/**
	 * 分页查询种草信息表
	 * @author 黄昭
	 */
	public Page<Recommend> page(RecommendQueryRequest queryReq){
		return recommendRepository.findAll(
				RecommendWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询种草信息表
	 * @author 黄昭
	 */
	public List<Recommend> list(RecommendQueryRequest queryReq){
		return recommendRepository.findAll(RecommendWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 黄昭
	 */
	public RecommendVO wrapperVo(Recommend recommend) {
		if (recommend != null){
			RecommendVO recommendVO = KsBeanUtil.convert(recommend, RecommendVO.class);
			if (Objects.nonNull(recommendVO)){
				DealStatistics dealStatistics = new DealStatistics(recommendVO.getPageCode()).invoke();
				recommendVO.setReadNum(dealStatistics.getReadNum());
				recommendVO.setVisitorNum(dealStatistics.getVisitorNum());
				recommendVO.setFabulousNum(dealStatistics.getFabulousNum());
				recommendVO.setForwardNum(dealStatistics.getForwardNum());
			}
			return recommendVO;
		}
		return null;
	}

	/**
	 * 将实体包装成VO
	 * @author xufeng
	 */
	public RecommendVO wrapperVo(Recommend recommend, String customerId, boolean mobileFlag) {
		if (recommend != null){
			RecommendVO recommendVO = KsBeanUtil.convert(recommend, RecommendVO.class);
			if (Objects.nonNull(recommendVO) && mobileFlag){
				DealStatistics dealStatistics = new DealStatistics(recommendVO.getPageCode(), customerId).invoke();
				recommendVO.setReadNum(dealStatistics.getReadNum());
				recommendVO.setVisitorNum(dealStatistics.getVisitorNum());
				recommendVO.setFabulousNum(dealStatistics.getFabulousNum());
				recommendVO.setForwardNum(dealStatistics.getForwardNum());
				recommendVO.setFabulousFlag(dealStatistics.fabulousFlag);
			}
			return recommendVO;
		}
		return null;
	}

	/**
	 * 修改置顶
	 * @param request
	 */
	public void updateTop(RecommendByIdRequest request) {
		RecommendQueryRequest recommendQueryRequest = new RecommendQueryRequest();
		recommendQueryRequest.setDelFlag(DeleteFlag.NO);
		recommendQueryRequest.setIsTop(Constants.yes);

		Optional<Recommend> opt = recommendRepository.findByIdAndDelFlag(request.getId(), DeleteFlag.NO);
		if (opt.isPresent()){
			if (Objects.equals(Constants.ZERO,opt.get().getStatus())){
				throw new SbcRuntimeException(SettingErrorCodeEnum.K070037);
			}
			if (Objects.equals(opt.get().getIsTop(),Constants.yes)){
				opt.get().setIsTop(Constants.no);
				opt.get().setTopTime(null);
			}else {
				if (recommendRepository.count(RecommendWhereCriteriaBuilder.build(recommendQueryRequest))>Constants.NINE){
					throw new SbcRuntimeException(SettingErrorCodeEnum.K070038);
				}
				opt.get().setIsTop(Constants.yes);
				opt.get().setTopTime(LocalDateTime.now());
			}
			opt.get().setUpdatePerson(request.getUserId());
			opt.get().setUpdateTime(LocalDateTime.now());
			recommendRepository.save(opt.get());
		}else {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
		}
	}

	/**
	 * 修改状态
	 * @param request
	 */
	public void updateStatus(RecommendByIdRequest request) {
		Optional<Recommend> opt = recommendRepository.findByIdAndDelFlag(request.getId(), DeleteFlag.NO);
		if (opt.isPresent()){
			if (Objects.equals(opt.get().getStatus(),Constants.yes)){
				opt.get().setStatus(Constants.no);
				opt.get().setIsTop(Constants.no);
			}else {
				opt.get().setStatus(Constants.yes);
			}
			opt.get().setUpdatePerson(request.getUserId());
			opt.get().setUpdateTime(LocalDateTime.now());
			recommendRepository.save(opt.get());
		}else {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
		}
	}

	public void delById(RecommendByIdRequest request) {
		Optional<Recommend> opt = recommendRepository.findByIdAndDelFlag(request.getId(), DeleteFlag.NO);
		if (opt.isPresent()){
			opt.get().setDelFlag(DeleteFlag.YES);
			opt.get().setDeletePerson(request.getUserId());
			opt.get().setDeleteTime(LocalDateTime.now());
			opt.get().setUpdatePerson(request.getUserId());
			opt.get().setUpdateTime(LocalDateTime.now());
			recommendRepository.save(opt.get());
		}else {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
		}
	}

	private class DealStatistics {
		private final String pageCode;
		private String customId;
		private Long readNum;
		private Long visitorNum;
		private Long fabulousNum;
		private Long forwardNum;
		private boolean fabulousFlag;

		public DealStatistics(String pageCode) {
			this.pageCode = pageCode;
		}

		public DealStatistics(String pageCode, String customId) {
			this.pageCode = pageCode;
			this.customId = customId;
		}

		public Long getReadNum() {
			return readNum;
		}

		public Long getVisitorNum() {
			return visitorNum;
		}

		public Long getFabulousNum() {
			return fabulousNum;
		}

		public Long getForwardNum() {
			return forwardNum;
		}

		public DealStatistics invoke() {
			// 阅读数
			readNum = Constants.NUM_0L;
			// 访客数
			visitorNum = Constants.NUM_0L;
			// 点赞数
			fabulousNum = Constants.NUM_0L;
			// 转发数
			forwardNum = Constants.NUM_0L;
			// 用户是否点赞
			fabulousFlag = false;
			// PV
			String pvKey = CacheKeyConstant.RECOMMEND_PV_KEY.concat(pageCode);
			String pv = redisUtil.getString(pvKey);
			if (StringUtils.isNotBlank(pv)) {
				readNum = Long.parseLong(pv);
			}
			// UV
			String uvKey = CacheKeyConstant.RECOMMEND_UV_KEY.concat(pageCode);
			Set<String> uvSet = stringRedisTemplate.opsForSet().members(uvKey);
			if (CollectionUtils.isNotEmpty(uvSet)){
				visitorNum = (long) uvSet.size();
			}
			// 点赞
			String thumbsUpKey = CacheKeyConstant.RECOMMEND_THUMBS_UP_KEY.concat(pageCode);
			Set<String> thumbsUpSet = stringRedisTemplate.opsForSet().members(thumbsUpKey);
			if (CollectionUtils.isNotEmpty(thumbsUpSet)){
				fabulousNum = (long) thumbsUpSet.size();
			}
			// 转发
			String forwardKey = CacheKeyConstant.RECOMMEND_FORWARD_KEY.concat(pageCode);
			String forward = redisUtil.getString(forwardKey);
			if (StringUtils.isNotBlank(forward)) {
				forwardNum = Long.parseLong(forward);
			}
			// 是否点赞
			if (StringUtils.isNotBlank(customId)){
				if (CollectionUtils.isNotEmpty(thumbsUpSet) && thumbsUpSet.contains(customId)){
					fabulousFlag = true;
				}
			}
			log.info("recommendSync readNum:{}, visitorNum:{}, fabulousNum:{}, forwardNum:{}",
					readNum, visitorNum, fabulousNum, forwardNum);
			return this;
		}
	}
}

