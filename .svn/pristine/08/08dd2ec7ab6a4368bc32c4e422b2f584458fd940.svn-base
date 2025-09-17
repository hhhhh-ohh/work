package com.wanmi.sbc.goods.provider.impl.standard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.standard.StandardGoodsProvider;
import com.wanmi.sbc.goods.api.request.standard.StandardGoodsAddRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardGoodsDeleteByGoodsIdsRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardGoodsDeleteProviderByGoodsIdsRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardGoodsModifyRequest;
import com.wanmi.sbc.goods.api.response.standard.StandardGoodsDeleteProviderGoodsIdsResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsPropertyDetailRelDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsPropertyDetailRelSaveDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyEnterType;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyType;
import com.wanmi.sbc.goods.standard.request.StandardSaveRequest;
import com.wanmi.sbc.goods.standard.service.StandardGoodsService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-08 15:27
 */
@Validated
@RestController
public class StandardGoodsController implements StandardGoodsProvider {

    @Autowired
    private StandardGoodsService standardGoodsService;

    /**
     * @param request 商品库新增 {@link StandardGoodsAddRequest}
     * @return
     */
    @Override

    public BaseResponse<String> add(@RequestBody @Valid StandardGoodsAddRequest request) {

        StandardSaveRequest standardSaveRequest = StandardConvert.convertAddRequest2saveRequest(request);
        List<GoodsPropertyDetailRelDTO> goodsDetailRel = request.getGoodsDetailRel();
        // 处理商品与属性关联关系数据
        if (CollectionUtils.isNotEmpty(goodsDetailRel)){
            // 把属性值为空的数据去除掉
            List<GoodsPropertyDetailRelSaveDTO> propertyDetailRels = this.getGoodsPropertyDetailList(goodsDetailRel);
            standardSaveRequest.setGoodsPropertyDetailRel(propertyDetailRels);
        }
        return BaseResponse.success(standardGoodsService.add(standardSaveRequest));
    }

    /**
     * @param request 商品库更新 {@link StandardGoodsModifyRequest}
     * @return
     */
    @Override

    public BaseResponse modify(@RequestBody @Valid StandardGoodsModifyRequest request) {

        StandardSaveRequest standardSaveRequest = StandardConvert.convertModifyRequest2saveRequest(request);
        List<GoodsPropertyDetailRelDTO> goodsDetailRel = request.getGoodsDetailRel();
        // 处理商品与属性关联关系数据
        if (CollectionUtils.isNotEmpty(goodsDetailRel)){
            // 把属性值为空的数据去除掉
            List<GoodsPropertyDetailRelSaveDTO> propertyDetailRels = this.getGoodsPropertyDetailList(goodsDetailRel);
            standardSaveRequest.setGoodsPropertyDetailRel(propertyDetailRels);
        }
        return BaseResponse.success(standardGoodsService.edit(standardSaveRequest));
    }

    /**
     * @param request 商品库删除 {@link StandardGoodsDeleteByGoodsIdsRequest}
     * @return
     */
    @Override

    public BaseResponse delete(@RequestBody @Valid StandardGoodsDeleteByGoodsIdsRequest request) {
        standardGoodsService.delete(request.getGoodsIds());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @param request 供货商品库删除 {@link StandardGoodsDeleteByGoodsIdsRequest}
     * @return
     */
    @Override

    public BaseResponse deleteProvider(@RequestBody @Valid StandardGoodsDeleteProviderByGoodsIdsRequest request) {
        standardGoodsService.deleteProvider(request.getGoodsIds());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @param request 供货商品库删除 并添加 删除原因 {@link StandardGoodsDeleteByGoodsIdsRequest}
     * @return
     */
    @Override
    public BaseResponse<StandardGoodsDeleteProviderGoodsIdsResponse> deleteProviderAddReason(@RequestBody @Valid StandardGoodsDeleteProviderByGoodsIdsRequest request) {
        List<String> stringList = standardGoodsService.deleteProviderAddReason(request.getGoodsIds(), request.getDeleteReason());
        return BaseResponse.success(new StandardGoodsDeleteProviderGoodsIdsResponse(stringList));
    }

    /**
     * 过滤商品属性，只取有值的属性列表
     * @param goodsDetailRel
     * @return
     */
    public List<GoodsPropertyDetailRelSaveDTO> getGoodsPropertyDetailList(List<GoodsPropertyDetailRelDTO> goodsDetailRel){
        // 把属性值为空的数据去除掉
        List<GoodsPropertyDetailRelSaveDTO> propertyDetailRels = goodsDetailRel.stream()
            .filter(rel ->
                (rel.getPropType() == GoodsPropertyEnterType.CHOOSE && CollectionUtils.isNotEmpty(rel.getDetailIdList()))
                    || (rel.getPropType() == GoodsPropertyEnterType.TEXT && StringUtils.isNoneBlank(rel.getPropValueText()))
                    || (rel.getPropType() == GoodsPropertyEnterType.DATE && Objects.nonNull(rel.getPropValueDate()))
                    || (rel.getPropType() == GoodsPropertyEnterType.PROVINCE && CollectionUtils.isNotEmpty(rel.getPropValueProvince()))
                    || (rel.getPropType() == GoodsPropertyEnterType.COUNTRY && CollectionUtils.isNotEmpty(rel.getPropValueCountry())))
            .map(this:: copyProperties).collect(Collectors.toList());
        return propertyDetailRels;
    }

    /**
     * 将list数据以逗号分隔进行存储
     *
     * @param goodsDetailRel
     * @return
     */
    private GoodsPropertyDetailRelSaveDTO copyProperties(GoodsPropertyDetailRelDTO goodsDetailRel) {
        GoodsPropertyDetailRelSaveDTO detailRel = KsBeanUtil.convert(goodsDetailRel, GoodsPropertyDetailRelSaveDTO.class);
        List<Long> goodsPropDetails = goodsDetailRel.getDetailIdList();
        if (CollectionUtils.isNotEmpty(goodsPropDetails)) {
            String detailId = StringUtils.join(goodsPropDetails, ",");
            detailRel.setDetailId(detailId);
        }

        List<String> propValueProvinceList = goodsDetailRel.getPropValueProvince();
        if (CollectionUtils.isNotEmpty(propValueProvinceList)) {
            String propValueProvince = StringUtils.join(propValueProvinceList, ",");
            detailRel.setPropValueProvince(propValueProvince);
        }

        List<Long> countryList = goodsDetailRel.getPropValueCountry();
        if (CollectionUtils.isNotEmpty(countryList)) {
            String countryId = StringUtils.join(countryList, ",");
            detailRel.setPropValueCountry(countryId);
        }
        detailRel.setDetailRelId(null);
        detailRel.setDelFlag(DeleteFlag.NO);
        detailRel.setCreateTime(LocalDateTime.now());
        detailRel.setUpdateTime(LocalDateTime.now());
        detailRel.setGoodsType(GoodsPropertyType.STANDARD_GOODS);

        return detailRel;
    }
}
