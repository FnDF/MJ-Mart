package edu.mj.mart.activities.employee.edit;

import static android.app.Activity.RESULT_OK;
import static edu.mj.mart.utils.SyntheticEnum.Role.MANAGER;
import static edu.mj.mart.utils.SyntheticEnum.StatusEmployee.ACTIVE;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import edu.mj.mart.R;
import edu.mj.mart.activities.employee.EmployeeManagerActivity;
import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentEditEmployeeBinding;
import edu.mj.mart.model.Employee;
import edu.mj.mart.utils.ImageUtil;

public class EditEmployeeFragment extends BaseFragment<FragmentEditEmployeeBinding, EditEmployPresenter> implements EditEmployeeView {

    private Employee employee;

    public EditEmployeeFragment(Employee employee) {
        this.employee = employee;
    }

    private RoleBottomSheetDialog roleBottomSheetDialog;
    private ActiveBottomSheetDialog activeBottomSheetDialog;
    private final int PICK_IMAGE_REQUEST = 1001;
    private Bitmap imageBitmap;

    @NonNull
    @Override
    protected FragmentEditEmployeeBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentEditEmployeeBinding.inflate(inflater, container, false);
    }

    @NonNull
    @Override
    protected EditEmployPresenter createPresenter() {
        return new EditEmployPresenter(requireActivity(), this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        if (getArguments() != null) {
//            employee = (Employee) getArguments().get("key_employee");
//        }
        if (employee == null) {
            Toast.makeText(requireContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            onBack();
            return;
        }
        if (employee.getAvatar() == null || employee.getAvatar().isEmpty()) {
            binding.ivAvatar.setImageResource(R.drawable.icon_profile_default);
        } else {
            StringBuilder ima = new StringBuilder();
            for (String s : employee.getAvatar()) {
                ima.append(s);
            }
            if (ima.toString().isEmpty()) {
                binding.ivAvatar.setImageResource(R.drawable.icon_profile_default);
            } else {
                binding.ivAvatar.setImageBitmap(ImageUtil.decode(ima.toString()));
            }
        }
        binding.ivAvatar.setOnClickListener(v -> {
            openFileChooser();
        });
        binding.tvFullName.setText(employee.getFullName());
        binding.edtFullName.setText(employee.getFullName());
        binding.edtFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.tvFullName.setText(editable.toString());
            }
        });
        binding.edtEmail.setText(employee.getEmail());
        binding.edtPhone.setText(employee.getPhone());

        setUIRoleValue();
        setUIActiveValue();

        binding.ivBack.setOnClickListener(v -> onBack());
        binding.tvRole.setOnClickListener(v -> {
            openEditRoleDialog();
        });
        binding.tvActive.setOnClickListener(v -> {
            openEditActiveDialog();
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Employee: id = " + employee.getId());

                String fullName = binding.edtFullName.getText().toString().trim();
                String email = binding.edtEmail.getText().toString().trim();
                String phone = binding.edtPhone.getText().toString().trim();
                if (fullName.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập họ tên", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<String> image = null;
                if (imageBitmap != null) {
                    String base64 = ImageUtil.convert(imageBitmap);
                    int length = base64.length();

                    image = new ArrayList<>();
                    image.add(base64.substring(0, length / 2));
                    image.add(base64.substring(length / 2));
                } else if (employee.getAvatar() != null) {
                    image = new ArrayList<>(employee.getAvatar());
                }
                employee.setFullName(fullName);
                employee.setAvatar(image);
                employee.setPhone(phone);

                presenter.updateEmployee(employee);
            }
        });
    }

    private void openEditRoleDialog() {
        if (roleBottomSheetDialog == null) {
            roleBottomSheetDialog = new RoleBottomSheetDialog(employee, role -> {
                if (employee == null) return;
                employee.setRole(role);
                setUIRoleValue();
            });
        }
        if (!roleBottomSheetDialog.isVisible()) {
            roleBottomSheetDialog.show(getParentFragmentManager(), "Role");
        }
    }

    private void setUIRoleValue() {
        if (employee == null) return;
        if (employee.getRole() == MANAGER.value) {
            binding.tvRole.setText(getString(R.string.title_manager));
        } else {
            binding.tvRole.setText(getString(R.string.title_staff));
        }
    }

    private void setUIActiveValue() {
        if (employee == null) return;
        if (employee.getActive() == ACTIVE.value) {
            binding.tvActive.setText(getString(R.string.title_active));
        } else {
            binding.tvActive.setText(getString(R.string.title_deactivate));
        }
    }

    private void openEditActiveDialog() {
        if (activeBottomSheetDialog == null) {
            activeBottomSheetDialog = new ActiveBottomSheetDialog(employee, active -> {
                if (employee == null) return;
                employee.setActive(active);
                setUIActiveValue();
            });
        }
        if (!activeBottomSheetDialog.isVisible()) {
            activeBottomSheetDialog.show(getParentFragmentManager(), "Active");
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void onBack() {
        requireActivity().getOnBackPressedDispatcher().onBackPressed();
    }

    @Override
    public void showLoading() {
        ((EmployeeManagerActivity) requireActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((EmployeeManagerActivity) requireActivity()).hideLoading();
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.edtFullName.getWindowToken(), 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), data.getData());
                int height = 500 * imageBitmap.getHeight() / imageBitmap.getWidth();
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 500, height, false);
                binding.ivAvatar.setImageBitmap(imageBitmap);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onUpdateSuccess(Employee employee) {
        Toast.makeText(requireContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
        ((EmployeeManagerActivity) requireActivity()).reloadData(employee);
        onBack();
    }

    @Override
    public void onUpdateFailed() {
        Toast.makeText(requireContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
    }
}
