package com.wanmi.sbc.goods.flashsalegoods.service;

import com.google.common.collect.Maps;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.request.flashsalegoods.*;
import com.wanmi.sbc.goods.api.response.flashsalegoods.IsFlashSaleResponse;
import com.wanmi.sbc.goods.bean.enums.FlashSaleGoodsStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.flashpromotionactivity.model.root.FlashPromotionActivity;
import com.wanmi.sbc.goods.flashpromotionactivity.repository.FlashPromotionActivityRepository;
import com.wanmi.sbc.goods.flashsalecate.model.root.FlashSaleCate;
import com.wanmi.sbc.goods.flashsalecate.repository.FlashSaleCateRepository;
import com.wanmi.sbc.goods.flashsalegoods.model.root.FlashSaleGoods;
import com.wanmi.sbc.goods.flashsalegoods.repository.FlashSaleGoodsRepository;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.GoodsQueryRequest;
import com.wanmi.sbc.goods.info.service.GoodsService;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.storecate.repository.StoreCateGoodsRelaRepository;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.flashsalesetting.FlashSaleSettingQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.flashsalesetting.FlashSaleSettingListRequest;
import com.wanmi.sbc.setting.api.response.flashsalesetting.FlashSaleSettingListResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>抢购商品表业务逻辑</p>
 *
 * @author bob
 * @date 2019-06-11 14:54:31
 */
@Service("FlashSaleGoodsService")
public class FlashSaleGoodsService {

    @Autowired
    private FlashSaleGoodsRepository flashSaleGoodsRepository;

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;

    @Autowired
    private FlashSaleGoodsService flashSaleGoodsService;

    @Autowired
    private FlashSaleCateRepository flashSaleCateRepository;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

    @Autowired
    private LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Resource
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private FlashPromotionActivityRepository flashPromotionActivityRepository;

    @Autowired
    private FlashSaleSettingQueryProvider flashSaleSettingQueryProvider;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired private GoodsService goodsService;

    @Autowired private StoreCateGoodsRelaRepository storeCateGoodsRelaRepository;

    @Autowired public SystemConfigQueryProvider systemConfigQueryProvider;

