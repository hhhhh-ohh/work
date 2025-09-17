package com.wanmi.ares.scheduled.customer;

import com.wanmi.ares.enums.StatisticsDataType;
import com.wanmi.ares.report.customer.dao.AreaDistributeReportMapper;
import com.wanmi.ares.report.customer.dao.CustomerOrderReportMapper;
import com.wanmi.ares.report.customer.dao.CustomerReportTable;
import com.wanmi.ares.report.customer.dao.LevelDistributeReportMapper;
import com.wanmi.ares.report.customer.model.request.CustomerAreaDistributeRequest;
import com.wanmi.ares.report.customer.model.request.CustomerLevelDistributeRequest;
import com.wanmi.ares.report.customer.model.request.CustomerOrderDataRequest;
import com.wanmi.ares.report.customer.service.CustomerGrowthGenerateService;
import com.wanmi.ares.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDate;

/**
 * @ClassName CustomerOrderDataStatisticsService
 * @Description 客户统计--客户订货量报表统计service类
 * @Author lvzhenwei
 * @Date 2019/9/18 15:09
 **/
@Service
public class CustomerDataStatisticsService {

    private static final int YESTERDAY_NUM = 1;

    private static final int THIRTY_DAY_NUM = 30;

    private static final int SEVEN_DAY_NUM = 7;

    public static final int BOSS_SHOP_TYPE = 0;

    public static final int SUPPLIER_SHOP_TYPE = 1;

    @Resource
    private AreaDistributeReportMapper areaDistributeReportMapper;

    @Resource
    private LevelDistributeReportMapper levelDistributeReportMapper;

    @Resource
    private CustomerOrderReportMapper customerOrderReportMapper;
    @Resource
    private CustomerGrowthGenerateService customerGrowthGenerateService;

    @Transactional(isolation= Isolation.READ_UNCOMMITTED)
    public void generateCustomerOrderData(String type){
        if(StringUtils.isNotBlank(type)){
            String[] typeArr = type.split(",");
            for(String generateType:typeArr){
                if(generateType.equals(StatisticsDataType.TODAY.toValue()+"")){
                    //执行当天的统计数据
                    generateThirdShopCustomerAreaDistribute(LocalDate.now());
                    generateThirdShopCustomerLevelDistribute(LocalDate.now());
                    generateCustomerOrderDatForToday();
                    this.customerGrowthGenerateService.generateTodayGrowth();
                } else if(generateType.equals(StatisticsDataType.YESTERDAY.toValue()+"")){
                    //执行昨天的统计数据
                    generateThirdShopCustomerAreaDistribute(LocalDate.now().minusDays(YESTERDAY_NUM));
                    generateThirdShopCustomerLevelDistribute(LocalDate.now().minusDays(YESTERDAY_NUM));
                    generateCustomerOrderDatForYesterday();
                    this.customerGrowthGenerateService.generateYesterdayGrowth();
                } else if(generateType.equals(StatisticsDataType.SEVEN.toValue()+"")){
                    //执行最近7天的统计数据
                    generateCustomerOrderDatForSeven();
                } else if(generateType.equals(StatisticsDataType.THIRTY.toValue()+"")){
                    //执行最近30天的统计数据
                    generateCustomerOrderDatForThirty();
                } else if(generateType.equals(StatisticsDataType.MONTH.toValue()+"")){
                    //执行按月的统计数据
                    generateCustomerOrderDatForMonth();
                }
            }
        }
    }

    /**
     * @Author lvzhenwei
     * @Description 生成客户统计客户概况--按客户地区分布数据
     * @Date 11:06 2019/9/23
     * @Param []
     * @return void
     **/
    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void generateThirdShopCustomerAreaDistribute(LocalDate targetDate){
        CustomerAreaDistributeRequest areaRequest = new CustomerAreaDistributeRequest();
        areaRequest.setTargetDate(targetDate);
        areaDistributeReportMapper.deleteReport(targetDate.toString());
        areaDistributeReportMapper.generateBossCustomerAreaDistribute(areaRequest);
        areaDistributeReportMapper.generateThirdShopCustomerAreaDistribute(areaRequest);
        areaDistributeReportMapper.generateThirdShopByStoreTypeCustomerAreaDistribute(areaRequest);
    }

    /**
     * @Author lvzhenwei
     * @Description 生成客户统计客户概况--按客等级区分布数据
     * @Date 11:06 2019/9/23
     * @Param []
     * @return void
     **/
    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void generateThirdShopCustomerLevelDistribute(LocalDate targetDate){
        CustomerLevelDistributeRequest request = new CustomerLevelDistributeRequest();
        request.setTargetDate(targetDate);
        levelDistributeReportMapper.deleteReport(targetDate.toString());
        levelDistributeReportMapper.generateBossCustomerLevelDistribute(request);
        levelDistributeReportMapper.generateThirdShopCustomerLevelDistribute(request);
    }

