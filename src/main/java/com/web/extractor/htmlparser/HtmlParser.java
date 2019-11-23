package com.web.extractor.htmlparser;

import com.web.extractor.model.Listing;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public interface HtmlParser {
  List<Listing> getListingFromAddress(String address) throws IOException;
}
