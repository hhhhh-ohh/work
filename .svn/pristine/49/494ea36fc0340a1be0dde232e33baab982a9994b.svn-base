package com.wanmi.sbc.account.bank.model.root;


import com.wanmi.sbc.common.enums.DeleteFlag;
import lombok.Data;

import jakarta.persistence.*;


/**
 * 银行entity
 * Created by sunkun on 2017/11/30.
 */
@Data
@Entity
@Table(name = "bank")
public class Bank {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private Long bankId;

    /**
     * 银行名称
     */
    @Column(name = "bank_name")
    private String bankName;

    /**
     * 银行code
     */
    @Column(name = "bank_code")
    private String bankCode;

    /**
     * 删除标记
     */
    @Column(name = "del_flag")
    private DeleteFlag deleteFlag;

}
