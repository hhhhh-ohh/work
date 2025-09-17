package com.wanmi.sbc.common.util.excel;

import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Nutils;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by mac on 2017/5/6.
 */
public class ExcelHelper<T> {

	/**
	 *
	 */
	private final HSSFWorkbook work;

	/**
	 * 大文件
	 */
	private final SXSSFWorkbook sxssfWorkbook;

	public ExcelHelper() {
		this.work = new HSSFWorkbook();
		this.sxssfWorkbook = new SXSSFWorkbook(1000);
	}

	/**
	 * 创建sheet
	 * @param sheetName
	 * @return
	 */
	public SXSSFSheet createSxssfSheet(String sheetName){
		return sxssfWorkbook.createSheet(sheetName);
	}

	/**
	 * @param sheetName
	 * @param dataList
	 */
	public void addSheet(String sheetName, Column[] columns, List<T> dataList) {
		int rowIndex = 0;
		HSSFSheet sheet = work.createSheet(sheetName);
		HSSFRow headRow = sheet.createRow(rowIndex++);

		//头
		int cellIndex = 0;
		for (Column c : columns) {
			HSSFCell cell = headRow.createCell(cellIndex++);
			cell.setCellValue(c.getHeader());
		}

		//体
		for (T data : dataList) {
			cellIndex = 0;
			HSSFRow row = sheet.createRow(rowIndex++);
			for (Column column : columns) {
				HSSFCell cell = row.createCell(cellIndex++);
				column.getRender().render(cell, data);
			}
		}

		//
	}

	/**
	 * @param sheetName
	 * @param columns
	 */
	public HSSFSheet addSheetHead(String sheetName, Column[] columns) {
		int rowIndex = 0;
		HSSFSheet sheet = work.createSheet(sheetName);
		HSSFRow headRow = sheet.createRow(rowIndex++);

		//头
		int cellIndex = 0;
		for (Column c : columns) {
			HSSFCell cell = headRow.createCell(cellIndex++);
			cell.setCellValue(c.getHeader());
		}
		return sheet;
	}

	/**
	 * 分页导出Excel
	 * @author  lvzhenwei
	 * @date 2021/4/27 4:37 下午
	 * @param sheet
	 * @param columns
	 * @param dataList
	 * @param rowIndex
	 * @return void
	 **/
	public void addSheetRow(HSSFSheet sheet, Column[] columns, List<T> dataList,int rowIndex) {
		//体
		for (T data : dataList) {
			int cellIndex = 0;
			HSSFRow row = sheet.createRow(rowIndex++);
			for (Column column : columns) {
				HSSFCell cell = row.createCell(cellIndex++);
				column.getRender().render(cell, data);
			}
		}
	}

	/**
	 * @param sheetName
	 * @param dataList
	 */
	public void addSheet(String sheetName, SpanColumn[] columns, List<T> dataList,
			String listPropsExp) {
		int rowIndex = 0;
		HSSFSheet sheet = work.createSheet(sheetName);
		HSSFRow headRow = sheet.createRow(rowIndex++);

		//头
		int cellIndex = 0;
		for (SpanColumn c : columns) {
			HSSFCell cell = headRow.createCell(cellIndex++);
			cell.setCellValue(c.getHeader());
		}

		//体
		for (T data : dataList) {
			cellIndex = 0;
			List list = getListProps(listPropsExp, data);
			int mergeRowCount = list != null && !list.isEmpty() ? list.size() : 0;
			HSSFRow row = sheet.createRow(rowIndex);
			boolean hasMerged = false;
			for (SpanColumn column : columns) {
				String expString = column.getPropsExp();
				List listProps = getListProps(expString, data);
				//判断当前列是不是list列
				if (listProps != null && !listProps.isEmpty()) {
					int rowIndexForList = rowIndex;
					HSSFRow rowForList;
					for (int i = 0; i < listProps.size(); i++) {
						if (sheet.getRow(rowIndexForList) != null) {
							rowForList = sheet.getRow(rowIndexForList);
						}
						else {
							rowForList = sheet.createRow(rowIndexForList);
						}
						HSSFCell cell = rowForList.createCell(cellIndex);
						new SpelColumnRender<T>(
								expString + "[" + i + "]." + column.getListPropsExp())
								.render(cell, data);
						rowIndexForList++;
					}
				}
				else if (mergeRowCount == 1) {
					HSSFCell cell = row.createCell(cellIndex);
					new SpelColumnRender<T>(expString).render(cell, data);
				}
				else {
					CellRangeAddress cra = new CellRangeAddress(rowIndex,
							rowIndex + mergeRowCount - 1, cellIndex, cellIndex);
					sheet.addMergedRegion(cra);
					HSSFCell cell = row.createCell(cellIndex);
					new SpelColumnRender<T>(expString).render(cell, data);
					hasMerged = true;
				}
				cellIndex++;
			}
			if (hasMerged) {
				rowIndex = rowIndex + mergeRowCount;
			}
			else {
				rowIndex++;
			}
		}
	}

