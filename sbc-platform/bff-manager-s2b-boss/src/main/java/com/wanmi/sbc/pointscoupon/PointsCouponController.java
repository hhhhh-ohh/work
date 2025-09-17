package com.wanmi.sbc.pointscoupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.marketing.api.provider.pointscoupon.PointsCouponQueryProvider;
import com.wanmi.sbc.marketing.api.provider.pointscoupon.PointsCouponSaveProvider;
import com.wanmi.sbc.marketing.api.request.pointscoupon.*;
import com.wanmi.sbc.marketing.api.response.pointscoupon.PointsCouponByIdResponse;
import com.wanmi.sbc.marketing.api.response.pointscoupon.PointsCouponModifyResponse;
import com.wanmi.sbc.marketing.api.response.pointscoupon.PointsCouponPageResponse;
import com.wanmi.sbc.marketing.bean.vo.PointsCouponVO;
import com.wanmi.sbc.mq.producer.ManagerBaseProducerService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Tag(name =  "积分兑换券表管理API", description =  "PointsCouponController")
@RestController
@Validated
@RequestMapping(value = "/pointscoupon")
public class PointsCouponController {

    @Autowired
    private PointsCouponQueryProvider pointsCouponQueryProvider;

    @Autowired
    private PointsCouponSaveProvider pointsCouponSaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private ManagerBaseProducerService managerBaseProducerService;

    @Operation(summary = "分页查询积分兑换券表")
    @PostMapping("/page")
    public BaseResponse<PointsCouponPageResponse> getPage(@RequestBody @Valid PointsCouponPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("pointsCouponId", "desc");
        return pointsCouponQueryProvider.page(pageReq);
    }

    @Operation(summary = "根据id查询积分兑换券表")
    @Parameter(name = "pointsCouponId", description = "积分兑换券id", required = true)
    @GetMapping("/{pointsCouponId}")
    public BaseResponse<PointsCouponByIdResponse> getById(@PathVariable Long pointsCouponId) {
        if (pointsCouponId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PointsCouponByIdRequest idReq = new PointsCouponByIdRequest();
        idReq.setPointsCouponId(pointsCouponId);
        return pointsCouponQueryProvider.getById(idReq);
    }

    @Operation(summary = "批量新增积分兑换券")
    @PostMapping("/batchAdd")
    public BaseResponse batchAdd(@RequestBody @Valid PointsCouponAddListRequest request) {
        List<PointsCouponAddRequest> addRequestList = request.getPointsCouponAddRequestList();
        if (addRequestList.size() == 0) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010159);
        }
        addRequestList.forEach(addRequest -> {
            addRequest.setBeginTime(request.getBeginTime());
            addRequest.setEndTime(request.getEndTime());
            addRequest.setStatus(EnableStatus.ENABLE);
            addRequest.setExchangeCount((long) 0);
            addRequest.setSellOutFlag(BoolFlag.NO);
            addRequest.setDelFlag(DeleteFlag.NO);
            addRequest.setCreatePerson(commonUtil.getOperatorId());
            addRequest.setCreateTime(LocalDateTime.now());
        });
        operateLogMQUtil.convertAndSend("营销", "积分商城", "添加积分商品");
        List<String> activityIdList = pointsCouponSaveProvider.batchAdd(request).getContext().getActivityIdList();
        if (CollectionUtils.isNotEmpty(activityIdList)){
            managerBaseProducerService.sendMQForAddCouponActivity(activityIdList);
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "修改积分兑换券表")
    @PutMapping("/modify")
    public BaseResponse<PointsCouponModifyResponse> modify(@RequestBody @Valid PointsCouponModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        BaseResponse<PointsCouponModifyResponse> modifyResponseBaseResponse = pointsCouponSaveProvider.modify(modifyReq);
        PointsCouponVO pointsCouponVO = modifyResponseBaseResponse.getContext().getPointsCouponVO();
        List<String> activityIdList = new ArrayList<>();
        activityIdList.add(pointsCouponVO.getActivityId());
        if (CollectionUtils.isNotEmpty(activityIdList)){
            managerBaseProducerService.sendMQForAddCouponActivity(activityIdList);
        }
        return modifyResponseBaseResponse;
    }

    @Operation(summary = "启用停用积分兑换券")
    @PutMapping("/modifyStatus")
    public BaseResponse modifyStatus(@RequestBody @Valid PointsCouponSwitchRequest request) {
        request.setUpdatePerson(commonUtil.getOperatorId());
        request.setUpdateTime(LocalDateTime.now());
        return pointsCouponSaveProvider.modifyStatus(request);
    }

    @Operation(summary = "根据id删除积分兑换券表")
    @Parameter(name = "pointsCouponId", description = "积分兑换券id", required = true)
    @DeleteMapping("/{pointsCouponId}")
    public BaseResponse deleteById(@PathVariable Long pointsCouponId) {
        if (pointsCouponId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 删除积分兑换券
        PointsCouponDelByIdRequest delByIdReq = new PointsCouponDelByIdRequest();
        delByIdReq.setPointsCouponId(pointsCouponId);
        delByIdReq.setOperatorId(commonUtil.getOperatorId());
        return pointsCouponSaveProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "关闭积分兑换券")
    @PutMapping("/close/{id}")
    public BaseResponse close(@PathVariable Long id) {
        if(commonUtil.getOperator().getPlatform() != Platform.PLATFORM) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return pointsCouponSaveProvider.close(PointsCouponCloseRequest.builder().pointsCouponId(id).operateId(commonUtil.getOperatorId()).build());
    }

}