    /**
     * @Author lvzhenwei
     * @Description 生成当天数据报表--客户统计--客户订货量
     * @Date 17:22 2019/9/19
     * @Param []
     * @return void
     **/
    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void generateCustomerOrderDatForToday(){
        String today = DateUtil.format(LocalDate.now(), DateUtil.FMT_DATE_1);
        customerOrderReportMapper.deleteCustomerReportByDate(CustomerReportTable.CUSTOMER_DAY.toString(), today);
        customerOrderReportMapper.deleteCustomerReportByDate(CustomerReportTable.CUSTOMER_LEVEL_DAY.toString(), today);
        customerOrderReportMapper.deleteCustomerReportByDate(CustomerReportTable.CUSTOMER_REGION_DAY.toString(), today);
        generateCustomerOrderDatForBossForToday();
        generateCustomerOrderDatForSupplierForToday();
    }

    /**
     * @Author lvzhenwei
     * @Description 生成昨天数据报表--客户统计--客户订货量
     * @Date 17:22 2019/9/19
     * @Param []
     * @return void
     **/
    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void generateCustomerOrderDatForYesterday(){
        String yesterday = DateUtil.format(LocalDate.now().minusDays(YESTERDAY_NUM), DateUtil.FMT_DATE_1);
        customerOrderReportMapper.deleteCustomerReportByDate(CustomerReportTable.CUSTOMER_DAY.toString(), yesterday);
        customerOrderReportMapper.deleteCustomerReportByDate(CustomerReportTable.CUSTOMER_LEVEL_DAY.toString(), yesterday);
        customerOrderReportMapper.deleteCustomerReportByDate(CustomerReportTable.CUSTOMER_REGION_DAY.toString(), yesterday);
        generateCustomerOrderDataForBossForYesterday();
        generateCustomerOrderDataForSupplierForYesterday();
    }

    /**
     * @Author lvzhenwei
     * @Description 生成最近7天数据报表--客户统计--客户订货量
     * @Date 17:22 2019/9/19
     * @Param []
     * @return void
     **/
    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void generateCustomerOrderDatForSeven(){
        customerOrderReportMapper.deleteCustomerReport(CustomerReportTable.CUSTOMER_RECENT_SEVEN.toString());
        customerOrderReportMapper.deleteCustomerReport(CustomerReportTable.CUSTOMER_LEVEL_RECENT_SEVEN.toString());
        customerOrderReportMapper.deleteCustomerReport(CustomerReportTable.CUSTOMER_REGION_RECENT_SEVEN.toString());
        generateCustomerOrderDataForBossForSeven();
        generateCustomerOrderDataForSupplierForSeven();
    }

    /**
     * @Author lvzhenwei
     * @Description 生成最近30天数据报表--客户统计--客户订货量
     * @Date 17:22 2019/9/19
     * @Param []
     * @return void
     **/
    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void generateCustomerOrderDatForThirty(){
        customerOrderReportMapper.deleteCustomerReport(CustomerReportTable.CUSTOMER_RECENT_THIRTY.toString());
        customerOrderReportMapper.deleteCustomerReport(CustomerReportTable.CUSTOMER_LEVEL_RECENT_THIRTY.toString());
        customerOrderReportMapper.deleteCustomerReport(CustomerReportTable.CUSTOMER_REGION_RECENT_THIRTY.toString());
        generateCustomerOrderDataForBossForThirty();
        generateCustomerOrderDataForSupplierForThirty();
    }

    /**
     * @Author lvzhenwei
     * @Description 生成按月统计数据报表--客户统计--客户订货量
     * @Date 17:22 2019/9/19
     * @Param []
     * @return void
     **/
    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void generateCustomerOrderDatForMonth(){
        customerOrderReportMapper.deleteCustomerReport(CustomerReportTable.CUSTOMER_MONTH.toString());
        customerOrderReportMapper.deleteCustomerReport(CustomerReportTable.CUSTOMER_LEVEL_MONTH.toString());
        customerOrderReportMapper.deleteCustomerReport(CustomerReportTable.CUSTOMER_REGION_MONTH.toString());
        generateCustomerOrderDataForBossForMonth();
        generateCustomerOrderDataForSupplierForMonth();
    }

