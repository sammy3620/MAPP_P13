package com.example.mapp_p13;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private JSONObject items;
    LayoutInflater inflater;
    private JSONArray names;

    public ProductListAdapter(Context ctx, JSONObject obj){
        setItems(obj);
        inflater = LayoutInflater.from(ctx);
    }

    public JSONObject getItems() {
        return items;
    }

    public void setItems(JSONObject items) {
        this.items = items;
        names = items.names();
    }

    public ProductListAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = inflater.inflate(R.layout.recycler_item, parent, false);
        return new ProductViewHolder(itemView, this);
    }


    public void onBindViewHolder(ProductListAdapter.ProductViewHolder holder, int position) {

        try {
            JSONObject obj = items.getJSONObject(names.getString(position));

            holder.tv.setText(obj.getString("name"));
        }catch(Exception e){
            Log.e("ProductListAdapter", e.getMessage());
        }
    }

    public int getItemCount() {
        return items.length();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv;
        ProductListAdapter mAdapter;
        public ProductViewHolder(View itemView, ProductListAdapter adapter){
            super(itemView);
            tv = itemView.findViewById(R.id.word);
            mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        public void onClick(View view) {
            try {
                JSONObject obj = items.getJSONObject(names.getString(this.getAdapterPosition()));
                Log.d("ProductViewholder","Clicked:" + this.getAdapterPosition() + "-" +
                        obj.getString("name") + " $" +
                        obj.getString("price"));

            }
            catch(Exception e) {
                Log.e("ProductViewHolder", e.getMessage());
            }
        }
    }
}