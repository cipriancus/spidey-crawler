package com.web.extractor.configuration;

import com.web.extractor.classification.EbayImageClassification;
import com.web.extractor.htmlparser.ebay.implementations.EbayHtmlParserCom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {
  @Bean
  public EbayHtmlParserCom ebayHtmlParser() {
    return new EbayHtmlParserCom();
  }

  @Bean
  public EbayImageClassification ebayImageClassification(RestTemplate restTemplate) {
    return new EbayImageClassification(restTemplate);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
