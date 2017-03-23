package info.zhwan.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.*;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@SpringBootApplication
public class ServletDemoApp {
  public static void main(String[] args) {
    SpringApplication.run(ServletDemoApp.class, args);
  }

//  @Bean
//  ServletRegistrationBean servletRegistrationBean() {
//    return new ServletRegistrationBean();
//  }

  @Bean
  FilterRegistrationBean filterRegistrationBean() {
    CustomFilter customFilter = new CustomFilter();
    return new FilterRegistrationBean(customFilter);
  }
}


@Component
class CustomServletContainer implements EmbeddedServletContainerCustomizer {

  @Override
  public void customize(ConfigurableEmbeddedServletContainer container) {
    if (TomcatEmbeddedServletContainerFactory.class.isAssignableFrom(container.getClass())) {
      TomcatEmbeddedServletContainerFactory tc =
        TomcatEmbeddedServletContainerFactory.class.cast(container);

    }
  }
}

//@Component
@Slf4j
class CustomFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("init {}", filterConfig);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    log.info("doFilter {} {} {}", request, response, chain);
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    log.info("destroy");
  }
}

@RestController
class GreetingRestController {
  @GetMapping("/greeting")
  Map<String, String> greeting() {
    return Collections.singletonMap("greeting", "hello zhwan~");
  }
}
