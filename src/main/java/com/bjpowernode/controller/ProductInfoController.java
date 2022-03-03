package com.bjpowernode.controller;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductConditionVo;
import com.bjpowernode.service.ProductInfoService;
import com.bjpowernode.util.FileNameUtil;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoController {

    public static final int PAGE_SIZE=5;

    String fileName="";
    @Autowired
    ProductInfoService productInfoService;
    @RequestMapping("/split.action")
    public String list(Model model,HttpSession session){
        PageInfo pageInfo=null;
        if (session.getAttribute("condition")==null){

           pageInfo = productInfoService.listProduct(1, PAGE_SIZE);
        }else {
            pageInfo=productInfoService.listProductInfoByCondition((ProductConditionVo) session.getAttribute("condition"),PAGE_SIZE);
            session.removeAttribute("condition");
        }
        model.addAttribute("pb",pageInfo);
        fileName="";
        return "product";
    }


    @RequestMapping("/ajaxsplit.action")
    @ResponseBody
    public void ajaxList(HttpSession httpSession, ProductConditionVo productConditionVo){
           PageInfo pageInfo = productInfoService.listProductInfoByCondition(productConditionVo, PAGE_SIZE);
            httpSession.setAttribute("pb",pageInfo);
        fileName="";

    }

    @RequestMapping(value = "/deleteajaxsplit.action",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteAjaxList(HttpSession httpSession,HttpServletRequest request){
        PageInfo pageInfo = productInfoService.listProductInfoByCondition((ProductConditionVo) request.getSession().getAttribute("condition"),PAGE_SIZE);
        httpSession.setAttribute("pb",pageInfo);
        fileName="";
        return (String) request.getAttribute("msg");
    }


    @RequestMapping("/ajaxImg.action")
    @ResponseBody
    public String ajaxImg(MultipartFile pimage,HttpSession httpSession ){
         fileName= FileNameUtil.getUUID()+FileNameUtil.getFileSuffix(pimage.getOriginalFilename());
        String path=httpSession.getServletContext().getRealPath("/image_big");
        String finalPath=path+File.separator+fileName;
        try {
            pimage.transferTo(new File(finalPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }



    @RequestMapping("/save.action")
    public String saveProductInfo(ProductInfo productInfo,Model model){
        productInfo.setpImage(fileName);
        productInfo.setpDate(new Date());
        int resurt = productInfoService.insertProduct(productInfo);
        return "forward:/prod/split.action";
    }

    @RequestMapping("/one.action")
    public String showProductInfo(Integer pid,Model model,HttpSession session,ProductConditionVo productConditionVo){
        ProductInfo productInfo = productInfoService.getProductInfo(pid);
        model.addAttribute("prod",productInfo);
        session.setAttribute("condition",productConditionVo);
        return "update";
    }

    @RequestMapping("/cancelupdate.action")
    public String cancelUpdate(){
        return "redirect:/prod/split.action";
    }


    @RequestMapping("/update.action")
    public String saveProductInfo(ProductInfo productInfo){
        if (fileName!=null){
            productInfo.setpImage(fileName);
        }
        int num=-1;
        try {
            num=productInfoService.updateProductInfo(productInfo);
        } catch (Exception e) {
        }
        if (num>0){

        }
        return "redirect:/prod/split.action";
    }

    @RequestMapping("/delete.action")
    public String removeOneProductInfo(Integer pid, HttpServletRequest request,ProductConditionVo productConditionVo){
        int num=-1;
        request.getSession().setAttribute("condition",productConditionVo);
        try {
          num=productInfoService.deleteOneProductInfo(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num>0){
           request.setAttribute("msg","删除成功！");
        }else {
            request.setAttribute("msg","删除失败！");
        }
        return "forward:/prod/deleteajaxsplit.action";
    }


    @RequestMapping("/deleteBatch.action")
    public String batchDeleteProductInfo(String pids,HttpServletRequest request,ProductConditionVo productConditionVo){
        request.getSession().setAttribute("condition",productConditionVo);
        String[] ids = pids.split(",");
        int num=-1;
        try {
            num=productInfoService.batchDeleteProductInfo(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num>0){
            request.setAttribute("msg","批量删除成功！");
        }else {
            request.setAttribute("msg","批量删除失败！");
        }
        return "forward:/prod/deleteajaxsplit.action";
    }




}
