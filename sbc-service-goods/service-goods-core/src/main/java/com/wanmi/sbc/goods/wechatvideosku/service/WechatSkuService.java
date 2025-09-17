package com.wanmi.sbc.goods.wechatvideosku.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EditStatus;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.GoodsConstant;
import com.wanmi.sbc.goods.api.request.goodscatethirdcaterel.GoodsCateThirdCateRelQueryRequest;
import com.wanmi.sbc.goods.api.request.thirdgoodscate.GradeRequest;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatcateaudit.WechatCateAuditQueryRequest;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku.*;
import com.wanmi.sbc.goods.api.response.thirdgoodscate.GradeResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoSaveDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsSaveDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.WechatShelveStatus;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.bean.vo.wechatvideo.WechatSkuVO;
import com.wanmi.sbc.goods.cate.service.GoodsCateService;
import com.wanmi.sbc.goods.goodscatethirdcaterel.model.root.GoodsCateThirdCateRel;
import com.wanmi.sbc.goods.goodscatethirdcaterel.service.GoodsCateThirdCateRelService;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.service.GoodsInfoService;
import com.wanmi.sbc.goods.info.service.GoodsService;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.spec.service.GoodsInfoSpecDetailRelService;
import com.wanmi.sbc.goods.thirdgoodscate.service.ThirdGoodsCateService;
import com.wanmi.sbc.goods.wechatvideocate.wechatcateaudit.model.root.WechatCateAudit;
import com.wanmi.sbc.goods.wechatvideocate.wechatcateaudit.service.WechatCateAuditService;
import com.wanmi.sbc.goods.wechatvideosku.model.root.WechatSku;
import com.wanmi.sbc.goods.wechatvideosku.repository.WechatSkuRepository;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformCateProvider;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformGoodsProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.cate.SellPlatformUploadImgRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.goods.*;
import com.wanmi.sbc.vas.api.response.sellplatform.goods.SellPlatformAddGoodsResponse;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformGoodsInfoAttrVO;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformGoodsInfoVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * <p>微信视频号带货商品业务逻辑</p>
 * @author 
 * @date 2022-04-15 11:23:50
 */
@Service("WechatSkuService")
public class WechatSkuService {

	@Value("${wechat.goods.path}")
	public  String goodsinfo_path;
	@Autowired
	private WechatSkuRepository wechatSkuRepository;

	@Autowired
	private GoodsInfoRepository goodsInfoRepository;

	@Autowired
	private SellPlatformGoodsProvider sellPlatformGoodsProvider;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

	@Autowired
	private GoodsInfoService goodsInfoService;

	@Autowired
	private GoodsCateThirdCateRelService goodsCateThirdCateRelService;

	@Autowired
	private SellPlatformCateProvider sellPlatformCateProvider;

	@Autowired
	private GoodsCateService goodsCateService;

	@Autowired
	private StoreQueryProvider storeQueryProvider;

	@Autowired
	private ThirdGoodsCateService thirdGoodsCateService;

	@Autowired
	private GoodsInfoSpecDetailRelService goodsInfoSpecDetailRelService;

	@Autowired
	private RedissonClient redissonClient;

	@Autowired
	private WechatCateAuditService wechatCateAuditService;

	@Autowired
	private TransactionTemplate transactionTemplate;