	private List getListProps(String listExp, T data) {
		ExpressionParser parser = new SpelExpressionParser();
		try {
			Expression exp = parser.parseExpression(listExp);
			Object o = exp.getValue(data);
            if (o != null) {
				Class<?> aClass = o.getClass();
				if (aClass.isAssignableFrom(ArrayList.class)) {
					return (ArrayList) o;
				}
			}
		}
		catch (SpelEvaluationException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param op
	 */
	public void write(OutputStream op) {
		try {
			this.work.write(op);
		}
		catch (IOException e) {
			throw new SbcRuntimeException(e);
		}
	}

	public static void setError(Workbook workbook, Cell cell, String comment) {
		// 如果未设红背景
		if (IndexedColors.RED.getIndex() != cell.getCellStyle()
				.getFillBackgroundColor()) {
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(style);
		}

		setCellComment(workbook, cell, comment);
	}

	public static void setError(Workbook workbook, Cell cell, String comment,CellStyle style) {
		//如果未设红背景
		if (IndexedColors.RED.getIndex() != cell.getCellStyle()
				.getFillBackgroundColor()) {
			cell.setCellStyle(style);
		}

		setCellComment(workbook, cell, comment);
	}

	public static void setError(CellStyle style, Workbook workbook, Cell cell, String comment) {
		//如果未设红背景
		if (IndexedColors.RED.getIndex() != cell.getCellStyle()
				.getFillBackgroundColor()) {
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(style);
		}

		setCellComment(workbook, cell, comment);
	}

	public static void setCellError(Workbook workbook, Cell cell, String comment) {
		// 如果未设红背景
		if (IndexedColors.RED.getIndex() != cell.getCellStyle()
				.getFillBackgroundColor()) {
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(style);
		}

		setErrorComment(workbook, cell, comment);
	}

	private static void setErrorComment(Workbook workbook, Cell cell, String comment) {
		Sheet sheet = workbook.getSheetAt(0);
		//当前单元格索引起始值
		int beginRowIndex = cell.getRowIndex();
		int beginCelIndex = cell.getColumnIndex();
		Comment t_comment = cell.getCellComment();
		if (t_comment == null) {
			Drawing patriarch = workbook.getSheetAt(0).createDrawingPatriarch();
			ClientAnchor anchor = patriarch.createAnchor(0, 0, 0, 0,
					(short) beginCelIndex, beginRowIndex,
					(short) beginCelIndex+1, beginRowIndex+3);
			Row row1 = sheet.getRow(anchor.getRow1());
			if(row1 != null){
				Cell cell1 = row1.getCell(anchor.getCol1());
				if(cell1 != null){
					cell1.removeCellComment();
				}
			}
			t_comment = patriarch.createCellComment(anchor);
		}

		RichTextString richTextString = t_comment.getString();
		String oldComment = (richTextString == null || StringUtils
				.isEmpty(richTextString.getString())) ?
				comment :
				richTextString.getString().concat("\n").concat(comment);
		//2003的批注
		if (richTextString instanceof HSSFRichTextString) {
			t_comment.setString(new HSSFRichTextString(oldComment));
		} else {
			t_comment.setString(new XSSFRichTextString(oldComment));
		}
		cell.setCellComment(t_comment);
	}


	private static void setCellComment(Workbook workbook, Cell cell, String comment) {
		int maxRowIndex = workbook.getSheetAt(0).getLastRowNum();
		int maxCellIndex = cell.getRow().getLastCellNum();

		//当前单元格索引起始值
		int beginRowIndex = cell.getRowIndex() + 1;
		int beginCelIndex = cell.getColumnIndex() + 1;

		//范围性单元格终止值
		int endRowIndex = beginRowIndex + 2;
		int endCelIndex = beginCelIndex + 2;

		//如果终止值超出范围，新增行和列
		if (endRowIndex > maxRowIndex) {
			workbook.getSheetAt(0).createRow(endRowIndex);
		}
		if (endCelIndex > maxCellIndex) {
			cell.getRow().createCell(endCelIndex);
		}

		Comment t_comment = cell.getCellComment();
		if (t_comment == null) {
			Drawing patriarch = workbook.getSheetAt(0).createDrawingPatriarch();
			t_comment = patriarch.createCellComment(patriarch
					.createAnchor(0, 0, 0, 0, (short) beginCelIndex, beginRowIndex,
							(short) endCelIndex+1, endRowIndex+3));
		}

		RichTextString richTextString = t_comment.getString();
		String oldComment = (richTextString == null || StringUtils
				.isEmpty(richTextString.getString())) ?
				comment :
				richTextString.getString().concat("\n").concat(comment);
		//2003的批注
		if (richTextString instanceof HSSFRichTextString) {
			t_comment.setString(new HSSFRichTextString(oldComment));
		} else {
			t_comment.setString(new XSSFRichTextString(oldComment));
		}
		cell.setCellComment(t_comment);
	}

	/**
	 * 清除文件批注
	 * @param workbook
	 */
	public static void clearComment(Workbook workbook, Cell cell) {
		//如果设红背景，去除背景
		if (IndexedColors.RED.getIndex() == cell.getCellStyle()
				.getFillForegroundColor() ) {
			CellStyle cellStyle = cell.getCellStyle();
			cellStyle.setFillPattern(FillPatternType.NO_FILL);
			cell.setCellStyle(cellStyle);
		}

		int maxRowIndex = workbook.getSheetAt(0).getLastRowNum();
		int maxCellIndex = cell.getRow().getLastCellNum();

		//当前单元格索引起始值
		int beginRowIndex = cell.getRowIndex() + 1;
		int beginCelIndex = cell.getColumnIndex() + 1;

		//范围性单元格终止值
		int endRowIndex = beginRowIndex + 2;
		int endCelIndex = beginCelIndex + 2;

		//如果终止值超出范围，新增行和列
		if (endRowIndex > maxRowIndex) {
			workbook.getSheetAt(0).createRow(endRowIndex);
		}
		if (endCelIndex > maxCellIndex) {
			cell.getRow().createCell(endCelIndex);
		}

		Comment t_comment = cell.getCellComment();
		if (t_comment != null) {
			cell.setCellComment(null);
		}
	}

	/**
	 * 获取值
	 */
	public static String getValue(Cell cell) {
		if (cell.getCellType() == CellType.BOOLEAN) {
			// 返回布尔类型的值
			return Nutils.nonNullActionRt(cell.getBooleanCellValue(), Object::toString, StringUtils.EMPTY);
		}
		else if (cell.getCellType() == CellType.NUMERIC) {
			// 返回数值类型的值

			return BigDecimal.valueOf(cell.getNumericCellValue())
					// 修改cell 自动加.0的 bug
					.stripTrailingZeros().toPlainString()
					.trim();
		}
		else {
			// 返回字符串类型的值
			return Nutils.nonNullActionRt(cell.getStringCellValue(), Object::toString, StringUtils.EMPTY);
		}
	}

	/***
	 * 获取Excel某个单元格的值
	 * @param cells	单元格数组
	 * @param index	下标
	 * @return		单元格的值
	 */
	public static String getValue(Cell[] cells, int index) {
		if (Objects.isNull(cells) || cells.length < index){
			return "";
		}
		Cell cell = cells[index];
		CellType cellType = cell.getCellType();

		String resultVal = "";
		switch (cellType){
			case BOOLEAN:
				resultVal = Nutils.toStr(cell.getBooleanCellValue(), true);
				break;
			case NUMERIC:
				resultVal = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString()
						.trim();
				break;
			default:
				resultVal = Nutils.toStr(cell.getStringCellValue(), true);
				break;
		}
		return resultVal;
	}

	/**
	 * 获取excel为空的数据行数
	 * @param sheet
	 * @return
	 */
	public static int getEmptyRowNum(Sheet sheet){
		int firstRowNum = sheet.getFirstRowNum();
		//获得当前sheet的结束行
		int lastRowNum = sheet.getLastRowNum();
		int finalRowNum = 0;
		for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
			Row row = sheet.getRow(rowNum);
			if(Objects.isNull(row)){
				continue;
			}
			Cell cell = row.getCell(0);
			if (cell == null) {
				finalRowNum = finalRowNum + 1;
			}
			if( cell !=null && StringUtils.isBlank(getValue(cell))){
				finalRowNum = finalRowNum + 1;
			}
		}
		return  finalRowNum;
	}

	/**
	 * 获取excel为空的数据行数
	 * @param sheet
	 * @return
	 */
	public static int getOrderForCustomerEmptyRowNum(Sheet sheet){
		int firstRowNum = sheet.getFirstRowNum();
		//获得当前sheet的结束行
		int lastRowNum = sheet.getLastRowNum();
		int finalRowNum = 0;
		for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
			Row row = sheet.getRow(rowNum);
			if(Objects.isNull(row)){
				continue;
			}
			Cell cell = row.getCell(0);
			Cell cell2 = row.getCell(1);
			if (cell == null && cell2 == null) {
				finalRowNum = finalRowNum + 1;
			}
			if( cell !=null && StringUtils.isBlank(getValue(cell))
					&& cell2 !=null && StringUtils.isBlank(getValue(cell2))){
				finalRowNum = finalRowNum + 1;
			}
		}
		return  finalRowNum;
	}

