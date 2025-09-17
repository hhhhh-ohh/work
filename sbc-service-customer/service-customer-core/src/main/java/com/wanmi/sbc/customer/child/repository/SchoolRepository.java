package com.wanmi.sbc.customer.child.repository;

import com.wanmi.sbc.customer.child.model.root.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository   extends JpaRepository<School, Integer> {

    List<School> findByAreaId(Long schoolId);
}
