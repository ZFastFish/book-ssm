package com.bjpowernode.util;

import java.util.UUID;

public  class FileNameUtil {

    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-","");
    }

    public static String getFileSuffix(String fileName){
        int i = fileName.indexOf(".");
        String fileSuffixName = fileName.substring(i);
        return fileSuffixName;
    }
}
