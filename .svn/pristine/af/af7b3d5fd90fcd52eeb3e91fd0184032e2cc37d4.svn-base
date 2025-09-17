package com.wanmi.sbc.job;

import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.marketing.api.provider.pointscoupon.PointsCouponQueryProvider;
import com.wanmi.sbc.marketing.api.provider.pointscoupon.PointsCouponSaveProvider;
import com.wanmi.sbc.marketing.api.request.pointscoupon.PointsCouponSwitchRequest;
import com.wanmi.sbc.marketing.bean.vo.PointsCouponVO;
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
public class PointsCouponJobHandler {

    @Autowired
    private PointsCouponQueryProvider pointsCouponQueryProvider;

    @Autowired
    private PointsCouponSaveProvider pointsCouponSaveProvider;

    @XxlJob(value = "pointsCouponJobHandler")
    public void execute() throws Exception {
        XxlJobHelper.log("积分兑换券定时任务执行 " + LocalDateTime.now());
        List<PointsCouponVO> pointsCouponVOList = pointsCouponQueryProvider.queryOverdueList().getContext().getPointsCouponVOList();
        int total = pointsCouponVOList.size();
        pointsCouponVOList.forEach(pointsCouponVO -> {
            PointsCouponSwitchRequest pointsCouponSwitchRequest = PointsCouponSwitchRequest.builder()
                    .pointsCouponId(pointsCouponVO.getPointsCouponId())
                    .status(EnableStatus.DISABLE)
                    .build();
            pointsCouponSaveProvider.modifyStatus(pointsCouponSwitchRequest);
        });
        XxlJobHelper.log("积分商品定时任务执行结束： " + LocalDateTime.now() + ",处理总数为：" + total);
    }
}
