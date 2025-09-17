package com.wanmi.ares.report.customer.dao;

import com.wanmi.ares.base.PageRequest;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.customer.model.request.CustomerOrderDataRequest;
import com.wanmi.ares.report.customer.model.root.CustomerAreaReport;
import com.wanmi.ares.report.customer.model.root.CustomerLevelReport;
import com.wanmi.ares.report.customer.model.root.CustomerReport;
import com.wanmi.ares.request.customer.CustomerOrderQueryRequest;
import com.wanmi.ares.source.model.root.Customer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOrderReportMapper {

    /**
     * 当天报表生成前清除
     *
     * @param tableName
     * @param deleteDate
     * @return
     */
    int deleteCustomerReportByDate(@Param("tableName") String tableName, @Param("deleteDate") String deleteDate);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 清空某张表
     * @Date 18:10 2019/10/8
     * @Param [tableName]
     **/
    int deleteCustomerReport(@Param("tableName") String tableName);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 运营后台客户统计--按天数据统计--客户订货报表按客户查看数据--预统计
     * @Date 15:03 2019/9/18
     * @Param []
     **/
    int generateCustomerOrderForBossForDay(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 运营后台客户统计--最近7天数据统计--客户订货报表按客户查看数据--预统计
     * @Date 15:04 2019/9/18
     * @Param []
     **/
    int generateCustomerOrderForBossForSeven(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 运营后台客户统计--最近30天数据统计--客户订货报表按客户查看数据--预统计
     * @Date 15:05 2019/9/18
     * @Param []
     **/
    int generateCustomerOrderForBossForThirty(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 运营后台客户统计--按月统计数据统计--客户订货报表按客户查看数据--预统计
     * @Date 15:06 2019/9/18
     * @Param []
     **/
    int generateCustomerOrderForBossForMonth(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家客户统计--按天数据统计--客户订货报表按客户查看数据--预统计
     * @Date 15:03 2019/9/18
     * @Param []
     **/
    int generateCustomerOrderForSupplierForDay(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家客户统计--最近7天数据统计--客户订货报表按客户查看数据--预统计
     * @Date 15:04 2019/9/18
     * @Param []
     **/
    int generateCustomerOrderForSupplierForSeven(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家客户统计--最近30天数据统计--客户订货报表按客户查看数据--预统计
     * @Date 15:05 2019/9/18
     * @Param []
     **/
    int generateCustomerOrderForSupplierForThirty(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家客户统计--按月统计数据统计--客户订货报表按客户查看数据--预统计
     * @Date 15:06 2019/9/18
     * @Param []
     **/
    int generateCustomerOrderForSupplierForMonth(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家客户统计--按天统计数据统计--客户订货报表按等级查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerLevelOrderForSupplierForDay(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家客户统计--最近7天统计数据统计--客户订货报表按等级查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerLevelOrderForSupplierForSeven(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家客户统计--最近30天统计数据统计--客户订货报表按等级查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerLevelOrderForSupplierForThirty(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家客户统计--按月统计数据统计--客户订货报表按等级查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerLevelOrderForSupplierForMonth(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 运营后台商客户统计--按天统计数据统计--客户订货报表按等级查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerLevelOrderForBossForDay(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 运营后台客户统计--最近7天统计数据统计--客户订货报表按等级查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerLevelOrderForBossForSeven(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 运营后台客户统计--最近30天统计数据统计--客户订货报表按等级查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerLevelOrderForBossForThirty(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 运营后台客户统计--按月统计数据统计--客户订货报表按等级查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerLevelOrderForBossForMonth(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 运营后台客户统计--按天统计数据统计--客户订货报表按区域查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerAreaOrderForBossForDay(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 运营后台客户统计--最近7天统计数据统计--客户订货报表按区域查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerAreaOrderForBossForSeven(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 运营后台客户统计--最近30天统计数据统计--客户订货报表按区域查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerAreaOrderForBossForThirty(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 运营后台客户统计--按月统计数据统计--客户订货报表按区域查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerAreaOrderForBossForMonth(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家客户统计--按天统计数据统计--客户订货报表按区域查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerAreaOrderForSupplierForDay(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家客户统计--最近7天统计数据统计--客户订货报表按区域查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerAreaOrderForSupplierForSeven(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家客户统计--最近30天统计数据统计--客户订货报表按区域查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerAreaOrderForSupplierForThirty(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家客户统计--按月统计数据统计--客户订货报表按区域查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerAreaOrderForSupplierForMonth(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * 查询会员报表
     *
     * @param customerOrderQueryRequest customerOrderQueryRequest
     * @param pageRequest               pageRequest
     * @param tableName                 tableName
     * @return 会员报表
     */
    List<CustomerReport> queryCustomerReport(@Param("customerOrderQueryRequest") CustomerOrderQueryRequest customerOrderQueryRequest
            , @Param("pageRequest") PageRequest pageRequest, @Param("tableName") String tableName, @Param("daylyDate") String daylyDate);


    /**
     * 查询数据总条数
     *
     * @param customerOrderQueryRequest customerOrderQueryRequest
     * @param tableName                 tableName
     * @return total
     */
    Integer countCustomerReport(@Param("customerOrderQueryRequest") CustomerOrderQueryRequest customerOrderQueryRequest
            , @Param("tableName") String tableName, @Param("daylyDate") String daylyDate);

    /**
     * 查询数据总条数
     *
     * @param customerOrderQueryRequest customerOrderQueryRequest
     * @param tableName                 tableName
     * @return total
     */
    Integer countCustomerLevelReport(@Param("customerOrderQueryRequest") CustomerOrderQueryRequest customerOrderQueryRequest,
                                     @Param("tableName") String tableName, @Param("daylyDate") String daylyDate);

    /**
     * 查询数据总条数
     *
     * @param customerOrderQueryRequest customerOrderQueryRequest
     * @param tableName                 tableName
     * @return total
     */
    Integer countCustomerStoreLevelReport(@Param("customerOrderQueryRequest") CustomerOrderQueryRequest customerOrderQueryRequest,
                                          @Param("tableName") String tableName, @Param("daylyDate") String daylyDate);

    /**
     * 查询数据总条数
     *
     * @param customerOrderQueryRequest customerOrderQueryRequest
     * @param tableName                 tableName
     * @return total
     */
    Integer countCustomerAreaReport(@Param("customerOrderQueryRequest") CustomerOrderQueryRequest customerOrderQueryRequest
            , @Param("tableName") String tableName, @Param("daylyDate") String daylyDate);

    /**
     * 查询客户级别报表
     *
     * @param customerOrderQueryRequest customerOrderQueryRequest
     * @param pageRequest               pageRequest
     * @param tableName                 tableName
     * @return List<CustomerLevelReport>
     */
    List<CustomerLevelReport> queryCustomerLevelReport(@Param("customerOrderQueryRequest") CustomerOrderQueryRequest customerOrderQueryRequest
            , @Param("pageRequest") PageRequest pageRequest, @Param("tableName") String tableName,
                                                       @Param("daylyDate") String daylyDate);

    /**
     * 查询会员地区报表
     *
     * @param customerOrderQueryRequest customerOrderQueryRequest
     * @param pageRequest               pageRequest
     * @param tableName                 tableName
     * @return List<CustomerAreaReport>
     */
    List<CustomerAreaReport> queryCustomerAreaReport(@Param("customerOrderQueryRequest") CustomerOrderQueryRequest customerOrderQueryRequest
            , @Param("pageRequest") PageRequest pageRequest, @Param("tableName") String tableName, @Param("daylyDate") String daylyDate);

    /**
     * 导出会员订货报表
     *
     * @param exportQuery exportQuery
     * @return 列表
     */
    List<CustomerReport> exportCustomerReport(@Param("exportQuery") ExportQuery exportQuery);

    /**
     * 统计导出数量
     *
     * @param exportQuery exportQuery
     * @return rows
     */
    int countExportCustomerReport(@Param("exportQuery") ExportQuery exportQuery);

    /**
     * 导出
     *
     * @param exportQuery
     * @return
     */
    List<CustomerAreaReport> exportCustomerAreaReport(@Param("exportQuery") ExportQuery exportQuery);

    /**
     * da
     *
     * @param exportQuery
     * @return
     */
    int countExportCustomerAreaReport(@Param("exportQuery") ExportQuery exportQuery);

    /**
     * 导出客户订货级别报表
     *
     * @param exportQuery exportQuery
     * @return List<CustomerLevelReport>
     */
    List<CustomerLevelReport> exportCustomerLevelReport(@Param("exportQuery") ExportQuery exportQuery);

    /**
     * 数量
     *
     * @param exportQuery
     * @return 总数
     */
    int countExportCustomerLevelReport(@Param("exportQuery") ExportQuery exportQuery);

    /**
     * 修改日报表中用户的名称或者账号变为最新
     *
     * @param customer customer
     * @return rows
     */
    int updateCustomerDayReport(@Param("customer") Customer customer);

    /**
     * 会员报表数据清理
     *
     * @param pname pname
     * @return rows
     */
    int clearCustomerReport(@Param("pname") String pname);

    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 运营后台客户统计--导出按客户查看数据客户订货报表数据总条数
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    int exportCustomerOrderTotalForBoss(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 运营后台客户统计--导出按客户查看数据客户订货报表数据
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    List<CustomerReport> exportCustomerOrderForBoss(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 第三方店铺客户统计--导出按客户查看数据客户订货报表数据总条数
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    int exportCustomerOrderTotalForSupplier(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 第三方店铺客户统计--导出按客户查看数据客户订货报表数据
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    List<CustomerReport> exportCustomerOrderForSupplier(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 运营后台客户统计--导出按等级查看数据客户订货报表数据总条数
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    int exportCustomerLevelOrderTotalForBoss(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 运营后台客户统计--导出按等级查看数据客户订货报表数据
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    List<CustomerLevelReport> exportCustomerLevelOrderForBoss(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 第三方店铺客户统计--导出按等级查看数据客户订货报表数据总条数
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    int exportCustomerLevelOrderTotalForSupplier(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 第三方店铺客户统计--导出按等级查看数据客户订货报表数据
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    List<CustomerLevelReport> exportCustomerLevelOrderForSupplier(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 运营后台客户统计--导出按区域查看数据客户订货报表数据总条数
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    int exportCustomerAreaOrderTotalForBoss(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 运营后台客户统计--导出按区域查看数据客户订货报表数据
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    List<CustomerAreaReport> exportCustomerAreaOrderForBoss(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 第三方店铺客户统计--导出按区域查看数据客户订货报表数据总条数
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    int exportCustomerAreaOrderTotalForSupplier(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 第三方店铺客户统计--导出按区域查看数据客户订货报表数据
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    List<CustomerAreaReport> exportCustomerAreaOrderForSupplier(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 第三方店铺和Boss端客户统计--导出客户域查看数据客户订货报表数据总条数
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    int exportCustomerOrderTotalForSupplierAndBoss(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 第三方店铺和boss客户统计--导出客户域查看数据客户订货报表数据
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    List<CustomerReport> exportCustomerOrderForSupplierAndBoss(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 第三方店铺和boss客户统计--导出按区域查看数据客户订货报表数据总条数
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    int exportCustomerAreaOrderTotalForSupplierAndBoss(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 第三方店铺和boss客户统计--导出地区域查看数据客户订货报表数据
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    List<CustomerAreaReport> exportCustomerAreaOrderForSupplierAndBoss(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家店铺类型客户统计--按天数据统计--客户订货报表按客户查看数据--预统计
     * @Date 15:03 2019/9/18
     * @Param []
     **/
    int generateCustomerOrderForSupplierStoreTypeForDay(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家店铺类型客户统计--按天统计数据统计--客户订货报表按区域查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerAreaOrderForSupplierStoreTypeForDay(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家店铺类型客户统计--按天统计数据统计--客户订货报表按等级查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerLevelOrderForSupplierStoreTypeForDay(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家店铺类型客户统计--最近7天数据统计--客户订货报表按客户查看数据--预统计
     * @Date 15:04 2019/9/18
     * @Param []
     **/
    int generateCustomerOrderForSupplierStoreTypeForSeven(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家店铺类型客户统计--最近7天统计数据统计--客户订货报表按区域查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerAreaOrderForSupplierStoreTypeForSeven(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家店铺类型客户统计--最近30天统计数据统计--客户订货报表按区域查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerAreaOrderForSupplierStoreTypeForThirty(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家店铺类型客户统计--按月统计数据统计--客户订货报表按区域查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerAreaOrderForSupplierStoreTypeForMonth(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家店铺类型客户统计--最近30天数据统计--客户订货报表按客户查看数据--预统计
     * @Date 15:05 2019/9/18
     * @Param []
     **/
    int generateCustomerOrderForSupplierStoreTypeForThirty(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家店铺类型客户统计--按月统计数据统计--客户订货报表按客户查看数据--预统计
     * @Date 15:06 2019/9/18
     * @Param []
     **/
    int generateCustomerOrderForSupplierStoreTypeForMonth(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家店铺类型客户统计--最近7天统计数据统计--客户订货报表按等级查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerLevelOrderForSupplierStoreTypeForSeven(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家店铺类型客户统计--最近30天统计数据统计--客户订货报表按等级查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerLevelOrderForSupplierStoreTypeForThirty(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return int
     * @Author lvzhenwei
     * @Description 第三方商家店铺类型客户统计--按月统计数据统计--客户订货报表按等级查看数据--预统计
     * @Date 17:28 2019/9/18
     * @Param [customerOrderDataRequest]
     **/
    int generateCustomerLevelOrderForSupplierStoreTypeForMonth(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 第三方店铺店铺类型客户统计--导出按客户查看数据客户订货报表数据总条数
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    int exportCustomerOrderTotalForSupplierByStoreType(CustomerOrderDataRequest customerOrderDataRequest);

    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 第三方店铺店铺类型客户统计--导出按客户查看数据客户订货报表数据
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    List<CustomerReport> exportCustomerOrderForSupplierByStoreType(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 第三方店铺店铺类型客户统计--导出按区域查看数据客户订货报表数据总条数
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    int exportCustomerAreaOrderTotalForSupplierByStoreType(CustomerOrderDataRequest customerOrderDataRequest);


    /**
     * @return java.util.List<com.wanmi.ares.report.customer.model.root.CustomerReport>
     * @Author lvzhenwei
     * @Description 第三方店铺客户统计--导出按区域查看数据客户订货报表数据
     * @Date 11:13 2019/9/21
     * @Param [customerOrderDataRequest]
     **/
    List<CustomerAreaReport> exportCustomerAreaOrderForSupplierByStoreType(CustomerOrderDataRequest customerOrderDataRequest);

}

