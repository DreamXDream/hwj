package controller;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URL;

public class sendEmail {

    static String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /*
        public static void main(String[] args) {
            sendHtmlEmail();//发送普通邮件
          //  sendHtmlEmail();//发送html5邮件
          //   sendAttachmentsEmail();//发送带附件邮件
        }
      */


    private static void sendSimpleEmail() {
        Email email=new SimpleEmail();
        email.setHostName("smtp.qq.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("604774625@qq.com","xtzpfnruquulbcjj"));//发送人的邮箱和授权码
        email.setSSLOnConnect(true);

        try {
            email.setFrom("604774625@qq.com");//发送人的邮箱
            email.setSubject("测试主题");//发送的主题
            email.setMsg("测试内容");//发送的内容
            email.addTo("1903959864@qq.com");//接受人的邮箱
            email.send();
            System.out.println("发送simple邮件成功！！！");
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }


    public static void sendHtmlEmail(String mail,String username)  {
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.qq.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("764373135@qq.com","bktvscjvcldvbfec"));//发送人的邮箱和授权码
        email.setSSLOnConnect(true);
        try {
            email.setFrom("764373135@qq.com","hwj");
            email.setSubject("HTML5格式邮箱");
            String activeUrl="http://localhost:8080/xuenian/User/activemail.do?mail="+mail+"&username="+username;
            //email.setHtmlMsg("点击链接激活账户"+activeUrl);
            email.addPart("点击链接激活账户"+activeUrl, "text/html;charset=utf-8");
            email.setTextMsg("测试内容");
            email.addTo(mail, "hzk");
            email.send();
            System.out.println("发送HTML邮件成功！！");
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
    public static void sendHtmlEmails(String mail)  {
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.qq.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("764373135@qq.com","bktvscjvcldvbfec"));//发送人的邮箱和授权码
        email.setSSLOnConnect(true);
        try {
            email.setFrom("764373135@qq.com","hwj");
            email.setSubject("HTML5格式邮箱");
            int check1=  (int)((Math.random()*9+1)*10000);
            String check=String.valueOf(check1);

            email.setHtmlMsg("这是你的验证码:"+check);
            key=check;
            email.setTextMsg("测试内容");
            email.addTo(mail, "hzk");
            email.send();
            System.out.println("发送HTML邮件成功！！");
        } catch (EmailException e) {
            e.printStackTrace();
        }

    }

    private static void sendAttachmentsEmail() {
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.qq.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("604774625@qq.com","xtzpfnruquulbcjj"));//发送人的邮箱和授权码
        email.setSSLOnConnect(true);
        try {
            EmailAttachment attachment = new EmailAttachment();
            attachment.setURL(new URL("https://www.xys1118.com/images/topbg.jpg"));
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription("xys.jpg");
            attachment.setName("xys.jpg");

            email.attach(attachment);
            email.attach(new File("C:/Users/admin/Desktop/测试/1/1.txt"));
            email.setFrom("604774625@qq.com","hqs");
            email.setSubject("HTML5带有附件邮箱");
            email.setMsg("测试附件邮件内容");
            email.addTo("1903959864@qq.com", "hzk");
            email.send();
            System.out.println("发送HTML邮件成功！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
