package com.wanmi.sbc.customer.ledgerfile.model.root;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 * <p>分账文件实体类</p>
 * @author 许云鹏
 * @date 2022-07-01 16:43:39
 */
@Data
@Entity
@Table(name = "ledger_file")
public class LedgerFile {

	/**
	 * id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;

	/**
	 * 文件内容
	 */
	@Column(name = "content")
	private byte[] content;

	/**
	 * 文件扩展名
	 */
	@Column(name = "file_ext")
	private String fileExt;

	/**
	 * 合同内容，html字符串
	 */
	@Column(name = "content_str")
	private String contentStr;

}
