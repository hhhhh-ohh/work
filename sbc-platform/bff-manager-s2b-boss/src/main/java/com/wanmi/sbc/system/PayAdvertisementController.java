package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.setting.api.provider.payadvertisement.PayAdvertisementProvider;
import com.wanmi.sbc.setting.api.provider.payadvertisement.PayAdvertisementQueryProvider;
import com.wanmi.sbc.setting.api.request.payadvertisement.*;
import com.wanmi.sbc.setting.api.response.payadvertisement.PayAdvertisementByIdResponse;
import com.wanmi.sbc.setting.api.response.payadvertisement.PayAdvertisementPageResponse;
import com.wanmi.sbc.setting.bean.vo.PayAdvertisementStoreVO;
import com.wanmi.sbc.setting.bean.vo.PayAdvertisementVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 黄昭
 * @className PayAdvertisementController
 * @description 魔方支付广告配置
 * @date 2022/4/6 9:53
 **/
@Tag(name = "PayAdvertisementController", description = "魔方支付广告配置APi")
@RestController
@Validated
@RequestMapping("/pay/advertisement")
public class PayAdvertisementController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private PayAdvertisementProvider payAdvertisementProvider;

    @Autowired
    private PayAdvertisementQueryProvider payAdvertisementQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMqUtil;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    /**
     * 新增支付广告页配置
     * @param addReq
     * @return
     */
    @Operation(summary = "新增支付广告页配置")
    @PostMapping("/add")
    public BaseResponse add(@RequestBody @Valid PayAdvertisementAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        payAdvertisementProvider.add(addReq);
        operateLogMqUtil
                .convertAndSend("设置","创建支付成功页广告","创建支付成功页广告："+addReq.getAdvertisementName());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改支付广告页配置
     * @param modifyReq
     * @return
     */
    @Operation(summary = "修改支付广告页配置")
    @PutMapping("/modify")
    public BaseResponse modify(@RequestBody @Valid PayAdvertisementModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        payAdvertisementProvider.modify(modifyReq);
        operateLogMqUtil
                .convertAndSend("设置","编辑支付成功页广告","编辑支付成功页广告："+modifyReq.getId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 分页查询支付广告页配置
     * @param pageReq
     * @return
     */
    @Operation(summary = "分页查询支付广告页配置")
    @PostMapping("/page")
    public BaseResponse<PayAdvertisementPageResponse> getPage(@RequestBody @Valid PayAdvertisementPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("createTime", "desc");
        BaseResponse<PayAdvertisementPageResponse> response = payAdvertisementQueryProvider.page(pageReq);
        List<PayAdvertisementVO> page = response.getContext()
                .getPayAdvertisementVOPage()
                .getContent();
        for (PayAdvertisementVO vo : page) {
            List<Long> levels = vo.getJoinLevelList();
            //平台客户等级
            List<CustomerLevelVO> customerLevelVOList =
                    customerLevelQueryProvider.listAllCustomerLevel().getContext().getCustomerLevelVOList();
            //平台
            if (CollectionUtils.isNotEmpty(customerLevelVOList) && CollectionUtils.isNotEmpty(levels)) {
                vo.setJoinLevelName(levels.stream().flatMap(level -> customerLevelVOList.stream()
                        .filter(customerLevelVO -> level.equals(customerLevelVO.getCustomerLevelId()))
                        .map(CustomerLevelVO::getCustomerLevelName)).collect(Collectors.joining(",")));
            }
        }
        return response;
    }

    /**
     * 根据id查询支付广告页配置
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询支付广告页配置")
    @GetMapping("/{id}")
    public BaseResponse<PayAdvertisementByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PayAdvertisementByIdRequest idReq = new PayAdvertisementByIdRequest();
        idReq.setId(id);
        BaseResponse<PayAdvertisementByIdResponse> response = payAdvertisementQueryProvider.getById(idReq);
        List<PayAdvertisementStoreVO> payAdvertisementStoreVOList = response.getContext().getStoreVOList();
        if (CollectionUtils.isNotEmpty(payAdvertisementStoreVOList)){
            List<Long> storeIds = payAdvertisementStoreVOList.stream()
                    .map(PayAdvertisementStoreVO::getStoreId)
                    .collect(Collectors.toList());
            List<StoreVO> storeVOList = storeQueryProvider
                    .listByIds(ListStoreByIdsRequest.builder().storeIds(storeIds).build())
                    .getContext()
                    .getStoreVOList();
            if (CollectionUtils.isNotEmpty(storeVOList)){
                Map<Long, String> map = storeVOList
                        .stream()
                        .collect(Collectors.toMap(StoreVO::getStoreId, StoreVO::getStoreName));
                payAdvertisementStoreVOList.forEach(v->v.setStoreName(map.get(v.getStoreId())));
            }
        }

        List<Long> levels = response.getContext().getPayAdvertisementVO().getJoinLevelList();
        //平台客户等级
        List<CustomerLevelVO> customerLevelVOList =
                customerLevelQueryProvider.listAllCustomerLevel().getContext().getCustomerLevelVOList();
        //平台
        if (CollectionUtils.isNotEmpty(customerLevelVOList) && CollectionUtils.isNotEmpty(levels)) {
            response.getContext().getPayAdvertisementVO().setJoinLevelName(levels.stream()
                    .flatMap(level -> customerLevelVOList.stream()
                    .filter(customerLevelVO -> level.equals(customerLevelVO.getCustomerLevelId()))
                    .map(CustomerLevelVO::getCustomerLevelName)).collect(Collectors.joining(",")));
        }
        return response;
    }

    /**
     * 根据id删除支付广告页配置
     * @param id
     * @return
     */
    @Operation(summary = "根据id删除支付广告页配置")
    @DeleteMapping("/delete/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        operateLogMqUtil
                .convertAndSend("设置","删除支付成功页广告","删除支付成功页广告："+id);
        PayAdvertisementByIdRequest delByIdReq = new PayAdvertisementByIdRequest();
        delByIdReq.setId(id);
        delByIdReq.setUserId(commonUtil.getOperator().getUserId());
        return payAdvertisementProvider.deleteById(delByIdReq);
    }

    /**
     * 根据id暂停支付广告页配置
     * @param id
     * @return
     */
    @Operation(summary = "根据id暂停支付广告页配置")
    @PutMapping("/pause/{id}")
    public BaseResponse pause(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        operateLogMqUtil
                .convertAndSend("设置","暂停支付成功页广告","暂停支付成功页广告："+id);
        PayAdvertisementByIdRequest delByIdReq = new PayAdvertisementByIdRequest();
        delByIdReq.setId(id);
        delByIdReq.setUserId(commonUtil.getOperator().getUserId());
        return payAdvertisementProvider.pause(delByIdReq);
    }

    /**
     * 根据id开启支付广告页配置
     * @param id
     * @return
     */
    @Operation(summary = "根据id开启支付广告页配置")
    @PutMapping("/start/{id}")
    public BaseResponse start(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        operateLogMqUtil
                .convertAndSend("设置","开启支付成功页广告","删除开启成功页广告："+id);
        PayAdvertisementByIdRequest delByIdReq = new PayAdvertisementByIdRequest();
        delByIdReq.setId(id);
        delByIdReq.setUserId(commonUtil.getOperator().getUserId());
        return payAdvertisementProvider.start(delByIdReq);
    }

    /**
     * 根据id关闭支付广告页配置
     * @param id
     * @return
     */
    @Operation(summary = "根据id关闭支付广告页配置")
    @PutMapping("/close/{id}")
    public BaseResponse close(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        operateLogMqUtil
                .convertAndSend("设置","关闭支付成功页广告","关闭支付成功页广告："+id);
        PayAdvertisementByIdRequest delByIdReq = new PayAdvertisementByIdRequest();
        delByIdReq.setId(id);
        delByIdReq.setUserId(commonUtil.getOperator().getUserId());
        return payAdvertisementProvider.close(delByIdReq);
    }
}