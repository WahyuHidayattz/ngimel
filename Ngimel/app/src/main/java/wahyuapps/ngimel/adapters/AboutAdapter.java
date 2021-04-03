package wahyuapps.ngimel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wahyuapps.ngimel.R;
import wahyuapps.ngimel.datas.AboutData;

public class AboutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<AboutData> data;

    public AboutAdapter(Context context, List<AboutData> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView mTitle, mSubtitle;
        ImageView mImage;

        public MyHolder(View view) {
            super(view);

            mTitle = view.findViewById(R.id.text_title);
            mSubtitle = view.findViewById(R.id.text_subtitle);
            mImage = view.findViewById(R.id.image_icon);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_about_item_list, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder mHolder = (MyHolder) holder;
        AboutData item = data.get(position);

        mHolder.mTitle.setText(item.title);
        mHolder.mSubtitle.setText(item.subtitle);
        mHolder.mImage.setImageResource(item.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
