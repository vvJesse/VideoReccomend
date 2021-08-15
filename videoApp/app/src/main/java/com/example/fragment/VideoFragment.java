package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.VideoAdapter;
import com.example.api.Api;
import com.example.api.ApiConfig;
import com.example.api.TtitCallback;
import com.example.entity.VideoEntity;
import com.example.entity.VideoListResponse;
import com.example.videoapp.LoginActivity;
import com.example.videoapp.R;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import util.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String title;
    RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    VideoAdapter videoAdapter;
    List<VideoEntity> list = new ArrayList<>();
    private int pagenum = 1;

    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1) {
        VideoFragment fragment = new VideoFragment();
        fragment.title = param1;
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView() {
        recyclerView = mRootView.findViewById(R.id.recycleView);
        refreshLayout = mRootView.findViewById(R.id.refresh);
    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());   //这里是fragment不是activity，得到的是homeactivity
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        videoAdapter = new VideoAdapter(getActivity());
        recyclerView.setAdapter(videoAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                pagenum = 1;
                getVideoList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                //refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                pagenum++;
                getVideoList(false);
            }
        });
        getVideoList(true);
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
//    }

    private void getVideoList(boolean isRefresh){
        String token = getStringFromSp("token");
        if(!StringUtils.isEmpty(token)){
            Map<String, Object> params = new LinkedHashMap<>();
            //HashMap<String,Object> params = new HashMap<>();
            params.put("token", token);
            params.put("page", pagenum);
            params.put("limit", ApiConfig.PAGE_SIZE);
            Api.config(ApiConfig.VIDEO_LIST, params).getRequest(getActivity(), new TtitCallback() {
                @Override
                public void onSuccess(String res) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(isRefresh){
                                refreshLayout.finishRefresh(true);
                            }else{
                                refreshLayout.finishLoadMore(true);
                            }
                            VideoListResponse response = new Gson().fromJson(res, VideoListResponse.class);
                            if (response!=null && response.getCode()==0){
                                List<VideoEntity> data = response.getPage().getList();
                                if(data!=null && data.size() > 0){
                                    if(isRefresh){
                                        list = data;
                                    }else {
                                        list.addAll(data);
                                    }
                                    videoAdapter.setData(list);
                                    videoAdapter.notifyDataSetChanged();
                                }else {
                                    if(isRefresh){
                                        showToast("暂时无数据加载");
                                    }else{
                                        showToast("没有更多数据了");
                                    }
                                }
                            }
                        }
                    });

//                    showToastSync(res);
                }

                @Override
                public void onFailure(Exception e) {
                    if(isRefresh){
                        refreshLayout.finishRefresh(true);
                    }else {
                        refreshLayout.finishLoadMore(true);
                    }
                }
            });
        }else{
            navigateTo(LoginActivity.class);
        }
    }


}