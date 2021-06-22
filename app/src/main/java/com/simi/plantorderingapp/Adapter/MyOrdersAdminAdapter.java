package com.simi.plantorderingapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simi.plantorderingapp.Components.MyOrdersAdmin;
import com.simi.plantorderingapp.ProductModel.SaveMyOrdersAdminDataModel;
import com.simi.plantorderingapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.UUID;

public class MyOrdersAdminAdapter extends RecyclerView.Adapter<MyOrdersAdminAdapter.ViewHolder>{
    Context context;
    ArrayList<SaveMyOrdersAdminDataModel> saveMyOrdersAdminDataModelList;
    public MyOrdersAdminAdapter(ArrayList<SaveMyOrdersAdminDataModel> saveMyOrdersAdminDataModelList, Context context) {
        this.saveMyOrdersAdminDataModelList= saveMyOrdersAdminDataModelList;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_orders_admin_layout, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.quantity.setText(saveMyOrdersAdminDataModelList.get(position).getQuantity());
        holder.paymentstatus.setText(saveMyOrdersAdminDataModelList.get(position).getPaymentstatus());
        holder.itemquantity.setText(saveMyOrdersAdminDataModelList.get(position).getItemquantity());
        holder.itemprice.setText(saveMyOrdersAdminDataModelList.get(position).getItemprice());
        holder.itemname.setText(saveMyOrdersAdminDataModelList.get(position).getItemname());
        holder.usermobile.setText(saveMyOrdersAdminDataModelList.get(position).getUsermobile());
        holder.product.setText("Product Id: "+ UUID.randomUUID().toString().trim());
      //  String Id= saveMyOrdersAdminDataModelList.get(position).getId().substring(saveMyOrdersAdminDataModelList.get(position).getId().indexOf("media&token=")).replace("media&token=","").trim();

        String id1= saveMyOrdersAdminDataModelList.get(position).getId(), id11="";

        if (id1.length() > 36) {
            String Id[] = saveMyOrdersAdminDataModelList.get(position).getId().split("media&token=");
            for (String s: Id) {
                s= s.replace("Id:","").trim();
                System.out.println("media&token= Id: " + s);
                if (s.length() <= 36) {
                    id1= s;
                }
            }
            holder.id.setText("Order Id: " + id1);
        }
        else {
            holder.id.setText("Order Id: " + saveMyOrdersAdminDataModelList.get(position).getId().trim());
        }
        holder.username.setText(saveMyOrdersAdminDataModelList.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return saveMyOrdersAdminDataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, usermobile, itemname, itemprice, itemquantity, paymentstatus, quantity, id, product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username= (TextView) itemView.findViewById(R.id.username_id);
            usermobile= (TextView) itemView.findViewById(R.id.usermobile_id);
            itemname= (TextView) itemView.findViewById(R.id.itemname_id);
            itemprice= (TextView) itemView.findViewById(R.id.itemprice_id);
            itemquantity= (TextView) itemView.findViewById(R.id.itemquantity_id);
            paymentstatus= (TextView) itemView.findViewById(R.id.paymentstatus_id);
            quantity= (TextView) itemView.findViewById(R.id.quantity_id);
            id= (TextView) itemView.findViewById(R.id.id_id);
            product= (TextView) itemView.findViewById(R.id.product_id);
        }
    }
}