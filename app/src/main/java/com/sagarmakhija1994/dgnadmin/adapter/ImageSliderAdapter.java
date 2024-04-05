package com.sagarmakhija1994.dgnadmin.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sagarmakhija1994.dgnadmin.R;
import com.sagarmakhija1994.dgnadmin.model.SliderDataModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends
        SliderViewAdapter<ImageSliderAdapter.SliderAdapterVH> {
    private Context context;
    private List<SliderDataModel> mSliderDataModels = new ArrayList<>();

    public ImageSliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<SliderDataModel> SliderDataModels) {
        this.mSliderDataModels = SliderDataModels;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderDataModels.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderDataModel SliderDataModel) {
        this.mSliderDataModels.add(SliderDataModel);
        notifyDataSetChanged();
    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        SliderDataModel SliderDataModel = mSliderDataModels.get(position);
        String filePath = SliderDataModel.getFile().getPath();
        viewHolder.imageViewBackground.setImageBitmap(BitmapFactory.decodeFile(filePath));
        viewHolder.itemView.setOnClickListener(v -> {
            //Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderDataModels.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}
