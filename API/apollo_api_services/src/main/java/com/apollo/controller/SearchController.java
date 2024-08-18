package com.apollo.controller;


import com.apollo.dto.VariantDTO;
import com.apollo.payload.response.SearchResponse;
import com.apollo.service.VariantService;
import com.apollo.service.impl.VariantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/search")
public class SearchController {
    private final VariantService variantService;

    @Autowired
    public SearchController(VariantServiceImpl variantService){
        this.variantService=variantService;
    }
    @GetMapping("")
    public ResponseEntity<SearchResponse> getVariantsByText(
            @RequestParam("searchText") String searchText,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        Pageable pageable= PageRequest.of(page,pageSize);
        SearchResponse searchResponse =new SearchResponse();
        Page<VariantDTO> variantDtoPage = variantService.getVariantsByContaining(searchText,pageable);
        searchResponse.setVariantDtoPage(variantDtoPage);
        return new ResponseEntity<>(searchResponse, HttpStatus.OK);


    }
    @GetMapping("/newest-variants")
    public ResponseEntity<SearchResponse> getNewestVariantsByText(
            @RequestParam("searchText") String searchText,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("createdDate")));
        SearchResponse searchResponse = new SearchResponse();
        Page<VariantDTO> variantDtoPage = variantService.getNewestVariantsByText(searchText, pageable);
        searchResponse.setVariantDtoPage(variantDtoPage);
        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
    }
    @GetMapping("/decrease-price")
    public ResponseEntity<SearchResponse> getVariantsByTextAndSortByDecreasePrice(
            @RequestParam("searchText") String searchText,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("price")));
        Page<VariantDTO> variantDtoPage = variantService.getVariantsByContaining(searchText,pageable);
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setVariantDtoPage(variantDtoPage);
        return new ResponseEntity<>(searchResponse,HttpStatus.OK);
    }
    @GetMapping("/increase-price")
    public ResponseEntity<SearchResponse> getVariantsByTextAndSortByIncreasePrice(
            @RequestParam("searchText") String searchText,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.asc("price")));
        Page<VariantDTO> variantDtoPage = variantService.getVariantsByContaining(searchText, pageable);
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setVariantDtoPage(variantDtoPage);
        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
    }
    @GetMapping("/price-range")
    public ResponseEntity<SearchResponse> searchByPriceRange(
            @RequestParam("searchText") String searchText,
            @RequestParam("minPrice") double minPrice,
            @RequestParam("maxPrice") double maxPrice,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        SearchResponse searchResponse = new SearchResponse();
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.asc("price")));
        Page<VariantDTO> variantDtoPage = variantService.getVariantsByNameContainingAndPriceBetween(searchText, minPrice,maxPrice,pageable);
        searchResponse.setVariantDtoPage(variantDtoPage);
        return new ResponseEntity<>(searchResponse,HttpStatus.OK);
    }
    @GetMapping("/rating")
    public ResponseEntity<SearchResponse> searchByRating(
            @RequestParam("searchText") String searchText,
            @RequestParam("star") long star,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        SearchResponse searchResponse = new SearchResponse();
        Pageable pageable = PageRequest.of(page, pageSize);
        searchResponse.setVariantDtoPage(variantService.getVariantsBySearchTextAndRating(searchText,star,pageable));
        return new ResponseEntity<>(searchResponse,HttpStatus.OK);
    }


}
