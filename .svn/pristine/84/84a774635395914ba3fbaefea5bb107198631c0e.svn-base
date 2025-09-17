package com.wanmi.sbc.groupon;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.elastic.api.provider.groupon.EsGrouponActivityProvider;
import com.wanmi.sbc.elastic.api.request.groupon.EsGrouponActivityAddReqquest;
import com.wanmi.sbc.elastic.api.request.groupon.EsGrouponActivityInitRequest;
import com.wanmi.sbc.goods.api.provider.groupongoodsinfo.GrouponGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.groupongoodsinfo.GrouponGoodsInfoListRequest;
import com.wanmi.sbc.goods.bean.dto.GrouponGoodsInfoForAddDTO;
import com.wanmi.sbc.goods.bean.vo.GrouponGoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivitySaveProvider;
import com.wanmi.sbc.marketing.api.provider.grouponcate.GrouponCateQueryProvider;
import com.wanmi.sbc.marketing.api.request.grouponactivity.*;
import com.wanmi.sbc.marketing.api.request.grouponcate.GrouponCateByIdRequest;
import com.wanmi.sbc.marketing.api.response.grouponactivity.GrouponActivityAddResponse;
import com.wanmi.sbc.marketing.api.response.grouponactivity.GrouponActivityModifyResponse;
import com.wanmi.sbc.marketing.bean.enums.AuditStatus;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.EsGrouponActivityVO;
import com.wanmi.sbc.marketing.bean.vo.GrouponActivityVO;
import com.wanmi.sbc.marketing.bean.vo.GrouponCateVO;
import com.wanmi.sbc.marketing.request.MarketingMutexValidateRequest;
import com.wanmi.sbc.marketing.service.MarketingBaseService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午1:36 2019/5/16
 * @Description:
 */
@Tag(name =  "S2B的拼团活动服务", description =  "GrouponActivityController")
@RestController
@Validated
@RequestMapping("/groupon/activity")
public class StoreGrouponActivityController {

    @Autowired
    private GrouponActivitySaveProvider grouponActivitySaveProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsGrouponActivityProvider  esGrouponActivityProvider;

    @Autowired private MarketingBaseService marketingBaseService;

    @Autowired private GrouponGoodsInfoQueryProvider grouponGoodsInfoQueryProvider;

    @Autowired private GrouponActivityQueryProvider grouponActivityQueryProvider;

    @Autowired private GrouponCateQueryProvider grouponCateQueryProvider;

    @Autowired private CommonUtil commonUtil;


    /**
     * 添加拼团活动
     */
    @Operation(summary = "批量审核通过拼团活动")
    @MultiSubmit
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse add(@RequestBody @Valid GrouponActivityAddRequest request) {
        Long storeId = commonUtil.getStoreId();
        if (Objects.nonNull(request.getPreTime()) && request.getPreTime() > Constants.MAX_PRE_TIME){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080187);
        }
        //校验分类
        this.validateCate(request.getGrouponCateId());
        // 全局互斥验证
        List<String> goodsInfoIds = request.getGoodsInfos().stream()
                .map(GrouponGoodsInfoForAddDTO::getGoodsInfoId).distinct().collect(Collectors.toList());
        marketingBaseService.mutexValidateByAdd(storeId, request.getStartTime(), request.getEndTime(), goodsInfoIds);

        // 添加拼团
        GrouponActivityAddResponse response = grouponActivitySaveProvider.add(request).getContext();
        // 记录日志
        operateLogMQUtil.convertAndSend(
                "营销", "添加拼团活动", StringUtils.join(response.getGrouponActivityInfos(), "，"));
        List<EsGrouponActivityVO> activityVOList = response.getEsGrouponActivityVOList();
        //拼团信息同步es
        if(CollectionUtils.isNotEmpty(activityVOList)){
            EsGrouponActivityAddReqquest addReqquest = EsGrouponActivityAddReqquest.builder().esGrouponActivityVOList(activityVOList).build();
            esGrouponActivityProvider.add(addReqquest);
        }
        this.grouponActivitySaveProvider.flushCache(GrouponActivityFlushCacheRequest.builder().esGrouponActivityVOList(response.getEsGrouponActivityVOList()).build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改拼团活动
     */
    @Operation(summary = "修改拼团活动")
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    @GlobalTransactional
    @MultiSubmit
    public BaseResponse modify(@RequestBody @Valid GrouponActivityModifyRequest request) {

        if (Objects.nonNull(request.getPreTime()) && request.getPreTime() > Constants.MAX_PRE_TIME){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080187);
        }
        //判断当前活动是否有效
        GrouponActivityVO activity = grouponActivityQueryProvider.getSimpleById(
                new GrouponActivityByIdRequest(request.getGrouponActivityId())).getContext();
        if(LocalDateTime.now().isAfter(activity.getEndTime())){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080018);
        }
        //校验分类
        this.validateCate(request.getGrouponCateId());

