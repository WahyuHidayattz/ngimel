package wahyuapps.ngimel.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import wahyuapps.ngimel.R;
import wahyuapps.ngimel.adapters.HelpAdapter;
import wahyuapps.ngimel.datas.HelpData;

public class HelpDialog extends DialogFragment {

    private static final String TAG = "dialog_help";
    private ImageView mImageButtonClose;
    private RecyclerView mRecyclerView;
    private View mDivider;
    private NestedScrollView mScroll;

    public static HelpDialog display(FragmentManager fragmentManager) {
        HelpDialog helpDialog = new HelpDialog();
        helpDialog.show(fragmentManager, TAG);
        return helpDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.bottom_sheet_edit_help, container, false);
        mImageButtonClose = v.findViewById(R.id.image_button_close);
        mRecyclerView = v.findViewById(R.id.recycler_view_help);
        mDivider = v.findViewById(R.id.divider);
        mScroll = v.findViewById(R.id.nested_scrollview);

        setupScrolling();
        seupRecyclerView();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    private void seupRecyclerView() {
        List<HelpData> data = new ArrayList<>();
        String[] titles = getResources().getStringArray(R.array.help_text);
        for (int i = 0; i<titles.length; i++){
            HelpData item = new HelpData();
            item.title = titles[i];
            item.number = String.valueOf(i+1);
            data.add(item);
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(new HelpAdapter(getContext(), data));
    }

    private void setupScrolling(){
        mScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY){
                    mDivider.setVisibility(View.VISIBLE);
                }if (scrollY == 0){
                    mDivider.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
