package controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.*;

import net.sf.json.JSONArray;
import org.apache.commons.io.FilenameUtils;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import Service.UserService;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;




@Controller
//注册
@RequestMapping(value = "/User") //可以对url分类管理
public class userConroller {
    String sessionclear;
    @Autowired

    private UserService userService;

    @RequestMapping(value = "/user.do")  //@RequestMapping实现queryItem方法和url进行映射
    @ResponseBody
    public String regist(HttpServletRequest request, User user, Model model, ModelAndView modelandview) {

        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String active = "0";
        String name = "未设置呢称";
        String job = "未设置职位";
        String sex = "未设置性别";
        String sign = "未设置签名";
        String img = "http://47.107.176.8:8080/video/honghu.jpg";
        String collect = "";

         System.out.println("注册:"+username+":"+password);

         User  user1 = userService.login(username, password);


            if ((user1 != null )   || !password.equals(password1)) {
                System.out.println("No");
                return "No";
            } else {
                try{
                    userService.insertnewUser(username, name, job, sex, sign, img, collect);

                    sendEmail a=new sendEmail();
                    a.sendHtmlEmail(email,username);
                    userService.regist(username, password, email, active);
                }catch (Exception e){

                    System.out.println("用户注册：" + user.getUsername() + user.getPassword());

                    return "Yes";
                }
            }

            //注册成功后跳转success.jsp页面
             return "";


    }

    //登录
    @RequestMapping(value = "/login.do")
    @ResponseBody
    public String login(HttpServletResponse response, HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password) throws Exception {
        User user = userService.login(username, password);
        User user1 = userService.admincheck(username, password);
        String password1 = request.getParameter("password1");
        ModelAndView mv = new ModelAndView("redirect:/home1.html");
        String remember = request.getParameter("checkbox");
        System.out.println(remember + username + password);

        //HttpSession session=request.getSession();
        if (user1 != null) {
            System.out.println("admin哦!");
            HttpSession session = request.getSession();
            session.setAttribute("admin", username);
            System.out.println("adminsession:" + session.getAttribute("admin"));
            return "admin";
        }
        if ((user != null) && password1.equals(password)&&"1".equals(user.getActive()) ) {

            //session.setAttribute("user",username);
            Cookie loginAccountCookie = new Cookie("loginAccount", username);
            Cookie loginPasswordCookie = new Cookie("loginPassword", password);
            loginAccountCookie.setPath(request.getContextPath() + "/");
            loginPasswordCookie.setPath(request.getContextPath() + "/");

            //设置Cookie的父路经

            //获取是否保存Cookie（例如：复选框的值）
            if ("checkon".equals(remember)) {


                //System.out.println(remember);
                //保存Cookie的时间长度，单位为秒
                loginAccountCookie.setMaxAge(1 * 24 * 60 * 60);
                loginPasswordCookie.setMaxAge(1 * 24 * 60 * 60);

                //加入Cookie到响应头
                response.addCookie(loginAccountCookie);
                response.addCookie(loginPasswordCookie);

                HttpSession session = request.getSession();
                //将数据存储到session中
                session.setAttribute("loginname", username);


                System.out.println("addSession:" + session.getAttribute("loginname"));


            } else {

                //不保存Cookie
                HttpSession session = request.getSession();
                //将数据存储到session中
                session.setAttribute("loginname", username);
                System.out.println("addSession:" + session.getAttribute("loginname"));

            }

            return "Yes";
        } else {
            return "No";
        }


    }

