package com.wanmi.sbc.excel;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.ImmutableMap;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.MarketingAllType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.excel.service.MarketingExcelService;
import com.wanmi.sbc.excel.service.impl.*;
import com.wanmi.sbc.goods.api.request.excel.MarketingTemplateRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import com.wanmi.sbc.system.service.SystemPointsConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.constraints.NotNull;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author houshuai
 * @date 2022/2/8 16:44
 * @description <p> 营销活动商品导入功能 </p>
 */
@Tag(name = "MarketingExcelController", description = "营销活动商品导入功能")
@RestController
@Validated
@RequestMapping("/marketing")
public class MarketingExcelController {

    @Autowired
    private Map<String, MarketingExcelService> marketingExcelServiceMap;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    private static final Map<MarketingAllType, Class<?>> CLASS = ImmutableMap.<MarketingAllType, Class<?>> builder()
            .put(MarketingAllType.GROUPON, GrouponExcelServiceImpl.class)
            .put(MarketingAllType.FLASH_SALE, FlashSaleExcelServiceImpl.class)
            .put(MarketingAllType.SUIT, SuitExcelServiceImpl.class)
            .put(MarketingAllType.RESERVATION, ReservationExcelServiceImpl.class)
            .put(MarketingAllType.PRESALE, PresaleExcelServiceImpl.class)
            .put(MarketingAllType.BUYOUT_PRICE, BuyoutPriceExcelServiceImpl.class)
            .put(MarketingAllType.DISCOUNT, DiscountExcelServiceImpl.class)
            .put(MarketingAllType.DISTRIBUTION, DistributionExcelServiceImpl.class)
            .put(MarketingAllType.ENTERPRISE, EnterpriseExcelServiceImpl.class)
            .put(MarketingAllType.LIMIT_BUY, LimitBuyExcelServiceImpl.class)
            .put(MarketingAllType.RESTRICTED_SALES, RestrictedSalesExcelServiceImpl.class)
            .put(MarketingAllType.FULL_REDUCTION, FullReductionExcelServiceImpl.class)
            .put(MarketingAllType.EARNEST, EarnestExcelServiceImpl.class)
            .put(MarketingAllType.FULL_RETURN, FullReturnExcelServiceImpl.class)
            .put(MarketingAllType.PREFERENTIAL, PreferentialExcelServiceImpl.class)
            .build();

    /**
     * 下载员工导入模版
     *
     */
    @Operation(summary = "下载员工导入模版")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @GetMapping("/goods/template/{encrypted}")
    public void downloadTemplate(@PathVariable String encrypted) {
        //获取入参
        Integer activityType = this.getActivityType(encrypted);
        //获取beanName
        String beanName = this.getBeanName(activityType);
        MarketingExcelService marketingExcelService = marketingExcelServiceMap.get(beanName);
        marketingExcelService.downloadTemplate(activityType);
    }

    /**
     * 上传文件
     */
    @Operation(summary = "上传文件")
    @PostMapping("/excel/upload")
    public BaseResponse<String> upload(@RequestParam("uploadFile") MultipartFile uploadFile,
                                       @NotNull @RequestParam Integer activityType) {
        //获取beanName
        String beanName = this.getBeanName(activityType);
        MarketingExcelService marketingExcelService = marketingExcelServiceMap.get(beanName);
        String fileExt = marketingExcelService.uploadFile(uploadFile);
        return BaseResponse.success(fileExt);
    }

    /**
     * 下载错误文件
     * @param ext
     * @param decrypted
     */
    @Operation(summary = "下载错误文件")
    @GetMapping("/excel/err/{ext}/{decrypted}")
    public void downErrExcel(@PathVariable String ext, @PathVariable String decrypted) {
        if (!StringUtils.equalsAnyIgnoreCase(ext, Constants.XLS,Constants.XLSX)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //获取入参
        Integer activityType = this.getActivityType(decrypted);
        //获取beanName
        String beanName = this.getBeanName(activityType);
        MarketingExcelService marketingExcelService = marketingExcelServiceMap.get(beanName);
        marketingExcelService.downloadErrorFile(ext);
    }

    /**
     * 下载错误文件
     */
    @Operation(summary = "查询导入数据")
    @GetMapping("/goods/list/{activityType}")
    public BaseResponse<List<GoodsInfoVO>> getGoodsInfoList(@PathVariable Integer activityType) {
        //获取beanName
        String beanName = this.getBeanName(activityType);
        MarketingExcelService marketingExcelService = marketingExcelServiceMap.get(beanName);
        List<GoodsInfoVO> goodsInfoList = marketingExcelService.getGoodsInfoList();
        systemPointsConfigService.clearBuyPointsForGoodsInfoVO(goodsInfoList);
        return BaseResponse.success(goodsInfoList);
    }

    /**
     * 获取spring的beanName
     * @param activityType
     * @return
     */
    private String getBeanName(Integer activityType){
        Class<?> clazz = CLASS.get(MarketingAllType.values()[activityType]);
        if(Objects.isNull(clazz)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //获取类名
        String className = ClassUtils.getShortClassName(clazz);
        //首字母小写
        String defaultBeanName = StringUtils.uncapitalize(className);
        //获取当前类上的service注解
        Service service = AnnotationUtils.getAnnotation(clazz, Service.class);
        if(Objects.isNull(service)){
            Component component = AnnotationUtils.getAnnotation(clazz, Component.class);
            Assert.notNull(component,"指定@Component或@Service注解中的一种");
            String beanName = component.value();
            return StringUtils.defaultIfBlank(beanName,defaultBeanName);
        }
        //获取注解beanName
        String beanName = service.value();
        return StringUtils.defaultIfBlank(beanName,defaultBeanName);
    };

    /**
     * 获取入参
     * @param encrypted
     * @return
     */
    private Integer getActivityType(String encrypted){
        byte[] decode = Base64.getUrlDecoder().decode(encrypted);
        String decrypted = new String(decode, StandardCharsets.UTF_8);
        MarketingTemplateRequest request = JSON.parseObject(decrypted, MarketingTemplateRequest.class);
        Integer activityType = request.getActivityType();
        if(Objects.isNull(activityType)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return activityType;
    }
}
