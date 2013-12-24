package com.dangoxj.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshouzhi on 13-12-24.
 */

class KeyWord {
    public String name;
    public boolean isFunction = true;
    public String retVal = "";
    public List<String> params = new ArrayList<String>();
}

/**
 *
 *<KeyWord name="bsearch" func="yes">
 *<Overload retVal="void *" >
 *<Param name="const void *key" />
 *<Param name="const void *base" />
 *<Param name="size_t nmemb" />
 *<Param name="size_t size" />
 *<Param name="int (*compar)(const void *, const void *)" />
 *</Overload>
 */
public class Notepad {

    private XMLConfiguration config ;

    public Notepad(){
        initialize();
    }

    private int keywordIndex = 0;

    private void initialize() {
        try {
            config = new XMLConfiguration("test.xml");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        config.clear();
        config.setEncoding("Windows-1252");
        config.setRootElementName("NotepadPlus");
        //<Environment ignoreCase="no" startFunc="(" stopFunc=")" paramSeparator="," terminal=";" additionalWordChar=""/>
        config.addProperty("Environment[@ignoreCase]","no");
        config.addProperty("Environment[@startFunc]","{");
        config.addProperty("Environment[@stopFunc]","}");
        config.addProperty("Environment[@paramSeparator]"," ");
        config.addProperty("Environment[@terminal]","");
        config.addProperty("Environment[@additionalWordChar]","");


        // AutoComplete language="C++">
        config.addProperty("AutoComplete[@language]","spider");

    }

    private void save(){
        try {
            config.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ConfigurationException {
        Notepad notepad = new Notepad();
        KeyWord keyWord = new KeyWord();
        keyWord.name = "MML_USN_INTER";
        keyWord.isFunction = false;
        notepad.addKeyword(keyWord);
        keyWord = new KeyWord();
        keyWord.name = "MML_USN_LOCAL";
        keyWord.isFunction = false;
        notepad.addKeyword(keyWord);

        keyWord = new KeyWord();
        keyWord.name = "MML_USN_Z_ADD_CHGIMSICFG";
        keyWord.params.add("IMSIPRE");
        keyWord.params.add("ADDD");
        notepad.addKeyword(keyWord);

        notepad.save();

    }

    private void addKeyword(KeyWord keyWord){
        config.addProperty("AutoComplete.KeyWord("+keywordIndex+")[@name]",keyWord.name);
        if (keyWord.isFunction){
            config.addProperty("AutoComplete.KeyWord("+keywordIndex+")[@func]","yes");
            config.addProperty("AutoComplete.KeyWord("+keywordIndex+").Overload[@retVal]","int");
            for (int i=0; i<keyWord.params.size(); i++){
                config.addProperty("AutoComplete.KeyWord("+keywordIndex+").Overload.Param("+i+")[@name]",keyWord.params.get(i));
            }
        }

        keywordIndex++;
    }
}
