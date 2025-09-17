package com.wanmi.sbc.goods.service.list.imp;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;
import com.wanmi.sbc.goods.api.provider.distributor.goods.DistributorGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoListByCustomerIdRequest;
import com.wanmi.sbc.goods.api.response.distributor.goods.DistributorGoodsInfoListByCustomerIdResponse;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.vo.DistributorGoodsInfoVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.LaKaLaUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className DistributorGoodsInfoListService
 * @description TODO
 * @date 2021/8/11 5:14 下午
 */
@Service
public class DistributorGoodsInfoListService extends GoodsInfoListService {

    @Autowired CommonUtil commonUtil;

    @Autowired DistributorGoodsInfoQueryProvider distributorGoodsInfoQueryProvider;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LaKaLaUtils laKaLaUtils;

    @Override
    public EsGoodsInfoQueryRequest setRequest(EsGoodsInfoQueryRequest request) {

        // CustomerVO customer = commonUtil.getCustomer();
        DistributorGoodsInfoListByCustomerIdRequest distributorRequest =
                new DistributorGoodsInfoListByCustomerIdRequest();
        distributorRequest.setCustomerId(commonUtil.getUserInfo().getUserId());

        BaseResponse<DistributorGoodsInfoListByCustomerIdResponse> baseResponse =
                distributorGoodsInfoQueryProvider.listByCustomerId(distributorRequest);
        List<DistributorGoodsInfoVO> distributorGoodsInfoVOList =
                baseResponse.getContext().getDistributorGoodsInfoVOList();
        List<String> distributorIdList =
                distributorGoodsInfoVOList.stream()
                        .map(DistributorGoodsInfoVO::getGoodsInfoId)
                        .collect(Collectors.toList());
        request.setDistributionGoodsAudit(DistributionGoodsAudit.CHECKED.toValue());
        request.setDistributionGoodsStatus(NumberUtils.INTEGER_ZERO);
        request = wrapEsGoodsInfoQueryRequest(request);
        request.setCustomerLevelId(null);
        request.setCustomerLevelDiscount(null);
        request.setDistributionGoodsInfoIds(distributorIdList);
        request.setIsBuyCycle(Constants.no);
        //查询拉卡拉开关
        PayGatewayVO payGatewayVO = laKaLaUtils.getPayGatewayVO();
        IsOpen isOpen = IsOpen.NO;
        if (payGatewayVO != null) {
            isOpen = payGatewayVO.getIsOpen();
        }
        //如果开的话，需要获取商品的审核状态
        if (IsOpen.YES.equals(isOpen)) {
            request.setDistributionGoodsInfoList(distributorGoodsInfoVOList);
        }
        threadRrequest.set(request);
        return request;
    }


    /**
     * 设置加入标识
     * @param t
     * @return
     */
    @Override
    public EsGoodsInfoSimpleResponse afterProcess(EsGoodsInfoSimpleResponse t, Long storeId) {
        if(CollectionUtils.isNotEmpty(threadRrequest.get().getDistributionGoodsInfoIds())){
            t.getEsGoodsInfoPage().getContent().stream().forEach(g -> {
                if(threadRrequest.get().getDistributionGoodsInfoIds().contains(g.getGoodsInfo().getGoodsInfoId())){
                    g.getGoodsInfo().setJoinDistributior(1);
                }else{
                    g.getGoodsInfo().setJoinDistributior(0);
                }
            });
        }
        t = super.afterProcess(t, storeId);
        return t;
    }


}
