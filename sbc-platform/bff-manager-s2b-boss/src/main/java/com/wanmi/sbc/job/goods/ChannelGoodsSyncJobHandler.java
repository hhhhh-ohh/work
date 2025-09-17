package com.wanmi.sbc.job.goods;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyListRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByCompanyInfoIdRequest;
import com.wanmi.sbc.customer.api.response.company.CompanyInfoListResponse;
import com.wanmi.sbc.customer.api.response.store.StoreByCompanyInfoIdResponse;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.provider.channel.goods.ChannelSyncGoodsProvider;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncVasRequest;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.Objects;

/**
 * @Author zhengyang
 * @Description 商品手动初始化定时任务
 * @Date 14:56 2021/05/24
 **/
@Slf4j
@Component
public class ChannelGoodsSyncJobHandler {

    @Resource
    private ChannelSyncGoodsProvider channelSyncGoodsProvider;
    @Resource
    private StoreQueryProvider storeQueryProvider;
    @Resource
    private CompanyInfoQueryProvider companyInfoQueryProvider;
    @Resource
    private CommonUtil commonUtil;

    /***
     * 默认京东Code
     */
    private static final String JD_COMPANY_CODE = "JDvop";

    @XxlJob(value="ChannelGoodsSyncJobHandler")
    public void execute() throws Exception {
        String s = XxlJobHelper.getJobParam();
        // 是否购买京东渠道
        if(commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_VOP)){
            BaseResponse<CompanyInfoListResponse> companyInfoResponse = companyInfoQueryProvider
                    .listCompanyInfo(CompanyListRequest.builder().equalsCompanyCode(JD_COMPANY_CODE).build());
            if (Objects.isNull(companyInfoResponse)
                    || Objects.isNull(companyInfoResponse.getContext())
                    && CollectionUtils.isEmpty(companyInfoResponse.getContext().getCompanyInfoVOList())) {
                XxlJobHelper.handleFail();
                return;
            }
            CompanyInfoVO companyInfoVO = companyInfoResponse.getContext().getCompanyInfoVOList().stream().findFirst().orElse(null);
            BaseResponse<StoreByCompanyInfoIdResponse> storeResponse = storeQueryProvider
                    .getStoreByCompanyInfoId(StoreByCompanyInfoIdRequest.builder().companyInfoId(companyInfoVO.getCompanyInfoId()).build());
            if (Objects.isNull(storeResponse) || Objects.isNull(storeResponse.getContext())) {
                XxlJobHelper.handleFail();
                return;
            }
            StoreVO storeVO = storeResponse.getContext().getStoreVO();
            String poolNum = null;
            String batchNum = null;
            if(Objects.nonNull(s)){
                log.info("初始化指定商品池商品，池编号："+s);
                JSONObject json = JSONObject.parseObject(s);
                poolNum = json.getString("poolNum");
                batchNum = json.getString("batchNum");
            }

            // 通知商品初始化任务开始
            channelSyncGoodsProvider.syncGoodsNotice(ChannelGoodsSyncVasRequest.builder()
                    .companyInfoId(companyInfoVO.getCompanyInfoId())
                    .storeId(storeVO.getStoreId())
                    .companyName(companyInfoVO.getCompanyName())
                    .poolNum(poolNum)
                    .batchNum(batchNum)
                    .thirdPlatformType(ThirdPlatformType.VOP).build());
        }
    }
}
