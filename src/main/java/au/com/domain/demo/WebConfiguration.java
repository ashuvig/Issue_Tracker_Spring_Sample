package au.com.domain.demo;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {


public void configureMessageConverters(List converters) {
    
    final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    final ObjectMapper objectMapper = new ObjectMapper();

    //configure Joda serialization
    //objectMapper.registerModule(new JodaModule());
    //objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    // Other options such as how to deal with nulls or identing...
    //objectMapper.setSerializationInclusion(clude.NON_NULL);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    converter.setObjectMapper(objectMapper);

    StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
    /*
    StringHttpMessageConverter must appear first in the list so that Spring has a chance to use
     it for Spring RestController methods that return simple String. Otherwise, it will use
      MappingJackson2HttpMessageConverter and clutter the response with escaped quotes and such
     */
    converters.add(stringHttpMessageConverter);
    converters.add(converter);
    super.configureMessageConverters(converters);
}
}