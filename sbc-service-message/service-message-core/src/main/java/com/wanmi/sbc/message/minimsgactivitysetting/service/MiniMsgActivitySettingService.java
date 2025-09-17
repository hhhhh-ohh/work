package com.wanmi.sbc.message.minimsgactivitysetting.service;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.TriggerNodeType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.minimsgcustomerrecord.MiniMsgCustomerRecordProvider;
import com.wanmi.sbc.empower.api.provider.minimsgcustomerrecord.MiniMsgCustomerRecordQueryProvider;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordModifyByActivityIdRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordPageRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformSendMiniMsgRequest;
import com.wanmi.sbc.empower.bean.vo.MiniMsgCustomerRecordVO;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingQueryRequest;
import com.wanmi.sbc.message.bean.enums.ProgramSendStatus;
import com.wanmi.sbc.message.bean.vo.MiniMsgActivitySettingVO;
import com.wanmi.sbc.message.minimsgactivitysetting.model.root.MiniMsgActivitySetting;
import com.wanmi.sbc.message.minimsgactivitysetting.repository.MiniMsgActivitySettingRepository;
import com.wanmi.sbc.message.minimsgtempsetting.model.root.MiniMsgTempSetting;
import com.wanmi.sbc.message.minimsgtempsetting.service.MiniMsgTempSettingService;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>小程序订阅消息配置表业务逻辑</p>
 * @author xufeng
 * @date 2022-08-11 16:16:32
 */
@Service("MiniMsgActivitySettingService")
public class MiniMsgActivitySettingService {
	@Autowired
	private MiniMsgActivitySettingRepository miniMsgActivitySettingRepository;

	@Autowired
	private MiniMsgCustomerRecordQueryProvider miniMsgCustomerRecordQueryProvider;

	@Autowired
	private MiniMsgCustomerRecordProvider miniMsgCustomerRecordProvider;

	@Autowired
	private MiniMsgTempSettingService miniMsgTempSettingService;

	@Autowired
	private MqSendProvider mqSendProvider;

	/**
	 * 新增活动消息配置表
	 * @author xufeng
	 */
	@Transactional
	public MiniMsgActivitySetting add(MiniMsgActivitySetting entity) {
		miniMsgActivitySettingRepository.save(entity);
		return entity;
	}

	@Async
	public void dealCustomerRecord(MiniMsgActivitySetting messageActivitySetting) {
		// 将活动id锁定到客户订阅信息中
		MiniMsgCusRecordPageRequest queryReq =
				MiniMsgCusRecordPageRequest.builder()
						.messageActivityId(Constants.NUM_MINUS_1L)
						.triggerNodeId(TriggerNodeType.NEW_ACTIVITY)
						.sendFlag(Constants.no)
						.build();
		// 每次拉100条
		queryReq.setPageSize(100);
		MicroServicePage<MiniMsgCustomerRecordVO> response =
				miniMsgCustomerRecordQueryProvider.page(queryReq).getContext().getMiniMsgCustomerRecordVOPage();
		int currentPage = 0;
		List<List<MiniMsgCustomerRecordVO>> returnList = Lists.newArrayList();
		while (true) {
			if (Objects.nonNull(response)
					&& !CollectionUtils.isEmpty(response.getContent())) {
				List<MiniMsgCustomerRecordVO> messageCustomerRecords = response.getContent();
				 currentPage++;
				// 根据customerId分组
				Map<String, MiniMsgCustomerRecordVO> messageCustomerRecordMap = messageCustomerRecords.stream().collect(
						Collectors.groupingBy(MiniMsgCustomerRecordVO::getCustomerId,
								Collectors.collectingAndThen(
										Collectors.reducing(( c1,  c2) -> c1.getId().compareTo(c2.getId()) <= 0 ?
												c1 : c2), Optional::get)));

				List<MiniMsgCustomerRecordVO> newResultList = new ArrayList<>(messageCustomerRecordMap.values());
				returnList.add(newResultList);
				if (currentPage >= response.getTotalPages()) {
					response = null;
				} else {
					queryReq.setPageNum(currentPage);
					response = miniMsgCustomerRecordQueryProvider.page(queryReq).getContext().getMiniMsgCustomerRecordVOPage();
				}
			} else {
				break;
			}
		}
		// 分批处理数据
		for (List<MiniMsgCustomerRecordVO> messageCustomerRecordVOS : returnList){
			List<Long> idList = messageCustomerRecordVOS .stream()
					.map(MiniMsgCustomerRecordVO::getId)
					.collect(Collectors.toList());

			// 批量更新活动id到用户订阅信息表中
			miniMsgCustomerRecordProvider.updateActivityIdByIdList(
					MiniMsgCusRecordModifyByActivityIdRequest.builder()
							.activityId(messageActivitySetting.getId())
							.idList(idList).build()
			);
			// 更新完发mq处理数据
			MiniMsgTempSetting miniMsgTempSetting =
					miniMsgTempSettingService.findByTriggerNodeId(TriggerNodeType.NEW_ACTIVITY);
			List<String> toUsers = messageCustomerRecordVOS .stream()
					.map(MiniMsgCustomerRecordVO::getOpenId)
					.collect(Collectors.toList());
			Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(Constants.ONE);
			map1.put("value", messageActivitySetting.getActivityName());
			Map<String, Object> map2 = Maps.newHashMapWithExpectedSize(Constants.ONE);
			map2.put("value", DateUtil.format(messageActivitySetting.getStartTime(), DateUtil.FMT_TIME_1));
			Map<String, Object> map3 = Maps.newHashMapWithExpectedSize(Constants.ONE);
			map3.put("value", DateUtil.format(messageActivitySetting.getEndTime(), DateUtil.FMT_TIME_1));
			Map<String, Object> map4 = Maps.newHashMapWithExpectedSize(Constants.ONE);
			map4.put("value", messageActivitySetting.getContext());
			Map<String, Object> map5 = Maps.newHashMapWithExpectedSize(Constants.ONE);
			map5.put("value", messageActivitySetting.getTips());
			Map<String, Object> map = Maps.newHashMapWithExpectedSize(Constants.ONE);
			map.put("thing2", map1);
			map.put("time6", map2);
			map.put("time9", map3);
			map.put("thing8", map4);
			map.put("thing7", map5);
			// 组装入参
			PlatformSendMiniMsgRequest messageRequest =
					PlatformSendMiniMsgRequest.builder()
							.triggerNodeId(TriggerNodeType.NEW_ACTIVITY)
							.activityId(messageActivitySetting.getId())
							.toUsers(toUsers)
							.templateId(miniMsgTempSetting.getTemplateId())
							.page(messageActivitySetting.getToPage())
							.data(map)
							.build();
			if (messageActivitySetting.getType() == Constants.ZERO){
				// 立即发送，直接发到消息队列
				MqSendDelayDTO mqSendDTO = new MqSendDelayDTO();
				mqSendDTO.setData(JSON.toJSONString(messageRequest));
				mqSendDTO.setTopic(ProducerTopic.SEND_MINI_PROGRAM_SUBSCRIBE_MSG);
				mqSendDTO.setDelayTime(6000L);
				mqSendProvider.sendDelay(mqSendDTO);
			}else {
				// 定时发送，计算间隔时间
				Duration duration = Duration.between(LocalDateTime.now(), messageActivitySetting.getSendTime());
				MqSendDelayDTO mqSendDTO = new MqSendDelayDTO();
				mqSendDTO.setData(JSON.toJSONString(messageRequest));
				mqSendDTO.setTopic(ProducerTopic.SEND_MINI_PROGRAM_SUBSCRIBE_MSG);
				if (duration.isNegative()){
					mqSendDTO.setDelayTime(Constants.NUM_5L);
				}else {
					// 三十天之内的直接放到延时队列
					if (duration.toDays() < Constants.NUM_30L){
						mqSendDTO.setDelayTime(duration.toMillis());
					}
				}
				if (Objects.nonNull(mqSendDTO.getDelayTime())){
					mqSendProvider.sendDelay(mqSendDTO);
				}
			}
		}
	}