        // 全局互斥验证
        List<GrouponGoodsInfoVO> grouponGoodsInfoVOList = grouponGoodsInfoQueryProvider
                .list(GrouponGoodsInfoListRequest.builder().grouponActivityId(request.getGrouponActivityId()).build())
                .getContext().getGrouponGoodsInfoVOList();
        List<String> skuIds = grouponGoodsInfoVOList.stream().map(GrouponGoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        MarketingMutexValidateRequest validateRequest = new MarketingMutexValidateRequest();
        validateRequest.setStoreId(commonUtil.getStoreId());
        validateRequest.setCrossBeginTime(request.getStartTime());
        validateRequest.setCrossEndTime(request.getEndTime());
        validateRequest.setSkuIds(skuIds);
        validateRequest.setNotSelfId(request.getGrouponActivityId());
        validateRequest.setGrouponIdFlag(true);
        marketingBaseService.mutexValidate(validateRequest);

        BaseResponse<GrouponActivityModifyResponse> response = grouponActivitySaveProvider.modify(request);

        if (Objects.isNull(response.getContext())){
            return response;
        }
        // 修改拼团活动
        String grouponActivityInfo = response.getContext().getGrouponActivityInfo();
        // 记录日志
        operateLogMQUtil.convertAndSend("营销", "修改拼团活动", grouponActivityInfo);
        EsGrouponActivityVO grouponActivityVO = response.getContext().getGrouponActivityVO();
        //拼团信息同步es
        if(Objects.nonNull(grouponActivityVO)){
            EsGrouponActivityAddReqquest addReqquest = EsGrouponActivityAddReqquest.builder()
                    .esGrouponActivityVOList(Collections.singletonList(grouponActivityVO))
                    .build();
            esGrouponActivityProvider.add(addReqquest);
        }
        //刷新缓存
        if(response.getContext().getGrouponActivityVO().getAuditStatus().equals(AuditStatus.CHECKED)){
            this.grouponActivitySaveProvider.flushCache(GrouponActivityFlushCacheRequest.builder()
                    .esGrouponActivityVOList(Collections.singletonList(response.getContext().getGrouponActivityVO()))
                    .build());
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 关闭拼团活动
     */
    @MultiSubmit
    @Operation(summary = "关闭拼团活动")
    @Parameter(name = "id", description = "拼团活动Id", required = true)
    @RequestMapping(value = "/close/{id}", method = RequestMethod.PUT)
    @GlobalTransactional
    public BaseResponse close(@PathVariable String id) {
        // 关闭拼团
        grouponActivitySaveProvider.closeActivity(GrouponActivityCloseRequest.builder().grouponActivityId(id).storeId(commonUtil.getStoreId()).build());
        // 记录日志
        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "关闭拼团活动",
                "活动ID:" + id);
        esGrouponActivityProvider.init(EsGrouponActivityInitRequest.builder().idList(Lists.newArrayList(id)).build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 校验分类是否存在
     * @param cateId 分类id
     */
    private void validateCate(String cateId){
        if (Objects.nonNull(cateId)){
            GrouponCateVO cate = grouponCateQueryProvider.getById(GrouponCateByIdRequest.builder().grouponCateId(cateId).build())
                    .getContext().getGrouponCateVO();
            if (Objects.isNull(cate)){
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080021);
            }
        }
    }
}
