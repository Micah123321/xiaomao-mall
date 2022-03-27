package com.xiaomao6.search.service;

import com.xiaomao6.search.vo.SearchParam;
import com.xiaomao6.search.vo.SearchResult;

public interface SearchService {

    SearchResult search(SearchParam param);
}
