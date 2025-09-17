package com.wanmi.ares.report.goods.service;

import com.wanmi.ares.enums.QueryDateCycle;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.customer.dao.CompanyInfoMapper;
import com.wanmi.ares.report.customer.dao.StoreCateMapper;
import com.wanmi.ares.report.goods.dao.GoodsBrandMapper;
import com.wanmi.ares.report.goods.dao.GoodsCateMapper;
import com.wanmi.ares.report.goods.dao.GoodsStoreCateMapper;
import com.wanmi.ares.report.goods.dao.SkuMapper;
import com.wanmi.ares.report.goods.model.criteria.GoodsInfoQueryCriteria;
import com.wanmi.ares.report.goods.model.criteria.GoodsQueryCriteria;
import com.wanmi.ares.report.goods.model.reponse.GoodsReportResponse;
import com.wanmi.ares.report.goods.model.reponse.SkuReportResponse;
import com.wanmi.ares.report.goods.model.request.GoodsQueryRequest;
import com.wanmi.ares.report.goods.model.root.GoodsReport;
import com.wanmi.ares.report.goods.model.root.SkuReport;
import com.wanmi.ares.request.GoodsBrandQueryRequest;
import com.wanmi.ares.request.GoodsCateQueryRequest;
import com.wanmi.ares.request.GoodsInfoQueryRequest;
import com.wanmi.ares.source.model.root.CompanyInfo;
import com.wanmi.ares.source.model.root.GoodsBrand;
import com.wanmi.ares.source.model.root.GoodsInfo;
import com.wanmi.ares.source.service.GoodsInfoService;
import com.wanmi.ares.utils.Constants;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.sbc.common.enums.StoreType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Sku报表服务
 * Created by dyt on 2017/9/21.
 */
@Service
@Slf4j
public class GoodsReportService {

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private GoodsCateMapper goodsCateMapper;

    @Autowired
    private GoodsBrandMapper goodsBrandMapper;

    @Autowired
    private GoodsStoreCateMapper goodsStoreCateMapper;

    @Autowired
    private StoreCateMapper storeCateMapper;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private O2oGoodsReportService o2oGoodsReportService;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private GoodsStoreCateQueryService goodsStoreCateQueryService;

    private static final String SPLIT_CHAR = "|";

