package com.example.checklistapp;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {



    OnClickListener listener = position -> {};
    List<Item> items;

    public ItemAdapter() {this.items = new ArrayList<>(); }

    public List<Item> getItems() {
        return items;
    }

    public Item getItem(int position) {return items.get(position);}

    public void setItems(List<Item> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void changeItem(int position){
        Item item = items.get(position);
        if (item.isChecked()){
            item.setChecked(false);
        }
        else if (!item.isChecked()){
            item.setChecked(true);
        }
        notifyDataSetChanged();
    }

    public void addItems(List<Item> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }



    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = getItems().get(position);

        String title = item.getTitle();
        boolean checked = item.isChecked();

        holder.title.setText(title);
        holder.checked.setChecked(checked);

        holder.checked.setOnCheckedChangeListener(null);

        holder.checked.setChecked(item.isChecked());

        holder.checked.setOnCheckedChangeListener((buttonView, isChecked) -> item.setChecked(!isChecked));
    }

    @Override
    public int getItemCount() {
        return getItems().size();
    }



    interface OnClickListener {
        void onClick(int position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        CheckBox checked;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.item_title);
            this.checked = itemView.findViewById(R.id.item_checkbox);
            checked.setOnClickListener(v -> { listener.onClick(getAdapterPosition()); });

        }


    }
}
