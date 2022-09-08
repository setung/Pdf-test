package study.pdftest.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.pdftest.openpdf.OpenPdfGenerator;
import study.pdftest.pdfbox.PdfBoxGenerator;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PdfController {

    private final OpenPdfGenerator openPdf;

    private final PdfBoxGenerator pdfBox;

    @GetMapping("/openPdf")
    public ResponseEntity<byte[]> getPdfByOpenPdf(@RequestBody RequestDto requestDto) {
        long start = System.currentTimeMillis();
        byte[] bytes = openPdf.generatePdfFromHtml(requestDto.htmlName, requestDto.getMap());
        long end = System.currentTimeMillis();
        System.out.println((end -start)/1000);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + requestDto.htmlName + "\"")
                .body(bytes);

    }

    @GetMapping("/pdfBox")
    public ResponseEntity<byte[]> getPdfByPdfBox(@RequestBody RequestDto requestDto) {
        long start = System.currentTimeMillis();
        byte[] bytes = pdfBox.generatePdfFromHtml(requestDto.htmlName, requestDto.getMap());
        long end = System.currentTimeMillis();
        System.out.println((end -start)/1000);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + requestDto.htmlName + "\"")
                .body(bytes);
    }

    @Data
    static class RequestDto {
        private String htmlName;
        private Map<String,Object> map;
    }
}