	/** 
	 * 新增微信视频号带货商品
	 * @author 
	 */
	public void add(WechatSkuAddRequest request) {
		List<String> goodsInfoIds = request.getGoodsInfoIds();
		List<GoodsInfoVO> goodsInfoList = JSON.parseArray(JSON.toJSONString(goodsInfoRepository.findValidByGoodsInfoIds(goodsInfoIds)), GoodsInfoVO.class);
		if (goodsInfoList.size()<goodsInfoIds.size()) {
			throw new SbcRuntimeException(GoodsErrorCodeEnum.K030137);
		}
		List<String> goodsIds = goodsInfoList.stream().map(v -> v.getGoodsId()).distinct().collect(Collectors.toList());
		if (goodsIds.size()<goodsInfoList.size()) {
			throw new SbcRuntimeException(GoodsErrorCodeEnum.K030136);
		}
		RLock lock = redissonClient.getLock(GoodsConstant.wechatGoodsAddLock+request.getStoreId());
		lock.lock();
		try {
			if (wechatSkuRepository.selectGoodsId(goodsIds).size()>0) {
				throw new SbcRuntimeException(GoodsErrorCodeEnum.K030135);
			}
			List<SellPlatformAddGoodsRequest> wxAddGoodsRequests = preAddOrUpdate(goodsInfoList);
			int poolSize = Math.min(Runtime.getRuntime().availableProcessors() * 2, wxAddGoodsRequests.size());
			ThreadPoolExecutor executor = new ThreadPoolExecutor(poolSize, poolSize, 1000L, TimeUnit.MILLISECONDS,
					new ArrayBlockingQueue(poolSize * 2), Executors.defaultThreadFactory(),
					new ThreadPoolExecutor.CallerRunsPolicy());
			ArrayList<Future<String>> result = new ArrayList<>();
			for (SellPlatformAddGoodsRequest wxAddGoodsRequest : wxAddGoodsRequests) {
				result.add(executor.submit(() -> {
					transactionTemplate.executeWithoutResult(status -> {
						//换取微信图片链接
						String temp_img_url = sellPlatformCateProvider.uploadImg(new SellPlatformUploadImgRequest(wxAddGoodsRequest.getSkus().get(0).getThumb_img())).getContext().getTemp_img_url();
						wxAddGoodsRequest.setHead_img(Collections.singletonList(temp_img_url));
						for (SellPlatformGoodsInfoVO skus : wxAddGoodsRequest.getSkus()) {
							skus.setThumb_img(temp_img_url);
							wechatSkuRepository.save(new WechatSku(null, null,null
									, wxAddGoodsRequest.getOut_product_id(),skus.getOut_sku_id(), EditStatus.checking, WechatShelveStatus.SHELVE,
									null,temp_img_url, null,LocalDateTime.now(),
									null, DeleteFlag.NO, request.getCreatePerson(), null));
						}
						SellPlatformAddGoodsResponse goodsResponse = sellPlatformGoodsProvider.addGoods(wxAddGoodsRequest).getContext();
						SellPlatformGoodsInfoVO sellPlatformGoodsInfoVO = goodsResponse.getSkus().get(0);
						wechatSkuRepository.updateProductId(sellPlatformGoodsInfoVO.getOut_sku_id(),sellPlatformGoodsInfoVO.getSku_id(),goodsResponse.getProduct_id().longValue());
					});
					return "success";
				}));
			}
			Exception exception = null;
			for (Future<String> stringFuture : result) {
				try {
					stringFuture.get();
				} catch (InterruptedException e) {
					exception = e;
					Thread.currentThread().interrupt();
				} catch (ExecutionException e) {
					exception = e;
				}
			}
			executor.shutdown();
			if (exception!=null) {
				Throwable cause = exception.getCause();
				if (cause instanceof SbcRuntimeException) {
					throw (SbcRuntimeException)cause;
				}
				throw new RuntimeException(cause);
			}
		}finally {
			if (lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

	/**
	 * 重新提交微信商品
	 * @param request
	 */
	public BaseResponse reAdd(WechatSkuAddRequest request){
		List<GoodsInfoVO> goodsInfoList = JSON.parseArray(JSON.toJSONString(goodsInfoRepository.findByGoodsInfoIds(request.getGoodsInfoIds())), GoodsInfoVO.class);
		GoodsInfoVO goodsInfoVO = goodsInfoList.get(0);
		RLock lock = redissonClient.getLock(GoodsConstant.wechatGoodsReAddLock + goodsInfoVO.getGoodsId());
		lock.lock();
		if (wechatSkuRepository.countAllByDelFlagAndGoodsIdAndEditStatus(DeleteFlag.NO,goodsInfoVO.getGoodsId(), EditStatus.failure)==0) {
			throw new SbcRuntimeException(GoodsErrorCodeEnum.K030135);
		}
		try {
			List<SellPlatformAddGoodsRequest> sellPlatformAddGoodsRequests = preAddOrUpdate(goodsInfoList);
			for (SellPlatformAddGoodsRequest wxAddGoodsRequest : sellPlatformAddGoodsRequests) {
				//换取微信图片链接
				String temp_img_url = sellPlatformCateProvider.uploadImg(new SellPlatformUploadImgRequest(wxAddGoodsRequest.getSkus().get(0).getThumb_img())).getContext().getTemp_img_url();
				wxAddGoodsRequest.setHead_img(Collections.singletonList(temp_img_url));
				for (SellPlatformGoodsInfoVO skus : wxAddGoodsRequest.getSkus()) {
					skus.setThumb_img(temp_img_url);
				}
			}
			SellPlatformAddGoodsRequest sellPlatformAddGoodsRequest = sellPlatformAddGoodsRequests.get(0);
			return transactionTemplate.execute(transactionStatus -> {
				wechatSkuRepository.reAdd(sellPlatformAddGoodsRequest.getOut_product_id(),EditStatus.checking,sellPlatformAddGoodsRequest.getSkus().get(0).getThumb_img());
				SellPlatformUpdateGoodsRequest wxUpdateGoodsRequest = JSON.parseObject(JSON.toJSONString(sellPlatformAddGoodsRequest), SellPlatformUpdateGoodsRequest.class);
				wxUpdateGoodsRequest.setAuditUpdate(true);
				return sellPlatformGoodsProvider.updateGoods(wxUpdateGoodsRequest);
			});
		}finally {
			if (lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void update(SellPlatformUpdateGoodsRequest wxUpdateGoodsRequest){
		if (wxUpdateGoodsRequest.getAuditUpdate()) {
			wechatSkuRepository.updateAuditStatus(wxUpdateGoodsRequest.getOut_product_id(), EditStatus.checking);
		}
		sellPlatformGoodsProvider.updateGoods(wxUpdateGoodsRequest);
	}

	public void updateGoods(GoodsInfoSaveDTO oldInfo, GoodsInfoSaveDTO newInfo, GoodsSaveDTO oldGoods, GoodsSaveDTO newGoods, GoodsSaveDTO finalGoods) {
		GoodsInfoVO willUpdate = null;//可能待更新的视频号商品
		Boolean auditUpdate = false;//微信商品，更新的字段是否要审核
		String wechatImg = null;//微信端图片链接
		String oldGoodsName = oldInfo.getGoodsInfoName();
		List<WechatSku> wechatSkus = this.list(WechatSkuQueryRequest.builder().delFlag(DeleteFlag.NO).notEditStatus(EditStatus.failure).goodsId(oldGoods.getGoodsId()).build());
		if(org.apache.commons.collections4.CollectionUtils.isEmpty(wechatSkus)) {
			return;
		}
		WechatSku wechatSku = wechatSkus.get(0);
		if (!wechatSku.getGoodsInfoId().equals(newInfo.getGoodsInfoId()))  {
			return;
		}
		if (!oldGoodsName.equals(newGoods.getGoodsName())) {
			willUpdate = JSON.parseObject(JSON.toJSONString(newInfo),GoodsInfoVO.class);
			auditUpdate = true;
		}
		if (newInfo.getMarketPrice()!=null && newInfo.getMarketPrice().compareTo(oldInfo.getMarketPrice())!=0) {
			if (willUpdate==null) {
				willUpdate = JSON.parseObject(JSON.toJSONString(newInfo),GoodsInfoVO.class);
			}
		}
		if (!newInfo.getStock().equals(oldInfo.getStock())) {
			if (willUpdate==null) {
				willUpdate = JSON.parseObject(JSON.toJSONString(newInfo),GoodsInfoVO.class);
			}
		}
		//验证图片是否有更新
		String newImg = null;
		if (!Objects.equals(oldInfo.getGoodsInfoImg(),newInfo.getGoodsInfoImg())) {
			newImg = newInfo.getGoodsInfoImg();
		}
		if (StringUtils.isBlank(newImg)
				&& !Objects.equals(newGoods.getGoodsImg(), oldInfo.getGoodsInfoImg())
				&& !Objects.equals(newGoods.getGoodsImg(), oldGoods.getGoodsImg())) {
			newImg = newGoods.getGoodsImg();
		}

		if (StringUtils.isNotBlank(newImg)) {
			if (willUpdate==null) {
				willUpdate = JSON.parseObject(JSON.toJSONString(newInfo),GoodsInfoVO.class);
			}
			//换取微信端图片链接
			wechatImg = sellPlatformCateProvider.uploadImg(new SellPlatformUploadImgRequest(newImg)).getContext().getTemp_img_url();
			auditUpdate = true;
			wechatSkuRepository.updateImg(newInfo.getGoodsInfoId(),wechatImg);
		}else {
			wechatImg = wechatSku.getImg();
		}
		//无需同步微信
		if (Objects.isNull(willUpdate)) {
			return;
		}

		willUpdate.setGoodsId(newGoods.getGoodsId());
		SellPlatformUpdateGoodsRequest sellPlatformUpdateGoodsRequest;
		if (auditUpdate) {
			willUpdate.setGoodsInfoImg(wechatImg);
			sellPlatformUpdateGoodsRequest = JSON.parseObject(JSON.toJSONString(this.preAddOrUpdate(Collections.singletonList(willUpdate)).get(0)), SellPlatformUpdateGoodsRequest.class);
			sellPlatformUpdateGoodsRequest.setTitle(newGoods.getGoodsName());
			sellPlatformUpdateGoodsRequest.setHead_img(Collections.singletonList(wechatImg));
			sellPlatformUpdateGoodsRequest.getSkus().get(0).setThumb_img(wechatImg);
		} else {
			sellPlatformUpdateGoodsRequest = new SellPlatformUpdateGoodsRequest();
			sellPlatformUpdateGoodsRequest.setOut_product_id(willUpdate.getGoodsId());
			SellPlatformGoodsInfoVO sellPlatformGoodsInfoVO = new SellPlatformGoodsInfoVO();
			sellPlatformGoodsInfoVO.setOut_sku_id(willUpdate.getGoodsInfoId());
			sellPlatformGoodsInfoVO.setStock_num(willUpdate.getStock().intValue());
			sellPlatformGoodsInfoVO.setSale_price(willUpdate.getMarketPrice().multiply(new BigDecimal("100")).intValue());
			sellPlatformGoodsInfoVO.setMarket_price(willUpdate.getMarketPrice().multiply(new BigDecimal("100")).intValue());
			sellPlatformUpdateGoodsRequest.setSkus(Collections.singletonList(sellPlatformGoodsInfoVO));
		}
		sellPlatformUpdateGoodsRequest.setAuditUpdate(auditUpdate);
		sellPlatformUpdateGoodsRequest.setEditStatus(wechatSkus.get(0).getEditStatus());
		this.update(sellPlatformUpdateGoodsRequest);
	}

	/**
	 * @description   商品编辑
	 * @author  wur
	 * @date: 2022/5/13 9:56
	 * @param oldGoodsInfo
	 * @param newGoodsInfo
	 * @param goods
	 * @return
	 **/
	public void updateGoods(GoodsInfo oldGoodsInfo, GoodsInfo newGoodsInfo, Goods goods) {
		List<WechatSku> wechatSkuList = this.list(WechatSkuQueryRequest.builder().delFlag(DeleteFlag.NO).notEditStatus(EditStatus.failure).goodsInfoId(newGoodsInfo.getGoodsInfoId()).build());
		if (org.apache.commons.collections4.CollectionUtils.isEmpty(wechatSkuList)) {
			return;
		}
		WechatSku wechatSku = wechatSkuList.get(0);
		WechatShelveStatus wechatShelveStatus =null;
		if (newGoodsInfo.getAddedFlag().equals(0)) {
			if (wechatSkuList.stream().filter(v->v.getEditStatus().equals(EditStatus.checked)&& v.getWechatShelveStatus().equals(WechatShelveStatus.SHELVE)).count()>0) {
				wechatShelveStatus = WechatShelveStatus.UN_SHELVE;
			}
		}else {
			if (wechatSkuList.stream().filter(v->v.getEditStatus().equals(EditStatus.checked)&& (v.getWechatShelveStatus().equals(WechatShelveStatus.UN_SHELVE)||v.getWechatShelveStatus().equals(WechatShelveStatus.VIOLATION_UN_SHELVE))).count()>0) {
				wechatShelveStatus = WechatShelveStatus.SHELVE;
			}
		}
		if (wechatShelveStatus!=null) {
			this.updateWecahtShelveStatus(Collections.singletonList(newGoodsInfo.getGoodsId()), wechatShelveStatus);
		}
		GoodsInfoVO willUpdate = null;//可能更新的微信商品
		Boolean auditUpdate = false;//微信商品，更新的字段是否要审核
		String wechatImg = null;//微信端图片链接
		String goodsInfoImg = newGoodsInfo.getGoodsInfoImg();
		if (StringUtils.isBlank(goodsInfoImg)) {
			goodsInfoImg = goods.getGoodsImg();
		}
		if (StringUtils.isNotBlank(goodsInfoImg)
				&& (StringUtils.isNotBlank(oldGoodsInfo.getGoodsInfoImg()) && !goodsInfoImg.equals(oldGoodsInfo.getGoodsInfoImg())
					|| StringUtils.isBlank(oldGoodsInfo.getGoodsInfoImg()) && StringUtils.isNotBlank(newGoodsInfo.getGoodsInfoImg()))) {
			if (willUpdate ==null) {
				willUpdate = JSON.parseObject(JSON.toJSONString(newGoodsInfo),GoodsInfoVO.class);
			}
			auditUpdate = true;
			wechatImg = sellPlatformCateProvider.uploadImg(new SellPlatformUploadImgRequest(goodsInfoImg)).getContext().getTemp_img_url();
			wechatSkuRepository.updateImg(newGoodsInfo.getGoodsInfoId(),wechatImg);
		} else {
			wechatImg = wechatSku.getImg();
		}
		if (newGoodsInfo.getMarketPrice()!=null && newGoodsInfo.getMarketPrice().compareTo(oldGoodsInfo.getMarketPrice()) !=0) {
			if (willUpdate ==null) {
				willUpdate = JSON.parseObject(JSON.toJSONString(newGoodsInfo),GoodsInfoVO.class);
			}
		}
		if (willUpdate!=null) {
			SellPlatformUpdateGoodsRequest sellPlatformUpdateGoodsRequest;
			if (auditUpdate) {
				willUpdate.setGoodsInfoImg(wechatImg);
				sellPlatformUpdateGoodsRequest = JSON.parseObject(JSON.toJSONString(this.preAddOrUpdate(Collections.singletonList(willUpdate)).get(0)), SellPlatformUpdateGoodsRequest.class);
				sellPlatformUpdateGoodsRequest.setTitle(goods.getGoodsName());
				sellPlatformUpdateGoodsRequest.setHead_img(Collections.singletonList(wechatImg));
				sellPlatformUpdateGoodsRequest.getSkus().get(0).setThumb_img(wechatImg);
			}else {
				sellPlatformUpdateGoodsRequest = new SellPlatformUpdateGoodsRequest();
				sellPlatformUpdateGoodsRequest.setOut_product_id(willUpdate.getGoodsId());
				SellPlatformGoodsInfoVO sellPlatformGoodsInfoVO = new SellPlatformGoodsInfoVO();
				sellPlatformGoodsInfoVO.setOut_sku_id(willUpdate.getGoodsInfoId());
				sellPlatformGoodsInfoVO.setStock_num(willUpdate.getStock().intValue());
				sellPlatformGoodsInfoVO.setSale_price(willUpdate.getMarketPrice().multiply(new BigDecimal("100")).intValue());
				sellPlatformGoodsInfoVO.setMarket_price(willUpdate.getMarketPrice().multiply(new BigDecimal("100")).intValue());
				sellPlatformUpdateGoodsRequest.setSkus(Collections.singletonList(sellPlatformGoodsInfoVO));
			}
			sellPlatformUpdateGoodsRequest.setOut_product_id(newGoodsInfo.getGoodsId());
			sellPlatformUpdateGoodsRequest.setAuditUpdate(auditUpdate);
			sellPlatformUpdateGoodsRequest.setEditStatus(wechatSku.getEditStatus());
			this.update(sellPlatformUpdateGoodsRequest);
		}
	}


	/**
	 * @description
	 * @author  wur
	 * @date: 2022/5/13 9:54
	 * @param goodsInfoList
	 * @return
	 **/
	public List<SellPlatformAddGoodsRequest> preAddOrUpdate(List<GoodsInfoVO> goodsInfoList) {
		Map<String, List<GoodsInfoSpecDetailRelVO>> relMap = goodsInfoSpecDetailRelRepository.joinName(goodsInfoList.stream().map(v -> v.getGoodsInfoId()).collect(Collectors.toList()))
				.stream().collect(Collectors.groupingBy(v -> v.getGoodsInfoId()));
		List<GoodsCateThirdCateRel> cateRels = goodsCateThirdCateRelService.list(GoodsCateThirdCateRelQueryRequest.builder()
				.thirdPlatformType(ThirdPlatformType.WECHAT_VIDEO)
				.delFlag(DeleteFlag.NO)
				.cateIdList(goodsInfoList.stream().map(GoodsInfoVO::getCateId).distinct().collect(Collectors.toList()))
				.build());
		//如果sku没有图片，使用spu的图片
		List<GoodsInfoVO> noImg = goodsInfoList.stream().filter(v -> StringUtils.isBlank(v.getGoodsInfoImg())).collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(noImg)) {
			List<Goods> goods = goodsService.listByGoodsIds(noImg.stream().map(v -> v.getGoodsId()).distinct().collect(Collectors.toList()));
			for (GoodsInfoVO goodsInfoVO : noImg) {
				String goodsImg = goods.stream().filter(v -> v.getGoodsId().equals(goodsInfoVO.getGoodsId())).findFirst().orElse(new Goods()).getGoodsImg();
				if (StringUtils.isBlank(goodsImg)) {
					throw new SbcRuntimeException(GoodsErrorCodeEnum.K030139, new Object[]{goodsInfoVO.getGoodsInfoNo()});
				}
				goodsInfoVO.setGoodsInfoImg(goodsImg);
			}
		}
		return goodsInfoList.stream().map(goodsInfoVO -> {
			long stock;
			if (Integer.valueOf(0).equals(goodsInfoVO.getGoodsSource())) {
				GoodsInfoSaveVO providerGoodsInfo = goodsInfoService.findByGoodsInfoIdAndDelFlag(goodsInfoVO.getProviderGoodsInfoId());
				if (providerGoodsInfo == null) {
					throw new RuntimeException("供应商商品不存在");
				}
				stock = providerGoodsInfo.getStock();
			} else if (Integer.valueOf(1).equals(goodsInfoVO.getGoodsSource())) {
				stock = goodsInfoVO.getStock();
			} else {
				stock = 999999;
			}
			goodsInfoVO.setMarketPrice(goodsInfoVO.getMarketPrice() == null ? BigDecimal.ZERO : goodsInfoVO.getMarketPrice());
			SellPlatformAddGoodsRequest sellPlatformAddGoodsRequest = new SellPlatformAddGoodsRequest();
			sellPlatformAddGoodsRequest.setOut_product_id(goodsInfoVO.getGoodsId());
			sellPlatformAddGoodsRequest.setTitle(goodsInfoVO.getGoodsInfoName());
			sellPlatformAddGoodsRequest.setPath(goodsinfo_path.replace("{goodsInfoId}", goodsInfoVO.getGoodsInfoId()));
			sellPlatformAddGoodsRequest.setHead_img(Collections.singletonList(goodsInfoVO.getGoodsInfoImg()));
			Optional<GoodsCateThirdCateRel> optional = cateRels.stream().filter(v -> v.getCateId().equals(goodsInfoVO.getCateId())).findFirst();
			if (!optional.isPresent()) {
				throw new SbcRuntimeException(GoodsErrorCodeEnum.K030138);
			}
			Long wechatCateId = optional.get().getThirdCateId();
			sellPlatformAddGoodsRequest.setThird_cat_id(wechatCateId.intValue());
			sellPlatformAddGoodsRequest.setBrand_id(2100000000);
			//填充商品资质
			WechatCateAudit wechatCateAudit = wechatCateAuditService.list(WechatCateAuditQueryRequest.builder().wechatCateId(wechatCateId).build()).get(0);
			String productQualification = wechatCateAudit.getProductQualificationUrls();
			if (StringUtils.isNotBlank(productQualification)) {
				sellPlatformAddGoodsRequest.setQualification_pics(Arrays.asList(productQualification.split(",")));
			}
			SellPlatformGoodsInfoVO sellPlatformGoodsInfoVO = new SellPlatformGoodsInfoVO();
			sellPlatformGoodsInfoVO.setOut_product_id(goodsInfoVO.getGoodsId());
			sellPlatformGoodsInfoVO.setOut_sku_id(goodsInfoVO.getGoodsInfoId());
			sellPlatformGoodsInfoVO.setThumb_img(goodsInfoVO.getGoodsInfoImg());
			sellPlatformGoodsInfoVO.setSale_price(goodsInfoVO.getMarketPrice().multiply(new BigDecimal("100")).intValue());
			sellPlatformGoodsInfoVO.setMarket_price(goodsInfoVO.getMarketPrice().multiply(new BigDecimal("100")).intValue());
			sellPlatformGoodsInfoVO.setStock_num((int) stock);
			List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetailRelVOS = relMap.get(goodsInfoVO.getGoodsInfoId());
			if (CollectionUtils.isNotEmpty(goodsInfoSpecDetailRelVOS)) {
				sellPlatformGoodsInfoVO.setSku_attrs(goodsInfoSpecDetailRelVOS.stream().map(v -> new SellPlatformGoodsInfoAttrVO(v.getSpecName(), v.getDetailName())).collect(Collectors.toList()));
			} else {
				sellPlatformGoodsInfoVO.setSku_attrs(Collections.singletonList(new SellPlatformGoodsInfoAttrVO("-", "-")));
			}
			sellPlatformAddGoodsRequest.setSkus(Collections.singletonList(sellPlatformGoodsInfoVO));
			sellPlatformAddGoodsRequest.setScene_group_list(Collections.singletonList(1));
			return sellPlatformAddGoodsRequest;
		}).collect(Collectors.toList());
	}


	/**
	 * 单个删除微信视频号带货商品
	 * @author 
	 */
	@Transactional
	public void deleteByGoodsId(WechatSkuDelByGoodsIdRequest request) {
		Long storeId = request.getStoreId();
		List<String> existGoodIds = wechatSkuRepository.selectByGoodsIdsAndStoreId(Collections.singletonList(request.getGoodsId()), storeId);
		if (CollectionUtils.isEmpty(existGoodIds)) {
			throw new SbcRuntimeException(GoodsErrorCodeEnum.K030140);
		}
		wechatSkuRepository.delByGoodsId(Collections.singletonList(request.getGoodsId()),request.getOperatorId());
		sellPlatformGoodsProvider.delGoods(new SellPlatformDeleteGoodsRequest(Collections.singletonList(request.getGoodsId())));
	}

	/**
	 * 更新上下架状态
	 * @param goodsIds
	 * @param wechatShelveStatus
	 */
	@Transactional
	public void updateWecahtShelveStatus(List<String> goodsIds, WechatShelveStatus wechatShelveStatus) {
		wechatSkuRepository.updateAddedByGoodsId(goodsIds, wechatShelveStatus);
		if (wechatShelveStatus.equals(WechatShelveStatus.UN_SHELVE)) {
			sellPlatformGoodsProvider.delistingGoods(new SellPlatformGoodsBaseRequest(goodsIds));
		} else {
			sellPlatformGoodsProvider.listingGoods(new SellPlatformGoodsBaseRequest(goodsIds));
		}
	}

	/**
	 * @description    删除微信代销
	 * @author  wur
	 * @date: 2022/5/13 10:54
	 * @param delInfoIds
	 * @param updatePerson
	 * @return
	 **/
	@Transactional
	public void deleteGoods(List<String> delInfoIds, String updatePerson) {
		List<WechatSku> wechatSkuList = this.list(WechatSkuQueryRequest.builder().delFlag(DeleteFlag.NO).goodsInfoIds(delInfoIds).build());
		if (org.apache.commons.collections4.CollectionUtils.isEmpty(wechatSkuList)) {
			return;
		}
		List<String> goodsIds = wechatSkuList.stream().map(v -> v.getGoodsId()).collect(Collectors.toList());
		sellPlatformGoodsProvider.delGoods(new SellPlatformDeleteGoodsRequest(goodsIds));
		wechatSkuRepository.delByGoodsId(goodsIds, updatePerson);
	}

	/**
	 * 单个查询微信视频号带货商品
	 * @author 
	 */
	public WechatSku getById(Long id){
		return wechatSkuRepository.findById(id).orElse(null);
	}
	
	/** 
	 * 分页查询微信视频号带货商品
	 * @author 
	 */
	public MicroServicePage<WechatSkuVO> page(WechatSkuQueryRequest queryReq){
		Page<WechatSku> page = wechatSkuRepository.findAll(
				WechatSkuWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
		Page<WechatSkuVO> newPage = page.map(entity -> wrapperVo(entity));
		List<GoodsInfoVO> goodsInfoVOS = newPage.getContent().stream().map(v->v.getGoodsInfoVO()).collect(Collectors.toList());
		goodsInfoSpecDetailRelService.fillSpecDetail(goodsInfoVOS);
		List<Long> storeIds =goodsInfoVOS.stream().map(v -> v.getStoreId()).distinct().collect(Collectors.toList());
		List<StoreVO> storeVOList = storeQueryProvider.listByIds(new ListStoreByIdsRequest(storeIds)).getContext().getStoreVOList();
		//填充平台类目和微信类目路径
		List<Long> cateIds = goodsInfoVOS.stream().map(v -> v.getCateId()).distinct().collect(Collectors.toList());
		List<GoodsCateShenceBurialSiteVO> goodsCateList = goodsCateService.listGoodsCateShenceBurialSite(cateIds);
		List<GoodsCateThirdCateRel> goodsCateThirdCateRelVOList = goodsCateThirdCateRelService.list(GoodsCateThirdCateRelQueryRequest.builder()
				.delFlag(DeleteFlag.NO)
				.thirdPlatformType(ThirdPlatformType.WECHAT_VIDEO)
				.cateIdList(cateIds)
				.build());
		List<GradeResponse> gradeResponses = wechatCateAuditService.gradeBycateIds(
				new GradeRequest(goodsCateThirdCateRelVOList.stream().map(v -> v.getThirdCateId()).distinct().collect(Collectors.toList()), ThirdPlatformType.WECHAT_VIDEO)
		);
		for (GoodsInfoVO goodsInfoVO : goodsInfoVOS) {
			goodsInfoVO.setStoreName(storeVOList.stream().filter(v->v.getStoreId().equals(goodsInfoVO.getStoreId())).findFirst().get().getStoreName());
			GoodsCateShenceBurialSiteVO goodsCateShenceBurialSiteVO = goodsCateList.stream()
					.filter(v -> v.getThreeLevelGoodsCate().getCateId().equals(goodsInfoVO.getCateId())).findFirst().get();
			goodsInfoVO.setCatePath(
					goodsCateShenceBurialSiteVO.getOneLevelGoodsCate().getCateName()+">"+
							goodsCateShenceBurialSiteVO.getSecondLevelGoodsCate().getCateName()+">"+
							goodsCateShenceBurialSiteVO.getThreeLevelGoodsCate().getCateName());
			Optional<GoodsCateThirdCateRel> optional = goodsCateThirdCateRelVOList.stream().filter(v -> v.getCateId().equals(goodsInfoVO.getCateId()))
					.findFirst();
			if (optional.isPresent()) {
				GradeResponse gradeResponse = gradeResponses.stream()
						.filter(s -> s.getThirdGrade().getCateId().equals(optional.get().getThirdCateId()
						)).findFirst().get();
				goodsInfoVO.setWechatCatePath(gradeResponse.getOneGrade().getCateName()+">"+gradeResponse.getSecondGrade().getCateName()+">"+gradeResponse.getThirdGrade().getCateName());
			}
		}
		//处理供应商库存
		updateGoodsStockForPrivate(goodsInfoVOS);
		MicroServicePage<WechatSkuVO> microPage = new MicroServicePage<>(newPage, queryReq.getPageable());
		return microPage;
	}

	/**
	 * @description    查询商品关联的 供应商库存
	 * @author  wur
	 * @date: 2022/5/17 14:30
	 * @param goodsInfoVOS
	 * @return
	 **/
	public void updateGoodsStockForPrivate(List<GoodsInfoVO> goodsInfoVOS) {
		List<String> privateGoodsInfoIdList = new ArrayList<>();
		goodsInfoVOS.forEach(goods->{
			if(StringUtils.isNotBlank(goods.getProviderGoodsInfoId())){
				privateGoodsInfoIdList.add(goods.getProviderGoodsInfoId());
			}
		});
		if(CollectionUtils.isEmpty(privateGoodsInfoIdList)){
			return;
		}
		List<GoodsInfo>  goodsInfoList = goodsInfoService.findByIds(privateGoodsInfoIdList);

		Map<String,Long> goodsStockMap = new HashMap<>();
		goodsInfoList.forEach(goodsInfo->{
			String goodsInfoId = goodsInfo.getGoodsInfoId();
			if(StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId())){
				goodsInfoId = goodsInfo.getProviderGoodsInfoId();
			}
			Long stock = goodsInfo.getStock();
			goodsStockMap.put(goodsInfoId,stock);
		});
		goodsInfoVOS.forEach(sku->{
			if(StringUtils.isNotBlank(sku.getProviderGoodsInfoId()) && goodsStockMap.containsKey(sku.getProviderGoodsInfoId())){
				sku.setStock(goodsStockMap.get(sku.getProviderGoodsInfoId()));
			}
		});
	}
	
	/** 
	 * 列表查询微信视频号带货商品
	 * @author 
	 */
	public List<WechatSku> list(WechatSkuQueryRequest queryReq){
		return wechatSkuRepository.findAll(
				WechatSkuWhereCriteriaBuilder.build(queryReq),
				queryReq.getSort());
	}

	/**
	 * 将实体包装成VO
	 * @author 
	 */
	public WechatSkuVO wrapperVo(WechatSku wechatSku) {
		if (wechatSku != null){
			WechatSkuVO wechatSkuVO=new WechatSkuVO();
			KsBeanUtil.copyPropertiesThird(wechatSku,wechatSkuVO);
			wechatSkuVO.setGoodsInfoVO(JSON.parseObject(JSON.toJSONString(wechatSku.getGoodsInfo()),GoodsInfoVO.class));
			return wechatSkuVO;
		}
		return null;
	}

	/**
	 * 处理微信审核回调
	 * @param request
	 */
	@Transactional
	public void audit(AuditRequest request) {
		List<WechatSku> wechatSkus = list(WechatSkuQueryRequest.builder().delFlag(DeleteFlag.NO).goodsId(request.getGoodsId()).build());
		if (CollectionUtils.isNotEmpty(wechatSkus)) {
			WechatSku wechatSku = wechatSkus.get(0);
			GoodsInfo goodsInfo = goodsInfoRepository.findByGoodsInfoIdAndDelFlag(wechatSku.getGoodsInfoId(), DeleteFlag.NO);
			//微信审核期间，商家下架商品，做下架处理
			if (Integer.valueOf(0).equals(goodsInfo.getAddedFlag()) &&WechatShelveStatus.SHELVE.equals(request.getWechatShelveStatus())) {
				request.setWechatShelveStatus(WechatShelveStatus.UN_SHELVE);
				sellPlatformGoodsProvider.delistingGoods(new SellPlatformGoodsBaseRequest(Collections.singletonList(wechatSku.getGoodsId())));
			}
			wechatSkuRepository.audit(request.getGoodsId(),request.getEditStatus(),request.getRejectReason(),request.getWechatShelveStatus());
		}
	}

	/**
	 * 处理微信上下架回调
	 * @param request
	 */
	@Transactional
	public void dealDown(DownRequest request) {
		if (request.getWechatShelveStatus().equals(WechatShelveStatus.VIOLATION_UN_SHELVE)) {
			wechatSkuRepository.down(request.getGoodsId(),request.getDownReason());
		}else {
			wechatSkuRepository.up(request.getGoodsId());
	}
}

	public List<String> listGoodsId(WechatSkuQueryRequest wechatSkuListReq) {
		return wechatSkuRepository.selectGoodsId(wechatSkuListReq.getGoodsIds());
	}

	/**
	 * 更新上下架状态
	 * @param request
	 */
	@Transactional
	public void updateShelveByStoreId(UpdateShelve request) {
		wechatSkuRepository.updateShelve(request.getStoreId(),request.getWechatShelveStatus().getValue());
		List<String> goodsIds = wechatSkuRepository.selectByStoreIdAndWechatShelveStatus(request.getStoreId(), request.getWechatShelveStatus());
		if (CollectionUtils.isNotEmpty(goodsIds)) {
			if (WechatShelveStatus.UN_SHELVE.equals(request.getWechatShelveStatus())) {
				sellPlatformGoodsProvider.delistingGoods(new SellPlatformGoodsBaseRequest(goodsIds));
			}else {
				sellPlatformGoodsProvider.listingGoods(new SellPlatformGoodsBaseRequest(goodsIds));
			}
		}
	}

	/**
	 * 同步微信商品库存
	 */
	public void syncStock() {
		List<GoodsInfoVO> goodsInfoVOS = wechatSkuRepository.selectStock();
		if (CollectionUtils.isNotEmpty(goodsInfoVOS)) {
			List<SellPlatformSyncStockRequest> wxSyncStockRequests = goodsInfoVOS.stream().map(v->{
				SellPlatformSyncStockRequest wxSyncStockRequest = new SellPlatformSyncStockRequest();
				wxSyncStockRequest.setOut_product_id(v.getGoodsId());
				wxSyncStockRequest.setOut_sku_id(v.getGoodsInfoId());
				wxSyncStockRequest.setStock_num(v.getStock().intValue());
				return wxSyncStockRequest;
			}).collect(Collectors.toList());
            sellPlatformGoodsProvider.syncStock(wxSyncStockRequests);
		}
	}

}
