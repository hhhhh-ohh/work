package com.wanmi.sbc.flashsalegoods;

import static com.wanmi.sbc.common.util.DateUtil.FMT_TIME_1;

import static java.time.Duration.between;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.goods.api.provider.flashpromotionactivity.FlashPromotionActivityQueryProvider;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsSaveProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.flashsalegoods.*;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.request.info.QueryGoodsInfoRedisStockRequest;
import com.wanmi.sbc.goods.api.response.flashsalegoods.*;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByIdResponse;
import com.wanmi.sbc.goods.bean.enums.FlashSaleGoodsStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.FlashPromotionActivityVO;
import com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoRedisStockVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.marketing.api.provider.newplugin.NewMarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.newplugin.MarketingPluginFlushCacheRequest;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.request.MarketingMutexValidateRequest;
import com.wanmi.sbc.marketing.service.MarketingBaseService;
import com.wanmi.sbc.store.StoreBaseService;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.util.CommonUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Tag(name =  "抢购商品表管理API", description =  "FlashSaleGoodsController")
@RestController
@Validated
@RequestMapping(value = "/flashsalegoods")
public class FlashSaleGoodsController {

    @Autowired
    private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired
    private FlashSaleGoodsSaveProvider flashSaleGoodsSaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    NewMarketingPluginProvider newMarketingPluginProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private FlashPromotionActivityQueryProvider flashPromotionActivityQueryProvider;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    @Autowired private MarketingBaseService marketingBaseService;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private StoreBaseService storeBaseService;

    @Autowired
    private GoodsBaseService goodsBaseService;

    /**
     * 分页查询抢购商品表
     * @param pageReq
     * @return
     */
    @Operation(summary = "分页查询抢购商品表")
    @PostMapping("/page")
    public BaseResponse<FlashSaleGoodsPageResponse> getPage(@RequestBody @Valid FlashSaleGoodsPageRequest pageReq) {
        if (Objects.isNull(pageReq.getType())){
            pageReq.setType(0);
        }
        pageReq.setStoreId(commonUtil.getStoreId());
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("id", "desc");
        BaseResponse<FlashSaleGoodsPageResponse> page = flashSaleGoodsQueryProvider.page(pageReq);

        //填充供应商
        List<GoodsInfoVO> goodsInfoVOS = page.getContext()
                .getFlashSaleGoodsVOPage().getContent()
                .stream().map(FlashSaleGoodsVO::getGoodsInfo)
                .collect(Collectors.toList());
        storeBaseService.populateProviderName(goodsInfoVOS);

        //填充marketingGoodsStatus属性
        goodsBaseService.populateMarketingGoodsStatus(goodsInfoVOS);

        //非商品积分模式下清零
        if((!systemPointsConfigService.isGoodsPoint())){
            page.getContext()
                    .getFlashSaleGoodsVOPage()
                    .getContent()
                    .forEach(v -> v.getGoodsInfo().setBuyPoint(0L));
        }

        return page;
    }

    /**
     * 列表查询抢购商品表
     * @param listReq
     * @return
     */
    @Operation(summary = "列表查询抢购商品表")
    @PostMapping("/list")
    public BaseResponse<FlashSaleGoodsListResponse> getList(@RequestBody @Valid FlashSaleGoodsListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("id", "desc");
        return flashSaleGoodsQueryProvider.list(listReq);
    }

