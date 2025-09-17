package com.wanmi.sbc.groupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.MarketingGoodsStatus;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.elastic.api.provider.groupon.EsGrouponActivityProvider;
import com.wanmi.sbc.elastic.api.provider.groupon.EsGrouponActivityQueryProvider;
import com.wanmi.sbc.elastic.api.request.groupon.*;
import com.wanmi.sbc.elastic.api.response.groupon.EsGrouponActivityPageResponse;
import com.wanmi.sbc.goods.api.provider.groupongoodsinfo.GrouponGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.groupongoodsinfo.GrouponGoodsInfoSaveProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.groupongoodsinfo.GrouponGoodsInfoCloseRequest;
import com.wanmi.sbc.goods.api.request.groupongoodsinfo.GrouponGoodsInfoListRequest;
import com.wanmi.sbc.goods.api.request.groupongoodsinfo.GrouponGoodsInfoModifyAuditStatusRequest;
import com.wanmi.sbc.goods.api.request.groupongoodsinfo.GrouponGoodsInfoModifyStickyRequest;
import com.wanmi.sbc.goods.api.request.info.DistributionGoodsPageRequest;
import com.wanmi.sbc.goods.bean.enums.AuditStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GrouponGoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivitySaveProvider;
import com.wanmi.sbc.marketing.api.request.grouponactivity.*;
import com.wanmi.sbc.marketing.api.response.grouponactivity.GrouponActivityAddResponse;
import com.wanmi.sbc.marketing.api.response.grouponactivity.GrouponActivityByIdResponse;
import com.wanmi.sbc.marketing.bean.vo.EsGrouponActivityVO;
import com.wanmi.sbc.marketing.bean.vo.GrouponActivityForManagerVO;
import com.wanmi.sbc.store.StoreBaseService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * S2B的拼团活动服务
 */
@Slf4j
@RestController
@Validated
@RequestMapping("/groupon/activity")
@Tag(name =  "S2B的拼团活动服务", description =  "GrouponActivityController")
public class GrouponActivityController {

    @Autowired
    private GrouponActivityQueryProvider grouponActivityQueryProvider;

    @Autowired
    private GrouponActivitySaveProvider grouponActivitySaveProvider;

    @Autowired
    private GrouponGoodsInfoSaveProvider grouponGoodsInfoSaveProvider;

    @Autowired
    private GrouponGoodsInfoQueryProvider grouponGoodsInfoQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsGrouponActivityQueryProvider esGrouponActivityQueryProvider;

    @Autowired
    private EsGrouponActivityProvider esGrouponActivityProvider;

    @Autowired
    private StoreBaseService storeBaseService;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;


    /**
     * 分页查询拼团活动
     *
     * @param pageRequest 商品 {@link DistributionGoodsPageRequest}
     * @return 拼团活动分页
     */
    @Operation(summary = "分页查询拼团活动")
    @RequestMapping(value = "page", method = RequestMethod.POST)
    public BaseResponse<EsGrouponActivityPageResponse> page(@RequestBody @Valid EsGrouponActivityPageRequest
                                                                    pageRequest) {
        pageRequest.setStoreId(Objects.toString(commonUtil.getStoreId(), pageRequest.getStoreId()));
        pageRequest.setDelFlag(DeleteFlag.NO);
        pageRequest.setSortColumn("startTime");
        pageRequest.setSortRole(SortType.DESC.toValue());
        //grouponActivityQueryProvider.page4Manager(pageRequest);
        BaseResponse<EsGrouponActivityPageResponse> response = esGrouponActivityQueryProvider.page(pageRequest);

        //填充goodsMarketingStatus属性
        this.populateMarketingGoodsStatus(response.getContext());
        return response;
    }

