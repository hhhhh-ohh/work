package com.wanmi.sbc.customer;

import com.wanmi.sbc.common.enums.GenderType;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.customer.agent.service.AgentService;
import com.wanmi.sbc.customer.api.request.child.CustomerChildSaveRequest;
import com.wanmi.sbc.customer.api.request.growthvalue.CustomerGrowthValueAddRequest;
import com.wanmi.sbc.customer.bean.enums.GrowthValueServiceType;
import com.wanmi.sbc.customer.child.model.root.CustomerChild;
import com.wanmi.sbc.customer.child.service.CustomerChildService;
import com.wanmi.sbc.customer.growthvalue.repository.CustomerGrowthValueRepository;
import com.wanmi.sbc.customer.growthvalue.service.GrowthValueIncreaseService;
import com.wanmi.sbc.customer.level.model.root.CustomerLevel;
import com.wanmi.sbc.customer.level.repository.CustomerLevelRepository;
import com.wanmi.sbc.customer.model.root.Customer;
import com.wanmi.sbc.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@SpringBootTest
@Slf4j
public class CustomerServiceTestApplication {

    @Autowired
    private GrowthValueIncreaseService growthValueIncreaseService;
    @Autowired
    private CustomerLevelRepository customerLevelRepository;
    @Autowired
    private CustomerChildService customerChildService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerGrowthValueRepository customerGrowthValueRepository;
    @Autowired
    private AgentService agentService;

    @Test
    public void testSaveChild(){
        CustomerChild customerChild = new CustomerChild();
        customerChild.setCustomerId("4028198198a830090198a844ccfb0001");
        customerChild.setParentName("张三老爹");
        customerChild.setChildName("王二麻子");
        customerChild.setChildGender(GenderType.FEMALE);
        customerChild.setChildBirthday(LocalDate.now());
        CustomerChildSaveRequest customerChildSaveRequest =
        CustomerChildSaveRequest.builder()
                .customerId(customerChild.getCustomerId())
                .parentName(customerChild.getParentName())
                .childName(customerChild.getChildName())
                .childBirthday(customerChild.getChildBirthday())
                .childGender(customerChild.getChildGender())
                .build();
        customerChildService.saveChild(customerChildSaveRequest);
    }
    @Test
    public void testGetListChild(){
        List<CustomerChild> customerChildList = customerChildService.findChildsByCustomerId("4028198198a830090198a844ccfb0001");
        log.info("customerChildList:{}", customerChildList);
    }

    @Test
    public void test() {
        CustomerGrowthValueAddRequest request = new CustomerGrowthValueAddRequest();

        growthValueIncreaseService.increaseGrowthValue( request.builder().customerId("4028198198a830090198a844ccfb0001")
                .type(OperateType.GROWTH)
                .serviceType(GrowthValueServiceType.SWDH5ORDERCOMPLETION)
                .growthValue(1000L)
                .tradeNo("XXXXXXXXXXXX")
                .opTime(LocalDateTime.now())
                .build());
    }
    @Test
    public void test2() {
        // 查询所有平台等级,按成长值降序
        List<CustomerLevel> allCustomerLevelList = customerLevelRepository.listLevelOrderByIdAsc();
        log.info("allCustomerLevelList:{}", allCustomerLevelList);
        CustomerLevel previousLevel = findPreviousLevelStream(allCustomerLevelList, 1L);
        log.info("previousLevel:{}", previousLevel);

    }
    @Test
    public void testDownLevel(){
       // customerRepository.downCustomerLevel("4028198197f241f90197f3b8cad40000", 0L, 1L);
        //customerRepository.updateMemberShipExpiredTime("4028198197f241f90197f3b8cad40000", LocalDateTime.now().plusYears(1));
        List<GrowthValueServiceType> serviceTypes = new ArrayList<>();

        serviceTypes.add(GrowthValueServiceType.ORDERCOMPLETION);
        serviceTypes.add(GrowthValueServiceType.SWDH5ORDERCOMPLETION);

        Integer summedGrowthValue = customerGrowthValueRepository.sumGrowthValueThePastYear("402819819888c777019889561cda0000", LocalDateTime.now().minusYears(1),serviceTypes);
        log.info("summedGrowthValue:{}", summedGrowthValue);
    }
    @Test
    public void testAvoidRelegation(){
        String customerId = "402819819888c777019889561cda0000";
        Customer customer =  customerRepository.findById(customerId).orElse(null);
        List<CustomerLevel> allCustomerLevelList = customerLevelRepository.listLevelOrderByIdAsc();
        Boolean avoidRelegation = isAvoidRelegation(customer, allCustomerLevelList);
        log.info("avoidRelegation:{}", avoidRelegation);

    }

    private Boolean isAvoidRelegation(Customer customer, List<CustomerLevel> allCustomerLevelList ) {
        if (customer.getMembershipExpiredTime() == null){
            return false;
        }
        // 获得有效期时间一年前的时间点
        LocalDateTime oneYearBefore = customer.getMembershipExpiredTime().minusYears(1);
        List<GrowthValueServiceType> serviceTypes = new ArrayList<>();
        serviceTypes.add(GrowthValueServiceType.ORDERCOMPLETION);
        serviceTypes.add(GrowthValueServiceType.SWDH5ORDERCOMPLETION);
        //统计近一年的消费金额
        Integer growthValueThePastYear = customerGrowthValueRepository.sumGrowthValueThePastYear(customer.getCustomerId(), oneYearBefore, serviceTypes);
        if (growthValueThePastYear == null){
            return false;
        }
        //查询用户当前等级的所需成长值
        Long needGrowthValue = allCustomerLevelList.stream().filter(level -> level.getCustomerLevelId().equals(customer.getCustomerLevelId()))
                .findFirst().map(CustomerLevel::getGrowthValue).get();
        if (growthValueThePastYear >= needGrowthValue){
            return true;
        }
        return false;
    }

    @Test
    public void test3() {
        CustomerGrowthValueAddRequest request = new CustomerGrowthValueAddRequest();

        growthValueIncreaseService.increaseGrowthValue( request.builder().customerId("4028198198a830090198a844ccfb0001")
                .type(OperateType.GROWTH)
                .serviceType(GrowthValueServiceType.DOWNLEVEL)
                .opTime(LocalDateTime.now())
                .build());
    }

    public CustomerLevel findPreviousLevelStream(List<CustomerLevel> allCustomerLevelList, Long currentLevelId) {
        if (allCustomerLevelList == null || allCustomerLevelList.isEmpty() ) {
            return null;
        }
        if (currentLevelId == null || currentLevelId.equals(allCustomerLevelList.get(0).getCustomerLevelId())) {
            return allCustomerLevelList.get(0);
        }
        return allCustomerLevelList.stream()
                .filter(level -> level.getCustomerLevelId() < currentLevelId)
                .reduce((first, second) -> second) // 获取最后一个满足条件的
                .orElse(null);
    }
}