    /**
     * 查询SKU报表
     * @return
     */
    public SkuReportResponse querySku(GoodsQueryRequest request){
        SkuReportResponse response = new SkuReportResponse();
        List<GoodsInfo> infos = null;

        GoodsInfoQueryCriteria criteria = new GoodsInfoQueryCriteria();
        if(StringUtils.isNotBlank(request.getCompanyId()) && (!Constants.BOSS_ID.equals(request.getCompanyId()))) {
            criteria.setCompanyId(request.getCompanyId());
        }

        Long pageNum = request.getPageNum() == null? Long.valueOf(0): request.getPageNum();
        Long pageSize = request.getPageSize() == null? Long.valueOf(10): request.getPageSize();
        criteria.setNumber(pageNum*pageSize);
        criteria.setSize(pageSize);
        String[] sort = request.getSort();
        criteria.setSortCol(sort[0]);
        criteria.setSortType(sort[1]);
        StoreSelectType storeSelectType = request.getStoreSelectType();
        if(storeSelectType != null){
            criteria.setStoreSelectType(storeSelectType);
        }

        Integer month = request.getMonth();
        int selectType = request.getSelectType() == null ? QueryDateCycle.TODAY.getValue() : request.getSelectType().getValue();
        if(month != null){
            criteria.setTable("goods_month");criteria.setMonth(month);
        }else if(selectType == QueryDateCycle.YESTERDAY.getValue()){
            criteria.setTable("goods_day");criteria.setDate(DateUtil.format(LocalDate.now().minusDays(1),DateUtil.FMT_DATE_1));
        }else if(selectType == QueryDateCycle.LATEST_7_DAYS.getValue()){
            criteria.setTable("goods_recent_seven");
        }else if(selectType == QueryDateCycle.LATEST_30_DAYS.getValue()){
            criteria.setTable("goods_recent_thirty");
        }else{
            criteria.setTable("goods_day");criteria.setDate(DateUtil.now());
        }
        Long count ;
        if(StringUtils.isNotBlank(request.getKeyword())){
            criteria.setKeyWord(request.getKeyword());
            count = skuMapper.queryGoodsInfoInGoodsReportCount(criteria);
            if(count<1){
                return response;
            }
            infos = skuMapper.queryGoodsInfoInGoodsReport(criteria);
            if(infos != null && CollectionUtils.isNotEmpty(infos)) {
                criteria.setIds(infos.parallelStream().map(GoodsInfo::getId).collect(Collectors.toList()));
            }
            criteria.setNumber(null);
            criteria.setSize(null);
        }else {
            count = skuMapper.countSkuReport(criteria);
        }
        response.setCount(count);
        if(count > 0){
            List<SkuReport>  skuReports = skuMapper.querySkuReport(criteria);
            response.setReportPage(new PageImpl<>(skuReports, request.getPageable() , count));
            if(infos == null) {
                GoodsInfoQueryRequest infoRequest = new GoodsInfoQueryRequest();
                infoRequest.setGoodsInfoIds(skuReports.parallelStream().map(SkuReport::getId).collect(Collectors.toList()));
                infoRequest.setPageSize(null);
                infoRequest.setPageNum(null);
                infos = goodsInfoService.queryGoodsInfoDetail(infoRequest);
            }else{
                infos = goodsInfoService.queryGoodsInfoDetail(infos);
            }
            response.setGoodsInfo(infos);
            return response;
        }

        return response;
    }

