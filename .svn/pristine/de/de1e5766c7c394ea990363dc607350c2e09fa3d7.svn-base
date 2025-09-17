package com.wanmi.sbc.vas.vop.cate;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.provider.channel.vop.cate.VopCategoryProvider;
import com.wanmi.sbc.empower.api.request.vop.category.VopGetCategoryRequest;
import com.wanmi.sbc.empower.api.response.vop.category.VopGetCategoryResponse;
import com.wanmi.sbc.goods.api.provider.thirdgoodscate.ThirdGoodsCateProvider;
import com.wanmi.sbc.goods.api.request.thirdgoodscate.CheckCateIdRequest;
import com.wanmi.sbc.goods.api.request.thirdgoodscate.UpdateAllRequest;
import com.wanmi.sbc.goods.api.response.thirdgoodscate.CheckCateIdResponse;
import com.wanmi.sbc.goods.bean.dto.ThirdGoodsCateDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author EDZ
 * @className VopCateService
 * @description 京东VOP类目相关
 * @date 2021/5/10 15:58
 */
@Slf4j
@Service
public class VopCateService {
    @Autowired private VopCategoryProvider vopCategoryProvider;

    @Autowired private ThirdGoodsCateProvider thirdGoodsCateProvider;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 若京东vop的增量商品同步时，对应的类目还未同步，直接把该类目也同步过来
     *
     * @param category 京东商品类目，格式：737;17407;17408，对应一二三级
     * @return 绑定的平台三级类目Id（返回为空没有绑定）
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Long checkGoodCate(String category) {
        log.info("京东vop同步商品类目检查请求category={}", category);
        List<Long> cateIdList =
                Arrays.stream(StringUtils.split(category.trim(), ";"))
                        .map(Long::valueOf)
                        .collect(Collectors.toList());
        CheckCateIdResponse checkCateIdResponse = new CheckCateIdResponse();
        checkCateIdResponse.setThirdCateIdSet(new HashSet<>());
        CheckCateIdRequest request = new CheckCateIdRequest();

        //一级分类是否存在
        boolean first = true;
        //二级分类是否存在
        boolean second = true;
        //三级分类是否存在
        boolean third = true;
        for (int i = 0; i < cateIdList.size(); i++) {
            //查询redis
            String redisThirdCateId = redisUtil.getString(RedisKeyConstant.VOP_CATE_ID + cateIdList.get(i));
            if (StringUtils.isNotBlank(redisThirdCateId)){
                checkCateIdResponse.getThirdCateIdSet().add(cateIdList.get(i));
                if (i == Constants.TWO){
                    checkCateIdResponse.setCateId(Long.valueOf(redisThirdCateId));
                }
                continue;
            }
            List<ThirdGoodsCateDTO> thirdGoodsCateDTOS = new ArrayList<>(cateIdList.size());
            ThirdGoodsCateDTO dto = new ThirdGoodsCateDTO();
            dto.setCateId(cateIdList.get(i));
            dto.setThirdPlatformType(ThirdPlatformType.VOP);
            thirdGoodsCateDTOS.add(dto);
            request.setThirdGoodsCateDTOS(thirdGoodsCateDTOS);
            CheckCateIdResponse response = thirdGoodsCateProvider.checkCateIdExist(request).getContext();
            Long thirdCateId = response.getThirdCateIdSet().stream().findFirst().orElse(null);
            if (Objects.isNull(thirdCateId)){
                if (i == Constants.ZERO){
                    //一级分类是否存在
                    first = false;
                }
                if (i == Constants.ONE){
                    //二级分类是否存在
                    second = false;
                }
                if (i == Constants.TWO){
                    //三级分类是否存在
                    third = false;
                }
            }else {
                checkCateIdResponse.getThirdCateIdSet().add(cateIdList.get(i));
                if (i == Constants.TWO){
                    checkCateIdResponse.setCateId(response.getCateId());
                    if(Objects.nonNull(checkCateIdResponse.getCateId())) {
                        redisUtil.setString(RedisKeyConstant.VOP_CATE_ID+thirdCateId, checkCateIdResponse.getCateId().toString(), 24*60*60);
                    }
                }
            }
        }

        log.info(
                "京东vop同步商品类目检查结果category={}, checkCateIdResponse={}",
                category,
                JSON.toJSONString(checkCateIdResponse));
        Long firstCateId = cateIdList.get(0);
        Long secondCateId = cateIdList.get(1);
        Long thirdCateId = cateIdList.get(2);
        VopGetCategoryRequest categoryRequest = new VopGetCategoryRequest();
        // 同步数据
        List<ThirdGoodsCateDTO> syncDtoList = new ArrayList<>();
        if (!first) {
            categoryRequest.setCid(firstCateId);
            VopGetCategoryResponse response =
                    vopCategoryProvider.getCategory(categoryRequest).getContext();
            ThirdGoodsCateDTO dto =
                    ThirdGoodsCateDTO.builder()
                            .cateId(response.getCatId())
                            .cateName(response.getName())
                            .cateParentId(response.getParentId())
                            .cateGrade(response.getCatClass() + 1)
                            .thirdPlatformType(ThirdPlatformType.VOP)
                            .catePath("0")
                            .build();
            syncDtoList.add(dto);
        }
        if (!second) {
            categoryRequest.setCid(secondCateId);
            VopGetCategoryResponse response =
                    vopCategoryProvider.getCategory(categoryRequest).getContext();
            ThirdGoodsCateDTO dto =
                    ThirdGoodsCateDTO.builder()
                            .cateId(response.getCatId())
                            .cateName(response.getName())
                            .cateParentId(response.getParentId())
                            .cateGrade(response.getCatClass() + 1)
                            .thirdPlatformType(ThirdPlatformType.VOP)
                            .catePath("0|" + firstCateId)
                            .build();
            syncDtoList.add(dto);
        }
        if (!third) {
            categoryRequest.setCid(thirdCateId);
            VopGetCategoryResponse response =
                    vopCategoryProvider.getCategory(categoryRequest).getContext();
            ThirdGoodsCateDTO dto =
                    ThirdGoodsCateDTO.builder()
                            .cateId(response.getCatId())
                            .cateName(response.getName())
                            .cateParentId(response.getParentId())
                            .cateGrade(response.getCatClass() + 1)
                            .thirdPlatformType(ThirdPlatformType.VOP)
                            .catePath("0|" + firstCateId + "|" + secondCateId)
                            .build();
            syncDtoList.add(dto);
        }
        if (CollectionUtils.isNotEmpty(syncDtoList)) {
            UpdateAllRequest updateAllRequest = new UpdateAllRequest();
            updateAllRequest.setThirdGoodsCateDTOS(syncDtoList);
            thirdGoodsCateProvider.addAll(updateAllRequest);
        }
        log.info(
                "京东vop同步商品类目检查返回category={}, cateId={}", category, checkCateIdResponse.getCateId());
        return checkCateIdResponse.getCateId();
    }
}
