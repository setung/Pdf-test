package study.pdftest.pdfbox;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.pdftest.data.Person;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class PdfBoxGeneratorTest {

    @Autowired
    PdfBoxGenerator generator;

    @Test
    void generatePengsu() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "펭수");
        map.put("age", "521412");
        map.put("job", "무직");
        map.put("address", "북극 어딘가");
        generator.generatePdfFromHtml("pengsu.html", map);
    }

    @Test
    void generateTable() {
        Map<String, Object> map = new HashMap<>();
        map.put("users", Person.getDump());
        generator.generatePdfFromHtml("table.html", map);
    }
}