package edu.mj.mart.activities.auth;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import edu.mj.mart.R;
import edu.mj.mart.utils.SharedPrefUtils;

public class XacMinhOTPFragment extends Fragment {

    ImageButton imgTroVeXac;
    Button btnXacNhan;
    EditText otp1, otp2, otp3, otp4;

//    QuenMatKhauFragment quenMatKhauFragment;

    private static final long OTP_EXPIRATION_TIME = 180000; // 3 phút = 180.000 milliseconds
    private Handler handler;
    private Runnable otpExpirationRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_xac_minh_otp, container, false);

        imgTroVeXac = view.findViewById(R.id.imgTroVeXacMinhOTP);
        btnXacNhan = view.findViewById(R.id.btnXacNhanOTP);
        otp1 = view.findViewById(R.id.otp1);
        otp2 = view.findViewById(R.id.otp2);
        otp3 = view.findViewById(R.id.otp3);
        otp4 = view.findViewById(R.id.otp4);

        imgTroVeXac.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    otp2.requestFocus();
                }
            }
        });

        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    otp3.requestFocus();
                }
            }
        });

        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    otp4.requestFocus();
                }
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xacNhanOTP();
            }
        });

        // Kiểm tra xem OTP có hết hạn không
        checkOTPExpiration();

        return view;
    }

    private void finishXacMinhOTPFragment() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void xacNhanOTP() {
        // Lấy giá trị OTP người dùng nhập
        String otp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString();

        // Kiểm tra xem người dùng đã nhập đủ 4 chữ số OTP hay chưa
        if (otp.length() == 4) {
            // Kiểm tra OTP có hết hạn không
            long otpTimestamp = SharedPrefUtils.getLongData(requireContext(), "otp_timestamp");

            if (System.currentTimeMillis() - otpTimestamp > OTP_EXPIRATION_TIME) {
                // OTP đã hết hạn
                Toast.makeText(getContext(), "OTP đã hết hạn. Vui lòng thực hiện lại!", Toast.LENGTH_SHORT).show();
            } else {
                // Kiểm tra OTP với dữ liệu đã lưu
                String otpXM = SharedPrefUtils.getStringData(requireContext(), "otp");
                if (otp.equals(otpXM)) {
                    // Sau khi xác nhận thành công, hủy bỏ Runnable để tránh xóa SharedPreferences
                    if (handler != null && otpExpirationRunnable != null) {
                        handler.removeCallbacks(otpExpirationRunnable);
                    }
                    otp1.setText("");
                    otp2.setText("");
                    otp3.setText("");
                    otp4.setText("");

                    // Chuyển qua màn hình
                    ((AuthActivity) requireActivity()).onNavigationResetPW();
                } else {
                    Toast.makeText(getContext(), "OTP không đúng, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            // Hiển thị thông báo lỗi nếu OTP chưa đầy đủ
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ OTP!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkOTPExpiration() {
        // Lấy timestamp khi OTP được lưu
        long otpTimestamp = SharedPrefUtils.getLongData(requireContext(), "otp_timestamp");

        if (otpTimestamp != 0) {
            // Tạo Handler và Runnable để xóa SharedPreferences sau 3 phút
            handler = new Handler();
            otpExpirationRunnable = new Runnable() {
                @Override
                public void run() {
                    // Xóa SharedPreferences sau 3 phút
                    SharedPrefUtils.removeData(requireContext(), "email");
                    SharedPrefUtils.removeData(requireContext(), "otp");
                    SharedPrefUtils.removeData(requireContext(), "otp_timestamp");
                }
            };

            // Chạy Runnable sau 3 phút
            handler.postDelayed(otpExpirationRunnable, OTP_EXPIRATION_TIME);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hủy Runnable nếu Fragment bị hủy
        if (handler != null && otpExpirationRunnable != null) {
            handler.removeCallbacks(otpExpirationRunnable);
        }
    }
}
