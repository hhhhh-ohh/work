package com.wanmi.sbc.excel.service;


import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsByConditionResponse;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author houshuai
 * @date 2022/2/8 14:19
 * @description <p> 营销商品导入相关功能 </p>
 */
public interface MarketingExcelService {


    /**
     * 下载模板文件
     *
     * @param activityType
     * @return
     */
    void downloadTemplate(Integer activityType);

    /**
     * 获取错误文件
     *
     * @param fileExt
     * @return
     */
    void downloadErrorFile(String fileExt);

    /**
     * 上传文件
     *
     * @param uploadFile
     * @return
     */
    String uploadFile(MultipartFile uploadFile);

    /**
     * 获取商品信息
     *
     * @return
     */
    List<GoodsInfoVO> getGoodsInfoList();

    /**
     * 返回文件
     *
     * @param file
     * @param excelName
     */
    default void write(String file, String excelName) {
        try {
            String fileName = URLEncoder.encode(excelName, StandardCharsets.UTF_8.name());
            HttpUtil.getResponse().setHeader("Content-Disposition",
                    String.format("attachment;filename=\"%s\";filename*=\"utf-8''%s\"", fileName, fileName));
            HttpUtil.getResponse()
                    .getOutputStream()
                    .write(Base64.getDecoder().decode(file));
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 校验sku
     * @param skuNoMap
     * @param goodsInfoQueryProvider
     * @param workbook
     * @param isError
     * @param goodsQueryProvider
     * @param idDistribution 社交分销校验
     * @param storeId
     * @param isEnterprise 组合购校验
     * @param excludeProviderFlag 预约预售校验
     */
    default void checkGoodsInfoNo(Map<String, List<Cell>> skuNoMap,
                                  GoodsInfoQueryProvider goodsInfoQueryProvider,
                                  Workbook workbook,
                                  AtomicBoolean isError,
                                  GoodsQueryProvider goodsQueryProvider,
                                  Boolean idDistribution,
                                  Long storeId,
                                  Boolean isEnterprise,
                                  Boolean excludeProviderFlag,
                                  Boolean isFlashSale,
                                  Boolean limitGoodsType) {
        Set<String> skuNoList = skuNoMap.keySet();
        GoodsInfoListByConditionRequest request = GoodsInfoListByConditionRequest.builder()
                .delFlag(DeleteFlag.NO.toValue())
                .storeId(storeId)
                .goodsInfoNos(new ArrayList<>(skuNoList))
                .build();
        //校验sku是否存在，或者重复
        List<GoodsInfoVO> goodsInfoList = goodsInfoQueryProvider.listByCondition(request).getContext().getGoodsInfos();

        if (CollectionUtils.isNotEmpty(goodsInfoList)) {
            Map<String, GoodsInfoVO> goodsInfoMap = goodsInfoList.stream()
                    .collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoNo, Function.identity()));
            Map<String, Integer> goodsSaleTypeMap = this.getGoodsSaleTypeMap(goodsInfoList, goodsQueryProvider,
                    idDistribution,isEnterprise,isFlashSale);
            skuNoMap.forEach((key, value) -> {
                if (Objects.isNull(goodsInfoMap.get(key))) {
                    value.forEach(cell -> {
                        //为单元格设置重复错误提示
                        ExcelHelper.setCellError(workbook, cell, "该商品编码不存在");
                    });
                    isError.set(true);
                } else {
                    GoodsInfoVO goodsInfoVO = goodsInfoMap.get(key);
                    CheckStatus auditStatus = goodsInfoVO.getAuditStatus();
                    if(excludeProviderFlag){
                        if(Objects.nonNull(goodsInfoVO.getProviderId())){
                            value.forEach(cell -> {
                                ExcelHelper.setCellError(workbook, cell, "请填写非供应商商品编码");
                            });
                            isError.set(true);
                        }
                    }
                    if (idDistribution) {
                        Integer saleType = goodsSaleTypeMap.get(goodsInfoVO.getGoodsId());
                        if (Objects.equals(saleType, NumberUtils.INTEGER_ZERO)) {
                            //为单元格设置重复错误提示
                            value.forEach(cell -> {
                                //为单元格设置重复错误提示
                                ExcelHelper.setCellError(workbook, cell, "该编码商品不是零售商品");
                            });
                            isError.set(true);
                        }else if(!Objects.equals(goodsInfoVO.getDistributionGoodsAudit(), DistributionGoodsAudit.COMMON_GOODS)){
                            //为单元格设置重复错误提示
                            value.forEach(cell -> {
                                //为单元格设置重复错误提示
                                ExcelHelper.setCellError(workbook, cell, "该编码商品已存在");
                            });
                            isError.set(true);
                        }
                    }
                    if(isFlashSale){
                        Integer saleType = goodsSaleTypeMap.get(goodsInfoVO.getGoodsId());
                        if (Objects.equals(saleType, NumberUtils.INTEGER_ZERO)) {
                            value.forEach(cell -> {
                                ExcelHelper.setCellError(workbook, cell, "该编码商品不是零售商品");
                            });
                            isError.set(true);
                        }
                    }
                    if (isEnterprise) {
                        Integer saleType = goodsSaleTypeMap.get(goodsInfoVO.getGoodsId());
                        if (Objects.equals(saleType, NumberUtils.INTEGER_ZERO)) {
                            //为单元格设置重复错误提示
                            value.forEach(cell -> {
                                //为单元格设置重复错误提示
                                ExcelHelper.setCellError(workbook, cell, "该编码商品不是零售商品");
                            });
                            isError.set(true);
                        }else if(Objects.nonNull(goodsInfoVO.getEnterPriseAuditState()) &&
                                !Objects.equals(goodsInfoVO.getEnterPriseAuditState(), EnterpriseAuditState.INIT)){
                            //为单元格设置重复错误提示
                            value.forEach(cell -> {
                                //为单元格设置重复错误提示
                                ExcelHelper.setCellError(workbook, cell, "该编码商品已存在");
                            });
                            isError.set(true);
                        }
                    }
                    if (CollectionUtils.size(value) > 1) {
                        value.forEach(cell -> {
                            ExcelHelper.setCellError(workbook, cell, "该商品编码重复");
                        });
                        isError.set(true);
                    } else if (Objects.equals(auditStatus, CheckStatus.FORBADE)) {
                        value.forEach(cell -> {
                            ExcelHelper.setCellError(workbook, cell, "该编码商品禁售中");
                        });
                        isError.set(true);
                    } else if (Objects.equals(auditStatus, CheckStatus.WAIT_CHECK) || Objects.equals(auditStatus, CheckStatus.NOT_PASS)) {
                        value.forEach(cell -> {
                            ExcelHelper.setCellError(workbook, cell, "该编码商品审核中");
                        });
                        isError.set(true);
                    }

                    if (limitGoodsType && GoodsType.REAL_GOODS.toValue() != goodsInfoVO.getGoodsType()) {
                        value.forEach(cell -> {
                            ExcelHelper.setCellError(workbook, cell, "该商品不是实物商品");
                        });
                        isError.set(true);
                    }
                }
            });
        } else if (CollectionUtils.isEmpty(goodsInfoList) && CollectionUtils.isNotEmpty(skuNoList)) {
            skuNoMap.forEach((key, value) -> {
                value.forEach(cell -> {
                    //为单元格设置重复错误提示
                    ExcelHelper.setCellError(workbook, cell, "该商品编码不存在");
                });
                isError.set(true);
            });
        }
    }

