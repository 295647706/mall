package com.api.controller.search;

import com.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 搜索模块
 */
@RequestMapping("/search")
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 搜索品牌
     * @return
     */
    @GetMapping(value = "/brand/pages")
    public Object brandSearch(String brandId, Integer page, Integer size){
        Map<String, Object> result = searchService.brandSearch(brandId, page, size);
        return result;
    }

    /**
     * 关键字搜索
     * @return
     */
    @RequestMapping(value = "/" +
            "/pages", method = RequestMethod.GET)
    public Object keySearch(String keyword, Integer page, Integer size){
        Map<String, Object> result = searchService.keySearch(keyword, page, size);
        return result;
    }

}