	/**
	 * 设置下拉列表元素
	 *
	 * @param strFormula 区域值 如sheet2!$A$1:$A$53
	 * @param firstRow   起始行
	 * @param endRow     终止行
	 * @param firstCol   起始列
	 * @param endCol     终止列
	 * @return HSSFDataValidation
	 * @throws
	 */
	public static HSSFDataValidation getDataValidation(String strFormula, int firstRow,
			int endRow, int firstCol, int endCol) {

		// 设置数据有效性加载在哪个单元格上。四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow,
				firstCol, endCol);
		DVConstraint constraint = DVConstraint.createFormulaListConstraint(strFormula);
		HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
		dataValidation.createErrorBox("Error", "Error");
		dataValidation.createPromptBox("", null);
		return dataValidation;
	}


	/**
	 * @description 大数据量导出
	 * @author  xuyunpeng
	 * @date 2021/5/31 5:16 下午
	 * @param sheetName
	 * @param columns
	 * @param dataList
	 * @return
	 */
	public void addSXSSFSheet(String sheetName, Column[] columns, List<T> dataList, boolean isActiveSheet) {
		int rowIndex = 0;
		sxssfWorkbook.setCompressTempFiles(true);
		SXSSFSheet sheet = sxssfWorkbook.createSheet(sheetName);
		if (isActiveSheet){
			sxssfWorkbook.setActiveSheet(sxssfWorkbook.getSheetIndex(sheet));
		}
		SXSSFRow headRow = sheet.createRow(rowIndex++);

		//头
		int cellIndex = 0;
		for (Column c : columns) {
			SXSSFCell cell = headRow.createCell(cellIndex++);
			cell.setCellValue(c.getHeader());
		}

		//体
		for (T data : dataList) {
			cellIndex = 0;
			SXSSFRow row = sheet.createRow(rowIndex++);
			for (Column column : columns) {
				SXSSFCell cell = row.createCell(cellIndex++);
				column.getRender().render(cell, data);
			}
		}
	}

	/**
	 * @param sheetName
	 * @param columns
	 */
	public SXSSFSheet addSXSSFSheetHead(String sheetName, Column[] columns) {
		int rowIndex = 0;
		SXSSFSheet sheet = sxssfWorkbook.createSheet(sheetName);
		SXSSFRow headRow = sheet.createRow(rowIndex++);

		//头
		int cellIndex = 0;
		for (Column c : columns) {
			SXSSFCell cell = headRow.createCell(cellIndex++);
			cell.setCellValue(c.getHeader());
		}
		return sheet;
	}

	/**
	 * 创建表头，需指定开始行
	 * @param sheet
	 * @param rowIndex
	 * @param columns
	 */
	public SXSSFSheet addSXSSFSheetHead(SXSSFSheet sheet, int rowIndex, Column[] columns) {
		SXSSFRow headRow = sheet.createRow(rowIndex++);
		//头
		int cellIndex = 0;
		for (Column c : columns) {
			SXSSFCell cell = headRow.createCell(cellIndex++);
			cell.setCellValue(c.getHeader());
		}
		return sheet;
	}

	/**
	 * @param sheetName
	 * @param columns
	 */
	public SXSSFSheet addSXSSFSheetHead(String sheetName, List<Column> columns) {
		SXSSFSheet sheet = sxssfWorkbook.createSheet(sheetName);
		SXSSFRow headRow = sheet.createRow(0);

        if (WmCollectionUtils.isNotEmpty(columns)) {
            int cellIndex = 0;
            for (Column c : columns) {
                SXSSFCell cell = headRow.createCell(cellIndex++);
                cell.setCellValue(c.getHeader());
            }
		}
		return sheet;
	}

	/**
	 * 分页导出Excel
	 * @author  lvzhenwei
	 * @date 2021/4/27 4:37 下午
	 * @param sheet
	 * @param columns
	 * @param dataList
	 * @param rowIndex
	 * @return void
	 **/
	public void addSXSSFSheetRow(SXSSFSheet sheet, Column[] columns, List<T> dataList,int rowIndex) {
		//体
		int i = 0;
		for (T data : dataList) {
			i++;
			int cellIndex = 0;
			SXSSFRow row = sheet.createRow(rowIndex++);
			for (Column column : columns) {
				SXSSFCell cell = row.createCell(cellIndex++);
				column.getRender().render(cell, data);
			}
		}
	}

	/**
	 * 分页导出Excel
	 * @author  lvzhenwei
	 * @date 2021/4/27 4:37 下午
	 * @param sheet
	 * @param columns
	 * @param dataList
	 * @param rowIndex
	 * @return void
	 **/
	public void addSXSSFSheetRow(SXSSFSheet sheet, List<Column> columns, List<T> dataList,int rowIndex) {
		//体
		int i = 0;
		for (T data : dataList) {
			i++;
			int cellIndex = 0;
			SXSSFRow row = sheet.createRow(rowIndex++);
			for (Column column : columns) {
				SXSSFCell cell = row.createCell(cellIndex++);
				column.getRender().render(cell, data);
			}
		}
	}

	/**
	 * @param op
	 */
	public void writeForSXSSF(OutputStream op) {
		try {
			this.sxssfWorkbook.write(op);
			//清理临时文件
			sxssfWorkbook.dispose();
		}
		catch (IOException e) {
			throw new SbcRuntimeException(e);
		}
	}

	/**
	 * 删除临时文件
	 */
	public void deleteTempFile(){
		sxssfWorkbook.dispose();
	}

	/**
	 * 支持必填和下拉选项
	 * @param sheet
	 * @param columns
	 * @param dataList
	 */
	public void addSheetPlus(HSSFSheet sheet, Column[] columns, List<T> dataList) {
		int rowIndex = 0;
		HSSFRow headRow = sheet.createRow(rowIndex++);

		//头
		int cellIndex = 0;
		for (Column c : columns) {
			// 设置列宽
			sheet.setColumnWidth(cellIndex, 6000);
			HSSFCell cell = headRow.createCell(cellIndex++);

			if (c.getRequired()) {
				// 必填字段开头，添加红色*标识
				String header = c.getHeader();
				HSSFRichTextString ts= new HSSFRichTextString("*" + header);
				HSSFFont font = work.createFont();
				font.setColor(HSSFFont.COLOR_RED);
				ts.applyFont(0,1, font);
				cell.setCellValue(ts);
			} else {
				cell.setCellValue(c.getHeader());
			}
		}

		//体
		for (T data : dataList) {
			cellIndex = 0;
			HSSFRow row = sheet.createRow(rowIndex++);
			for (Column column : columns) {
				HSSFCell cell = row.createCell(cellIndex++);
				column.getRender().render(cell, data);

				// 判断下拉选择数组是否为空，不为空则添加下拉选择功能
				String[] chooseArray = column.getChooseArray();
				if (ArrayUtils.isNotEmpty(chooseArray)) {
					this.setHSSFValidation(sheet, chooseArray, cell.getRowIndex(), cell.getRowIndex(),
							cell.getColumnIndex(), cell.getColumnIndex());
				}
			}
		}
	}

	/**
	 * 设置下拉列表选择
	 * @param sheet
	 * @param textlist
	 * @param firstRow
	 * @param endRow
	 * @param firstCol
	 * @param endCol
	 * @return
	 */
	public HSSFSheet setHSSFValidation(HSSFSheet sheet, String[] textlist, int firstRow, int endRow,
									   int firstCol, int endCol) {
		// 加载下拉列表内容
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
		// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
		sheet.addValidationData(data_validation_list);
		return sheet;
	}

	/**
	 * 导出插入图片
	 * @param drawing
	 * @param rowIndex
	 * @param colIndex
	 * @param bytes
	 * @throws IOException
	 */
	public void insertExcelPic(SXSSFWorkbook sxssfWorkbook, Drawing<?> drawing, int rowIndex, int colIndex, byte[] bytes) throws IOException {
		try {
			int pictureIdx = sxssfWorkbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			CreationHelper helper = sxssfWorkbook.getCreationHelper();
			ClientAnchor anchor = helper.createClientAnchor();
			anchor.setCol1(colIndex); // 图片起始列
			anchor.setRow1(rowIndex); // 图片起始行
			Picture picture = drawing.createPicture(anchor, pictureIdx);
			// 设置图片大小
			picture.resize();
		} catch (Exception e) {
			throw new SbcRuntimeException(e);
		}
	}
}
