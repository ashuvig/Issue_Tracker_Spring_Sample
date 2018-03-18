package au.com.domain.demo;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.util.UrlPathHelper;

@SpringBootApplication
@ComponentScan({"au.com.domain.demo.*"})
@Configuration
public class IssueTrackerApplication extends WebMvcConfigurerAdapter {
	private static final Logger log = Logger.getLogger("IssueTrackerApplication");
    public static void main(final String[] args) {
    	log.info("-=============> Starting App - IssueTrackerApplication <======== ");
        SpringApplication.run(IssueTrackerApplication.class, args);
    }
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }
}
    

