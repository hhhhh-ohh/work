package com.wanmi.sbc.customer.child.repository;

import com.wanmi.sbc.customer.child.model.root.CustomerChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomerChildRepository  extends JpaRepository<CustomerChild, String> {

    List<CustomerChild> findByCustomerId(String customerId);

    CustomerChild findByChildId(String childId);

    @Modifying
    @Query("update CustomerChild set schoolName = ?2, schoolCode = ?3, badgeCode = ?4, updateTime = ?5 where childId = ?1")
    int updateSchoolByChildId(String childId, String schoolName, String schoolCode,String badgeCode, LocalDateTime updateTime);
}
