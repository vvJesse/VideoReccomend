package com.example.videoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.api.Api;
import com.example.api.ApiConfig;
import com.example.api.TtitCallback;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import util.AppConfig;
import util.StringUtils;

public class RegisterActivity extends BaseActivity {

    private Button btnRegis;
    private EditText etAccount;
    private EditText etPwd;
    private Button returnBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        btnRegis = findViewById(R.id.btn_regis2);
        etAccount = findViewById(R.id.regis_id);
        etPwd = findViewById(R.id.regis_password);
        returnBox = findViewById(R.id.returnBox2);
    }

    @Override
    protected void initData() {
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = etAccount.getText().toString().trim();
                String password = etPwd.getText().toString().trim();
                register(account, password);
            }
        });
        returnBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent in = new Intent(RegisterActivity.this, MainActivity.class);
//                startActivity(in);
                navigateTo(MainActivity.class);
            }
        });
    }

//    private void register(String account, String password)
//    {
//        if(account == null || password == null)
//        {
//            return;
//        }
//        Data data = Data.getInstance();
//        data.register(account, password);
//        Intent in = new Intent(RegisterActivity.this, MainActivity.class);
//        startActivity(in);
//    }

//    private void register(String account, String pwd) {
//        if (StringUtils.isEmpty(account)) {
//            showToast("请输入账号");
//            return;
//        }
//        if (StringUtils.isEmpty(pwd)) {
//            showToast("请输入密码");
//            return;
//        }
//        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("mobile", account);
//        params.put("password", pwd);
//        Api.config(ApiConfig.REGISTER, params).getRequest(this,new TtitCallback() {
//            @Override
//            public void onSuccess(final String res) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast(res);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                Log.e("onFailure", e.toString());
//            }
//        });
//    }

    private void register(String account, String pwd) {
        if (StringUtils.isEmpty(account)) {
            showToast("请输入账号");
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            showToast("请输入密码");
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", account);
        params.put("password", pwd);
        Api.config(ApiConfig.REGISTER, params).postRequest(this,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(res);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("onFailure", e.toString());
            }
        });
    }

//    private void register(String account, String pwd) {
//        if (StringUtils.isEmpty(account)) {
//            showToast("请输入账号");
//            return;
//        }
//        if (StringUtils.isEmpty(pwd)) {
//            showToast("请输入密码");
//            return;
//        }
//
//        //第一步创建OKHttpClient
//        OkHttpClient client = new OkHttpClient.Builder()
//                .build();
//        Map m = new HashMap();
//        m.put("mobile", account);
//        m.put("password", pwd);
//        JSONObject jsonObject = new JSONObject(m);
//        String jsonStr = jsonObject.toString();
//        RequestBody requestBodyJson =
//                RequestBody.create(MediaType.parse("application/json;charset=utf-8")
//                        , jsonStr);
//        //第三步创建Rquest
//        Request request = new Request.Builder()
//                .url(AppConfig.BASE_URl + "/app/register")
//                .addHeader("contentType", "application/json;charset=UTF-8")
//                .post(requestBodyJson)
//                .build();
//        //第四步创建call回调对象
//        final Call call = client.newCall(request);
//        //第五步发起请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("onFailure", e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String result = response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast(result);
//                    }
//                });
//            }
//        });
//    }
}