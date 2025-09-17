package com.wanmi.sbc.goods.wechatvideocate.wechatcateaudit.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.GoodsConstant;
import com.wanmi.sbc.goods.api.request.thirdgoodscate.GradeRequest;
import com.wanmi.sbc.goods.api.request.thirdgoodscate.WechatAuditRequest;
import com.wanmi.sbc.goods.api.request.thirdgoodscate.WechatCateCallbackRequest;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatcateaudit.WechatCateAuditQueryRequest;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatcatecertificate.WechatCateCertificateQueryRequest;
import com.wanmi.sbc.goods.api.response.thirdgoodscate.GradeResponse;
import com.wanmi.sbc.goods.bean.dto.WechatAuditDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.ThirdGoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.wechatvideo.WechatCateDTO;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.repository.GoodsCateRepository;
import com.wanmi.sbc.goods.cate.service.GoodsCateService;
import com.wanmi.sbc.goods.goodscatethirdcaterel.model.root.GoodsCateThirdCateRel;
import com.wanmi.sbc.goods.goodscatethirdcaterel.repository.GoodsCateThirdCateRelRepository;
import com.wanmi.sbc.goods.thirdgoodscate.model.root.ThirdGoodsCate;
import com.wanmi.sbc.goods.thirdgoodscate.repository.ThirdGoodsCateRepository;
import com.wanmi.sbc.goods.wechatvideocate.wechatcateaudit.model.root.WechatCateAudit;
import com.wanmi.sbc.goods.wechatvideocate.wechatcateaudit.repository.WechatCateAuditRepository;
import com.wanmi.sbc.goods.wechatvideocate.wechatcatecertificate.model.root.WechatCateCertificate;
import com.wanmi.sbc.goods.wechatvideocate.wechatcatecertificate.repository.WechatCateCertificateRepository;
import com.wanmi.sbc.goods.wechatvideocate.wechatcatecertificate.service.WechatCateCertificateService;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformCateProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.cate.SellPlatformAuditCateRequest;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformAuditCateVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>微信类目审核状态业务逻辑</p>
 * @author 
 * @date 2022-04-09 17:02:02
 */
@Service("WechatCateAuditService")
public class WechatCateAuditService {

	/** 审核中 */
	private static final String AUDITING = "1050009";

	@Autowired
	private WechatCateAuditRepository wechatCateAuditRepository;

	@Autowired
	private GoodsCateThirdCateRelRepository goodsCateThirdCateRelRepository;

	@Autowired
	private ThirdGoodsCateRepository thirdGoodsCateRepository;

	@Autowired
	private GoodsCateRepository goodsCateRepository;

	@Autowired
	private RedissonClient redissonClient;

	@Autowired
	private SellPlatformCateProvider sellPlatformCateProvider;

	@Autowired
	private WechatCateCertificateRepository wechatCateCertificateRepository;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private WechatCateCertificateService wechatCateCertificateService;

	@Autowired
	private GoodsCateService goodsCateService;

	/**
	 * 列表查询微信类目审核状态
	 * @author 
	 */
	public List<WechatCateAudit> list(WechatCateAuditQueryRequest queryReq){
		return wechatCateAuditRepository.findAll(
				WechatCateAuditWhereCriteriaBuilder.build(queryReq),
				queryReq.getSort());
	}

