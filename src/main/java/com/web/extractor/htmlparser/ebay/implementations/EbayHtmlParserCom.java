package com.web.extractor.htmlparser.ebay.implementations;

import com.web.extractor.htmlparser.ebay.AbstratEbayHtmlParser;
import com.web.extractor.scheduler.EbayScheduler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

import java.util.List;

public class EbayHtmlParserCom extends AbstratEbayHtmlParser {

  protected List<Node> getUlChildNodes(Document document) {
    return document.getElementById("mainContent").child(1).child(0).child(1).child(0).childNodes();
  }

  protected String getUrlFromNode(Node node) {
    return node.childNode(0).childNode(1).childNode(1).attr("href");
  }

  protected String getTitleFromNode(Node node) {
    return node.childNode(0).childNode(1).childNode(1).childNode(1).childNode(0).toString();
  }

  protected void setNextPageUrl(Document document) {

    String url =
        document
            .getElementById("srp-river-results-SEARCH_PAGINATION_MODEL_V2")
            .getElementsByClass("x-pagination__control")
            .get(1)
            .attr("href");

    if (!url.isEmpty() && url.contains("https://")) {
      EbayScheduler.setNextScheduledUrl(url);
    } else {
      EbayScheduler.resetScheduledUrl();
    }
  }
}
