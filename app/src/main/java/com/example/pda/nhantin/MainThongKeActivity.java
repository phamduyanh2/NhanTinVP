package com.example.pda.nhantin;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pda.adapter.DanhSachAdapter;
import com.example.pda.model.ViPhamHanhLang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainThongKeActivity extends AppCompatActivity {

    SimpleDateFormat formatngay = new SimpleDateFormat("dd-MM-yyyy");
    RadioButton rdDoi1_2, rdDoi2_2;
    RadioGroup rdGroupDoi2;

    Spinner spLoaiVP2, spDuong2;


    Button btnSaoLuu, btnQuayLai,btnThoat2,btnCapNhap;


    TextView txtLoaiViPham2, txtDuong2;

    ArrayAdapter<String> adapterDuong2;
    ArrayAdapter<String> adapterLoaiVP2;

    boolean lastSelectDuong2 = true;
    boolean lastSelectLoaiVP2 = true;

    String[] arrLoaiVP2;
    String[] arrDuong2;

// Phục vụ lấy danh sách List View:
    ListView lvDanhSach;
    DanhSachAdapter adapterDanhSach;
    ArrayList<ViPhamHanhLang>dsDanhSach;

    // Phục vụ lay dữ liệu ra va hien thi len
    String DATABASE_NAME="datatinnhan.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thong_ke);
        addControl();
        addEvent();
        showAllDanhSach();



    }



    private void showAllDanhSach() {
        try {

            //Buoc 1: Mo Co so Du Lieu
            database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            Cursor cursor = database.query("NoiDung", null, null, null, null, null, null); //Ten bang
            // Cursor cursor2 =database.rawQuery("select * from noidung",null); //Ten bang




            dsDanhSach.clear();
            while (cursor.moveToNext()) {

                int ma = cursor.getInt(0);
                String soDienThoai = cursor.getString(1);
                String ngay = cursor.getString(2);
                String gio = cursor.getString(3);
                String loaiViPham = cursor.getString(4);
                String duong = cursor.getString(5);
                String noiDung = cursor.getString(6);
                String doi = cursor.getString(7);



                dsDanhSach.add(new ViPhamHanhLang(soDienThoai, formatngay.parse(ngay), gio, loaiViPham, duong, noiDung, doi));

            }
            cursor.close();
            adapterDanhSach.notifyDataSetChanged();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void addEvent() {

        //Loai Vi pham
        spDuong2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            //region Description
            //<editor-fold desc="ac">

            //</editor-fold>
            //endregion




            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                if (parent.getId() == R.id.spDuong2) {
//                    lastSelectDuong2 = position1;
                    if (!lastSelectDuong2) {

                    txtDuong2.setText(arrDuong2[position1]);

                    }
                    lastSelectDuong2=false;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spLoaiVP2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                if (parent.getId() == R.id.spLoai2) {
//                    lastSelectLoaiVP2 = position2;
                    if (!lastSelectLoaiVP2) {

                        txtLoaiViPham2.setText(arrLoaiVP2[position2]);
                    }
                    lastSelectLoaiVP2=false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//    btnQuayLai.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent myIntentBack =new Intent(MainThongKeActivity.this, MainActivity.class);
//            startActivity(myIntentBack);
//        }
//    });

   }

    private void addControl() {
        rdDoi1_2= (RadioButton) findViewById(R.id.rdDoi1_2);
        rdDoi2_2= (RadioButton) findViewById(R.id.rdDoi2_2);
        rdGroupDoi2= (RadioGroup) findViewById(R.id.rdGroupDoi2);
        btnCapNhap = (Button) findViewById(R.id.btnCapNhap);
        btnSaoLuu= (Button) findViewById(R.id.btnSaoLuu);
        btnQuayLai= (Button) findViewById(R.id.btnQuayLai);
        btnThoat2= (Button) findViewById(R.id.btnThoat2);


        spDuong2= (Spinner) findViewById(R.id.spDuong2);
        spLoaiVP2 = (Spinner) findViewById(R.id.spLoai2);

        txtDuong2= (TextView) findViewById(R.id.txtDuong2);
        txtLoaiViPham2 = (TextView) findViewById(R.id.txtLoaiVP2);

        //Tạo dữ liệu cho Spiner Đường
        arrDuong2=getResources().getStringArray(R.array.stDuong);
        adapterDuong2=new ArrayAdapter<String>(MainThongKeActivity.this,
                android.R.layout.simple_list_item_1,
                arrDuong2);
        adapterDuong2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spDuong2.setAdapter(adapterDuong2);
////
        //Tạo dữ liệu cho Spiner Loai Vi Pham
        arrLoaiVP2=getResources().getStringArray(R.array.stLoaiViPham);
        adapterLoaiVP2=new ArrayAdapter<String>(MainThongKeActivity.this,
                android.R.layout.simple_list_item_1,
                arrLoaiVP2);
        adapterLoaiVP2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spLoaiVP2.setAdapter(adapterLoaiVP2);




        //Tao Du lieu de lay danh sach hien thi len List View

        lvDanhSach= (ListView) findViewById(R.id.lvDanhSach);
        dsDanhSach=new ArrayList<ViPhamHanhLang>();
        adapterDanhSach=new DanhSachAdapter(
                MainThongKeActivity.this,
                R.layout.item,
                dsDanhSach);
        lvDanhSach.setAdapter(adapterDanhSach);
    }




}
