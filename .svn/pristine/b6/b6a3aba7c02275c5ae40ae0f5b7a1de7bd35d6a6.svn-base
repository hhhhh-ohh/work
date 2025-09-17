package com.wanmi.ares.report.goods.service;

import com.wanmi.ares.report.goods.dao.GoodsTotalMapper;
import com.wanmi.ares.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * 商品概览报表服务
 * Created by dyt on 2017/9/21.
 */
@Service
public class GoodsTotalGenerateService {

    @Autowired
    private O2oGoodsTotalGenerateService o2oGoodsTotalGenerateService;

    @Autowired
    private GoodsTotalMapper goodsReportMapper;

    /**
     * 生成商品概览
     * 仅支持昨日，内容为商品总数、上架商品数
     */


    public void generate(int type){
        LocalDate statDate = null;
        switch (type){
            case Constants.GoodsGenerateType.TODAY:
                statDate = LocalDate.now();
                break;
            case Constants.GoodsGenerateType.YESTERDAY:
                statDate = LocalDate.now().minusDays(1);
                break;
            default:
                break;
        }
        if(statDate!=null) {
            this.goodsReportMapper.deleteGoodsTotalForSelect(statDate);
            this.goodsReportMapper.saveGoodsTotalForSelect(statDate);
            o2oGoodsTotalGenerateService.saveO2oGoodsTotalForSelect(statDate);
        }
    }
}
