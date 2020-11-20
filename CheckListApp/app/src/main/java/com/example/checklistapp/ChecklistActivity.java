package com.example.checklistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChecklistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemAdapter adapter = new ItemAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        recyclerView = findViewById(R.id.itemlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Checklist checklist = Client.getSingleton().getSelectedChecklist();
        if(checklist != null){

            adapter.setItems(checklist.getItems());
        }

        adapter.setOnClickListener(position -> {
            Item item = adapter.getItems().get(position);

            new UpdateItemTask().execute(item.getItemid());


            //Client.getSingleton().updateItem(item.getItemid());

        });
        
   }

//    private void changeCheckboxState() {
//        Item item = adapter.getItem(recyclerView.getChildAdapterPosition(adapter.ItemViewHolder.getAdapterPosition()));
//
//        Client.getSingleton().updateItem(item.getItemid());
//    }


}
