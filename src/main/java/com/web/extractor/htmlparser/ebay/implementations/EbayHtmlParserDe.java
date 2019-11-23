package com.web.extractor.htmlparser.ebay.implementations;

import com.web.extractor.htmlparser.ebay.AbstratEbayHtmlParser;
import com.web.extractor.scheduler.EbayScheduler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import java.util.ArrayList;
import java.util.List;

public class EbayHtmlParserDe extends AbstratEbayHtmlParser {

  protected String getUrlFromNode(Node node) {
    return node.childNode(1).childNode(1).childNode(1).attr("href");
  }

  protected String getTitleFromNode(Node node) {
    return node.childNode(3).childNode(0).childNode(0).toString();
  }

  protected List<Node> getUlChildNodes(Document document) {
    List<Node> list = new ArrayList<>();
    list.addAll(
        document.getElementById("mainContent").child(3).child(0).child(1).child(1).childNodes());
    list.remove(0);
    list.remove(list.size() - 1);
    list.remove(list.size() - 1);

    return list;
  }

  protected boolean isCurrentPageElement(Node node) {
    return false;
  }

  protected boolean isPageListContainerElement(Node node) {
    return node.attr("id").contains("srp-river-results-SEARCH_PAGINATION_MODEL_V2");
  }

  protected void setNextPageUrl(Document document) {

    String url =
        document
            .getElementById("pgCtrlTbl")
            .getElementsByClass("pagn-next")
            .get(0)
            .childNode(0)
            .attr("href");

    if (!url.isEmpty() && url.contains("https://")) {
      EbayScheduler.setNextScheduledUrl(url);
    } else {
      EbayScheduler.resetScheduledUrl();
    }
  }
}
