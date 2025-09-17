package com.wanmi.sbc.vas.recommend.caterelatedrecommend.mapper;

import com.wanmi.sbc.vas.api.request.recommend.caterelatedrecommend.CateRelatedRecommendDetailListRequest;
import com.wanmi.sbc.vas.api.request.recommend.caterelatedrecommend.CateRelatedRecommendInfoListRequest;
import com.wanmi.sbc.vas.bean.vo.recommend.CateRelatedRecommendDetailVO;
import com.wanmi.sbc.vas.bean.vo.recommend.CateRelatedRecommendInfoVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CateRelatedRecommendMapper {

    List<CateRelatedRecommendInfoVO> getCateRelateRecommendInfoList(CateRelatedRecommendInfoListRequest relatedRecommendInfoListRequest);

    Long getCateRelateRecommendDetailNum(CateRelatedRecommendDetailListRequest cateRelatedRecommendDetailListRequest);

    List<CateRelatedRecommendDetailVO> getCateRelateRecommendDetailList(CateRelatedRecommendDetailListRequest cateRelatedRecommendDetailListRequest);
}
