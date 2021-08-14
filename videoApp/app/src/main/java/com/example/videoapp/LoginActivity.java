package com.example.videoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import android.content.Intent;

import com.example.api.Api;
import com.example.api.ApiConfig;
import com.example.api.TtitCallback;
import com.example.entity.LoginResponse;
import com.google.gson.Gson;

public class LoginActivity extends BaseActivity {
    private Button btnLogin;
    private EditText etAccount;
    private EditText etPwd;
    private Button returnBox;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        initView();
//        initData();
//    }

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        btnLogin = findViewById(R.id.btn_login2);
        etAccount = findViewById(R.id.et_acc);
        returnBox = findViewById(R.id.returnBox);
        etPwd = findViewById(R.id.et_pass);
    }

    @Override
    protected void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = etAccount.getText().toString().trim();
                String password = etPwd.getText().toString().trim();
                login(account, password);
            }
        });
        returnBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent in = new Intent(LoginActivity.this, MainActivity.class);
                navigateTo(MainActivity.class);
                //tartActivity(in);
            }
        });
    }

//    private void login(String account, String password) {
//        Data data = Data.getInstance();
//        if(data.login(account, password))
//        {
//            Intent homePage = new Intent(LoginActivity.this, HomeActivity.class);
//            startActivity(homePage);
//        }
//
//    }

//    private void login(String account, String pwd){
//        if (StringUtils.isEmpty(account)) {
//            showToast("请输入账号");
//            return;
//        }
//        if (StringUtils.isEmpty(pwd)) {
//            showToast("请输入密码");
//            return;
//        }
//        HashMap<String, Object> m = new HashMap<String, Object>();
//        m.put("mobile", account);
//        m.put("password", pwd);
//        Api.config("app/login", m).postRequest(this, new TtitCallback() {
//            @Override
//            public void onSuccess(String res) {
//                Log.e("onSuccess", res);
////                Gson gson = new Gson();
////                String userJson = gson.toJson(res, LoginResponse.class);
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        showToast(res);
////                    }
////                });
//                showToastSync(res);
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        });
//    }

    private void login(String account, String pwd) {
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
        Api.config(ApiConfig.LOGIN, params).postRequest(this,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("onSuccess", res);
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(res, LoginResponse.class);
                if (loginResponse.getCode() == 0) {
                    String token = loginResponse.getToken();
                    insertVal("token", token);
                    Intent in = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(in);
                    showToastSync(res);
                } else {
                    showToastSync("登录失败");
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }


//        private void login(String account, String pwd) {
//            if (StringUtils.isEmpty(account)) {
//                showToast("请输入账号");
//                return;
//            }
//            if (StringUtils.isEmpty(pwd)) {
//                showToast("请输入密码");
//                return;
//            }
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
//                .url(AppConfig.BASE_URl + "/app/login")
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