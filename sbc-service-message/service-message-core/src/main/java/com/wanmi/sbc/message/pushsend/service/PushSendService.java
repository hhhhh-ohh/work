package com.wanmi.sbc.message.pushsend.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.apppush.AppPushProvider;
import com.wanmi.sbc.empower.api.request.apppush.AppPushCancelRequest;
import com.wanmi.sbc.empower.api.request.apppush.AppPushQueryRequest;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSendRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushCancelResponse;
import com.wanmi.sbc.empower.api.response.apppush.AppPushQueryResponse;
import com.wanmi.sbc.empower.bean.enums.AppPushAppType;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.vo.AppPushSendResultVO;
import com.wanmi.sbc.message.api.request.pushsend.PushSendAddRequest;
import com.wanmi.sbc.message.api.request.pushsend.PushSendModifyRequest;
import com.wanmi.sbc.message.api.request.pushsend.PushSendQueryRequest;
import com.wanmi.sbc.message.bean.enums.PushPlatform;
import com.wanmi.sbc.message.bean.enums.PushStatus;
import com.wanmi.sbc.message.bean.vo.PushSendVO;
import com.wanmi.sbc.message.pushdetail.repository.PushDetailRepository;
import com.wanmi.sbc.message.pushsend.model.root.PushSend;
import com.wanmi.sbc.message.pushsend.repository.PushSendRepository;
import com.wanmi.sbc.message.umengtoken.model.root.UmengToken;
import com.wanmi.sbc.message.umengtoken.repository.UmengTokenRepository;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>会员推送信息业务逻辑</p>
 * @author Bob
 * @date 2020-01-08 17:15:32
 */
@Slf4j
@Service("PushSendService")
public class PushSendService {
	@Autowired
	private PushSendRepository pushSendRepository;
	@Autowired
	private UmengTokenRepository umengTokenRepository;
	@Autowired
	private AppPushProvider appPushProvider;
	@Autowired
	private PushDetailRepository pushDetailRepository;

