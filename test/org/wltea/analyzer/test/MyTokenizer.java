package org.wltea.analyzer.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKTokenizer;

import net.sf.classifier4J.ITokenizer;


public class MyTokenizer implements ITokenizer {

    private List<String> list;
    private String[] strArray;
    private static Collection<String> exwordc = new ArrayList<String>();
    private static Collection<String> stopwordc = new ArrayList<String>();
    private static String exdict = "F:/eclipse_workspace/IK-analyzer/ik-analyzer/exdict.dic";

    // 加载新增词库
    static {

        try {
            /*File file = new File(exdict);
            FileInputStream fin = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String line = "";
            while ((line = reader.readLine()) != null) {
                exwordc.add(line.trim());
            }
            reader.close();*/
        	exwordc.add("龙光");
        	exwordc.add("徐孺");
        	exwordc.add("陈蕃");
        	
        	stopwordc.add("三江");
        	stopwordc.add("五湖");
        	stopwordc.add("雄州");
        	
            System.out.println("加载词典::" + exdict);
            // 增加词库
            Configuration cfg = DefaultConfig.getInstance();
            Dictionary dict = Dictionary.initial(cfg);
            dict.addWords(exwordc);
            dict.addStopWords(stopwordc);
        } catch (Exception e) {
           System.out.println(e + "------------------加载词典出错，请确认词典文件！------------------");
        }
    }

    /**
     * 分词，返回分词数组
     * 
     * @param input
     *            文本字符串
     * @return String[]
     */
    public String[] tokenize(String input) {
        list = new ArrayList<String>();

        IKTokenizer tokenizer = new IKTokenizer(new StringReader(input), true);
        try {
            while (tokenizer.incrementToken()) {
                TermAttribute termAtt = (TermAttribute) tokenizer.getAttribute(TermAttribute.class);
                String str = termAtt.term();
                list.add(str);
            }
        } catch (IOException e) {
            System.out.println(e + "------------------分词出错------------------");
        }
        strArray = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            strArray[i] = (String) list.get(i);
        }

        return strArray;
    }

}
