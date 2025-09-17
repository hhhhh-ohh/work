package com.wanmi.sbc.goods.provider.impl.suppliercommissiongoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.goods.api.provider.suppliercommissiongoods.SupplierCommissionGoodsProvider;
import com.wanmi.sbc.goods.api.request.suppliercommissiongoods.SupplierCommissionGoodsQueryRequest;
import com.wanmi.sbc.goods.api.request.suppliercommissiongoods.SupplierCommissionGoodsSynRequest;
import com.wanmi.sbc.goods.api.response.suppliercommissiongoods.SupplierCommissionGoodsPageResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoSaveDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsSaveDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsEditType;
import com.wanmi.sbc.goods.bean.vo.StandardGoodsVO;
import com.wanmi.sbc.goods.bean.vo.SupplierCommissionGoodsVO;
import com.wanmi.sbc.goods.brand.entity.ContractBrandBase;
import com.wanmi.sbc.goods.brand.service.ContractBrandService;
import com.wanmi.sbc.goods.info.request.GoodsInfoQueryRequest;
import com.wanmi.sbc.goods.info.service.GoodsInfoService;
import com.wanmi.sbc.goods.info.service.GoodsService;
import com.wanmi.sbc.goods.providergoodsedit.model.root.ProviderGoodsEditDetail;
import com.wanmi.sbc.goods.providergoodsedit.service.ProviderGoodsEditDetailService;
import com.wanmi.sbc.goods.providergoodsedit.service.ProviderGoodsSynService;
import com.wanmi.sbc.goods.standard.service.StandardGoodsService;
import com.wanmi.sbc.goods.suppliercommissiongoods.model.root.SupplierCommissionGood;
import com.wanmi.sbc.goods.suppliercommissiongoods.service.SupplierCommissionGoodService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wur
 * @className SupplierCommissionGoodsController
 * @description 代销商品变更服务
 * @date 2021/9/14 19:43
 **/
@RestController
@Validated
public class SupplierCommissionGoodsController implements SupplierCommissionGoodsProvider {

    @Autowired private SupplierCommissionGoodService supplierCommissionGoodsService;

    @Autowired private ProviderGoodsEditDetailService providerGoodsEditDetailService;

    @Autowired private StandardGoodsService standardGoodsService;

    @Autowired private GoodsService goodsService;

    @Autowired private GoodsInfoService goodsInfoService;

    @Autowired private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired private ProviderGoodsSynService providerGoodsSynService;

    @Autowired private ContractBrandService contractBrandService;


    @Override
    public BaseResponse<SupplierCommissionGoodsPageResponse> page(@Valid SupplierCommissionGoodsQueryRequest queryRequest) {
        //分页查询有操作记录的代销商品
        Page<SupplierCommissionGood> supplierCommissionGoodsPage = supplierCommissionGoodsService.page(queryRequest);
        List<SupplierCommissionGood> commissionGoodsList = supplierCommissionGoodsPage.getContent();
        if (CollectionUtils.isEmpty(commissionGoodsList)) {
            return BaseResponse.SUCCESSFUL();
        }
        //根据代销商品查询操作日志
        List<String> providerGoodsIdList = commissionGoodsList.stream().map(SupplierCommissionGood :: getProviderGoodsId).collect(Collectors.toList());
        List<ProviderGoodsEditDetail> goodsEditDetailList = providerGoodsEditDetailService.queryByGoodsIdList(providerGoodsIdList);
        Map<String, List<ProviderGoodsEditDetail>> goodsEditMap = goodsEditDetailList.stream().collect(Collectors.groupingBy(ProviderGoodsEditDetail::getGoodsId));

        //查询代销商品信息
        List<StandardGoodsVO> standardGoodsList = standardGoodsService.queryByProvideGoodsIdList(providerGoodsIdList);
        if (CollectionUtils.isEmpty(standardGoodsList)) {
            return BaseResponse.SUCCESSFUL();
        }
        Map<String, StandardGoodsVO> standardGoodsMap = standardGoodsList.stream().collect(Collectors.toMap(StandardGoodsVO::getProviderGoodsId, standardGoods -> standardGoods));
        //封装返回数据
        Page<SupplierCommissionGoodsVO> newPage = supplierCommissionGoodsPage.map(entity -> supplierCommissionGoodsService.wrapperVo(entity));
        newPage.getContent().forEach(supplierCommissionGoodsVO -> {
            if (goodsEditMap.containsKey(supplierCommissionGoodsVO.getProviderGoodsId())){
                List<ProviderGoodsEditDetail> goodsEditDetails = goodsEditMap.get(supplierCommissionGoodsVO.getProviderGoodsId());
                supplierCommissionGoodsVO.setProviderGoodsEditVOList(goodsEditDetails.stream().map(goodsEditDetail->providerGoodsEditDetailService.wrapperVo(goodsEditDetail)).collect(Collectors.toList()));
            }
            if (standardGoodsMap.containsKey(supplierCommissionGoodsVO.getProviderGoodsId())) {
                StandardGoodsVO standardGoods = standardGoodsMap.get(supplierCommissionGoodsVO.getProviderGoodsId());
                supplierCommissionGoodsVO.setProviderGoodsName(standardGoods.getGoodsName());
                supplierCommissionGoodsVO.setProviderGoodsImg(standardGoods.getGoodsImg());
                supplierCommissionGoodsVO.setProviderGoodsNo(standardGoods.getGoodsNo());
                supplierCommissionGoodsVO.setProviderName(standardGoods.getProviderName());
            }
        });
        MicroServicePage<SupplierCommissionGoodsVO> microPage = new MicroServicePage<>(newPage, queryRequest.getPageable());
        return BaseResponse.success(SupplierCommissionGoodsPageResponse.builder().supplierCommissionGoodsPage(microPage).build());
    }


