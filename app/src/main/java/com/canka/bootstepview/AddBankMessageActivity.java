package com.canka.bootstepview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.canka.bootstepview.util.BootStepUtils;


public class AddBankMessageActivity extends AppCompatActivity{
    private static final String TAG="BinkMessageActivity";
    private Context mContext;
    private Toolbar mToolbar;
    private EditText mEditTextBankPhone;
    private TextView mTextViewBankType;
    private Button mButtonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_type);
        mContext=this;
        initView();
        initData();
        initListener();
    }

    private void initView(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar_bink_type);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.community_main_details_back);
        mEditTextBankPhone= (EditText) findViewById(R.id.et_bink_type_phone);
        mTextViewBankType= (TextView) findViewById(R.id.tv_bink_type_name);
        mButtonNext= (Button) findViewById(R.id.btn_bink_type_next);

    }

    private void initData(){
        mTextViewBankType.setText(R.string.bank_name);
    }

    private void initListener(){
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEditTextBankPhone.addTextChangedListener(new TextChangedListener());
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString(BootStepUtils.PHONE_KEY,phoneTextString());
                BootStepUtils.intentToActivityClasss(AddBankMessageActivity.this,
                        AddBankVerificationActivity.class,bundle);
            }
        });
    }

    private String phoneTextString(){
        return mEditTextBankPhone.getText().toString();
    }

    private class TextChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String bankPhone=phoneTextString();
            boolean isEmpty = bankPhone.length() > 0 ;
            if (isEmpty && BootStepUtils.checkMobile(bankPhone)) {
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

}
