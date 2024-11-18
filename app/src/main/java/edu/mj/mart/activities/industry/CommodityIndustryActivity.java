package edu.mj.mart.activities.industry;

import static edu.mj.mart.utils.SyntheticEnum.Role.STAFF;

import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.mj.mart.adapter.CIAdapter;
import edu.mj.mart.core.BaseActivity;
import edu.mj.mart.databinding.ActivityCommodityIndustryBinding;
import edu.mj.mart.model.CI;
import edu.mj.mart.utils.Constants;

public class CommodityIndustryActivity extends BaseActivity<ActivityCommodityIndustryBinding> {

    private CIAdapter ciAdapter;
    private final ArrayList<CI> cis = new ArrayList<>();

    @NonNull
    @Override
    protected ActivityCommodityIndustryBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityCommodityIndustryBinding.inflate(layoutInflater);
    }

    @Override
    public void setupView() {
        super.setupView();
        ciAdapter = new CIAdapter(cis, item -> {

        });
        binding.cardViewCreateCI.setOnClickListener(view -> {
            if (Constants.currentAccount != null) {
                if (Constants.currentAccount.getRole() == STAFF.value) {
                    Toast.makeText(this, "Bạn không có đủ quyền hạn cần thiết", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            CreateCIBottomSheetDialog dialog = new CreateCIBottomSheetDialog(this::fetchData);
            dialog.show(getSupportFragmentManager(), "CreateCIBottomSheetDialog");
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(ciAdapter);
    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.DB_COLLECTION_CI)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ciAdapter.setDataSource(task.getResult().toObjects(CI.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
