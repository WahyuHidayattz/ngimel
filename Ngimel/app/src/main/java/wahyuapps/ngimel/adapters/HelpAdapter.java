package wahyuapps.ngimel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wahyuapps.ngimel.R;
import wahyuapps.ngimel.datas.HelpData;

public class HelpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<HelpData> data;

    public HelpAdapter(Context context, List<HelpData> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView mTitle, mNumber;

        public MyHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.text_title);
            mNumber = view.findViewById(R.id.text_number);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bottom_sheet_help_list, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder mHolder = (MyHolder) holder;
        HelpData item = data.get(position);

        mHolder.mTitle.setText(item.title);
        mHolder.mNumber.setText(item.number);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