    private void generateCustomerOrderDatForBossForToday(){
        CustomerOrderDataRequest customerOrderDataRequest = new CustomerOrderDataRequest();
        customerOrderDataRequest.setBeginDate(LocalDate.now());
        customerOrderDataRequest.setEndDate(LocalDate.now().plusDays(1));
        customerOrderDataRequest.setStatisticsDate(LocalDate.now());
        customerOrderDataRequest.setFlowDataType(StatisticsDataType.TODAY.toValue());
        customerOrderDataRequest.setShopType(BOSS_SHOP_TYPE);
        customerOrderReportMapper.generateCustomerOrderForBossForDay(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerAreaOrderForBossForDay(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerLevelOrderForBossForDay(customerOrderDataRequest);
    }

    private void generateCustomerOrderDatForSupplierForToday(){
        CustomerOrderDataRequest customerOrderDataRequest = new CustomerOrderDataRequest();
        customerOrderDataRequest.setBeginDate(LocalDate.now());
        customerOrderDataRequest.setEndDate(LocalDate.now().plusDays(1));
        customerOrderDataRequest.setStatisticsDate(LocalDate.now());
        customerOrderDataRequest.setFlowDataType(StatisticsDataType.TODAY.toValue());
        customerOrderDataRequest.setShopType(SUPPLIER_SHOP_TYPE);
        customerOrderReportMapper.generateCustomerOrderForSupplierForDay(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerAreaOrderForSupplierForDay(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerLevelOrderForSupplierForDay(customerOrderDataRequest);

        //统计店铺和门店
        customerOrderReportMapper.generateCustomerOrderForSupplierStoreTypeForDay(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerAreaOrderForSupplierStoreTypeForDay(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerLevelOrderForSupplierStoreTypeForDay(customerOrderDataRequest);
    }

    private void generateCustomerOrderDataForBossForYesterday(){
        CustomerOrderDataRequest customerOrderDataRequest = new CustomerOrderDataRequest();
        customerOrderDataRequest.setBeginDate(LocalDate.now().minusDays(YESTERDAY_NUM));
        customerOrderDataRequest.setEndDate(LocalDate.now());
        customerOrderDataRequest.setStatisticsDate(LocalDate.now().minusDays(YESTERDAY_NUM));
        customerOrderDataRequest.setFlowDataType(StatisticsDataType.YESTERDAY.toValue());
        customerOrderDataRequest.setShopType(BOSS_SHOP_TYPE);
        customerOrderReportMapper.generateCustomerOrderForBossForDay(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerAreaOrderForBossForDay(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerLevelOrderForBossForDay(customerOrderDataRequest);
    }

    private void generateCustomerOrderDataForSupplierForYesterday(){
        CustomerOrderDataRequest customerOrderDataRequest = new CustomerOrderDataRequest();
        customerOrderDataRequest.setBeginDate(LocalDate.now().minusDays(YESTERDAY_NUM));
        customerOrderDataRequest.setEndDate(LocalDate.now());
        customerOrderDataRequest.setStatisticsDate(LocalDate.now().minusDays(YESTERDAY_NUM));
        customerOrderDataRequest.setFlowDataType(StatisticsDataType.YESTERDAY.toValue());
        customerOrderDataRequest.setShopType(SUPPLIER_SHOP_TYPE);
        customerOrderReportMapper.generateCustomerOrderForSupplierForDay(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerAreaOrderForSupplierForDay(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerLevelOrderForSupplierForDay(customerOrderDataRequest);

        //统计店铺和门店
        customerOrderReportMapper.generateCustomerOrderForSupplierStoreTypeForDay(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerAreaOrderForSupplierStoreTypeForDay(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerLevelOrderForSupplierStoreTypeForDay(customerOrderDataRequest);
    }

    private void generateCustomerOrderDataForBossForSeven(){
        CustomerOrderDataRequest customerOrderDataRequest = new CustomerOrderDataRequest();
        customerOrderDataRequest.setBeginDate(LocalDate.now().minusDays(SEVEN_DAY_NUM));
        customerOrderDataRequest.setEndDate(LocalDate.now());
        customerOrderDataRequest.setStatisticsDate(LocalDate.now());
        customerOrderDataRequest.setFlowDataType(StatisticsDataType.SEVEN.toValue());
        customerOrderDataRequest.setShopType(BOSS_SHOP_TYPE);
        customerOrderReportMapper.generateCustomerOrderForBossForSeven(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerAreaOrderForBossForSeven(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerLevelOrderForBossForSeven(customerOrderDataRequest);
    }

    private void generateCustomerOrderDataForSupplierForSeven(){
        CustomerOrderDataRequest customerOrderDataRequest = new CustomerOrderDataRequest();
        customerOrderDataRequest.setBeginDate(LocalDate.now().minusDays(SEVEN_DAY_NUM));
        customerOrderDataRequest.setEndDate(LocalDate.now());
        customerOrderDataRequest.setStatisticsDate(LocalDate.now());
        customerOrderDataRequest.setFlowDataType(StatisticsDataType.SEVEN.toValue());
        customerOrderDataRequest.setShopType(SUPPLIER_SHOP_TYPE);
        customerOrderReportMapper.generateCustomerOrderForSupplierForSeven(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerAreaOrderForSupplierForSeven(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerLevelOrderForSupplierForSeven(customerOrderDataRequest);

        //统计店铺和门店
        customerOrderReportMapper.generateCustomerOrderForSupplierStoreTypeForSeven(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerAreaOrderForSupplierStoreTypeForSeven(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerLevelOrderForSupplierStoreTypeForSeven(customerOrderDataRequest);
    }

    private void generateCustomerOrderDataForBossForThirty(){
        CustomerOrderDataRequest customerOrderDataRequest = new CustomerOrderDataRequest();
        customerOrderDataRequest.setBeginDate(LocalDate.now().minusDays(THIRTY_DAY_NUM));
        customerOrderDataRequest.setEndDate(LocalDate.now());
        customerOrderDataRequest.setStatisticsDate(LocalDate.now());
        customerOrderDataRequest.setFlowDataType(StatisticsDataType.THIRTY.toValue());
        customerOrderDataRequest.setShopType(BOSS_SHOP_TYPE);
        customerOrderReportMapper.generateCustomerOrderForBossForThirty(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerAreaOrderForBossForThirty(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerLevelOrderForBossForThirty(customerOrderDataRequest);
    }

    private void generateCustomerOrderDataForSupplierForThirty(){
        CustomerOrderDataRequest customerOrderDataRequest = new CustomerOrderDataRequest();
        customerOrderDataRequest.setBeginDate(LocalDate.now().minusDays(THIRTY_DAY_NUM));
        customerOrderDataRequest.setEndDate(LocalDate.now());
        customerOrderDataRequest.setStatisticsDate(LocalDate.now());
        customerOrderDataRequest.setFlowDataType(StatisticsDataType.THIRTY.toValue());
        customerOrderDataRequest.setShopType(SUPPLIER_SHOP_TYPE);
        customerOrderReportMapper.generateCustomerOrderForSupplierForThirty(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerAreaOrderForSupplierForThirty(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerLevelOrderForSupplierForThirty(customerOrderDataRequest);

        //统计店铺和门店
        customerOrderReportMapper.generateCustomerOrderForSupplierStoreTypeForThirty(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerAreaOrderForSupplierStoreTypeForThirty(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerLevelOrderForSupplierStoreTypeForThirty(customerOrderDataRequest);
    }

    private void generateCustomerOrderDataForBossForMonth(){
        CustomerOrderDataRequest customerOrderDataRequest = new CustomerOrderDataRequest();
        customerOrderDataRequest.setBeginDate(DateUtil.getMonthFirstAndLastDayDate(1,0));
        customerOrderDataRequest.setEndDate(DateUtil.getMonthFirstAndLastDayDate(1,1).plusDays(1));
        customerOrderDataRequest.setStatisticsDate(DateUtil.getMonthFirstAndLastDayDate(1,0));
        customerOrderDataRequest.setFlowDataType(StatisticsDataType.MONTH.toValue());
        customerOrderDataRequest.setShopType(BOSS_SHOP_TYPE);
        customerOrderReportMapper.generateCustomerOrderForBossForMonth(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerAreaOrderForBossForMonth(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerLevelOrderForBossForMonth(customerOrderDataRequest);
    }

    private void generateCustomerOrderDataForSupplierForMonth(){
        CustomerOrderDataRequest customerOrderDataRequest = new CustomerOrderDataRequest();
        customerOrderDataRequest.setBeginDate(DateUtil.getMonthFirstAndLastDayDate(1,0));
        customerOrderDataRequest.setEndDate(DateUtil.getMonthFirstAndLastDayDate(1,1).plusDays(1));
        customerOrderDataRequest.setStatisticsDate(DateUtil.getMonthFirstAndLastDayDate(1,0));
        customerOrderDataRequest.setFlowDataType(StatisticsDataType.MONTH.toValue());
        customerOrderDataRequest.setShopType(SUPPLIER_SHOP_TYPE);
        customerOrderReportMapper.generateCustomerOrderForSupplierForMonth(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerAreaOrderForSupplierForMonth(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerLevelOrderForSupplierForMonth(customerOrderDataRequest);

        customerOrderReportMapper.generateCustomerOrderForSupplierStoreTypeForMonth(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerAreaOrderForSupplierStoreTypeForMonth(customerOrderDataRequest);
        customerOrderReportMapper.generateCustomerLevelOrderForSupplierStoreTypeForMonth(customerOrderDataRequest);
    }

}