	/**
	 * 处理微信类目审核回调
	 * @param wechatCateCallbackRequest
	 */
	@Transactional
	public void dealCallback(WechatCateCallbackRequest wechatCateCallbackRequest) {
		if (AuditStatus.CHECKED.equals(wechatCateCallbackRequest.getAuditStatus())) {
			WechatCateAudit wechatCateAudit = list(WechatCateAuditQueryRequest.builder().auditId(wechatCateCallbackRequest.getAuditId()).build()).get(0);
				if (wechatCateAudit != null && StringUtils.isNotBlank(wechatCateAudit.getCateIds())) {
					LocalDateTime now = LocalDateTime.now();
					goodsCateThirdCateRelRepository.saveAll(Stream.of(wechatCateAudit.getCateIds().split(",")).map(v->{
						GoodsCateThirdCateRel goodsCateThirdCateRel = new GoodsCateThirdCateRel();
						goodsCateThirdCateRel.setCateId(Long.valueOf(v));
						goodsCateThirdCateRel.setThirdCateId(wechatCateAudit.getWechatCateId());
						goodsCateThirdCateRel.setDelFlag(DeleteFlag.NO);
						goodsCateThirdCateRel.setCreateTime(now);
						goodsCateThirdCateRel.setThirdPlatformType(ThirdPlatformType.WECHAT_VIDEO);
						return goodsCateThirdCateRel;
					}).collect(Collectors.toList()));
				}
				wechatCateAuditRepository.dealChecked(wechatCateCallbackRequest.getAuditId());
		}else {
			wechatCateAuditRepository.dealNotPass(wechatCateCallbackRequest.getRejectReason(),wechatCateCallbackRequest.getAuditId());
		}
	}

	/**
	 * 根据审核状态，查询微信类目审核记录
	 * @param auditStatus
	 * @return
	 */
	public List<WechatCateDTO> tree(AuditStatus auditStatus) {
		//三级微信类目
		List<WechatCateDTO> third = wechatCateAuditRepository.getByAuditStatus(auditStatus);
		if (CollectionUtils.isNotEmpty(third)) {
			if (AuditStatus.CHECKED.equals(auditStatus)) {
				//查询类目映射
				List<GoodsCateThirdCateRel> relList = goodsCateThirdCateRelRepository.findByThirdCateIdsAndThirdPlatformType(third.stream().map(v -> v.getCateId()).collect(Collectors.toList()), ThirdPlatformType.WECHAT_VIDEO);
				if (CollectionUtils.isNotEmpty(relList)) {
					//查询映射的平台类目
					List<GoodsCate> goodsCates = goodsCateRepository.queryCates(relList.stream().map(v -> v.getCateId()).collect(Collectors.toList()), DeleteFlag.NO);
					if (CollectionUtils.isNotEmpty(goodsCates)) {
						List<GoodsCateVO> goodsCateVOS = JSON.parseArray(JSON.toJSONString(goodsCates), GoodsCateVO.class);
						Map<Long, List<GoodsCateThirdCateRel>> map = relList.stream().collect(Collectors.groupingBy(v -> v.getThirdCateId()));
						for (Map.Entry<Long, List<GoodsCateThirdCateRel>> entry : map.entrySet()) {
							WechatCateDTO wechatCateDTO = third.stream().filter(v -> v.getCateId().equals(entry.getKey())).findFirst().get();
							List<Long> cateIds = entry.getValue().stream().map(v -> v.getCateId()).collect(Collectors.toList());
							wechatCateDTO.setGoodsCateVOS(goodsCateVOS.stream().filter(s -> cateIds.contains(s.getCateId())).collect(Collectors.toList()));
						}
					}
				}
			} else {
				List<WechatCateDTO> mapped = third.stream().filter(v -> StringUtils.isNotBlank(v.getCateIds())).collect(Collectors.toList());
				//查询映射的平台类目
				List<GoodsCate> goodsCates = goodsCateRepository.queryCates(mapped.stream().flatMap(v -> Stream.of(v.getCateIds().split(","))).map(s -> Long.valueOf(s)).collect(Collectors.toList()), DeleteFlag.NO);
				if (CollectionUtils.isNotEmpty(goodsCates)) {
					List<GoodsCateVO> goodsCateVOS = JSON.parseArray(JSON.toJSONString(goodsCates), GoodsCateVO.class);
					mapped.forEach(v -> {
						String[] cateIds = v.getCateIds().split(",");
						ArrayList<GoodsCateVO> goodsCateVOList = new ArrayList<>();
						for (String cateId : cateIds) {
							goodsCateVOList.add(goodsCateVOS.stream().filter(s -> s.getCateId().equals(Long.valueOf(cateId))).findFirst().get());
						}
						v.setGoodsCateVOS(goodsCateVOList);
					});
				}
			}
			//三级微信类目的父类目
			List<WechatCateDTO> parentCates = JSON.parseArray(JSON.toJSONString(thirdGoodsCateRepository.findBycateIdsAndThirdPlatformType(
					third.stream()
							.flatMap(v -> Stream.of(v.getCatePath().split("\\|")))
							.distinct().filter(s -> !s.equals("0"))
							.map(t -> Long.valueOf(t)).collect(Collectors.toList()), ThirdPlatformType.WECHAT_VIDEO)), WechatCateDTO.class);
			//一级类目
			List<WechatCateDTO> one = parentCates.stream().filter(v -> v.getCateGrade().equals(1)).collect(Collectors.toList());
			parentCates.addAll(third);
			//查找子类目
			for (WechatCateDTO thirdGoodsCate : one) {
				this.getChildrenCate(thirdGoodsCate, parentCates);
			}
			return one;
		} else {
			return new ArrayList<>();
		}
	}