    /**
     *     	 * 获取 Cookie中的信息

     */
    @RequestMapping(value = "/cookies.do")
    @ResponseBody
    public String getCookie(HttpServletRequest request) {
        String username = "";
        String password = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                if (c.getName().equals("loginAccount")) {
                    username = c.getValue();

                }
                if (c.getName().equals("loginPassword")) {
                    password = c.getValue();
                }
            }
            return username + "+" + password;
        } else {
            System.out.println(username + password);
            return "No";
        }
    }


    //退出登录  清除 session
    @RequestMapping(value = "/clearsession.do")
    @ResponseBody
    public String login(HttpServletRequest request) {


        HttpSession session = request.getSession();


        session.setAttribute("loginname", "");
        return "Yes";

    }

    @RequestMapping(value = "/clearadminsession.do")
    @ResponseBody
    public String clearadminsession(HttpServletRequest request) {


        HttpSession session = request.getSession();


        session.setAttribute("admin", "");
        return "Yes";

    }

    //  上传视频
    @RequestMapping(value = "/addImg.do")
    @ResponseBody
    public String addImg(HttpServletRequest request, Video video) throws Exception {
        //这个RequestParam(value="file")的是我们在前台的name=“file”
        // 使用UUID给图片重命名，并去掉四个“-”

        String text = request.getParameter("text");
        String murl = request.getParameter("murl");
        String vurl = request.getParameter("vurl");
        String videoid = request.getParameter("videoid");
        String classify = request.getParameter("classify");
        String vtime = "";
        if (text != "") {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String time = df.format(new Date());
            video.setTime(time);
            int playback = 0;
            System.out.println("上传时间" + time);

            userService.setimg(text, murl, vurl, time, vtime, playback, videoid,classify);
            return "Yes";
            // 重定向到查询所有用户的Controller，测试图片回显

        } else {
            return "No";
        }

    }

    //上传图片

    @RequestMapping(value = "/imgchange.do")
    @ResponseBody
    public String imgchange(HttpServletRequest request, Person person, @RequestParam(value = "imgsetname") String imgsetname, @RequestParam(value = "imgfile") MultipartFile pictureFile) throws Exception {
        //这个RequestParam(value="file")的是我们在jsp的name=“file”
        // 使用UUID给图片重命名，并去掉四个“-”




        String name = UUID.randomUUID().toString().replaceAll("-", "");
        // 获取文件的扩展名
        String ext = FilenameUtils.getExtension(pictureFile
                .getOriginalFilename());
        System.out.println("Username_:" + imgsetname);
        System.out.println(name + ext);
        // 设置图片上传路径
        // 以绝对路径保存重名命后的图片
        pictureFile.transferTo(new File("D:\\video\\" + name + "." + ext));
        // 把图片存储路径保存到数据库

        person.setUsername(imgsetname);
        person.setImg("http://localhost:8080/video/" + name + "." + ext);
        String img = "http://localhost:8080/video/" + name + "." + ext;
          String names=name+"."+ext;

        String imgs=  userService.findimg(imgsetname);

        String  path="D:\\video\\"+imgs;
        File file = new File(path);//文件路径
        file.delete();




        userService.imgchange(imgsetname, img,names);




        // 重定向到查询所有用户的Controller，测试图片回显

        return "Yes";
    }

    //获取视频列表
    @RequestMapping(value = "/video.do")
    @ResponseBody
    public List<Video> video(Video video, Model model) {

        List<Video> v = userService.findvideo();

        for (int i = 0; i < v.size(); i++) {
            System.out.println(v.get(i).getVurl());
            System.out.println(v.get(i).getTime());
        }
        return v;
    }
    //获取视频列表
    @RequestMapping(value = "/videos.do")
    @ResponseBody
    public  List<Video> videos(Video video, Model model) {

        List<Video> v = userService.findvideo();


        for (int i = 0; i < v.size(); i++) {

        }

        JSONArray j = JSONArray.fromObject(v);
         System.out.println(j);

        return j;
    }



    @RequestMapping(value = "/ordersearchvideo.do")
    @ResponseBody
    public List<Video> ordersearchvideo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Video> v1;

        v1 = (List<Video>) session.getAttribute("list");

        System.out.println("分界线2:");
        Collections.sort(v1);
        for (int i = 0; i < v1.size(); i++) {
            System.out.println(v1.get(i).getText());

        }

        return v1;

    }



    //播放量
    @RequestMapping(value = "/playback.do")
    @ResponseBody
    public String playback(HttpServletRequest request, Video video, Model model, ModelAndView modelandview) {
        String text = request.getParameter("text");


        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("video1");
        //int a=userService.playback(text);
        //int playback=a+1;

        userService.updataplayback(text);
        String videoid = userService.selectvideosrc(text);

        HttpSession session = request.getSession();
        session.setAttribute("src", videoid);
        session.setAttribute("text", text);
        String src = (String) session.getAttribute("src");

        System.out.println(videoid);

        return videoid;


    }


    //获取lesson
    @RequestMapping(value = "/getvideosrc.do")
    @ResponseBody
    public String getvideosrc(HttpServletRequest request, User user, Model model) {

        HttpSession session = request.getSession();

        String src = (String) session.getAttribute("src");
        String text = (String) session.getAttribute("text");

        return src + "+" + text;

    }
    //获取lesson


    //添加管理员
    @RequestMapping(value = "/admin.do")
    @ResponseBody
    public String registadmin(HttpServletRequest request, User user, Model model) {

        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        String username = request.getParameter("username");
        System.out.println(password + password1 + username);
        User user1 = userService.admincheck(username, password);


        if (user1 != null || !password.equals(password1)) {


            System.out.println("No");
            return "No";
        } else {

            userService.registadmin(user);

            //注册成功后跳转success.jsp页面
            System.out.println("用户注册：" + user.getUsername() + user.getPassword());

            return "Yes";

        }
    }

    //更新admin
    @RequestMapping(value = "/updataadmin.do")
    @ResponseBody
    public List<User> updataadmin(HttpServletRequest request, User user, Model model) {

        Integer id = Integer.parseInt(request.getParameter("id"));
        List<User> v = userService.findadmin1(id);

        return v;
    }


    //更新admin
    @RequestMapping(value = "/updataadmin1.do")
    @ResponseBody
    public String updataadmin1(HttpServletRequest request, User user, Model model) {

        String password = request.getParameter("findpassword");
        Integer id = Integer.parseInt(request.getParameter("findid"));
        String username = request.getParameter("findusername");
        System.out.println(password + username);
        User user1 = userService.admincheck(username, password);
        if (user1 != null) {
            return "No";
        } else {
            try {
                userService.updataadmin1(id, username, password);
            } catch (Exception e) {
                System.out.print("异常跑出");
            }
            return "Yes";
        }

    }


    //更新视频信息

    @RequestMapping(value = "/ updatavideo.do")
    @ResponseBody
    public String  updatavideo(HttpServletRequest request,Video video, Model model) {


        Integer id = Integer.parseInt(request.getParameter("idfind"));
        String videotext = request.getParameter("videotext");
        String classifies = request.getParameter("classifies");
        Integer playback = Integer.parseInt(request.getParameter("playback"));


        userService.updatavideo(id,videotext,classifies,playback);


            return "Yes";


    }
    //判断管理员是否存在
    @RequestMapping(value = "/adminexist.do")
    @ResponseBody
    public String adminexist(HttpServletRequest request, Model model) {
        String username = request.getParameter("session");

        User user = userService.adminexist(username);
        if (user != null) {
            return "Yes";
        } else {
            return "No";
        }


    }


    @RequestMapping(value = "/deleteleavemessage.do")
    @ResponseBody
    public String deleteleavemessage(HttpServletRequest request, Model model) {

        Integer id = Integer.parseInt(request.getParameter("idfind"));

            userService.deleteleavemessage(id);

        return "Yes";
    }

    //删除管理员
    @RequestMapping(value = "/deleteadmin.do")
    @ResponseBody
    public String deleteadmin(HttpServletRequest request, Model model) {

        Integer id = Integer.parseInt(request.getParameter("id"));
        System.out.println(id);
        try {
            userService.deleteadmin(id);
        } catch (Exception e) {
            System.out.print("异常跑出");
        }
        return "Yes";
    }