    /**
     * 查询商品分类报表
     * @return
     */
    public GoodsReportResponse queryGoodsCate(GoodsQueryRequest request){
        if(request.getSortCol() != null && request.getSortCol() == 7){
            request.setSortCol(0);
        }

        GoodsReportResponse response = new GoodsReportResponse();
        GoodsQueryCriteria criteria = new GoodsQueryCriteria();
        criteria.setCompanyId(request.getCompanyId());
        Long pageNum = request.getPageNum() == null? Long.valueOf(0): request.getPageNum();
        Long pageSize = request.getPageSize() == null? Long.valueOf(10): request.getPageSize();
        criteria.setNumber(pageNum*pageSize);
        criteria.setSize(pageSize);

        String[] sort = request.getSort();
        criteria.setSortCol(sort[0]);
        criteria.setSortType(sort[1]);
        StoreSelectType storeSelectType = request.getStoreSelectType();
        if(storeSelectType != null){
            criteria.setStoreSelectType(storeSelectType);
            CompanyInfo companyInfo = companyInfoMapper.queryByCompanyCode("O0000001");
            criteria.setO2oCompanyId(String.valueOf(companyInfo.getCompanyId()));
        }


        Integer month = request.getMonth();
        int selectType = request.getSelectType() == null ? QueryDateCycle.TODAY.getValue() : request.getSelectType().getValue();
        if(month != null){
            if(request.getCompanyId() == null || request.getCompanyId().equals("0")){
                criteria.setTable("goods_cate_month");
            }else {
                criteria.setTable("goods_store_cate_month");
            }
            criteria.setMonth(month);
        }else if(selectType == QueryDateCycle.YESTERDAY.getValue()){
            if(request.getCompanyId() == null || request.getCompanyId().equals("0")){
                criteria.setTable("goods_cate_day");
            }else {
                criteria.setTable("goods_store_cate_day");
            }
            criteria.setDate(DateUtil.format(LocalDate.now().minusDays(1),DateUtil.FMT_DATE_1));
        }else if(selectType == QueryDateCycle.LATEST_7_DAYS.getValue()){
            if(request.getCompanyId() == null || request.getCompanyId().equals("0")){
                criteria.setTable("goods_cate_recent_seven");
            }else {
                criteria.setTable("goods_store_cate_recent_seven");
            }
        }else if(selectType == QueryDateCycle.LATEST_30_DAYS.getValue()){
            if(request.getCompanyId() == null || request.getCompanyId().equals("0")){
                criteria.setTable("goods_cate_recent_thirty");
            }else {
                criteria.setTable("goods_store_cate_recent_thirty");
            }
        }else{
            if(request.getCompanyId() == null || request.getCompanyId().equals("0")){
                criteria.setTable("goods_cate_day");
            }else {
                criteria.setTable("goods_store_cate_day");
            }
                criteria.setDate(DateUtil.now());
        }
        criteria.setIsLeaf(1);
        if(StringUtils.isNotEmpty(request.getId())) {
            if(request.getCompanyId() == null || request.getCompanyId().equals("0")){
                String catePath = goodsCateMapper.queryGoodsCatePath(Integer.valueOf(request.getId()));
                if (StringUtils.isBlank(catePath)) {
                    return response;
                }
                String oldCatePath = catePath.concat(String.valueOf(request.getId()).concat(SPLIT_CHAR));
                List<Long> cateIds = goodsCateMapper.queryGoodsCateChild(oldCatePath);
                if (CollectionUtils.isEmpty(cateIds)) {
                    cateIds = new ArrayList<>();
                }
                cateIds.add(Long.valueOf(request.getId()));
                criteria.setIds(cateIds.stream().map(String::valueOf).collect(Collectors.toList()));
                List<GoodsReport> skuReports = goodsCateMapper.queryGoodsCateReportByCateId(criteria);
                skuReports =
                        skuReports.stream().filter(goodsReport -> !ObjectUtils.isEmpty(goodsReport)).map(goodsReport-> {
                            goodsReport.setId(request.getId());
                            return goodsReport;
                        }).collect(Collectors.toList());
                Page page = new PageImpl<>(skuReports, request.getPageable() , 0);
                response.setReportPage(page);
                response.setCount(page.getTotalElements());
                GoodsCateQueryRequest cateRequest = new GoodsCateQueryRequest();
                cateRequest.setIds(skuReports.parallelStream().map(GoodsReport::getId).collect(Collectors.toList()));
                response.setCates(skuReports.size() == 0 ? Collections.emptyList():goodsCateMapper.queryGoodsCateByIds(cateRequest));
                return response;
            }else{
                List<Long> cateIds = goodsStoreCateQueryService.queryStoreCateChild(request);
                criteria.setIds(cateIds.stream().map(String::valueOf).collect(Collectors.toList()));
                List<GoodsReport> skuReports = goodsStoreCateMapper.queryGoodsCateReportByCateId(criteria);
                skuReports.stream().forEach(goodsReport->goodsReport.setId(request.getId()));
                Page page = new PageImpl<>(skuReports, request.getPageable() , 0);
                response.setReportPage(page);
                response.setCount(page.getTotalElements());
                GoodsCateQueryRequest cateRequest = new GoodsCateQueryRequest();
                cateRequest.setIds(skuReports.parallelStream().map(GoodsReport::getId).collect(Collectors.toList()));
                cateRequest.setPageSize(pageSize);
                setStoreCates(response,request,skuReports);
                return response;
            }


        }else{
            //查询三级分类
            if(request.getCompanyId() == null || request.getCompanyId().equals("0")){
                Long count = goodsCateMapper.countGoodsCateReport(criteria);
                response.setCount(count);
                if(count > 0){
                    List<GoodsReport> skuReports = goodsCateMapper.queryGoodsCateReport(criteria);
                    response.setReportPage(new PageImpl<>(skuReports, request.getPageable() , count));

                    GoodsCateQueryRequest cateRequest = new GoodsCateQueryRequest();
                    cateRequest.setIds(skuReports.parallelStream().map(GoodsReport::getId).collect(Collectors.toList()));
                    response.setCates(goodsCateMapper.queryGoodsCateByIds(cateRequest));
                    return response;
                }
            }else {
                Long count = goodsStoreCateMapper.countGoodsCateReport(criteria);
                response.setCount(count);
                if (count > 0) {
                    List<GoodsReport> skuReports = goodsStoreCateMapper.queryGoodsCateReport(criteria);
                    response.setReportPage(new PageImpl<>(skuReports, request.getPageable(), count));

                    setStoreCates(response,request,skuReports);

                    return response;
                }
            }

        }
        return response;
    }

