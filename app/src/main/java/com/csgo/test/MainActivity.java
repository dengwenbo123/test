package com.csgo.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,Runnable{

    Handler handler;
    private static final String TAG = "Web";
    SharedPreferences sp;
    String error="未获取到数据";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView image1 = findViewById(R.id.city_add);
        image1.setOnClickListener(this);

        TextView clothIndexTv = findViewById(R.id.frag_index_tv_dress);
        TextView  carIndexTv = findViewById(R.id.frag_index_tv_washcar);
        TextView  coldIndexTv = findViewById(R.id.frag_index_tv_cold);
        TextView sportIndexTv = findViewById(R.id.frag_index_tv_sport);
        TextView raysIndexTv = findViewById(R.id.frag_index_tv_rays);
        TextView umbrellaTv = findViewById(R.id.frag_index_tv_umbrella);

        clothIndexTv.setOnClickListener(this);
        carIndexTv.setOnClickListener(this);
        coldIndexTv.setOnClickListener(this);
        sportIndexTv.setOnClickListener(this);
        raysIndexTv.setOnClickListener(this);
        umbrellaTv.setOnClickListener(this);




         handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {


                }

        };
        Thread t =new Thread(this);
        t.start();
        show();
    }

    //按钮监听
    @Override
    public void onClick(View v) {
        sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        switch (v.getId()) {
            case R.id.city_add:
                Intent intent = new Intent();
                intent.setClass(this, city_add.class);
                startActivity(intent);
                break;
            case R.id.frag_index_tv_dress:
                builder.setTitle("穿衣指数");
                String msg0 = sp.getString("clothes_detail",error);
                String msg_info0=sp.getString("clothes_info",error);
                builder.setMessage(msg_info0+"\n"+msg0);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_washcar:
                builder.setTitle("洗车指数");
                String msg1 = sp.getString("carwash_detail",error);
                String msg_info1=sp.getString("carwash_info",error);
                builder.setMessage(msg_info1+"\n"+msg1);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_cold:
                builder.setTitle("感冒指数");
                String msg2 = sp.getString("cold_detail",error);
                String msg_info2=sp.getString("cold_info",error);
                builder.setMessage(msg_info2+"\n"+msg2);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_sport:
                builder.setTitle("运动指数");
                String msg3 = sp.getString("sports_detail",error);
                String msg_info3=sp.getString("sports_info",error);
                builder.setMessage(msg_info3+"\n"+msg3);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_rays:
                builder.setTitle("紫外线指数");
                String msg4 = sp.getString("ultraviolet_detail",error);
                String msg_info4=sp.getString("ultraviolet_info",error);
                builder.setMessage(msg_info4+"\n"+msg4);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_umbrella:
                builder.setTitle("雨伞指数");
                String msg5 = sp.getString("umbrella_detail",error);
                String msg_info5=sp.getString("umbrella_info",error);
                Log.i("TAG",msg5);
                builder.setMessage(msg_info5+"\n"+msg5);
                builder.setPositiveButton("确定",null);
                break;
        }
        builder.create().show();
    }






