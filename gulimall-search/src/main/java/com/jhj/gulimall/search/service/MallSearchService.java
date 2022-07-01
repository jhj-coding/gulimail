package com.jhj.gulimall.search.service;

import com.jhj.gulimall.search.vo.SearchParam;
import com.jhj.gulimall.search.vo.SearchResult;

public interface MallSearchService {
    SearchResult search(SearchParam searchParam);
}
