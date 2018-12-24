package domain;


public class Video1 implements Comparable<Video1>{
    int id;
    String text;
    String murl;
    String vurl;
    String time;
    String vtime;
    int playback;
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



    @Override
    public int compareTo(Video1 o) {
        if(o.playback>this.playback){
            return 1;
        }else if(o.playback>this.playback){
            return 0;
        }else{
            return -1;
        }
    }
}
