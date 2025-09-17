package com.wanmi.sbc.drawactivity;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.coupon.CouponActivityController;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponActivityProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponActivityInitRequest;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponInfoQueryProvider;
import com.wanmi.sbc.marketing.api.provider.drawactivity.DrawActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.drawactivity.DrawActivitySaveProvider;
import com.wanmi.sbc.marketing.api.request.coupon.*;
import com.wanmi.sbc.marketing.api.request.drawactivity.*;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityGetByIdResponse;
import com.wanmi.sbc.marketing.api.response.drawactivity.*;
import com.wanmi.sbc.marketing.bean.dto.DrawPrizeDTO;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.bean.enums.DrawPrizeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.RangeDayType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.bean.vo.DrawActivityVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author huangzhao
 */
@Tag(name =  "抽奖活动表管理API", description =  "DrawActivityController")
@RestController
@Validated
@RequestMapping(value = "/drawactivity")
public class DrawActivityController {

    @Autowired
    private DrawActivityQueryProvider drawActivityQueryProvider;

    @Autowired
    private DrawActivitySaveProvider drawActivitySaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private CouponInfoQueryProvider couponInfoQueryProvider;

    @Autowired
    private CouponActivityProvider couponActivityProvider;

    @Autowired
    private CouponActivityQueryProvider couponActivityQueryProvider;

    @Autowired
    private CouponActivityController couponActivityController;

    @Autowired
    private EsCouponActivityProvider esCouponActivityProvider;

    @Operation(summary = "分页查询抽奖活动表")
    @PostMapping("/page")
    public BaseResponse<DrawActivityPageResponse> getPage(@RequestBody @Valid DrawActivityPageRequest pageReq) {
        pageReq.putSort("id", "desc");
        return drawActivityQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询抽奖活动表")
    @PostMapping("/list")
    public BaseResponse<DrawActivityListResponse> getList(@RequestBody @Valid DrawActivityListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("id", "desc");
        return drawActivityQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询抽奖活动表")
    @GetMapping("/{id}")
    public BaseResponse<DrawActivityGetDetailsByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        DrawActivityByIdRequest idReq = new DrawActivityByIdRequest();
        idReq.setId(id);
        return drawActivityQueryProvider.getDetailsById(idReq);
    }

    @Operation(summary = "根据id查询抽奖活动修改页面回显")
    @GetMapping("/getByIdForUpdate/{id}")
    public BaseResponse<DrawActivityForUpdateResponse> getByIdForUpdate(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        DrawActivityForUpdateRequest drawActivityForUpdateRequest = new DrawActivityForUpdateRequest();
        drawActivityForUpdateRequest.setId(id);
        return drawActivityQueryProvider.getByIdForUpdate(drawActivityForUpdateRequest);
    }

    @Operation(summary = "新增抽奖活动表")
    @PostMapping("/add")
    @MultiSubmit
    @GlobalTransactional
    public BaseResponse<DrawActivityAddResponse> add(@RequestBody @Valid DrawActivityAddRequest addReq) {
        addReq.setUserId(commonUtil.getOperatorId());

        //保存抽奖活动
        BaseResponse<DrawActivityAddResponse> response = drawActivitySaveProvider.add(addReq);

        //如果奖品类型为优惠券，同步创建优惠券活动
        addCouponActivity(response.getContext().getDrawActivityVO(),addReq.getPrizeDTOList());

        return response;
    }

    /**
     * 批量创建抽奖优惠券活动
     * @param drawActivityVO
     * @param prizeDTOList
     */
    private void addCouponActivity(DrawActivityVO drawActivityVO,List<DrawPrizeDTO> prizeDTOList) {
        List<DrawPrizeDTO> couponPrizes = prizeDTOList
                .stream()
                .filter(v -> Objects.equals(DrawPrizeType.COUPON, v.getPrizeType())
                        && Objects.nonNull(v.getCouponCodeId()))
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(couponPrizes)){
            //校验优惠券有效期
            checkCoupon(drawActivityVO, couponPrizes);

            CouponActivityAddRequest request = new CouponActivityAddRequest();
            request.setActivityName(drawActivityVO.getActivityName());
            List<CouponActivityConfigSaveRequest> saveRequestList = couponPrizes.stream().map(prize -> {
                CouponActivityConfigSaveRequest couponActivityConfigSaveRequest = new CouponActivityConfigSaveRequest();
                couponActivityConfigSaveRequest.setCouponId(prize.getCouponCodeId());
                couponActivityConfigSaveRequest.setTotalCount(prize.getPrizeNum().longValue());
                return couponActivityConfigSaveRequest;
            }).collect(Collectors.toList());
            request.setCouponActivityConfigs(saveRequestList);
            request.setCouponActivityType(CouponActivityType.DRAW_COUPON);
            request.setEndTime(drawActivityVO.getEndTime());
            request.setStartTime(drawActivityVO.getStartTime());
            request.setJoinLevel(drawActivityVO.getJoinLevel());
            request.setPlatformFlag(DefaultFlag.YES);
            request.setReceiveType(DefaultFlag.NO);
            request.setDrawActivityId(drawActivityVO.getId());
            //创建优惠券活动
            couponActivityController.add(request);
        }
    }

