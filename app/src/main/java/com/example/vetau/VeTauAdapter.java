package com.example.vetau;

import android.app.Activity;
import android.content.Context;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class VeTauAdapter extends BaseAdapter implements Filterable {
    private ArrayList<VeTau> list;
    private ArrayList<VeTau> listFilter;
    private Context context;
    private DataFilter filter;

    public VeTauAdapter(ArrayList<VeTau> list, Activity context) {
        this.list = list;
        this.listFilter = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getMa();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_row, null);
        }
        VeTau veTau = (VeTau) getItem(position);
        if(veTau != null) {
            NumberFormat format = NumberFormat.getCurrencyInstance();
            TextView tvGa = view.findViewById(R.id.tvGa);
            TextView tvDonGia = view.findViewById(R.id.tvDonGia);
            TextView tvKhuHoi = view.findViewById(R.id.tvKhuHoi);

            String ga = veTau.getGaDi() + " -> " + veTau.getGaDen();
            String khuHoi = veTau.getKhuHoi() == 1 ? "Khứ hồi" : "Một chiều";
            String donGia = format.format(veTau.getDonGia());
            tvGa.setText(ga);
            tvKhuHoi.setText(khuHoi);
            tvDonGia.setText(donGia);
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        if(filter == null) {
            filter = new DataFilter();
        }
        return filter;
    }

    public class DataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint != null && !constraint.toString().trim().equals("")) {
                constraint = constraint.toString().toLowerCase();
                ArrayList<VeTau> veTaus = new ArrayList<>();
                for(VeTau veTau : listFilter) {
                    if(veTau.getGaDen().toLowerCase().contains(constraint)) {
                        veTaus.add(veTau);
                    }
                }
                results.count = veTaus.size();
                results.values = veTaus;
            } else {
                results.count = listFilter.size();
                results.values = listFilter;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = (ArrayList<VeTau>) results.values;
            notifyDataSetChanged();
        }
    }
}
