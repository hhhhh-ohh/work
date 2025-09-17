package com.wanmi.sbc.job;

import com.wanmi.sbc.goods.api.provider.pointsgoods.PointsGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.pointsgoods.PointsGoodsSaveProvider;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsMinusStockRequest;
import com.wanmi.sbc.goods.bean.vo.PointsGoodsVO;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yang
 * @since 2019/5/24
 */
@Component
@Slf4j
public class PointsGoodsJobHandler {

    @Autowired
    private PointsGoodsQueryProvider pointsGoodsQueryProvider;

    @Autowired
    private PointsGoodsSaveProvider pointsGoodsSaveProvider;

    @XxlJob(value = "pointsGoodsJobHandler")
    public void execute() throws Exception {
        XxlJobHelper.log("积分商品定时任务执行 " + LocalDateTime.now());
        List<PointsGoodsVO> pointsGoodsVOList = pointsGoodsQueryProvider.queryOverdueList().getContext().getPointsGoodsVOList();
        int total = pointsGoodsVOList.size();
        pointsGoodsVOList.forEach(pointsGoodsVO -> {
            // 过期积分商品兑换库存归0并停用
            pointsGoodsSaveProvider.resetStockById(PointsGoodsMinusStockRequest.builder()
                    .pointsGoodsId(pointsGoodsVO.getPointsGoodsId())
                    .build());
        });
        XxlJobHelper.log("积分商品定时任务执行结束： " + LocalDateTime.now() + ",处理总数为：" + total);
    }
}
