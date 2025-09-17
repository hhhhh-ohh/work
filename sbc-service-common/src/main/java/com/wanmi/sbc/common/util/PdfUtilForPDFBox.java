package com.wanmi.sbc.common.util;

import com.google.common.collect.Lists;
import com.lowagie.text.pdf.BaseFont;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author edy
 * @className PdfUtilForPDFBox
 * @description html转pdf 以及 pdf合并
 * @date 2023/8/15 下午3:00
 **/
@Slf4j
public class PdfUtilForPDFBox {

    public PdfUtilForPDFBox(){}

    public static File htmlToPdf(String htmlName, String partA, String partB) {
//        File tmpHtmlFile = null;
        try {
            //获取合同html模版
            String htmlPath = PdfUtilForPDFBox.class.getClass().getResource("/"+htmlName).getPath();
            log.info("htmlPath========"+htmlPath);
            //替换html内容
            String content = new String(Files.readAllBytes(Paths.get(htmlPath)), StandardCharsets.UTF_8);
            // 替换指定内容
            String replacedContent = content.replace(Constants.PART_A, partA)
                    .replace(Constants.PART_B, partB);
            // 将替换后的内容重写到新的html文件
//            String tmpHtmlPath = System.getProperty("user.dir").concat("/temp.html");
//            log.info("tmpHtmlPath========"+tmpHtmlPath);
//            Files.write(Paths.get(tmpHtmlPath), replacedContent.getBytes(StandardCharsets.UTF_8));
//            tmpHtmlFile = new File(tmpHtmlPath);
            //将html转PDF
            String pdfPath = System.getProperty("user.dir").concat("/temp.pdf");
            log.info("pdfPath========"+pdfPath);
            File file = new File(pdfPath);
            OutputStream os = new FileOutputStream(file);
            File parent = file.getAbsoluteFile().getParentFile();
            String baseUrl = parent == null ? "" : parent.toURI().toURL().toExternalForm();
            ITextRenderer renderer = ITextRenderer.fromString(replacedContent, baseUrl);
            //添加字体，解决中文不显示的问题
            renderer.getFontResolver().addFont(PdfUtilForPDFBox.class.getClass().getResource("/simsun.ttc").getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);
            os.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert HTML to PDF.");
        } finally{
            //删除临时html文件
//            tmpHtmlFile.deleteOnExit();
        }
    }

    /**
     * @description html 转成pdf 的字节数组
     * @author
     * @date
     * @param htmlName
     * @param partA
     * @param partB
     * @return
     **/
    public static byte[] htmlToPdfByte(String htmlName, String partA, String partB) {
        File file = htmlToPdf(htmlName,partA,partB);
        try (FileInputStream fileInputStream = new FileInputStream(file)){
            return IOUtils.toByteArray(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        } finally {
            //file.deleteOnExit();
        }
    }

    /**
     * 合并pdf文件流
     * @param fileBytes
     * @return
     */
    public static byte[] mergePdfFile(List<byte[]> fileBytes) {
        List<File> mergeTempFileList = new ArrayList<>();
        try {
            PDFMergerUtility pdfMerger = new PDFMergerUtility();
            String mergeTempPdfPath = System.getProperty("user.dir").concat("/mergeTemp.pdf");
            pdfMerger.setDestinationFileName(mergeTempPdfPath);
            for (int i = 0; i < fileBytes.size(); i++) {
                byte[] fileByte = fileBytes.get(i);
                String mergeTemp1PdfPath = System.getProperty("user.dir").concat("/mergeTemp_" + i).concat(".pdf");
                FileOutputStream mergeTemp1Stream = new FileOutputStream(mergeTemp1PdfPath);
                mergeTemp1Stream.write(fileByte);
                File mergeTemp1PdfPathFile= new File(mergeTemp1PdfPath);
                pdfMerger.addSource(mergeTemp1PdfPathFile);
                mergeTempFileList.add(mergeTemp1PdfPathFile);
            }
            pdfMerger.mergeDocuments(null);
            File mergerFile = new File(mergeTempPdfPath);
            FileInputStream fileInputStream = new FileInputStream(mergerFile);
            mergeTempFileList.add(mergerFile);
            return IOUtils.toByteArray(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to merge PDF files.");
        } finally{
            for(File file : mergeTempFileList){
                file.deleteOnExit();
            }
        }
    }

  public static void main(String[] args) {
      try {
          //测试html转pdf
          byte[] tempPdfByte = htmlToPdfByte(Constants.LAKALA_LEDGER_CONTRACT,"partA","partB");
          log.info("tempPdfByte==="+tempPdfByte.length);
          //测试pdf合并
          File file1 = new File("D:\\workspaces\\sbc\\sbc-service-common\\target\\classes\\temp.pdf");
          File file2 = new File("D:\\workspaces\\sbc\\sbc-service-common\\target\\classes\\temp2.pdf");
          // 合并的PDF文件数组
          FileInputStream fileInputStream = new FileInputStream(file1);
          FileInputStream fileInputStream2 = new FileInputStream(file2);

          byte[] pdf1 = IOUtils.toByteArray(fileInputStream);
          byte[] pdf2 = IOUtils.toByteArray(fileInputStream2);
          List<byte[]> fileBytes = Lists.newArrayList(pdf1, pdf2);
          byte[] mergePdfByte = mergePdfFile(fileBytes);
          log.info("mergePdfByte==="+mergePdfByte.length);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

}
