package com.wanmi.sbc.job;

import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.api.provider.flashsaleactivity.FlashSaleActivityQueryProvider;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.flashsaleactivity.FlashSaleActivityPageRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsListRequest;
import com.wanmi.sbc.goods.bean.vo.FlashSaleActivityVO;
import com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsVO;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 定时任务Handler
 * 秒杀活动预热秒杀库存到redis中
 *
 * @author minchen
 */
@Component
@Slf4j
public class FlashSalePreheatStockJobHandler {

    @Autowired
    private FlashSaleActivityQueryProvider flashSaleActivityQueryProvider;

    @Autowired
    private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired
    private RedisUtil redisService;

    @XxlJob(value = "flashSalePreheatStockJobHandler")
    public void execute() throws Exception {
        // 每小时第55分钟执行 0 55 * * * ?
        // 查询未来10分钟内即将新开启的秒杀活动
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plus(10, ChronoUnit.MINUTES);
        FlashSaleActivityPageRequest activityPageRequest = FlashSaleActivityPageRequest.builder()
                .fullTimeBegin(startTime)
                .fullTimeEnd(endTime)
                .build();
        List<FlashSaleActivityVO> activityVOList = flashSaleActivityQueryProvider.page(activityPageRequest).getContext()
                .getFlashSaleActivityVOPage()
                .getContent();
        // 初始化秒杀数据过期时间
        long expireSeconds = Constants.FLASH_SALE_LAST_HOUR * 60 * 60 + 600L;

        // 根据活动信息查询参与该场次的秒杀商品
        activityVOList.forEach(activityVO -> {
            FlashSaleGoodsListRequest goodsListRequest = FlashSaleGoodsListRequest.builder()
                    .activityDate(activityVO.getActivityDate())
                    .activityTime(activityVO.getActivityTime())
                    .delFlag(DeleteFlag.NO)
                    .build();
            List<FlashSaleGoodsVO> flashSaleGoodsVOS = flashSaleGoodsQueryProvider.list(goodsListRequest)
                    .getContext()
                    .getFlashSaleGoodsVOList();
            flashSaleGoodsVOS.forEach(flashSaleGoodsVO -> {
                // 预热库存进redis
                String flashSaleGoodsInfoKey = RedisKeyConstant.FLASH_SALE_GOODS_INFO_KEY + flashSaleGoodsVO.getGoodsInfoId();
                redisService.setObj(flashSaleGoodsInfoKey, flashSaleGoodsVO, expireSeconds);

                // 预热库存进redis
                String flashSaleStockKey =
                        RedisKeyConstant.FLASH_SALE_GOODS_INFO_STOCK_KEY + flashSaleGoodsVO.getGoodsInfoId();
                redisService.setString(flashSaleStockKey, String.valueOf(flashSaleGoodsVO.getStock()),
                        expireSeconds);
            });
        });
    }
}
