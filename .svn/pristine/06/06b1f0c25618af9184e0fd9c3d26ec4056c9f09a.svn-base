package com.wanmi.sbc.payingmemberlevel.excel;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.MarketingAllType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

public interface PayingMemberExcelService {

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
    String uploadFile(MultipartFile uploadFile,PayingMemberTemplateRequest payingMemberTemplateRequest);

    /**
     * 获取商品信息
     *
     * @return
     */
    List getGoodsInfoList();

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
     * @param workbook
     * @param isError
     */
    default void checkGoodsInfoNo(Map<String, List<Cell>> skuNoMap,
                                  List<GoodsInfoVO> goodsInfoList,
                                  Workbook workbook,
                                  AtomicBoolean isError,
                                  PayingMemberTemplateRequest payingMemberTemplateRequest) {
        Set<String> skuNoList = skuNoMap.keySet();
        Integer activityType = payingMemberTemplateRequest.getActivityType();

        if (CollectionUtils.isNotEmpty(goodsInfoList)) {
            Map<String, GoodsInfoVO> goodsInfoMap = goodsInfoList.stream()
                    .collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoNo, Function.identity()));
            skuNoMap.forEach((key, value) -> {
                if (Objects.isNull(goodsInfoMap.get(key))) {
                    value.forEach(cell -> {
                        //为单元格设置重复错误提示
                        ExcelHelper.setCellError(workbook, cell, "该商品编码不存在");
                    });
                    isError.set(true);
                } else {
                    GoodsInfoVO goodsInfoVO = goodsInfoMap.get(key);
                    Integer saleType = goodsInfoVO.getSaleType();
                    if(saleType == Constants.ZERO) {
                        value.forEach(cell -> {
                            ExcelHelper.setCellError(workbook, cell, "该商品不能是批发商品");
                        });
                        isError.set(true);
                    }
                    Integer levelStoreRange = payingMemberTemplateRequest.getLevelStoreRange();
                    Integer levelDiscountType = payingMemberTemplateRequest.getLevelDiscountType();
                    if (activityType == MarketingAllType.PAYING_MEMBER_DISCOUNT.toValue()) {
                        //付费会员等级商家范围：0.自营商家
                        if (NumberUtils.INTEGER_ZERO.equals(levelStoreRange)) {
                            // 如果是第三方
                            if (goodsInfoVO.getCompanyType().equals(BoolFlag.YES)) {
                                value.forEach(cell -> {
                                    ExcelHelper.setCellError(workbook, cell, "该商品不属于自营商家商品");
                                });
                                isError.set(true);
                            }
                        } else {
                            //付费会员等级商家范围：1.自定义选择
                            List<Long> storeIdList = payingMemberTemplateRequest.getStoreIdList();
                            if (!storeIdList.contains(goodsInfoVO.getStoreId())) {
                                value.forEach(cell -> {
                                    ExcelHelper.setCellError(workbook, cell, "该商品不属于自定义选择商家的商品");
                                });
                                isError.set(true);
                            }
                        }
                    } else if (activityType == MarketingAllType.PAYING_MEMBER_RECOMMEND.toValue()){
                        //付费会员等级商家范围：0.自营商家
                        if (NumberUtils.INTEGER_ZERO.equals(levelStoreRange)) {
                            if (NumberUtils.INTEGER_ZERO.equals(levelDiscountType)) {
                                // 如果是第三方
                                if (goodsInfoVO.getCompanyType().equals(BoolFlag.YES)) {
                                    value.forEach(cell -> {
                                        ExcelHelper.setCellError(workbook, cell, "该商品不属于付费会员折扣商品,超出推荐商品范围");
                                    });
                                    isError.set(true);
                                }
                            } else {
                                List<String> skuIdList = payingMemberTemplateRequest.getSkuIdList();
                                if (!skuIdList.contains(goodsInfoVO.getGoodsInfoId())) {
                                    value.forEach(cell -> {
                                        ExcelHelper.setCellError(workbook, cell, "该商品不属于付费会员折扣商品,超出推荐商品范围");
                                    });
                                    isError.set(true);
                                }
                            }
                        } else {
                            //付费会员等级商家范围：1.自定义选择
                            if (NumberUtils.INTEGER_ZERO.equals(levelDiscountType)) {
                                List<Long> storeIdList = payingMemberTemplateRequest.getStoreIdList();
                                if (!storeIdList.contains(goodsInfoVO.getStoreId())) {
                                    value.forEach(cell -> {
                                        ExcelHelper.setCellError(workbook, cell, "该商品不属于付费会员折扣商品,超出推荐商品范围");
                                    });
                                    isError.set(true);
                                }
                            } else {
                                List<String> skuIdList = payingMemberTemplateRequest.getSkuIdList();
                                if (!skuIdList.contains(goodsInfoVO.getGoodsInfoId())) {
                                    value.forEach(cell -> {
                                        ExcelHelper.setCellError(workbook, cell, "该商品不属于付费会员折扣商品");
                                    });
                                    isError.set(true);
                                }
                            }
                        }
                    }
                    CheckStatus auditStatus = goodsInfoVO.getAuditStatus();
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
     * 校验折扣
     * @param priceStr
     * @param workbook
     * @param cells
     * @param index
     * @param isError
     */
    default void checkDiscount(String priceStr, Workbook workbook, Cell[] cells, int index, AtomicBoolean isError){
        if (StringUtils.isEmpty(priceStr)) {
            priceStr = String.valueOf(Constants.TEN);
        }
        if(!(priceStr.matches("^[+]?([0-9]+(.[0-9]{1,2})?)$"))){
            ExcelHelper.setCellError(workbook, getCell(cells, index), "此项必须为两位小数或大于0的整数");
            isError.set(true);
        } else if(NumberUtils.createBigDecimal(priceStr).compareTo(BigDecimal.ZERO) < 0
                || NumberUtils.createBigDecimal(priceStr).compareTo(new BigDecimal("10.00")) > 0){
            ExcelHelper.setCellError(workbook, getCell(cells, index), "此项必须在0-10.00范围内");
            isError.set(true);
        }
    }
}
