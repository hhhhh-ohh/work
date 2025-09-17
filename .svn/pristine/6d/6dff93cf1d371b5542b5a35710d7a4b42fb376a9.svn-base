package com.wanmi.sbc.customer.provider.impl.points;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailSaveProvider;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsBatchAdjustRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailBatchAddRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailImportAddRequest;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.customer.model.root.Customer;
import com.wanmi.sbc.customer.points.service.CustomerPointsDetailService;
import com.wanmi.sbc.setting.api.provider.PointsBasicRuleQueryProvider;
import com.wanmi.sbc.setting.api.provider.SystemPointsConfigQueryProvider;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>会员积分明细保存服务接口实现</p>
 */
@Slf4j
@RestController
@Validated
public class CustomerPointsDetailController implements CustomerPointsDetailSaveProvider {
    @Autowired
    private CustomerPointsDetailService customerPointsDetailService;

    @Autowired
    private SystemPointsConfigQueryProvider systemPointsConfigQueryProvider;

    @Autowired
    private PointsBasicRuleQueryProvider pointsBasicRuleQueryProvider;

    @Override
    public BaseResponse add(@RequestBody @Valid CustomerPointsDetailAddRequest request) {
        // 增加积分
        if (request.getType() == OperateType.GROWTH) {
            if (request.getServiceType() == PointsServiceType.REGISTER) {// 注册
                customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_REGISTER);
            } else if (request.getServiceType() == PointsServiceType.BINDINGWECHAT) {// 绑定微信
                customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_BIND_WECHAT);
            } else if (request.getServiceType() == PointsServiceType.PERFECTINFO) {// 完善个人信息
                customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_COMPLETE_INFORMATION);
            } else if (request.getServiceType() == PointsServiceType.ADDSHIPPINGADDRESS) {// 添加收货地址
                customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_ADD_DELIVERY_ADDRESS);
            } else if (request.getServiceType() == PointsServiceType.FOCUSONSTORE) {// 收藏店铺
                customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_FOLLOW_STORE);
            } else if (request.getServiceType() == PointsServiceType.SHARE) {// 分享商品
                customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_SHARE_GOODS);
            } else if (request.getServiceType() == PointsServiceType.EVALUATE) {// 评论商品
                customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_COMMENT_GOODS);
            } else if (request.getServiceType() == PointsServiceType.SHAREREGISTER) {// 分享注册
                customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_SHARE_REGISTER);
            } else if (request.getServiceType() == PointsServiceType.SHAREPURCHASE) {// 分享购买
                customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_SHARE_BUY);
            } else { // 订单完成、会员导入
                customerPointsDetailService.increasePoints(request, null);
            }
        } else {
            // 扣除积分
            customerPointsDetailService.deductPoints(request);
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse batchAdd (@RequestBody @Valid CustomerPointsBatchAdjustRequest request) {
        customerPointsDetailService.batchIncreasePoints(request);
        return BaseResponse.SUCCESSFUL() ;
    }

    @Override
    public BaseResponse batchReduce (@RequestBody @Valid CustomerPointsBatchAdjustRequest request) {
        customerPointsDetailService.batchReducePoints(request);
        return BaseResponse.SUCCESSFUL() ;
    }

    @Override
    public BaseResponse batchCover (@RequestBody @Valid CustomerPointsBatchAdjustRequest request) {
        customerPointsDetailService.batchCoverPoints(request);
        return BaseResponse.SUCCESSFUL() ;
    }

    @Override
    public BaseResponse batchAddCustomer(@RequestBody @Valid CustomerPointsDetailImportAddRequest addRequest) {
        List<List<CustomerPointsDetailAddRequest>> requestList = addRequest.getCustomerPointsDetailAddRequestList();
        boolean isPointsOpen = systemPointsConfigQueryProvider.isPointsOpen().getContext().isOpen();
        List<ConfigVO> configs = pointsBasicRuleQueryProvider.listPointsBasicRule().getContext().getConfigVOList();
        ExecutorService executorService = this.newThreadPoolExecutor(requestList.size());
        CountDownLatch count = new CountDownLatch(requestList.size());
        for (List<CustomerPointsDetailAddRequest> addRequests : requestList) {
            executorService.execute(() -> {
                addRequests.forEach(request -> {
                    // 增加积分
                    if (request.getType() == OperateType.GROWTH) {
                        if (request.getServiceType() == PointsServiceType.REGISTER) {// 注册
                            customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_REGISTER, isPointsOpen, configs);
                        } else if (request.getServiceType() == PointsServiceType.BINDINGWECHAT) {// 绑定微信
                            customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_BIND_WECHAT, isPointsOpen, configs);
                        } else if (request.getServiceType() == PointsServiceType.PERFECTINFO) {// 完善个人信息
                            customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_COMPLETE_INFORMATION, isPointsOpen, configs);
                        } else if (request.getServiceType() == PointsServiceType.ADDSHIPPINGADDRESS) {// 添加收货地址
                            customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_ADD_DELIVERY_ADDRESS, isPointsOpen, configs);
                        } else if (request.getServiceType() == PointsServiceType.FOCUSONSTORE) {// 收藏店铺
                            customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_FOLLOW_STORE, isPointsOpen, configs);
                        } else if (request.getServiceType() == PointsServiceType.SHARE) {// 分享商品
                            customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_SHARE_GOODS, isPointsOpen, configs);
                        } else if (request.getServiceType() == PointsServiceType.EVALUATE) {// 评论商品
                            customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_COMMENT_GOODS, isPointsOpen, configs);
                        } else if (request.getServiceType() == PointsServiceType.SHAREREGISTER) {// 分享注册
                            customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_SHARE_REGISTER, isPointsOpen, configs);
                        } else if (request.getServiceType() == PointsServiceType.SHAREPURCHASE) {// 分享购买
                            customerPointsDetailService.increasePoints(request, ConfigType.POINTS_BASIC_RULE_SHARE_BUY, isPointsOpen, configs);
                        } else { // 订单完成、会员导入
                            customerPointsDetailService.increasePoints(request, null, isPointsOpen, configs);
                        }
                    } else {
                        // 扣除积分
                        customerPointsDetailService.deductPoints(request);
                    }
                });
                count.countDown();
            });
        }
        this.await(count);
        executorService.shutdown();
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 创建线程池
     *
     * @return
     */
    private ExecutorService newThreadPoolExecutor(int corePoolSize) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("会员导入-%d").build();
        int maximumPoolSize = corePoolSize * 2;
        int capacity = 1010;
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                NumberUtils.LONG_ZERO, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(capacity), namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }


    /**
     * 主进程等待线程执行结束
     *
     * @param count
     */
    private void await(CountDownLatch count) {
        try {
            count.await();
        } catch (InterruptedException e) {
            log.error("客户导入线程等待异常:{}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public BaseResponse returnPoints(@RequestBody @Valid CustomerPointsDetailAddRequest request) {
        customerPointsDetailService.returnPoints(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse batchAdd(@RequestBody CustomerPointsDetailBatchAddRequest request) {
        customerPointsDetailService.increasePoints(request);
        return BaseResponse.SUCCESSFUL();
    }
}
