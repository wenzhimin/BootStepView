package com.canka.bootstepview.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.regex.Pattern;

/**
 * Created by ye on 2017/8/4.
 */

public class BootStepUtils {
    public static final String PHONE_KEY="phone";
    /**
     * 中文名
     *
     * @param name
     *            只能是中文，长度为2-7位
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean IsChinese(String name) {
        // String regex = "/^([\u4e00-\u9fa5]){2,7}$/";
        String regex = "^[\u4e00-\u9fa5]{2,7}";
        return Pattern.matches(regex, name);
    }

    /** 银行卡19位 */
    public static boolean IsBankCard19(String card) {
        String regex = "^\\d{19}$";
        return Pattern.matches(regex, card);
    }

    /** 银行卡16位 */
    public static boolean IsBankCard16(String card) {
        String regex = "^\\d{16}$";
        return Pattern.matches(regex, card);
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param mobile
     *            移动、联通、电信运营商的号码段
     *            <p>
     *            移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *            、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
     *            </p>
     *            <p>
     *            联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
     *            </p>
     *            <p>
     *            电信的号段：133、153、180（未启用）、189
     *            </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[34578]\\d{9}$";
        return Pattern.matches(regex, mobile);
    }

    public static void intentToActivityClasss(Context mContext, Class clazz, Bundle bundle) {
        Intent intent = new Intent(mContext, clazz);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }
}
