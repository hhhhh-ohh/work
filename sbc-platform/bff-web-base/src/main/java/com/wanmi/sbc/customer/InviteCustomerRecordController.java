package com.wanmi.sbc.customer;


import com.wanmi.sbc.common.base.*;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionInviteNewQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.DistributionCountInvitedCustRequest;
import com.wanmi.sbc.customer.api.request.customer.DistributionInviteNewPageRequest;
import com.wanmi.sbc.customer.api.response.customer.DistributionInviteNewPageResponse;
import com.wanmi.sbc.customer.api.response.distribution.DistributionCountInvitedCustResponse;
import com.wanmi.sbc.customer.bean.enums.InvalidFlag;
import com.wanmi.sbc.customer.bean.vo.DistributionInviteNewForPageVO;
import com.wanmi.sbc.customer.response.CountInviteCustomerResponse;
import com.wanmi.sbc.customer.response.MyCustomerVO;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.marketing.bean.vo.DistributionSettingVO;
import com.wanmi.sbc.order.api.provider.distribution.ConsumeRecordQueryProvider;
import com.wanmi.sbc.order.api.request.distribution.PageByCustomerIdRequest;
import com.wanmi.sbc.order.api.response.distribution.CountConsumeResponse;
import com.wanmi.sbc.order.bean.vo.CountCustomerConsumeVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Validated
@Slf4j
@Tag(name = "InviteCustomerRecordController", description = "S2B web公用-邀请客户记录API")
@RequestMapping("/customer")
public class InviteCustomerRecordController {

    @Autowired
    private DistributionInviteNewQueryProvider distributionInviteNewQueryProvider;

    @Autowired
    private ConsumeRecordQueryProvider consumeRecordQueryProvider;

    /**
     * 注入分销设置缓存service
     */
    @Autowired
    private DistributionCacheService distributionCacheService;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "统计我邀请的好友信息")
    @RequestMapping(value = "/count-invite-customer", method = RequestMethod.POST)
    public BaseResponse<CountInviteCustomerResponse> countInviteCustomer() {
        String customerId = commonUtil.getOperatorId();
        Boolean isDistributor = commonUtil.isDistributor();
        CountInviteCustomerResponse response = new CountInviteCustomerResponse();
        // 邀新记录表统计
        DistributionCountInvitedCustRequest distributionCountInvitedCustRequest = new DistributionCountInvitedCustRequest();
        distributionCountInvitedCustRequest.setCustomerId(customerId);
        DistributionCountInvitedCustResponse invitedCustResponse = distributionInviteNewQueryProvider.distinctCountInvitedCustomers(distributionCountInvitedCustRequest).getContext();
        response.setInviteNum(invitedCustResponse.getTotalCount());
        response.setValidInviteNum(invitedCustResponse.getValidCount());
        // 如果是分销员，要统计我的顾客
        if (isDistributor) {
            QueryByIdRequest queryByIdRequest = new QueryByIdRequest();
            queryByIdRequest.setId(customerId);
            Integer myCustomerNum = consumeRecordQueryProvider.countByDistributionCustomerId(queryByIdRequest).getContext();
            response.setMyCustomerNum(myCustomerNum);
        }
        return BaseResponse.success(response);
    }

    @Operation(summary = "分页我邀请的客户信息")
    @RequestMapping(value = "/page-invite-customer", method = RequestMethod.POST)
    public BaseResponse<Page> pageInviteCustomer(@RequestBody BaseQueryRequest request) {
        // 查询邀新记录获得分页数据
        String customerId = commonUtil.getOperatorId();
        request.setSortColumn("registerTime");
        request.setSortRole(SortType.DESC.toValue());
        // 查询邀新记录获得分页数据
        Page inviteNewPage = this.pageInviteRecord(customerId, DefaultFlag.NO, request);
        return BaseResponse.success(inviteNewPage);
    }

    @Operation(summary = "分页我有效邀请的客户信息")
    @RequestMapping(value = "/page-valid-invite-customer", method = RequestMethod.POST)
    public BaseResponse<Page> pageValidInvite(@RequestBody BaseQueryRequest request) {
        // 查询邀新记录获得分页数据
        String customerId = commonUtil.getOperatorId();
        request.setSortColumn("orderFinishTime");
        request.setSortRole(SortType.DESC.toValue());
        // 查询邀新记录获得分页数据
        Page inviteNewPage = this.pageInviteRecord(customerId, DefaultFlag.YES, request);
        return BaseResponse.success(inviteNewPage);
    }

    @Operation(summary = "分页我的顾客信息")
    @RequestMapping(value = "/page-my-customer", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage> pageMyCustomer(@RequestBody PageByCustomerIdRequest request) {
        String customerId = commonUtil.getOperatorId();
        request.setCustomerId(customerId);
        return consumeRecordQueryProvider.pageByCustomerId(request);
    }

    /**
     * 查询分销配置信息
     * @return
     */
    @Operation(summary = "查询分销配置信息")
    @RequestMapping(value = "/get-distribution-setting", method = RequestMethod.GET)
    public BaseResponse<DistributionSettingVO> getDistributionSetting() {
        return BaseResponse.success(distributionCacheService.queryDistributionSetting());
    }

    /**
     * 分页获得邀新记录
     */
    private Page<List<MyCustomerVO>> pageInviteRecord(String customerId, DefaultFlag validConsume, BaseQueryRequest baseQueryRequest) {
        DistributionInviteNewPageRequest inviteNewPageRequest = KsBeanUtil.convert(baseQueryRequest,DistributionInviteNewPageRequest.class);
        inviteNewPageRequest.setRequestCustomerId(customerId);
        if (DefaultFlag.YES == validConsume) {
            inviteNewPageRequest.setAvailableDistribution(InvalidFlag.YES);
        }
        DistributionInviteNewPageResponse inviteNewPage =
                distributionInviteNewQueryProvider.findInviteNewRecordPage(inviteNewPageRequest).getContext();
        if (inviteNewPage.getTotal() == 0) {
            return null;
        }
        List<String> ids = inviteNewPage.getRecordList().stream()
                .map(DistributionInviteNewForPageVO::getInvitedNewCustomerId).collect(Collectors.toList());
        QueryByIdListRequest request = new QueryByIdListRequest();
        request.setIdList(ids);
        List<MyCustomerVO> myCustomerVOList = KsBeanUtil.convertList(inviteNewPage.getRecordList(), MyCustomerVO.class);
        CountConsumeResponse countConsumeResponse = null;
        if (DefaultFlag.NO == validConsume) {
            countConsumeResponse = consumeRecordQueryProvider.countConsume(request).getContext();
        } else {
            countConsumeResponse = consumeRecordQueryProvider.countValidConsume(request).getContext();
        }
        List<CountCustomerConsumeVO> voList = countConsumeResponse.getCountCustomerConsumeList();
        if(CollectionUtils.isNotEmpty(myCustomerVOList)) {
            myCustomerVOList.forEach(item -> {
                String cId = item.getInvitedNewCustomerId();
                if(CollectionUtils.isNotEmpty(voList)) {
                    Optional<CountCustomerConsumeVO> target = voList.stream().filter(vo -> cId.equals(vo.getCustomerId())).findFirst();
                    // 匹配相同的客户id，获得用户的消费数据
                    if (target.isPresent()) {
                        item.setAmount(target.get().getAmount());
                        item.setOrderNum(target.get().getOrderNum());
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            });
        }
        Page page = new PageImpl<>(myCustomerVOList, inviteNewPageRequest.getPageable(),
                inviteNewPage.getTotal());
        return page;
    }
}
