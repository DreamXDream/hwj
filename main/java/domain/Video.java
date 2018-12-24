package domain;


public class Video implements Comparable<Video>{
	int id;
	String text;
	String murl;
	String vurl;
	String time;
	String vtime;
	int playback;
	String videoid;

	public String getVideoid() {
		return videoid;
	}

	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	String classify;
	public int getPlayback() {
		return playback;
	}
	public void setPlayback(int playback) {
		this.playback = playback;
	}
	public String getVtime() {
		return vtime;
	}
	public void setVtime(String vtime) {
		this.vtime = vtime;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getMurl() {
		return murl;
	}
	public void setMurl(String murl) {
		this.murl = murl;
	}
	public String getVurl() {
		return vurl;
	}
	public void setVutl(String vurl) {
		this.vurl = vurl;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

//重写Video方法  来排序播放量
	@Override
	public int compareTo(Video o) {
		if(o.playback>this.playback){
			return 1;
		}else if(o.playback>this.playback){
			return 0;
		}else{
			return -1;
		}
	}
}
