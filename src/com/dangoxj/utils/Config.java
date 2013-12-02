package com.dangoxj.utils;

import org.apache.commons.configuration.*;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.*;
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

        try {
            testSqlLite();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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


//    How to Specify Database Files
//    Here is an example to select a file C:\work\mydatabase.db (in Windows)
//    Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/work/mydatabase.db");
//    A UNIX (Linux, Mac OS X, etc) file /home/leo/work/mydatabase.db
//    Connection connection = DriverManager.getConnection("jdbc:sqlite:/home/leo/work/mydatabase.db");
//    How to Use Memory Databases
//    SQLite supports on-memory database management, which does not create any database files. To use a memory database in your Java code, get the database connection as follows:
//    Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
    public static void testSqlLite() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:Results.db");

            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //statement.execute("begin;");

            statement.executeUpdate("drop table if exists Result");
            //create table Result(Id integer PRIMARY KEY, Keyword string, Ie string, Body text, Reson int)

            statement.executeUpdate("create table Result (id integer PRIMARY KEY, name string, time timestamp)");
            long begin = System.currentTimeMillis();
            //for (int i=0; i<100000; i++) {
                statement.executeUpdate("insert into Result(name,time) values('leo','2013-01-02 11:11:11')");
                
            //}

            System.err.println((System.currentTimeMillis() - begin) / 1000);
            //statement.executeUpdate("insert into person values(2, 'yui')");

            connection.commit();
            //statement.execute("commit;");


            ResultSet rs = statement.executeQuery("select * from Result");
            while(rs.next())
            {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
                System.out.println("time = " + rs.getString("time"));
            }
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
}
