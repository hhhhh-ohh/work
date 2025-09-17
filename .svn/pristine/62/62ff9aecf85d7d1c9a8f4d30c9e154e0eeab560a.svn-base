package com.wanmi.sbc.goods.info.service;

import com.google.common.base.Joiner;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.goods.brand.model.root.ContractBrand;
import com.wanmi.sbc.goods.brand.model.root.GoodsBrand;
import com.wanmi.sbc.goods.brand.repository.ContractBrandRepository;
import com.wanmi.sbc.goods.brand.request.ContractBrandQueryRequest;
import com.wanmi.sbc.goods.cate.model.root.ContractCate;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.repository.ContractCateRepository;
import com.wanmi.sbc.goods.cate.repository.GoodsCateRepository;
import com.wanmi.sbc.goods.cate.request.ContractCateQueryRequest;
import com.wanmi.sbc.goods.storecate.model.root.StoreCate;
import com.wanmi.sbc.goods.storecate.repository.StoreCateRepository;
import com.wanmi.sbc.goods.storecate.request.StoreCateQueryRequest;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponQueryProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponListRequest;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCouponVO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品EXCEL处理服务
 * Created by dyt on 2017/8/17.
 */
@Service
public class S2bGoodsExcelService {

    @Value("classpath:/download/supplier_iep_goods_template.xls")
    private Resource templateFileIEP;

    @Value("classpath:/download/supplier_goods_template.xls")
    private Resource templateFile;

    @Value("classpath:/download/supplier_virtual_goods_template.xls")
    private Resource templateFileVirtual;

    @Value("classpath:/download/supplier_card_goods_template.xls")
    private Resource templateFileCard;

    @Autowired
    private GoodsCateRepository goodsCateRepository;

    @Autowired
    private ContractCateRepository contractCateRepository;

    @Autowired
    private StoreCateRepository storeCateRepository;

    @Autowired
    private ContractBrandRepository contractBrandRepository;

