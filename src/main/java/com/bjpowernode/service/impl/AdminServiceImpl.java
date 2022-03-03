package com.bjpowernode.service.impl;

import com.bjpowernode.dao.AdminMapper;
import com.bjpowernode.pojo.Admin;
import com.bjpowernode.pojo.AdminExample;
import com.bjpowernode.service.AdminService;
import com.bjpowernode.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;



    @Override
    public Admin login(String username, String password) {
        /*String mdPassword = MD5Util.getMD5(password);  //将明文密码转化为密文密码*/
        AdminExample adminExample=new AdminExample();
        adminExample.createCriteria().andANameEqualTo(username).andAPassEqualTo(password);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if (admins.size()>0){
            Admin admin = admins.get(0);
            return admin;
        }
        return null;
    }
}
