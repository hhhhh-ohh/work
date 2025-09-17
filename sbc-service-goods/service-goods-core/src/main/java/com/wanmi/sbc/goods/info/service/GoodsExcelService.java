package com.wanmi.sbc.goods.info.service;

import com.google.common.collect.ImmutableMap;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.api.request.excel.MarketingTemplateRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.brand.model.root.GoodsBrand;
import com.wanmi.sbc.goods.brand.repository.GoodsBrandRepository;
import com.wanmi.sbc.goods.brand.request.GoodsBrandQueryRequest;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.repository.GoodsCateRepository;
import jakarta.annotation.PostConstruct;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 商品EXCEL处理服务
 * Created by dyt on 2017/8/17.
 */
@Service
public class GoodsExcelService {

    @Value("classpath:/download/goods_template.xlsx")
    private Resource templateFile;

    @Value("classpath:order_for_customer_template.xlsx")
    private Resource orderForCustomerTemplate;

    @Autowired
    private GoodsCateRepository goodsCateRepository;

    @Autowired
    private GoodsBrandRepository goodsBrandRepository;

    @Value("classpath:/download/groupon_goods_template.xlsx")
    private Resource grouponTemplateFile;

    @Value("classpath:/download/flash_sale_goods_template.xlsx")
    private Resource flashSaleTemplateFile;

    @Value("classpath:/download/suit_goods_template.xlsx")
    private Resource suitTemplateFile;

    @Value("classpath:/download/reservation_goods_template.xlsx")
    private Resource reservationTemplateFile;

    @Value("classpath:/download/presale_goods_template.xlsx")
    private Resource presaleTemplateFile;

    @Value("classpath:/download/pack_goods_template.xlsx")
    private Resource packTemplateFile;

    @Value("classpath:/download/distribution_goods_template.xlsx")
    private Resource distributionTemplateFile;

    @Value("classpath:/download/enterprise_goods_template.xlsx")
    private Resource enterpriseTemplateFile;

    @Value("classpath:/download/earnest_goods_template.xlsx")
    private Resource earnestTemplateFile;

    @Value("classpath:/download/limit_buy_goods_template.xlsx")
    private Resource limitBuyTemplateFile;

    @Value("classpath:/download/paying_member_discount_template.xlsx")
    private Resource payingMemberDiscountTemplateFile;

    @Value("classpath:/download/paying_member_recommend_template.xlsx")
    private Resource payingMemberRecommendTemplateFile;

    @Value("classpath:/download/batch_goods_stock_template.xlsx")
    private Resource batchGoodsStockTemplateFile;

    private static Map<Integer,Resource> templateFileMap;

    @PostConstruct
    private void initTemplateFile(){
        templateFileMap = ImmutableMap.<Integer, Resource> builder()
                .put(1, grouponTemplateFile)
                .put(2, flashSaleTemplateFile)
                .put(3, suitTemplateFile)
                .put(4, reservationTemplateFile)
                .put(5, presaleTemplateFile)
                //打包一口价和第二件半价模板相同
                .put(6, packTemplateFile)
                .put(7, packTemplateFile)
                .put(8, distributionTemplateFile)
                .put(9, enterpriseTemplateFile)
                .put(10, limitBuyTemplateFile)
                .put(11, packTemplateFile)
                //打包一口价、第二件半价、满折、满减模板相同
                .put(12, packTemplateFile)
                .put(13, packTemplateFile)
                .put(14,earnestTemplateFile)
                .put(16, packTemplateFile)
                .put(17, payingMemberDiscountTemplateFile)
                .put(18, payingMemberRecommendTemplateFile)
                .put(19, packTemplateFile)

                //商品库存批量新增
                .put(20,batchGoodsStockTemplateFile)
                .build();
    }

    /**
     * 导出模板
     * 加载xls已有模板，填充商品分类、品牌数据，实现excel下拉列表
     * @return base64位文件字符串
     */
    public String exportTemplate() {
        if (templateFile == null || !templateFile.exists()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030147);
        }
        List<GoodsCate> cates = goodsCateRepository.queryLeaf();
        List<GoodsBrand> brands = goodsBrandRepository.findAll(GoodsBrandQueryRequest.builder().delFlag(DeleteFlag.NO.toValue()).build().getWhereCriteria());
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             InputStream is = templateFile.getInputStream();
             Workbook wk = WorkbookFactory.create(is)) {
            Sheet sheet = wk.getSheetAt(1);
            //填放分类数据
            int cateSize = cates.size();
            for (int i = 0; i < cateSize; i++) {
                GoodsCate cate = cates.get(i);
                sheet.createRow(i).createCell(0).setCellValue(String.valueOf(cate.getCateId()).concat("_").concat(cate.getCateName()));
            }
            Sheet brandSheet = wk.getSheetAt(2);
            int brandSize = brands.size();
            for (int i = 0; i < brandSize; i++) {
                GoodsBrand brand = brands.get(i);
                brandSheet.createRow(i).createCell(0).setCellValue(String.valueOf(brand.getBrandId()).concat("_").concat(brand.getBrandName()));
            }
            wk.write(baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    /**
     * 导出营销商品模板
     *
     * @return base64位文件字符串
     */
    public String getTemplate(MarketingTemplateRequest request) {
        Integer activityType = request.getActivityType();
        Resource templateFile = templateFileMap.get(activityType);
        if (Objects.isNull(templateFile) || !templateFile.exists()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030147);
        }
        try (InputStream is = templateFile.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Workbook wk = WorkbookFactory.create(is)) {
            wk.write(baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    public String getOrderForCustomerTemplate(){
        if (orderForCustomerTemplate == null || !orderForCustomerTemplate.exists()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030147);
        }

        try (InputStream is = orderForCustomerTemplate.getInputStream();
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             Workbook wk = WorkbookFactory.create(is)) {
             wk.write(baos);
             return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

}