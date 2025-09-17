package com.wanmi.sbc.bargaingoods;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.response.store.StoreByIdResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoAndOtherByIdResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsImageVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.marketing.api.provider.bargaingoods.BargainGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bargaingoods.BargainGoodsSaveProvider;
import com.wanmi.sbc.marketing.api.request.bargaingoods.*;
import com.wanmi.sbc.marketing.bean.dto.BargainGoodsInfoForAddDTO;
import com.wanmi.sbc.marketing.bean.vo.BargainGoodsVO;
import com.wanmi.sbc.marketing.service.MarketingBaseService;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name =  "砍价商品管理API", description =  "BargainGoodsController")
@RestController
@Validated
@RequestMapping(value = "/bargaingoods")
public class BargainGoodsController {

    @Autowired
    private BargainGoodsQueryProvider bargainGoodsQueryProvider;

    @Autowired
    private BargainGoodsSaveProvider bargainGoodsSaveProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    protected CommonUtil commonUtil;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Autowired
    private MarketingBaseService marketingBaseService;

    @Operation(summary = "分页查询砍价商品")
    @PostMapping("/page")
    public BaseResponse<MicroServicePage<BargainGoodsVO>> getBargainGoodsByPage(@RequestBody @Valid BargainGoodsQueryRequest pageReq) {
        Operator operator = commonUtil.getOperator();
        if (Platform.PLATFORM != operator.getPlatform()) {
            pageReq.setStoreId(commonUtil.getStoreId());
        }
        pageReq.putSort("createTime", "desc");
        BaseResponse<MicroServicePage<BargainGoodsVO>> microServicePageBaseResponse = bargainGoodsQueryProvider.pageNew(pageReq);
        List<BargainGoodsVO> goodsVOS = microServicePageBaseResponse.getContext().getContent();
        if (CollectionUtils.isNotEmpty(goodsVOS)){
            List<GoodsInfoVO> goodsInfoVOS = goodsVOS.stream().map(BargainGoodsVO::getGoodsInfoVO).collect(Collectors.toList());
            goodsBaseService.populateMarketingGoodsStatus(goodsInfoVOS);
        }

        return microServicePageBaseResponse;
    }

