package com.baraa.software.eventhorizon.roadtomindvalley.pinboard;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baraa.software.eventhorizon.roadtomindvalley.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PinboardRecyclerViewAdapter  extends  RecyclerView.Adapter<PinboardRecyclerViewAdapter.PinboardViewHolder>{

    List<PinsViewModel> viewModels;


    public PinboardRecyclerViewAdapter(List<PinsViewModel> viewModels) {
        this.viewModels = viewModels;

    }

    @NonNull
    @Override
    public PinboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        PinboardViewHolder viewHolder = new PinboardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PinboardViewHolder holder, int position) {
        PinsViewModel viewModel = viewModels.get(position);

        holder.getImageView().setImageBitmap(viewModel.getImage());
        holder.getTvCategory().setText(viewModel.getCategory());
    }


    @Override
    public int getItemCount() {
        return viewModels.size();
    }

    public class PinboardViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.tvCategory)
        TextView tvCategory;

        public PinboardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTvCategory() {
            return tvCategory;
        }
    }
}
