package com.wanmi.sbc.elastic.goodsevaluate.service;

import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluateAnswerRequest;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluateInitRequest;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.enums.ElasticErrorCodeEnum;
import com.wanmi.sbc.elastic.goodsevaluate.model.root.EsGoodsEvaluate;
import com.wanmi.sbc.elastic.goodsevaluate.model.root.EsGoodsEvaluateImage;
import com.wanmi.sbc.elastic.goodsevaluate.repository.EsGoodsEvaluateRepository;
import com.wanmi.sbc.goods.api.provider.goodsevaluate.GoodsEvaluateQueryProvider;
import com.wanmi.sbc.goods.api.request.goodsevaluate.GoodsEvaluatePageRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsEvaluateImageVO;
import com.wanmi.sbc.goods.bean.vo.GoodsEvaluateVO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author houshuai
 * @date 2020/12/28 18:24
 * @description <p> 评价管理Service </p>
 */
@Slf4j
@Service
public class EsGoodsEvaluateService {

    @Autowired
    private EsGoodsEvaluateRepository esGoodsEvaluateRepository;

    @Autowired
    private GoodsEvaluateQueryProvider goodsEvaluateQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esGoodsEvaluate.json")
    private Resource mapping;

    /**
     * 评价管理新增
     * @param request
     * @return
     */
    public BaseResponse add(EsGoodsEvaluateAnswerRequest request) {
        GoodsEvaluateVO goodsEvaluateVO = request.getGoodsEvaluateVO();
        if (Objects.isNull(goodsEvaluateVO)) {
            throw new SbcRuntimeException();
        }
        EsGoodsEvaluate esGoodsEvaluate = new EsGoodsEvaluate();

        BeanUtils.copyProperties(request.getGoodsEvaluateVO(), esGoodsEvaluate);
        List<GoodsEvaluateImageVO> evaluateImageList = goodsEvaluateVO.getEvaluateImageList();
        if(CollectionUtils.isNotEmpty(evaluateImageList)) {
            List<EsGoodsEvaluateImage> esGoodsEvaluateImages = KsBeanUtil.convertList(evaluateImageList, EsGoodsEvaluateImage.class);
            esGoodsEvaluate.setGoodsEvaluateImages(esGoodsEvaluateImages);
        }
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        esGoodsEvaluateRepository.save(esGoodsEvaluate);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 初始化评价管理
     * @param request
     */
    public void init(EsGoodsEvaluateInitRequest request) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        boolean flg = true;
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        List<String> idList = request.getIdList();
        GoodsEvaluatePageRequest pageRequest = new GoodsEvaluatePageRequest();
        try {
            while (flg) {
                if (CollectionUtils.isNotEmpty(idList)) {
                    pageRequest.setEvaluateIdList(idList);
                    pageSize = idList.size();
                    pageNum = 0;
                    flg = false;
                }
                pageRequest.setPageNum(pageNum);
                pageRequest.setPageSize(pageSize);
                List<GoodsEvaluateVO> content = goodsEvaluateQueryProvider.page(pageRequest).getContext()
                        .getGoodsEvaluateVOPage().getContent();

                if (CollectionUtils.isEmpty(content)) {
                    flg = false;
                    log.info("==========ES初始化评价管理列表，结束pageNum:{}==============", pageNum);
                } else {
                    List<EsGoodsEvaluate> esGoodsEvaluateList = content.stream()
                            .map(goodsEvaluateVO -> {
                                EsGoodsEvaluate esGoodsEvaluate = new EsGoodsEvaluate();
                                BeanUtils.copyProperties(goodsEvaluateVO, esGoodsEvaluate);
                                List<EsGoodsEvaluateImage> esGoodsEvaluateImages = KsBeanUtil.convert(goodsEvaluateVO.getEvaluateImageList(), EsGoodsEvaluateImage.class);
                                esGoodsEvaluate.setGoodsEvaluateImages(esGoodsEvaluateImages);
                                return esGoodsEvaluate;
                            }).collect(Collectors.toList());
                    esGoodsEvaluateRepository.saveAll(esGoodsEvaluateList);
                    log.info("==========ES初始化评价管理列表成功，当前pageNum:{}==============", pageNum);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.info("==========ES初始化评价管理列表异常，异常pageNum:{}==============", pageNum);
            throw new SbcRuntimeException(ElasticErrorCodeEnum.K040016, new Object[]{pageNum});
        }
    }

    /**
     * 创建索引以及mapping
     */
    private void createIndexAddMapping() {
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.GOODS_EVALUATE, mapping);
    }
}
