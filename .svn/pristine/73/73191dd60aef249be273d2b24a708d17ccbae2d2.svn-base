package com.wanmi.sbc.goods.contract.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.api.response.store.NoDeleteStoreByIdResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.ContractCateAuditQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandAuditListRequest;
import com.wanmi.sbc.goods.api.request.cate.ContractCateAuditListRequest;
import com.wanmi.sbc.goods.api.request.contract.ContractAuditCheckRequest;
import com.wanmi.sbc.goods.api.request.contract.ContractAuditSaveRequest;
import com.wanmi.sbc.goods.api.response.brand.ContractBrandAuditListResponse;
import com.wanmi.sbc.goods.api.response.cate.ContractCateAuditListResponse;
import com.wanmi.sbc.goods.bean.dto.ContractBrandAuditSaveDTO;
import com.wanmi.sbc.goods.bean.dto.ContractBrandAuditSimpleDTO;
import com.wanmi.sbc.goods.bean.dto.ContractCateAuditSaveDTO;
import com.wanmi.sbc.goods.bean.dto.ContractCateAuditSimpleDTO;
import com.wanmi.sbc.goods.bean.vo.ContractBrandAuditVO;
import com.wanmi.sbc.goods.bean.vo.ContractCateAuditVO;
import com.wanmi.sbc.goods.brand.model.root.ContractBrand;
import com.wanmi.sbc.goods.brand.model.root.GoodsBrand;
import com.wanmi.sbc.goods.brand.repository.ContractBrandRepository;
import com.wanmi.sbc.goods.brand.request.ContractBrandAuditSaveRequest;
import com.wanmi.sbc.goods.brand.request.ContractBrandQueryRequest;
import com.wanmi.sbc.goods.brand.request.ContractBrandSaveRequest;
import com.wanmi.sbc.goods.brand.service.ContractBrandAuditService;
import com.wanmi.sbc.goods.brand.service.ContractBrandService;
import com.wanmi.sbc.goods.cate.model.root.ContractCate;
import com.wanmi.sbc.goods.cate.model.root.ContractCateAudit;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.repository.ContractCateRepository;
import com.wanmi.sbc.goods.cate.request.ContractCateAuditSaveRequest;
import com.wanmi.sbc.goods.cate.request.ContractCateQueryRequest;
import com.wanmi.sbc.goods.cate.request.ContractCateSaveRequest;
import com.wanmi.sbc.goods.cate.service.ContractCateAuditService;
import com.wanmi.sbc.goods.cate.service.ContractCateService;
import com.wanmi.sbc.goods.contract.request.ContractRequest;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 签约信息服务
 * Created by sunkun on 2017/11/2.
 */
@Service
public class ContractService {

    @Resource
    private ContractCateService contractCateService;

    @Resource
    private ContractBrandService contractBrandService;

    @Resource
    private ContractCateAuditService contractCateAuditService;

    @Resource
    private ContractBrandAuditService contractBrandAuditService;

    @Resource
    private StoreQueryProvider storeQueryProvider;

    @Resource
    private ContractBrandAuditQueryProvider contractBrandAuditQueryProvider;

    @Resource
    private ContractCateAuditQueryProvider contractCateAuditQueryProvider;

    @Resource
    private ContractCateRepository contractCateRepository;

    @Resource
    private ContractBrandRepository contractBrandRepository;

    /**
     * 签约信息更新
     *
     * @param request 签约信息
     */
    @Transactional
    public List<Long> renewal(ContractRequest request) {
        List<Long> ids = new ArrayList<>();//品牌id
        //签约分类删除
        if (CollectionUtils.isNotEmpty(request.getDelCateIds())) {
            contractCateService.cateDelVerify(request.getDelCateIds(), request.getStoreId());
            contractCateService.deleteByIds(request.getDelCateIds(), request.getStoreId());
        }
        //签约品牌删除
        if (CollectionUtils.isNotEmpty(request.getDelBrandIds())) {
            ids = contractBrandService.deleteByIds(request.getDelBrandIds(), request.getStoreId());
        }
        //签约分类更新
        if (CollectionUtils.isNotEmpty(request.getCateSaveRequests())) {
            renewalCate(request.getCateSaveRequests(), request.getStoreId());
        }
        //签约品牌更新
        if (CollectionUtils.isNotEmpty(request.getBrandSaveRequests())) {
            renewalBrands(request.getBrandSaveRequests(), request.getStoreId());
        }
        return ids;
    }