	/**
	 * 修改小程序订阅消息配置表
	 * @author xufeng
	 */
	@Transactional
	public MiniMsgActivitySetting modify(MiniMsgActivitySetting entity) {
		miniMsgActivitySettingRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除小程序订阅消息配置表
	 * @author xufeng
	 */
	@Transactional
	public void deleteById(Long id) {
		miniMsgActivitySettingRepository.deleteById(id);
	}

	/**
	 * 修改推送状态
	 * @author xufeng
	 */
	@Transactional
	public void modifyById(ProgramSendStatus sendStatus, Integer preCount, Integer realCount, Long id) {
		miniMsgActivitySettingRepository.modifyById(sendStatus, preCount, realCount, id);
	}

	/**
	 * 修改扫描状态
	 * @author xufeng
	 */
	@Transactional
	public void modifyScanFlagByIds(List<Long> ids) {
		miniMsgActivitySettingRepository.modifyScanFlagByIds(ids);
	}

	/**
	 * 批量删除小程序订阅消息配置表
	 * @author xufeng
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		miniMsgActivitySettingRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询小程序订阅消息配置表
	 * @author xufeng
	 */
	public MiniMsgActivitySetting getOne(Long id){
		return miniMsgActivitySettingRepository.findById(id)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "小程序订阅消息配置表不存在"));
	}

	/**
	 * 分页查询小程序订阅消息配置表
	 * @author xufeng
	 */
	public Page<MiniMsgActivitySetting> page(MiniMsgActivitySettingQueryRequest queryReq){
		return miniMsgActivitySettingRepository.findAll(
				MiniMsgActivitySettingWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询小程序订阅消息配置表
	 * @author xufeng
	 */
	public List<MiniMsgActivitySetting> list(MiniMsgActivitySettingQueryRequest queryReq){
		return miniMsgActivitySettingRepository.findAll(MiniMsgActivitySettingWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author xufeng
	 */
	public MiniMsgActivitySettingVO wrapperVo(MiniMsgActivitySetting miniMsgActivitySetting) {
		if (miniMsgActivitySetting != null){
			MiniMsgActivitySettingVO miniMsgActivitySettingVO = KsBeanUtil.convert(miniMsgActivitySetting, MiniMsgActivitySettingVO.class);
			if (ProgramSendStatus.NOT_SEND.equals(miniMsgActivitySetting.getSendStatus()) && miniMsgActivitySetting.getType()==Constants.ZERO
				&& miniMsgActivitySetting.getPreCount() > Constants.ZERO){
				miniMsgActivitySettingVO.setSendStatus(ProgramSendStatus.SENDING);
			}
			return miniMsgActivitySettingVO;
		}
		return null;
	}

}

