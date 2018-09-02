package com.bjpowernode.util;

import com.bjpowernode.po.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xml转换为Map
 * ClassName：MessageUtil
 * Package：com.bjpowernode.util
 * Description：
 *
 * @Date: 2018/9/2 16:08
 * @Author：zhangheng@bjpowernode.com
 */


public class MessageUtil {

    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";


    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        HashMap<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();

        ServletInputStream ins = request.getInputStream();
        Document doc = reader.read(ins);

        Element root = doc.getRootElement();

        List<Element> list = root.elements();
        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }

        ins.close();
        return map;
    }

    /**
     * 将文本信息转换为xml
     *
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage) {
        XStream xStream = new XStream();

        xStream.alias("xml", textMessage.getClass());
        return xStream.toXML(textMessage);
    }

    /**
     * 主菜单
     *
     * @return
     */
    public static String menuText() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("欢迎老铁的关注,请按照菜单进行操作:\n\n");
        buffer.append("1、课程介绍\n");
        buffer.append("2、动力节点介绍\n");
        buffer.append("回复?调出此菜单");

        return buffer.toString();
    }

    public static String initText(String toUserName, String fromUserName, String content) {

        TextMessage text = new TextMessage();
        text.setToUserName(fromUserName);
        text.setFromUserName(toUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime() + "");

        text.setContent(content);

        return textMessageToXml(text);

    }

    public static String firstMenu() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("课程主要是介绍JAVA的学习,详情你上网搜搜就知道了。");

        return buffer.toString();
    }

    public static String SecondMenu() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("北京动力节点教育科技有限公司自2009年成立以来，已经累计培训了2000名以上Java软件工程师，分别在加拿大、");
        buffer.append("法国、日本、新西兰、爱尔兰及国内大中城市成功就业，实现了Java软件工程师梦想。");
        buffer.append("动力节点创始人王勇老师，是国内知名的Java培训...你懂得");

        return buffer.toString();
    }
}
