package com.canka.bootstepview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.canka.bootstepview.util.BootStepUtils;

public class AddBankVerificationActivity extends AppCompatActivity {
    private static final String TAG="BinkVerification";
    private Context mContext;
    private Toolbar mToolbar;
    private TextView mTextViewPhone;
    private EditText mEditTextVerificationCode;
    private TextView mTextViewVerificationCode;
    private Button mButtonNext;
    private static final int CODE_ONE = -1;
    private static final int CODE_TWO = -2;
    private static String code = "";
    private int i = 60;
    private Bundle bundle=null;
    private String phone="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_verification_code);
        mContext=this;
        bundle=getIntent().getExtras();
        phone=bundle!=null?bundle.getString(BootStepUtils.PHONE_KEY):"";
        initView();
        initData();
        initListener();
    }
    private void initView(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar_bink_verification_code);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.community_main_details_back);
        mEditTextVerificationCode= (EditText) findViewById(R.id.et_bink_verification_code);
        mTextViewPhone= (TextView) findViewById(R.id.tv_bink_verification_phone);
        mTextViewVerificationCode= (TextView) findViewById(R.id.tv_bink_verification_code);
        mButtonNext= (Button) findViewById(R.id.btn_bink_type_next);

    }

    private void initData(){
        mTextViewPhone.setText(phone);
    }

    private void initListener(){
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEditTextVerificationCode.addTextChangedListener(new TextChangedListener());
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCanRegister()){
                   BootStepUtils.intentToActivityClasss(AddBankVerificationActivity.this,AddBankActivity.class,null);
                }
            }
        });
        mTextViewVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVerificationCode();
            }
        });
    }

    private class TextChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String bankPhone=mEditTextVerificationCode.getText().toString();
            boolean isEmpty = bankPhone.length() > 0 ;
            if (isEmpty) {
                mButtonNext.setEnabled(true);
                mButtonNext.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }else{
                mButtonNext.setEnabled(false);
                mButtonNext.setBackgroundColor(getResources().getColor(R.color.title_text_color_night));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private Handler mHandler=new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case CODE_ONE:
                    mTextViewVerificationCode.setText(i+"秒后重发");
                    break;
                case CODE_TWO:
                    mTextViewVerificationCode.setText("重新发送");
                    mTextViewVerificationCode.setClickable(true);
                    i = 60;
                    break;
            }
        }
    };

    /**
     * 判断 输入的验证码 与获取的验证码是否相等
     * @return
     */
    private boolean isCanRegister() {
//        if (code == null || code.length() <= 0) {
//            Toast.makeText(getApplicationContext(), "请先获取验证码！",Toast.LENGTH_SHORT).show();
//            return false;
//        }
        if (getCoding().isEmpty()) {
            mEditTextVerificationCode.setError(Html
                    .fromHtml("<font color=#E10979>请输入验证码！</font>"));
            return false;
        }
//        if (!code.equals(getCoding())) {
//            mEditTextVerificationCode.setError(Html.fromHtml("<font color=#E10979>验证码错误！</font>"));
//            return false;
//        }
        return true;
    }

    /**
     * 获取输入的验证码
     * @return
     */
    private String getCoding() {
        return mEditTextVerificationCode.getText().toString();
    }

    /**
     * 重新发送
     */
    private void getVerificationCode() {
        mTextViewVerificationCode.setClickable(false);
        mTextViewVerificationCode.setText(i+"秒后重发");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; i > 0; i--) {
                    mHandler.sendEmptyMessage(CODE_ONE);
                    if (i <= 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mHandler.sendEmptyMessage(CODE_TWO);
            }
        }).start();
    }
}
