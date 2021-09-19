package com.example.pendomovieapptask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;


public class KeywordsRecViewAdapter extends RecyclerView.Adapter<KeywordsRecViewAdapter.ViewHolder> {
    public static final String KEYWORD_ID_KEY = "keywordId";

    private ArrayList<Keyword> keywords = new ArrayList<>();
    private Context context;

    public KeywordsRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_keyword_item, null, true);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.keyword.setText(keywords.get(position).getName());

        holder.keyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchedMoviesActivity.class);
                intent.putExtra(KEYWORD_ID_KEY, keywords.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return keywords.size();
    }

    public void setKeywords(ArrayList<Keyword> keywords) {
        this.keywords = keywords;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView keyword;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            keyword = itemView.findViewById(R.id.keyword_item);
        }
    }
}
