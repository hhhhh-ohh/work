package com.wanmi.sbc.order.wxpayuploadshippinginfo.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.empower.api.provider.pay.weixin.WxPayProvider;
import com.wanmi.sbc.empower.api.request.pay.weixin.*;
import com.wanmi.sbc.empower.api.response.pay.weixin.WxPayResultResponse;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.order.api.request.paycallbackresult.PayCallBackResultQueryRequest;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesQueryRequest;
import com.wanmi.sbc.order.api.request.wxpayuploadshippinginfo.WxPayUploadShippingInfoListRequest;
import com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo.WxPayShippingOrderStatusResponse;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.vo.WxPayUploadShippingInfoVO;
import com.wanmi.sbc.order.paycallback.PayCallBackUtil;
import com.wanmi.sbc.order.paycallback.wechat.WxPayV3AndRefundCallBackService;
import com.wanmi.sbc.order.paycallbackresult.model.root.PayCallBackResult;
import com.wanmi.sbc.order.paycallbackresult.service.PayCallBackResultService;
import com.wanmi.sbc.order.paytimeseries.model.root.PayTimeSeries;
import com.wanmi.sbc.order.paytimeseries.service.PayTimeSeriesService;
import com.wanmi.sbc.order.trade.model.entity.value.Logistics;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.ShippingItem;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.request.TradeQueryRequest;
import com.wanmi.sbc.order.trade.service.TradeService;
import com.wanmi.sbc.setting.api.provider.expresscompanythirdrel.ExpressCompanyThirdRelQueryProvider;
import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.ThirdExpressCompanyListRequest;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyThirdRelDetailVO;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.order.wxpayuploadshippinginfo.repository.WxPayUploadShippingInfoRepository;
import com.wanmi.sbc.order.wxpayuploadshippinginfo.model.root.WxPayUploadShippingInfo;
import com.wanmi.sbc.order.api.request.wxpayuploadshippinginfo.WxPayUploadShippingInfoQueryRequest;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.sensitiveword.SensitiveWordsSignUtil;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>微信小程序支付发货信息业务逻辑</p>
 * @author 吕振伟
 * @date 2023-07-24 14:13:21
 */
@Service("WxPayUploadShippingInfoService")
@Slf4j
public class WxPayUploadShippingInfoService {
	@Autowired
	private WxPayUploadShippingInfoRepository wxPayUploadShippingInfoRepository;

	@Autowired
	private PayTimeSeriesService payTimeSeriesService;

	@Autowired
	private PayCallBackResultService payCallBackResultService;

	@Autowired
	private PayCallBackUtil payCallBackUtil;

	@Autowired
	private TradeService tradeService;

    @Autowired
    private WxPayProvider wxPayProvider;

    @Autowired private ExpressCompanyThirdRelQueryProvider expressCompanyThirdRelQueryProvider;

    private static final String AMOUNT = "amount";

    @Autowired private RedissonClient redissonClient;

	public WxPayUploadShippingInfo getOne(Long id){
		return wxPayUploadShippingInfoRepository.getById(id);
	}


	public WxPayUploadShippingInfo getOneByBusinessId(String transactionId){
		return wxPayUploadShippingInfoRepository.findByTransactionId(transactionId).orElse(null);
	}

	/**
	 * 新增微信小程序支付发货信息
	 * @author 吕振伟
	 */
	@Transactional
	public WxPayUploadShippingInfo add(WxPayUploadShippingInfo entity) {
		wxPayUploadShippingInfoRepository.save(entity);
		return entity;
	}