    @Override
    public BaseResponse synGoodsInfo(@Valid SupplierCommissionGoodsSynRequest synRequest) {
        // 根据ID批量查询
        List<SupplierCommissionGood> commissionGoodsList = supplierCommissionGoodsService.findByIdAndUpdateFlag(synRequest.getIdList(), synRequest.getBaseStoreId(), DefaultFlag.YES);
        if (CollectionUtils.isEmpty(commissionGoodsList)) {
            return BaseResponse.SUCCESSFUL();
        }

        // 查询有商品信息变更的供应商商品
        List<String> providerGoodsId = commissionGoodsList.stream().map(SupplierCommissionGood :: getProviderGoodsId).collect(Collectors.toList());
        List<ProviderGoodsEditDetail> goodsEditDetailList = providerGoodsEditDetailService.queryByGoodsIdAndEnditType(providerGoodsId, GoodsEditType.INFO_EDIT);
        if (CollectionUtils.isEmpty(goodsEditDetailList)) {
            return BaseResponse.SUCCESSFUL();
        }
        // 查询供应商商品信息和商家商品信息
        List<GoodsSaveDTO> providerGoodsList = KsBeanUtil.convertList(goodsService.findAllByGoodsId(providerGoodsId), GoodsSaveDTO.class);
        List<String> goodsIdList = commissionGoodsList.stream().map(SupplierCommissionGood :: getGoodsId).collect(Collectors.toList());
        List<GoodsSaveDTO> goodsList = KsBeanUtil.convertList(goodsService.findAllByGoodsId(goodsIdList), GoodsSaveDTO.class);
        if (CollectionUtils.isEmpty(goodsList)) {
            return BaseResponse.SUCCESSFUL();
        }
        goodsIdList = goodsList.stream().map(GoodsSaveDTO::getGoodsId).collect(Collectors.toList());

        //查询商家GoodsInfo信息
        List<GoodsInfoSaveDTO> goodsInfoList = KsBeanUtil.convertList(goodsInfoService.findByParams(GoodsInfoQueryRequest.builder().goodsIds(goodsIdList).build()), GoodsInfoSaveDTO.class);
        if (CollectionUtils.isEmpty(goodsInfoList)) {
            return BaseResponse.SUCCESSFUL();
        }

        //查询供应商GoodsInfo信息
        List<String> providerGoodsInfoIdList = goodsInfoList.stream().filter(g -> Objects.nonNull(g.getProviderGoodsInfoId())).map(GoodsInfoSaveDTO ::getProviderGoodsInfoId).collect(Collectors.toList());
        List<GoodsInfoSaveDTO> providerGoodsInfoList = KsBeanUtil.convertList(goodsInfoService.findByIds(providerGoodsInfoIdList), GoodsInfoSaveDTO.class);
        Map<String, GoodsSaveDTO> providerGoodsMap = providerGoodsList.stream().collect(Collectors.toMap(GoodsSaveDTO::getGoodsId, g->g));
        Map<String, List<GoodsInfoSaveDTO>> providerGoodsInfoMap = providerGoodsInfoList.stream().collect(Collectors.groupingBy(GoodsInfoSaveDTO::getGoodsId));
        Map<String, List<GoodsInfoSaveDTO>> goodsInfoMap = goodsInfoList.stream().collect(Collectors.groupingBy(GoodsInfoSaveDTO::getGoodsId));

        List<Long> brandIds =
                providerGoodsList.stream()
                        .filter(goodsSaveDTO -> Objects.nonNull(goodsSaveDTO.getBrandId()))
                        .map(GoodsSaveDTO::getBrandId)
                        .collect(Collectors.toList());
        List<Long> brandIdList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(brandIds)) {
            List<ContractBrandBase> contractBrandList = contractBrandService.findAllByStoreIdAndBrandIdIn(synRequest.getBaseStoreId(), brandIds);
            if (CollectionUtils.isNotEmpty(contractBrandList)) {
                brandIdList = contractBrandList.stream().map(ContractBrandBase :: getBrandId).collect(Collectors.toList());
            }
        }

        //同步操作
        for (GoodsSaveDTO goods : goodsList) {
            if (!providerGoodsMap.containsKey(goods.getProviderGoodsId())) {
                continue;
            }
            GoodsSaveDTO providerGoods = providerGoodsMap.get(goods.getProviderGoodsId());
            if (!providerGoodsInfoMap.containsKey(providerGoods.getGoodsId())
                    || !goodsInfoMap.containsKey(goods.getGoodsId())) {
                continue;
            }
            //获取供应商Good
            boolean storeHasBrand = false;
            if (Objects.isNull(providerGoods.getBrandId()) || brandIdList.contains(providerGoods.getBrandId())) {
                storeHasBrand = true;
            }
            providerGoodsSynService.synGoodsInfo(providerGoods, goods, providerGoodsInfoMap.get(providerGoods.getGoodsId()), goodsInfoMap.get(goods.getGoodsId()), storeHasBrand);
        }
        // 更新同步状态
        supplierCommissionGoodsService.updateSynStatusById(commissionGoodsList.stream().map(SupplierCommissionGood :: getId).collect(Collectors.toList()), DefaultFlag.YES);
        esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().skuIds(goodsInfoList.stream().map(GoodsInfoSaveDTO::getGoodsInfoId).collect(Collectors.toList())).build());
        return BaseResponse.SUCCESSFUL();
    }
}