    /**
     * 二次签约信息更新
     *
     * @param request 签约信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void renewalAudit(ContractRequest request) {
        //校验删除的签约类目是否有关联商品
        //签约分类删除
        if (CollectionUtils.isNotEmpty(request.getDelCateIds())) {
            contractCateService.cateDelVerify(request.getDelCateIds(), request.getStoreId());
        }
        if (CollectionUtils.isNotEmpty(request.getDelBrandIds())){
            contractBrandService.brandDelVerify(request.getDelBrandIds(),request.getStoreId());
        }

        //保存逻辑删除的数据(分类+品牌)
        //签约分类删除
        if (CollectionUtils.isNotEmpty(request.getDelCateIds())) {
            ContractCateQueryRequest contractCateQueryRequest = new ContractCateQueryRequest();
            contractCateQueryRequest.setCateIds(request.getDelCateIds());
            contractCateQueryRequest.setStoreId(request.getStoreId());
            //查询需要删除的签约分类数据  设置删除标记 插入二次签约新表中
            List<ContractCate> contractCates = contractCateService.queryContractCateList(contractCateQueryRequest);

            //需要删除的二次签约类目
            List<ContractCateAudit> contractCateAudits = new ArrayList<>();

            if(CollectionUtils.isNotEmpty(contractCates)){
                contractCates.forEach(contractCate -> {
                    ContractCateAudit contractCateAudit = new ContractCateAudit();
                    KsBeanUtil.copyPropertiesThird(contractCate, contractCateAudit);
                    contractCateAudit.setDeleteFlag(DeleteFlag.YES);
                    contractCateAudits.add(contractCateAudit);
                });
            }

            //插入需要删除的二次签约类目
            if(CollectionUtils.isNotEmpty(contractCateAudits)){
                List<ContractCateAuditSaveRequest> cateSaveRequests = new ArrayList<>();
                contractCateAudits.forEach(cate -> {
                    ContractCateAuditSaveRequest contractCateAuditSaveRequest = new ContractCateAuditSaveRequest();
                    KsBeanUtil.copyPropertiesThird(cate,contractCateAuditSaveRequest);
                    contractCateAuditSaveRequest.setCateId(cate.getGoodsCate().getCateId());
                    cateSaveRequests.add(contractCateAuditSaveRequest);
                });

                renewalCateAudit(cateSaveRequests, request.getStoreId());
            }
        }

        //插入二次签约分类
        if (CollectionUtils.isNotEmpty(request.getCateSaveRequests())) {
            List<ContractCateAuditSaveRequest> cateSaveRequests
                    = KsBeanUtil.copyListProperties(request.getCateSaveRequests(), ContractCateAuditSaveRequest.class);
            cateSaveRequests.forEach(cate -> cate.setDeleteFlag(DeleteFlag.NO));
            renewalCateAudit(cateSaveRequests, request.getStoreId());
        }
        //插入二次签约品牌
        if (CollectionUtils.isNotEmpty(request.getBrandSaveRequests())) {
            List<ContractBrandAuditSaveRequest> brandAuditSaveRequests =
                    KsBeanUtil.copyListProperties(request.getBrandSaveRequests(), ContractBrandAuditSaveRequest.class);
            brandAuditSaveRequests.forEach(brand -> brand.setDeleteFlag(DeleteFlag.NO));
            renewalBrandsAudit(brandAuditSaveRequests, request.getStoreId());
        }
    }


    /**
     * 更新签约分类
     *
     * @param list 签约分类信息批量数据
     * @param storeId 店铺id
     */
    @Transactional
    public void renewalCate(List<ContractCateSaveRequest> list, Long storeId) {
        list.forEach(info -> {
            info.setStoreId(storeId);
            if (info.getContractCateId() == null) {
                contractCateService.add(info);
            } else {
                contractCateService.update(info);
            }
        });
    }

    /**
     * 更新二次签约分类
     *
     * @param list 签约分类信息批量数据
     * @param storeId 店铺id
     */
    @Transactional
    public void renewalCateAudit(List<ContractCateAuditSaveRequest> list, Long storeId) {
        list.forEach(info -> {
            info.setStoreId(storeId);
            info.setContractCateId(null);
            contractCateAuditService.add(info);
        });
    }

