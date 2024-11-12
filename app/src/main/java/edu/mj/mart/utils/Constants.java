package edu.mj.mart.utils;

import java.util.ArrayList;
import java.util.List;

import edu.mj.mart.R;
import kotlin.Triple;

public class Constants {

    public static List<Triple<Integer, String, String>> getTutorials() {
        ArrayList<Triple<Integer, String, String>> list = new ArrayList<>();
        list.add(new Triple<>(R.drawable.ic_tutorial_1, "Đơn giản và tiện lợi", "Quản lý cửa hàng dễ dàng hơn với ứng dụng của chúng tôi"));
        list.add(new Triple<>(R.drawable.ic_tutorial_2, "Quản lý đơn hàng", "Quản lý cửa hàng thông minh, tối ưu hoá quy trình kinh doanh"));
        list.add(new Triple<>(R.drawable.ic_tutorial_3, "Thống kê & Báo cáo", "Tiết kiệm thời thời gian và nâng cao hiệu suất kinh doanh với ứng dụng quản lý cửa hàng của chúng tôi"));
        return list;
    }
}
