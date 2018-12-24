package controller;

import com.qcloud.Module.Vod;
import com.qcloud.QcloudApiModuleCenter;
import com.qcloud.Utilities.Json.JSONObject;
import java.util.TreeMap;

public class MovieController {


    public static String shanchu(String fileId)
    {

        TreeMap<String, Object> config = new TreeMap();
        config.put("SecretId", "AKIDa4OzSjCv42oRODkAYr0ykKiz77wbuPXY");
        config.put("SecretKey", "yIHyaHrFUXOzlQwsgJl02KZcUnwtkSyJ");
        config.put("RequestMethod", "GET");
        config.put("DefaultRegion", "gz");
        QcloudApiModuleCenter module = new QcloudApiModuleCenter(new Vod(), config);
        TreeMap<String, Object> params = new TreeMap();
        params.put("fileId", fileId);
        params.put("priority", Integer.valueOf(0));

        String result = null;
        try
        {
            result = module.call("DeleteVodFile", params);
            JSONObject json_result = new JSONObject(result);
            System.out.println(json_result);
            return "yes";
        }
        catch (Exception e)
        {
            System.out.println("error..." + e.getMessage());
        }
        return null;
    }
    public static String shichang(String fileId)
    {
        TreeMap<String, Object> config = new TreeMap();
        config.put("SecretId", "AKIDa4OzSjCv42oRODkAYr0ykKiz77wbuPXY");
        config.put("SecretKey", "yIHyaHrFUXOzlQwsgJl02KZcUnwtkSyJ");

        config.put("RequestMethod", "GET");

        config.put("DefaultRegion", "gz");
        QcloudApiModuleCenter module = new QcloudApiModuleCenter(new Vod(), config);
        TreeMap<String, Object> params = new TreeMap();
        params.put("fileId", fileId);


        String result = null;
        try
        {
            result = module.call("GetVideoInfo", params);
            JSONObject json_result = new JSONObject(result);
           String z= json_result.get("metaData").toString().split("duration\":")[1].split(",")[0];
            int a = Integer.parseInt(z);
            int minute = a / 60;
            int second = a % 60;
          String shichang = "";
            if(minute<10 && second>9){
                shichang="0"+minute+":"+second;
            }
            if(minute<10 && second<10){
                shichang="0"+minute+":"+"0"+second;
            }
            if(minute>9 && second<10){
                shichang=minute+":"+"0"+second;
            }
            if(minute>9 && second>10){
                shichang=minute+":"+second;
            }
            return shichang;
        }
        catch (Exception e)
        {
            System.out.println("error..." + e.getMessage());
        }
        return result;
    }

}