    /**
     * @description 填充goodsMarketingStatus属性
     * @Author qiyuanzhao
     * @Date 2022/10/13 15:08
     * @param
     * @return
     **/
    private void populateMarketingGoodsStatus(EsGrouponActivityPageResponse context) {
        MicroServicePage<GrouponActivityForManagerVO> goodsVOPage = context.getGrouponActivityVOPage();
        List<GrouponActivityForManagerVO> managerVOS = context.getGrouponActivityVOPage().getContent();
        if (org.apache.commons.collections.CollectionUtils.isEmpty(managerVOS)){
            return;
        }
        List<String> goodsInfoIds = managerVOS.stream().map(GrouponActivityForManagerVO::getGoodsInfoId).collect(Collectors.toList());

        //填充营销商品状态
        Map<String, MarketingGoodsStatus> statusMap = goodsBaseService.getMarketingGoodsStatusListByGoodsInfoIds(goodsInfoIds);

        for (GrouponActivityForManagerVO grouponActivityForManagerVO : managerVOS){
            String goodsInfoId = grouponActivityForManagerVO.getGoodsInfoId();
            MarketingGoodsStatus status = statusMap.get(goodsInfoId);
            grouponActivityForManagerVO.setMarketingGoodsStatus(status);
        }
        goodsVOPage.setContent(managerVOS);
        context.setGrouponActivityVOPage(goodsVOPage);
    }


    /**
     * 查询拼团活动
     *
     * @param activityId 拼团活动id
     */
    @Operation(summary = "查询拼团活动")
    @RequestMapping(value = "/{activityId}", method = RequestMethod.GET)
    public BaseResponse<GrouponActivityByIdResponse> info(@PathVariable String activityId) {

        // 1.查询拼团活动
        BaseResponse<GrouponActivityByIdResponse> response = grouponActivityQueryProvider.getById(
                new GrouponActivityByIdRequest(activityId));
        //越权校验
        commonUtil.checkStoreId(Long.valueOf(response.getContext().getGrouponActivity().getStoreId()));

        // 2.查询拼团活动商品
        GrouponGoodsInfoListRequest request = new GrouponGoodsInfoListRequest();
        request.setGrouponActivityId(activityId);
        List<GrouponGoodsInfoVO> goodsInfos = grouponGoodsInfoQueryProvider.list(
                request).getContext().getGrouponGoodsInfoVOList();

        //填充供应商名称
        List<GoodsInfoVO> goodsInfoVOS = goodsInfos.stream().map(GrouponGoodsInfoVO::getGoodsInfo).collect(Collectors.toList());
        storeBaseService.populateProviderName(goodsInfoVOS);

        //填充marketingGoodsStatus属性
        goodsBaseService.populateMarketingGoodsStatus(goodsInfoVOS);

        response.getContext().setGrouponGoodsInfos(goodsInfos);
        return response;
    }

