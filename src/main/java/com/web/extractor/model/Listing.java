package com.web.extractor.model;

import lombok.Data;

@Data
public class Listing {
  // we only need the URL, python will pull the rest
  private String listingUrl;
  private String title;
}
