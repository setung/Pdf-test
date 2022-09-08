package study.pdftest.pdfbox;

import com.openhtmltopdf.pdfboxout.PDFCreationListener;
import com.openhtmltopdf.pdfboxout.PdfBoxRenderer;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.stereotype.Component;
import study.pdftest.ThymeleafParser;

import java.io.*;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PdfBoxGenerator {

    private final ThymeleafParser htmlParser;

    public byte[] generatePdfFromHtml(String htmlName, Map<String, Object> variableMap) {
        String html = htmlParser.parseHtmlFileToString(htmlName, variableMap);
        return generateFromHtml(htmlName + "_pdfbox.pdf", html);
    }

    private byte[] generateFromHtml(String fileName, String html) {
        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(fileName);
            builder.toStream(outputStream);
            builder.useFont(new File(getClass().getClassLoader().getResource("static/font/NanumGothic.ttf").getFile()), "NanumGothic");
            builder.withHtmlContent(html, null);
            builder.run();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.writeTo(outputStream);
            return baos.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
