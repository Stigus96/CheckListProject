package com.example.checklistapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ChecklistViewHolder> {
    OnClickListener listener = position -> {};
    List<Checklist> checklists;

    public ChecklistAdapter(){
        this.checklists = new ArrayList<>();
    }

    public List<Checklist> getChecklists(){return checklists;}

    public void setChecklists(List<Checklist> checklists){
        this.checklists = checklists;
        notifyDataSetChanged();
        }


    public void setOnClickListener(OnClickListener listener) {this.listener = listener;}


    @NonNull
    @Override
    public ChecklistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist,parent,false);
            return new ChecklistViewHolder(view);
            }

    @Override
    public void onBindViewHolder(@NonNull ChecklistViewHolder holder, int position) {

            Checklist checklist = getChecklists().get(position);
            String title = checklist.getTitle();

            holder.title.setText(title);
            }

    @Override
    public int getItemCount() {return getChecklists().size();}

    interface OnClickListener {
        void onClick(int position);
    }

    class ChecklistViewHolder extends RecyclerView.ViewHolder{

        TextView title;

        public ChecklistViewHolder(@NonNull View checklistView){
            super(checklistView);
            checklistView.setOnClickListener(v -> listener.onClick(getAdapterPosition()));
            this.title = checklistView.findViewById(R.id.checklist_title);
        }

    }

}



