package com.wanmi.sbc.marketing.preferential.service;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.request.market.MarketingIdRequest;
import com.wanmi.sbc.marketing.api.request.market.PauseModifyRequest;
import com.wanmi.sbc.marketing.api.request.preferential.PreferentialAddRequest;
import com.wanmi.sbc.marketing.api.request.preferential.PreferentialGoodsRequest;
import com.wanmi.sbc.marketing.api.request.preferential.PreferentialLevelRequest;
import com.wanmi.sbc.marketing.api.response.preferential.PreferentialDetailResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.MarketingPreferentialGoodsDetailVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingPreferentialLevelVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingVO;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.common.repository.MarketingRepository;
import com.wanmi.sbc.marketing.common.request.MarketingSaveRequest;
import com.wanmi.sbc.marketing.common.service.MarketingService;
import com.wanmi.sbc.marketing.plugin.impl.CustomerLevelPlugin;
import com.wanmi.sbc.marketing.preferential.model.root.MarketingPreferentialDetail;
import com.wanmi.sbc.marketing.preferential.model.root.MarketingPreferentialLevel;
import com.wanmi.sbc.marketing.preferential.repository.MarketingPreferentialGoodsDetailRepository;
import com.wanmi.sbc.marketing.preferential.repository.MarketingPreferentialLevelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author edz
 * @className MarketingPreferentialService
 * @description 加价购服务
 * @date 2022/11/17 18:01
 **/
@Service
public class MarketingPreferentialService {

    @Autowired
    private MarketingService marketingService;

    @Autowired
    private MarketingRepository marketingRepository;

    @Autowired
    private MarketingPreferentialGoodsDetailRepository marketingPreferentialGoodsDetailRepository;

    @Autowired
    private MarketingPreferentialLevelRepository marketingPreferentialLevelRepository;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    protected CustomerLevelPlugin customerLevelPlugin;

    /**
     * @description 新增加价购活动
     * @author  edz
     * @date: 2022/11/18 17:12
     * @param preferentialRequest
     * @return void
     */
    @Transactional
    public void add(PreferentialAddRequest preferentialRequest){
        // 保存活动主体信息
        Marketing marketing = marketingService.addMarketing(KsBeanUtil.convert(preferentialRequest,
                MarketingSaveRequest.class));

        // 保存活动阶梯信息
        List<PreferentialLevelRequest> marketingPreferentialLevels = preferentialRequest.getPreferentialLevelList();
        if (Objects.nonNull(marketingPreferentialLevels.get(0).getFullCount())){
            marketingPreferentialLevels.sort(Comparator.comparing(PreferentialLevelRequest::getFullCount));
        } else {
            marketingPreferentialLevels.sort(Comparator.comparing(PreferentialLevelRequest::getFullAmount));
        }
        marketingPreferentialLevels.forEach(request -> {
            request.setMarketingId(marketing.getMarketingId());
            MarketingPreferentialLevel marketingPreferentialLevel =
                    marketingPreferentialLevelRepository.save(KsBeanUtil.convert(request,
                    MarketingPreferentialLevel.class));
            request.getPreferentialDetailList().forEach(g -> {
                g.setMarketingId(marketing.getMarketingId());
                g.setPreferentialLevelId(marketingPreferentialLevel.getPreferentialLevelId());
            });
        });

        // 保存活动关联商品信息
        List<PreferentialGoodsRequest> preferentialGoodsRequests =
                marketingPreferentialLevels.stream()
                        .flatMap(g -> g.getPreferentialDetailList().stream()).collect(Collectors.toList());

        marketingPreferentialGoodsDetailRepository.saveAll(KsBeanUtil.convert(preferentialGoodsRequests,
                MarketingPreferentialDetail.class));
    };

