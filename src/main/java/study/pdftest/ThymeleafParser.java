package study.pdftest;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Map;

@Component
public class ThymeleafParser {

    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

    SpringTemplateEngine templateEngine = new SpringTemplateEngine();

    public ThymeleafParser() {
        // 타임리프 리졸버 설정
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        // 스프링 템플릿 엔진을 타임리프 리졸버로 사용하도록 설정
        templateEngine.setTemplateResolver(templateResolver);
    }

    // 타임리프 html에 데이터를 넣어 렌더링 한 후 문자열로 반환한다.
    public String parseHtmlFileToString(String htmlName, Map<String, Object> variableMap) {
        Context context = new Context();
        variableMap.keySet().forEach(key -> context.setVariable(key, variableMap.get(key)));
        return templateEngine.process(htmlName, context);
    }
}