    @Operation(summary = "新增砍价商品")
    @PostMapping("/add")
    @MultiSubmit
    public BaseResponse add(@RequestBody @Valid BargainGoodsActivityAddRequest addReq) {
        // 基础参数校验
        addReq.validate();
        // 填充店铺id
        addReq.setStoreId(commonUtil.getStoreId());
        marketingBaseService.mutexValidateByAdd(addReq.getStoreId(), addReq.getBeginTime(), addReq.getEndTime(),
                addReq.getGoodsInfos().stream().map(BargainGoodsInfoForAddDTO::getGoodsInfoId).filter(StringUtils::isNotBlank).collect(Collectors.toList()));
        bargainGoodsSaveProvider.add(addReq);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "修改砍价商品")
    @PutMapping("/modify")
    @MultiSubmit
    public BaseResponse modify(@RequestBody @Valid BargainGoodsModifyRequest modifyRequest) {
        modifyRequest.setStoreId(commonUtil.getStoreId());
        bargainGoodsSaveProvider.modify(modifyRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "砍价商品审核")
    @PutMapping("/check")
    public BaseResponse bargainGoodsCheck(@RequestBody @Valid BargainCheckRequest bargainCheckRequest) {
        if (AuditStatus.WAIT_CHECK == bargainCheckRequest.getAuditStatus()) {
            // 审核状态不允许为待审核
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (AuditStatus.NOT_PASS == bargainCheckRequest.getAuditStatus() && StringUtils.isBlank(bargainCheckRequest.getReasonForRejection())) {
            // 驳回时必须带上原因
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (!Objects.equals(Platform.PLATFORM, commonUtil.getOperator().getPlatform())) {
            // 仅平台端可进行审核操作
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        bargainGoodsSaveProvider.bargainGoodsCheck(bargainCheckRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "终止砍价活动")
    @RequestMapping(value = "/terminal", method = RequestMethod.PUT)
    public BaseResponse terminalActivity(@RequestBody @Valid TerminalActivityRequest terminalActivityRequest) {
        terminalActivityRequest.setStoreId(commonUtil.getStoreId());
        bargainGoodsSaveProvider.terminalActivity(terminalActivityRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "删除砍价活动")
    @DeleteMapping(value = "/delete")
    public BaseResponse deleteBargainGoods(@RequestBody @Valid TerminalActivityRequest terminalActivityRequest) {
        terminalActivityRequest.setStoreId(commonUtil.getStoreId());
        bargainGoodsSaveProvider.deleteBargainGoods(terminalActivityRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "查询砍价活动详情")
    @GetMapping(value = "/{id}")
    public BaseResponse<BargainGoodsVO> getById(@PathVariable Long id) {
        // 1. 查询砍价活动
        BargainGoodsVO bargainGoodsVO = bargainGoodsQueryProvider.getById(BargainGoodsQueryRequest.builder().bargainGoodsId(id).build()).getContext();
        if (Objects.isNull(bargainGoodsVO) || Objects.isNull(bargainGoodsVO.getGoodsInfoId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //越权校验
        commonUtil.checkStoreId(bargainGoodsVO.getStoreId());
        if (!Objects.equals(Platform.PLATFORM, commonUtil.getOperator().getPlatform())
                && !commonUtil.getStoreId().equals(bargainGoodsVO.getStoreId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
        }

        // 2. 查询关联的商品详情
        GoodsInfoAndOtherByIdResponse goodsInfoByIdResponse = goodsInfoQueryProvider.getSkuAndOtherInfoById(GoodsInfoByIdRequest.builder()
                .goodsInfoId(bargainGoodsVO.getGoodsInfoId())
                .build()).getContext();
        if (Objects.isNull(goodsInfoByIdResponse) || Objects.isNull(goodsInfoByIdResponse.getGoodsInfo())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //填充砍价商品状态
        List<GoodsInfoVO> goodsInfoVOS = new ArrayList<>();
        goodsInfoVOS.add(goodsInfoByIdResponse.getGoodsInfo());
        goodsBaseService.populateMarketingGoodsStatus(goodsInfoVOS);

        GoodsInfoVO goodsInfoVO = goodsInfoByIdResponse.getGoodsInfo();
        String goodsInfoId = goodsInfoVO.getGoodsInfoId();

        // 3. 处理规格值
        List<GoodsInfoSpecDetailRelVO> skuSpec = goodsInfoByIdResponse.getSpecDetailRelVOList().stream()
                .filter(item -> goodsInfoId.equals(item.getGoodsInfoId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(skuSpec)) {
            goodsInfoVO.setSpecText(StringUtils.join(
                    skuSpec.stream()
                            .map(GoodsInfoSpecDetailRelVO::getDetailName)
                            .collect(Collectors.toList()),
                    " "));
        }

        // 4. 处理类目
        String cateName = Optional.ofNullable(goodsInfoByIdResponse.getGoodsCateVO()).map(GoodsCateVO::getCateName).orElse(null);
        goodsInfoVO.setCateName(cateName);

        // 5. 处理图片，若sku维度的图片不存在，就取spu维度的
        if (Objects.isNull(goodsInfoVO.getGoodsInfoImg()) && CollectionUtils.isNotEmpty(goodsInfoByIdResponse.getGoodsImageVOList())) {
            String goodsImage = goodsInfoByIdResponse.getGoodsImageVOList().stream().findFirst().map(GoodsImageVO::getArtworkUrl).orElse(null);
            goodsInfoVO.setGoodsInfoImg(goodsImage);
        }

        // 6. 查询店铺信息
        StoreByIdResponse storeByIdResponse =
                storeQueryProvider.getById(StoreByIdRequest.builder().storeId(bargainGoodsVO.getStoreId()).build()).getContext();
        if (Objects.nonNull(storeByIdResponse) && Objects.nonNull(storeByIdResponse.getStoreVO())) {
            // 填充商家名称和编号
            StoreVO storeVO = storeByIdResponse.getStoreVO();
            bargainGoodsVO.setSupplierName(storeVO.getSupplierName());
            bargainGoodsVO.setCompanyCode(storeVO.getCompanyCode());
        }
        bargainGoodsVO.setGoodsInfoVO(goodsInfoVO);
        return BaseResponse.success(bargainGoodsVO);
    }

}
