package com.wanmi.sbc.vas.recommend.recommendgoodsmanage.mapper;

import com.wanmi.sbc.vas.api.request.recommend.recommendgoodsmanage.RecommendGoodsManageListRequest;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendGoodsManageInfoVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendGoodsManageMapper {

    Long getRecommendGoodsInfoListNum(RecommendGoodsManageListRequest request);

    List<RecommendGoodsManageInfoVO> getRecommendGoodsInfoList(RecommendGoodsManageListRequest request);
}
