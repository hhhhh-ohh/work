package com.wanmi.sbc.goods.cate.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.cate.model.root.ContractCateAudit;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.repository.ContractCateAuditRepository;
import com.wanmi.sbc.goods.cate.repository.GoodsCateRepository;
import com.wanmi.sbc.goods.cate.request.ContractCateAuditQueryRequest;
import com.wanmi.sbc.goods.cate.request.ContractCateAuditSaveRequest;
import com.wanmi.sbc.goods.cate.response.ContractCateAuditResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 签约分类服务
 * @author wangchao
 */
@Service
@Transactional
public class ContractCateAuditService {

    @Resource
    private ContractCateAuditRepository contractCateAuditRepository;

    @Resource
    private GoodsCateService goodsCateService;

    @Resource
    private GoodsCateRepository goodsCateRepository;

    /**
     * 新增
     *
     * @param request
     */
    public void add(ContractCateAuditSaveRequest request) {
        //查询平台分类
        GoodsCate goodsCate = goodsCateService.findById(request.getCateId());
        if (goodsCate == null) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030004);
        }
        if(goodsCate.getDelFlag() == DeleteFlag.YES){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030007,new Object[]{goodsCate.getCateName()});
        }

        //查询店铺签约分类总数
        request.setCateId(null);
        //保存分类
        ContractCateAudit contractCate = new ContractCateAudit();
        BeanUtils.copyProperties(request, contractCate);

        contractCate.setGoodsCate(goodsCate);
        contractCateAuditRepository.save(contractCate);
    }

    /**
     * 根据平台类目id和店铺id删除签约分类
     * @param storeId
     */
    public void deleteByStoreId(Long storeId) {
        contractCateAuditRepository.deleteByStoreId(storeId);
    }


    /**
     * 查询
     *
     * @param request
     * @return
     */
    public List<ContractCateAuditResponse> queryList(ContractCateAuditQueryRequest request) {
        List<ContractCateAudit> contractCates = contractCateAuditRepository.findAll(request.getWhereCriteria());
        //根据path得到所有上级类目的编号列表
        List<Long> allGoodsCateIds = new ArrayList<>();
        contractCates.stream().map(ContractCateAudit::getGoodsCate).map(GoodsCate::getCatePath).forEach(info -> {
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
        List<ContractCateAuditResponse> contractCateResponseList = new ArrayList<>();
        //填充返回对象
        contractCates.forEach(info -> {
            ContractCateAuditResponse contractCateResponse = new ContractCateAuditResponse();
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
     * 根据分类Ids查询签约分类数量
     * @param ids
     * @return
     */
    public int findCountByIds(List<Long> ids) {
        return contractCateAuditRepository.findCountByIds(ids);
    }

}
