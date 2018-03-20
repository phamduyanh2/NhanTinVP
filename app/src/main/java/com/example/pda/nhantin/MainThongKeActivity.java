package com.example.pda.nhantin;

import android.content.Intent;
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

public class MainThongKeActivity extends AppCompatActivity {

    RadioButton rdDoi1_2, rdDoi2_2;
    RadioGroup rdGroupDoi2;

    Spinner spLoaiVP2, spDuong2;

    Button btnSaoLuu, btnQuayLai,btnThoat2,btnCapNhap;
    ListView lvDanhSach;

    TextView txtLoaiViPham2, txtDuong2;

    ArrayAdapter<String> adapterDuong2;
    ArrayAdapter<String> adapterLoaiVP2;

    boolean lastSelectDuong2 = true;
    boolean lastSelectLoaiVP2 = true;

    String[] arrLoaiVP2;
    String[] arrDuong2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thong_ke);
        addControl();
        addEvent();

    }

   private void addEvent() {

        //Loai Vi pham
        spDuong2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
        lvDanhSach= (ListView) findViewById(R.id.lvDanhSach);

        spDuong2= (Spinner) findViewById(R.id.spDuong2);
        spLoaiVP2 = (Spinner) findViewById(R.id.spLoai2);

        txtDuong2= (TextView) findViewById(R.id.txtDuong2);
        txtLoaiViPham2= (TextView) findViewById(R.id.txtLoaiVP2);

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



    }




}