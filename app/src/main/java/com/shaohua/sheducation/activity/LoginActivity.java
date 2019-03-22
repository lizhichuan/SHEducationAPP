package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.njcool.lzccommon.app.App;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolRegexUtil;
import com.njcool.lzccommon.utils.CoolSPUtil;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.request.LoginBody;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.common.UserPref;
import com.shaohua.sheducation.resultbean.LoginResultVO;
import com.shaohua.sheducation.resultbean.UserVO;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends SHBaseActivity {


    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_pass)
    EditText etPass;
    @Bind(R.id.tv_forget)
    TextView tvForget;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.img_qq)
    ImageView imgQq;
    @Bind(R.id.img_weichat)
    ImageView imgWeichat;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.img_close)
    ImageView imgClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        App.getInstance().addActivity(this);
        ButterKnife.bind(this);
        findViews();

    }


    private void findViews() {

    }

    @Subscribe
    public void onEventMainThread(UserVO loginVO) {


    }


    @OnClick({R.id.tv_forget, R.id.tv_login, R.id.img_qq,
            R.id.img_weichat, R.id.tv_register, R.id.img_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget:
                startActivity(ForgetPassActivity.class);
                break;
            case R.id.tv_login:
                if (!CoolRegexUtil.isMobileNO(etPhone.getText().toString())) {
                    CoolPublicMethod.Toast(LoginActivity.this, "手机号码格式不正确，请检查");
                    return;
                }
                if (!CoolRegexUtil.isPassword(etPass.getText().toString())) {
                    CoolPublicMethod.Toast(LoginActivity.this, "密码格式不正确，应该为6-18位字母数字组合");
                    return;
                }
                LoginBody loginRequest = new LoginBody();
                loginRequest.setMobile(etPhone.getText().toString());
                loginRequest.setPassword(etPass.getText().toString());
                Login(LoginActivity.this, loginRequest);
                break;
            case R.id.img_qq:
                startActivity(BingAccountActivity.class);
                break;
            case R.id.img_weichat:
                startActivity(BingAccountActivity.class);
                break;
            case R.id.tv_register:
                startActivity(RegisterActivity.class);
                finish();
                break;
            case R.id.img_close:
                finish();
                break;
        }
    }

    /**
     * 注册
     *
     * @param context
     * @param body
     */
    public void Login(final Context context, LoginBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<LoginResultVO> call = CoolHttpUrl.getApi().getService().Login(body);
            call.enqueue(new Callback<LoginResultVO>() {
                @Override
                public void onResponse(Call<LoginResultVO> call, Response<LoginResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        LoginResultVO result = response.body();
                        if (result.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            UserPref.saveUserInfo(result.getResult());
                            CoolSPUtil.insertDataToLoacl(LoginActivity.this, "uid", result.getResult().getId() + "");
                            startActivity(ChooseLikeTagActivity.class);
                            finish();
                        } else {
                            SHHttpUtil.resultCode(context, response.code(), response.message());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<LoginResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }

    }
}