    /**
     * getCell
     *
     * @param cells
     * @param index
     * @return
     */
    default Cell getCell(Cell[] cells, int index) {
        return cells[index];
    }

    /**
     * 根据goodsInfoId查询goods信息是否是零售商品
     *
     * @param goodsInfoList
     * @param goodsQueryProvider
     * @param idDistribution
     * @return
     */
    default Map<String, Integer> getGoodsSaleTypeMap(List<GoodsInfoVO> goodsInfoList,
                                                     GoodsQueryProvider goodsQueryProvider,
                                                     Boolean idDistribution,
                                                     Boolean isEnterprise,
                                                     Boolean isFlashSale) {
        if (!(idDistribution || isEnterprise || isFlashSale)) {
            return Collections.emptyMap();
        }
        List<String> goodsIdList = goodsInfoList.stream()
                .map(GoodsInfoVO::getGoodsId)
                .distinct()
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(goodsIdList)) {
            return Collections.emptyMap();
        }
        GoodsByConditionRequest request = GoodsByConditionRequest.builder()
                .goodsIds(goodsIdList)
                .build();
        GoodsByConditionResponse response = goodsQueryProvider.listByCondition(request).getContext();
        List<GoodsVO> goodsVOList = response.getGoodsVOList();
        if (CollectionUtils.isEmpty(goodsVOList)) {
            return Collections.emptyMap();
        }
        return goodsVOList.stream()
                .collect(Collectors.toMap(GoodsVO::getGoodsId, GoodsVO::getSaleType));
    }

    /**
     * 判断是否是正整数
     *
     * @param numStr
     * @param workbook
     * @param cells
     * @param index
     * @param isError
     */
    default void checkNumber(String numStr, Workbook workbook, Cell[] cells, int index, AtomicBoolean isError) {
        if (!NumberUtils.isDigits(numStr)) {
            ExcelHelper.setCellError(workbook, getCell(cells, index), "此项必须为整数");
            isError.set(true);
        } else if (NumberUtils.toLong(numStr) < 1 || NumberUtils.toLong(numStr) > 9999999) {
            ExcelHelper.setCellError(workbook, getCell(cells, index), "此项必须在1-9999999范围内");
            isError.set(true);
        }
    }

    /**
     * 校验金额
     * @param priceStr
     * @param workbook
     * @param cells
     * @param index
     * @param isError
     */
    default void checkPrice(String priceStr, Workbook workbook, Cell[] cells, int index, AtomicBoolean isError){
        if(!(priceStr.matches("^[+]?([0-9]+(.[0-9]{1,2})?)$"))){
            ExcelHelper.setCellError(workbook, getCell(cells, index), "此项必须为两位小数或大于0的整数");
            isError.set(true);
        } else if(NumberUtils.createBigDecimal(priceStr).compareTo(BigDecimal.ZERO) < 0
                || NumberUtils.createBigDecimal(priceStr).compareTo(new BigDecimal("9999999.99")) > 0){
            ExcelHelper.setCellError(workbook, getCell(cells, index), "此项必须在0-9999999.99范围内");
            isError.set(true);
        }
    }

}
