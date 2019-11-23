package com.web.extractor.htmlparser.ebay;

import com.web.extractor.htmlparser.HtmlParser;
import com.web.extractor.htmlparser.ebay.implementations.EbayHtmlParserCom;
import com.web.extractor.htmlparser.ebay.implementations.EbayHtmlParserDe;

public class EbayFactory {
  public static HtmlParser create(String address) {
    if (address.contains("ebay.com")) {
      return new EbayHtmlParserCom();
    } else if (address.contains("ebay.de")) {
      return new EbayHtmlParserDe();
    } else {
      throw new RuntimeException("No implementation for address " + address);
    }
  }
}
