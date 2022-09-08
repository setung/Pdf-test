package study.pdftest.openpdf;

import com.lowagie.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;
import study.pdftest.ThymeleafParser;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OpenPdfGenerator {

    private final ThymeleafParser htmlParser;

    public byte[] generatePdfFromHtml(String htmlName, Map<String, Object> variableMap) {
        String html = htmlParser.parseHtmlFileToString(htmlName, variableMap);
       return generateFromHtml(htmlName + "_openPdf.pdf", html);
    }

    private byte[] generateFromHtml(String fileName, String html) {
        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver().addFont(
                    new ClassPathResource("static/font/NanumGothic.ttf").getURI().toString(),
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED
            );
            renderer.getSharedContext().setReplacedElementFactory(
                    new ImageReplacedElementFactory(renderer.getSharedContext().getReplacedElementFactory()));

            String baseUrl = FileSystems.getDefault()
                    .getPath("src\\main\\resources")
                    .toUri()
                    .toURL()
                    .toString();

            renderer.setDocumentFromString(html,baseUrl);
            renderer.layout();
            renderer.createPDF(outputStream);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.writeTo(outputStream);
            return baos.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