    /**
     * 校验优惠券有效期
     * @param drawActivityVO
     * @param couponPrizes
     */
    private void checkCoupon(DrawActivityVO drawActivityVO, List<DrawPrizeDTO> couponPrizes) {
        List<String> couponCodeIds = couponPrizes.stream().map(DrawPrizeDTO::getCouponCodeId)
                .collect(Collectors.toList());
        List<CouponInfoVO> couponInfos = couponInfoQueryProvider
                .queryCouponInfos(CouponInfoQueryRequest
                        .builder()
                        .couponIds(couponCodeIds)
                        .build())
                .getContext()
                .getCouponCodeList();
        //校验优惠券过期时间是否早于活动结束时间
        List<String> errorCouponNames = new ArrayList<>();
        couponInfos.forEach(v->{
            if (Objects.equals(RangeDayType.RANGE_DAY,v.getRangeDayType())){
                if (v.getEndTime().isBefore(drawActivityVO.getEndTime())){
                    errorCouponNames.add(v.getCouponName());
                }
            }
        });
        if (CollectionUtils.isNotEmpty(errorCouponNames)){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080082,new Object[]{StringUtils.join(errorCouponNames,",")});
        }
    }

    @Operation(summary = "修改抽奖活动表")
    @PutMapping("/modify")
    @MultiSubmit
    @GlobalTransactional
    public BaseResponse<DrawActivityModifyResponse> modify(@RequestBody @Valid DrawActivityModifyRequest modifyReq) {
        modifyReq.setUserId(commonUtil.getOperatorId());
        //编辑抽奖活动
        BaseResponse<DrawActivityModifyResponse> response = drawActivitySaveProvider.modify(modifyReq);

        //编辑优惠券活动
        CouponActivityByDrawIdRequest couponActivityByDrawIdRequest = new CouponActivityByDrawIdRequest();
        couponActivityByDrawIdRequest.setDrawActivityId(modifyReq.getId());
        CouponActivityGetByIdResponse activityGetByIdResponse = couponActivityQueryProvider
                .getByDrawActivityId(couponActivityByDrawIdRequest)
                .getContext();
        if (Objects.isNull(activityGetByIdResponse)){
            //如果奖品类型为优惠券，同步创建优惠券活动
            addCouponActivity(response.getContext().getDrawActivityVO(),modifyReq.getPrizeDTOList());
        }else {
            //抽奖活动已存在，判断是否需要编辑或删除
            modifyCouponActivity(response.getContext().getDrawActivityVO(),modifyReq.getPrizeDTOList(),activityGetByIdResponse);
        }

        return response;
    }

    /**
     * 编辑优惠券活动
     * @param drawActivityVO
     * @param prizeDTOList
     * @param activityGetByIdResponse
     */
    private void modifyCouponActivity(DrawActivityVO drawActivityVO, List<DrawPrizeDTO> prizeDTOList, CouponActivityGetByIdResponse activityGetByIdResponse) {
        List<DrawPrizeDTO> couponPrizes = prizeDTOList
                .stream()
                .filter(v -> Objects.equals(DrawPrizeType.COUPON, v.getPrizeType())
                        && Objects.nonNull(v.getCouponCodeId()))
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(couponPrizes)){
            //校验优惠券有效期
            checkCoupon(drawActivityVO,prizeDTOList);

            CouponActivityModifyRequest request = new CouponActivityModifyRequest();
            request.setActivityId(activityGetByIdResponse.getActivityId());
            request.setActivityName(drawActivityVO.getActivityName());
            List<CouponActivityConfigSaveRequest> saveRequestList = couponPrizes.stream().map(prize -> {
                CouponActivityConfigSaveRequest couponActivityConfigSaveRequest = new CouponActivityConfigSaveRequest();
                couponActivityConfigSaveRequest.setCouponId(prize.getCouponCodeId());
                couponActivityConfigSaveRequest.setTotalCount(prize.getPrizeNum().longValue());
                return couponActivityConfigSaveRequest;
            }).collect(Collectors.toList());
            request.setCouponActivityConfigs(saveRequestList);
            request.setCouponActivityType(CouponActivityType.DRAW_COUPON);
            request.setEndTime(drawActivityVO.getEndTime());
            request.setStartTime(drawActivityVO.getStartTime());
            request.setJoinLevel(drawActivityVO.getJoinLevel());
            request.setPlatformFlag(DefaultFlag.YES);
            request.setReceiveType(DefaultFlag.NO);
            request.setDrawActivityId(drawActivityVO.getId());
            //编辑优惠券活动对应优惠券
            couponActivityController.modify(request);
        }else {
            //无优惠券奖品，删除优惠券活动
            CouponActivityDeleteByIdAndOperatorIdRequest request = new CouponActivityDeleteByIdAndOperatorIdRequest();
            request.setId(activityGetByIdResponse.getActivityId());
            request.setOperatorId(commonUtil.getOperatorId());
            couponActivityProvider.deleteByIdAndOperatorId(request);
        }
    }

    /**
     * 暂停抽奖活动
     *
     * @param drawActivityId
     * @return
     */
    @Operation(summary = "暂停抽奖活动")
    @RequestMapping(value = "/pause/{drawActivityId}", method = RequestMethod.PUT)
    public BaseResponse pauseDrawActivityId(@PathVariable("drawActivityId") Long drawActivityId) {
        DrawActivityByIdRequest drawActivityByIdRequest = new DrawActivityByIdRequest();
        drawActivityByIdRequest.setId(drawActivityId);
        DrawActivityVO drawActivity = drawActivityQueryProvider.getById(drawActivityByIdRequest).getContext().getDrawActivityVO();
        if (null != drawActivity) {
            if (LocalDateTime.now().isBefore(drawActivity.getStartTime())) {
                //如果现在时间在抽奖活动开始之前
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080071);
            } else if (LocalDateTime.now().isAfter(drawActivity.getEndTime())) {
                //结束时间之后
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080063);
            } else if (drawActivity.getPauseFlag() == 1) {
                //已经暂停了
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080074);
            } else {
                DrawActivityPauseByIdRequest drawActivityPauseByIdRequest = new DrawActivityPauseByIdRequest();
                drawActivityPauseByIdRequest.setId(drawActivityId);
                drawActivitySaveProvider.pauseById(drawActivityPauseByIdRequest);
                operateLogMQUtil.convertAndSend("营销", "暂停抽奖活动", "暂停抽奖活动：" + getDrawActivityName(drawActivityId));

                CouponActivityByDrawIdRequest couponActivityByDrawIdRequest = new CouponActivityByDrawIdRequest();
                couponActivityByDrawIdRequest.setDrawActivityId(drawActivityId);
                CouponActivityGetByIdResponse activityGetByIdResponse = couponActivityQueryProvider
                        .getByDrawActivityId(couponActivityByDrawIdRequest)
                        .getContext();
                if (Objects.nonNull(activityGetByIdResponse)){
                    //更新es
                    EsCouponActivityInitRequest initRequest = new EsCouponActivityInitRequest();
                    initRequest.setIdList(Lists.newArrayList(activityGetByIdResponse.getActivityId()));
                    esCouponActivityProvider.init(initRequest);

                }
                return BaseResponse.SUCCESSFUL();
            }
        } else {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080070);
        }
    }

    /**
     * 开启抽奖活动
     *
     * @param drawActivityId
     * @return
     */
    @Operation(summary = "开启抽奖活动")
    @RequestMapping(value = "/start/{drawActivityId}", method = RequestMethod.PUT)
    public BaseResponse startDrawActivityId(@PathVariable("drawActivityId") Long drawActivityId) {
        DrawActivityByIdRequest drawActivityByIdRequest = new DrawActivityByIdRequest();
        drawActivityByIdRequest.setId(drawActivityId);
        DrawActivityVO drawActivity = drawActivityQueryProvider.getById(drawActivityByIdRequest).getContext().getDrawActivityVO();
        if (null != drawActivity) {
            //如果现在时间在抽奖活动开始之前
            if (LocalDateTime.now().isBefore(drawActivity.getStartTime())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080071);
            }else if(LocalDateTime.now().isAfter(drawActivity.getEndTime())){
                //结束时间之后
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080063);
            } else if(drawActivity.getPauseFlag() ==0){
                //已经开启了
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080061);
            } else {
                DrawActivityStartByIdRequest drawActivityStartByIdRequest = new DrawActivityStartByIdRequest();
                drawActivityStartByIdRequest.setId(drawActivityId);
                drawActivitySaveProvider.startById(drawActivityStartByIdRequest);
                operateLogMQUtil.convertAndSend("营销", "开启抽奖活动", "开启抽奖活动：" + getDrawActivityName(drawActivityId));

                CouponActivityByDrawIdRequest couponActivityByDrawIdRequest = new CouponActivityByDrawIdRequest();
                couponActivityByDrawIdRequest.setDrawActivityId(drawActivityId);
                CouponActivityGetByIdResponse activityGetByIdResponse = couponActivityQueryProvider
                        .getByDrawActivityId(couponActivityByDrawIdRequest)
                        .getContext();
                if (Objects.nonNull(activityGetByIdResponse)){
                    //更新es
                    EsCouponActivityInitRequest initRequest = new EsCouponActivityInitRequest();
                    initRequest.setIdList(Lists.newArrayList(activityGetByIdResponse.getActivityId()));
                    esCouponActivityProvider.init(initRequest);

                }
                return BaseResponse.SUCCESSFUL();
            }
        } else {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080070);
        }
    }

    @Operation(summary = "根据id删除抽奖活动表")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        DrawActivityDelByIdRequest delByIdReq = new DrawActivityDelByIdRequest();
        delByIdReq.setId(id);
        drawActivitySaveProvider.deleteById(delByIdReq);

        //删除优惠券活动
        CouponActivityByDrawIdRequest couponActivityByDrawIdRequest = new CouponActivityByDrawIdRequest();
        couponActivityByDrawIdRequest.setDrawActivityId(id);
        CouponActivityGetByIdResponse activityGetByIdResponse = couponActivityQueryProvider
                .getByDrawActivityId(couponActivityByDrawIdRequest)
                .getContext();
        if (Objects.nonNull(activityGetByIdResponse)){
            CouponActivityDeleteByIdAndOperatorIdRequest request = new CouponActivityDeleteByIdAndOperatorIdRequest();
            request.setId(activityGetByIdResponse.getActivityId());
            request.setOperatorId(commonUtil.getOperatorId());
            couponActivityProvider.deleteByIdAndOperatorId(request);
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "根据idList批量删除抽奖活动表")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid DrawActivityDelByIdListRequest delByIdListReq) {
        drawActivitySaveProvider.deleteByIdList(delByIdListReq);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 关闭抽奖活动
     *
     * @param drawActivityId
     * @return
     */
    @Operation(summary = "关闭抽奖活动")
    @RequestMapping(value = "/close/{drawActivityId}", method = RequestMethod.PUT)
    public BaseResponse closeDrawActivityId(@PathVariable("drawActivityId") Long drawActivityId) {
        DrawActivityByIdRequest drawActivityByIdRequest = new DrawActivityByIdRequest();
        drawActivityByIdRequest.setId(drawActivityId);
        DrawActivityVO drawActivity = drawActivityQueryProvider.getById(drawActivityByIdRequest).getContext().getDrawActivityVO();
        if (null != drawActivity) {
            //如果现在时间在抽奖活动开始之前
            if (LocalDateTime.now().isBefore(drawActivity.getStartTime())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080071);
            }else if(LocalDateTime.now().isAfter(drawActivity.getEndTime())){
                //结束时间之后
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080063);
            } else {
                drawActivitySaveProvider.closeById(drawActivityByIdRequest);
                //刷新优惠券ES
                CouponActivityByDrawIdRequest couponActivityByDrawIdRequest = new CouponActivityByDrawIdRequest();
                couponActivityByDrawIdRequest.setDrawActivityId(drawActivityId);
                CouponActivityGetByIdResponse activityGetByIdResponse = couponActivityQueryProvider
                        .getByDrawActivityId(couponActivityByDrawIdRequest)
                        .getContext();
                if (Objects.nonNull(activityGetByIdResponse)){
                    //更新es
                    EsCouponActivityInitRequest initRequest = new EsCouponActivityInitRequest();
                    initRequest.setIdList(Lists.newArrayList(activityGetByIdResponse.getActivityId()));
                    esCouponActivityProvider.init(initRequest);

                }
                operateLogMQUtil.convertAndSend("营销", "关闭抽奖活动", "关闭抽奖活动：" + getDrawActivityName(drawActivityId));
                return BaseResponse.SUCCESSFUL();
            }
        } else {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080070);
        }
    }

    /**
     * 获取抽奖活动名称
     *
     * @param drawActivityId
     * @return
     */
    private String getDrawActivityName(long drawActivityId) {
        DrawActivityByIdRequest request = new DrawActivityByIdRequest();
        request.setId(drawActivityId);
        DrawActivityByIdResponse drawActivity = drawActivityQueryProvider.getById(request).getContext();
        return Objects.nonNull(drawActivity) ? drawActivity.getDrawActivityVO().getActivityName() : " ";
    }
}