    /**
     * 根据id查询抢购商品表
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询抢购商品表")
    @Parameter(name = "id", description = "抢购id", required = true)
    @GetMapping("/{id}")
    public BaseResponse<FlashSaleGoodsByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        FlashSaleGoodsByIdRequest idReq = new FlashSaleGoodsByIdRequest();
        idReq.setId(id);
        return flashSaleGoodsQueryProvider.getById(idReq);
    }

    /**
     * 新增抢购商品表
     * @param addReq
     * @return
     */
    @Operation(summary = "新增抢购商品表")
    @PostMapping("/batchAdd")
    public BaseResponse<FlashSaleGoodsAddResponse> batchAdd(@RequestBody @Valid FlashSaleGoodsBatchAddRequest addReq) {
        Long storeId = commonUtil.getStoreId();
        List<String> skuIds = addReq.getFlashSaleGoodsVOList().stream().map(FlashSaleGoodsVO::getGoodsInfoId)
                .collect(Collectors.toList());
        // 根据类型走限时购逻辑
        if (Objects.nonNull(addReq.getType()) && Constants.ONE == addReq.getType()){
            if (StringUtils.isBlank(addReq.getActivityName())){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            if (DateUtil.parseDate(addReq.getStartTimeStr(), FMT_TIME_1).withSecond(Constants.NUM_59)
                    .isBefore(LocalDateTime.now().withSecond(Constants.ZERO))){
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080188);
            }
            if (DateUtil.parseDate(addReq.getStartTimeStr(), FMT_TIME_1)
                    .equals(DateUtil.parseDate(addReq.getEndTimeStr(), FMT_TIME_1))){
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080189);
            }
            if (Objects.nonNull(addReq.getPreTime()) && addReq.getPreTime() > Constants.MAX_PRE_TIME){
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080187);
            }
            LocalDateTime startTime = DateUtil.parse(addReq.getStartTimeStr(), FMT_TIME_1);
            LocalDateTime endTime = DateUtil.parse(addReq.getEndTimeStr(), FMT_TIME_1);
            //全局互斥验证
            marketingBaseService.mutexValidateByAdd(storeId, startTime, endTime, skuIds);

            FlashPromotionActivityVO flashPromotionActivityVO = new FlashPromotionActivityVO();
            flashPromotionActivityVO.setDelFlag(DeleteFlag.NO);
            flashPromotionActivityVO.setCreatePerson(commonUtil.getOperatorId());
            flashPromotionActivityVO.setStoreId(storeId);
            flashPromotionActivityVO.setCreateTime(LocalDateTime.now());
            flashPromotionActivityVO.setActivityName(addReq.getActivityName());
            flashPromotionActivityVO.setStartTime(startTime);
            flashPromotionActivityVO.setEndTime(endTime);
            flashPromotionActivityVO.setStatus(Constants.ZERO);
            flashPromotionActivityVO.setPreTime(Objects.nonNull(addReq.getPreTime())?addReq.getPreTime():Constants.ZERO);
            addReq.setFlashPromotionActivityVO(flashPromotionActivityVO);
            addReq.getFlashSaleGoodsVOList().forEach(flashPromotionGoodsVO -> {
                //检查电子卡券库存
                checkElectronicCard(flashPromotionGoodsVO.getGoodsInfoId(),flashPromotionGoodsVO.getStock());
                flashPromotionGoodsVO.setDelFlag(DeleteFlag.NO);
                flashPromotionGoodsVO.setCreatePerson(commonUtil.getOperatorId());
                flashPromotionGoodsVO.setStoreId(storeId);
                flashPromotionGoodsVO.setCreateTime(LocalDateTime.now());
                flashPromotionGoodsVO.setSalesVolume(0L);
                flashPromotionGoodsVO.setActivityName(addReq.getActivityName());
                flashPromotionGoodsVO.setStartTime(startTime);
                flashPromotionGoodsVO.setEndTime(endTime);
                flashPromotionGoodsVO.setStatus(Constants.ZERO);
                flashPromotionGoodsVO.setType(Constants.ONE);
                flashPromotionGoodsVO.setPreTime(addReq.getPreTime());
                if (Objects.nonNull(addReq.getPreTime()) && addReq.getPreTime()>0){
                    flashPromotionGoodsVO.setPreStartTime(startTime.minus(addReq.getPreTime(), ChronoUnit.HOURS));
                }
            });
            BaseResponse<FlashSaleGoodsAddResponse> response =  flashSaleGoodsSaveProvider.batchAddPromotion(addReq);

