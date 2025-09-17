package com.wanmi.sbc.vas.recommend.manualrecommendgoods.mapper;

import com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods.ManualRecommendGoodsInfoListRequest;
import com.wanmi.sbc.vas.bean.vo.recommend.GoodsSpecTextVO;
import com.wanmi.sbc.vas.bean.vo.recommend.ManualRecommendGoodsInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManualRecommendGoodsMapper {

    Long getManualRecommendGoodsInfoNum(ManualRecommendGoodsInfoListRequest request);

    List<ManualRecommendGoodsInfoVO> getManualRecommendGoodsInfoList(ManualRecommendGoodsInfoListRequest request);

    Long getManualRecommendChooseGoodsNum(ManualRecommendGoodsInfoListRequest request);

    List<ManualRecommendGoodsInfoVO> getManualRecommendChooseGoodsList(ManualRecommendGoodsInfoListRequest request);

    List<ManualRecommendGoodsInfoVO> getGoodsBuyPointAndMarketPrice(@Param("goodsIdList") List<String> goodsIdListt);

    List<GoodsSpecTextVO> getGoodsSpecTextList(@Param("goodsIdList") List<String> goodsIdList);
}