    /**
     * @description 加价购活动更新
     * @author  edz
     * @date: 2022/11/21 10:52
     * @param preferentialRequest
     * @return void
     */
    @Transactional
    public void modify(PreferentialAddRequest preferentialRequest){
        Marketing marketing = marketingService.queryById(preferentialRequest.getMarketingId());
        if (marketing.getBeginTime().isBefore(LocalDateTime.now()) && BoolFlag.YES.equals(marketing.getIsPause())){
            marketingService.pauseModify(PauseModifyRequest.builder()
                    .marketingId(preferentialRequest.getMarketingId())
                    .endTime(preferentialRequest.getEndTime())
                    .updatePerson(preferentialRequest.getUpdatePerson())
                    .build());
        } else if (marketing.getBeginTime().isAfter(LocalDateTime.now())){
            marketingService.modifyMarketing(KsBeanUtil.convert(preferentialRequest, MarketingSaveRequest.class));

            // 删除档次阶梯。再保存
            marketingPreferentialLevelRepository.deleteByMarketingId(preferentialRequest.getMarketingId());
            List<PreferentialLevelRequest> marketingPreferentialLevels = preferentialRequest.getPreferentialLevelList();
            if (Objects.nonNull(marketingPreferentialLevels.get(0).getFullCount())){
                marketingPreferentialLevels.sort(Comparator.comparing(PreferentialLevelRequest::getFullCount));
            } else {
                marketingPreferentialLevels.sort(Comparator.comparing(PreferentialLevelRequest::getFullAmount));
            }
            marketingPreferentialLevels.forEach(request -> {
                request.setMarketingId(preferentialRequest.getMarketingId());
                MarketingPreferentialLevel marketingPreferentialLevel =
                        marketingPreferentialLevelRepository.save(KsBeanUtil.convert(request,
                                MarketingPreferentialLevel.class));
                request.getPreferentialDetailList().forEach(g -> {
                    g.setMarketingId(marketing.getMarketingId());
                    g.setPreferentialLevelId(marketingPreferentialLevel.getPreferentialLevelId());
                });
            });

            // 删除关联商品信息。再保存
            marketingPreferentialGoodsDetailRepository.deleteByMarketingId(preferentialRequest.getMarketingId());
            List<PreferentialGoodsRequest> preferentialGoodsRequests =
                    marketingPreferentialLevels.stream()
                            .flatMap(g -> g.getPreferentialDetailList().stream()).collect(Collectors.toList());
            marketingPreferentialGoodsDetailRepository.saveAll(KsBeanUtil.convert(preferentialGoodsRequests,
                    MarketingPreferentialDetail.class));
        } else {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080015);
        }
    }

    /**
     * @description 加价购活动详情
     * @author  edz
     * @date: 2022/11/18 16:10
     * @param marketingIdRequest
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.preferential.PreferentialDetailResponse>
     */
    public PreferentialDetailResponse getPreferentialDetail(MarketingIdRequest marketingIdRequest){
        // 1. 活动主体信息
        Marketing marketing =
                marketingRepository
                        .findById(marketingIdRequest.getMarketingId())
                        .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000003));
        if (!MarketingType.PREFERENTIAL.equals(marketing.getMarketingType())){
            // 越权
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }

        // 2. 活动阶梯信息
        List<MarketingPreferentialLevel> marketingPreferentialLevelList =
                marketingPreferentialLevelRepository.findByMarketingId(marketingIdRequest.getMarketingId());
        List<MarketingPreferentialLevelVO> marketingPreferentialLevelVOS =
                KsBeanUtil.convert(marketingPreferentialLevelList, MarketingPreferentialLevelVO.class);

        // 3. 活动阶梯商品信息
        List<MarketingPreferentialDetail> marketingPreferentialDetailList =
                marketingPreferentialGoodsDetailRepository.findByMarketingId(marketingIdRequest.getMarketingId());
        List<MarketingPreferentialGoodsDetailVO> marketingPreferentialGoodsDetailVOS =
                KsBeanUtil.convert(marketingPreferentialDetailList, MarketingPreferentialGoodsDetailVO.class);
        // <阶梯等级ID，对应等级商品>
        Map<Long, List<MarketingPreferentialGoodsDetailVO>> marketingLevelIdToGoodsRefDetailsMap =
         marketingPreferentialGoodsDetailVOS.stream()
                 .collect(Collectors.groupingBy(MarketingPreferentialGoodsDetailVO::getPreferentialLevelId));
        // 阶梯信息填充关联商品
        marketingPreferentialLevelVOS.forEach(preferentialLevel -> {
            preferentialLevel.setPreferentialDetailList(marketingLevelIdToGoodsRefDetailsMap.get(preferentialLevel.getPreferentialLevelId()));
        });

        List<String> goodsInfoIds =
                marketingPreferentialDetailList.stream().map(MarketingPreferentialDetail::getGoodsInfoId)
                        .collect(Collectors.toList());

        // 4. 商品详细信息
        List<GoodsInfoVO> goodsInfoList = goodsInfoQueryProvider.listViewByIds(
                GoodsInfoViewByIdsRequest.builder().goodsInfoIds(goodsInfoIds).isHavSpecText(Constants.yes).build()
        ).getContext().getGoodsInfos();

        return PreferentialDetailResponse.builder()
                .marketingVO(KsBeanUtil.convert(marketing, MarketingVO.class))
                .preferentialLevelList(marketingPreferentialLevelVOS)
                .goodsInfoVOList(goodsInfoList)
                .build();
    }

    /**
     * @description marketing和levelId查询
     * @author  bob
     * @date: 2022/12/4 00:43
     * @param marketingIds
     * @param levelIds
     * @return java.util.List<com.wanmi.sbc.marketing.preferential.model.root.MarketingPreferentialDetail>
     */
    public List<MarketingPreferentialDetail> listDetailByMarketingIdsAndLevelIds(List<Long> marketingIds, List<Long> levelIds){
        return marketingPreferentialGoodsDetailRepository.findByMarketingIdInAndPreferentialLevelIdIn(marketingIds, levelIds);
    }

    /**
     * @description marketing和levelId查询
     * @author  bob
     * @date: 2022/12/4 00:43
     * @param marketingIds
     * @return java.util.List<com.wanmi.sbc.marketing.preferential.model.root.MarketingPreferentialDetail>
     */
    public List<MarketingPreferentialDetail> listDetailByMarketingIds(List<Long> marketingIds){
        return marketingPreferentialGoodsDetailRepository.findByMarketingIdIn(marketingIds);
    }


    public List<MarketingPreferentialLevelVO> listLevelByLevelId(List<Long> marketingIds){
        List<MarketingPreferentialLevel> marketingPreferentialLevelList =
                marketingPreferentialLevelRepository.findByMarketingIdIn(marketingIds);
        List<MarketingPreferentialLevelVO> marketingPreferentialLevelVOS =
                KsBeanUtil.convert(marketingPreferentialLevelList, MarketingPreferentialLevelVO.class);
        return marketingPreferentialLevelVOS;
    }
}
