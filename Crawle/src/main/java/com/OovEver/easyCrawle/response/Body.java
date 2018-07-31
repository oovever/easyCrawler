package com.OovEver.easyCrawle.response;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.XElements;
import us.codecraft.xsoup.Xsoup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * 响应的Body
 * @author OovEver
 * 2018/5/17 22:54
 */
public class Body {
    private final InputStream inputStream;
    private final  String      charset;
    private        String      bodyString;

    /**
     *
     * @param inputStream 输入流
     * @param charset 字符集
     */
    public Body(InputStream inputStream, String charset) {
        this.inputStream = inputStream;
        this.charset = charset;
    }

    /**
     *
     * @return 按指定编码格式解析读入的字符串
     */
    @Override
    public String toString() {
        if (null == this.bodyString) {
//            每一行的容量
            StringBuilder html = new StringBuilder(100);
            try {
//                按指定编码读入缓冲区
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset));
                String         temp;
                while ((temp = br.readLine()) != null) {
                    html.append(temp).append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.bodyString = html.toString();
        }
        return this.bodyString;

    }
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * 根据CSS解析相应的html内容
     * @param css 要解析内容部分的CSS
     * @return 解析结果
     */
    public Elements css(String css) {
        return Jsoup.parse(this.toString()).select(css);
    }

    /**
     * 基于XSOUP解析html
     * @param xpath 解析关键词
     * @return 解析结果
     */
    public XElements xpath(String xpath) {
        return Xsoup.compile(xpath).evaluate(Jsoup.parse(this.toString()));
    }

}
