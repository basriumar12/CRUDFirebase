package com.basri.crud;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class AdapterData extends RecyclerView.Adapter<AdapterData.MyHolder> {


    //Deklarasi Variable
    private ArrayList<Kegiatan> kegiatan;
    private Context context;
    public  AdapterData(ArrayList<Kegiatan> kegiatan, Context context){

        this.kegiatan = kegiatan;
        this.context = context;


    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView tvKegiatan, tvDesk;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvKegiatan = itemView.findViewById(R.id.tv_kegiatan);
            tvDesk = itemView.findViewById(R.id.tv_desk);

        }
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_data, parent, false);
        return new MyHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {

        holder.tvDesk.setText(kegiatan.get(position).getDesk());
        holder.tvKegiatan.setText(kegiatan.get(position).getKegiatan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //intent untuk kirim data
                Intent kirimData = new Intent(holder.itemView.getContext(),
                        EditActivity.class
                        );

                kirimData.putExtra("KEY_KEGIATAN", kegiatan.get(position).getKegiatan());
                kirimData.putExtra("KEY_DESK", kegiatan.get(position).getDesk());
                kirimData.putExtra("KEY_KEY", kegiatan.get(position).getKey());

                holder.itemView.getContext().startActivity(kirimData);


            }
        });
    }

    @Override
    public int getItemCount() {
        return kegiatan.size();
    }
}
