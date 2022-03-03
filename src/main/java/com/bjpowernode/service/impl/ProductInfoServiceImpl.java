package com.bjpowernode.service.impl;

import com.bjpowernode.dao.ProductInfoMapper;
import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.ProductInfoExample;
import com.bjpowernode.pojo.vo.ProductConditionVo;
import com.bjpowernode.service.ProductInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public PageInfo listProduct(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        ProductInfoExample productInfoExample = new ProductInfoExample();
        productInfoExample.setOrderByClause("p_id desc");
        List<ProductInfo> productInfos = productInfoMapper.selectByExample(productInfoExample);
        PageInfo pageInfo=new PageInfo(productInfos,3);
        return pageInfo;
    }

    @Override
    public int insertProduct(ProductInfo productInfo) {

        return productInfoMapper.insertSelective(productInfo);
    }

    @Override
    public ProductInfo getProductInfo(Integer pid) {
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(pid);
        return productInfo;
    }

    @Override
    public int updateProductInfo(ProductInfo productInfo) {
        return productInfoMapper.updateByPrimaryKey(productInfo);
    }

    @Override
    public int deleteOneProductInfo(Integer pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int batchDeleteProductInfo(String[] pids) {

        return productInfoMapper.batchDelete(pids);
    }

    @Override
    public PageInfo<ProductInfo> listProductInfoByCondition(ProductConditionVo productConditionVo,int pageSize) {
        PageHelper.startPage(productConditionVo.getPage(),pageSize);
        List<ProductInfo> productInfosByCondition = productInfoMapper.listProductByCondition(productConditionVo);
        PageInfo pageInfo=new PageInfo(productInfosByCondition,3);
        return pageInfo;
    }
}
