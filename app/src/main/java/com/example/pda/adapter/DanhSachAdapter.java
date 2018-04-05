package com.example.pda.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pda.model.ViPhamHanhLang;
import com.example.pda.nhantin.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by pda on 04/05/2018.
 */
public class DanhSachAdapter extends ArrayAdapter{
    SimpleDateFormat formatngay = new SimpleDateFormat("dd-MM-yyyy");

    Activity context;
    int resource;
    List<ViPhamHanhLang> objects;
    public DanhSachAdapter(Activity context, int resource, List<ViPhamHanhLang> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=this.context.getLayoutInflater();
        View row2= inflater.inflate(this.resource,null);

        TextView txtNgaylv= (TextView) row2.findViewById(R.id.txtNgaylv);
        TextView txtDuonglv= (TextView) row2.findViewById(R.id.txtDuonglv);
        TextView txtDoilv= (TextView) row2.findViewById(R.id.txtDoilv);
        TextView txtNoiDunglv= (TextView) row2.findViewById(R.id.txtNoiDunglv);

        ViPhamHanhLang dsViPham =this.objects.get(position);


        txtNgaylv.setText(dsViPham.getNgay().toString());



        txtDuonglv.setText(dsViPham.getDuong());
        txtDoilv.setText(dsViPham.getDoi());
        txtNoiDunglv.setText(dsViPham.getNoiDung());

        return row2;
    }
}
