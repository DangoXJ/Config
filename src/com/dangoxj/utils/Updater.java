package com.dangoxj.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.lang.model.element.Element;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by zhangshouzhi on 13-12-29.
 */
public class Updater {

    private String remotePath;

    private Hashtable<String,File> remoteFileList = new Hashtable<String,File>();
    private Hashtable<String,File> localFileList = new Hashtable<String,File>();

    private static Log logger = LogFactory.getLog(Updater.class);
    private XMLConfiguration config;

    public Updater(){
        this.initialize();
    }

    private boolean  preCheck(){
        if (null == this.remotePath ){
            logger.error("UpdateConfig.xml deos not exsit!");
            return false;
        }
        File dir = new File(this.remotePath);

        if (!dir.exists()){
            logger.error("RemotePath "+ this.remotePath +" Does Not Exsit! ");
            return false;
        }

        return true;
    }

    private void initialize() {
        try {
            File configFile = new File("UpdateConfig.xml");
            config = new XMLConfiguration("UpdateConfig.xml");
            remotePath = config.getString("remotePath");

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void getRemoteFileList(){
        File dir = new File(this.remotePath);

        List<File> list = new ArrayList<File>();

        list.addAll(FileUtils.listFiles(dir, null, true));

        for (File next: list){
            if (!remoteFileList.contains(next)) {
                System.out.println(next);
                remoteFileList.put(next.getAbsolutePath().replace(this.remotePath,""),next);
            }
        }

        //printFileList(remoteFileList);
    }

    private void getLocalFileList(){
        File dir = new File(System.getProperty("user.dir"));
        List<File> list = new ArrayList<File>();

        list.addAll(FileUtils.listFiles(dir, null, true));

        for (File next: list){
            if (!localFileList.contains(next)) {
                localFileList.put(next.getAbsolutePath().replace(dir.getAbsolutePath(),""),next);
            }
        }

        //printFileList(localFileList);
    }

    private void updateFiles(){
       // for (File next: remoteFileList){

        //}
        int updateNum = 0;
        int totalNum = remoteFileList.size();
        int doneNum = 0;
        Enumeration<String> keys = remoteFileList.keys();
        String key;
        File localFile;
        File remoteFile;

        boolean needUpdate = false;

        while(keys.hasMoreElements()){
            needUpdate = false;
            key = keys.nextElement();
            localFile = localFileList.get(key);
            remoteFile = remoteFileList.get(key);

            if ( null == remoteFile ){
                // 几乎不可能
                continue;
            } else if ( null == localFile ){
                localFile = new File(System.getProperty("user.dir")+key);
                needUpdate = true;
            } else if ( FileUtils.isFileNewer(remoteFile,localFile)){
                needUpdate = true;
            }

            if (needUpdate ) {
                updateNum++;
                try {
                    logger.info("Updating from "+ remoteFile.getAbsolutePath());
                    FileUtils.copyFile(remoteFile, localFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            doneNum++;

            //updateNum++;

        }

        logger.info("Update "+ updateNum +" Files.");
    }

    public void update(){
        if (this.preCheck()){
            this.getLocalFileList();
            this.getRemoteFileList();
            this.updateFiles();
        }

    }

    @Deprecated
    private void printFileList(Hashtable<String,File> fileList){
        if (null == fileList){
            return;
        }
        Enumeration<String> keys = fileList.keys();
        String key;
        while(keys.hasMoreElements()){
            key = keys.nextElement();
            logger.debug(key + "\t"+fileList.get(key));
        }
    }

    public static void main(String[] args){
        PropertyConfigurator.configure("log4j.properties");

        Updater updater = new Updater();
        updater.update();
    }
}