    /**
     * 新增抢购商品表
     *
     * @author bob
     */
    @Transactional
    public List<FlashSaleGoods> batchAdd(List<FlashSaleGoods> entity) throws SbcRuntimeException {
        List<String> skuIds = entity.stream().map(FlashSaleGoods::getGoodsInfoId).collect(Collectors.toList());
        String date = entity.get(0).getActivityDate();
        String time = entity.get(0).getActivityTime();
        LocalDateTime startTime =
                DateUtil.parse(date.concat(" ").concat(time), DateUtil.FMT_TIME_2);
        LocalDateTime endTime = startTime.plusHours(Constants.TWO*Constants.FLASH_SALE_LAST_HOUR);
        startTime = startTime.minusHours(Constants.FLASH_SALE_LAST_HOUR);
        validateFlashSaleGoods(skuIds, startTime, endTime, null);
        //校验分类是否已删除
        List<Long> cateIds = entity.stream().map(FlashSaleGoods::getCateId).collect(Collectors.toList());
        List<FlashSaleCate> flashSaleCates =
                flashSaleCateRepository.findAllById(cateIds).stream().filter(r -> r.getDelFlag().equals(DeleteFlag.YES))
                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(flashSaleCates)) {
            List<String> cateNames =
                    flashSaleCates.stream().map(FlashSaleCate::getCateName).collect(Collectors.toList());
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030108, new Object[]{cateNames.toString()});
        }

        return flashSaleGoodsRepository.saveAll(entity);
    }

    /**
     * 修改抢购商品表
     *
     * @author bob
     */
    @Transactional
    public FlashSaleGoods modify(FlashSaleGoods entity) {

        FlashSaleGoods flashSaleGoods = flashSaleGoodsRepository.findById(entity.getId())
                .orElseThrow(() -> new SbcRuntimeException(GoodsErrorCodeEnum.K030110));

        //判断分类是否存在
        if (Objects.nonNull(entity.getCateId())){
            FlashSaleCate flashSaleCate = flashSaleCateRepository.findById(entity.getCateId()).orElse(null);
            if (Objects.isNull(flashSaleGoods) || DeleteFlag.YES.equals(flashSaleCate.getDelFlag())){
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030104);
            }
        }

        if (LocalDateTime.now().isAfter(flashSaleGoods.getActivityFullTime())) {
            //秒杀活动已开始
            flashSaleGoods.setCateId(Objects.nonNull(entity.getCateId())?entity.getCateId():null);
            flashSaleGoods.setStock(flashSaleGoods.getStock()+entity.getStock());
        }else {
            flashSaleGoods.setCateId(Objects.nonNull(entity.getCateId())?entity.getCateId():null);
            flashSaleGoods.setStock(flashSaleGoods.getStock()+entity.getStock());
            flashSaleGoods.setMinNum(entity.getMinNum());
            flashSaleGoods.setMaxNum(entity.getMaxNum());
            flashSaleGoods.setPostage(entity.getPostage());
            flashSaleGoods.setPrice(entity.getPrice());
        }

        flashSaleGoodsRepository.save(flashSaleGoods);
        return flashSaleGoods;
    }

    /**
     * 单个删除抢购商品表
     *
     * @author bob
     */
    @Transactional
    public void deleteById(Long id) {
        FlashSaleGoods flashSaleGoods = getById(id);
        LocalDateTime activityFullTime = flashSaleGoods.getActivityFullTime();
        // 活动时间开始前一个小时后无法删除
        if (!activityFullTime.minusHours(1).isAfter(LocalDateTime.now())) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030107);
        }
        flashSaleGoodsRepository.modifyDelFlagById(id);
        deleteFlashCache(flashSaleGoods);
    }

    /**
     * 批量删除抢购商品表
     *
     * @author bob
     */
    @Transactional
    public void deleteByIdList(List<Long> ids) {
        flashSaleGoodsRepository.deleteByIdList(ids);
    }

    /**
     * 批量删除抢购商品表
     *
     * @author bob
     */
    @Transactional
    public void deleteByTimeList(List<String> activityTimeList) {
        flashSaleGoodsRepository.deleteByTimeList(activityTimeList);
    }

    /**
     * 单个查询抢购商品表
     *
     * @author bob
     */
    public FlashSaleGoods getById(Long id) {
        return flashSaleGoodsRepository.findById(id).orElse(null);
    }

    /**
     * 单个查询抢购商品表
     *
     * @author xufeng
     */
    public List<FlashSaleGoods> getByActivityId(String activityId) {
        return flashSaleGoodsRepository.findByActivityId(activityId);
    }

    /**
     * 分页查询抢购商品表
     *
     * @author bob
     */
    public Page<FlashSaleGoods> page(FlashSaleGoodsQueryRequest queryReq) {
        if (Objects.isNull(queryReq.getType())){
            queryReq.setType(Constants.ZERO);
        }

        // 判断BOSS是否配置了无货商品不展示
        if (auditQueryProvider.isGoodsOutOfStockShow().getContext().isOutOfStockShow()) {
            queryReq.setStockFlag(Constants.yes);
        }

        if(Objects.nonNull(queryReq.getActivityFullTimeEnd())){
            //秒杀
            if(queryReq.getType().equals(Constants.ZERO)){
                Long preTime = this.getPreTime();
                if(preTime > 0L){
                    queryReq.setActivityFullTimeEnd(LocalDateTime.now().plusHours(preTime));
                }else{
                    queryReq.setActivityFullTimeEnd(LocalDateTime.now());
                }
            }

        }

        Page<FlashSaleGoods> page = flashSaleGoodsRepository.findAll(
                FlashSaleGoodsWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
        //如果是linkedmall商品，实时查库存
        List<GoodsInfo> goodsInfos = page.getContent().stream().map(FlashSaleGoods::getGoodsInfo).collect(Collectors.toList());
        List<Long> itemIds = goodsInfos.stream()
                .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType()))
                .map(v -> Long.valueOf(v.getThirdPlatformSpuId()))
                .distinct()
                .collect(Collectors.toList());
        if (itemIds.size() > 0) {
            List<LinkedMallStockVO> stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
            if (stocks != null) {
                for (GoodsInfo goodsInfo : goodsInfos) {
                    if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
                        Optional<LinkedMallStockVO> optional = stocks.stream()
                                .filter(v -> String.valueOf(v.getItemId()).equals(goodsInfo.getThirdPlatformSpuId()))
                                .findFirst();
                        if (optional.isPresent()) {
                            Optional<LinkedMallStockVO.SkuStock> skuStock = optional.get().getSkuList().stream()
                                    .filter(v -> String.valueOf(v.getSkuId()).equals(goodsInfo.getThirdPlatformSkuId())).findFirst();
                            if (skuStock.isPresent()) {
                                Long quantity = skuStock.get().getStock();
                                goodsInfo.setStock(quantity);
                            }
                        }

                    }
                }
            }
        }
        return page;
    }

    /**
     * 获取秒杀预热时间
     * @return
     */
    private Long getPreTime () {
        //查询秒杀预热时间
        String redisTime = redisService.getString(CacheKeyConstant.FLASH_PRE_TIME);
        Long preTime = 0L;

        if(Objects.nonNull(redisTime)){
            preTime = Long.valueOf(redisTime);
        }else{
            FlashSaleSettingListRequest flashSaleSettingListRequest = FlashSaleSettingListRequest.builder()
                    .delFlag(DeleteFlag.NO)
                    .build();
            FlashSaleSettingListResponse flashSaleSettingListResponse
                    = flashSaleSettingQueryProvider.list(flashSaleSettingListRequest).getContext();
            if(Objects.nonNull(flashSaleSettingListResponse)
                    && org.apache.commons.collections.CollectionUtils.isNotEmpty(flashSaleSettingListResponse.getFlashSaleSettingVOList())){
                Integer pre = flashSaleSettingListResponse.getFlashSaleSettingVOList().get(0).getPreTime();

                if(Objects.nonNull(pre)){
                    preTime = Long.valueOf(pre);
                }

                redisService.setString(CacheKeyConstant.FLASH_PRE_TIME, String.valueOf(preTime));
                redisService.expireByMinutes(CacheKeyConstant.FLASH_PRE_TIME,24*60L);
            }
        }

        return preTime;
    }

    /**
     * 列表查询抢购商品表
     *
     * @author bob
     */
    public List<FlashSaleGoods> list(FlashSaleGoodsQueryRequest queryReq) {
        if (Objects.isNull(queryReq.getType()) && !Objects.equals(Boolean.TRUE,queryReq.getFindAll())){
            queryReq.setType(Constants.ZERO);
        }
        Sort sort = queryReq.getSort();
        List<FlashSaleGoods> flashSaleGoodsList = new ArrayList<>();
        if (Objects.nonNull(sort)) {
            flashSaleGoodsList.addAll(flashSaleGoodsRepository.findAll(FlashSaleGoodsWhereCriteriaBuilder.build(queryReq), sort));
        } else {
            flashSaleGoodsList.addAll(flashSaleGoodsRepository.findAll(FlashSaleGoodsWhereCriteriaBuilder.build(queryReq)));
        }

        if (Objects.equals(Boolean.TRUE,queryReq.getFindAll())){
            queryReq.setType(Constants.ONE);
            if (Objects.nonNull(sort)) {
                flashSaleGoodsList.addAll(flashSaleGoodsRepository.findAll(FlashSaleGoodsWhereCriteriaBuilder.build(queryReq), sort));
            } else {
                flashSaleGoodsList.addAll(flashSaleGoodsRepository.findAll(FlashSaleGoodsWhereCriteriaBuilder.build(queryReq)));
            }
        }

        return flashSaleGoodsList;
    }

    /**
     * 秒杀设置参与商家数量
     */
    public Long storeCount() {
        return flashSaleGoodsRepository.storeCount();
    }


    /**
     * 将实体包装成VO
     *
     * @author bob
     */
    public FlashSaleGoodsVO wrapperVo(FlashSaleGoods flashSaleGoods) {
        if (Objects.isNull(flashSaleGoods)) {
            return null;
        }

        FlashSaleGoodsVO flashSaleGoodsVO = new FlashSaleGoodsVO();
        KsBeanUtil.copyPropertiesThird(flashSaleGoods, flashSaleGoodsVO);
        FlashSaleCate flashSaleCate = flashSaleGoods.getFlashSaleCate();
        if (Objects.nonNull(flashSaleCate)) {
            FlashSaleCateVO flashSaleCateVO = new FlashSaleCateVO();
            KsBeanUtil.copyPropertiesThird(flashSaleCate, flashSaleCateVO);
            flashSaleGoodsVO.setFlashSaleCateVO(flashSaleCateVO);
        }
        Goods goods = flashSaleGoods.getGoods();
        if (Objects.nonNull(goods)) {
            GoodsVO goodsVO = new GoodsVO();
            KsBeanUtil.copyPropertiesThird(goods, goodsVO);
            flashSaleGoodsVO.setGoods(goodsVO);
        }
        GoodsInfo goodsInfo = flashSaleGoods.getGoodsInfo();
        if (Objects.nonNull(goodsInfo)) {
            GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
            KsBeanUtil.copyPropertiesThird(goodsInfo, goodsInfoVO);
            StoreVO storeVO = storeQueryProvider.getById(StoreByIdRequest.builder()
                    .storeId(goodsInfo.getStoreId())
                    .build()).getContext().getStoreVO();
            // 店铺名称
            goodsInfoVO.setStoreName(storeVO.getStoreName());
            // 最大可兑换库存
            flashSaleGoodsVO.setMaxStock(goodsInfo.getStock() + flashSaleGoods.getStock());
            flashSaleGoodsVO.setGoodsInfo(goodsInfoVO);
        }
        Integer type = flashSaleGoods.getType();
        // 商品状态
        FlashSaleGoodsStatus flashSaleGoodsStatus;
        if (Objects.isNull(type)||Constants.ZERO==type){
            flashSaleGoodsStatus = getFlashSaleGoodsStatus(flashSaleGoods);
            // 是否可修改(活动结束后)
            if (flashSaleGoods.getActivityFullTime().plusHours(Constants.TWO).isAfter(LocalDateTime.now())) {
                flashSaleGoodsVO.setModifyFlag(BoolFlag.YES);
            } else {
                flashSaleGoodsVO.setModifyFlag(BoolFlag.NO);
            }
        } else {
            flashSaleGoodsStatus = getFlashPromotionGoodsStatus(flashSaleGoods);
        }
        flashSaleGoodsVO.setFlashSaleGoodsStatus(flashSaleGoodsStatus);
        String goodsInfoId = flashSaleGoods.getGoodsInfoId();
        List<GoodsInfoSpecDetailRel> GoodsInfoSpecDetailRels = goodsInfoSpecDetailRelRepository.findByGoodsInfoId(goodsInfoId);
        flashSaleGoodsVO.setSpecText(StringUtils.join(GoodsInfoSpecDetailRels.stream()
                .map(GoodsInfoSpecDetailRel::getDetailName)
                .collect(Collectors.toList()), " "));
        return flashSaleGoodsVO;
    }

    /**
     * 获取秒杀商品活动状态
     *
     * @param flashSaleGoods
     * @return
     */
    public FlashSaleGoodsStatus getFlashSaleGoodsStatus(FlashSaleGoods flashSaleGoods) {
        if (LocalDateTime.now().isBefore(flashSaleGoods.getActivityFullTime())) {
            return FlashSaleGoodsStatus.NOT_START;
        } else if (LocalDateTime.now().isAfter(flashSaleGoods.getActivityFullTime().plusHours(Constants.TWO))) {
            return FlashSaleGoodsStatus.ENDED;
        } else {
            return FlashSaleGoodsStatus.STARTED;
        }
    }

    /**
     * 获取秒杀商品活动状态
     *
     * @param flashPromotionGoods
     * @return
     */
    public FlashSaleGoodsStatus getFlashPromotionGoodsStatus(FlashSaleGoods flashPromotionGoods) {
        // 暂停中
        if (Objects.nonNull(flashPromotionGoods.getStatus()) && Constants.ONE==flashPromotionGoods.getStatus()){
            // 已结束的优先返回
            if (LocalDateTime.now().isAfter(flashPromotionGoods.getEndTime())) {
                return FlashSaleGoodsStatus.ENDED;
            }
            return FlashSaleGoodsStatus.PAUSED;
        }
        if (LocalDateTime.now().isBefore(flashPromotionGoods.getStartTime())) {
            return FlashSaleGoodsStatus.NOT_START;
        } else if (LocalDateTime.now().isAfter(flashPromotionGoods.getEndTime())) {
            return FlashSaleGoodsStatus.ENDED;
        } else {
            return FlashSaleGoodsStatus.STARTED;
        }
    }

    /**
     * 商品是否在指定时间内
     *
     * @author bob
     */
    public List<FlashSaleGoods> getByGoodsIdAndFullTime(IsInProgressReq isInProgressReq) {
        return flashSaleGoodsRepository.queryByGoodsIdAndActivityFullTime(isInProgressReq.getGoodsId()
                , isInProgressReq.getBegin(), isInProgressReq.getEnd());
    }

    /**
     * 根据SKUId判断商品是否在指定时间内
     *
     * @author bob
     */
    public List<FlashSaleGoods> getByGoodsInfoIdAndFullTime(IsInProgressByGoodsInfoIdReq isInProgressByGoodsInfoIdReq) {
        return flashSaleGoodsRepository.queryByGoodsInfoIdAndActivityFullTime(isInProgressByGoodsInfoIdReq.getGoodsInfoId()
                , isInProgressByGoodsInfoIdReq.getBegin(), isInProgressByGoodsInfoIdReq.getEnd());
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 扣减库存
     * @Date 11:12 2019/6/21
     * @Param [request]
     **/
    @Transactional
    public void batchMinusStock(FlashSaleGoodsBatchMinusStockRequest request) {
        flashSaleGoodsRepository.subStockById(request.getStock(), request.getId());
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 增加秒杀商品库存
     * @Date 16:03 2019/7/1
     * @Param [request]
     **/
    @Transactional
    public void addStockById(FlashSaleGoodsBatchAddStockRequest request) {
        flashSaleGoodsRepository.addStockById(request.getStock(), request.getId());
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 增加销量
     * @Date 10:15 2019/6/22
     * @Param [request]
     **/
    @Transactional
    public void batchPlusSalesVolumeById(FlashSaleGoodsBatchPlusSalesVolumeRequest request) {
        flashSaleGoodsRepository.plusSalesVolumeById(request.getSalesVolume().longValue(), request.getId());
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 减少销量
     * @Date 14:41 2019/8/5
     * @Param [request]
     **/
    @Transactional
    public void subSalesVolumeById(FlashSaleGoodsBatchStockAndSalesVolumeRequest request) {
        flashSaleGoodsRepository.subSalesVolumeById(request.getNum().longValue(), request.getId());
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 扣减库存增加销量
     * @Date 10:15 2019/6/22
     * @Param [request]
     **/
    @Transactional
    public void batchStockAndSalesVolume(FlashSaleGoodsBatchStockAndSalesVolumeRequest request) {
        int updateRowNum = flashSaleGoodsRepository.subStockById(request.getNum(), request.getId());
        if (updateRowNum <= 0) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030061);
        }
        flashSaleGoodsRepository.plusSalesVolumeById(request.getNum().longValue(), request.getId());
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 增加库存扣减销量
     * @Date 10:15 2019/6/22
     * @Param [request]
     **/
    @Transactional
    public void subStockAndSalesVolume(FlashSaleGoodsBatchStockAndSalesVolumeRequest request) {
        flashSaleGoodsRepository.addStockById(request.getNum(), request.getId());
        flashSaleGoodsRepository.subSalesVolumeById(request.getNum().longValue(), request.getId());
    }

    /**
     * 未结束活动关联的商品(只查一条)
     *
     * @author bob
     */
    public IsFlashSaleResponse getByActivityTime(IsFlashSaleRequest isFlashSaleRequest) {
        Object byActivityTime = flashSaleGoodsRepository.queryByActivityTime(isFlashSaleRequest.getActivityTime());
        return new IsFlashSaleResponse().convertFromNativeSQLResult(byActivityTime);
    }

    public List<FlashSaleGoodsSimpleVO> querySimpleByIds(List<Long> ids){
        List<Map<String,Object>> result = this.flashSaleGoodsRepository.querySimpleByIds(ids);
        if(CollectionUtils.isEmpty(result)){
            return null;
        }
        List<FlashSaleGoodsSimpleVO> response = FlashSaleGoodsSimpleVO.toBean(result);
        return response;
    }


    /**
     * 单个查询抢购商品表
     *
     * @author bob
     */
    public FlashSaleGoods getByIdNotDel(Long id) {
        return flashSaleGoodsRepository.findByIdAndDelFlag(id, DeleteFlag.NO);
    }

    /**
     * @description 关闭活动
     * @author  xuyunpeng
     * @date 2021/6/25 10:02 上午
     * @param id 商品id
     * @return
     */
    @Transactional
    public void close(Long id) {
        FlashSaleGoods flashSaleGoods = this.getById(id);
        if (Objects.isNull(flashSaleGoods)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030110);
        }
        flashSaleGoodsRepository.modifyDelFlagById(id);
        deleteFlashCache(flashSaleGoods);
    }

    private void deleteFlashCache(FlashSaleGoods flashSaleGoods) {
        // 删除redis key
        String goodsInfoId = flashSaleGoods.getGoodsInfoId();
        String flashSaleGoodsInfoKey = RedisKeyConstant.FLASH_SALE_GOODS_INFO_KEY + goodsInfoId;
        String flashSaleStockKey = RedisKeyConstant.FLASH_SALE_GOODS_INFO_STOCK_KEY + goodsInfoId;
        redisService.delete(flashSaleGoodsInfoKey);
        redisService.delete(flashSaleStockKey);
    }

    /**
     * 同步到缓存
     */
    public Page<FlashSaleGoodsVO> sycCache(FlashSaleGoodsQueryRequest request){

        Page<FlashSaleGoods> page = flashSaleGoodsRepository.findAll(
                FlashSaleGoodsWhereCriteriaBuilder.build(request),
                request.getPageRequest());

        return page.map(entity -> flashSaleGoodsService.wrapperVo(entity));
    }

    /**
     * 根据SKUId判断商品是否在指定时间内
     *
     * @author bob
     */
    public List<FlashSaleGoods> getFlashSaleGoods(IsInProgressRequest request) {
        return flashSaleGoodsRepository.queryByGoodsInfoIdInAndActivityFullTime(request.getGoodsInfoIdList()
                , request.getBegin(), request.getEnd());
    }

    /**
     * 删除限时抢购活动
     *
     * @author xufeng
     */
    @Transactional
    public void deleteByActivityId(String activityId) {
        flashPromotionActivityRepository.deleteByActivityId(activityId);
        flashSaleGoodsRepository.deleteByActivityId(activityId);
    }

    /**
     * 新增抢购商品表
     *
     * @author xufeng
     */
    @Transactional
    public List<FlashSaleGoods> batchAddPromotion(FlashPromotionActivity flashPromotionActivity,
                                                  List<FlashSaleGoods> entity) throws SbcRuntimeException {
        List<String> skuIds = entity.stream().map(FlashSaleGoods::getGoodsInfoId).collect(Collectors.toList());
        Map<String, Integer> saleTypeMap = this.getSaleTypeMap(skuIds);
        //判断是否是批发商品
        boolean anyMatch = entity.stream().anyMatch(flashSale -> {
            String goodsInfoId = flashSale.getGoodsInfoId();
            Integer saleType = saleTypeMap.get(goodsInfoId);
            return Objects.equals(saleType, NumberUtils.INTEGER_ZERO);
        });
        //存在批发商品或者商品不存在则抛异常
        if(anyMatch){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030141);
        }

        LocalDateTime startTime = flashPromotionActivity.getStartTime();
        LocalDateTime endTime = flashPromotionActivity.getEndTime();
        validateFlashSaleGoods(skuIds, startTime,endTime,null);
        // 新增限时购活动信息
        FlashPromotionActivity f = flashPromotionActivityRepository.save(flashPromotionActivity);
        entity.forEach(e -> e.setActivityId(f.getActivityId()));
        return flashSaleGoodsRepository.saveAll(entity);
    }

    /**
     * 校验是否存在进行中秒杀活动
     *
     * @author xufeng
     */
    private void validateFlashSaleGoods(List<String> skuIds, LocalDateTime startTime, LocalDateTime endTime, String activityId) {
        // 全局互斥开启时，跳过原先冲突验证
        if (mutexFlag()) {
            return;
        }
        List<String> goodsInfoIds = new ArrayList<>();
        List<FlashSaleGoods> flashSaleGoods = flashSaleGoodsRepository.queryBySkuIds(skuIds);

        //排除活动本身
        if (StringUtils.isNotBlank(activityId)){
            flashSaleGoods = flashSaleGoods
                    .stream()
                    .filter(v->!Objects.equals(v.getActivityId(),activityId))
                    .collect(Collectors.toList());
        }

        for (FlashSaleGoods flashSaleGood : flashSaleGoods) {
          if (Constants.ONE == flashSaleGood.getType()){
              //判断是否有秒杀商品有时间交集
              if (DateUtil.compareTimeIntersection(startTime,endTime,flashSaleGood.getStartTime(),flashSaleGood.getEndTime())){
                  goodsInfoIds.add(flashSaleGood.getGoodsInfoId());
              }
          }else {
              //判断是否有限时商品有时间交集
              if (DateUtil.compareTimeIntersection(startTime,endTime,flashSaleGood.getActivityFullTime(),
                      flashSaleGood.getActivityFullTime().plusHours(Constants.FLASH_SALE_LAST_HOUR))){
                  goodsInfoIds.add(flashSaleGood.getGoodsInfoId());
              }
          }
        }
        if (CollectionUtils.isNotEmpty(goodsInfoIds)){
            List<String> goodsInfoNo =
                    goodsInfoRepository.findByGoodsInfoIds(goodsInfoIds).stream().map(GoodsInfo::getGoodsInfoNo)
                            .collect(Collectors.toList());
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030111, new Object[]{goodsInfoNo.toString()});
        }
    }

    /**
     * 新增抢购商品表
     *
     * @author xufeng
     */
    @Transactional
    public List<FlashSaleGoods> batchModifyPromotion(FlashPromotionActivity flashPromotionActivity,
                                                  List<FlashSaleGoods> entity) throws SbcRuntimeException {
        List<String> skuIds = entity.stream().map(FlashSaleGoods::getGoodsInfoId).collect(Collectors.toList());
        LocalDateTime startTime = flashPromotionActivity.getStartTime();
        LocalDateTime endTime = flashPromotionActivity.getEndTime();
        validateFlashSaleGoods(skuIds, startTime, endTime, flashPromotionActivity.getActivityId());
        // 获取活动状态，如果暂停，则修改数据
        Integer status = flashPromotionActivity.getStatus();
        if (Objects.equals(status,NumberUtils.INTEGER_ONE)) {
            //获取商品
            List<FlashSaleGoods> flashSaleGoodsList = flashSaleGoodsRepository.findByActivityId(flashPromotionActivity.getActivityId());
            //转换成map
            Map<String, FlashSaleGoods> flashSaleGoodsMap = flashSaleGoodsList.stream()
                    .collect(Collectors.toMap(FlashSaleGoods::getGoodsInfoId, Function.identity()));
            //重新设置id
            entity = entity.stream().peek(flashSaleGoods -> {
                String goodsInfoId = flashSaleGoods.getGoodsInfoId();
                FlashSaleGoods oldFlashSaleGoods = flashSaleGoodsMap.get(goodsInfoId);
                flashSaleGoods.setId(oldFlashSaleGoods.getId());
                //保存原先的抢购销量
                flashSaleGoods.setSalesVolume(oldFlashSaleGoods.getSalesVolume());
            }).collect(Collectors.toList());
        } else {
            // 老数据删除
            flashSaleGoodsRepository.deleteByActivityId(flashPromotionActivity.getActivityId());
        }
        // 修改限时购活动信息
        flashPromotionActivityRepository.save(flashPromotionActivity);

        return flashSaleGoodsRepository.saveAll(entity);
    }


    /**
     * 根据活动id查询商品数量
     *
     * @author xufeng
     */
    public int findCountByActivityId(String activityId) {
        return flashSaleGoodsRepository.findCountByActivityId(activityId);
    }

    /**
     * 根据SKUId判断商品是否在指定时间内
     *
     * @author xufeng
     */
    public List<FlashSaleGoods> queryInProgressByGoodsId(IsInProgressReq isInProgressReq) {
        return flashSaleGoodsRepository.queryInProgressByGoodsId(isInProgressReq.getGoodsId());
    }

    /**
     * 秒杀、限时购互斥验证
     * @param request 入参
     * @return 验证结果
     */
    public void validate(FlashSaleGoodsValidateRequest request) {
        String crossStartTime = DateUtil.format(request.getCrossBeginTime(), DateUtil.FMT_TIME_1);
        String crossEndTime = DateUtil.format(request.getCrossEndTime(), DateUtil.FMT_TIME_1);
        Map<String, Byte> spuType = Maps.newHashMap();

        List<String> skuIds = new ArrayList<>();
        Map<String, Byte> skuType = Maps.newHashMap();

        for (int pageNo = 0; ; pageNo++) {
            PageRequest pageRequest =
                    PageRequest.of(pageNo, 150);
            Page<Object[]> salePage = flashSaleGoodsRepository.pageByCrossTime(crossStartTime, crossEndTime,
                    request.getNotId(), request.getStoreId(), pageRequest);
            if (salePage.getTotalElements() == 0) {
                return;
            }
            if (CollectionUtils.isNotEmpty(salePage.getContent())) {
                if (Boolean.TRUE.equals(request.getAllFlag())) {
                    this.throwException(StringUtil.cast(salePage.getContent().get(0), 3, Byte.class));
                }
                salePage.getContent().forEach(s -> {
                    String goodsId = StringUtil.cast(s, 1, String.class);
                    String skuId = StringUtil.cast(s, 2, String.class);
                    Byte type = StringUtil.cast(s, 3, Byte.class);
                    spuType.put(goodsId,type);
                    if (CollectionUtils.isNotEmpty(request.getSkuIds())) {
                        skuIds.add(skuId);
                        skuType.put(skuId, type);
                    }
                });

                //验证商品相关品牌是否存在
                if (CollectionUtils.isNotEmpty(request.getBrandIds())) {
                    this.checkGoodsAndBrand(new ArrayList<>(spuType.keySet()), request.getBrandIds()).stream().findFirst()
                            .ifPresent(c -> this.throwException(spuType.get(c)));
                } else if (CollectionUtils.isNotEmpty(request.getStoreCateIds())) {
                    //验证商品相关店铺分类是否存在
                    this.checkGoodsAndStoreCate(new ArrayList<>(spuType.keySet()), request.getStoreCateIds()).stream().findFirst()
                            .ifPresent(c -> this.throwException(spuType.get(c)));
                } else if (CollectionUtils.isNotEmpty(request.getSkuIds())) {
                    //验证自定义货品范围
                    if (CollectionUtils.isNotEmpty(skuIds)) {
                        request.getSkuIds().stream().filter(skuIds::contains).findFirst()
                                .ifPresent(c -> this.throwException(skuType.get(c)));
                    }
                }
                // 最后一页，退出循环
                if (pageNo >= salePage.getTotalPages() - 1) {
                    break;
                }
            }
        }
    }

    /**
     * 验证商品、品牌的重合
     * @param spuIds 商品spuId
     * @param brandIds 品牌Id
     * @return 重合spu结果
     */
    public List<String> checkGoodsAndBrand(List<String> spuIds, List<Long> brandIds) {
        GoodsQueryRequest request = new GoodsQueryRequest();
        request.setGoodsIds(spuIds);
        request.setBrandIds(brandIds);
        request.setDelFlag(DeleteFlag.NO.toValue());
        return goodsService.listCols(request, Collections.singletonList("goodsId")).stream()
                .map(Goods::getGoodsId).collect(Collectors.toList());
    }

    /**
     * 验证商品、店铺分类的重合
     * @param spuIds 商品skuId
     * @param cateIds 店铺分类Id
     * @return 重合结果
     */
    public List<String> checkGoodsAndStoreCate(List<String> spuIds, List<Long> cateIds) {
        return storeCateGoodsRelaRepository.findGoodsIdByStoreCateIdsAndGoodsIds(cateIds, spuIds);
    }

    /**
     * 获取销售类型
     * @return
     */
    private Map<String,Integer> getSaleTypeMap(List<String> skuIds){
        List<GoodsInfo> goodsInfoIdList = goodsInfoRepository.findByGoodsInfoIds(skuIds);
        List<String> spuIdList = goodsInfoIdList.stream()
                .map(GoodsInfo::getGoodsId)
                .distinct()
                .collect(Collectors.toList());
        List<Goods> goodsList = goodsRepository.findAllByGoodsIdIn(spuIdList);
        Map<String, Integer> goodsSaleTypeMap = goodsList.stream()
                .collect(Collectors.toMap(Goods::getGoodsId, Goods::getSaleType));
        return goodsInfoIdList.stream().collect(Collectors.toMap(GoodsInfo::getGoodsInfoId,goodsInfo -> {
            String goodsId = goodsInfo.getGoodsId();
            return goodsSaleTypeMap.get(goodsId);
        }));
    }

    /**
     * 抛异常
     * @param type 类型
     */
    private void throwException(Byte type) {
        String desc = NumberUtils.BYTE_ONE.equals(type) ? "限时购" : "秒杀";
        throw new SbcRuntimeException(GoodsErrorCodeEnum.K030112, new Object[]{desc});
    }

    /**
     * 是否全局互斥
     * @return true:是 false:否
     */
    private Boolean mutexFlag() {
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigType(ConfigType.MARKETING_MUTEX.toValue());
        ConfigVO configVO = systemConfigQueryProvider.findByConfigTypeAndDelFlag(configQueryRequest).getContext().getConfig();
        //营销互斥不验证标识
        return Objects.nonNull(configVO) && NumberUtils.INTEGER_ONE.equals(configVO.getStatus());
    }
}