    /**
     * 查询商品品牌报表
     * @return
     */
    public GoodsReportResponse queryGoodsBrand(GoodsQueryRequest request){
        if(request.getSortCol() != null && request.getSortCol() == 7){
            request.setSortCol(0);
        }

        GoodsReportResponse response = new GoodsReportResponse();
        List<GoodsBrand> infos = null;


        GoodsQueryCriteria criteria = new GoodsQueryCriteria();
        criteria.setCompanyId(request.getCompanyId());
        Long pageNum = request.getPageNum() == null? Long.valueOf(0): request.getPageNum();
        Long pageSize = request.getPageSize() == null? Long.valueOf(10): request.getPageSize();
        criteria.setNumber(pageNum*pageSize);
        criteria.setSize(pageSize);
        String[] sort = request.getSort();
        criteria.setSortCol(sort[0]);
        criteria.setSortType(sort[1]);
        if(StringUtils.isNotBlank(request.getId())) {
            criteria.setIds(Collections.singletonList(request.getId()));
        }
        StoreSelectType storeSelectType = request.getStoreSelectType();
        if(storeSelectType != null){
            criteria.setStoreSelectType(storeSelectType);
            CompanyInfo companyInfo = companyInfoMapper.queryByCompanyCode("O0000001");
            criteria.setO2oCompanyId(String.valueOf(companyInfo.getCompanyId()));
        }

        Integer month = request.getMonth();
        int selectType = request.getSelectType() == null ? QueryDateCycle.TODAY.getValue() : request.getSelectType().getValue();
        if(month != null){
            criteria.setTable("goods_brand_month");criteria.setMonth(month);
        }else if(selectType == QueryDateCycle.YESTERDAY.getValue()){
            criteria.setTable("goods_brand_day");criteria.setDate(DateUtil.format(LocalDate.now().minusDays(1),DateUtil.FMT_DATE_1));
        }else if(selectType == QueryDateCycle.LATEST_7_DAYS.getValue()){
            criteria.setTable("goods_brand_recent_seven");
        }else if(selectType == QueryDateCycle.LATEST_30_DAYS.getValue()){
            criteria.setTable("goods_brand_recent_thirty");
        }else{
            criteria.setTable("goods_brand_day");criteria.setDate(DateUtil.now());
        }

        Long count = goodsBrandMapper.countGoodsBrandReport(criteria);
        response.setCount(count);
        if(count > 0){
            List<GoodsReport> skuReports = goodsBrandMapper.queryGoodsBrandReport(criteria);
            response.setReportPage(new PageImpl<>(skuReports, request.getPageable() , count));
            if(infos == null) {
                GoodsBrandQueryRequest brandRequest = new GoodsBrandQueryRequest();
                brandRequest.setIds(skuReports.parallelStream().map(GoodsReport::getId).collect(Collectors.toList()));

                infos = goodsBrandMapper.queryByIds(brandRequest);
            }
            response.setBrands(infos);
            return response;
        }

        return response;
    }