    /**
     * 批量审核拼团活动
     *
     * @param request
     * @return
     */
    @Operation(summary = "批量审核通过拼团活动")
    @RequestMapping(value = "/batch-check", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse batchCheckMarketing(@RequestBody @Valid GrouponActivityBatchCheckRequest request) {
        GrouponActivityAddResponse response = grouponActivitySaveProvider.batchCheckMarketing(request).getContext();
        if (CollectionUtils.isEmpty(response.getEsGrouponActivityVOList())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GrouponGoodsInfoModifyAuditStatusRequest modifyAuditStatusRequest = new GrouponGoodsInfoModifyAuditStatusRequest();
        modifyAuditStatusRequest.setAuditStatus(AuditStatus.CHECKED);
        modifyAuditStatusRequest.setGrouponActivityIds(request.getGrouponActivityIdList());
        grouponGoodsInfoSaveProvider.modifyAuditStatusByGrouponActivityIds(modifyAuditStatusRequest);
        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "批量审核通过拼团活动",
                "批量审核活动商品通过");
        //同步es
        EsGrouponActivityBatchCheckRequest checkRequest = new EsGrouponActivityBatchCheckRequest();
        checkRequest.setGrouponActivityIdList(request.getGrouponActivityIdList());
        esGrouponActivityProvider.batchCheckMarketing(checkRequest);

        List<EsGrouponActivityVO> flushList = new ArrayList<>();
        for(String activityId : checkRequest.getGrouponActivityIdList()){
            flushList.add(EsGrouponActivityVO.builder().grouponActivityId(activityId)
                    .auditStatus(com.wanmi.sbc.marketing.bean.enums.AuditStatus.CHECKED)
                    .build());
        }
        this.grouponActivitySaveProvider.flushCache(GrouponActivityFlushCacheRequest.builder().esGrouponActivityVOList(response.getEsGrouponActivityVOList()).build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 驳回拼团活动
     *
     * @param request
     * @return
     */
    @Operation(summary = "驳回拼团活动")
    @RequestMapping(value = "/refuse", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse refuseCheckMarketing(@RequestBody @Valid GrouponActivityRefuseRequest request) {
        // 验证拼团活动
        GrouponActivityByIdResponse response = grouponActivityQueryProvider.getById(
                new GrouponActivityByIdRequest(request.getGrouponActivityId())).getContext();
        if (Objects.isNull(response.getGrouponActivity())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (!com.wanmi.sbc.marketing.bean.enums.AuditStatus.WAIT_CHECK.equals(response.getGrouponActivity().getAuditStatus())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        grouponActivitySaveProvider.refuseCheckMarketing(request);
        GrouponGoodsInfoModifyAuditStatusRequest modifyAuditStatusRequest = new GrouponGoodsInfoModifyAuditStatusRequest();
        modifyAuditStatusRequest.setAuditStatus(AuditStatus.NOT_PASS);
        modifyAuditStatusRequest.setGrouponActivityIds(Collections.singletonList(request.getGrouponActivityId()));
        grouponGoodsInfoSaveProvider.modifyAuditStatusByGrouponActivityIds(modifyAuditStatusRequest);
        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "驳回拼团活动",
                "活动ID" + request.getGrouponActivityId() + "原因:" + request.getAuditReason());
        //同步es
        EsGrouponActivityRefuseRequest refuseRequest = new EsGrouponActivityRefuseRequest();
        BeanUtils.copyProperties(request,refuseRequest);
        esGrouponActivityProvider.batchRefuseMarketing(refuseRequest);
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 批量修改拼团活动精选状态
     *
     * @param request
     * @return
     */
    @Operation(summary = "批量修改拼团活动精选状态")
    @RequestMapping(value = "/batch-sticky", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse batchStickyMarketing(@RequestBody GrouponActivityBatchStickyRequest request) {
        grouponActivitySaveProvider.batchStickyMarketing(request);
        GrouponGoodsInfoModifyStickyRequest modifyStickyRequest = new GrouponGoodsInfoModifyStickyRequest();
        modifyStickyRequest.setGrouponActivityIds(request.getGrouponActivityIdList());
        modifyStickyRequest.setSticky(request.getSticky());
        grouponGoodsInfoSaveProvider.modifyStickyByGrouponActivityIds(modifyStickyRequest);
        //记录操作日志
        String stickyStr = request.getSticky() ? "是精选" : "不是精选";
        operateLogMQUtil.convertAndSend("营销", "批量修改拼团活动精选状态",
                "批量修改拼团活动精选状态为:" + stickyStr);
        //同步es
        EsGrouponActivityBatchStickyRequest req = new EsGrouponActivityBatchStickyRequest();
        BeanUtils.copyProperties(request, req);
        esGrouponActivityProvider.batchStickyMarketing(req);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "根据拼团活动ID删除拼团活动、拼团活动商品")
    @RequestMapping(value = "/delete-by-id", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse deleteById(@RequestBody GrouponActivityDelByIdRequest request) {
        grouponActivitySaveProvider.deleteById(request);
        grouponGoodsInfoSaveProvider.close(new GrouponGoodsInfoCloseRequest(request.getGrouponActivityId()));
        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "根据拼团活动ID删除拼团活动、拼团活动商品",
                "活动ID:" + request.getGrouponActivityId());
        //同步删除es
        EsGrouponActivityDelByIdRequest esGrouponActivityDelByIdRequest = new EsGrouponActivityDelByIdRequest(request.getGrouponActivityId());
        esGrouponActivityProvider.deleteById(esGrouponActivityDelByIdRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
