package com.web.extractor.htmlparser.ebay;

import com.web.extractor.htmlparser.HtmlParser;
import com.web.extractor.model.Listing;
import com.web.extractor.scheduler.EbayScheduler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstratEbayHtmlParser implements HtmlParser {

  public List<Listing> getListingFromAddress(String address) throws IOException {
    Document document = Jsoup.connect(address).get();

    List<Node> childNodes = getUlChildNodes(document);

    List<Listing> allParsedListings = new ArrayList<>();

    for (Node childNode : childNodes) {
      if (isLiElement((Element) childNode)) {
        allParsedListings.add(populateListingFromNode(childNode));
      }
    }

    setNextPageUrl(document);

    return allParsedListings;
  }

  protected abstract void setNextPageUrl(Document document);

  protected Listing populateListingFromNode(Node node) {
    Listing listing = new Listing();
    String url = getUrlFromNode(node);
    String title = getTitleFromNode(node);
    listing.setListingUrl(url);
    listing.setTitle(title);
    return listing;
  }

  protected boolean isLiElement(Element node) {
    return node.tag().getName().equals("li");
  }

  protected abstract List<Node> getUlChildNodes(Document document);

  protected abstract String getUrlFromNode(Node node);

  protected abstract String getTitleFromNode(Node node);
}
