package info.zhwan.spring.genericdi;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses=Logger.class)
public class AppContext {
}