	/**
	 * 修改微信小程序支付发货信息
	 * @author 吕振伟
	 */
	@Transactional
	public WxPayUploadShippingInfo modify(WxPayUploadShippingInfo entity) {
		wxPayUploadShippingInfoRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除微信小程序支付发货信息
	 * @author 吕振伟
	 */
	@Transactional
	public void deleteById(Long id) {
		wxPayUploadShippingInfoRepository.deleteById(id);
	}

	/**
	 * 批量删除微信小程序支付发货信息
	 * @author 吕振伟
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		wxPayUploadShippingInfoRepository.deleteByIdList(ids);
	}

	/**
	 * 分页查询微信小程序支付发货信息
	 * @author 吕振伟
	 */
	public Page<WxPayUploadShippingInfo> page(WxPayUploadShippingInfoQueryRequest queryReq){
		return wxPayUploadShippingInfoRepository.findAll(
				WxPayUploadShippingInfoWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询微信小程序支付发货信息
	 * @author 吕振伟
	 */
	public List<WxPayUploadShippingInfo> list(WxPayUploadShippingInfoQueryRequest queryReq){
		return wxPayUploadShippingInfoRepository.findAll(WxPayUploadShippingInfoWhereCriteriaBuilder.build(queryReq));
	}



	/**
	 * 将实体包装成VO
	 * @author 吕振伟
	 */
	public WxPayUploadShippingInfoVO wrapperVo(WxPayUploadShippingInfo wxPayUploadShippingInfo) {
		if (wxPayUploadShippingInfo != null){
			WxPayUploadShippingInfoVO wxPayUploadShippingInfoVO = KsBeanUtil.convert(wxPayUploadShippingInfo, WxPayUploadShippingInfoVO.class);
			return wxPayUploadShippingInfoVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author 吕振伟
	 */
	public Long count(WxPayUploadShippingInfoQueryRequest queryReq) {
		return wxPayUploadShippingInfoRepository.count(WxPayUploadShippingInfoWhereCriteriaBuilder.build(queryReq));
	}
	/**
	 * 处理微信小程序后台自动发货
	 * @param tradeIds 订单号
	 */
	public void syncByTradeIds(List<String> tradeIds) {
		List<Trade> tradeList = tradeService.details(tradeIds);
		for(Trade trade : tradeList) {
			String tradeId = trade.getId();
			try {
				log.info("开始处理微信小程序自动发货, 订单号: {}", tradeId);
				// 0. 判断支付方式是否为微信支付
				if (trade.getPayWay() == null || !PayWay.WECHAT.equals(trade.getPayWay())) {
					log.info("订单支付方式不是微信支付, 订单号: {}, 支付方式: {}", tradeId, trade.getPayWay());
					return;
				}
				DeliverStatus deliverStatus = trade.getTradeState().getDeliverStatus();
				FlowState flowState = trade.getTradeState().getFlowState();
				if (!(deliverStatus == DeliverStatus.SHIPPED && Arrays.asList(FlowState.COMPLETED, FlowState.DELIVERED).contains(flowState))) {
					log.info("当前订单没有进入待收货状态，暂不处理, 订单号: {}, 发货状态: {}, 订单状态: {}", tradeId, deliverStatus, flowState);
					return;
				}

				// 2. 处理主订单
				log.info("开始处理主订单微信支付自动发货, 订单号: {}", tradeId);
				this.processOrderWxPayUploadShipping(trade, tradeId, true);

				// 3. 如果是定金预售，处理尾款订单
				String tailOrderNo = trade.getTailOrderNo();
				if (StringUtils.isNotBlank(tailOrderNo) && payCallBackUtil.isTailPayOrder(tailOrderNo)) {
					log.info("开始处理尾款订单微信支付自动发货, 订单号: {}, 尾款订单号: {}", tradeId, tailOrderNo);
					this.processOrderWxPayUploadShipping(trade, tailOrderNo, false);
				}
			} catch (Exception e) {
				log.error("微信支付自动发货处理异常, 订单号: {}, 异常信息: ", tradeId, e);
			}
		}
	}

	/**
	 * 处理订单的微信支付自动发货
	 * @param trade 订单信息
	 * @param businessId 业务ID(订单ID)
	 * @param needQueryParentId 是否需要根据parentId查询
	 */
	@Transactional
	public void processOrderWxPayUploadShipping(Trade trade, String businessId, boolean needQueryParentId) {
		log.info("开始处理订单微信支付回调查询, 订单号: {}, 业务ID: {}, 是否需要查询parentId: {}",
				trade.getId(), businessId, needQueryParentId);

		// 1、根据订单号调用微信小程序后台发货信息录入接口
		List<String> businessIdList = Collections.singletonList(businessId);

		List<PayCallBackResult> payCallBackResults =
				this.handleWxPayUploadShippingInfo(businessIdList);
		log.info("查询微信支付回调结果, 订单号: {}, 业务ID: {}, 查询结果数量: {}",
				trade.getId(), businessId, CollectionUtils.isEmpty(payCallBackResults) ? 0 : payCallBackResults.size());

		// 2. 订单号没有查询到微信支付回调结果，则根据parentId调用微信小程序后台发货信息录入接口
		if (needQueryParentId && CollectionUtils.isEmpty(payCallBackResults) && StringUtils.isNotEmpty(trade.getParentId())) {
			businessIdList = Collections.singletonList(trade.getParentId());
			payCallBackResults =
					this.handleWxPayUploadShippingInfo(businessIdList);

			log.info("根据parentId查询微信支付回调结果完成, 订单号: {}, parentId: {}, 查询结果数量: {}",
					trade.getId(), trade.getParentId(), CollectionUtils.isEmpty(payCallBackResults) ? 0 : payCallBackResults.size());
		}

		// 3. 如果仍然没有回调结果，则不处理
		if (CollectionUtils.isEmpty(payCallBackResults)) {
			log.info("未查询到微信支付回调结果, 不处理, 订单号: {}, 业务ID: {}", trade.getId(), businessId);
		}
	}


	/**
	 * 补偿处理推送失败的数据
	 * @param wxPayUploadShippingInfoListReq
	 */
	public void handleWxPayUploadShippingInfo(WxPayUploadShippingInfoListRequest wxPayUploadShippingInfoListReq) {
		WxPayUploadShippingInfoQueryRequest queryReq = KsBeanUtil.convert(wxPayUploadShippingInfoListReq, WxPayUploadShippingInfoQueryRequest.class);
		//获取推送失败的数据
		List<WxPayUploadShippingInfo> wxPayUploadShippingInfoList = wxPayUploadShippingInfoRepository.findAll(WxPayUploadShippingInfoWhereCriteriaBuilder.build(queryReq));
		//1、从wxPayUploadShippingInfoList收集businessId list集合
		List<String> businessIdList = wxPayUploadShippingInfoList.stream().map(WxPayUploadShippingInfo::getBusinessId).collect(Collectors.toList());

		// 没有需要处理的数据，直接返回
		if (CollectionUtils.isEmpty(businessIdList)) {
			return;
		}

		handleWxPayUploadShippingInfo(businessIdList);
	}

	/**
	 * 根据订单号或者父订单号调用微信小程序后台发货信息录入接口
	 * @param businessIdList
	 */
	@Transactional
	public List<PayCallBackResult> handleWxPayUploadShippingInfo(List<String> businessIdList) {
		log.info("开始处理微信小程序后台发货信息录入, 业务ID: {}", JSON.toJSONString(businessIdList));
		List<PayCallBackResult> payCallBackResultList = new ArrayList<>();
		try{
			//1、获取支付关联关系数据
			List<PayTimeSeries> payTimeSeriesList = payTimeSeriesService.list(PayTimeSeriesQueryRequest.builder().businessIdList(businessIdList).status(TradeStatus.SUCCEED).build());

			if (CollectionUtils.isNotEmpty(payTimeSeriesList)) {
					//2、将payTimeSeriesList收集成map，key为payNo，value为businessId
					Map<String, String> payTimeSeriesMap = payTimeSeriesList.stream().collect(Collectors.toMap(PayTimeSeries::getPayNo, PayTimeSeries::getBusinessId));
					//3、从payTimeSeriesList 收集 payNo List集合
					List<String> payNoList = payTimeSeriesList.stream().map(PayTimeSeries::getPayNo).collect(Collectors.toList());
					//4、获取支付回调记录集合
					payCallBackResultList = payCallBackResultService.list(PayCallBackResultQueryRequest.builder().businessIds(payNoList).build());
					log.info("微信小程序后台发货信息录入, 查询微信支付回调结果, 查询结果数量: {}", payCallBackResultList.size());
					//5、遍历payCallBackResultList
					payCallBackResultList.forEach(payCallBackResult -> {
						WxPayResultResponse wxPayResultResponse = new WxPayResultResponse();
						String resultContext = payCallBackResult.getResultXml();
						if(StringUtils.isNotBlank(resultContext) && resultContext.startsWith("<xml>")){
							XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
							xStream.alias("xml", WxPayResultResponse.class);
							Class<?>[] classes = new Class[] { WxPayResultResponse.class};
							xStream.allowTypes(classes);
							wxPayResultResponse =
									(WxPayResultResponse) xStream.fromXML(payCallBackResult.getResultContext());
						} else if(StringUtils.isNotBlank(resultContext) && resultContext.startsWith("{")) {
							JSONObject callBackJson = JSON.parseObject(resultContext);
							wxPayResultResponse.setMch_id(callBackJson.getString("mchid"));
							wxPayResultResponse.setOut_trade_no(callBackJson.getString("out_trade_no"));
							wxPayResultResponse.setTransaction_id(callBackJson.getString("transaction_id"));
							wxPayResultResponse.setTrade_type(callBackJson.getString("trade_type"));
							wxPayResultResponse.setResult_code(callBackJson.getString("trade_state"));
							if(Objects.nonNull(callBackJson.getString("success_time"))){
								wxPayResultResponse.setTime_end(
										this.getTime(
												callBackJson.getString("success_time"))); //  2018-06-08T10:34:56+08:00
							}
							// 封装用户信息
							if (callBackJson.containsKey("payer")) {
								JSONObject payerJson = callBackJson.getJSONObject("payer");
								wxPayResultResponse.setOpenid(payerJson.getString("openid"));
							}
							// 封装支付金额信息
							if (callBackJson.containsKey(AMOUNT)) {
								JSONObject amountJson = callBackJson.getJSONObject(AMOUNT);
								wxPayResultResponse.setTotal_fee(amountJson.getString("total"));
								wxPayResultResponse.setFee_type(amountJson.getString("currency"));
								wxPayResultResponse.setCash_fee(amountJson.getString("payer_total"));
								wxPayResultResponse.setCash_fee_type(amountJson.getString("payer_currency"));
							}
						}


					if("JSAPI".equals(wxPayResultResponse.getTrade_type())){
						String businessId = payTimeSeriesMap.get(wxPayResultResponse.getOut_trade_no());
						boolean isMergePay = payCallBackUtil.isMergePayOrder(businessId);
						// 是否是授信还款
						boolean isCreditRepay = payCallBackUtil.isCreditRepayFlag(businessId);
						Trade trade = null;
						if (payCallBackUtil.isTailPayOrder(businessId)) {
							trade = tradeService.queryAll(TradeQueryRequest.builder().tailOrderNo(businessId).build()).get(0);
						} else {
							trade = tradeService.detail(businessId);
						}
						if(Objects.nonNull(trade) || isMergePay){
							wxPayUploadShippingInfo(trade,wxPayResultResponse, businessId, isMergePay, isCreditRepay);
							log.info("调用小程序自动发货接口完成, 订单号: {}, 业务ID: {}", trade.getId(), businessId);
						}
					} else {
						log.info("交易类型不是JSAPI, 不处理, 业务ID: {}", payTimeSeriesMap.get(wxPayResultResponse.getOut_trade_no()));
					}
				});
			} else {
				log.info("微信小程序后台发货信息录入, 未查询到支付关联关系数据, 业务ID: {}", JSON.toJSONString(businessIdList));
			}
		} catch (Exception e){
			log.error("根据订单号或者父订单号调用微信小程序后台发货信息录入接口报错:{}",e);
		}

		return payCallBackResultList;
	}

	/**
	 * @description 微信支付--发货信息录入接口调用
	 * @author
	 * @date
	 * @param wxPayResultResponse1
	 * @return
	 **/
	@Transactional
	public void wxPayUploadShippingInfo(Trade trade,
										WxPayResultResponse wxPayResultResponse1,
										String businessId,
										boolean isMergePay,
										boolean isCreditRepay){
		log.info("微信支付V3--发货信息录入接口调用");
		String transaction_id = wxPayResultResponse1.getTransaction_id();
		RLock rLock = redissonClient.getFairLock("WX_PAY_UPLOAD_SHIPPING:".concat(Objects.toString(transaction_id, "1")));
		rLock.lock();
		try {
			//支付类型，0：商品订单支付；1：授信还款；2：付费会员
			Integer payType = Constants.ZERO;
			//订单单号类型，用于确认需要上传详情的订单。枚举值1，使用下单商户号和商户侧单号；枚举值2，使用微信支付单号
			Integer order_number_type = Constants.TWO;
			//物流模式，发货方式枚举值：1、实体物流配送采用快递公司进行实体物流配送形式 2、同城配送 3、虚拟商品，虚拟商品，例如话费充值，点卡等，无实体配送形式 4、用户自提
			Integer logistics_type = Constants.THREE;
			//发货模式，发货模式枚举值：1、UNIFIED_DELIVERY（统一发货）2、SPLIT_DELIVERY（分拆发货） 示例值: UNIFIED_DELIVERY
			Integer delivery_mode = Constants.ONE;
			String item_desc = "";
			//如果是付费会员
			if (businessId.startsWith(GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID)) {
				item_desc = "付费会员";
				payType = Constants.TWO;
			}
			//如果是授信还款
			if (isCreditRepay) {
				item_desc = "授信还款";
				payType = Constants.ONE;
			}
			//订单支付
			boolean moreOrderFlag = false;
			List<Trade> finalTradeList = new ArrayList<>();
			if (Objects.nonNull(trade)) {
				finalTradeList.add(trade);
			}
			if (isMergePay) {
				log.info("微信支付V3--发货信息录入接口调用，isMergePay:{}", isMergePay);
				//查询订单信息
				List<Trade> tradeList = tradeService.detailsByParentId(businessId);
				log.info("微信支付V3--发货信息录入接口调用，tradeList:{}", tradeList);
				if (!tradeList.isEmpty()) {
					if (tradeList.size() > 1) {
						moreOrderFlag = true;
					}
					trade = tradeList.get(0);
				}
				log.info("微信支付V3--发货信息录入接口调用，trade:{}", trade);
				finalTradeList = tradeList;
			}
			log.info("微信支付V3--发货信息录入接口调用，trade1:{}", trade);
			if (Objects.nonNull(trade)) {
				logistics_type = Constants.ONE;
				//虚拟、卡券订单
				if (Objects.nonNull(trade.getOrderTag()) && (trade.getOrderTag().getVirtualFlag() || trade.getOrderTag().getElectronicCouponFlag())) {
					//3、虚拟商品，虚拟商品，例如话费充值，点卡等
					logistics_type = Constants.THREE;
				}
				if (Boolean.TRUE.equals(trade.getPickupFlag())) {
					//4、用户自提
					logistics_type = Constants.THREE;
				}
				//同城配送
				if (trade.getDeliverWay() != null && DeliverWay.SAME_CITY.equals(trade.getDeliverWay())) {
					logistics_type = Constants.THREE;
				}
				List<TradeItem> tradeItems = trade.getTradeItems();
				if (!tradeItems.isEmpty()) {
					if (moreOrderFlag || tradeItems.size() > 1) {
						item_desc = tradeItems.get(0).getSkuName() + "等商品";
					} else {
						item_desc = tradeItems.get(0).getSkuName();
					}
				} else {
					log.info("微信支付V3--发货信息录入接口调用，item_desc1");
					item_desc = "您购买的商品";
				}
			}
			//快递
			List<ShippingListRequest> shippingListRequestList = new ArrayList<>();
			if (Constants.ONE == logistics_type) {
				if (CollectionUtils.isNotEmpty(finalTradeList)) {
					Map<String, String> mapping = new HashMap<>();
					List<Long> logisticCompanyIds = finalTradeList
							.stream()
							.filter(t -> CollectionUtils.isNotEmpty(t.getTradeDelivers()))
							.flatMap(t -> t.getTradeDelivers().stream())
							.map(t -> t.getLogistics().getLogisticCompanyId())
							.filter(StringUtils::isNotBlank).map(Long::valueOf).distinct().toList();
					if (CollectionUtils.isNotEmpty(logisticCompanyIds)) {
						ThirdExpressCompanyListRequest listRequest = new ThirdExpressCompanyListRequest();
						listRequest.setExpressCompanyId(logisticCompanyIds);
						listRequest.setSellPlatformType(SellPlatformType.MINI_PROGRAM_PAY);
						List<ExpressCompanyThirdRelDetailVO> relDetailList = expressCompanyThirdRelQueryProvider.listWithDetail(listRequest).getContext().getThirdRelDetailList();
						if (CollectionUtils.isNotEmpty(relDetailList)) {
							relDetailList
									.stream()
									.filter(r -> r.getExpressCompanyId() != null && StringUtils.isNotBlank(r.getThirdExpressCompanyCode()))
									.forEach(r -> mapping.put(String.valueOf(r.getExpressCompanyId()), r.getThirdExpressCompanyCode()));
						}
					}
					finalTradeList
							.stream()
							.filter(t -> CollectionUtils.isNotEmpty(t.getTradeDelivers()))
							.forEach(t -> {
								String phone = SensitiveWordsSignUtil.isPhone(ObjectUtils.defaultIfNull(t.getConsignee().getPhone(), t.getBuyer().getPhone()));
								t.getTradeDelivers().forEach(d -> {
									Logistics logistics = d.getLogistics();
									if (logistics != null && StringUtils.isNotBlank(logistics.getLogisticNo())) {
										ShippingListRequest shippingListRequest = new ShippingListRequest();
										shippingListRequest.setTracking_no(logistics.getLogisticNo());
										String shipItemDesc = "商品";
										if (CollectionUtils.isNotEmpty(d.getShippingItems())) {
											ShippingItem shippingItem = d.getShippingItems().get(0);
											shipItemDesc = ObjectUtils.defaultIfNull(shippingItem.getItemName(), "")
													.concat(String.valueOf(shippingItem.getItemNum() == null ? "1" : shippingItem.getItemNum()))
													.concat(ObjectUtils.defaultIfNull(shippingItem.getUnit(), "个"));
											if (d.getShippingItems().size() > 1) {
												shipItemDesc = shipItemDesc + "等商品";
											}
										}
										shippingListRequest.setExpress_company(mapping.get(logistics.getLogisticCompanyId()));
										shippingListRequest.setItem_desc(shipItemDesc);
										ShippingListContactRequest contactRequest = new ShippingListContactRequest();
										contactRequest.setReceiver_contact(phone);
										shippingListRequest.setContact(contactRequest);
										shippingListRequestList.add(shippingListRequest);
									}
								});
							});
				} else {
					ShippingListRequest shippingListRequest = new ShippingListRequest();
					shippingListRequest.setItem_desc(item_desc);
					shippingListRequestList.add(shippingListRequest);
				}
			} else {
				ShippingListRequest shippingListRequest = new ShippingListRequest();
				shippingListRequest.setItem_desc(item_desc);
				shippingListRequestList.add(shippingListRequest);
			}

			log.info("微信支付V3--发货信息录入接口调用，参数组装");
			WxPayUploadShippingInfoRequest wxPayUploadShippingInfoRequest = new WxPayUploadShippingInfoRequest();
			UploadShippingInfoOrderKeyRequest orderKeyRequest = new UploadShippingInfoOrderKeyRequest();
			orderKeyRequest.setOrder_number_type(order_number_type);
			orderKeyRequest.setTransaction_id(transaction_id);
			orderKeyRequest.setOut_trade_no(businessId);
			wxPayUploadShippingInfoRequest.setOrder_key(orderKeyRequest);
			wxPayUploadShippingInfoRequest.setLogistics_type(logistics_type);
			wxPayUploadShippingInfoRequest.setDelivery_mode(delivery_mode);
			wxPayUploadShippingInfoRequest.setShipping_list(shippingListRequestList);

			LocalDateTime localDateTime = LocalDateTime.now();
			ZoneOffset zoneOffset = ZoneOffset.of("+08:00");
			OffsetDateTime offsetDateTime = localDateTime.atOffset(zoneOffset);
			String formattedDateTime = offsetDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
			wxPayUploadShippingInfoRequest.setUpload_time(formattedDateTime);

			UploadShippingInfoPayerRequest payerRequest = new UploadShippingInfoPayerRequest();
			payerRequest.setOpenid(wxPayResultResponse1.getOpenid());
			wxPayUploadShippingInfoRequest.setPayer(payerRequest);
			log.info("微信支付V3--发货信息录入接口调用，查询是否存在记录");
			WxPayUploadShippingInfo wxPayUploadShippingInfo = getOneByBusinessId(transaction_id);
			if (wxPayUploadShippingInfo != null) {
				if (Constants.ONE == wxPayUploadShippingInfo.getResultStatus()) {
					log.info("微信支付V3--发货信息录入接口调用，wxPayUploadShippingInfo:{}，已存在记录", wxPayUploadShippingInfo);
					return;
				}
			} else {
				wxPayUploadShippingInfo = new WxPayUploadShippingInfo();
			}
			wxPayUploadShippingInfo.setBusinessId(businessId);
			wxPayUploadShippingInfo.setTransactionId(transaction_id);
			wxPayUploadShippingInfo.setMchId(wxPayResultResponse1.getMch_id());
			wxPayUploadShippingInfo.setPayType(payType);
			wxPayUploadShippingInfo.setResultStatus(Constants.ZERO);
			wxPayUploadShippingInfo.setErrorNum(Constants.ZERO);
			wxPayUploadShippingInfo.setCreateTime(LocalDateTime.now());
			log.info("微信支付V3--发货信息录入接口调用，logistics_type:{}", logistics_type);
			wxPayUploadShippingInfo.setUpdateTime(LocalDateTime.now());
			log.info("微信支付V3--发货信息录入接口调用，wxPayUploadShippingInfoRequest:{}", wxPayUploadShippingInfoRequest);
			String resultStr = wxPayProvider.wxPayUploadShippingInfo(wxPayUploadShippingInfoRequest).getContext().toString();
			log.info("微信支付V3--发货信息录入接口调用，resultStr:{}", resultStr);
			JSONObject resultJson = JSONObject.parseObject(resultStr);
			String errCode = String.valueOf(resultJson.get("errcode"));
			if (errCode.equals("0")) {
				wxPayUploadShippingInfo.setResultStatus(Constants.ONE);
			} else {
				wxPayUploadShippingInfo.setResultStatus(Constants.TWO);
				wxPayUploadShippingInfo.setErrorNum(Constants.ONE);
			}
			wxPayUploadShippingInfo.setResultContext(resultStr);
			log.info("微信支付V3--发货信息录入接口调用，wxPayUploadShippingInfo:{}", wxPayUploadShippingInfo);
			add(wxPayUploadShippingInfo);
		} catch (Exception e) {
			throw e;
		} finally {
			rLock.unlock();
		}
	}


	/**
	 * 交易流水号
	 * @param tid 订单号
	 * @return 交易状态
	 */
	public WxPayShippingOrderStatusResponse getWxOrderShippingStatus(String tid) {
		WxPayShippingOrderStatusResponse response = new WxPayShippingOrderStatusResponse();
		Trade trade = tradeService.detail(tid);
		if(trade.getPayWay() == null || !PayWay.WECHAT.equals(trade.getPayWay())) {
			return response;
		}
		List<String> businessIds = new ArrayList<>();
		if(StringUtils.isNotBlank(trade.getParentId())) {
			businessIds.add(trade.getParentId());
		}
		if(StringUtils.isNotBlank(trade.getTailOrderNo())) {
			businessIds.add(trade.getTailOrderNo());
		} else {
			businessIds.add(trade.getId());
		}
		WxPayUploadShippingInfoQueryRequest queryRequest = new WxPayUploadShippingInfoQueryRequest();
		queryRequest.setBusinessIdList(businessIds);
		List<WxPayUploadShippingInfo> shopShippingInfos = this.list(queryRequest).stream().filter(t -> StringUtils.isNotBlank(t.getTransactionId())).toList();
		if(CollectionUtils.isEmpty(shopShippingInfos)) {
			return response;
		}
		String transactionId = shopShippingInfos.get(0).getTransactionId();
		response.setWxTransactionId(transactionId);
		WxPayUploadShippingInfoGetOrderRequest indexRequest = new WxPayUploadShippingInfoGetOrderRequest();
		indexRequest.setTransaction_id(transactionId);
		try {
			Integer status = wxPayProvider.wxPayUploadShippingGetOrder(indexRequest).getContext();
			response.setStatus(status);
		}  catch (Exception e) {
			log.error("微信支付V3--查询发货信息接口调用异常", e);
		}
		return response;
	}

	/**
	 * @description 时间格式转换和V2 保持一致
	 * @author wur
	 * @date: 2022/11/30 14:50
	 * @param time
	 * @return
	 */
	private String getTime(String time) {
		StringBuilder timeBuf = new StringBuilder("");
		timeBuf.append(time.substring(0, 10).replace("-", ""));
		timeBuf.append(time.substring(11, 19).replace(":", ""));
		return timeBuf.toString();
	}

}

