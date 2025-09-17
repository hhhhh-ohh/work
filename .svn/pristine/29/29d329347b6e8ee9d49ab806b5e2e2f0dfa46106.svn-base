package com.wanmi.sbc.goods.brand.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.Pinyin4jUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsBrandProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsBrandSaveRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.brand.model.root.GoodsBrand;
import io.seata.common.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description 商品品牌导入
 * @author malianfeng
 * @date 2022/8/31 10:51
 */
@Slf4j
@Service
public class GoodsBrandExcelService {

    @Autowired private GoodsBrandService goodsBrandService;

    @Autowired private EsGoodsBrandProvider esGoodsBrandProvider;

    @Transactional(rollbackFor = Exception.class)
    public Boolean importGoodsBrand(List<GoodsBrandVO> goodsBrandList) {
        if (CollectionUtils.isEmpty(goodsBrandList)) {
            return Boolean.TRUE;
        }
        // 收集品牌名称列表
        List<String> brandNames = goodsBrandList.stream().map(GoodsBrandVO::getBrandName).collect(Collectors.toList());
        // 查询已存在的品牌名称
        Set<String> existBrandSet = new HashSet<>(goodsBrandService.findExistBrandNames(brandNames));
        // 构造实体列表
        List<GoodsBrand> saveGoodsBrands = new ArrayList<>();
        goodsBrandList.stream()
                .filter(goodsBrandVO -> !existBrandSet.contains(goodsBrandVO.getBrandName()))
                .forEach(goodsBrandVO -> {
                    GoodsBrand goodsBrand = KsBeanUtil.convert(goodsBrandVO, GoodsBrand.class);
                    goodsBrand.setDelFlag(DeleteFlag.NO);
                    goodsBrand.setCreateTime(LocalDateTime.now());
                    goodsBrand.setUpdateTime(LocalDateTime.now());
                    saveGoodsBrands.add(goodsBrand);
                });
        if (CollectionUtils.isNotEmpty(saveGoodsBrands)) {
            // 批量保存
            goodsBrandService.saveAll(saveGoodsBrands);
            // 批量同步ES
            List<GoodsBrandVO> esSaveGoodsBrandList = KsBeanUtil.convert(saveGoodsBrands, GoodsBrandVO.class);
            for (GoodsBrandVO esSaveGoodsBrandVO : esSaveGoodsBrandList) {
                String py = Pinyin4jUtil.converterToFirstSpell(esSaveGoodsBrandVO.getBrandName(),",");
                if (StringUtils.isNotBlank(py)){
                    esSaveGoodsBrandVO.setFirst(py.substring(0, 1).toUpperCase());
                }
            }
            EsGoodsBrandSaveRequest request = EsGoodsBrandSaveRequest.builder().goodsBrandVOList(esSaveGoodsBrandList).build();
            esGoodsBrandProvider.addGoodsBrandList(request);
        }
        return Boolean.TRUE;
    }
}
