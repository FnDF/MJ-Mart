package edu.mj.mart.utils;

import java.util.ArrayList;
import java.util.List;

import edu.mj.mart.R;
import edu.mj.mart.model.Account;
import kotlin.Triple;

public class Constants {

    public static List<Triple<Integer, String, String>> getTutorials() {
        ArrayList<Triple<Integer, String, String>> list = new ArrayList<>();
        list.add(new Triple<>(R.drawable.ic_tutorial_1, "Đơn giản và tiện lợi", "Quản lý cửa hàng dễ dàng hơn với ứng dụng của chúng tôi"));
        list.add(new Triple<>(R.drawable.ic_tutorial_2, "Quản lý đơn hàng", "Quản lý cửa hàng thông minh, tối ưu hoá quy trình kinh doanh"));
        list.add(new Triple<>(R.drawable.ic_tutorial_3, "Thống kê & Báo cáo", "Tiết kiệm thời thời gian và nâng cao hiệu suất kinh doanh với ứng dụng quản lý cửa hàng của chúng tôi"));
        return list;
    }

    public static final String DB_COLLECTION_USERS = "users";
    public static final String DB_COLLECTION_KEY_EMAIL = "email";
    public static final String DB_COLLECTION_KEY_PASSWORD = "password";

    // region -> Account

    public static final String ACCOUNT_ROLE = "role";
    public static final String ACCOUNT_EMAIL = "email";
    public static final String ACCOUNT_PASSWORD = "password";
    public static final String ACCOUNT_PHONE = "phone";
    public static final String ACCOUNT_FULL_NAME = "full_name";
    public static final String ACCOUNT_ACTIVE = "active";
    public static final String ACCOUNT_AVATAR = "avatar";

    public static final String EMAIL_ADMIN_EXAMPLE = "hoanggiadai19@gmail.com";
    public static final String FULL_NAME_ADMIN_EXAMPLE = "Admin";
    public static final String PASSWORD_ADMIN_EXAMPLE = AESEncryption.encrypt("Abc12345");

    public static String KEY_SERIALIZABLE_ACCOUNT = "key_account";

    public static Account currentAccount;
    // endregion
}
