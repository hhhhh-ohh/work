package com.wanmi.sbc.bargainGoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.service.detail.GoodsDetailInterface;
import com.wanmi.sbc.marketing.api.provider.bargaingoods.BargainGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.request.bargaingoods.BargainGoodsQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.BargainGoodsVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name =  "砍价商品管理API", description =  "BargainGoodsController")
@RestController
@Validated
@RequestMapping(value = "/bargaingoods")
public class BargainGoodsController {

    @Autowired
    private BargainGoodsQueryProvider bargainGoodsQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Resource(name = "bargainGoodsInfoDetailService")
    private GoodsDetailInterface<BargainGoodsVO> bargainGoodsInfoDetailService;


    @Operation(summary = "分页查询砍价商品")
    @PostMapping("/page")
    public BaseResponse<MicroServicePage<BargainGoodsVO>> getPage(@RequestBody BargainGoodsQueryRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.setAuditStatus(AuditStatus.CHECKED);
        pageReq.setGoodsStatus(DeleteFlag.YES);
        pageReq.setStoped(false);
        pageReq.setLeaveStock(true);
        LocalDateTime now = LocalDateTime.now();
        pageReq.setBeginTimeEnd(now);
        pageReq.setEndTimeBegin(now);
        pageReq.putSort("createTime", "desc");
        pageReq.setUserId(commonUtil.getOperatorId());
        //查询有效的店铺信息
        ListStoreRequest queryRequest = new ListStoreRequest();
        queryRequest.setAuditState(CheckState.CHECKED);
        queryRequest.setStoreState(StoreState.OPENING);
        queryRequest.setGteContractStartDate(now);
        queryRequest.setLteContractEndDate(now);
        queryRequest.setDelFlag(DeleteFlag.NO);
        queryRequest.setNotShowStoreType(StoreType.PROVIDER);
        List<StoreVO> storeList = storeQueryProvider.listStore(queryRequest).getContext().getStoreVOList();
        if (CollectionUtils.isEmpty(storeList)) {
            MicroServicePage<BargainGoodsVO> page = new MicroServicePage<>();
            page.setSize(pageReq.getPageSize());
            page.setNumber(pageReq.getPageNum());
            page.setTotal(0);
            return BaseResponse.success(page);
        }
        List<Long> storeIdList = storeList.stream().map(StoreVO ::getStoreId).collect(Collectors.toList());
        pageReq.setStoreIds(storeIdList);
        return bargainGoodsQueryProvider.pageForCustomer(pageReq);
    }

    @Operation(summary = "分页查询砍价商品 - 未登录")
    @PostMapping("/unlogin/page")
    public BaseResponse<MicroServicePage<BargainGoodsVO>> unloginPage(@RequestBody BargainGoodsQueryRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.setAuditStatus(AuditStatus.CHECKED);
        pageReq.setGoodsStatus(DeleteFlag.YES);
        pageReq.setStoped(false);
        pageReq.setLeaveStock(true);
        LocalDateTime now = LocalDateTime.now();
        pageReq.setBeginTimeEnd(now);
        pageReq.setEndTimeBegin(now);
        pageReq.putSort("createTime", "desc");
        //查询有效的店铺信息
        ListStoreRequest queryRequest = new ListStoreRequest();
        queryRequest.setAuditState(CheckState.CHECKED);
        queryRequest.setStoreState(StoreState.OPENING);
        queryRequest.setGteContractStartDate(now);
        queryRequest.setLteContractEndDate(now);
        queryRequest.setDelFlag(DeleteFlag.NO);
        queryRequest.setNotShowStoreType(StoreType.PROVIDER);
        List<StoreVO> storeList = storeQueryProvider.listStore(queryRequest).getContext().getStoreVOList();
        if (CollectionUtils.isEmpty(storeList)) {
            MicroServicePage<BargainGoodsVO> page = new MicroServicePage<>();
            page.setSize(pageReq.getPageSize());
            page.setNumber(pageReq.getPageNum());
            page.setTotal(0);
            return BaseResponse.success(page);
        }
        List<Long> storeIdList = storeList.stream().map(StoreVO ::getStoreId).collect(Collectors.toList());
        pageReq.setStoreIds(storeIdList);
        return bargainGoodsQueryProvider.pageForCustomer(pageReq);
    }

    @Operation(summary = "查询砍价商品")
    @PostMapping("/list")
    public BaseResponse<List<BargainGoodsVO>> list(@RequestBody BargainGoodsQueryRequest pageReq) {
        if (StringUtils.isBlank(pageReq.getUserId())) {
            pageReq.setUserId(commonUtil.getOperatorId());
        }
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.setAuditStatus(AuditStatus.CHECKED);
        pageReq.setGoodsStatus(DeleteFlag.YES);
        pageReq.setStoped(false);
        pageReq.setLeaveStock(true);
        LocalDateTime now = LocalDateTime.now();
        pageReq.setBeginTimeEnd(now);
        pageReq.setEndTimeBegin(now);
        pageReq.putSort("createTime", "desc");
        //查询有效的店铺信息
        ListStoreRequest queryRequest = new ListStoreRequest();
        queryRequest.setAuditState(CheckState.CHECKED);
        queryRequest.setStoreState(StoreState.OPENING);
        queryRequest.setGteContractStartDate(now);
        queryRequest.setLteContractEndDate(now);
        queryRequest.setDelFlag(DeleteFlag.NO);
        queryRequest.setNotShowStoreType(StoreType.PROVIDER);
        List<StoreVO> storeList = storeQueryProvider.listStore(queryRequest).getContext().getStoreVOList();
        if (CollectionUtils.isEmpty(storeList)) {
            return BaseResponse.success(Collections.EMPTY_LIST);
        }
        List<Long> storeIdList = storeList.stream().map(StoreVO ::getStoreId).collect(Collectors.toList());
        pageReq.setStoreIds(storeIdList);
        return bargainGoodsQueryProvider.listForCustomer(pageReq);
    }

    @Operation(summary = "根据id查询砍价商品")
    @GetMapping("/{bargainGoodsId}")
    public BaseResponse<BargainGoodsVO> getById(@PathVariable String bargainGoodsId) {
        if (StringUtils.isBlank(bargainGoodsId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        BargainGoodsVO response = bargainGoodsInfoDetailService.getDetail(bargainGoodsId, null);
        return BaseResponse.success(response);
    }

    @Operation(summary = "根据id查询砍价商品 - 未登录")
    @GetMapping("/unlogin/{bargainGoodsId}")
    public BaseResponse<BargainGoodsVO> unloginById(@PathVariable String bargainGoodsId) {
        if (StringUtils.isBlank(bargainGoodsId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        BargainGoodsVO response = bargainGoodsInfoDetailService.getDetail(bargainGoodsId, null);
        return BaseResponse.success(response);
    }

}
