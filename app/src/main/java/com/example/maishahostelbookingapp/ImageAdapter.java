package com.example.maishahostelbookingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
    private List<Image> imageList;
    private ViewPager2 viewPager2;
    public ImageAdapter(List<Image> imageList,ViewPager2 viewPager2){
        this.imageList=imageList;
        this.viewPager2=viewPager2;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_container,parent,false);
        return new ImageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imageView.setImageResource(imageList.get(position).getImage());
        if(position==imageList.size()-2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            imageList.addAll(imageList);
            notifyDataSetChanged();
        }
    };
}
