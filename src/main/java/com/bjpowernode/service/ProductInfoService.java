package com.bjpowernode.service;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductConditionVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductInfoService {
   PageInfo listProduct(Integer pageNum, Integer pageSize);  //分頁

    int insertProduct(ProductInfo productInfo);

    ProductInfo getProductInfo(Integer pid);

    int updateProductInfo(ProductInfo productInfo);

    int deleteOneProductInfo(Integer pid);

    int batchDeleteProductInfo(String []pids);

    PageInfo<ProductInfo> listProductInfoByCondition(ProductConditionVo productConditionVo,int pageSize);
}
