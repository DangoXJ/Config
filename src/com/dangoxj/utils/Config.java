package com.dangoxj.utils;

import org.apache.commons.configuration.*;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by zhangshouzhi on 13-12-1.
 */
public class Config {
    public static void main(String[] args) throws ConfigurationException, IOException {
        XMLConfiguration config =new XMLConfiguration("test.xml");
        System.out.println(config.getString("Lmt[@name]"));
        System.out.println(config.getString("lmt.test"));
        System.out.println(config.getString("lmt.path","default"));
        System.out.println(config.getString("Person(1)[@Name]"));

        // 设置为XPath 解析
        config.setExpressionEngine(new XPathExpressionEngine());
        System.out.println(config.getString("Person[@Name = 'ZSZ0']/P"));
        System.out.println(config.getString("Person[N = 'ZSZ0']/P"));
        System.out.println(config.getString("databases/database[name = 'dev']/url"));
        System.out.println(config.getString("databases/database[name = 'production']/url"));

        config.save();
    }

    public static XMLConfiguration getXMLConfig(String filename) throws IOException, ConfigurationException {
        File config = new File(filename);
        // 如果存在，备份一下
        if (config.exists()){
            FileUtils.moveFile(config, new File(filename+".bak"));
        }
        List<String> lines = new ArrayList<String>();
        lines.add("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
        lines.add("<Errors/>");
        FileUtils.writeLines(config,lines);
        XMLConfiguration ret = new XMLConfiguration(filename);
        ret.setAutoSave(false);
        return ret;
    }
}
