package edu.mj.mart.activities.fragments.auth.forget;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.util.Properties;
import java.util.Random;

import edu.mj.mart.base.BasePresenter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgetPassPresenter extends BasePresenter<ForgetPassView> {

    protected ForgetPassPresenter(FragmentActivity fragmentActivity, ForgetPassView view) {
        super(fragmentActivity, view);
    }

    public void sendOTP(String email) {
        mView.showLoading();

    }

    // AsyncTask để gửi email OTP trên background thread
    private class SendEmailTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String userEmail = params[0];
            sendEmailOTP(userEmail);
            return null;
        }
    }

    // Phương thức gửi email OTP
    public void sendEmailOTP(String userEmail) {
        String host = "smtp.gmail.com"; // SMTP server
        final String username = "daihgph36944@fpt.edu.vn"; // Thay bằng email của bạn
        final String password = "pmbiwtwlkgsmujwx"; // Thay bằng mật khẩu email của bạn

        String otpCode = generateOTP();  // Tạo mã OTP ngẫu nhiên
        long otpTimestamp = System.currentTimeMillis();  // Lấy thời gian gửi OTP

        // Lưu thông tin vào SharedPreferences tạm thời
        SharedPreferences sharedPreferences = fragmentActivity.getSharedPreferences("OTP_PREFS", fragmentActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", userEmail);
        editor.putString("otp", otpCode);
        editor.putLong("otp_timestamp", otpTimestamp);
        editor.apply();  // Lưu thông tin

        // Cấu hình các thuộc tính gửi email qua SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject("MJ Mart - Đặt lại mật khẩu");
            message.setText("Mã OTP của bạn là: " + otpCode + "\nHạn sử dụng: 3 phút");

            // Gửi email
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            fragmentActivity.runOnUiThread(() -> Toast.makeText(fragmentActivity, "Gửi email thất bại !", Toast.LENGTH_SHORT).show());
            Log.e("SendEmailTask", "Error sending email: " + e.getMessage());

        }
    }

    // Hàm tạo mã OTP ngẫu nhiên 4 chữ số
    private String generateOTP() {
        Random random = new Random();
        int otp = random.nextInt(9000) + 1000; // OTP có 4 chữ số
        return String.valueOf(otp);
    }
}