            //刷新缓存
            flushCache(response.getContext().getFlashSaleGoodsVOS(), addReq.getType());
            return response;
        }
        FlashSaleGoodsVO vo = addReq.getFlashSaleGoodsVOList().get(0);
        LocalDateTime activityDate = DateUtil.parse(vo.getActivityDate()+vo.getActivityTime(),
                "yyyy-MM-ddHH:mm");
        if (LocalDateTime.now().isBefore(activityDate.minusHours(1))) {
            //全局互斥验证
            marketingBaseService.mutexValidateByAdd(storeId, activityDate, activityDate.plusHours(2), skuIds);

            addReq.getFlashSaleGoodsVOList().forEach(flashSaleGoodsVO -> {
                checkElectronicCard(flashSaleGoodsVO.getGoodsInfoId(),flashSaleGoodsVO.getStock());
                flashSaleGoodsVO.setDelFlag(DeleteFlag.NO);
                flashSaleGoodsVO.setCreatePerson(commonUtil.getOperatorId());
                flashSaleGoodsVO.setStoreId(commonUtil.getStoreId());
                flashSaleGoodsVO.setCreateTime(LocalDateTime.now());
                flashSaleGoodsVO.setSalesVolume(0L);
                flashSaleGoodsVO.setActivityFullTime(activityDate);
                flashSaleGoodsVO.setType(Constants.ZERO);
            });
            BaseResponse<FlashSaleGoodsAddResponse> response =  flashSaleGoodsSaveProvider.batchAdd(addReq);

            //刷新缓存
            flushCache(response.getContext().getFlashSaleGoodsVOS(), addReq.getType());
            return response;
        } else {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030109);
        }
    }

    /**
     * 修改抢购商品表
     * @param modifyReq
     * @return
     */
    @Operation(summary = "修改抢购商品表")
    @PutMapping("/modify")
    public BaseResponse<FlashSaleGoodsModifyResponse> modify(@RequestBody @Valid FlashSaleGoodsModifyRequest modifyReq) {
        if (Objects.isNull(modifyReq.getType())){
            modifyReq.setType(0);
        }
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        if (Objects.isNull(modifyReq.getMaxNum())){
            modifyReq.setMaxNum(modifyReq.getStock());
        }
        BaseResponse<FlashSaleGoodsModifyResponse> response =  flashSaleGoodsSaveProvider.modify(modifyReq);
        FlashSaleGoodsVO flashSaleGoodsVO = response.getContext().getFlashSaleGoodsVO();
        checkElectronicCard(flashSaleGoodsVO.getGoodsInfoId(),modifyReq.getStock());
        //刷新库存
        String flashSaleGoodsInfoKey = RedisKeyConstant.FLASH_SALE_GOODS_INFO_KEY + flashSaleGoodsVO.getGoodsInfoId();
        if (redisService.hasKey(flashSaleGoodsInfoKey)){
            long expireSeconds = flashSaleGoodsVO.getActivityFullTime().toEpochSecond(ZoneOffset.of("+8")) + Constants.FLASH_SALE_LAST_HOUR * 60 * 60 + 600L - LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
            redisService.setObj(flashSaleGoodsInfoKey,flashSaleGoodsVO,expireSeconds);
        }
        String flashSaleStockKey =
                RedisKeyConstant.FLASH_SALE_GOODS_INFO_STOCK_KEY + flashSaleGoodsVO.getGoodsInfoId();
        if (redisService.hasKey(flashSaleStockKey)){
            redisService.incrByKey(flashSaleStockKey,Long.valueOf(modifyReq.getIncreaseStock()));
        }

        //刷新缓存
        flushCache(Collections.singletonList(flashSaleGoodsVO), modifyReq.getType());
        return response;
    }

    /**
     * 根据id删除抢购商品表
     * @param id
     * @return
     */
    @Operation(summary = "根据id删除抢购商品表")
    @Parameter(name = "id", description = "抢购id", required = true)
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        FlashSaleGoodsDelByIdRequest delByIdReq = new FlashSaleGoodsDelByIdRequest();
        delByIdReq.setId(id);
        BaseResponse response = flashSaleGoodsSaveProvider.deleteById(delByIdReq);

        //清除缓存
        delCache(id);
        return response;
    }

    /**
     * 刷新缓存
     *
     * @description
     * @author zhanggaolei
     * @date 2021/7/26 11:03 上午
     * @param flashSaleGoodsVOList
     * @return void
     */
    private void flushCache(List<FlashSaleGoodsVO> flashSaleGoodsVOList, Integer type) {
        List<GoodsInfoMarketingCacheDTO> cacheList = new ArrayList<>();
        for (FlashSaleGoodsVO flashSaleGoodsVO : flashSaleGoodsVOList) {
            if (flashSaleGoodsVO.getFlashSaleGoodsStatus()!=null&&!FlashSaleGoodsStatus.PAUSED.equals(flashSaleGoodsVO.getFlashSaleGoodsStatus())) {
                GoodsInfoMarketingCacheDTO cacheDTO = new GoodsInfoMarketingCacheDTO();
                cacheDTO.setId(flashSaleGoodsVO.getId());
                cacheDTO.setSkuId(flashSaleGoodsVO.getGoodsInfoId());
                if (Objects.nonNull(type) && Constants.ONE == type) {
                    cacheDTO.setMarketingPluginType(MarketingPluginType.FLASH_PROMOTION);
                    cacheDTO.setBeginTime(flashSaleGoodsVO.getStartTime());
                    cacheDTO.setEndTime(flashSaleGoodsVO.getEndTime());
                    cacheDTO.setPreStartTime(flashSaleGoodsVO.getPreStartTime());
                } else {
                    cacheDTO.setMarketingPluginType(MarketingPluginType.FLASH_SALE);
                    cacheDTO.setBeginTime(flashSaleGoodsVO.getActivityFullTime());
                    cacheDTO.setEndTime(
                            flashSaleGoodsVO
                                    .getActivityFullTime()
                                    .plusHours(Constants.FLASH_SALE_LAST_HOUR));
                }

                cacheDTO.setPrice(flashSaleGoodsVO.getPrice());
                cacheList.add(cacheDTO);
            }
        }

        if (CollectionUtils.isNotEmpty(cacheList)) {
            newMarketingPluginProvider.flushCache(
                    MarketingPluginFlushCacheRequest.builder()
                            .goodsInfoMarketingCacheDTOS(cacheList)
                            .deleteFlag(DeleteFlag.NO)
                            .build());
        }
    }

    /**
     * 批量删除缓存
     *
     * @description
     * @author xufeng
     * @date 2022/02/10 16:03
     * @param flashSaleGoodsVOList
     * @return void
     */
    private void batchDelCache(List<FlashSaleGoodsVO> flashSaleGoodsVOList, Integer type) {
        List<GoodsInfoMarketingCacheDTO> cacheList = new ArrayList<>();
        for (FlashSaleGoodsVO flashSaleGoodsVO : flashSaleGoodsVOList) {
            GoodsInfoMarketingCacheDTO cacheDTO = new GoodsInfoMarketingCacheDTO();
            cacheDTO.setId(flashSaleGoodsVO.getId());
            cacheDTO.setSkuId(flashSaleGoodsVO.getGoodsInfoId());
            if (Objects.nonNull(type) && Constants.ONE == type){
                cacheDTO.setMarketingPluginType(MarketingPluginType.FLASH_PROMOTION);
                cacheDTO.setBeginTime(flashSaleGoodsVO.getStartTime());
                cacheDTO.setEndTime(flashSaleGoodsVO.getEndTime());
                cacheDTO.setPreStartTime(flashSaleGoodsVO.getPreStartTime());
            }else {
                cacheDTO.setMarketingPluginType(MarketingPluginType.FLASH_SALE);
                cacheDTO.setBeginTime(flashSaleGoodsVO.getActivityFullTime());
                cacheDTO.setEndTime(flashSaleGoodsVO.getActivityFullTime().plusHours(Constants.FLASH_SALE_LAST_HOUR));
            }

            cacheDTO.setPrice(flashSaleGoodsVO.getPrice());
            cacheList.add(cacheDTO);
        }

        if (CollectionUtils.isNotEmpty(cacheList)) {
            newMarketingPluginProvider.flushCache(
                    MarketingPluginFlushCacheRequest.builder()
                            .goodsInfoMarketingCacheDTOS(cacheList)
                            .deleteFlag(DeleteFlag.YES)
                            .build());
        }
    }
    /**
     * 删除缓存
     *
     * @description
     * @author zhanggaolei
     * @date 2021/7/26 11:01 上午
     * @param id
     * @return void
     */
    private void delCache(Long id) {
        FlashSaleGoodsVO flashSaleGoodsVO =
                this.flashSaleGoodsQueryProvider
                        .getById(FlashSaleGoodsByIdRequest.builder().id(id).build())
                        .getContext()
                        .getFlashSaleGoodsVO();
        if (flashSaleGoodsVO != null) {
            GoodsInfoMarketingCacheDTO cacheDTO = new GoodsInfoMarketingCacheDTO();
            cacheDTO.setId(flashSaleGoodsVO.getId());
            cacheDTO.setMarketingPluginType(MarketingPluginType.FLASH_SALE);
            cacheDTO.setSkuId(flashSaleGoodsVO.getGoodsInfoId());
            cacheDTO.setBeginTime(flashSaleGoodsVO.getActivityFullTime());
            cacheDTO.setEndTime(flashSaleGoodsVO.getActivityFullTime().plusHours(Constants.FLASH_SALE_LAST_HOUR));
            newMarketingPluginProvider.flushCache(
                    MarketingPluginFlushCacheRequest.builder()
                            .goodsInfoMarketingCacheDTOS(Collections.singletonList(cacheDTO))
                            .deleteFlag(DeleteFlag.YES)
                            .build());
        }
    }

    /**
     * 关闭抢购商品
     * @param id
     * @return
     */
    @Operation(summary = "关闭抢购商品")
    @Parameter(name = "id", description = "抢购id", required = true)
    @PutMapping("/close/{id}")
    public BaseResponse close(@PathVariable Long id) {
        flashSaleGoodsSaveProvider.close(FlashSaleGoodsCloseRequest.builder().id(id).build());
        delCache(id);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改抢购商品暂停开始状态
     * @param modifyReq
     * @return
     */
    @Operation(summary = "修改抢购商品暂停开始状态")
    @PutMapping("/flashPromotion/modifyStatus")
    public BaseResponse modifyByActivityId(@RequestBody @Valid FlashPromotionActivityModifyStatusRequest modifyReq) {
        FlashSaleGoodsByActivityIdRequest idReq = new FlashSaleGoodsByActivityIdRequest();
        idReq.setActivityId(modifyReq.getActivityId());
        List<FlashSaleGoodsVO> flashSaleGoodsVOs =
                flashSaleGoodsQueryProvider.getByActivityId(idReq).getContext().getFlashSaleGoodsVOs();
        if (CollectionUtils.isNotEmpty(flashSaleGoodsVOs)){
            // 校验未开始和已暂停的可以编辑
            LocalDateTime returnStartTime = flashSaleGoodsVOs.get(0).getStartTime();
            LocalDateTime returnEndTime = flashSaleGoodsVOs.get(0).getEndTime();
            LocalDateTime nowTime = LocalDateTime.now();
            // 已结束不可编辑
            if (returnEndTime.isBefore(nowTime)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            // 未开始，不可编辑
            if (returnStartTime.isAfter(nowTime)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }

        // 删除限时购缓存
        flashSaleGoodsVOs.forEach(flashSaleGoodsVO -> {
            // 删除限时购缓存
            String flashSaleGoodsInfoKey = RedisKeyConstant.FLASH_SALE_GOODS_INFO_KEY + flashSaleGoodsVO.getGoodsInfoId();
//            if (redisService.hasKey(flashSaleGoodsInfoKey)){
                redisService.delete(flashSaleGoodsInfoKey);
//            }
        });
        // 开始 刷新缓存，暂停 删除缓存
        if (modifyReq.getStatus()==Constants.ZERO){
            flashSaleGoodsVOs.forEach(flashSaleGoodsVO -> {
                flashSaleGoodsVO.setFlashSaleGoodsStatus(FlashSaleGoodsStatus.STARTED);
            });
            flushCache(flashSaleGoodsVOs, Constants.ONE);
        }else {
            //删除之前缓存
            batchDelCache(flashSaleGoodsVOs, Constants.ONE);
        }
        return flashPromotionActivityQueryProvider.modifyByActivityId(modifyReq);
    }

    /**
     * 根据抢购活动id删除抢购商品表
     * @param activityId
     * @return
     */
    @Operation(summary = "根据id删除抢购商品表")
    @Parameter(name = "activityId", description = "抢购活动id", required = true)
    @DeleteMapping("/flashPromotion/{activityId}")
    public BaseResponse deleteByActivityId(@PathVariable String activityId) {
        if (activityId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        FlashSaleGoodsByActivityIdRequest idReq = new FlashSaleGoodsByActivityIdRequest();
        idReq.setActivityId(activityId);

        List<FlashSaleGoodsVO> flashSaleGoodsVOs =
                flashSaleGoodsQueryProvider.getByActivityId(idReq).getContext().getFlashSaleGoodsVOs();

        FlashSaleGoodsDelByActivityIdRequest delByIdReq = new FlashSaleGoodsDelByActivityIdRequest();
        delByIdReq.setActivityId(activityId);
        BaseResponse response = flashSaleGoodsSaveProvider.deleteByActivityId(delByIdReq);
        // 删除限时购缓存
        flashSaleGoodsVOs.forEach(flashSaleGoodsVO -> {
            // 删除限时购缓存
            String flashSaleGoodsInfoKey = RedisKeyConstant.FLASH_SALE_GOODS_INFO_KEY + flashSaleGoodsVO.getGoodsInfoId();
//            if (redisService.hasKey(flashSaleGoodsInfoKey)){
                redisService.delete(flashSaleGoodsInfoKey);
//            }
            String flashSaleStockKey =
                    RedisKeyConstant.FLASH_SALE_GOODS_INFO_STOCK_KEY + flashSaleGoodsVO.getGoodsInfoId();
//            if (redisService.hasKey(flashSaleStockKey)){
                redisService.delete(flashSaleStockKey);
//            }
        });

        //删除之前缓存
        batchDelCache(flashSaleGoodsVOs, Constants.ONE);
        return response;
    }

    /**
     * 修改抢购活动表
     * @param modifyReq
     * @return
     */
    @Operation(summary = "修改抢购活动表")
    @PutMapping("/flashPromotion/modify")
    public BaseResponse<FlashSaleGoodsAddResponse> modifyPromotion(@RequestBody @Valid FlashSaleGoodsBatchModifyRequest modifyReq) {
        LocalDateTime startTime = DateUtil.parse(modifyReq.getStartTimeStr(), FMT_TIME_1);
        LocalDateTime endTime = DateUtil.parse(modifyReq.getEndTimeStr(), FMT_TIME_1);
        String activityId = modifyReq.getActivityId();
        LocalDateTime createTime = null;
        String createPerson = null;
        Integer returnStatus = null;
        FlashSaleGoodsByActivityIdRequest idReq = new FlashSaleGoodsByActivityIdRequest();
        idReq.setActivityId(activityId);
        List<FlashSaleGoodsVO> flashSaleGoodsVOs =
                flashSaleGoodsQueryProvider.getByActivityId(idReq).getContext().getFlashSaleGoodsVOs();
        Map<String, FlashSaleGoodsVO> saleGoodsMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(flashSaleGoodsVOs)){
            //越权校验
            commonUtil.checkStoreId(flashSaleGoodsVOs.get(0).getStoreId());
            createPerson = flashSaleGoodsVOs.get(0).getCreatePerson();
            createTime = flashSaleGoodsVOs.get(0).getCreateTime();
            // 校验未开始和已暂停的可以编辑
            returnStatus = flashSaleGoodsVOs.get(0).getStatus();
            LocalDateTime returnStartTime = flashSaleGoodsVOs.get(0).getStartTime();
            LocalDateTime returnEndTime = flashSaleGoodsVOs.get(0).getEndTime();
            LocalDateTime nowTime = LocalDateTime.now();
            // 已结束不可编辑
            if (returnEndTime.isBefore(nowTime)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            // 进行中，非暂停状态不可编辑
            if (returnStartTime.isBefore(nowTime)){
                if (Constants.ZERO==returnStatus){
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080099);
                }
            }
            saleGoodsMap.putAll(flashSaleGoodsVOs.stream().collect(Collectors.toMap(FlashSaleGoodsVO::getGoodsInfoId, Function.identity())));
        }
        Long storeId = commonUtil.getStoreId();
        //全局互斥校验
        List<String> skuIds = modifyReq.getFlashSaleGoodsVOList().stream().map(FlashSaleGoodsVO::getGoodsInfoId)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(skuIds)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        MarketingMutexValidateRequest validateRequest = new MarketingMutexValidateRequest();
        validateRequest.setStoreId(storeId);
        validateRequest.setCrossBeginTime(startTime);
        validateRequest.setCrossEndTime(endTime);
        validateRequest.setSkuIds(skuIds);
        validateRequest.setNotSelfId(activityId);
        validateRequest.setFlashIdFlag(Boolean.TRUE);
        marketingBaseService.mutexValidate(validateRequest);

        FlashPromotionActivityVO flashPromotionActivityVO = new FlashPromotionActivityVO();
        flashPromotionActivityVO.setActivityId(activityId);
        flashPromotionActivityVO.setDelFlag(DeleteFlag.NO);
        flashPromotionActivityVO.setUpdatePerson(commonUtil.getOperatorId());
        flashPromotionActivityVO.setStoreId(storeId);
        flashPromotionActivityVO.setUpdateTime(LocalDateTime.now());
        flashPromotionActivityVO.setActivityName(modifyReq.getActivityName());
        flashPromotionActivityVO.setStartTime(startTime);
        flashPromotionActivityVO.setEndTime(endTime);
        flashPromotionActivityVO.setStatus(returnStatus);
        flashPromotionActivityVO.setPreTime(Objects.nonNull(modifyReq.getPreTime())?modifyReq.getPreTime():Constants.ZERO);
        flashPromotionActivityVO.setCreatePerson(createPerson);
        flashPromotionActivityVO.setCreateTime(createTime);
        modifyReq.setFlashPromotionActivityVO(flashPromotionActivityVO);
        Integer finalReturnStatus = returnStatus;
        FlashSaleGoodsVO goodsVO = new FlashSaleGoodsVO();
        goodsVO.setSalesVolume(0L);
        modifyReq.getFlashSaleGoodsVOList().forEach(flashPromotionGoodsVO -> {
            //检查电子卡券库存
            checkElectronicCard(flashPromotionGoodsVO.getGoodsInfoId(),flashPromotionGoodsVO.getStock());
            flashPromotionGoodsVO.setActivityId(activityId);
            flashPromotionGoodsVO.setDelFlag(DeleteFlag.NO);
            flashPromotionGoodsVO.setUpdatePerson(commonUtil.getOperatorId());
            flashPromotionGoodsVO.setStoreId(storeId);
            flashPromotionGoodsVO.setUpdateTime(LocalDateTime.now());
            FlashSaleGoodsVO saleGoodsVO = saleGoodsMap.getOrDefault(flashPromotionGoodsVO.getGoodsInfoId(), goodsVO);
            flashPromotionGoodsVO.setSalesVolume(saleGoodsVO.getSalesVolume());
            flashPromotionGoodsVO.setActivityName(modifyReq.getActivityName());
            flashPromotionGoodsVO.setStartTime(startTime);
            flashPromotionGoodsVO.setEndTime(endTime);
            flashPromotionGoodsVO.setStatus(finalReturnStatus);
            flashPromotionGoodsVO.setPreTime(Objects.nonNull(modifyReq.getPreTime())?modifyReq.getPreTime():Constants.ZERO);
            if (Objects.nonNull(modifyReq.getPreTime()) && modifyReq.getPreTime()>0){
                flashPromotionGoodsVO.setPreStartTime(startTime.minus(modifyReq.getPreTime(), ChronoUnit.HOURS));
            }
            flashPromotionGoodsVO.setType(Constants.ONE);
            flashPromotionGoodsVO.setPreTime(modifyReq.getPreTime());
            // 删除限时购缓存
            String flashSaleGoodsInfoKey = RedisKeyConstant.FLASH_SALE_GOODS_INFO_KEY + flashPromotionGoodsVO.getGoodsInfoId();
//            if (redisService.hasKey(flashSaleGoodsInfoKey)){
                redisService.delete(flashSaleGoodsInfoKey);
//            }
            if (Objects.nonNull(flashPromotionGoodsVO.getIncreaseStock())){
                String flashSaleStockKey =
                        RedisKeyConstant.FLASH_SALE_GOODS_INFO_STOCK_KEY + flashPromotionGoodsVO.getGoodsInfoId();
                if (redisService.hasKey(flashSaleStockKey)){
                    redisService.incrByKey(flashSaleStockKey,Long.valueOf(flashPromotionGoodsVO.getIncreaseStock()));
                    Duration duration = between(LocalDateTime.now(),  flashPromotionGoodsVO.getEndTime());
                    long existSeconds = duration.getSeconds();
                    if (existSeconds<=0){
                        existSeconds = Constants.FIVE;
                    }
                    redisService.expireBySeconds(flashSaleStockKey, existSeconds);
                }
            }
        });
        BaseResponse<FlashSaleGoodsAddResponse> response =  flashSaleGoodsSaveProvider.batchModify(modifyReq);

        //删除之前缓存
        batchDelCache(flashSaleGoodsVOs, Constants.ONE);
        //刷新缓存
        flushCache(response.getContext().getFlashSaleGoodsVOS(), Constants.ONE);
        return response;
    }

    /**
     * 检查电子卡券库存
     * @param goodsInfoId
     * @param stock
     */
    private void checkElectronicCard(String goodsInfoId,Integer stock){
        GoodsInfoByIdResponse goodsInfoByIdResponse = goodsInfoQueryProvider.getById(GoodsInfoByIdRequest.builder()
                .goodsInfoId(goodsInfoId)
                .build()).getContext();
        Integer goodsType = goodsInfoByIdResponse.getGoodsType();

        //如果是卡券商品，独立库存不能大于现有库存
        if (Objects.nonNull(goodsType) && goodsType == Constants.TWO) {
            //获取独立库存
            List<GoodsInfoRedisStockVO> goodsInfoRedisStockVOList = goodsInfoProvider.queryGoodsInfoRedisStock(QueryGoodsInfoRedisStockRequest.builder()
                    .goodsInfoIdList(Lists.newArrayList(goodsInfoId))
                    .build())
                    .getContext().getGoodsInfoRedisStockVOList();
            // 获取卡券库存
            Long skuStock = goodsInfoRedisStockVOList.get(0).getStock();
            if (stock > skuStock) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
    }
}
