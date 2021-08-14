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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import util.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String title;
    RecyclerView recyclerView;

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

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_video, container, false);
        recyclerView = v.findViewById(R.id.recycleView);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());   //这里是fragment不是activity，得到的是homeactivity
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getVideoList();
    }

    private void getVideoList(){
        String token = getStringFromSp("token");
        if(!StringUtils.isEmpty(token)){
            HashMap<String,Object> params = new HashMap<>();
            params.put("token", "");
            Api.config(ApiConfig.VIDEO_LIST_ALL, params).getRequest(getActivity(), new TtitCallback() {
                @Override
                public void onSuccess(String res) {

                    VideoListResponse response = new Gson().fromJson(res, VideoListResponse.class);
                    if (response!=null && response.getCode()==0){
                        List<VideoEntity> data = response.getPage().getList();
                        VideoAdapter videoAdapter = new VideoAdapter(getActivity(), data);
                        recyclerView.setAdapter(videoAdapter);
                    }
//                    showToastSync(res);
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }else{
            navigateTo(LoginActivity.class);
        }
    }


}