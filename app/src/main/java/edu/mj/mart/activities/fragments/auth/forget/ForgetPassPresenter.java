package edu.mj.mart.activities.fragments.auth.forget;

import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.mj.mart.base.BasePresenter;
import edu.mj.mart.utils.Constants;
import edu.mj.mart.utils.SharedPrefUtils;

public class ForgetPassPresenter extends BasePresenter<ForgetPassView> {

    protected ForgetPassPresenter(FragmentActivity fragmentActivity, ForgetPassView view) {
        super(fragmentActivity, view);
    }

    public void sendOTP(String email) {
        mView.showLoading();

    }

    public void checkEmail(String email) {
        if (mView != null) {
            mView.showLoading();
        }
        db.collection(Constants.DB_COLLECTION_USERS)
                .whereEqualTo(Constants.DB_COLLECTION_KEY_EMAIL, email)
                .get()
                .addOnCompleteListener(task -> {
                    if (mView == null) return;
                    if (task.getResult() == null || task.getResult().isEmpty() || !task.isSuccessful()) {
                        mView.hideLoading();
                        mView.onEmailIsNotExists(email);
                    } else {
                        new SendEmailTask().execute(email);
                    }
                });
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

        SharedPrefUtils.saveData(fragmentActivity, "email", userEmail);
        SharedPrefUtils.saveData(fragmentActivity, "otp", otpCode);
        SharedPrefUtils.saveData(fragmentActivity, "otp_timestamp", otpTimestamp);

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

            fragmentActivity.runOnUiThread(() -> {
                if (mView != null) {
                    mView.sendOTPSuccessfully();
                }
            });

        } catch (MessagingException e) {
            Log.e("SendEmailTask", "Error sending email: " + e.getMessage());
            fragmentActivity.runOnUiThread(() -> {
                if (mView != null) {
                    mView.sendOTPFailed(e.getMessage());
                }
            });
        }
    }

    // Hàm tạo mã OTP ngẫu nhiên 4 chữ số
    private String generateOTP() {
        Random random = new Random();
        int otp = random.nextInt(9000) + 1000; // OTP có 4 chữ số
        return String.valueOf(otp);
    }
}
