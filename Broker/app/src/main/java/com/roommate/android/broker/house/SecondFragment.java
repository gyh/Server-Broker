package com.roommate.android.broker.house;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.roommate.android.broker.R;
import com.roommate.android.broker.common.ImagePathUtils;
import com.roommate.android.broker.common.core.BaseActivity;
import com.roommate.android.broker.common.view.SmoothListView.NextpageLoader;
import com.roommate.android.broker.common.view.SmoothListView.SmoothListView;
import com.roommate.android.broker.house.data.House;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by GYH on 2015/8/11.
 */
public class SecondFragment extends Fragment{

    private SmoothListView smoothListView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    //实例化碎片
    public static SecondFragment instance() {
        SecondFragment view = new SecondFragment();
        return view;
    }
    private NextpageLoader nextpageLoader = null;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.second_fragment, null);
        smoothListView = (SmoothListView)view.findViewById(R.id.listview);
        //输入数据
        //http://search.tootoo.cn/searchMB/goods?scope=11202&channelType=2&pageno=1&geoId=1,2,3,0&yz=39d16330300072bce2e7dab87a5d85f0&brandIds=&catIds=14844&specialIds=&pagesize=10&promotionIds=
        HashMap<String,Object> requestParams = new HashMap<>();
        requestParams.put("channelType","2");
        requestParams.put("scope","11202");
        requestParams.put("geoId","1,2,3,0");
        requestParams.put("yz","39d16330300072bce2e7dab87a5d85f0");
        requestParams.put("key","水果");

        nextpageLoader = new NextpageLoader(smoothListView,(BaseActivity) getActivity(),requestParams,"http://search.tootoo.cn/searchMB/goods") {
            @Override
            protected void showError() {

            }

            @Override
            protected ArrayList<?> toList(String result) {
                ArrayList<House> strings = new ArrayList<>();
                JsonParser jsonParser = new JsonParser();
                JsonObject resultObj = jsonParser.parse(result).getAsJsonObject().get("result").getAsJsonObject();
                JsonArray rows = resultObj.get("rows").getAsJsonArray();
                for(int i=0;i<rows.size();i++){
                    JsonObject row = rows.get(i).getAsJsonObject();
                    House house = new House();
                    house.title = row.get("goodsName").getAsString();
                    house.picPath = ImagePathUtils.getSmallUrl("http://img01.ttmimg.com/" + row.get("picPath").getAsString());
                    strings.add(house);
                }
                return strings;
            }

            @Override
            protected View getItemView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if(convertView ==null){
                    viewHolder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.item_house_res,null);
                    TextView textView = (TextView) convertView.findViewById(R.id.tv_title);
                    ImageView imageView = (ImageView)convertView.findViewById(R.id.img_house);
                    viewHolder.imageView = imageView;
                    viewHolder.tvTitle = textView;
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                House house = (House) getItem(position);
                viewHolder.tvTitle.setText(house.title);
                x.image().bind(viewHolder.imageView, house.picPath);
                return convertView;
            }
        };

        smoothListView.setRefreshEnable(false);
        smoothListView.setLoadMoreEnable(true);

        nextpageLoader.setPageNumParamKey("pageno");
        nextpageLoader.setPageSizeParamKey("pagesize");
        nextpageLoader.setStartNum(1);

        nextpageLoader.showPageOne();

        return view;
    }

    /**
     * The Class ViewHolder.
     */
    static class ViewHolder {


        /** The image view. */
        ImageView imageView;


        /** The tv title. */
        TextView tvTitle;

    }

    class MyAdatper extends BaseAdapter {

        private LayoutInflater inflater ;

        MyAdatper(){
            inflater =  LayoutInflater.from(getContext());
        }

        @Override
        public int getCount() {
            return nextpageLoader.getItemCount();
        }

        @Override
        public Object getItem(int position) {
            return nextpageLoader.getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView ==null){
                convertView = inflater.inflate(R.layout.item_house_res,null);
            }
            return convertView;
        }
    }
}