    /**
     * 更新签约品牌
     *
     * @param list 签约品牌信息
     * @param storeId 瘟铺od
     */
    @Transactional
    public void renewalBrands(List<ContractBrandSaveRequest> list, Long storeId) {
        NoDeleteStoreByIdRequest noDeleteStoreByIdRequest = new NoDeleteStoreByIdRequest();
        noDeleteStoreByIdRequest.setStoreId(storeId);
        BaseResponse<NoDeleteStoreByIdResponse>  noDeleteStoreByIdResponseBaseResponse  = storeQueryProvider.getNoDeleteStoreById(noDeleteStoreByIdRequest);
        StoreVO storeVO = noDeleteStoreByIdResponseBaseResponse.getContext().getStoreVO();
        list.forEach(info -> {

            info.setStoreId(storeId);
            if (info.getContractBrandId() != null && info.getContractBrandId() > 0) {
                //商家自定义品牌修改且店铺已审核 = 非法输入
                if (Objects.nonNull(info.getCheckBrandId())) {
                    if (storeVO.getAuditState() == CheckState.CHECKED) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                }
                contractBrandService.update(info);
            } else {
                //商家自定义品牌新增且店铺已审核 = 非法输入
                if (Objects.isNull(info.getBrandId())) {
                    if (storeVO.getAuditState() == CheckState.CHECKED) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                }
                contractBrandService.add(info);
            }
        });
    }

    /**
     * 更新二次签约品牌
     *
     * @param list 签约品牌信息
     * @param storeId 店铺id
     */
    @Transactional
    public void renewalBrandsAudit(List<ContractBrandAuditSaveRequest> list, Long storeId) {
        list.forEach(info -> {
            info.setStoreId(storeId);
            info.setContractBrandId(null);
            contractBrandAuditService.add(info);
        });
    }

    /**
     * 校验并审核二次签约信息
     * @param request
     * @return
     */
    @GlobalTransactional
    public void checkAudit(ContractAuditCheckRequest request){

        if(request.getContractAuditState().equals(CheckState.CHECKED)){
            //二次签约品牌
            ContractBrandAuditListResponse contractBrandAuditListResponse = contractBrandAuditQueryProvider.list(
                    ContractBrandAuditListRequest
                            .builder()
                            .storeId(request.getStoreId())
                            .build())
                    .getContext();

            //二次签约分类
            ContractCateAuditListResponse contractCateAuditListResponse = contractCateAuditQueryProvider.list(
                    ContractCateAuditListRequest
                            .builder()
                            .storeId(request.getStoreId())
                            .build())
                    .getContext();


            if(CollectionUtils.isNotEmpty(contractCateAuditListResponse.getContractCateList())){
                //覆盖二次签约分类信息
                //校验删除的签约类目是否有关联商品
                List<Long> delCateIds =
                        contractCateAuditListResponse.getContractCateList()
                                .stream()
                                .filter(cate -> cate.getDeleteFlag().equals(DeleteFlag.YES))
                                .map(ContractCateAuditVO::getCateId)
                                .collect(Collectors.toList());

                //校验删除的分类
                contractCateService.cateDelVerify(delCateIds,request.getStoreId());

                List<ContractCateAuditVO> cateAuditSaveRequests =
                        contractCateAuditListResponse.getContractCateList()
                                .stream()
                                .filter(cate -> cate.getDeleteFlag().equals(DeleteFlag.NO))
                                .collect(Collectors.toList());

                //先删除再新增
                if(CollectionUtils.isNotEmpty(cateAuditSaveRequests)){
                    contractCateService.deleteByStoreId(request.getStoreId());
                    cateAuditSaveRequests.forEach(cate -> {
                        ContractCate contractCate = new ContractCate();
                        KsBeanUtil.copyProperties(cate,contractCate);
                        contractCate.setContractCateId(null);

                        GoodsCate goodsCate = new GoodsCate();
                        goodsCate.setCateId(cate.getCateId());
                        contractCate.setGoodsCate(goodsCate);

                        contractCateRepository.save(contractCate);
                    });
                }
            }

            if(CollectionUtils.isNotEmpty(contractBrandAuditListResponse.getContractBrandVOList())){
                //覆盖二次签约品牌信息
                List<ContractBrandAuditVO> brandAuditSaveRequests =
                        contractBrandAuditListResponse.getContractBrandVOList()
                                .stream()
                                .filter(brand -> brand.getDeleteFlag().equals(DeleteFlag.NO))
                                .collect(Collectors.toList());

                //先删除再新增
                if(CollectionUtils.isNotEmpty(brandAuditSaveRequests)){
                    contractBrandService.deleteByStoreId(request.getStoreId());
                    brandAuditSaveRequests.forEach(brand -> {
                        ContractBrand contractBrand = new ContractBrand();
                        KsBeanUtil.copyProperties(brand,contractBrand);
                        contractBrand.setContractBrandId(null);

                        GoodsBrand goodsBrand = new GoodsBrand();
                        goodsBrand.setBrandId(brand.getBrandId());
                        contractBrand.setGoodsBrand(goodsBrand);

                        contractBrandRepository.save(contractBrand);
                    });
                }
            }
        }

        //删除店铺下二次签约的所有数据
        contractCateAuditService.deleteByStoreId(request.getStoreId());
        contractBrandAuditService.deleteByStoreId(request.getStoreId());

    }

