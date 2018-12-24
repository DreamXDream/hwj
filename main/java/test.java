import java.io.File;
import java.io.IOException;


public class test {
    public static void main(String[] args) {
       String  path="D:\\未来软件园_百度搜索.url";
        File file = new File(path);//文件路径
        file.delete();

    }
}