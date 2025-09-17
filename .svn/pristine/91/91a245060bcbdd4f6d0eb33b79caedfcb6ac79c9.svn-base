
package com.wanmi.ares.view.export;

import lombok.Data;

@Data
public class ExportDataView {

  /**
   * 主键
   */
  private long id; // optional
  /**
   * 用户标识
   */
  private String userId; // optional
  /**
   * 商家标识
   */
  private Long companyInfoId; // optional
  /**
   * 开始日期
   */
  private String beginDate; // optional
  /**
   * 截止日期
   */
  private String endDate; // optional
  /**
   * 导出报表类别
   *
   * @see com.wanmi.ares.enums.ReportType
   */
  private String typeCd; // optional
  /**
   * 导出状态(等待生成导出文件,导出文件生成中,导出文件生成完毕)
   *
   * @see com.wanmi.ares.enums.ExportStatus
   */
  private com.wanmi.ares.enums.ExportStatus exportStatus; // optional
  /**
   * 发起导出请求时间
   */
  private String createTime; // optional
  /**
   * 文件成功生成时间/错误时间
   */
  private String finishTime; // optional
  /**
   * 导出文件下载全路径
   */
  private String filePath; // optional


  /**
   * 自然月份 如：2021-02
   */
  private String month;
}
