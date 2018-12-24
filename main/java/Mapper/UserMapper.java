package Mapper;





import java.util.List;

import domain.*;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
	  

    public User findByusername(String username, String password);

	User findByusernames(String username, String password);

    public void save(String username, String password,String email,String active);
	public Video videoup(String url);
	public void setimg(String text,String name, String name1,String time,String vtime,int playback,String videoid,String classify);
	public List<Video> findvideo();
	public void saveadmin(User user);
	public User findByadmin(String username, String password);
	public List<User> findadmin(String admin);
	public User deleteadmin(Integer id);
	public List<User> updataadmin(Integer id);
	public User updataadmin1(Integer id,String username, String password);
	public User adminexist(String username);
	public void submitmessage(String username, String name, String job, String sex, String sign);
	public List<Person> selectmessage(String username);
	public void imgchange(String imgsetname, String img, String names);
	public void leavemessage(String username, String message,String time, String img,String txt);
	public String findmessageimg(String username);
	public List<Leavemessage> findleavemessage(String txt);
	public Person collect(String username,String collect);
	public List<Person> findcollect(String username);
	public int playback(String text);
	public int updataplayback(String text);
	public String selectvideosrc(String text);
	public List<Video> ordervideo();
	public List<Video> ordervideo1();
	public List<Video> findcollect1(@Param("p1")List<String> p1);
	public void insertnewUser(String username, String name, String job, String sex, String sign, String img,
			String collect);
	public void deleteCollect(String txt);

	Video findvideotext(String text);

	String deletevideosrc(String text);

	String getVideotext(String videoid);

	List<Video> searchvideo(String text);

	String getheadimg(String username);

	User findemail(String email);

	void updataemail(String active,String email);



	List<User> findUser();

	User deleteuser(String id);

	String findmail1(String mail);

	void updatapasswords(String username,String password);

	void repeatmessage(int id,String username, String reusername, String message, String time, String txt, String img,String active);

	List<RepeatMessage> findrepeatmessage();

	void updatavideo(Integer id, String videotext, String classifies, Integer playback);

	void updatavideotime(String id, String vtime);

      List<Leavemessage>  getleavemessage();

	void deleteleavemessage(Integer id);

	List<Person> findcollectall();

	User findemails(String email, String username);

	List<RepeatMessage> messagestotal(String username, String active);


	List<RepeatMessage> findmessagetell(String username);

	void readmessage(String active, String findid);

	String findimg(String imgsetname);
}
