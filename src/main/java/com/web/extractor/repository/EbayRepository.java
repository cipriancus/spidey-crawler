package com.web.extractor.repository;

import com.web.extractor.model.Listing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.util.List;

@Repository
public class EbayRepository {

  private static final Logger log = LoggerFactory.getLogger(EbayRepository.class);

  /**
   * TODO: investigate the posibility of a database There is a case where listings are "finished"
   * and we have to pull the status of a listing and delete it.
   *
   * @param worthyListings
   */
  public void saveWorthyListings(List<Listing> worthyListings) {
    File file = new File("WorthyListings.txt");
    try (FileWriter fr = new FileWriter(file, true)) {

      for (Listing listing : worthyListings) {
        fr.write(listing.getListingUrl() + "\n");
      }

    } catch (IOException e) {
      log.error("There has been an error while saving listings to file  ", e);
    }
  }
}
