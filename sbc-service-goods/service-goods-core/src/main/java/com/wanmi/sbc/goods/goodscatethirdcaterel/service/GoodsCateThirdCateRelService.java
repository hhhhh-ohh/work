package com.wanmi.sbc.goods.goodscatethirdcaterel.service;

import com.wanmi.sbc.common.enums.AuditStatus;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.request.goodscatethirdcaterel.DeleteWechatCateMapRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsByThirdCateRequest;
import com.wanmi.sbc.goods.api.request.goodscatethirdcaterel.GoodsCateThirdCateRelQueryRequest;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatcateaudit.WechatCateAuditQueryRequest;
import com.wanmi.sbc.goods.api.response.goodscatethirdcaterel.GoodsCateThirdCateRelAddResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsCateThirdCateRelVO;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.service.GoodsCateService;
import com.wanmi.sbc.goods.goodscatethirdcaterel.model.root.GoodsCateThirdCateRel;
import com.wanmi.sbc.goods.goodscatethirdcaterel.repository.GoodsCateThirdCateRelRepository;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRepository;
import com.wanmi.sbc.goods.thirdgoodscate.model.root.ThirdGoodsCate;
import com.wanmi.sbc.goods.thirdgoodscate.service.ThirdGoodsCateService;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.goods.wechatvideocate.wechatcateaudit.repository.WechatCateAuditRepository;
import com.wanmi.sbc.goods.wechatvideocate.wechatcateaudit.service.WechatCateAuditService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>平台类目和第三方平台类目映射业务逻辑</p>
 *
 * @author
 * @date 2020-08-18 19:51:55
 */
@Service
public class GoodsCateThirdCateRelService {
    @Autowired
    private GoodsCateThirdCateRelRepository goodsCateThirdCateRelRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;

    @Autowired
    private StandardGoodsRepository standardGoodsRepository;

    @Autowired
    private GoodsCateService goodsCateService;

    @Autowired
    private MqSendProvider mqSendProvider;

    @Autowired
    private WechatCateAuditRepository wechatCateAuditRepository;

    @Autowired
    private WechatCateAuditService wechatCateAuditService;

    @Autowired
    private ThirdGoodsCateService thirdGoodsCateService;

    /**
     * 修改平台类目和第三方平台类目映射
     *
     * @author
     */
    @Transactional
    public GoodsCateThirdCateRel modify(GoodsCateThirdCateRel entity) {
        goodsCateThirdCateRelRepository.save(entity);
        return entity;
    }

    /**
     * 单个删除平台类目和第三方平台类目映射
     *
     * @author
     */
    @Transactional
    public void deleteById(GoodsCateThirdCateRel entity) {
        goodsCateThirdCateRelRepository.save(entity);
    }

    /**
     * 批量删除平台类目和第三方平台类目映射
     *
     * @author
     */
    @Transactional
    public void deleteByIdList(List<GoodsCateThirdCateRel> infos) {
        goodsCateThirdCateRelRepository.saveAll(infos);
    }