    /**
     * 校验编辑前后信息是否有变动
     * @param request
     * @return
     */
    public Boolean checkAuditData(ContractAuditSaveRequest request){
        //二次签约品牌
        List<ContractBrandAuditSaveDTO> contractBrandAuditSaveDTOList = request.getBrandSaveRequests();

        ContractBrandQueryRequest queryRequest = new ContractBrandQueryRequest();
        KsBeanUtil.copyPropertiesThird(request, queryRequest);
        List<ContractBrand> contractBrandList = contractBrandRepository.findAll(queryRequest.getWhereCriteria());

        if(CollectionUtils.isNotEmpty(contractBrandList)){
            List<ContractBrandAuditSimpleDTO> oldBrand = KsBeanUtil.convertList(contractBrandList,ContractBrandAuditSimpleDTO.class);
            //如果编辑前后品牌都是有数据 则排序后比对hashcode值
            if(CollectionUtils.isNotEmpty(contractBrandAuditSaveDTOList)){
                List<ContractBrandAuditSimpleDTO> newBrand = KsBeanUtil.convertList(contractBrandAuditSaveDTOList,ContractBrandAuditSimpleDTO.class);

                newBrand.forEach(brand -> brand.setStoreId(request.getStoreId()));
                Collections.sort(newBrand,Comparator.comparing(ContractBrandAuditSimpleDTO::getBrandId));
                Collections.sort(oldBrand,Comparator.comparing(ContractBrandAuditSimpleDTO::getBrandId));

                if(newBrand.hashCode() != oldBrand.hashCode()){
                    return true;
                }
            }else{
                return true;
            }
        }else if(CollectionUtils.isNotEmpty(contractBrandAuditSaveDTOList)){
            return true;
        }

        //二次签约分类
        List<ContractCateAuditSaveDTO> contractCateAuditSaveDTOList = request.getCateSaveRequests();

        ContractCateQueryRequest contractCateQueryRequest =
                KsBeanUtil.convert(request, ContractCateQueryRequest.class);
        List<ContractCate> contractCateList = contractCateRepository.findAll(contractCateQueryRequest.getWhereCriteria());

        //如果编辑前后品牌都是有数据 则排序后比对hashcode值
        if(CollectionUtils.isNotEmpty(contractCateList)){
            List<ContractCateAuditSimpleDTO> oldCate = KsBeanUtil.convertList(contractCateList,ContractCateAuditSimpleDTO.class);
            if(CollectionUtils.isNotEmpty(contractCateAuditSaveDTOList)){
                List<ContractCateAuditSimpleDTO> newCate = KsBeanUtil.convertList(contractCateAuditSaveDTOList,ContractCateAuditSimpleDTO.class);

                newCate.forEach(cate -> cate.setStoreId(request.getStoreId()));
                Collections.sort(newCate,Comparator.comparing(ContractCateAuditSimpleDTO::getCateId));
                Collections.sort(oldCate,Comparator.comparing(ContractCateAuditSimpleDTO::getCateId));

                if(newCate.hashCode() != oldCate.hashCode()){
                    return true;
                }
            }else{
                return true;
            }
        }else if(CollectionUtils.isNotEmpty(contractCateAuditSaveDTOList)){
            return true;
        }


        return false;
    }

}
