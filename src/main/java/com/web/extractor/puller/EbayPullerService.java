package com.web.extractor.puller;

import com.web.extractor.aspects.annotation.LogExecutionTime;
import com.web.extractor.aspects.annotation.LogPullingAddressInfoAspect;
import com.web.extractor.aspects.annotation.LogPullingBackoffAspect;
import com.web.extractor.classification.EbayImageClassification;
import com.web.extractor.htmlparser.HtmlParser;
import com.web.extractor.htmlparser.ebay.EbayFactory;
import com.web.extractor.model.Listing;
import com.web.extractor.repository.EbayRepository;
import com.web.extractor.scheduler.EbayScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
@EnableScheduling
public class EbayPullerService {

  private static final Logger log = LoggerFactory.getLogger(EbayPullerService.class);

  @Value("${site.ebay.url}")
  private String initialAddress;

  private HtmlParser htmlParser;

  private EbayRepository ebayRepository;

  private EbayImageClassification ebayImageClassification;

  @Autowired
  public EbayPullerService(
      EbayRepository ebayRepository, EbayImageClassification ebayImageClassification) {
    this.ebayRepository = ebayRepository;
    this.ebayImageClassification = ebayImageClassification;
  }

  @PostConstruct
  private void setInitialAddress() {
    EbayScheduler.initAddresses(initialAddress);
    this.htmlParser = EbayFactory.create(initialAddress);
  }

  /**
   * start every minute but we have to put at 5 min for the python to have time to process and
   * download data
   */
  @LogExecutionTime
  @LogPullingAddressInfoAspect
  @LogPullingBackoffAspect
  @Scheduled(cron = "0 0/2 * * * ?")
  public void pullEbayListing() {

    if (!EbayScheduler.scheduleCrawling()) { // it is not scheduled yet
      return;
    }

    String address = getScheduledUrl();

    try {
      List<Listing> listings = htmlParser.getListingFromAddress(address);

      listings = ebayImageClassification.getWorthyListings(listings);

      ebayRepository.saveWorthyListings(listings);
    } catch (IOException e) {
      log.error("There has been an error while pulling ", e);
      backoffCrawling();
    }
  }

  private String getScheduledUrl() {
    return EbayScheduler.getScheduledUrl();
  }

  public void backoffCrawling() {
    EbayScheduler.backoffCrawling();
  }
}
