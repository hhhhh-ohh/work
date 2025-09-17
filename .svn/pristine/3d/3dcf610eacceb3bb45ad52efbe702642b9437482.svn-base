package com.wanmi.sbc.customer.child.service;

import com.wanmi.sbc.customer.child.model.root.School;
import com.wanmi.sbc.customer.child.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {
    @Autowired
    private SchoolRepository schoolRepository;

    public List<School> findByAreaId(Long areaId) {
        return schoolRepository.findByAreaId(areaId);
    }
}
