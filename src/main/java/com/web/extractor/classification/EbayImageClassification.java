package com.web.extractor.classification;

import com.web.extractor.model.Listing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.List;

public class EbayImageClassification {

  private static final Logger log = LoggerFactory.getLogger(EbayImageClassification.class);

  @Value("${site.ebay.classification-uri}")
  private String predictionUri;

  private RestTemplate restTemplate;

  @Autowired
  public EbayImageClassification(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<Listing> getWorthyListings(List<Listing> pulledListings) {
    try {
      HttpEntity<Object> requestEntity = createRequestEntity(pulledListings);

      ResponseEntity<List<Listing>> rateResponse =
          restTemplate.exchange(
              predictionUri,
              HttpMethod.POST,
              requestEntity,
              new ParameterizedTypeReference<List<Listing>>() {});

      if (rateResponse.getStatusCode() == HttpStatus.OK) {
        return rateResponse.getBody();
      }
    } catch (RestClientException e) {
      log.error("There has been an error while waiting for classification request ", e);
    }
    return Collections.emptyList();
  }

  private HttpEntity<Object> createRequestEntity(List<Listing> pulledListings) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<>(pulledListings, headers);
  }
}
