package Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Mapper.UserMapper;
import domain.Leavemessage;
import domain.Person;
import domain.User;
import domain.Video;
import domain.RepeatMessage;
@Service
//加入到spring容器中

public class UserService {  

	@Autowired  
	//自动注入
	private UserMapper usermapper;  

	public void regist(String username, String password,String email,String active ) {
		// TODO Auto-generated method stub  
		 usermapper.save(username, password,email,active);
	}  

	public User login(String username, String password) {
		// TODO Auto-generated method stub  

		return usermapper.findByusername(username, password);
	}
	public User logins(String username, String password) {
		return usermapper.findByusernames(username, password);
	}


	public List<Video> findvideo() {
		// TODO Auto-generated method stub
		return usermapper.findvideo();
	}

	public void registadmin(User user) {  
		// TODO Auto-generated method stub  
		usermapper.saveadmin(user);  
	}

	public User admincheck(String username, String password) {  
		// TODO Auto-generated method stub  

		return usermapper.findByadmin(username, password);
	}

	public List<User> findadmin(String admin) {
		// TODO Auto-generated method stub
		return usermapper.findadmin(admin);
	}

	public User deleteadmin(Integer id) {
		// TODO Auto-generated method stub
		return usermapper.deleteadmin(id);
	}

	public List<User> findadmin1(Integer id) {
		// TODO Auto-generated method stub
		return usermapper.updataadmin(id);
	}

	public User updataadmin1(Integer id,String username, String password) {
		return usermapper.updataadmin1(id,username,password);

	}

	public User adminexist(String username) {
		return usermapper.adminexist(username);

	}
	public void setimg(String text,String name,String name1,String time,String vtime,int playback,String videoid,String classify) {
		usermapper.setimg(text,name,name1,time,vtime,playback,videoid,classify);

	}
	public void submitmessage(String username, String name, String job, String sex, String sign) {
		usermapper.submitmessage(username,name,job,sex,sign);
		
	}

	public List<Person> selectmessage(String username) {
		// TODO Auto-generated method stub
		return usermapper.selectmessage(username);
	}

	public void imgchange(String imgsetname, String img, String names) {
		usermapper.imgchange(imgsetname,img,names);
		
	}

	public void leavemessage(String username, String message,String time, String img,String txt) {
		
		usermapper.leavemessage(username,message,time,img,txt);
	}

	public String findmessageimg(String username) {
		// TODO Auto-generated method stub
		return usermapper.findmessageimg(username);
	}

	public List<Leavemessage> findleavemessage(String txt) {
		// TODO Auto-generated method stub
		return usermapper.findleavemessage(txt);
	}

	public Person collect(String username, String collect) {
		// TODO Auto-generated method stub
		return usermapper.collect(username, collect);

	}

	public List<Person> findcollect(String username) {
		// TODO Auto-generated method stub
		return usermapper.findcollect(username);
	}

	public int playback(String text) {
		return usermapper.playback(text);
		
	}

	public int updataplayback(String text) {
		return usermapper.updataplayback(text);
		
	}

	public String selectvideosrc(String text) {
		return usermapper.selectvideosrc(text);
		
	}

	public List<Video> ordervideo() {
		// TODO Auto-generated method stub
		return usermapper.ordervideo();
	}

	public List<Video> ordervideo1() {
		// TODO Auto-generated method stub
		return usermapper.ordervideo1();
	}

	public List<Video> findcollect1(List<String> p1) {
		// TODO Auto-generated method stub
		return usermapper.findcollect1(p1);
	}

	public void insertnewUser(String username, String name, String job, String sex, String sign, String img,
			String collect) {
		usermapper.insertnewUser(username,name,job,sex,sign,img,collect);
		
	}
	public void deleteCollect(String txt) {
		usermapper.deleteCollect(txt);
		
	}
	public Video findvideotext(String text) {
		return usermapper.findvideotext(text);
	}

	public String deletevideosrc(String text) {
		return usermapper.deletevideosrc(text);

	}

	public String getVideotext(String videoid) {
		return usermapper.getVideotext(videoid);
	}

	public List<Video> searchvideo(String text) {
		return usermapper.searchvideo(text);
	}


	public User findemail(String email) {
		return usermapper.findemail(email);
	}

	public void updataemail(String active,String email) {
		usermapper.updataemail(active,email);
	}

    public List<User> findUser() {
		return usermapper.findUser();
    }


	public User deleteuser(String id) {
		// TODO Auto-generated method stub
		return usermapper.deleteuser(id);
	}

	public String findmail1(String mail) {
		return usermapper.findmail1(mail);
	}

	public void updatapasswords(String username,String password) {
		usermapper.updatapasswords(username,password);
	}


    public void repeatmessage(int id,String username, String reusername, String message, String time, String txt, String img,String active) {
       usermapper.repeatmessage(id,username, reusername,message,time,txt,img,active);
    }

	public List<RepeatMessage> findrepeatmessage() {

		return usermapper.findrepeatmessage();
	}

    public void updatavideo(Integer id, String videotext, String classifies, Integer playback) {
		usermapper.updatavideo(id,videotext, classifies,playback);
    }

	public void updatavideotime(String id,String vtime) {
		usermapper.updatavideotime(id,vtime);
	}

    public List<Leavemessage> getleavemessage() {

		return usermapper.getleavemessage();
	}

	public void deleteleavemessage(Integer id) {
         usermapper.deleteleavemessage(id);
	}

	public List<Person> findcollectall() {
		return  usermapper.findcollectall();
	}

	public User findemails(String email, String username) {
		return usermapper.findemails(email,username);
	}


	public List<RepeatMessage> messagestotal(String username, String active) {
		return usermapper.messagestotal(username,active);
	}

    public List<RepeatMessage> findmessagetell(String username) {
		return usermapper.findmessagetell(username);
    }

	public void readmessage(String active, String findid) {
		usermapper.readmessage(active,findid);
	}

	public String findimg(String imgsetname) {
		return usermapper.findimg(imgsetname);
	}
}
