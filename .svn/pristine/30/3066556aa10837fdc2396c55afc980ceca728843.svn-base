package com.wanmi.sbc.elastic.goods.service;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.Pinyin4jUtil;
import com.wanmi.sbc.elastic.api.request.goods.EsCateDeleteRequest;
import com.wanmi.sbc.elastic.goods.model.root.EsCateBrand;
import com.wanmi.sbc.elastic.goods.model.root.GoodsBrandNest;
import com.wanmi.sbc.elastic.goods.model.root.GoodsCateNest;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * ES商品信息数据源操作
 * Created by daiyitian on 2017/4/21.
 */
@Service
public class EsCateBrandService {


    @Autowired
    private EsGoodsInfoElasticService esGoodsInfoElasticService;


    /**
     * 商品分类同步至es
     *
     * @param updateToEs
     */
    public void updateToEs(List<GoodsCateVO> updateToEs) {
        if (CollectionUtils.isNotEmpty(updateToEs)) {
            esGoodsInfoElasticService.updateCateName(updateToEs.get(0));
        }
    }

    /**
     * es模块-创建时迁移
     *
     * @param isDelete   是否是删除品牌，false时表示更新品牌
     * @param goodsBrand 操作品牌实体
     */
    public void updateBrandFromEs(boolean isDelete, GoodsBrandVO goodsBrand) {
        if (Objects.nonNull(goodsBrand)) {
            if (isDelete) {
                goodsBrand = new GoodsBrandVO();
            }
            esGoodsInfoElasticService.updateBrandName(goodsBrand);
        }
    }
}
