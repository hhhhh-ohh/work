package com.wanmi.ares.report.goods.service;

import com.wanmi.ares.interfaces.GoodsService;
import com.wanmi.ares.report.goods.model.reponse.GoodsReportResponse;
import com.wanmi.ares.report.goods.model.reponse.GoodsTotalResponse;
import com.wanmi.ares.report.goods.model.reponse.SkuReportResponse;
import com.wanmi.ares.report.goods.model.request.GoodsQueryRequest;
import com.wanmi.ares.report.goods.model.root.GoodsReport;
import com.wanmi.ares.report.goods.service.GoodsReportService;
import com.wanmi.ares.report.goods.service.GoodsTotalService;
import com.wanmi.ares.report.goods.service.O2oSkuGenerateService;
import com.wanmi.ares.request.goods.GoodsReportRequest;
import com.wanmi.ares.source.model.root.StoreCate;
import com.wanmi.ares.view.goods.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by sunkun on 2017/10/16.
 */
@Service
public class GoodsServiceImpl implements GoodsService.Iface {

    @Resource
    private GoodsReportService goodsReportService;

    @Resource
    private GoodsTotalService goodsTotalService;

    @Autowired
    private O2oSkuGenerateService o2oSkuGenerateService;

    @Override
    public GoodsTotalView queryGoodsTotal(GoodsReportRequest request) {
        GoodsQueryRequest queryRequest = new GoodsQueryRequest();
        BeanUtils.copyProperties(request, queryRequest);
        GoodsTotalResponse response= goodsTotalService.query(queryRequest);
        GoodsTotalView view = new GoodsTotalView();
        BeanUtils.copyProperties(response, view);
        view.setOrderConversion(response.getOrderConversion().doubleValue());
        return view;
    }

    @Override
    public GoodsReportPageView querySkuReport(GoodsReportRequest request)  {
        GoodsQueryRequest queryRequest = new GoodsQueryRequest();
        BeanUtils.copyProperties(request, queryRequest);
        SkuReportResponse reportResponse = goodsReportService.querySku(queryRequest);

        GoodsReportPageView view = new GoodsReportPageView();
        BeanUtils.copyProperties(reportResponse.getReportPage(), view);
        view.setGoodsReportList(reportResponse.getReportPage().getContent().stream().map(skuReport -> {
            GoodsReportView reportView = new GoodsReportView();
            BeanUtils.copyProperties(skuReport, reportView);
            reportView.setOrderAmt(skuReport.getOrderAmt().doubleValue());
            reportView.setPayAmt(skuReport.getPayAmt().doubleValue());
            reportView.setReturnOrderAmt(skuReport.getReturnOrderAmt().doubleValue());
            reportView.setOrderConversion(skuReport.getOrderConversion().doubleValue());
            reportView.setGoodsUv(skuReport.getTotalUv());
            reportView.setGoodsPv(skuReport.getTotalPv());
            reportView.setCustomerPayCount(skuReport.getCustomerPayCount());
            return reportView;
        }).collect(Collectors.toList()));
        view.setTotalElements(reportResponse.getCount().intValue());
        view.setGoodsSkuViewList(reportResponse.getGoodsInfo().stream().map(goodsInfo -> {
            GoodsSkuView skuView = new GoodsSkuView();
            BeanUtils.copyProperties(goodsInfo, skuView);
            return skuView;
        }).collect(Collectors.toList()));
        return view;
    }

    @Override
    public GoodsReportPageView queryCateReport(GoodsReportRequest request)  {
        GoodsQueryRequest queryRequest = new GoodsQueryRequest();
        BeanUtils.copyProperties(request, queryRequest);
        GoodsReportResponse reportResponse = goodsReportService.queryGoodsCate(queryRequest);

        GoodsReportPageView view = new GoodsReportPageView();
        setReport(view, reportResponse);

        if(queryRequest.getCompanyId().equals("0")){
            view.setGoodsCateViewList(reportResponse.getCates().stream().map(goodsCate -> {
                GoodsCateView cateView = new GoodsCateView();
                BeanUtils.copyProperties(goodsCate, cateView);
                return cateView;
            }).collect(Collectors.toList()));
        }else {
            view.setGoodsCateViewList(reportResponse.getStoreCates().stream().map(goodsCate -> {
                GoodsCateView cateView = new GoodsCateView();
                BeanUtils.copyProperties(goodsCate, cateView);
                cateView.setName(goodsCate.getCateName());
                return cateView;
            }).collect(Collectors.toList()));
        }

        view.setTotalElements(reportResponse.getCount().intValue());
        return view;
    }

    @Override
    public GoodsReportPageView queryBrandReport(GoodsReportRequest request)  {
        GoodsQueryRequest queryRequest = new GoodsQueryRequest();
        BeanUtils.copyProperties(request, queryRequest);
        GoodsReportResponse reportResponse = goodsReportService.queryGoodsBrand(queryRequest);

        GoodsReportPageView view = new GoodsReportPageView();
        setReport(view, reportResponse);
        view.setGoodsBrandViewList(reportResponse.getBrands().stream().map(goodsBrand -> {
            GoodsBrandView brandView = new GoodsBrandView();
            BeanUtils.copyProperties(goodsBrand, brandView);
            return brandView;
        }).collect(Collectors.toList()));
        view.setTotalElements(reportResponse.getCount().intValue());
        return view;
    }

    @Override
    public GoodsReportPageView queryStoreCateReport(GoodsReportRequest request) {
        GoodsQueryRequest queryRequest = new GoodsQueryRequest();
        BeanUtils.copyProperties(request, queryRequest);
        GoodsReportResponse reportResponse = goodsReportService.queryStoreCate(queryRequest);

        GoodsReportPageView view = new GoodsReportPageView();
        setReport(view, reportResponse);
        view.setGoodsCateViewList(reportResponse.getStoreCates().stream().map(goodsCate -> {
            GoodsCateView cateView = new GoodsCateView();
            BeanUtils.copyProperties(goodsCate, cateView);
            cateView.setName(goodsCate.getCateName());
            return cateView;
        }).collect(Collectors.toList()));
        view.setTotalElements(reportResponse.getCount().intValue());
        return view;
    }

    private void setReport(GoodsReportPageView view, GoodsReportResponse reportResponse){
        BeanUtils.copyProperties(reportResponse.getReportPage(), view);
        List<StoreCate> storeCates = reportResponse.getStoreCates();
        List<GoodsReport> reports = reportResponse.getReportPage().getContent();
        List<GoodsReportView> goodsReportViews = new ArrayList<>();
        IntStream.range(0, reports.size())
                .mapToObj(i -> {
                    GoodsReportView reportView = new GoodsReportView();
                    BeanUtils.copyProperties(reports.get(i), reportView);
                    reportView.setOrderAmt(reports.get(i).getOrderAmt().doubleValue());
                    reportView.setPayAmt(reports.get(i).getPayAmt().doubleValue());
                    reportView.setReturnOrderAmt(reports.get(i).getReturnOrderAmt().doubleValue());
                    if(Objects.nonNull(storeCates) && storeCates.size() > 0){
                        reportView.setName(storeCates.get(i).getCateName());
                        reportView.setParentNames(storeCates.get(i).getParentNames());
                    }
                    goodsReportViews.add(reportView);
                    return i;
                })
                .collect(Collectors.toList());
        view.setGoodsReportList(goodsReportViews);
    }
}
