package com.wanmi.sbc.job;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreListByStoreIdsRequest;
import com.wanmi.sbc.customer.api.response.store.StoreListByStoreIdsResponse;
import com.wanmi.sbc.goods.api.provider.distributor.goods.DistributorGoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.distributor.goods.DistributorGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoDeleteByStoreIdsRequest;
import com.wanmi.sbc.goods.api.response.distributor.goods.DistributorGoodsInfoListAllStoreIdResponse;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务Handler
 * 根据分销设置的奖励规则补发邀请奖励
 */
@Component
@Slf4j
public class DistributorGoodsJobHandler {

    @Autowired
    private DistributorGoodsInfoProvider distributorGoodsInfoProvider;

    @Autowired
    private DistributorGoodsInfoQueryProvider distributorGoodsInfoQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @XxlJob(value = "distributorGoodsJobHandler")
    @Transactional(rollbackFor = Exception.class)
    public void execute() throws Exception {
        XxlJobHelper.log("DistributorGoodsTask定时任务执行开始： " + LocalDateTime.now());
        BaseResponse<DistributorGoodsInfoListAllStoreIdResponse> baseResponse = distributorGoodsInfoQueryProvider.findAllStoreId();
        List<Long> list = baseResponse.getContext().getList();
        if (CollectionUtils.isEmpty(list)) {
            XxlJobHelper.log("DistributorGoodsTask定时任务执行结束,分销员商品表------店铺ID集合大小为0 " + LocalDateTime.now());
            return;
        }
        BaseResponse<StoreListByStoreIdsResponse> storeIdsResponseBaseResponse = storeQueryProvider.listByStoreIds(new StoreListByStoreIdsRequest(list));
        list = storeIdsResponseBaseResponse.getContext().getLongList();
        if (CollectionUtils.isEmpty(list)) {
            XxlJobHelper.log("DistributorGoodsTask定时任务执行结束,分销员商品表------已过期的店铺ID集合大小为0 " + LocalDateTime.now());
            return;
        }
        BaseResponse response = distributorGoodsInfoProvider.deleteByStoreIds(new DistributorGoodsInfoDeleteByStoreIdsRequest(list));
        XxlJobHelper.log("DistributorGoodsTask定时任务执行结束： " + LocalDateTime.now() + ",处理总数为：" + response.getContext());
    }
}
