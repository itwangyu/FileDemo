package com.xtoolapp.file.filedemo.fastjson;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by WangYu on 2018/8/31.
 */
public class FastJsonActivity extends AppCompatActivity {

    String strJson = "{\n" +
            "    \"code\": 1,\n" +
            "    \"msg\": \"success\",\n" +
            "    \"out_scene\": {\n" +
            "        \"call\": {\n" +
            "            \"enable\": true,\n" +
            "            \"time\": 2\n" +
            "        },\n" +
            "        \"cool\": {\n" +
            "            \"enable\": true,\n" +
            "            \"temp\": 37\n" +
            "        }\n" +
            "    }\n" +
            "}";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(strJson);
            Log.i("wangyu", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonBean vo = JSON.parseObject(strJson, CommonBean.class); //反序列化
        Log.i("wangyu", vo.out_scene.get("call").toString());
    }

    public static class CommonBean implements Serializable{
        public int code;
        public String msg;
        public Map<String,SceneConfig> out_scene;
    }

    public static class SceneConfig implements Serializable{
        public boolean enable;
        public int time;
        public int temp;

        @Override
        public String toString() {
            return "SceneConfig{" +
                    "enable=" + enable +
                    ", time=" + time +
                    ", temp=" + temp +
                    '}';
        }
    }
}
