package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryService extends BaseService {

    @Autowired
    private CategoryRepository repository;

}
