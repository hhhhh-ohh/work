package com.wanmi.sbc.electroniccoupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.elastic.api.provider.sku.EsSkuQueryProvider;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsElectronicIdRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponQueryProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.*;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.*;
import com.wanmi.sbc.marketing.bean.dto.ElectronicSendRecordNumDTO;
import com.wanmi.sbc.marketing.bean.enums.CardSaleState;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCouponVO;
import com.wanmi.sbc.marketing.bean.vo.ElectronicGoodsVO;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.common.util.CollectionUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Tag(name =  "电子卡券表管理API", description =  "ElectronicCouponController")
@RestController
@Validated
@RequestMapping(value = "/electronic/coupons")
public class ElectronicCouponController {

    @Autowired
    private ElectronicCouponQueryProvider electronicCouponQueryProvider;

    @Autowired
    private ElectronicCouponProvider electronicCouponProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EsSkuQueryProvider esSkuQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;


    @Operation(summary = "分页查询电子卡券表")
    @PostMapping("/page")
    public BaseResponse<ElectronicCouponPageResponse> getPage(@RequestBody @Valid ElectronicCouponPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.setStoreId(commonUtil.getStoreId());
        return electronicCouponQueryProvider.page(pageReq);
    }

    @Operation(summary = "查询电子卡券表")
    @PostMapping("/list")
    public BaseResponse<ElectronicCouponListResponse> list(@RequestBody @Valid ElectronicCouponListRequest listReq) {
        //查询已绑定过的卡券id
        if (StringUtils.isNotBlank(listReq.getGoodsId())) {
            GoodsElectronicIdRequest goodsElectronicIdRequest = GoodsElectronicIdRequest.builder()
                    .goodsIds(Collections.singletonList(listReq.getGoodsId())).build();
            List<Long> electronicCouponIds = goodsQueryProvider
                    .findElectronicCouponIds(goodsElectronicIdRequest).getContext().getElectronicCouponIds();
            listReq.setIncludeIds(electronicCouponIds);
        }

        listReq.setDelFlag(DeleteFlag.NO);
        listReq.setStoreId(commonUtil.getStoreId());
        listReq.setBindingFlag(Boolean.FALSE);
        listReq.putSort("createTime", "desc");
        return electronicCouponQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询电子卡券表")
    @GetMapping("/{id}")
    public BaseResponse<ElectronicCouponByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ElectronicCouponByIdRequest idReq = new ElectronicCouponByIdRequest();
        idReq.setId(id);
        return electronicCouponQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增电子卡券表")
    @PostMapping("/add")
    public BaseResponse<ElectronicCouponAddResponse> add(@RequestBody @Valid ElectronicCouponAddRequest addReq) {
        addReq.setStoreId(commonUtil.getStoreId());
        return electronicCouponProvider.add(addReq);
    }

    @Operation(summary = "修改电子卡券表")
    @PutMapping("/modify")
    public BaseResponse<ElectronicCouponModifyResponse> modify(@RequestBody @Valid ElectronicCouponModifyRequest modifyReq) {
        return electronicCouponProvider.modify(modifyReq);
    }

    @Operation(summary = "查询关联商品")
    @PostMapping("/goods")
    public BaseResponse<ElectronicGoodsPageResponse> getGoodsPage(@RequestBody @Valid ElectronicGoodsPageRequest request) {
        List<Long> couponIds = new ArrayList<>();
        if (request.getSaleType() != null) {
            List<ElectronicCouponVO> electronicCouponVOList = electronicCouponQueryProvider
                    .list(ElectronicCouponListRequest.builder()
                            .delFlag(DeleteFlag.NO)
                            .storeId(commonUtil.getStoreId())
                            .saleType(request.getSaleType()).build())
                    .getContext().getElectronicCouponVOList();
            if (CollectionUtils.isEmpty(electronicCouponVOList)) {
                return BaseResponse.success(ElectronicGoodsPageResponse.builder()
                        .electronicGoodsVOPage(new MicroServicePage<>()).build());
            }
            couponIds = electronicCouponVOList.stream().map(ElectronicCouponVO::getId).collect(Collectors.toList());
        }
        EsSkuPageRequest esSkuPageRequest = new EsSkuPageRequest();
        esSkuPageRequest.setDelFlag(DeleteFlag.NO.toValue());
        esSkuPageRequest.setStoreId(commonUtil.getStoreId());
        esSkuPageRequest.setLikeGoodsName(request.getSkuName());
        esSkuPageRequest.setElectronicCouponsIds(couponIds);
        esSkuPageRequest.setElectronicCouponsName(request.getCouponName());
        esSkuPageRequest.setGoodsType(GoodsType.ELECTRONIC_COUPON_GOODS.toValue());
        esSkuPageRequest.setPageNum(request.getPageNum());
        esSkuPageRequest.setPageSize(request.getPageSize());
        EsSkuPageResponse esSkuPageResponse = esSkuQueryProvider.page(esSkuPageRequest).getContext();

        if (CollectionUtils.isEmpty(esSkuPageResponse.getGoodsInfoPage().getContent())) {
            return BaseResponse.success(ElectronicGoodsPageResponse.builder()
                    .electronicGoodsVOPage(new MicroServicePage<>()).build());
        }

        //查询商品关联的卡券发放数量、卡券状态
        List<ElectronicSendRecordNumDTO> dtos = new ArrayList<>();
        for (GoodsInfoVO goodsInfoVO : esSkuPageResponse.getGoodsInfoPage()) {
            ElectronicSendRecordNumDTO dto = new ElectronicSendRecordNumDTO();
            dto.setCouponId(goodsInfoVO.getElectronicCouponsId());
            dto.setSkuNo(goodsInfoVO.getGoodsInfoNo());
            dtos.add(dto);
        }
        ElectronicSendRecordNumResponse sendRecordNumResponse = electronicCouponQueryProvider
                .goodsCardInfo(ElectronicSendRecordNumRequest.builder().dtos(dtos).build()).getContext();
        MicroServicePage<ElectronicGoodsVO> microServicePage = this.wrapperVo(esSkuPageResponse, sendRecordNumResponse);
        return BaseResponse.success(ElectronicGoodsPageResponse.builder().electronicGoodsVOPage(microServicePage).build());
    }

    /**
     * 数据封装
     * @author 许云鹏
     */
    private MicroServicePage<ElectronicGoodsVO> wrapperVo(EsSkuPageResponse esSkuPageResponse, ElectronicSendRecordNumResponse sendRecordNumResponse) {
        Map<String, Long> sendMap = sendRecordNumResponse.getSendMap();
        Map<Long, CardSaleState> saleStateMap = sendRecordNumResponse.getSaleStateMap();
        MicroServicePage<GoodsInfoVO> goodsInfoPage = esSkuPageResponse.getGoodsInfoPage();

        List<ElectronicGoodsVO> electronicGoodsVOList = goodsInfoPage.stream().map(goodsInfoVO -> {
            ElectronicGoodsVO electronicGoodsVO = new ElectronicGoodsVO();
            electronicGoodsVO.setBrand(goodsInfoVO.getBrandName());
            electronicGoodsVO.setCate(goodsInfoVO.getCateName());
            electronicGoodsVO.setCouponName(goodsInfoVO.getElectronicCouponsName());
            electronicGoodsVO.setGoodsType(GoodsType.fromValue(goodsInfoVO.getGoodsType()));
            electronicGoodsVO.setMarketPrice(goodsInfoVO.getMarketPrice());
            electronicGoodsVO.setSkuName(goodsInfoVO.getGoodsInfoName());
            electronicGoodsVO.setSkuNo(goodsInfoVO.getGoodsInfoNo());
            electronicGoodsVO.setSpecName(goodsInfoVO.getSpecText());
            electronicGoodsVO.setSendNum(sendMap.get(goodsInfoVO.getGoodsInfoNo()));
            electronicGoodsVO.setCardSaleState(saleStateMap.get(goodsInfoVO.getElectronicCouponsId()));
            return electronicGoodsVO;
        }).collect(Collectors.toList());

        MicroServicePage<ElectronicGoodsVO> microServicePage = new MicroServicePage<>();
        microServicePage.setNumber(goodsInfoPage.getNumber());
        microServicePage.setSize(goodsInfoPage.getSize());
        microServicePage.setTotal(goodsInfoPage.getTotal());
        microServicePage.setPageable(goodsInfoPage.getPageable());
        microServicePage.setContent(electronicGoodsVOList);

        return microServicePage;
    }

}
