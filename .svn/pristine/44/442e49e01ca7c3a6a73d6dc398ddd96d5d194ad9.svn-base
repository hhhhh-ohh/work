package com.wanmi.sbc.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByIdsResponse;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardQueryProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.*;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardInfoResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardPageResponse;
import com.wanmi.sbc.marketing.bean.enums.GiftCardScopeType;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


/**
 * @description 平台端-礼品卡
 * @author  wur
 * @date: 2022/12/8 20:37
 **/
@RestController("giftCardController")
@Validated
@RequestMapping("/giftCard")
@Tag(name = "GiftCardController", description = "S2B 平台端-礼品卡")
@Slf4j
public class GiftCardController {

    @Autowired
    private GiftCardQueryProvider giftCardQueryProvider;

    @Autowired
    private GiftCardProvider giftCardProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    /**
     * @description 礼品卡-新增
     * @author  wur
     * @date: 2022/12/9 11:25
     * @param request
     * @return
     **/
    @Operation(summary = "S2B 平台端-礼品卡-新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BaseResponse addGiftCard(@RequestBody @Valid GiftCardNewRequest request) {
        request.checkParam();
        request.setUserId(commonUtil.getOperatorId());
        if (!GiftCardScopeType.ALL.equals(request.getScopeType())){
            if (CollectionUtils.isEmpty(request.getScopeIdList())){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if(GiftCardScopeType.GOODS.equals(request.getScopeType())) {
                GoodsInfoListByIdsResponse goodsInfoListByIdsResponse =
                        goodsInfoQueryProvider.listByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(request.getScopeIdList()).build()).getContext();
                if (Objects.isNull(goodsInfoListByIdsResponse) || goodsInfoListByIdsResponse.getGoodsInfos().size() != request.getScopeIdList().size()) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }
        }
        return giftCardProvider.add(request);
    }

    /**
     *
     * @description 礼品卡-编辑
     * @author  wur
     * @date: 2022/12/9 11:25
     * @param request
     * @return
     **/
    @Operation(summary = "S2B 平台端-礼品卡-编辑")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResponse saveGiftCard(@RequestBody @Valid GiftCardSaveRequest request) {
        request.checkParam();
        request.setUserId(commonUtil.getOperatorId());
        String joinLock = CacheKeyConstant.MARKETING_GIFT_CARD_STOCK_LOCK.concat(request.getGiftCardId().toString());
        RLock rLock = redissonClient.getFairLock(joinLock);
        rLock.lock();
        try {
            return giftCardProvider.save(request);
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
    }

    /**
     *
     * @description  礼品卡-新增
     * @author  wur
     * @date: 2022/12/9 11:25
     * @param request
     * @return
     **/
    @Operation(summary = "S2B 平台端-礼品卡-删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResponse deleteGiftCard(@RequestBody @Valid GiftCardDeleteRequest request) {
        request.setUserId(commonUtil.getOperatorId());
        return giftCardProvider.delete(request);
    }

    /**
     * @description   礼品卡-分页查询
     * @author  wur
     * @date: 2022/12/9 11:25
     * @param request
     * @return
     **/
    @Operation(summary = "S2B 平台端-礼品卡-分页查询")
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    public BaseResponse<GiftCardPageResponse> queryPage(@RequestBody @Valid GiftCardPageRequest request) {
        if (StringUtils.isBlank(request.getSortColumn())) {
            request.setSortColumn("createTime");
            request.setSortRole("desc");
        } else {
            // 处理根据库存排序
            if (request.getSortColumn().equals("stock")) {
                Map<String, String> sortMap = new LinkedHashMap<>();
                sortMap.put("stockType", request.getSortRole());
                sortMap.put(request.getSortColumn(), request.getSortRole());
                request.setSortMap(sortMap);
                request.setSortColumn(null);
                request.setSortRole(null);
            }
        }
        return giftCardQueryProvider.queryPage(request);
    }

    /**
     * @description   礼品卡-详情查询
     * @author  wur
     * @date: 2022/12/9 11:25
     * @param request
     * @return
     **/
    @Operation(summary = "S2B 平台端-礼品卡-详情查询")
    @RequestMapping(value = "/queryInfo", method = RequestMethod.POST)
    public BaseResponse<GiftCardInfoResponse> queryInfo(@RequestBody @Valid GiftCardInfoRequest request) {
        return giftCardQueryProvider.queryInfo(request);
    }
}
