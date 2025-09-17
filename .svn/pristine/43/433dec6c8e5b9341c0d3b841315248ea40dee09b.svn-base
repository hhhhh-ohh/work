package com.wanmi.sbc.elastic.distributionmatter.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.MD5Util;
import com.wanmi.sbc.elastic.api.request.distributionmatter.*;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.enums.ElasticErrorCodeEnum;
import com.wanmi.sbc.elastic.distributionmatter.model.root.EsDistributionGoodsMatter;
import com.wanmi.sbc.elastic.distributionmatter.model.root.EsObjectGoodsInfo;
import com.wanmi.sbc.elastic.distributionmatter.repository.EsDistributionGoodsMatterRepository;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.goods.api.provider.distributionmatter.DistributionGoodsMatterQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.distributionmatter.DistributionGoodsMatterPageRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByIdResponse;
import com.wanmi.sbc.goods.bean.dto.MarketingMaterialDTO;
import com.wanmi.sbc.goods.bean.enums.MatterType;
import com.wanmi.sbc.goods.bean.vo.DistributionGoodsMatterPageVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2021/1/8 14:06
 * @description <p> 商品素材 </p>
 */
@Slf4j
@Service
public class EsDistributionGoodsMatterService {

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private WechatAuthProvider wechatAuthProvider;

    @Autowired
    private EsDistributionGoodsMatterRepository esDistributionGoodsMatterRepository;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private DistributionGoodsMatterQueryProvider distributionGoodsMatterQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esDistributionGoodsMatter.json")
    private Resource mapping;

