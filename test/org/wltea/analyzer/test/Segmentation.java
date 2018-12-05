package org.wltea.analyzer.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.classifier4J.ITokenizer;
import sun.misc.Compare;

public class Segmentation {
	public static Map<String, Integer> map = null;
    public static void main(String[] args) throws IOException {

        String path = "F:/eclipse_workspace/IK-analyzer/ik-analyzer/bb.txt";
        File file = new File(path);
        FileInputStream fin = new FileInputStream(file);
        String input = getString(fin);

        System.out.println("开始分词::" + path);
        ITokenizer tokenizer = new MyTokenizer();
        String[] words = tokenizer.tokenize(input);
        map= new HashMap<String, Integer>(); 
        for (String word : words) {
        	if(map.containsKey(word)){
        		int count = map.get(word);
        		count++;
        		map.put(word, count);
        	}else{
        		map.put(word, 1);
        	}
        }
        
        Set<String> set = map.keySet();
        List<String> list = new ArrayList<String>(set);
        Collections.sort(list, new Comparator<String>() {
        	/*
             * int compare(Person p1, Person p2) 返回一个基本类型的整型，
             * 返回负数表示：p1 小于p2，
             * 返回0 表示：p1和p2相等，
             * 返回正数表示：p1大于p2
             */
            
			@Override
			public int compare(String o1, String o2) {
				return map.get(o2)-map.get(o1);
			}
		});
        for(String item: list){
        	System.out.println(item);
        }
        
    }

    /**
     * 从 inputStream 读取文本并转为一个字符串。
     * 
     * @param is
     *            inputStream 输入流
     * @return String 文本字符串
     * @throws IOException
     */
    public static String getString(InputStream is) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = "";
        StringBuffer stringBuffer = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            stringBuffer.append(line);
            stringBuffer.append(" ");
        }

        reader.close();

        return stringBuffer.toString().trim();
    }
}