    /**
     * 单个查询平台类目和第三方平台类目映射
     *
     * @author
     */
    public GoodsCateThirdCateRel getOne(Long id) {
        return goodsCateThirdCateRelRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "平台类目和第三方平台类目映射不存在"));
    }

    /**
     * 分页查询平台类目和第三方平台类目映射
     *
     * @author
     */
    public Page<GoodsCateThirdCateRel> page(GoodsCateThirdCateRelQueryRequest queryReq) {
        return goodsCateThirdCateRelRepository.findAll(
                GoodsCateThirdCateRelWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询平台类目和第三方平台类目映射
     *
     * @author
     */
    public List<GoodsCateThirdCateRel> list(GoodsCateThirdCateRelQueryRequest queryReq) {
        return goodsCateThirdCateRelRepository.findAll(GoodsCateThirdCateRelWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author
     */
    public GoodsCateThirdCateRelVO wrapperVo(GoodsCateThirdCateRel goodsCateThirdCateRel) {
        if (goodsCateThirdCateRel != null) {
            GoodsCateThirdCateRelVO goodsCateThirdCateRelVO = KsBeanUtil.convert(goodsCateThirdCateRel, GoodsCateThirdCateRelVO.class);
            return goodsCateThirdCateRelVO;
        }
        return null;
    }

    /**
     * 批量新增linkedmall类目映射
     *
     * @param goodsCateThirdCateRels
     */
    @Transactional
    public GoodsCateThirdCateRelAddResponse addBatch(List<GoodsCateThirdCateRel> goodsCateThirdCateRels, int source) {
        for (GoodsCateThirdCateRel goodsCateThirdCateRel : goodsCateThirdCateRels) {
            Long thirdCateId = goodsCateThirdCateRel.getThirdCateId();
            Long cateId = goodsCateThirdCateRel.getCateId();
            GoodsCate goodsCate = goodsCateService.findById(goodsCateThirdCateRel.getCateId());
            if (goodsCate.getCateGrade() != 3) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030180);
            }
            goodsCateThirdCateRel.setDelFlag(DeleteFlag.NO);
            goodsCateThirdCateRel.setCreateTime(LocalDateTime.now());
            goodsCateThirdCateRel.setUpdateTime(LocalDateTime.now());
            //mq同步商品和商品库
            this.sendThirdCate(source,thirdCateId,cateId);
        }

        //更改映射关系
        goodsCateThirdCateRelRepository.deleteInThirdCateIds(goodsCateThirdCateRels.stream().map(GoodsCateThirdCateRel::getThirdCateId).collect(Collectors.toList()), goodsCateThirdCateRels.get(0).getThirdPlatformType());
        goodsCateThirdCateRelRepository.saveAll(goodsCateThirdCateRels);
        return new GoodsCateThirdCateRelAddResponse();
    }

    /**
     * 同步更新商品和商品资料库中的类目
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public GoodsCateThirdCateRelAddResponse modifyThirdCate(GoodsByThirdCateRequest request) {
        Integer source = request.getSource();
        Long thirdCateId = request.getThirdCateId();
        Long cateId = request.getCateId();
        //重新维护商品的平台类目和三方类目的映射关系
        goodsRepository.updateThirdCateMap(source, thirdCateId, cateId);
        goodsInfoRepository.updateThirdCateMap(source, thirdCateId, cateId);

        //重新维护商品库的平台类目和三方类目的映射关系
        standardGoodsRepository.updateThirdCateMap(source, thirdCateId, cateId);
        List<Long> thirdCateIdList = Collections.singletonList(thirdCateId);
        //返回给es更新的商品表商品
        List<String> updateEsGoodsIds = goodsRepository.findAllByThirdCateIdInAndGoodsSource(thirdCateIdList, source);
        //返回给es更新的商品库商品
        List<String> updateEsStandardGoodsIds = standardGoodsRepository.findAllByThirdCateIdInAndGoodsSource(thirdCateIdList, source);
        return GoodsCateThirdCateRelAddResponse.builder()
                .updateEsGoodsIds(updateEsGoodsIds)
                .updateEsStandardGoodsIds(updateEsStandardGoodsIds)
                .build();
    }

    /**
     * 新增平台类目和微信类目映射API
     * @param goodsCateThirdCateRels
     */
    @Transactional
    public void addBatchForWechat(List<GoodsCateThirdCateRel> goodsCateThirdCateRels){
        //待映射的平台类目
        ArrayList<Long> cateIds = new ArrayList<>();
        Long thirdCateId = goodsCateThirdCateRels.get(0).getThirdCateId();
        // 校验平台类目ID是否存在
        List<Long> distinctCateIds = goodsCateThirdCateRels.stream().map(GoodsCateThirdCateRel::getCateId).distinct().collect(Collectors.toList());
        List<GoodsCate> existGoodsCateList = goodsCateService.findByIds(distinctCateIds);
        if (goodsCateThirdCateRels.size() != existGoodsCateList.size()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030181);
        }
        // 校验第三方类目ID是否存在
        List<ThirdGoodsCate> existThirdCateList = thirdGoodsCateService.getByCateIds(ThirdPlatformType.WECHAT_VIDEO, Collections.singletonList(thirdCateId));
        if (CollectionUtils.isEmpty(existThirdCateList)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030181);
        }
        for (GoodsCateThirdCateRel goodsCateThirdCateRel : goodsCateThirdCateRels) {
            goodsCateThirdCateRel.setCreateTime(LocalDateTime.now());
            goodsCateThirdCateRel.setDelFlag(DeleteFlag.NO);
            cateIds.add(goodsCateThirdCateRel.getCateId());
        }
        //映射过的平台类目
        ArrayList<Long> mapedCateIds = new ArrayList<>();
        List<String> cateIdSring = wechatCateAuditRepository.selectCateIds(Collections.singletonList(thirdCateId));
        if (CollectionUtils.isNotEmpty(cateIdSring)) {
            mapedCateIds.addAll(cateIdSring.stream().filter(s-> StringUtils.isNotBlank(s)).flatMap(v-> Stream.of(v.split(","))).map(s->Long.valueOf(s)).collect(Collectors.toList()));
        }
        mapedCateIds.addAll(goodsCateThirdCateRelRepository.selectCateIdByThirdPlatformType(ThirdPlatformType.WECHAT_VIDEO,Collections.singletonList(thirdCateId)));
        if (cateIds.stream().anyMatch(v->mapedCateIds.contains(v))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030182);
        }
        AuditStatus auditStatus = wechatCateAuditService.list(WechatCateAuditQueryRequest.builder().wechatCateId(thirdCateId).build()).get(0).getAuditStatus();
        if (AuditStatus.CHECKED.equals(auditStatus)) {
            goodsCateThirdCateRelRepository.deleteInThirdCateIds(goodsCateThirdCateRels.stream().map(v->v.getThirdCateId()).distinct().collect(Collectors.toList()),ThirdPlatformType.WECHAT_VIDEO);
            goodsCateThirdCateRelRepository.saveAll(goodsCateThirdCateRels);
        }else {
            wechatCateAuditRepository.updateCateIds(String.join(",",goodsCateThirdCateRels.stream().map(v->String.valueOf(v.getCateId())).collect(Collectors.toList())),thirdCateId);
        }
    }

    /**
     * 删除微信类目映射
     * @param request
     */
    @Transactional
    public void deleteWechat(DeleteWechatCateMapRequest request) {
        // 判断第三方类目id是否存在
        List<ThirdGoodsCate> existCateList = thirdGoodsCateService.getByCateIds(request.getThirdPlatformType(), Collections.singletonList(request.getThirdCateId()));
        if (CollectionUtils.isEmpty(existCateList)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030005);
        }
        if (goodsCateThirdCateRelRepository.deleteInThirdCateIds(Collections.singletonList(request.getThirdCateId()), request.getThirdPlatformType()) == 0) {
            wechatCateAuditRepository.updateCateIds("", request.getThirdCateId());
        }
    }



    /**
     * t同步到mq
     * @param source
     * @param thirdCateId
     * @param cateId
     */
    private void sendThirdCate(int source,Long thirdCateId,Long cateId){
        GoodsByThirdCateRequest request = GoodsByThirdCateRequest.builder()
                .thirdCateId(thirdCateId)
                .cateId(cateId)
                .source(source)
                .build();
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendDTO.setTopic(ProducerTopic.GOODS_CATE_ID_SYNC);
        mqSendProvider.send(mqSendDTO);
    }



}