    /**
     * 新增商品素材
     *
     * @param distributionGoodsMatteAddRequest {@link EsDistributionGoodsMatteAddRequest}
     * @return BaseResponse
     */
    public BaseResponse add(EsDistributionGoodsMatteAddRequest distributionGoodsMatteAddRequest) {
        this.createIndexAddMapping();
        EsDistributionGoodsMatter distributionGoodsMatter =
                KsBeanUtil.copyPropertiesThird(distributionGoodsMatteAddRequest, EsDistributionGoodsMatter.class);
        //营销素材配置图片链接的，预生成小程序码
        GoodsInfoListByIdRequest byIdRequest = new GoodsInfoListByIdRequest(distributionGoodsMatteAddRequest.getGoodsInfoId());
        GoodsInfoListByIdResponse idResponse = goodsInfoQueryProvider.getGoodsInfoById(byIdRequest).getContext();
        GoodsInfoVO goodsInfoVO = idResponse.getGoodsInfoVO();
        if (Objects.nonNull(goodsInfoVO)) {
            EsObjectGoodsInfo esObjectGoodsInfo = new EsObjectGoodsInfo();
            BeanUtils.copyProperties(goodsInfoVO, esObjectGoodsInfo);
            distributionGoodsMatter.setEsObjectGoodsInfo(esObjectGoodsInfo);
        }
        if (distributionGoodsMatteAddRequest.getMatterType().equals(MatterType.MARKETING)) {
            List<MarketingMaterialDTO> marketingMaterialDTOList = JSONArray.parseArray(distributionGoodsMatteAddRequest.getMatter(), MarketingMaterialDTO.class);
            marketingMaterialDTOList.forEach(item -> {
                //把链接加密，生成redisKey，作为参数传递，用来生成小程序码
                if (StringUtils.isNotBlank(item.getLink()) && item.getLink() != null) {
                    String redisKey = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(item.getLink(), "utf-8")).toUpperCase().substring(16));
                    if (StringUtils.isNotBlank(redisKey)) {
                        redisService.setString(redisKey, item.getLink(), 15000000L);
                        item.setRedisKey("FX" + redisKey);
                        //生成码
                        MiniProgramQrCodeRequest request = new MiniProgramQrCodeRequest();
                        request.setPage("pages/sharepage/sharepage");
                        request.setScene("FX" + redisKey);
                        //生成透明底色的码
                        request.setIs_hyaline(Boolean.TRUE);
                        String codeUrl = wechatAuthProvider.getWxaCodeUnlimit(request).getContext();
                        //若成功生成，
                        if (StringUtils.isNotBlank(codeUrl)) {
                            item.setLinkSrc(codeUrl);
                        }
                    }
                }
            });
            //重新设值
            distributionGoodsMatter.setMatter(JSONObject.toJSONString(marketingMaterialDTOList));
        }
        distributionGoodsMatter.setRecommendNum(0);
        distributionGoodsMatter.setCreateTime(LocalDateTime.now());
        distributionGoodsMatter.setUpdateTime(LocalDateTime.now());
        esDistributionGoodsMatterRepository.save(distributionGoodsMatter);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 初始化分销商品素材
     *
     * @param request {@link EsDistributionGoodsMatterPageRequest}
     */
    public void init(EsDistributionGoodsMatterInitRequest request) {
        this.createIndexAddMapping();
        Integer pageNum = request.getPageNum();
        try {

            DistributionGoodsMatterPageRequest pageRequest = KsBeanUtil.convert(request, DistributionGoodsMatterPageRequest.class);
            while (true) {
                pageRequest.setPageNum(pageNum);
                pageRequest.setPageSize(request.getPageSize());
                pageRequest.putSort("updateTime", SortType.DESC.toValue());
                List<DistributionGoodsMatterPageVO> goodsMatterPageVOList = distributionGoodsMatterQueryProvider.pageNewForBoss(pageRequest)
                        .getContext().getDistributionGoodsMatterPage().getContent();
                if (CollectionUtils.isEmpty(goodsMatterPageVOList)) {
                    break;
                }

                List<EsDistributionGoodsMatter> newList = goodsMatterPageVOList.stream()
                        .map(target -> {
                            EsDistributionGoodsMatter dest = new EsDistributionGoodsMatter();
                            BeanUtils.copyProperties(target, dest);
                            GoodsInfoVO goodsInfoVO = target.getGoodsInfo();
                            if (Objects.nonNull(goodsInfoVO)) {
                                EsObjectGoodsInfo esObjectGoodsInfo = new EsObjectGoodsInfo();
                                BeanUtils.copyProperties(goodsInfoVO, esObjectGoodsInfo);
                                dest.setEsObjectGoodsInfo(esObjectGoodsInfo);
                            }
                            return dest;
                        }).collect(Collectors.toList());
                esDistributionGoodsMatterRepository.saveAll(newList);
                pageNum++;
            }
        } catch (Exception e) {
            log.error("==========ES初始化商品素材列表异常，异常pageNum:{}==============", pageNum);
            throw new SbcRuntimeException(ElasticErrorCodeEnum.K040010, new Object[]{pageNum});
        }

    }

    /**
     * 修改分销素材
     *
     * @param request {@link EsDistributionGoodsMatteAddRequest}
     */
    public void modify(EsDistributionGoodsMatteAddRequest request) {
        this.createIndexAddMapping();
        Optional<EsDistributionGoodsMatter> optional = esDistributionGoodsMatterRepository.findById(request.getId());
        optional.ifPresent(entity -> {
            BeanUtils.copyProperties(request, entity);
            entity.setUpdateTime(LocalDateTime.now());
            esDistributionGoodsMatterRepository.save(entity);
        });
    }

    /**
     * 删除分销素材
     *
     * @param request {@link EsDeleteByIdListRequest}
     */
    public void deleteList(EsDeleteByIdListRequest request) {
        this.createIndexAddMapping();
        Iterable<EsDistributionGoodsMatter> distributionGoodsMatters = esDistributionGoodsMatterRepository.findAllById(request.getIds());
        List<EsDistributionGoodsMatter> goodsMatters = Lists.newArrayList(distributionGoodsMatters);
        if (CollectionUtils.isEmpty(goodsMatters)) {
            return;
        }
        List<EsDistributionGoodsMatter> newList = goodsMatters.stream()
                .peek(entity -> {
                    entity.setDelFlag(DeleteFlag.YES);
                    entity.setDeleteTime(LocalDateTime.now());
                }).collect(Collectors.toList());
        esDistributionGoodsMatterRepository.saveAll(newList);
    }

    /**
     * 修改分销素材
     *
     * @param request {@link EsDistributionGoodsMatteByIdRequest}
     */
    public void modifyGoodsMatter(EsDistributionGoodsMatteByIdRequest request) {
        this.createIndexAddMapping();
        Optional<EsDistributionGoodsMatter> optional = esDistributionGoodsMatterRepository.findById(request.getId());
        optional.ifPresent(entity -> {
            int recommendNum = Objects.isNull(entity.getRecommendNum()) ?
                    NumberUtils.INTEGER_ZERO : entity.getRecommendNum();
            entity.setRecommendNum(recommendNum + 1);
            esDistributionGoodsMatterRepository.save(entity);
        });
    }

    /**
     * 创建索引以及mapping
     */
    private void createIndexAddMapping() {
        esBaseService.existsOrCreate(EsConstants.DISTRIBUTION_GOODS_MATTER, mapping);
    }
}
