package com.wanmi.sbc.goods.cate.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.api.request.goodscatethirdcaterel.GoodsCateThirdCateRelQueryRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.GoodsType;import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.cate.model.root.ContractCate;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.repository.ContractCateRepository;
import com.wanmi.sbc.goods.cate.repository.GoodsCateRepository;
import com.wanmi.sbc.goods.cate.request.ContractCateQueryRequest;
import com.wanmi.sbc.goods.cate.request.ContractCateSaveRequest;
import com.wanmi.sbc.goods.cate.response.ContractCateResponse;
import com.wanmi.sbc.goods.goodsaudit.model.root.GoodsAudit;
import com.wanmi.sbc.goods.goodsaudit.repository.GoodsAuditRepository;
import com.wanmi.sbc.goods.goodsaudit.service.GoodsAuditWhereCriteriaBuilder;
import com.wanmi.sbc.goods.goodscatethirdcaterel.model.root.GoodsCateThirdCateRel;
import com.wanmi.sbc.goods.goodscatethirdcaterel.repository.GoodsCateThirdCateRelRepository;
import com.wanmi.sbc.goods.goodscatethirdcaterel.service.GoodsCateThirdCateRelWhereCriteriaBuilder;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.GoodsQueryRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 签约分类服务
 * Created by sunkun on 2017/10/30.
 */
@Service
@Transactional
public class ContractCateService {

    @Resource
    private ContractCateRepository contractCateRepository;


    @Resource
    private GoodsCateRepository goodsCateRepository;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private GoodsAuditRepository goodsAuditRepository;

    @Autowired
    private GoodsCateThirdCateRelRepository goodsCateThirdCateRelRepository;