    @Autowired
    private ElectronicCouponQueryProvider electronicCouponQueryProvider;
    /**
     * 导出模板
     * 加载xls已有模板，填充商品分类、品牌数据，实现excel下拉列表
     *
     * @return base64位文件字符串
     */
    public String exportTemplate(Long storeId,int type,Integer goodsType) {
        Resource file = getDownLoadFileByType(type,goodsType);

        // 根据店铺获取品牌
        List<GoodsBrand> brands = getGoodsBrands(storeId);
        // 根据店获取平台类目
        List<GoodsCate> cates = getGoodsCates(storeId,goodsType);
        // 根据店铺获取店铺分类
        List<StoreCate> storeCates = getStoreCates(storeId);



        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             InputStream is = file.getInputStream();
             Workbook wk = WorkbookFactory.create(is)) {
            Sheet cateSheet = wk.getSheetAt(1);
            //填放分类数据
            int cateSize = cates.size();
            for (int i = 0; i < cateSize; i++) {
                GoodsCate cate = cates.get(i);
                // 查询商品分类所有父分类名称
                String allCateName = queryParentCate(cate.getCateId());
                cateSheet.createRow(i).createCell(0).setCellValue(String.valueOf(cate.getCateId()).concat("_").concat(allCateName));
            }

            Sheet brandSheet = wk.getSheetAt(2);
            int brandSize = brands.size();
            for (int i = 0; i < brandSize; i++) {
                GoodsBrand brand = brands.get(i);
                brandSheet.createRow(i).createCell(0).setCellValue(String.valueOf(brand.getBrandId()).concat("_").concat(brand.getBrandName()));
            }

            //填放分类数据
            Sheet storeCateSheet = wk.getSheetAt(3);
            int storeCateSize = storeCates.size();
            for (int i = 0; i < storeCateSize; i++) {
                StoreCate cate = storeCates.get(i);
                // 查询店铺分类所有父分类名称
                String storeCateName = queryStoreCate(cate);
                storeCateSheet.createRow(i).createCell(0).setCellValue(String.valueOf(cate.getStoreCateId()).concat("_").concat(storeCateName));
            }

            if (Constants.TWO == goodsType) {
                List<ElectronicCouponVO> electronicCoupons = getElectronicCoupons(storeId);
                //填放电子卡券数据
                Sheet electronicCouponsSheet = wk.getSheetAt(4);
                int electronicCouponsSize = electronicCoupons.size();
                for (int i = 0; i < electronicCouponsSize; i++) {
                    ElectronicCouponVO electronicCouponVO = electronicCoupons.get(i);
                    // 查询店铺分类所有父分类名称
                    electronicCouponsSheet.createRow(i).createCell(0).setCellValue(String.valueOf(electronicCouponVO.getId()).concat("_").concat(electronicCouponVO.getCouponName()));
                }
            }

            wk.write(baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    /**
     * 递归查询商品分类所有父分类名称
     *
     * @param cateId
     * @return
     */
    public String queryParentCate(Long cateId) {
        GoodsCate goodsCate = goodsCateRepository.findById(cateId).orElseGet(GoodsCate::new);
        if (goodsCate.getCateParentId() != 0) {
            String queryParentCate = queryParentCate(goodsCate.getCateParentId());
            return queryParentCate + "-" + goodsCate.getCateName();
        }
        return goodsCate.getCateName();
    }

    /**
     * 递归查询所有店铺分类所有父分类名称
     *
     * @param cate 分类对象
     * @return
     */
    protected String queryStoreCate(StoreCate cate){
        if (Objects.isNull(cate)){
            return "";
        }
        if (cate.getCateGrade() == Constants.TWO) {
            StoreCate storeCate = storeCateRepository.findById(cate.getCateParentId()).orElseGet(StoreCate::new);
            if (Objects.nonNull(storeCate)) {
                return Joiner.on("-").join(storeCate.getCateName(), cate.getCateName());
            }
        }
        return cate.getCateName();
    }

    /***
     * 获得店铺分类
     * @param storeId   店铺ID
     * @return          店铺分类
     */
    protected List<StoreCate> getStoreCates(Long storeId) {
        StoreCateQueryRequest queryRequest = new StoreCateQueryRequest();
        queryRequest.setStoreId(storeId);
        queryRequest.setDelFlag(DeleteFlag.NO);
        List<StoreCate> storeCates = storeCateRepository.findAll(queryRequest.getWhereCriteria());
        return storeCates;
    }

    /***
     * 获得店铺签约商品分类
     * @param storeId   店铺ID
     * @return          店铺签约商品分类
     */
    protected List<GoodsCate> getGoodsCates(Long storeId, Integer goodsType) {

        ContractCateQueryRequest cateQueryRequest = new ContractCateQueryRequest();
        cateQueryRequest.setStoreId(storeId);

        List<Long> cateIds = contractCateRepository.findAll(cateQueryRequest.getWhereCriteria()).stream().map(ContractCate::getGoodsCate).map(GoodsCate::getCateId).collect(Collectors.toList());
        List<GoodsCate> cates = new ArrayList<>();
        if (goodsType == GoodsType.REAL_GOODS.toValue()) {
            cates = goodsCateRepository.queryLeaf(cateIds);
        }else{
            cates = goodsCateRepository.queryLeaf(cateIds,BoolFlag.YES);
        }
        return cates;
    }

    /***
     * 获得店铺签约品牌
     * @param storeId   店铺ID
     * @return          店铺签约商品分类
     */
    protected List<GoodsBrand> getGoodsBrands(Long storeId) {
        ContractBrandQueryRequest contractBrandQueryRequest = new ContractBrandQueryRequest();
        contractBrandQueryRequest.setStoreId(storeId);
        List<GoodsBrand> brands = contractBrandRepository.findAll(contractBrandQueryRequest.getWhereCriteria()).stream()
                .filter(contractBrand -> contractBrand.getGoodsBrand() != null && StringUtils.isNotBlank(contractBrand.getGoodsBrand().getBrandName()))
                .map(ContractBrand::getGoodsBrand).collect(Collectors.toList());
        return brands;
    }


    /***
     * 获得店铺签约品牌
     * @param storeId   店铺ID
     * @return          店铺签约商品分类
     */
    protected List<ElectronicCouponVO>  getElectronicCoupons(Long storeId) {
        List<ElectronicCouponVO> electronicCouponVOList = electronicCouponQueryProvider.list(ElectronicCouponListRequest.builder()
                .storeId(storeId)
                .delFlag(DeleteFlag.NO)
                .build()).getContext().getElectronicCouponVOList();
        return electronicCouponVOList;
    }

    /***
     * 根据类型获得下载文件
     * @param type
     * @return
     */
    protected Resource getDownLoadFileByType(int type,Integer goodsType) {
        Resource file;
        switch (goodsType) {
            case 0:
                file = type == NumberUtils.INTEGER_ZERO ? templateFile : templateFileIEP;
                break;
            case 1:
                file = templateFileVirtual;
                break;
            case 2:
                file = templateFileCard;
                break;
            default:
                file = null;
                break;
        }
        if (file == null || !file.exists())   {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030147);
        }
        return file;
    }
}