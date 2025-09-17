//package com.wanmi.sbc.common.util;
//
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontProvider;
//import com.itextpdf.text.pdf.*;
//import com.itextpdf.tool.xml.Pipeline;
//import com.itextpdf.tool.xml.XMLWorker;
//import com.itextpdf.tool.xml.XMLWorkerHelper;
//import com.itextpdf.tool.xml.css.CssFilesImpl;
//import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
//import com.itextpdf.tool.xml.html.CssAppliersImpl;
//import com.itextpdf.tool.xml.html.HTML;
//import com.itextpdf.tool.xml.html.TagProcessorFactory;
//import com.itextpdf.tool.xml.html.Tags;
//import com.itextpdf.tool.xml.parser.XMLParser;
//import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
//import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
//import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
//import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
//import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
//import com.wanmi.sbc.common.exception.SbcRuntimeException;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.io.IOUtils;
//import org.springframework.util.Base64Utils;
//
//import java.io.*;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//import java.util.Objects;
//
//public class PdfUtil {
//
//    private PdfUtil(){}
//
//
//    public static File htmlToPdf(String html) {
//        File file = new File(System.getProperty("user.dir").concat("/temp.pdf"));
//        Document document = null;
//        try (
//                FileOutputStream fileOutputStream = new FileOutputStream(file);
//                ByteArrayInputStream bais = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
//                ){
//            // step 1
//            document= new Document();
//            BaseFont bfChinese;
//            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
//            CustomerFontProvider customerFontProvider = new CustomerFontProvider(BaseColor.BLACK, "", "", false, false, 16, 1, bfChinese);
//            // step 2
//            PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
//            document.open();
//            final TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
//            tagProcessorFactory.removeProcessor(HTML.Tag.IMG);
//
//            final CssFilesImpl cssFiles = new CssFilesImpl();
//            cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
//            final StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);
//            final HtmlPipelineContext hpc = new HtmlPipelineContext(new CssAppliersImpl(customerFontProvider));
//            hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
//            final HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(document, writer));
//            final Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
//
//            final XMLWorker worker = new XMLWorker(pipeline, true);
//
//            final Charset charset = StandardCharsets.UTF_8;
//            final XMLParser xmlParser = new XMLParser(true, worker, charset);
//            xmlParser.parse(bais, charset);
//            return file;
//        } catch (Exception e){
//            e.printStackTrace();
//            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
//        } finally {
//            // step 5
//            if(Objects.nonNull(document)) {
//                document.close();
//            }
//        }
//    }
//
//    /**
//     * html 转成pdf 的字节数组
//     * @param html
//     * @return
//     */
//    public static String htmlToPdfBase64Str(String html) {
//        File file = htmlToPdf(html);
//        try (FileInputStream fileInputStream = new FileInputStream(file)){
//            return Base64Utils.encodeToString(IOUtils.toByteArray(fileInputStream));
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
//        } finally {
//            file.deleteOnExit();
//        }
//    }
//
//    /**
//     * html 转成pdf 的字节数组
//     * @param html
//     * @return
//     */
//    public static byte[] htmlToPdfByte(String html) {
//        File file = htmlToPdf(html);
//        try (FileInputStream fileInputStream = new FileInputStream(file)){
//            return IOUtils.toByteArray(fileInputStream);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
//        } finally {
//            file.deleteOnExit();
//        }
//    }
//
//    /**
//     * 合并pdf文件流
//     * @param fileBytes
//     * @return
//     */
//    public static byte[] mergePdfFile(List<byte[]> fileBytes) {
//        if (CollectionUtils.isEmpty(fileBytes)) {
//            return null;
//        }
//
//        byte[] bytes = null;
//        PdfReader pdfReader = null;
//        Document document = null;
//        PdfCopy pdfCopy = null;
//
//        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//            document = new Document();
//            pdfCopy = new PdfCopy(document, baos);
//            document.open();
//            for (byte[] fileByte : fileBytes) {
//                pdfReader = new PdfReader(fileByte);
//                int pageTotal = pdfReader.getNumberOfPages();
//                for (int pageNo=1;pageNo<=pageTotal;pageNo++){
//                    document.newPage();
//                    PdfImportedPage page = pdfCopy.getImportedPage(pdfReader, pageNo);
//                    pdfCopy.addPage(page);
//                }
//                pdfReader.close();
//            }
//            document.close();
//            bytes = baos.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally{
//            if (pdfReader != null) {
//                pdfReader.close();
//            }
//
//            if (pdfCopy != null) {
//                pdfCopy.close();
//            }
//
//            if (document != null) {
//                document.close();
//            }
//        }
//
//        return bytes;
//    }
//
//    static class  CustomerFontProvider implements FontProvider{
//        private BaseColor bc;
//        private String fontname;
//        private String encoding;
//        private boolean embedded;
//        private boolean cached;
//        private float size;
//        private int style;
//        private BaseFont baseFont;
//
//
//        public BaseColor getBc() {
//            return bc;
//        }
//
//        public void setBc(BaseColor bc) {
//            this.bc = bc;
//        }
//
//        public String getFontname() {
//            return fontname;
//        }
//
//        public void setFontname(String fontname) {
//            this.fontname = fontname;
//        }
//
//        public String getEncoding() {
//            return encoding;
//        }
//
//        public void setEncoding(String encoding) {
//            this.encoding = encoding;
//        }
//
//        public boolean isEmbedded() {
//            return embedded;
//        }
//
//        public void setEmbedded(boolean embedded) {
//            this.embedded = embedded;
//        }
//
//        public boolean isCached() {
//            return cached;
//        }
//
//        public void setCached(boolean cached) {
//            this.cached = cached;
//        }
//
//        public float getSize() {
//            return size;
//        }
//
//        public void setSize(float size) {
//            this.size = size;
//        }
//
//        public int getStyle() {
//            return style;
//        }
//
//        public void setStyle(int style) {
//            this.style = style;
//        }
//
//        public BaseFont getBaseFont() {
//            return baseFont;
//        }
//
//        public void setBaseFont(BaseFont baseFont) {
//            this.baseFont = baseFont;
//        }
//
//        public CustomerFontProvider(BaseColor bc, String fontname, String encoding, boolean embedded, boolean cached, float size,
//                              int style, BaseFont baseFont) {
//            super();
//            this.bc = bc;
//            this.fontname = fontname;
//            this.encoding = encoding;
//            this.embedded = embedded;
//            this.cached = cached;
//            this.size = size;
//            this.style = style;
//            this.baseFont = baseFont;
//        }
//
//        public Font getFont(String arg0, String arg1, boolean arg2, float arg3, int arg4, BaseColor arg5) {
//            Font font = null;
//            if (baseFont == null) {
//                font = new Font();
//            } else {
//                font = new Font(baseFont);
//            }
//            font.setColor(arg5);
//            font.setFamily(fontname);
//            font.setSize(size);
//            font.setStyle(arg4);
//            return font;
//        }
//
//        public boolean isRegistered(String arg0) {
//            // TODO Auto-generated method stub
//            return true;
//        }
//    }
//}