	/**
	 * 新增会员推送信息
	 * @author Bob
	 */
	@Transactional
	public PushSend add(PushSendAddRequest pushSendAddRequest) {
		PushSend entity = KsBeanUtil.convert(pushSendAddRequest, PushSend.class);
		entity = pushSendRepository.save(entity);

		List<AppPushSendResultVO> resultEntries = this.pushCommon(entity, pushSendAddRequest.getCustomers());
		for (AppPushSendResultVO resultEntry : resultEntries){
			if (AppPushAppType.IOS.equals(resultEntry.getAppType())){
				if (Boolean.TRUE.equals(resultEntry.getSuccess())){
					entity.setIosTaskId(resultEntry.getTaskId());
				} else {
					log.error("PushSendService.add::友盟iOS发送接口失败");
					throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060020, new Object[]{"iOS消息发送"});
				}
			} else if (AppPushAppType.ANDROID.equals(resultEntry.getAppType())) {
				if (Boolean.TRUE.equals(resultEntry.getSuccess())){
					entity.setAndroidTaskId(resultEntry.getTaskId());
				} else {
					log.error("PushSendService.add::友盟android发送接口失败");
					throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060020, new Object[]{"android消息发送"});
				}
			}
		}
		return pushSendRepository.save(entity);
	}

	private List<AppPushSendResultVO> pushCommon(PushSend entity, List<String> customerIdList){

		this.cancel(entity);

		Integer recipient = entity.getMsgRecipient();
		List<String> iosTokenList = new ArrayList<>();
		List<String> androidTokenList = new ArrayList<>();
		Map<PushPlatform, List<UmengToken>> umengTokenList = new HashMap<>();
		if (recipient == 0){
			umengTokenList =
					umengTokenRepository.findAll().stream().collect(Collectors.groupingBy(UmengToken::getPlatform));
		} else {
			umengTokenList =
					umengTokenRepository.queryByCustomerIdIn(customerIdList).map(umengTokens -> umengTokens.stream()
							.collect(Collectors.groupingBy(UmengToken::getPlatform))).orElseGet(HashMap::new);
		}

		if (umengTokenList.get(PushPlatform.IOS) != null ){
			iosTokenList =
					umengTokenList.get(PushPlatform.IOS).stream().map(UmengToken::getDevlceToken).collect(Collectors.toList());
		} else if (Objects.nonNull(entity.getIosTaskId())) {
			pushDetailRepository.deleteById(entity.getIosTaskId());
			entity.setIosTaskId(null);
		}

		if (umengTokenList.get(PushPlatform.ANDROID) != null){
			androidTokenList =
					umengTokenList.get(PushPlatform.ANDROID).stream().map(UmengToken::getDevlceToken).collect(Collectors.toList());
		} else if (Objects.nonNull(entity.getAndroidTaskId())) {
			pushDetailRepository.deleteById(entity.getAndroidTaskId());
			entity.setAndroidTaskId(null);
		}

		JSONObject params = new JSONObject();
		if (StringUtils.isNotBlank(entity.getMsgRouter())){
			String router = entity.getMsgRouter().replaceAll("'", "\"");
			JSONObject jsonObject = JSONObject.parseObject(router);
			String link = jsonObject.getString("linkKey");
			JSONObject info = jsonObject.getJSONObject("info");
			switch (link){
				case "goodsList":
					String skuId = info.getString("skuId");
					params.put("type", 0);
					params.put("skuId", skuId);
					break;
				case "categoryList":
					JSONArray selectedKeys = info.getJSONArray("selectedKeys");
					String pathNames = info.getString("pathName");
					String[] names =  pathNames.split(",");
					String cateId = selectedKeys.getString(selectedKeys.size()-1);
					String cateName = names[names.length-1];
					params.put("type", 3);
					params.put("cateId", cateId);
					params.put("cateName", cateName);
					break;
				case "storeList":
					String storeId = info.getString("storeId");
					params.put("type", 4);
					params.put("storeId", storeId);
					break;
				case "promotionList":
					params.put("type", 5);
					String cateKey = info.getString("cateKey");
					if ("groupon".equals(cateKey)){
						String goodsInfoId = info.getString("goodsInfoId");
						params.put("node", 0);
						params.put("skuId", goodsInfoId);
					} else if ("full".equals(cateKey)){
						String marketingId = info.getString("marketingId");
						params.put("node", 2);
						params.put("mid", marketingId);
					} else if ("flash".equals(cateKey)){
						params.put("node", 1);
						String goodsInfoId = info.getString("goodsInfoId");
						params.put("skuId", goodsInfoId);
					} else if ("onePrice".equals(cateKey)) {
						String marketingId = info.getString("marketingId");
						params.put("node", 2);
						params.put("mid", marketingId);
					} else if ("halfPrice".equals(cateKey)) {
						String marketingId = info.getString("marketingId");
						params.put("node", 2);
						params.put("mid", marketingId);
					} else if ("comBuy".equals(cateKey)) {
						String goodsInfoId = info.getString("goodsInfoId");
						params.put("node", 3);
						params.put("skuId", goodsInfoId);
					} else if ("preOrder".equals(cateKey)) {
						String goodsInfoId = info.getString("goodsInfoId");
						params.put("node", 1);
						params.put("skuId", goodsInfoId);
					} else if ("preSell".equals(cateKey)) {
						String goodsInfoId = info.getString("goodsInfoId");
						params.put("node", 1);
						params.put("skuId", goodsInfoId);
					}
					break;
				case "userpageList":
					params.put("type", 12);
					String appPath = info.getString("appPath");
					params.put("router", appPath);
					break;
				case "pageList":
					params.put("type",6);
					String pageType = info.getString("pageType");
					String pageCode = info.getString("pageCode");
					params.put("pageType", pageType);
					params.put("pageCode", pageCode);
					break;
				case "miniLink":
					params.put("type", 13);
					params.put("pageId", info.getString("pageId"));
					break;
				default:
					break;
			}
		}

		entity.setExpectedSendCount(iosTokenList.size() + androidTokenList.size());
		AppPushSendRequest pushEntry = new AppPushSendRequest();
		pushEntry.setImage(entity.getMsgImg());
//		pushEntry.setOutBizNo(entity.getId().toString());
		pushEntry.setRouter(params.toJSONString());
		pushEntry.setText(entity.getMsgContext());
		pushEntry.setTicker("通知");
		pushEntry.setTitle(entity.getMsgTitle());
		if (Objects.nonNull(entity.getPushTime())){
			pushEntry.setSendTime(entity.getPushTime());
		}
		pushEntry.setIosTokenList(iosTokenList);
		pushEntry.setAndroidTokenList(androidTokenList);
		return appPushProvider.send(pushEntry).getContext().getDataList();
	}

	/**
	 * @Description: 友盟任务取消
	 * @param entity
	 * @Date: 2020/1/12 15:08
	 */
	private void cancel(PushSend entity){
		if (StringUtils.isNotBlank(entity.getIosTaskId())){
			AppPushQueryRequest appPushQueryRequest =
					AppPushQueryRequest.builder().taskId(entity.getIosTaskId()).type(AppPushAppType.IOS).build();
			AppPushQueryResponse resultEntry =
					appPushProvider.query(appPushQueryRequest).getContext();
			if (Boolean.FALSE.equals(resultEntry.getSuccess())){
				log.error("PushSendService.cancel::友盟iOS查询接口失败");
				throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060020, new Object[]{"iOS查询"});
			}

			if (resultEntry.getStatus() == PushStatus.CANCEL.toValue())
				return;
			if (resultEntry.getStatus() == PushStatus.QUEUE.toValue() || resultEntry.getStatus() == PushStatus.SEND.toValue()
			|| resultEntry.getStatus() == PushStatus.JOB_NOT_START.toValue()){
				AppPushCancelRequest appPushCancelRequest =
						AppPushCancelRequest.builder().taskId(entity.getIosTaskId()).type(AppPushAppType.IOS).build();
				AppPushCancelResponse queryResultEntry = appPushProvider.cancel(appPushCancelRequest).getContext();
				if (Boolean.FALSE.equals(queryResultEntry.getSuccess())){
					log.error("PushSendService.cancel::友盟iOS撤销接口失败");
					throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060020, new Object[]{"iOS撤销"});
				}
			} else {
				throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060021);
			}
		}

		if (StringUtils.isNotBlank(entity.getAndroidTaskId())){
			AppPushQueryRequest appPushQueryRequest =
					AppPushQueryRequest.builder().taskId(entity.getAndroidTaskId()).type(AppPushAppType.ANDROID).build();
			AppPushQueryResponse resultEntry =
					appPushProvider.query(appPushQueryRequest).getContext();
			if (Boolean.FALSE.equals(resultEntry.getSuccess())){
				log.error("PushSendService.cancel::友盟android查询接口失败");
				throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060020, new Object[]{"android查询"});
			}

			if (resultEntry.getStatus() == PushStatus.CANCEL.toValue())
				return;
			if (resultEntry.getStatus() == PushStatus.QUEUE.toValue() || resultEntry.getStatus() == PushStatus.SEND.toValue()){
				AppPushCancelRequest appPushCancelRequest =
						AppPushCancelRequest.builder().taskId(entity.getAndroidTaskId()).type(AppPushAppType.ANDROID).build();
				AppPushCancelResponse queryResultEntry = appPushProvider.cancel(appPushCancelRequest).getContext();
				if (Boolean.FALSE.equals(queryResultEntry.getSuccess())){
					log.error("PushSendService.cancel::友盟android撤销接口失败");
					throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060020, new Object[]{"android撤销"});
				}
			} else {
				throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060021);
			}
		}
	}

	/**
	 * 修改会员推送信息
	 * @author Bob
	 */
	@Transactional
	public PushSend modify(PushSendModifyRequest pushSendModifyRequest) {
		PushSend entity = KsBeanUtil.convert(pushSendModifyRequest, PushSend.class);
		PushSend pushSend = pushSendRepository.getOne(entity.getId());

		if (pushSend.getPushTime() == null){
			throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060021);
		}

		if (pushSend.getPushTime().isBefore(LocalDateTime.now())){
			throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060021);
		}

		pushSend.setMsgName(entity.getMsgName());
		pushSend.setMsgTitle(entity.getMsgTitle());
		pushSend.setMsgContext(entity.getMsgContext());
		pushSend.setMsgImg(entity.getMsgImg());
		pushSend.setMsgRecipient(entity.getMsgRecipient());
		pushSend.setMsgRecipientDetail(entity.getMsgRecipientDetail());
		pushSend.setPushTime(entity.getPushTime());
		pushSend.setMsgRouter(entity.getMsgRouter());

		pushSend = pushSendRepository.save(pushSend);

		List<AppPushSendResultVO> resultEntries = this.pushCommon(pushSend, pushSendModifyRequest.getCustomers());

		for (AppPushSendResultVO resultEntry : resultEntries){
			if (AppPushAppType.IOS.equals(resultEntry.getAppType())){
				if (Boolean.TRUE.equals(resultEntry.getSuccess())){
					pushSend.setIosTaskId(resultEntry.getTaskId());
				} else {
					throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060020, new Object[]{"iOS消息发送"});
				}
			} else if (AppPushAppType.ANDROID.equals(resultEntry.getAppType())) {
				if (Boolean.TRUE.equals(resultEntry.getSuccess())){
					pushSend.setAndroidTaskId(resultEntry.getTaskId());
				} else {
					throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060020, new Object[]{"android消息发送"});
				}
			}
		}
		return pushSendRepository.save(pushSend);
	}

	/**
	 * 单个删除会员推送信息
	 * @author Bob
	 */
	@Transactional
	public void deleteById(PushSend entity) {
		Optional<PushSend> pushSend = pushSendRepository.findById(entity.getId());
		PushSend send = pushSend.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "推送消息不存在"));
		if (send.getPushTime() != null){
			if (send.getPushTime().isAfter(LocalDateTime.now())){
				this.cancel(send);
			} else {
				throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060021);
			}
		}

		if (send.getCreateTime().isAfter(LocalDateTime.now())){
			throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060021);
		}
		pushSendRepository.deleteById(entity.getId());
	}

	/**
	 * 批量删除会员推送信息
	 * @author Bob
	 */
	@Transactional
	public void deleteByIdList(List<PushSend> infos) {
		pushSendRepository.saveAll(infos);
	}

	/**
	 * 单个查询会员推送信息
	 * @author Bob
	 */
	public PushSend getOne(Long id){
		return pushSendRepository.findById(id)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "会员推送信息不存在"));
	}

	/**
	 * 分页查询会员推送信息
	 * @author Bob
	 */
	public Page<PushSend> page(PushSendQueryRequest queryReq){
		return pushSendRepository.findAll(
				PushSendWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询会员推送信息
	 * @author Bob
	 */
	public List<PushSend> list(PushSendQueryRequest queryReq){
		return pushSendRepository.findAll(PushSendWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author Bob
	 */
	public PushSendVO wrapperVo(PushSend pushSend) {
		if (pushSend != null){
			PushSendVO pushSendVO = KsBeanUtil.convert(pushSend, PushSendVO.class);
			return pushSendVO;
		}
		return null;
	}
}

