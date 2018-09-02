package com.bjpowernode.servlet;

import com.bjpowernode.po.TextMessage;
import com.bjpowernode.util.CheckUtil;
import com.bjpowernode.util.MessageUtil;
import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * ClassName：WeiXinServlet
 * Package：com.bjpowernode.servlet
 * Description：
 *
 * @Date: 2018/9/2 11:20
 * @Author：zhangheng@bjpowernode.com
 */

public class WeiXinServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");


        PrintWriter out = response.getWriter();
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {

            out.print(echostr);

        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        //这个变量不用,写着玩玩
        String otherMessage = "               `.-----:::::----.`   \n" +
                "     `:+oo+/---:---..........-----:---/+oo+:`     \n" +
                "   `oo/--:/+os:-.................--:so+/:--/oo`   \n" +
                "   +.  .::----+-..................-+----::.  .+   \n" +
                "   ` .::----...-..................-.------::. `   \n" +
                "    :oo+-........-.....``......-++/......--:/:    \n" +
                "  ./dddds         `.-.`````..-/ddddo         `..  \n" +
                " - .hdddo.......`    :`````.: .hdddo.......`    - \n" +
                " :::://:------...--...````...---::-------:::::::: \n" +
                "`/:::////////:::--...............-::://///////://.\n" +
                ":/:::////////:::--..............--::::////////::/:\n" +
                "//:::::::-------..................-------::::::://\n" +
                "//::/::---------.................---------:::/:://\n" +
                "//::/:::-----------...........-----------::::+:://\n" +
                ":/:::o:::------------------------------:::::o:://:\n" +
                ".//::++:::::-------------------------::::::++:://.\n" +
                " ://::o+::::::--------------------::::::::+o:://: \n" +
                "  ///::os::::::::::-----------::::::::::/so::///  \n" +
                "  `://::/yo::::::::::::::::::::::::::::oy/:///:`  \n" +
                "    :///::+ys/::::::::::::::::::::::/sy+::///:    \n" +
                "     .////::/syso/::::::::::::::/osys/://///.     \n" +
                "       ./////::/osyyyssoooossyyyso/:://///.       \n" +
                "         .://////::://++++++//::///////:.         \n" +
                "            .-//////////////////////-.            \n" +
                "                ..-::////////::-..  ";

        try {
            Map<String, String> map = MessageUtil.xmlToMap(request);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            String message = null;
            if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {

                if ("1".equals(content)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
                } else if ("2".equals(content)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.SecondMenu());
                } else if ("?".equals(content) || "？".equals(content)) {

                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                } else {
                    message = MessageUtil.initText(toUserName, fromUserName, "输的什么玩意，不认识,重新输\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02");
                }

            } else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {

                String eventType = map.get("Event");
                if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {

                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }
            }

            out.print(message);
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