//删除用户

    @RequestMapping(value = "/deleteuser.do")
    @ResponseBody
    public String deleteuser(HttpServletRequest request, Model model) {

        String id = request.getParameter("username");
        System.out.println(id);
        try {
            userService.deleteuser(id);
        } catch (Exception e) {
            System.out.print("异常跑出");
        }
        return "Yes";
    }
    //删除视频
    @RequestMapping(value = "/deleteVideo.do")
    @ResponseBody
    public String deleteVideo(HttpServletRequest request, Model model) {
        String text = request.getParameter("txt");
        String videoid = userService.selectvideosrc(text);


        MovieController a = new MovieController();
        String result = a.shanchu(videoid);
        if ("yes".equals(result)) {
            userService.deletevideosrc(text);

            return "Yes";
        } else {
            System.out.println(result);
            return "No";
        }

    }


    //查找管理员
    @RequestMapping(value = "/getadmin.do")
    @ResponseBody
    public List<User> User(HttpServletRequest request, User user, Model model) {

        String admin = request.getParameter("admin");
        System.out.println("要查找的" + admin);
        List<User> v = userService.findadmin(admin);
        for (int i = 0; i < v.size(); i++) {
            System.out.println(v.get(i).getUsername());
        }

        return v;
    }
    //查找所有用户
    @RequestMapping(value = "/getUser.do")
    @ResponseBody
    public List<User> getUser(HttpServletRequest request, User user, Model model) {



        List<User> v = userService.findUser();
        for (int i = 0; i < v.size(); i++) {
            System.out.println(v.get(i).getUsername());
        }

        return v;
    }

    //个人信息


    @RequestMapping(value = "/submitmessage.do")
    @ResponseBody
    public String submitmessage(HttpServletRequest request, Person person, Model model) {

        String username = request.getParameter("session");
        String name = request.getParameter("nickname");
        String job = request.getParameter("job");
        String sex = request.getParameter("sex");
        String sign = request.getParameter("sign");
        System.out.println(username + name);
        userService.submitmessage(username, name, job, sex, sign);

        return "Yes";


    }

    //页面个人信息
    @RequestMapping(value = "/selectmessage.do")
    @ResponseBody
    public List<Person> Person(HttpServletRequest request, Person person, Model model) {

        String username = request.getParameter("session");
        System.out.println("加载username:" + username);
        List<Person> p = userService.selectmessage(username);


        return p;
    }

    @RequestMapping(value = "/findleavemessage.do")
    @ResponseBody
    public List<Leavemessage> findleavemessage(HttpServletRequest request, Leavemessage leavemessage, Model model) {
        HttpSession session = request.getSession();

        String txt = session.getAttribute("text").toString();
        String videoid = session.getAttribute("src").toString();
        //  String txt=userService.getVideotext("videoid");
        System.out.println("videoid" + videoid);


        List<Leavemessage> p = userService.findleavemessage(txt);


        return p;
    }
    @RequestMapping(value ="/messagesremind.do")
    @ResponseBody
     public String messagesremind(HttpServletRequest request,RepeatMessage repeatMessage ){

        String username=request.getParameter("username");
        System.out.println(username);
        List<RepeatMessage> list = new ArrayList<RepeatMessage>();//创建集合对象；
        String active="0";
        list=   userService.messagestotal(username,active);
        int totals=list.size();
        String total=totals+"";
        return total;
    }

    @RequestMapping(value = "/findrepeatmessage.do")
    @ResponseBody
    public List<RepeatMessage> findrepeatmessage(HttpServletRequest request, RepeatMessage repeatMessage, Model model) {
        HttpSession session = request.getSession();
       // String txt = session.getAttribute("text").toString();
        //String videoid = session.getAttribute("src").toString();
        //  String txt=userService.getVideotext("videoid");
        //System.out.println("videoid" + videoid);
        List<RepeatMessage> p = userService.findrepeatmessage();

        return p;
    }
    @RequestMapping(value = "/findmessagetell.do")
    @ResponseBody
    public  List<RepeatMessage> findmessagetell(HttpServletRequest request, RepeatMessage repeatMessage, Model model) {
        HttpSession session = request.getSession();
        // String txt = session.getAttribute("text").toString();
        //String videoid = session.getAttribute("src").toString();
        //  String txt=userService.getVideotext("videoid");
        //System.out.println("videoid" + videoid);
        String username=request.getParameter("username");
        List<RepeatMessage> p = userService.findmessagetell(username);




        return p;
    }




    //留言板
    @RequestMapping(value = "/leavemessage.do")
    @ResponseBody
    public String leavemessage(HttpServletRequest request, Leavemessage leavemessage, Model model) {

        String username = request.getParameter("session");
        String message = request.getParameter("message");
        String videoid = request.getParameter("videoid");
        String txt = userService.getVideotext(videoid);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        String time = df.format(new Date());
        System.out.println(time);// new Date()为获取当前系统时间

        System.out.println(txt);

        leavemessage.setMessage(message);

        String img = userService.findmessageimg(username);

        userService.leavemessage(username, message, time, img, txt);

        return "Yes";

    }


    //一级回复
    @RequestMapping(value = "/repeatmessage.do")
    @ResponseBody
    public String repeatmessage(HttpServletRequest request, Leavemessage leavemessage, Model model) {

        String username = request.getParameter("session");
        String reusername=request.getParameter("reusername");
        String message = request.getParameter("repeatmessage");
        String videoid = request.getParameter("videoid");
        String ids = request.getParameter("id");
        int id=Integer.parseInt(ids);
        String txt = userService.getVideotext(videoid);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        String time = df.format(new Date());
        System.out.println(time);// new Date()为获取当前系统时间

        System.out.println(txt);

        String img = userService.findmessageimg(username);

        String active="0";
if(reusername.equals(username)){

    active="1";
    userService.repeatmessage(id,username, reusername,message,time,txt,img,active);

}
else{

    active="0";
    userService.repeatmessage(id,username, reusername,message,time,txt,img,active);
}



        return img;

    }



    //二级回复
    @RequestMapping(value = "/repeatmessaged.do")
    @ResponseBody
    public String repeatmessaged(HttpServletRequest request, Leavemessage leavemessage, Model model) {

        String username = request.getParameter("session");
        String reusername=request.getParameter("reusername");
        String message = request.getParameter("repeatmessage");
        String videoid = request.getParameter("videoid");
        String ids = request.getParameter("id");
        int id=Integer.parseInt(ids);
        String txt = userService.getVideotext(videoid);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        String time = df.format(new Date());
        System.out.println(time);// new Date()为获取当前系统时间

        System.out.println(txt);

        String img = userService.findmessageimg(username);
        String active="0";
        if(reusername.equals(username)){

            active="1";
            userService.repeatmessage(id,username, reusername,message,time,txt,img,active);

        }
        else{

            active="0";
            userService.repeatmessage(id,username, reusername,message,time,txt,img,active);
        }

        return img;

    }


    @RequestMapping(value = "/readmessage.do")
    @ResponseBody
    public String readmessage(HttpServletRequest request, RepeatMessage repeatMessage, Model model) {

        String findid = request.getParameter("findid");
        String active="1";
        userService.readmessage(active,findid);

    return "";
    }

    // produces = "text/html; charset=UTF-8规定传值页面不为乱码
    //获取视频标题
    @RequestMapping(value = "/gettxt.do", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String gettxt(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("text/xml;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String text = session.getAttribute("text").toString();
        String videoid = session.getAttribute("src").toString();
        System.out.println("text:" + text);
        return text;

    }




    @RequestMapping(value = "/getvideoid.do")
    @ResponseBody
    public String getvideoid(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        String videoid = session.getAttribute("src").toString();

        System.out.println("获得的id" + videoid);
        return videoid;

    }

    @RequestMapping(value = "/getvideotext.do",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getvideotext(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        String videoid = session.getAttribute("src").toString();

        String videotext= userService.getVideotext(videoid);
      //  System.out.println("获得的："+videotext);
       // System.out.println("获得的id" + videoid);
        return videotext;

    }


    //收藏
    @RequestMapping(value = "/collect.do")
    @ResponseBody
    public String collect(HttpServletRequest request, Person person, Model model) {


        String username = request.getParameter("session");

        String videoid = request.getParameter("videoid");
        String collect1 = userService.getVideotext(videoid);


        String findcollect = "<" + collect1 + ">";
        List<Person> p = userService.findcollect(username);
        for (int i = 0; i < p.size(); i++) {

            System.out.println(p.get(i).getCollect());
            String collect2 = p.get(i).getCollect().toString();

            int intIndex = collect2.indexOf(findcollect);
            if (intIndex == -1) {

                String collect = collect2 + "<" + collect1 + ">";
                try {
                    userService.collect(username, collect);
                } catch (Exception e) {
                    return "Yes";
                }

            } else {
                collect2 = collect2.replace(findcollect, "");
                String collect = collect2;

                try {
                    userService.collect(username, collect);
                } catch (Exception e) {
                    return "No";
                }

            }
        }
        return "";
    }

    @RequestMapping(value = "/getcollectred.do")
    @ResponseBody
    public String getcollectred(HttpServletRequest request, Person person, Model model) {


        String username = request.getParameter("session");

        String videoid = request.getParameter("videoid");
        String collect1 = userService.getVideotext(videoid);
        System.out.println("213213:"+username);

        String findcollect = "<" + collect1 + ">";
        List<Person> p = userService.findcollect(username);
        for (int i = 0; i < p.size(); i++) {

            System.out.println(p.get(i).getCollect());
            String collect2 = p.get(i).getCollect().toString();

            int intIndex = collect2.indexOf(findcollect);
            if (intIndex == -1) {

                return "No";

            } else {

                    return "Yes";


            }
        }
        return "";


    }




    //获取用户收藏视频
    @RequestMapping(value = "/getCollect.do")
    @ResponseBody
    public List<Video> getCollect(HttpServletRequest request, Person person, Video video, Model model) {

        List<Video> kong = null;
        String username = request.getParameter("session");
        String s = null;
        System.out.println("collect:" + username);
        try {

            List<Person> p = userService.findcollect(username);

            for (int i = 0; i < p.size(); i++) {
                s = p.get(i).getCollect();
            }

        } catch (Exception e) {
            System.out.print("异常跑出");
        }
        if (s != null) {
            String a[] = s.split("<");
            String b = "";
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < a.length; i++) {
                sb.append(a[i]);        //append String并不拥有该方法，所以借助StringBuffer
            }
            String sb1 = sb.toString();

            System.out.println(sb1);    //0123sb12f

            String c[] = sb1.split(">");

            List<String> p1 = null;

            for (int i = 0; i < c.length; i++) {
                String get = c[i];
                //Video video1=userService.findcollect1(get);
                p1 = java.util.Arrays.asList(c);
                //System.out.println(c[i]);
            }
            List<Video> p2 = userService.findcollect1(p1);

            return p2;

        } else {

        }
        return kong;

    }

    //删除收藏
    @RequestMapping(value = "/deleteCollect.do")
    @ResponseBody
    public String deleteCollect(HttpServletRequest request, Person person, Model model) {


        String username = request.getParameter("session");

        String collect1 = request.getParameter("txt");
        System.out.println("接收" + collect1);
        String findcollect = "<" + collect1 + ">";

        List<Person> p = userService.findcollect(username);
        for (int i = 0; i < p.size(); i++) {

            System.out.println(p.get(i).getCollect());
            String collect2 = p.get(i).getCollect().toString();

            int intIndex = collect2.indexOf(findcollect);
            if (intIndex == -1) {
                return "No";

            } else {
                collect2 = collect2.replace(findcollect, "");
                String collect = collect2;

                try {
                    userService.collect(username, collect);
                } catch (Exception e) {
                    return "Yes";
                }

            }
        }
        return "";


    }
    @RequestMapping(value = "/cookies1.do")
    @ResponseBody
    public String getCookie1(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("loginname");
        System.out.println("username:" + username);
        return username;

    }
    //获取用户名

    @RequestMapping(value = "/cookies2.do")
    @ResponseBody
    public String getCookie2(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("loginname");
        System.out.println("username:" + username);
        return username;

    }


    @RequestMapping(value = "/ getleavemessage.do")
    @ResponseBody
    public List<Leavemessage>  getleavemessage(HttpServletRequest request, Model model) {

        List<Leavemessage> v ;


        v=userService.getleavemessage();

        return v;

    }




    //获取头像
    @RequestMapping(value = "/getheadimg1.do")
    @ResponseBody
    public String getheadimg1(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String username ;
        String username1=request.getParameter("username1");
        username=username1;
        System.out.println("username:" + username);
        String imgsrc = userService.findmessageimg(username);
        System.out.println("src:" + imgsrc);

            return imgsrc;






    }

    //获取头像
    @RequestMapping(value = "/getheadimg.do")
    @ResponseBody
    public String getheadimg(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();


        String username = (String) session.getAttribute("loginname");
        System.out.println("username:" + username);
        String imgsrc = userService.findmessageimg(username);

        System.out.println("src:" + imgsrc);
        return imgsrc;

    }

    @RequestMapping(value = "/go.do")
    public ModelAndView go(User user,HttpServletRequest request, Model model) {

        ModelAndView mv = new ModelAndView("redirect:/login(1).html");
        return mv;
    }


    //验证邮箱
    @RequestMapping(value = "/activemail.do")
    public ModelAndView activemail(User user,HttpServletRequest request, Model model) {

        String email = request.getParameter("mail");
        String username = request.getParameter("username");
        System.out.println(email);
        String active = "1";
        //1.根据激活码从数据库查出用户信息
        ModelAndView mv = new ModelAndView("redirect:/success.html");
        ModelAndView mv1 = new ModelAndView("redirect:/fail.html");

        user = userService.findemails(email,username);
        //2.如果能查出来那么我们就将这个用户的激活码状态变为激活
        if (user != null) {
            userService.updataemail(active, email);
            return mv;

        } else {
            System.out.println("激活失败!");
            return mv1;
        }
    }

//获取管理员
    @RequestMapping(value = "/getadminsession.do")
    @ResponseBody
    public String getadminsession(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("admin");
        System.out.println("admin:" + username);
        System.out.println("get:" + session.getAttribute("admin"));
        return username;


    }

    //form表单ajax提交
    @RequestMapping(value = "/formsub.do")
    @ResponseBody
    public String formsub(HttpServletRequest request) {

        return "Yes";
    }





    //通过shangchuan.do方法得到秘钥
//APPID APPKEY 进入腾讯云控制台获取https://console.cloud.tencent.com/cam/capi
    @RequestMapping(value = "/shangchuan.do")
    @ResponseBody
    public String shangchuan(Model model){
        Signature sign = new Signature();
        sign.setSecretId("AKIDa4OzSjCv42oRODkAYr0ykKiz77wbuPXY");
        sign.setSecretKey("yIHyaHrFUXOzlQwsgJl02KZcUnwtkSyJ");

        sign.setCurrentTime(System.currentTimeMillis() / 1000L);
        sign.setRandom(new Random().nextInt(2147483647));
        sign.setSignValidDuration(172800);
        try
        {
            return sign.getUploadSignature();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "获取签名失败";

    }
    @RequestMapping(value = "/getvideotime.do")
    @ResponseBody
    public String getvideotime(Model model,HttpServletRequest request) {
        String id = request.getParameter("id");
        System.out.println(id);
        MovieController a=new MovieController();
        String vtime= a.shichang(id);
        System.out.println(vtime);
     userService.updatavideotime(id,vtime);


    return "Yes";
    }


    @ServerEndpoint(value = "/chat/{id}")
    public static class ChatAnnotation {

        private static final String GUEST_PREFIX = "";
        /**
         * 一个提供原子操作的Integer的类。在Java语言中，++i和i++操作并不是线程安全的，
         * 在使用的时候，不可避免的会用到synchronized关键字。 而AtomicInteger则通过一种线程安全的加减操作接口。
         */
        private static final AtomicInteger connectionIds = new AtomicInteger(0);
        private static final Set<ChatAnnotation> connections = new CopyOnWriteArraySet<>();
        private String nickname;
        private Session session;

        /**
         * 创建连接时间调用的方法
         *
         * @param session
         */
        @OnOpen
        public void start(@PathParam("id") String id, Session session) {
            this.session = session;
            nickname = GUEST_PREFIX + id;
            connections.add(this);
            String message = String.format("%s %s", id, "来啦！");
            //上线通知
            broadcast(message);
            try {
                //系统问候语
               // SendHello(this.nickname);
                //返回在线用户
                onlineList();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        /**
         * 链接关闭时调用方法
         */
        @OnClose
        public void end() {
            connections.remove(this);
            String message = String.format("%s %s", nickname, "溜了..");
            broadcast(message);
        }
        /**
         * 传输信息过程中调用方法
         * @param message
         */
        @OnMessage
        public void incoming(String message) {
            // Never trust the client


            String m = String.format(" %s", message);
            if(m.contains("to")){
                //点对点发送
                broadcastOneToOne(m,nickname);
            }else{
                //群发
                broadcast(m);
            }
        }
        /**
         * 发生错误是调用方法
         * @param t
         * @throws Throwable
         */
        @OnError
        public void onError(Throwable t) throws Throwable {
            System.out.println("错误: " + t.toString());
        }
        /**
         * 消息广播
         * 通过connections，对所有其他用户推送信息的方法
         * @param msg
         */
        private static void broadcast(String msg) {
            for (ChatAnnotation client : connections) {
                try {
                    synchronized (client) {
                        client.session.getBasicRemote().sendText(msg);
                    }
                } catch (IOException e) {
                    System.out.println("错误:向客户端发送消息失败");
                    connections.remove(client);
                    try {
                        client.session.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    String message = String.format("* %s %s", client.nickname,"退出聊天室");
                    broadcast(message);
                }
            }
        }
        /**
         * 点对点发送消息
         * 通过connections，对所有其他用户推送信息的方法
         * @param msg
         */
        private static void broadcastOneToOne(String msg, String nickName) {
            String[] arr = msg.split("to");
            for (ChatAnnotation client : connections) {
                try {
                    if(arr[1].equals(client.nickname) || nickName.equals(client.nickname)){
                        synchronized (client) {
                            client.session.getBasicRemote().sendText(arr[0]);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("错误:向客户端发送消息失败");
                    connections.remove(client);
                    try {
                        client.session.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    String message = String.format("* %s %s", client.nickname,"退出聊天室");
                    broadcast(message);
                }
            }
        }
        //系统问候语
        private static void SendHello(String nickName) throws IOException{
            String m = String.format("* %s %s", nickName, "你好");
            for (ChatAnnotation client : connections) {
                if(client.nickname.equals(nickName)){
                    client.session.getBasicRemote().sendText(m);
                }
            }
        }
        //在线用户
        private static void onlineList() throws IOException{
            String online = "";
            for (ChatAnnotation client : connections) {
                if(online.equals("")){
                    online = client.nickname;
                }else{
                    online += ","+client.nickname;
                }
            }
            String m = String.format(" %s %s", "当前在线用户", online);
            for (ChatAnnotation client : connections) {
                client.session.getBasicRemote().sendText(m);
            }
        }
    }


    @RequestMapping(value = "/getcheck.do")  //@RequestMapping实现queryItem方法和url进行映射
    @ResponseBody
    public String getcheck(HttpServletRequest request) {
        String mail=request.getParameter("mail");
        User user=  userService.findemail(mail);

        String check;

        String username="test";
        HttpSession session=request.getSession();
        if(user!=null){
            sendEmail a=new sendEmail();
            try{
                a.sendHtmlEmails(mail);
               String key1=  a.getKey();
               session.setAttribute("check",key1);

            }catch (Exception e){
                System.out.println("-");
            }

                  username = userService.findmail1(mail);
                 System.out.println(username);

             return username;
        }
        else{
            return "No";
        }
    }
    @RequestMapping(value = "/updatapassword.do")  //@RequestMapping实现queryItem方法和url进行映射
    @ResponseBody
    public String updatapassword(HttpServletRequest request) {
        String checknumber=request.getParameter("checknumber");
        HttpSession session=request.getSession();
        String check= (String) session.getAttribute("check");

        if(checknumber.equals(check)){
            return "Yes";
        }
        else{
            return "No";
        }

    }

    @RequestMapping(value = "/updatapasswords.do")  //@RequestMapping实现queryItem方法和url进行映射
    @ResponseBody
    public String updatapasswords(HttpServletRequest request) {
        String password=request.getParameter("password");
        String username=request.getParameter("username");
     System.out.println(username+":"+password);
           userService.updatapasswords(username,password);

            return "Yes";


    }

}


