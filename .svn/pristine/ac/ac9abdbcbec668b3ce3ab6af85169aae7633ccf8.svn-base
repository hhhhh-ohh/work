package com.wanmi.sbc.goods.cate.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.api.request.cate.BossGoodsCateDeleteByIdRequest;
import com.wanmi.sbc.goods.api.request.goodscatethirdcaterel.GoodsCateThirdCateRelQueryRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.repository.GoodsCateRepository;
import com.wanmi.sbc.goods.cate.request.GoodsCateQueryRequest;
import com.wanmi.sbc.goods.goodscatethirdcaterel.model.root.GoodsCateThirdCateRel;
import com.wanmi.sbc.goods.goodscatethirdcaterel.service.GoodsCateThirdCateRelService;
import com.wanmi.sbc.goods.goodspropcaterel.repository.GoodsPropCateRelRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.GoodsQueryRequest;
import com.wanmi.sbc.goods.prop.service.GoodsPropService;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRepository;
import com.wanmi.sbc.goods.standard.request.StandardQueryRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品分类服务
 * Created by daiyitian on 2017/4/11.
 */
@Service
@Transactional(readOnly = true, timeout = 10)
public class S2bGoodsCateService {

    private static final String SPLIT_CHAR = "|";

    @Autowired
    private GoodsCateRepository goodsCateRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsCateService goodsCateService;

    @Autowired
    private ContractCateService contractCateService;

    @Autowired
    private GoodsPropService goodsPropService;


    @Autowired
    private GoodsCateThirdCateRelService goodsCateThirdCateRelService;

    @Autowired
    private GoodsPropCateRelRepository goodsPropCateRelRepository;

    @Autowired
    private ContractCateAuditService contractCateAuditService;

    @Autowired
    private StandardGoodsRepository standardGoodsRepository;


    /**
     * 删除商品分类
     *
     * @param cateId 分类编号
     * @throws SbcRuntimeException
     */
    @Transactional
    public List<Long> delete(Long cateId) throws SbcRuntimeException {
        GoodsCate goodsCate = goodsCateRepository.findById(cateId).orElse(null);
        if (goodsCate == null || goodsCate.getDelFlag().compareTo(DeleteFlag.YES) == 0) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<Long> allCate = new ArrayList<>();
        String oldCatePath = goodsCate.getCatePath().concat(String.valueOf(goodsCate.getCateId())).concat(SPLIT_CHAR);

        //查询默认分类
        GoodsCateQueryRequest request = new GoodsCateQueryRequest();
        request.setLikeCatePath(oldCatePath);
        List<GoodsCate> childCateList = goodsCateRepository.findAll(request.getWhereCriteria());
        childCateList.add(goodsCate);

        childCateList.stream().forEach(cate -> {
            cate.setDelFlag(DeleteFlag.YES);
            allCate.add(cate.getCateId());
        });
        goodsCateRepository.saveAll(childCateList);

        //删除类目关联的属性数据
        goodsPropCateRelRepository.deletePropByCateId(
                childCateList.stream().map(GoodsCate::getCateId).collect(Collectors.toList()));

//        Iterable<EsCateBrand> esCateBrands = esCateBrandService.queryCateBrandByCateIds(allCate);
//        List<EsCateBrand> cateBrandList = new ArrayList<>();
//        esCateBrands.forEach(cateBrand -> {
//            cateBrand.setGoodsCate(new GoodsCate());
//            cateBrandList.add(cateBrand);
//        });
//        if (CollectionUtils.isNotEmpty(cateBrandList)) {
//            esCateBrandService.save(cateBrandList);
//        }

        // 获取所有三级分类准备删除签约的分类
        List<Long> delIds = childCateList.stream().filter(f -> Objects.equals(f.getCateGrade(),3))
                .map(GoodsCate::getCateId).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(delIds)) {
            contractCateService.deleteByIds(delIds);
            goodsPropService.deletePropByCateId(delIds);
        }
        //生成缓存
        goodsCateService.fillRedis();
        return allCate;
    }

    /**
     * 验证是否有子类(包含签约分类)
     *
     * @param cateId
     */
    public Integer checkSignChild(Long cateId) {
        GoodsCate cate = goodsCateRepository.findById(cateId).orElse(null);
        if (cate == null || cate.getDelFlag().compareTo(DeleteFlag.YES) == 0) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        String oldCatePath = cate.getCatePath().concat(String.valueOf(cate.getCateId()).concat(SPLIT_CHAR));
        List<GoodsCate> list = goodsCateRepository.findAll
                (GoodsCateQueryRequest.builder().delFlag(DeleteFlag.NO.toValue())
                        .likeCatePath(oldCatePath).build().getWhereCriteria());
        if (CollectionUtils.isNotEmpty(list)) {
            return Constants.yes;
        }
        return Constants.no;
    }

    /**
     * 验证签约分类下是否有商品
     *
     * @param cateId
     */
    public Integer checkSignGoods(Long cateId) {
        GoodsCate cate = goodsCateRepository.findById(cateId).orElse(null);
        if (cate == null || cate.getDelFlag().compareTo(DeleteFlag.YES) == 0) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        String oldCatePath = cate.getCatePath().concat(String.valueOf(cate.getCateId()).concat(SPLIT_CHAR));
        List<GoodsCate> childCateList = goodsCateRepository
                .findAll(GoodsCateQueryRequest.builder().delFlag(DeleteFlag.NO.toValue())
                        .likeCatePath(oldCatePath).build().getWhereCriteria());
        childCateList.add(cate);
        List<Long> ids = childCateList.stream().filter(f -> Objects.equals(f.getCateGrade(), 3))
                .map(GoodsCate::getCateId).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(ids)) {
            if(goodsRepository.count(GoodsQueryRequest.builder().delFlag(DeleteFlag.NO.toValue())
                    .cateIds(ids).build().getWhereCriteria()) > 0) {
                return Constants.yes;
            }
            if (standardGoodsRepository.count(StandardQueryRequest.builder().delFlag(DeleteFlag.NO.toValue())
                    .cateIds(ids).build().getWhereCriteria()) > 0){
                return Constants.yes;
            }

            //校验是否有二次签约分类信息
            if(contractCateAuditService.findCountByIds(ids) > 0){
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030013);
            }

            if (contractCateService.findCountByIds(ids) > 0){
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030015);
            }

        }



        return Constants.no;
    }

    /**
     * 验证签约分类下是否关联三方渠道类目
     * @param request
     */
    public void checkSignGoodsRel(BossGoodsCateDeleteByIdRequest request) {
        GoodsCate cate = goodsCateRepository.findById(request.getCateId()).orElse(null);
        if (cate == null || cate.getDelFlag().compareTo(DeleteFlag.YES) == 0) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        String oldCatePath = cate.getCatePath().concat(String.valueOf(cate.getCateId()).concat(SPLIT_CHAR));
        List<GoodsCate> childCateList = goodsCateRepository
                .findAll(GoodsCateQueryRequest.builder().delFlag(DeleteFlag.NO.toValue())
                        .likeCatePath(oldCatePath).build().getWhereCriteria());
        childCateList.add(cate);
        List<Long> ids = childCateList.stream().filter(f -> Objects.equals(f.getCateGrade(), 3))
                .map(GoodsCate::getCateId).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(ids)) {
            List<GoodsCateThirdCateRel> list = goodsCateThirdCateRelService.list(GoodsCateThirdCateRelQueryRequest.builder().delFlag(DeleteFlag.NO).cateIdList(ids).build());
            if (list!=null&&list.size()>0) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030187);
            }
        }
    }
}