	//递归微信子类目
	public void getChildrenCate(WechatCateDTO cateRelVO, List<WechatCateDTO> thirdGoodsCateRelVOS) {
		List<WechatCateDTO> children = thirdGoodsCateRelVOS.stream().filter(v -> v.getCateParentId().equals(cateRelVO.getCateId())).collect(Collectors.toList());
		if (children.size() > 0) {
			cateRelVO.setChildren(children);
			for (WechatCateDTO child : children) {
				getChildrenCate(child, thirdGoodsCateRelVOS);
			}
		}
	}


	/**
	 * 根据第三级类目查询一二级类目
	 * @param request
	 * @return
	 */
	public List<GradeResponse> gradeBycateIds(GradeRequest request) {
		List<ThirdGoodsCate> thirdCate = thirdGoodsCateRepository.findBycateIdsAndThirdPlatformType(request.getCateIds(), request.getThirdPlatformType());
		if (CollectionUtils.isNotEmpty(thirdCate)) {
			//三级类目
			List<ThirdGoodsCateVO> thirdGoodsCateVOS = JSON.parseArray(JSON.toJSONString(thirdCate), ThirdGoodsCateVO.class);
			//父级类目id
			List<Long> parentCateIds = thirdGoodsCateVOS.stream()
					.flatMap((v -> Stream.of(v.getCatePath().split("\\|"))
							.filter(t -> !t.equals("0")).map(s -> Long.valueOf(s)))).collect(Collectors.toList());
			List<ThirdGoodsCateVO> parentCateVOS = JSON.parseArray(JSON.toJSONString(thirdGoodsCateRepository.findBycateIdsAndThirdPlatformType(parentCateIds, request.getThirdPlatformType())), ThirdGoodsCateVO.class);
			return thirdGoodsCateVOS.stream().map(v -> {
				GradeResponse gradeResponse = new GradeResponse();
				gradeResponse.setThirdGrade(v);
				List<Long> parentCateId = Stream.of(v.getCatePath().split("\\|")).map(s -> Long.valueOf(s)).collect(Collectors.toList());
				gradeResponse.setSecondGrade(parentCateVOS.stream().filter(s -> s.getCateId().equals(parentCateId.get(2))).findFirst().get());
				gradeResponse.setOneGrade(parentCateVOS.stream().filter(s -> s.getCateId().equals(parentCateId.get(1))).findFirst().get());
				return gradeResponse;
			}).collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * 查询可供审核的微信类目并关联平台类目
	 * @return
	 */
	public List<WechatCateDTO> listWechatForAudit() {
		List<WechatCateDTO> wechatCateDTOS = JSON.parseArray(JSON.toJSONString(thirdGoodsCateRepository.findByThirdPlatformTypeAndDelFlag(ThirdPlatformType.WECHAT_VIDEO,DeleteFlag.NO)),WechatCateDTO.class);
		if (CollectionUtils.isNotEmpty(wechatCateDTOS)) {
			Map<Long, AuditStatus> auditStatusMap = wechatCateAuditRepository.findAll().stream().collect(Collectors.toMap(v -> v.getWechatCateId(), t -> t.getAuditStatus()));
			wechatCateDTOS.stream().filter(v->v.getCateGrade().equals(3)).forEach(s->{
				AuditStatus auditStatus = auditStatusMap.get(s.getCateId());
				if (auditStatus != null) {
					s.setAuditStatus(auditStatus);
				}
			});
			//一级类目
			List<WechatCateDTO> oneGrade = wechatCateDTOS.stream().filter(v -> v.getCateGrade().equals(1)).collect(Collectors.toList());
			for (WechatCateDTO thirdGoodsCateRelVO : oneGrade) {
				getChildrenCate(thirdGoodsCateRelVO, wechatCateDTOS);
			}
			return oneGrade;
		}else {
			return new ArrayList<>();
		}
	}

	public void wechatAudit(WechatAuditRequest request) {
		List<WechatAuditDTO> wechatAuditDTOS = request.getWechatAuditDTOS();
		List<Long> wechatCateIds = wechatAuditDTOS.stream().map(v -> v.getLevel3()).collect(Collectors.toList());
		RLock lock = redissonClient.getLock(GoodsConstant.wechatCateAuditLock);
		lock.lock();
		try {
			if (wechatCateAuditRepository.countChecked(wechatCateIds) > 0) {
				throw new SbcRuntimeException(GoodsErrorCodeEnum.K030183);
			}
			if (wechatCateAuditRepository.countWaitCheck(wechatCateIds) > 0) {
				throw new SbcRuntimeException(GoodsErrorCodeEnum.K030184);
			}
			ArrayList<Long> mapedCateIds = new ArrayList<>();//映射过的平台类目
			List<String> cateIdSring = wechatCateAuditRepository.selectCateIds(wechatCateIds);
			if (CollectionUtils.isNotEmpty(cateIdSring)) {
				mapedCateIds.addAll(cateIdSring.stream().filter(s -> StringUtils.isNotBlank(s)).flatMap(v -> Stream.of(v.split(","))).map(s -> Long.valueOf(s)).collect(Collectors.toList()));
			}
			// 查询本次提审类目中，不通过旧记录的微信类目ID
			List<Long> notPassWechatCateIds = wechatCateAuditRepository.findByWechatCateIdsAndAuditStatus(wechatCateIds, AuditStatus.NOT_PASS).stream()
					.map(WechatCateAudit::getWechatCateId).collect(Collectors.toList());
			mapedCateIds.addAll(goodsCateThirdCateRelRepository.selectCateIdByThirdPlatformType(ThirdPlatformType.WECHAT_VIDEO, wechatCateIds));
			if (wechatAuditDTOS.stream().filter(v -> CollectionUtils.isNotEmpty(v.getCateIds())).flatMap(s -> s.getCateIds().stream()).distinct().anyMatch(v -> mapedCateIds.contains(v))) {
				throw new SbcRuntimeException(GoodsErrorCodeEnum.K030182);
			}
			List<WechatCateAudit> wechatCateAudits = new ArrayList<>();
			Exception exception = null;
			Map<Boolean, List<WechatAuditDTO>> auditMap = wechatAuditDTOS.stream().collect(Collectors.partitioningBy(v -> Integer.valueOf(1).equals(v.getQualificationType())));
			List<WechatAuditDTO> needAudit = auditMap.get(true);//需要审核的类目
			List<WechatAuditDTO> doNotNeedAudit = auditMap.get(false);//不需要审核的类目
			if (CollectionUtils.isNotEmpty(needAudit)) {
				ArrayList<WechatCateCertificate> wechatCateCertificates = new ArrayList<>();
				int poolSize = Math.min(Runtime.getRuntime().availableProcessors() * 2, wechatAuditDTOS.size());
				ThreadPoolExecutor executor = new ThreadPoolExecutor(poolSize, poolSize, 1000L, TimeUnit.MILLISECONDS,
						new ArrayBlockingQueue(poolSize * 2), Executors.defaultThreadFactory(),
						new ThreadPoolExecutor.CallerRunsPolicy());
				ArrayList<Future<WechatCateAudit>> auditFutures = new ArrayList<>();
				//上传类目资质
				for (WechatAuditDTO wechatAuditDTO : needAudit) {
					wechatCateCertificates.addAll(wechatAuditDTO.getCertificateUrls().stream().map(v->new WechatCateCertificate(null,wechatAuditDTO.getLevel3(),v,LocalDateTime.now(),request.getOperatorId())).collect(Collectors.toList()));
					auditFutures.add(executor.submit(() -> {
						SellPlatformAuditCateVO wxAuditCateVO = new SellPlatformAuditCateVO();
						wxAuditCateVO.setLevel1(wechatAuditDTO.getLevel1().intValue());
						wxAuditCateVO.setLevel2(wechatAuditDTO.getLevel2().intValue());
						wxAuditCateVO.setLevel3(wechatAuditDTO.getLevel3().intValue());
						wxAuditCateVO.setCertificate(wechatAuditDTO.getCertificateUrls());
						SellPlatformAuditCateRequest wxChannelsAuditCateRequest = new SellPlatformAuditCateRequest();
						wxChannelsAuditCateRequest.setLicense(request.getBusinessLicence());
						wxChannelsAuditCateRequest.setCategory_info(wxAuditCateVO);
						wxChannelsAuditCateRequest.setScene_group_list(Collections.singletonList(1));
						WechatCateAudit wechatCateAudit = new WechatCateAudit();
						try{
							wechatCateAudit.setAuditId(sellPlatformCateProvider.auditCate(wxChannelsAuditCateRequest).getContext().getAudit_id());
						} catch (SbcRuntimeException e){
							String errorCode = e.getErrorCode();
							if (AUDITING.equals(errorCode)) {
								// 审核中
								wechatCateAudit.setAuditStatus(AuditStatus.WAIT_CHECK);
							}
						}
						wechatCateAudit.setAuditStatus(AuditStatus.WAIT_CHECK);
						wechatCateAudit.setWechatCateId(wechatAuditDTO.getLevel3());
						wechatCateAudit.setCateIds(String.join(",", wechatAuditDTO.getCateIds().stream().map(s -> String.valueOf(s)).collect(Collectors.toList())));
						wechatCateAudit.setCreateTime(LocalDateTime.now());
						wechatCateAudit.setCreatePerson(request.getOperatorId());
						if (CollectionUtils.isNotEmpty(wechatAuditDTO.getProductQualificationUrls())) {
							wechatCateAudit.setProductQualificationUrls(String.join(",",wechatAuditDTO.getProductQualificationUrls()));
						}
						return wechatCateAudit;
					}));
				}
				executor.shutdown();
				for (Future<WechatCateAudit> auditFuture : auditFutures) {
					try {
						wechatCateAudits.add(auditFuture.get());
					} catch (InterruptedException e) {
						exception = e;
						Thread.currentThread().interrupt();
					} catch (ExecutionException e) {
						exception = e;
					}
				}
				if (CollectionUtils.isNotEmpty(wechatCateAudits)) {
					transactionTemplate.executeWithoutResult(status -> {
						List<Long> successful = wechatCateAudits.stream().map(v -> v.getWechatCateId()).collect(Collectors.toList());
						wechatCateCertificateRepository.delByCateIds(successful);
						wechatCateAuditRepository.delByCateIds(successful);
						wechatCateCertificateRepository.saveAll(wechatCateCertificates.stream().filter(v -> successful.contains(v.getCateId())).collect(Collectors.toList()));
						if (CollectionUtils.isNotEmpty(notPassWechatCateIds)) {
							// 删除本次类目提审中，不通过的旧记录
							wechatCateAuditRepository.deleteAllByWechatCateIds(notPassWechatCateIds);
						}
						//批量保存微信类目审核记录
						wechatCateAuditRepository.saveAll(wechatCateAudits);
					});
				}
			}
			if (CollectionUtils.isNotEmpty(doNotNeedAudit)) {
				ArrayList<GoodsCateThirdCateRel> goodsCateThirdCateRels = new ArrayList<>();
				for (WechatAuditDTO wechatAuditDTO : doNotNeedAudit) {
					if (CollectionUtils.isNotEmpty(wechatAuditDTO.getCateIds())) {
						goodsCateThirdCateRels.addAll(wechatAuditDTO.getCateIds().stream().map(v -> {
							GoodsCateThirdCateRel goodsCateThirdCateRel = new GoodsCateThirdCateRel();
							goodsCateThirdCateRel.setCateId(v);
							goodsCateThirdCateRel.setThirdCateId(wechatAuditDTO.getLevel3());
							goodsCateThirdCateRel.setThirdPlatformType(ThirdPlatformType.WECHAT_VIDEO);
							goodsCateThirdCateRel.setCreateTime(LocalDateTime.now());
							goodsCateThirdCateRel.setDelFlag(DeleteFlag.NO);
							return goodsCateThirdCateRel;
						}).collect(Collectors.toList()));
					}
				}
				transactionTemplate.executeWithoutResult(status -> {
					if (CollectionUtils.isNotEmpty(goodsCateThirdCateRels)) {
						goodsCateThirdCateRelRepository.saveAll(goodsCateThirdCateRels);
					}
					if (CollectionUtils.isNotEmpty(notPassWechatCateIds)) {
						// 删除本次类目提审中，不通过的旧记录
						wechatCateAuditRepository.deleteAllByWechatCateIds(notPassWechatCateIds);
					}
					wechatCateAuditRepository.saveAll(doNotNeedAudit.stream().map(v -> {
						WechatCateAudit wechatCateAudit = new WechatCateAudit();
						wechatCateAudit.setWechatCateId(v.getLevel3());
						wechatCateAudit.setAuditStatus(AuditStatus.CHECKED);
						wechatCateAudit.setCateIds(String.join(",", v.getCateIds().stream().map(s -> String.valueOf(s)).collect(Collectors.toList())));
						wechatCateAudit.setCreateTime(LocalDateTime.now());
						wechatCateAudit.setCreatePerson(request.getOperatorId());
						if (CollectionUtils.isNotEmpty(v.getProductQualificationUrls())) {
							wechatCateAudit.setProductQualificationUrls(String.join(",",v.getProductQualificationUrls()));
						}
						return wechatCateAudit;
					}).collect(Collectors.toList()));
				});
			}
			if (exception != null) {
				Throwable cause = exception.getCause();
				if (cause instanceof SbcRuntimeException) {
					throw (SbcRuntimeException) cause;
				}
				throw new RuntimeException(exception);
			}
		} finally {
			if (lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

	/**
	 * 查询审核失败的微信类目
	 * @return
	 */
	public WechatCateDTO getUnchekedWechatCate(Long cateId) {
		WechatCateDTO wechatCateDTO = wechatCateAuditRepository.joinGoodsCateByCateId(cateId);
		if (StringUtils.isNotBlank(wechatCateDTO.getCateIds())) {
			wechatCateDTO.setGoodsCateVOS(JSON.parseArray(JSON.toJSONString(goodsCateService.findByIds(Stream.of(wechatCateDTO.getCateIds().split(",")).map(Long::valueOf).collect(Collectors.toList()))), GoodsCateVO.class));
		}
		List<WechatCateCertificate> list = wechatCateCertificateService.list(WechatCateCertificateQueryRequest.builder().cateId(cateId).build());
		if (CollectionUtils.isNotEmpty(list)) {
			wechatCateDTO.setCertificateUrls(list.stream().map(v->v.getCertificateUrl()).collect(Collectors.toList()));
		}
		return wechatCateDTO;
	}

}
