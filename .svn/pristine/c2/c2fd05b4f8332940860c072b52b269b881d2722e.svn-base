package com.wanmi.sbc.distribute;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelListRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.bean.enums.LedgerBindState;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelVO;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;
import com.wanmi.sbc.goods.api.provider.distributor.goods.DistributorGoodsInfoProvider;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoAddRequest;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoByCustomerIdRequest;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoDeleteRequest;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoModifySequenceRequest;
import com.wanmi.sbc.goods.api.response.distributor.goods.DistributorGoodsInfoAddResponse;
import com.wanmi.sbc.goods.api.response.distributor.goods.DistributorGoodsInfoCountsResponse;
import com.wanmi.sbc.goods.api.response.distributor.goods.DistributorGoodsInfoDeleteResponse;
import com.wanmi.sbc.goods.bean.dto.DistributorGoodsInfoModifySequenceDTO;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionSettingQueryProvider;
import com.wanmi.sbc.marketing.api.request.distribution.DistributionStoreSettingListByStoreIdsRequest;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionStoreSettingListByStoreIdsResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.DistributionStoreSettingVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.LaKaLaUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分销员商品
 */
@Tag(name =  "分销员商品API" ,description = "DistributorGoodsInfoController")
@RestController
@Validated
@RequestMapping("/distributor-goods")
public class DistributorGoodsInfoController {

    @Autowired
    private DistributorGoodsInfoProvider distributorGoodsInfoProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private DistributionSettingQueryProvider distributionSettingQueryProvider;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Autowired
    private LaKaLaUtils laKaLaUtils;

    /**
     * 新增分销商品
     * @return
     */
    @Operation(summary = "新增分销商品")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public BaseResponse<DistributorGoodsInfoAddResponse> add(@RequestBody DistributorGoodsInfoAddRequest request){
        //检查分销员的商品上限
        distributorGoodsInfoProvider.checkCountsByCustomerId(new DistributorGoodsInfoByCustomerIdRequest(commonUtil.getOperatorId()));
        //查询拉卡拉开关
        PayGatewayVO payGatewayVO = laKaLaUtils.getPayGatewayVO();
        IsOpen isOpen = IsOpen.NO;
        if (payGatewayVO != null) {
            isOpen = payGatewayVO.getIsOpen();
        }
        //如果开的话，需要获取检查该商品所属的商家的分账关系绑定的状态
        if (IsOpen.YES.equals(isOpen)) {
            // 获取商户id
            Long companyInfoId = storeQueryProvider.getById(StoreByIdRequest.builder()
                    .storeId(request.getStoreId())
                    .build()).getContext().getStoreVO().getCompanyInfoId();
            //根据商户id跟接收方id查询绑定关系
            List<LedgerReceiverRelVO> ledgerReceiverRelVOList = ledgerReceiverRelQueryProvider.list(LedgerReceiverRelListRequest.builder()
                    .supplierId(companyInfoId)
                    .receiverId(commonUtil.getOperatorId())
                    .build()).getContext().getLedgerReceiverRelVOList();
            //查不到就是未绑定
            if (CollectionUtils.isEmpty(ledgerReceiverRelVOList)
                    || ledgerReceiverRelVOList.get(0).getBindState() != LedgerBindState.BINDING.toValue()) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080168);
            }
        }
        request.setStatus(Constants.ZERO);
        request.setCustomerId(commonUtil.getOperatorId());
        return distributorGoodsInfoProvider.add(request);
    }

    /**
     * 删除分销商品
     * @return
     */
    @Operation(summary = "删除分销商品")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public BaseResponse<DistributorGoodsInfoDeleteResponse> delete(@RequestBody DistributorGoodsInfoDeleteRequest request){
        request.setCustomerId(commonUtil.getOperatorId());
        return distributorGoodsInfoProvider.delete(request);
    }

    /**
     * 修改分销员商品排序
     * @return
     */
    @Operation(summary = "修改分销员商品排序")
    @RequestMapping(value = "/modify-sequence",method = RequestMethod.POST)
    public BaseResponse<DistributorGoodsInfoDeleteResponse> modifySequence(@RequestBody DistributorGoodsInfoModifySequenceRequest request){
        List<DistributorGoodsInfoModifySequenceDTO> distributorGoodsInfoModifySequenceDTOList = request.getDistributorGoodsInfoDTOList();
        List<String> storeIdList = distributorGoodsInfoModifySequenceDTOList.stream().map(d ->String.valueOf(d.getStoreId())).distinct().collect(Collectors.toList());
        BaseResponse<DistributionStoreSettingListByStoreIdsResponse>  baseResponse = distributionSettingQueryProvider.listByStoreIds(new DistributionStoreSettingListByStoreIdsRequest(storeIdList));
        List<DistributionStoreSettingVO> list = baseResponse.getContext().getList();
        Map<String,DefaultFlag> map =list.stream().collect(Collectors.toMap(DistributionStoreSettingVO::getStoreId,DistributionStoreSettingVO::getOpenFlag));
        distributorGoodsInfoModifySequenceDTOList.stream().forEach(distributorGoodsInfoModifySequenceDTO -> {
            String storeId = String.valueOf(distributorGoodsInfoModifySequenceDTO.getStoreId());
            distributorGoodsInfoModifySequenceDTO.setStatus(DefaultFlag.NO == map.get(storeId) ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO);
            distributorGoodsInfoModifySequenceDTO.setCustomerId(commonUtil.getOperatorId());
            distributorGoodsInfoModifySequenceDTO.setCreateTime(LocalDateTime.now());
            distributorGoodsInfoModifySequenceDTO.setUpdateTime(LocalDateTime.now());
        });
        return distributorGoodsInfoProvider.modifySequence(request);
    }

    /**
     * 查询分销员下分销商品数
     *
     * @return
     */
    @Operation(summary = "查询分销员下分销商品数")
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public BaseResponse<DistributorGoodsInfoCountsResponse> count(){
        DistributorGoodsInfoByCustomerIdRequest request = new DistributorGoodsInfoByCustomerIdRequest(commonUtil.getOperatorId());
        return distributorGoodsInfoProvider.checkCountsByCustomerId(request);
    }

}
