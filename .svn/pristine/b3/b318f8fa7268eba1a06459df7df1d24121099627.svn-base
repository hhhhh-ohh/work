package com.wanmi.sbc.flashsale;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.flashsalegoods.IsInProgressReq;
import com.wanmi.sbc.goods.api.response.flashsalegoods.IsInProgressResp;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @program: sbc-micro-service
 * @description: 秒杀商品
 * @create: 2019-06-17 10:30
 **/
@RestController
@Validated
@RequestMapping("/flashsalebase")
@Tag(name = "FlashsaleGoodsController", description = "秒杀商品")
public class FlashsaleGoodsController {
    @Autowired
    FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired
    AuditQueryProvider auditQueryProvider;

    /**
     * @Description: 商品是否正在抢购活动中
     * @param goodsId
     * @Author: Bob
     * @Date: 2019-06-17 10:41
     */
    @Operation(summary = "商品是否正在抢购活动中")
    @GetMapping("/{goodsId}/isInProgress")
    public BaseResponse<IsInProgressResp> isInProgress(@PathVariable String goodsId){
        LocalDateTime begin = LocalDateTime.now();
        LocalDateTime end = begin.minus(Constants.FLASH_SALE_LAST_HOUR, ChronoUnit.HOURS);
        return flashSaleGoodsQueryProvider.isInProgress(IsInProgressReq.builder().goodsId(goodsId).begin(begin)
                .end(end).build());
    }

    /**
     * @Description: 商品是否正在限时抢购活动中
     * @param goodsId
     * @Author: xufeng
     * @Date: 2022-02-17 15:41
     */
    @Operation(summary = "商品是否正在限时抢购活动中")
    @GetMapping("/{goodsId}/promotionIsInProgress")
    public BaseResponse<IsInProgressResp> promotionIsInProgress(@PathVariable String goodsId){
        return flashSaleGoodsQueryProvider.promotionIsInProgress(IsInProgressReq.builder().goodsId(goodsId).build());
    }

    /**
     * 查询限时抢购库存抢完后是否允许原价购买
     */
    @Operation(summary = "查询限时抢购库存抢完后是否允许原价购买")
    @GetMapping("/promotion/original/price/list")
    public BaseResponse<ConfigVO> flashPromotionOriginalPrice () {
        return auditQueryProvider.isAllowFlashPromotionOriginalPrice();
    }

    /**
     * 查询整点秒杀库存抢完后是否允许原价购买
     */
    @Operation(summary = "查询限时抢购库存抢完后是否允许原价购买")
    @GetMapping("/original/price/list")
    public BaseResponse<ConfigVO> flashSaleOriginalPrice () {
        return auditQueryProvider.isAllowFlashSaleOriginalPrice();
    }
}