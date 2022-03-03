package com.bjpowernode.service.impl;

import com.bjpowernode.dao.ProductTypeMapper;
import com.bjpowernode.pojo.ProductType;
import com.bjpowernode.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeMapper productTypeMapper;
    @Override
    public List<ProductType> listProductType() {
        List<ProductType> productTypes = productTypeMapper.selectByExample(null);
        return productTypes;
    }
}
