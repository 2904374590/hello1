package com.flower.utils;/*
 * ctrl+Alt+T    代码包围
 * ctrl+shift+/   多行
 * ctrl+/         单行
 * shift+f6       重命名
 * alt+enter      百能快捷键
 * ctrl+n         搜索类
 * alt+insert     构造方法
 * ctrl+alt+v     自动生成对象
 * ctrl+H         继承树
 * alt+enter      导包，自动修正代码，解决出现的任何问题，也可以生成返回值
 * */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    public static String readFileOfTxt(String path){

        StringBuilder sb = new StringBuilder();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            char[] buf = new char[1024];
            int len = -1;
            while ((len = bufferedReader.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
