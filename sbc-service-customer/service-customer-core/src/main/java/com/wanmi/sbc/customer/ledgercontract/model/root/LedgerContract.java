package com.wanmi.sbc.customer.ledgercontract.model.root;


import jakarta.persistence.*;
import lombok.Data;

/**
 * <p>分账合同协议配置实体类</p>
 * @author 许云鹏
 * @date 2022-07-07 17:54:08
 */
@Data
@Entity
@Table(name = "ledger_contract")
public class LedgerContract {

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 协议标题
	 */
	@Column(name = "title")
	private String title;

	/**
	 * 正文
	 */
	@Column(name = "body")
	private String body;

	/**
	 * 协议内容
	 */
	@Column(name = "content")
	private String content;

}