    /**
     * 查询店铺商品分类报表
     * @return
     */
    public GoodsReportResponse queryStoreCate(GoodsQueryRequest request){
        if(request.getSortCol() != null && request.getSortCol() == 7){
            request.setSortCol(0);
        }

        GoodsReportResponse response = new GoodsReportResponse();
        GoodsQueryCriteria criteria = new GoodsQueryCriteria();
        criteria.setCompanyId(request.getCompanyId());
        Long pageNum = request.getPageNum() == null? Long.valueOf(0): request.getPageNum();
        Long pageSize = request.getPageSize() == null? Long.valueOf(10): request.getPageSize();
        criteria.setNumber(pageNum*pageSize);
        criteria.setSize(pageSize);

        String[] sort = request.getSort();
        criteria.setSortCol(sort[0]);
        criteria.setSortType(sort[1]);

        Integer month = request.getMonth();
        int selectType = request.getSelectType() == null ? QueryDateCycle.TODAY.getValue() : request.getSelectType().getValue();
        if(month != null){
            criteria.setTable("goods_store_cate_month");criteria.setMonth(month);
        }else if(selectType == QueryDateCycle.YESTERDAY.getValue()){
            criteria.setTable("goods_store_cate_day");criteria.setDate(DateUtil.format(LocalDate.now().minusDays(1),DateUtil.FMT_DATE_1));
        }else if(selectType == QueryDateCycle.LATEST_7_DAYS.getValue()){
            criteria.setTable("goods_store_cate_recent_seven");
        }else if(selectType == QueryDateCycle.LATEST_30_DAYS.getValue()){
            criteria.setTable("goods_store_cate_recent_thirty");
        }else{
            criteria.setTable("goods_store_cate_day");criteria.setDate(DateUtil.now());
        }

        criteria.setIsLeaf(1);
        if(StringUtils.isNotEmpty(request.getId())) {
            List<Long> cateIds = goodsStoreCateQueryService.queryStoreCateChild(request);
            criteria.setIds(cateIds.stream().map(String::valueOf).collect(Collectors.toList()));
            List<GoodsReport> skuReports = goodsStoreCateMapper.queryGoodsCateReport(criteria);
            skuReports.stream().forEach(goodsReport->goodsReport.setId(request.getId()));
            response.setReportPage(new PageImpl<>(skuReports, request.getPageable() , 1));

            GoodsCateQueryRequest cateRequest = new GoodsCateQueryRequest();
            cateRequest.setIds(skuReports.parallelStream().map(GoodsReport::getId).collect(Collectors.toList()));
            cateRequest.setPageSize(pageSize);
            setStoreCates(response,request,skuReports);
            response.setCount(1L);
            return response;
        }else {
            //查询三级分类
            Long count = goodsStoreCateMapper.countGoodsCateReport(criteria);
            if (count > 0) {
                List<GoodsReport> skuReports = goodsStoreCateMapper.queryGoodsCateReport(criteria);
                response.setReportPage(new PageImpl<>(skuReports, request.getPageable(), count));
                setStoreCates(response,request,skuReports);
                response.setCount(count);
                return response;
            }
        }
        return response;
    }

    private void setStoreCates(GoodsReportResponse response,GoodsQueryRequest request,List<GoodsReport> skuReports){
        String companyId = request.getCompanyId();
        StoreType storeType = null;
        if(Objects.nonNull(companyId) && !"0".equals(companyId)){
            CompanyInfo companyInfo = companyInfoMapper.queryByCompanyId(Integer.parseInt(companyId));
            storeType = companyInfo.getStoreType() == 2 ? StoreType.O2O : StoreType.SUPPLIER;
        }
        if(request.getStoreSelectType() == StoreSelectType.O2O || storeType == StoreType.O2O){
            response.setStoreCates(o2oGoodsReportService.o2oQueryByIds(skuReports.parallelStream().map(GoodsReport::getId).collect(Collectors.toList())));
        }else {
            response.setStoreCates(storeCateMapper.queryByIds(skuReports.parallelStream().map(GoodsReport::getId).collect(Collectors.toList())));
        }
    }

}