//显示数据
public void show(){
       sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();

        String degree_now=sp.getString("degree_now",error);
    Log.i("TAG",degree_now);
     TextView tmp_now=findViewById(R.id.tmp_now);
     tmp_now.setText(degree_now+"℃");

    String st_weather_now=sp.getString("weather_now",error);
    TextView weather_now=findViewById(R.id.weather);
    weather_now.setText(st_weather_now);

    String st_humidity_now=sp.getString("humidity_now",error);
    TextView humidity_now=findViewById(R.id.humidity_now);
    humidity_now.setText(st_humidity_now);

    String st_pressure_now=sp.getString("pressure_now",error);
    TextView pressure_now=findViewById(R.id.pressure_now);
    pressure_now.setText("气压"+st_pressure_now+"hPa");

    String st_city_name=sp.getString("city_name",error);
    TextView city_name=findViewById(R.id.city);
    city_name.setText(findObjectProvince(st_city_name)+" "+st_city_name);

    String st_update_time_now=sp.getString("update_time_now",error);
    TextView update_time_now=findViewById(R.id.update_time_now);
    String year=st_update_time_now.substring(0,4);
    String month=st_update_time_now.substring(4,6);
    String day=st_update_time_now.substring(6,8);
    update_time_now.setText(year+"-"+month+"-"+day);

    String st_tips_now=sp.getString("tips_now",error);
    TextView tips_now=findViewById(R.id.tips_now);
    tips_now.setText(st_tips_now);

    String st_time1=sp.getString("time1",error);
    TextView time1=findViewById(R.id.fu_time1);
    time1.setText(st_time1);

    String st_time2=sp.getString("time2",error);
    TextView time2=findViewById(R.id.fu_time2);
    time2.setText(st_time2);

    String st_time3=sp.getString("time3",error);
    TextView time3=findViewById(R.id.fu_time3);
    time3.setText(st_time3);

    String st_day_weather1=sp.getString("day_weather1",error);
    TextView day_weather1=findViewById(R.id.day_weather1);
    day_weather1.setText(st_day_weather1);

    String st_day_weather2=sp.getString("day_weather2",error);
    TextView day_weather2=findViewById(R.id.day_weather2);
    day_weather2.setText(st_day_weather2);

    String st_day_weather3=sp.getString("day_weather3",error);
    TextView day_weather3=findViewById(R.id.day_weather3);
    day_weather3.setText(st_day_weather3);

    String st_day_wind_direction1=sp.getString("day_wind_direction1",error);
    TextView day_wind_direction1=findViewById(R.id.day_wind_direction1);
    day_wind_direction1.setText(st_day_wind_direction1);

    String st_day_wind_direction2=sp.getString("day_wind_direction2",error);
    TextView day_wind_direction2=findViewById(R.id.day_wind_direction2);
    day_wind_direction2.setText(st_day_wind_direction2);


    String st_day_wind_direction3=sp.getString("day_wind_direction3",error);
    TextView day_wind_direction3=findViewById(R.id.day_wind_direction3);
    day_wind_direction3.setText(st_day_wind_direction3);

    String st_max_degree1=sp.getString("max_degree1",error);
    String st_min_degree1=sp.getString("min_degree1",error);
    TextView max_degree1=findViewById(R.id.degree1);
    max_degree1.setText(st_min_degree1+"~"+st_max_degree1+"℃");

    String st_max_degree2=sp.getString("max_degree2",error);
    String st_min_degree2=sp.getString("min_degree2",error);
    TextView max_degree2=findViewById(R.id.degree2);
    max_degree2.setText(st_min_degree2+"~"+st_max_degree2+"℃");

    String st_max_degree3=sp.getString("max_degree3",error);
    String st_min_degree3=sp.getString("min_degree3",error);
    TextView max_degree3=findViewById(R.id.degree3);
    max_degree3.setText(st_min_degree3+"~"+st_max_degree3+"℃");



}
    public void run(){
        sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);;
        SharedPreferences.Editor editor = sp.edit();
        //将旧的时间放入
        String old_time = sp.getString("time", "0000-00-00");
        Log.i(TAG, "run: old_time=" + old_time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String now_time = format.format(System.currentTimeMillis());
        Log.i(TAG, "run: now_time= " + now_time);
        editor.putString("time", now_time);
        editor.apply();

        String city_name =sp.getString("city_name",error);
        String province_name= findObjectProvince(city_name);
         Log.i(TAG,province_name);


        // if(old_time != now_time){

            HttpURLConnection http = null;
            URL url=null;
            try {
                url = new URL("https://wis.qq.com/weather/common?source=pc&weather_type=observe|index|rise|alarm|air|tips|forecast_24h&province="+province_name+"&city="+city_name);
                http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("GET");
                if (http.getResponseCode() != 200) {
                    throw new RuntimeException("请求url失败");
                }
                InputStream in = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder  response=new StringBuilder();
                Log.i("TAG", "reasult"+reader.toString()+"re");
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                Log.i("TAG", response.toString());
                parseJSONObjectOrJSONArray(response.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

   // }




    public void parseJSONObjectOrJSONArray(String jsonData){
        try {

            sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);;
            SharedPreferences.Editor editor = sp.edit();

            JSONObject jsonObject = new JSONObject(jsonData);
            String data = jsonObject.getString("data");
            JSONObject jsondata = new JSONObject(data);

            String observe0 = jsondata.getString("observe");
            JSONObject observe1 = new JSONObject(observe0);
            String degree_now = observe1.getString("degree"); //现在的温度
            String humidity_now = observe1.getString("humidity"); //现在的湿度
            String pressure_now = observe1.getString("pressure"); //现在的压强
            String weather_now = observe1.getString("weather"); //现在的天气
            String update_time_now = observe1.getString("update_time"); //现在的发布时间
            Log.i("TAG","温度"+degree_now);

            String tips = jsondata.getString("tips");
            JSONObject jsontips = new JSONObject(tips);
            String tips_observe = jsontips.getString("observe");
            JSONObject json_tips_observe = new JSONObject(tips_observe);
            String tips_now=json_tips_observe.getString("1");//当前的tips
            Log.i("TAG",tips_now);

            String forecast_24h = jsondata.getString("forecast_24h");
            JSONObject json_forecast_24h = new JSONObject(forecast_24h);
            String tom1 = json_forecast_24h.getString("2");   //明天
            String tom2 = json_forecast_24h.getString("3");   //后天
            String tom3 = json_forecast_24h.getString("4");   //大后天
            JSONObject json_forecast_24h_tom1 = new JSONObject(tom1);
            JSONObject json_forecast_24h_tom2 = new JSONObject(tom2);
            JSONObject json_forecast_24h_tom3 = new JSONObject(tom3);
            String time1 = json_forecast_24h_tom1.getString("time"); //日期
            String time2 = json_forecast_24h_tom2.getString("time");
            String time3 = json_forecast_24h_tom3.getString("time");
            String day_weather1 = json_forecast_24h_tom1.getString("day_weather"); //天气
            String day_weather2 = json_forecast_24h_tom2.getString("day_weather");
            String day_weather3 = json_forecast_24h_tom3.getString("day_weather");
            String day_wind_direction1 = json_forecast_24h_tom1.getString("day_wind_direction"); //风向
            String day_wind_direction2 = json_forecast_24h_tom2.getString("day_wind_direction");
            String day_wind_direction3 = json_forecast_24h_tom3.getString("day_wind_direction");
            String max_degree1 = json_forecast_24h_tom1.getString("max_degree");  //最高温
            String max_degree2= json_forecast_24h_tom2.getString("max_degree");
            String max_degree3 = json_forecast_24h_tom3.getString("max_degree");
            String min_degree1 = json_forecast_24h_tom1.getString("min_degree");   //最低温
            String min_degree2 = json_forecast_24h_tom2.getString("min_degree");
            String min_degree3 = json_forecast_24h_tom3.getString("min_degree");


            String index = jsondata.getString("index");  //天气指数
            JSONObject json_index= new JSONObject(index);
            String clothes = json_index.getString("clothes");
            String carwash = json_index.getString("carwash");
            String cold = json_index.getString("cold");
            String sports = json_index.getString("sports");
            String ultraviolet = json_index.getString("ultraviolet");
            String umbrella = json_index.getString("umbrella");
            JSONObject json_clothes= new JSONObject(clothes);
            JSONObject json_carwash= new JSONObject(carwash);
            JSONObject json_cold= new JSONObject(cold);
            JSONObject json_sports= new JSONObject(sports);
            JSONObject json_ultraviolet= new JSONObject(ultraviolet);
            JSONObject json_umbrella= new JSONObject(umbrella);
            String clothes_detail =json_clothes.getString("detail");
            String carwash_detail =json_carwash.getString("detail");
            String cold_detail =json_cold.getString("detail");
            String sports_detail =json_sports.getString("detail");
            String ultraviolet_detail =json_ultraviolet.getString("detail");
            String umbrella_detail =json_umbrella.getString("detail");

            String umbrella_info =json_umbrella.getString("info");
            String clothes_info =json_clothes.getString("info");
            String carwash_info =json_carwash.getString("info");
            String cold_info =json_cold.getString("info");
            String sports_info =json_sports.getString("info");
            String ultraviolet_info =json_ultraviolet.getString("info");



            editor.putString("degree_now",degree_now);
            editor.putString("humidity_now",humidity_now);
            editor.putString("pressure_now",pressure_now);
            editor.putString("weather_now",weather_now);
            editor.putString("update_time_now",update_time_now);
            editor.putString("tips_now",tips_now);
            editor.putString("time1",time1);
            editor.putString("time2",time2);
            editor.putString("time3",time3);
            editor.putString("day_weather1",day_weather1);
            editor.putString("day_weather2",day_weather2);
            editor.putString("day_weather3",day_weather3);
            editor.putString("day_wind_direction1",day_wind_direction1);
            editor.putString("day_wind_direction2",day_wind_direction2);
            editor.putString("day_wind_direction3",day_wind_direction3);
            editor.putString("max_degree1",max_degree1);
            editor.putString("max_degree2",max_degree2);
            editor.putString("max_degree3",max_degree3);
            editor.putString("min_degree1",min_degree1);
            editor.putString("min_degree2",min_degree2);
            editor.putString("min_degree3",min_degree3);
            editor.putString("clothes_detail",clothes_detail);
            editor.putString("carwash_detail",carwash_detail);
            editor.putString("cold_detail",cold_detail);
            editor.putString("sports_detail",sports_detail);
            editor.putString("ultraviolet_detail",ultraviolet_detail);
            editor.putString("umbrella_detail",umbrella_detail);

            editor.putString("clothes_info",clothes_info);
            editor.putString("carwash_info",carwash_info);
            editor.putString("cold_info",cold_info);
            editor.putString("sports_info",sports_info);
            editor.putString("ultraviolet_info",ultraviolet_info);
            editor.putString("umbrella_info",umbrella_info);
            editor.apply();
            Log.i("TAG",sp.getString("degree_now",error));





        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


//    添加城市
    public static List<String[]> allCity() {

        List<String[]> allCityList = new ArrayList<>();

        allCityList.add(new String[]{"北京"});
        allCityList.add(new String[]{"上海"});
        allCityList.add(new String[]{"天津"});
        allCityList.add(new String[]{"重庆"});


        allCityList.add(new String[]{"哈尔滨", "齐齐哈尔", "牡丹江", "大庆", "伊春", "双鸭山", "鹤岗", "鸡西", "佳木斯", "七台河", "黑河", "绥化", "大兴安岭"});
        allCityList.add(new String[]{"长春", "延边", "吉林", "白山", "白城", "四平", "松原", "辽源", "大安", "通化"});
        allCityList.add(new String[]{"沈阳", "大连", "葫芦岛", "旅顺", "本溪", "抚顺", "铁岭", "辽阳", "营口", "阜新", "朝阳", "锦州", "丹东", "鞍山"});
        allCityList.add(new String[]{"呼和浩特", "呼伦贝尔", "锡林浩特", "包头", "赤峰", "海拉尔", "乌海", "鄂尔多斯", "通辽"});

        allCityList.add(new String[]{"石家庄", "唐山", "张家口", "廊坊", "邢台", "邯郸", "沧州", "衡水", "承德", "保定", "秦皇岛"});
        allCityList.add(new String[]{"郑州", "开封", "洛阳", "平顶山", "焦作", "鹤壁", "新乡", "安阳", "濮阳", "许昌", "漯河", "三门峡", "南阳", "商丘", "信阳", "周口", "驻马店"});
        allCityList.add(new String[]{"济南", "青岛", "淄博", "威海", "曲阜", "临沂", "烟台", "枣庄", "聊城", "济宁", "菏泽", "泰安", "日照", "东营", "德州", "滨州", "莱芜", "潍坊"});
        allCityList.add(new String[]{"太原", "阳泉", "晋城", "晋中", "临汾", "运城", "长治", "朔州", "忻州", "大同", "吕梁"});

        allCityList.add(new String[]{"南京", "苏州", "昆山", "南通", "太仓", "吴县", "徐州", "宜兴", "镇江", "淮安", "常熟", "盐城", "泰州", "无锡", "连云港", "扬州", "常州", "宿迁"});
        allCityList.add(new String[]{"合肥", "巢湖", "蚌埠", "安庆", "六安", "滁州", "马鞍山", "阜阳", "宣城", "铜陵", "淮北", "芜湖", "毫州", "宿州", "淮南", "池州"});
        allCityList.add(new String[]{"西安", "韩城", "安康", "汉中", "宝鸡", "咸阳", "榆林", "渭南", "商洛", "铜川", "延安"});
        allCityList.add(new String[]{"银川", "固原", "中卫", "石嘴山", "吴忠"});

        allCityList.add(new String[]{"兰州", "白银", "庆阳", "酒泉", "天水", "武威", "张掖", "甘南", "临夏", "平凉", "定西", "金昌"});
        allCityList.add(new String[]{"西宁", "海北", "海西", "黄南", "果洛", "玉树", "海东", "海南"});
        allCityList.add(new String[]{"武汉", "宜昌", "黄冈", "恩施", "荆州", "神农架", "十堰", "咸宁", "襄樊", "孝感", "随州", "黄石", "荆门", "鄂州"});
        allCityList.add(new String[]{"长沙", "邵阳", "常德", "郴州", "吉首", "株洲", "娄底", "湘潭", "益阳", "永州", "岳阳", "衡阳", "怀化", "韶山", "张家界"});

        allCityList.add(new String[]{"杭州", "湖州", "金华", "宁波", "丽水", "绍兴", "雁荡山", "衢州", "嘉兴", "台州", "舟山", "温州"});
        allCityList.add(new String[]{"南昌", "萍乡", "九江", "上饶", "抚州", "吉安", "鹰潭", "宜春", "新余", "景德镇", "赣州"});
        allCityList.add(new String[]{"福州", "厦门", "龙岩", "南平", "宁德", "莆田", "泉州", "三明", "漳州"});
        allCityList.add(new String[]{"贵阳", "安顺", "赤水", "遵义", "铜仁", "六盘水", "毕节", "凯里", "都匀"});

        allCityList.add(new String[]{"成都", "泸州", "内江", "凉山", "阿坝", "巴中", "广元", "乐山", "绵阳", "德阳", "攀枝花", "雅安", "宜宾", "自贡", "甘孜州", "达州", "资阳", "广安", "遂宁", "眉山", "南充"});
        allCityList.add(new String[]{"广州", "深圳", "潮州", "韶关", "湛江", "惠州", "清远", "东莞", "江门", "茂名", "肇庆", "汕尾", "河源", "揭阳", "梅州", "中山", "德庆", "阳江", "云浮", "珠海", "汕头", "佛山"});
        allCityList.add(new String[]{"南宁", "桂林", "阳朔", "柳州", "梧州", "玉林", "桂平", "贺州", "钦州", "贵港", "防城港", "百色", "北海", "河池", "来宾", "崇左"});
        allCityList.add(new String[]{"昆明", "保山", "楚雄", "德宏", "红河", "临沧", "怒江", "曲靖", "思茅", "文山", "玉溪", "昭通", "丽江", "大理"});

        allCityList.add(new String[]{"海口", "三亚", "儋州", "琼山", "通什", "文昌"});
        allCityList.add(new String[]{"乌鲁木齐", "阿勒泰", "阿克苏", "昌吉", "哈密", "和田", "喀什", "克拉玛依", "石河子", "塔城", "库尔勒", "吐鲁番", "伊宁"});

        allCityList.add(new String[]{"拉萨","昌都地区","山南地区","阿里地区","那曲地区","林芝地区","日喀则地区"});
        allCityList.add(new String[]{"香港"});
        allCityList.add(new String[]{"澳门"});
        allCityList.add(new String[]{"台湾"});


        return allCityList;
    }
//    //添加省份
    public static List<String> provinceList() {
        List<String> list = new ArrayList<>();

        list.add("北京");
        list.add("上海");
        list.add("天津");
        list.add("重庆");

        list.add("黑龙江");
        list.add("吉林");
        list.add("辽宁");
        list.add("内蒙古");

        list.add("河北");
        list.add("河南");
        list.add("山东");
        list.add("山西");

        list.add("江苏");
        list.add("安徽");
        list.add("陕西");
        list.add("宁夏");

        list.add("甘肃");
        list.add("青海");
        list.add("湖北");
        list.add("湖南");

        list.add("浙江");
        list.add("江西");
        list.add("福建");
        list.add("贵州");

        list.add("四川");
        list.add("广东");
        list.add("广西");
        list.add("云南");

        list.add("海南");
        list.add("新疆");
        list.add("西藏");

        list.add("香港");
        list.add("澳门");
        list.add("台湾");
        return list;
    }

    public static String findObjectProvince(String cityName) {
        if (cityName.contains("市")) {
            int index = cityName.indexOf("市");
            cityName = cityName.substring(0, index);

        }
        for (int i = 0; i < allCity().size(); i++) {
            for (int j = 0; j < allCity().get(i).length; j++) {
                if (allCity().get(i)[j].equals(cityName)) {
                    String provinceName = provinceList().get(i);
                    return provinceName;
                }
            }

        }
        return "查询失败";
    }








}



