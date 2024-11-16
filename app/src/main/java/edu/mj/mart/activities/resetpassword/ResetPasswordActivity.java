package edu.mj.mart.activities.resetpassword;

import static edu.mj.mart.utils.Constants.ACCOUNT_PASSWORD;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.mj.mart.R;
import edu.mj.mart.core.BaseActivity;
import edu.mj.mart.databinding.ActivityResetPasswordBinding;
import edu.mj.mart.model.Account;
import edu.mj.mart.utils.AESEncryption;
import edu.mj.mart.utils.Constants;

public class ResetPasswordActivity extends BaseActivity<ActivityResetPasswordBinding> {

    protected FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean isShowOldPassword;
    private boolean isShowNewPassword;
    private boolean ivShowConfirmNewPassword;

    @NonNull
    @Override
    protected ActivityResetPasswordBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityResetPasswordBinding.inflate(layoutInflater);
    }

    @Override
    protected void onStart() {
        super.onStart();

        binding.ivShowOldPassword.setOnClickListener(view1 -> {
            if (isShowOldPassword) {
                isShowOldPassword = false;
                binding.edtOldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.edtOldPassword.setSelection(binding.edtOldPassword.getText().toString().trim().length());
                binding.ivShowOldPassword.setImageResource(R.drawable.ic_hide_pass);
            } else {
                isShowOldPassword = true;
                binding.edtOldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.edtOldPassword.setSelection(binding.edtOldPassword.getText().toString().trim().length());
                binding.ivShowOldPassword.setImageResource(R.drawable.baseline_remove_red_eye_24);
            }
        });

        binding.ivShowNewPassword.setOnClickListener(view1 -> {
            if (isShowNewPassword) {
                isShowNewPassword = false;
                binding.edtNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.edtNewPassword.setSelection(binding.edtNewPassword.getText().toString().trim().length());
                binding.ivShowNewPassword.setImageResource(R.drawable.ic_hide_pass);
            } else {
                isShowNewPassword = true;
                binding.edtNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.edtNewPassword.setSelection(binding.edtNewPassword.getText().toString().trim().length());
                binding.ivShowNewPassword.setImageResource(R.drawable.baseline_remove_red_eye_24);
            }
        });

        binding.ivShowConfirmNewPassword.setOnClickListener(view1 -> {
            if (ivShowConfirmNewPassword) {
                ivShowConfirmNewPassword = false;
                binding.edtConfirmNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.edtConfirmNewPassword.setSelection(binding.edtConfirmNewPassword.getText().toString().trim().length());
                binding.ivShowConfirmNewPassword.setImageResource(R.drawable.ic_hide_pass);
            } else {
                ivShowConfirmNewPassword = true;
                binding.edtConfirmNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.edtConfirmNewPassword.setSelection(binding.edtConfirmNewPassword.getText().toString().trim().length());
                binding.ivShowConfirmNewPassword.setImageResource(R.drawable.baseline_remove_red_eye_24);
            }
        });

        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass = binding.edtOldPassword.getText().toString().trim();
                String newPass = binding.edtNewPassword.getText().toString().trim();
                String confirmNewPass = binding.edtConfirmNewPassword.getText().toString().trim();

                if (oldPass.isEmpty() || oldPass.length() < 8) {
                    Toast.makeText(ResetPasswordActivity.this, getString(R.string.password_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPass.isEmpty() || newPass.length() < 8) {
                    Toast.makeText(ResetPasswordActivity.this, getString(R.string.password_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (confirmNewPass.isEmpty() || confirmNewPass.length() < 8) {
                    Toast.makeText(ResetPasswordActivity.this, getString(R.string.password_empty), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPass.equals(confirmNewPass)) {
                    Toast.makeText(ResetPasswordActivity.this, getString(R.string.password_wrong), Toast.LENGTH_SHORT).show();
                    return;
                }
                binding.layoutLoading.setVisibility(View.VISIBLE);
                Account account = Constants.currentAccount;
                db.collection(Constants.DB_COLLECTION_USERS)
                        .document(account.getId())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    String storedPassword = document.getString(ACCOUNT_PASSWORD);
                                    if (TextUtils.isEmpty(storedPassword)) {
                                        binding.layoutLoading.setVisibility(View.GONE);
                                        Toast.makeText(ResetPasswordActivity.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (storedPassword.equals(AESEncryption.encrypt(oldPass))) {
                                        account.setPassword(AESEncryption.encrypt(newPass));

                                        db.collection(Constants.DB_COLLECTION_USERS)
                                                .document(account.getId())
                                                .set(account.convertToHashMap())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        binding.layoutLoading.setVisibility(View.GONE);
                                                        Constants.currentAccount = account;
                                                        Toast.makeText(ResetPasswordActivity.this, "Thay đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        binding.layoutLoading.setVisibility(View.GONE);
                                                        Toast.makeText(ResetPasswordActivity.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        binding.layoutLoading.setVisibility(View.GONE);
                                        Toast.makeText(ResetPasswordActivity.this, "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    binding.layoutLoading.setVisibility(View.GONE);
                                    Toast.makeText(ResetPasswordActivity.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        binding.ivBack.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Constants.currentAccount == null) {
            finish();
            return;
        }


    }
}