    /**
     * 新增
     *
     * @param request
     */
    public void add(ContractCateSaveRequest request) {
        //查询平台分类
        GoodsCate goodsCate = goodsCateRepository.findById(request.getCateId()).orElse(null);
        if (goodsCate == null) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030004);
        }
        if(goodsCate.getDelFlag() == DeleteFlag.YES){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030007,new Object[]{goodsCate.getCateName()});
        }
        //查询店铺下该平台分类是否存在
        ContractCateQueryRequest contractCateQueryRequest = new ContractCateQueryRequest();
        contractCateQueryRequest.setStoreId(request.getStoreId());
        contractCateQueryRequest.setCateId(request.getCateId());
        BeanUtils.copyProperties(request, contractCateQueryRequest);
        List<ContractCate> list = contractCateRepository.findAll(contractCateQueryRequest.getWhereCriteria());
        if (!list.isEmpty()) {
            //已签约
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030001);
        }
        //查询店铺签约分类总数
        request.setCateId(null);
        /*list = contractCateRepository.findAll(contractCateQueryRequest.getWhereCriteria());
        if (list.size() >= 200) {
            throw new SbcRuntimeException(SigningClassErrorCode.MOST_CONTRACT_NUMBER);
        }*/
        //保存分类
        ContractCate contractCate = new ContractCate();
        BeanUtils.copyProperties(request, contractCate);

        contractCate.setGoodsCate(goodsCate);
        contractCateRepository.save(contractCate);
    }

    /**
     * 根据平台类目id和店铺id删除签约分类
     * @param ids
     * @param storeId
     */
    public void deleteByIds(List<Long> ids, Long storeId) {
        contractCateRepository.deleteByIdsAndStoreId(ids, storeId);
    }


    /**
     * 查询
     *
     * @param request
     * @return
     */
    public List<ContractCateResponse> queryList(ContractCateQueryRequest request) {
        List<ContractCate> contractCates = contractCateRepository.findAll(request.getWhereCriteria());
        //根据path得到所有上级类目的编号列表
        List<Long> allGoodsCateIds = new ArrayList<>();
        contractCates.stream().map(ContractCate::getGoodsCate).map(GoodsCate::getCatePath).forEach(info -> {
            Arrays.asList(info.split(Constants.CATE_PATH_SPLITTER)).forEach(idStr -> {
                if (StringUtils.isNotBlank(idStr)) {
                    Long id = Long.valueOf(idStr);
                    if (id > 0) {
                        allGoodsCateIds.add(id);
                    }
                }
            });
        });
        //根据上级类目编号列表查询上级类目
        List<GoodsCate> tempList1 = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(allGoodsCateIds)) {
            tempList1 = goodsCateRepository.queryCates(allGoodsCateIds.stream().distinct().collect(Collectors.toList()), DeleteFlag.NO);
        }
        List<GoodsCate> goodsCates = tempList1;
        List<ContractCateResponse> contractCateResponseList = new ArrayList<>();
        //填充返回对象
        contractCates.forEach(info -> {
            ContractCateResponse contractCateResponse = new ContractCateResponse();
            BeanUtils.copyProperties(info, contractCateResponse);
            contractCateResponse.setCateId(info.getGoodsCate().getCateId());
            contractCateResponse.setCateName(info.getGoodsCate().getCateName());
            //筛选并填充上级分类名称
            Arrays.asList(info.getGoodsCate().getCatePath().split(Constants.CATE_PATH_SPLITTER)).forEach(id -> {
                List<GoodsCate> cates = goodsCates.stream().filter(cate -> cate.getCateId() == Long.parseLong(id)).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(cates)) {
                    GoodsCate goodsCate = cates.get(0);
                    contractCateResponse.setParentGoodCateNames(StringUtils.isBlank(contractCateResponse.getParentGoodCateNames()) ?
                            goodsCate.getCateName() :
                            contractCateResponse.getParentGoodCateNames().concat(Constants.STRING_SLASH_SPLITTER).concat(goodsCate.getCateName()));
                }
            });
            contractCateResponse.setPlatformCateRate(info.getGoodsCate().getCateRate());
            contractCateResponseList.add(contractCateResponse);
        });
        return contractCateResponseList;
    }

    /**
     * 查询店铺已签约的类目列表，包含上级类目
     *
     * @param storeId 店铺编号
     * @return 店铺已签约的类目列表，包含上级类目
     */
    public List<GoodsCate> listCate(Long storeId,GoodsType goodsType) {
        // 查询商家已签约的叶子类目
        ContractCateQueryRequest request = new ContractCateQueryRequest();
        if (!storeId.equals(Constants.BOSS_DEFAULT_STORE_ID)){
            request.setStoreId(storeId);
        }
        List<ContractCate> list = contractCateRepository.findAll(request.getWhereCriteria());


        // 根据path得到所有上级类目的编号列表
        Set<Long> cateIds = new HashSet<>();
        list.forEach(
                contractCate -> {
                    String catePath = contractCate.getGoodsCate().getCatePath();
                    if (StringUtils.isNotEmpty(catePath)) {

                        String[] cates = catePath.split(Constants.CATE_PATH_SPLITTER);
                        for (String id : cates) {
                            if (!"0".equals(id) && StringUtils.isNotEmpty(id)) {

                                cateIds.add(Long.parseLong(id));
                            }
                        }
                        cateIds.add(contractCate.getCateId());
                    }
                });

        // 根据类目编号列表查询类目信息
        List<GoodsCate> cateList = null;
        if (goodsType != null && !goodsType.equals(GoodsType.REAL_GOODS)) {
            cateList = goodsCateRepository.queryCates(new ArrayList<>(cateIds), DeleteFlag.NO, BoolFlag.YES);
        }else{
            cateList = goodsCateRepository.queryCates(new ArrayList<>(cateIds),DeleteFlag.NO);
        }

        // 把查出来的父级一期返回出去
//        cateList.addAll(list.stream().map(ContractCate::getGoodsCate).collect(Collectors.toList()));
        return cateList;
    }

    /**
     * 根据主键查询
     *
     * @return
     */
    public ContractCate queryById(Long id) {
        return contractCateRepository.findById(id).orElse(null);
    }


    /**
     * 根据平台类目主键和店铺主键查询签约分类
     *
     * @param cateIds
     * @param storeId
     * @return
     */
    public void cateDelVerify(List<Long> cateIds, Long storeId) {
        //根据平台分类及店铺查询签约分类
        List<ContractCate> contractCates = contractCateRepository.queryByCateIdAndStoreId(cateIds, storeId);
        //查询的结果数据不对，则抛异常
        if (NumberUtils.compare(cateIds.size(), contractCates.size()) != 0) {
            //分类不存在
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030005);
        }
        //纪录商品名称
        StringBuilder goodNames = new StringBuilder();
        List<String> cateNameList = new ArrayList<>();
        GoodsQueryRequest goodsQueryRequest = new GoodsQueryRequest();
        for (ContractCate contractCate : contractCates) {
            goodsQueryRequest.setCateId(contractCate.getGoodsCate().getCateId());
            goodsQueryRequest.setStoreId(storeId);
            goodsQueryRequest.setDelFlag(0);
            List<Goods> goodsList = goodsRepository.findAll(goodsQueryRequest.getWhereCriteria());
            if (goodsList.size() > 0 && !cateNameList.contains(contractCate.getGoodsCate().getCateName())) {
                goodNames.append(contractCate.getGoodsCate().getCateName());
                goodNames.append(',');
                cateNameList.add(contractCate.getGoodsCate().getCateName());
            }

            GoodsAuditQueryRequest queryRequest = new GoodsAuditQueryRequest();
            KsBeanUtil.copyProperties(goodsQueryRequest,queryRequest);
            List<GoodsAudit> goodsAuditList = goodsAuditRepository.findAll(GoodsAuditWhereCriteriaBuilder.build(queryRequest));
            if (goodsAuditList.size() > 0 && !cateNameList.contains(contractCate.getGoodsCate().getCateName())) {
                goodNames.append(contractCate.getGoodsCate().getCateName());
                goodNames.append(',');
                cateNameList.add(contractCate.getGoodsCate().getCateName());
            }
        }
        if (goodNames.length() > 0) {
            throw new SbcRuntimeException(goodNames.substring(0, goodNames.length() - 1),
                    GoodsErrorCodeEnum.K030016);
        }

    }

    /**
     * 根据平台分类主键列表修改签约分类扣率
     *
     * @param cateIds
     * @param cateRate
     */
    public void updateCateRate(BigDecimal cateRate, List<Long> cateIds) {
        contractCateRepository.updateCateRate(cateRate, cateIds);
    }

    /**
     * 修改
     *
     * @param request
     */
    public void update(ContractCateSaveRequest request) {
        ContractCate contractCate = new ContractCate();
        BeanUtils.copyProperties(request, contractCate);
        GoodsCate goodsCate = goodsCateRepository.findById(request.getCateId()).orElse(null);
        if(Objects.isNull(goodsCate)){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030004);
        }
        if(goodsCate.getDelFlag() == DeleteFlag.YES){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030007,new Object[]{goodsCate.getCateName()});
        }
        contractCate.setGoodsCate(goodsCate);
        contractCateRepository.saveAndFlush(contractCate);
    }

    /**
     * 根据分类Ids查询签约分类数量
     * @param ids
     * @return
     */
    public int findCountByIds(List<Long> ids) {
        return contractCateRepository.findCountByIds(ids);
    }


    /**
     * 根据分类Ids删除签约分类
     * @param ids
     * @return
     */
    public void deleteByIds(List<Long> ids) {
        contractCateRepository.deleteByIds(ids);
    }

    /**
     * 查询签约分类列表
     * @param request
     * @return
     */
    public List<ContractCate> queryContractCateList(ContractCateQueryRequest request) {
        return contractCateRepository.findAll(request.getWhereCriteria());
    }

    /**
     * 根据平台类目id和店铺id删除签约分类
     * @param storeId
     */
    public void deleteByStoreId(Long storeId) {
        contractCateRepository.deleteByStoreId(storeId);
    }

    /**
     * 查询商家签约的平台类目列表，并且平台类目映射到已审核的微信微信类目
     * @param storeId
     * @return
     */
    public List<GoodsCateVO> mapedWechat(Long storeId) {
        //审核过的微信类目映射的平台类目

        List<Long> mapedCateIds = goodsCateThirdCateRelRepository.findAll(GoodsCateThirdCateRelWhereCriteriaBuilder.build(GoodsCateThirdCateRelQueryRequest.builder()
                .thirdPlatformType(ThirdPlatformType.WECHAT_VIDEO)
                .delFlag(DeleteFlag.NO).build())).stream().map(GoodsCateThirdCateRel::getCateId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(mapedCateIds)) {
            ContractCateQueryRequest request = new ContractCateQueryRequest();
            request.setStoreId(storeId);
            request.setCateIds(mapedCateIds);
            List<ContractCate> list = contractCateRepository.findAll(request.getWhereCriteria());
            if (CollectionUtils.isNotEmpty(list)) {
                List<GoodsCate> thirdCates = list.stream().map(s -> s.getGoodsCate()).collect(Collectors.toList());
                // 根据path得到所有上级类目的编号列表
                List<Long> paretnCateIds = thirdCates.stream().filter(v->StringUtils.isNotBlank(v.getCatePath()))
                        .map(contractCate -> Arrays.asList(contractCate.getCatePath().split(Constants.CATE_PATH_SPLITTER))).
                                flatMap(Collection::stream).filter(id -> !"0".equals(id)).map(Long::valueOf)
                        .distinct().collect(Collectors.toList());
                List<GoodsCate> parentCateList = goodsCateRepository.queryCates(paretnCateIds, DeleteFlag.NO);
                parentCateList.addAll(thirdCates);
                return JSON.parseArray(JSON.toJSONString(parentCateList),GoodsCateVO.class);
            }else {
                return new ArrayList<>();
            }
        }else {
            return new ArrayList<>();
        }
    }
}
