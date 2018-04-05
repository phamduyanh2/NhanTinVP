package com.example.pda.nhantin;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pda.model.ViPhamHanhLang;

import org.xml.sax.Parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText txtNgay, txtGio, txtNoiDung, txtSoDienThoai;
    TextView txtLoaiViPham, txtDuong;

    Button btnNgay, btnGio;
    Button btnGui, btnClear, btnHuy, btnLuu, btnThongKe, btnThoat;
    ImageButton btnDanhBa;
    Spinner spLoaiVP, spDuong, spDanhBa;

    RadioButton rdDoi1, rdDoi2;
    RadioGroup rdGroupDoi;

    String[] arrLoaiVP;
    String[] arrDuong;
    String[] arrDanhBa;

    ArrayAdapter<String> adapterDanhba;
    ArrayAdapter<String> adapterDuong;
    ArrayAdapter<String> adapterLoaiVP;

    boolean lastSelectDuong = true;
    boolean lastSelectLoaiVP = true;
    boolean lastSelectDanhBa = true;
    String luuNoiDung;

    SimpleDateFormat formatngay = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat formatgio = new SimpleDateFormat("HH:mm a");

    String chonDoi="";

    ArrayList<ViPhamHanhLang>dsViPhamHanhLang=new ArrayList<ViPhamHanhLang>();

// Phục vụ sao chép cơ sở dữ liệu vào
    String DATABASE_NAME="datatinnhan.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kiemtraPermission(); // kiem tra xem da co cho phep truy cap chua
        addControl();
        addEvent();
        xuLyChepDuLieuTuAssetVaoMobile();

    }

    private void xuLyChepDuLieuTuAssetVaoMobile() {
        try {
            InputStream myInput;

            myInput = getAssets().open(DATABASE_NAME);

            // Path to the just created empty db
            String outFileName = layDuongDanDatabase();

            // if the path doesn't exist first, create it
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);

            Toast.makeText(MainActivity.this,f.toString(),Toast.LENGTH_LONG).show();        //them vao xem duong dan file luu o dau

            if (!f.exists())
                f.mkdir();

            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    private String layDuongDanDatabase() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;


    }


    private void kiemtraPermission() {

        int[] result = {ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS),
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)};

       if( result[0] == PackageManager.PERMISSION_DENIED||result[1] == PackageManager.PERMISSION_DENIED) {
           Toast.makeText(MainActivity.this, "Ban phai cap quyen cho ung dung", Toast.LENGTH_SHORT).show();
           addPermission();
       }
        else if (result[0] == PackageManager.PERMISSION_GRANTED) {
       }
    }

    private void addPermission() {

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_CONTACTS},
                1);
    }

    // Lay ket qua tu thong bao co cap quyen cho truy cạp tin nhan hay khong
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    Toast.makeText(MainActivity.this, "Cho phep truy cap vao danh ba va Tin nhan", Toast.LENGTH_SHORT).show();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }





    private void addEvent() {

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this, MainThongKeActivity.class);
                startActivity(myIntent);


            }
        });

        //Loai Vi pham
        spDuong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                if (parent.getId() == R.id.spDuong) {
//                    lastSelectDuong = position1;
                    if (!lastSelectDuong) {


                        txtNoiDung.hasFocus();
                        luuNoiDung = txtNoiDung.getText().toString();
                        View et = getCurrentFocus();        //Lay v tri con tro chuot
                        txtNoiDung = (EditText) et;
                        txtNoiDung.setText(luuNoiDung.toString()+arrDuong[position1]+" ");
                        txtNoiDung.setSelection(txtNoiDung.getText().length()); // di chyen con tro chuot den cuoi
                    }
                    lastSelectDuong=false;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spLoaiVP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                if (parent.getId() == R.id.spLoai) {
//                    lastSelectLoaiVP = position2;
                    if (!lastSelectLoaiVP) {

                        txtNoiDung.hasFocus();
                        luuNoiDung = txtNoiDung.getText().toString();
                        View et = getCurrentFocus();        //Lay v tri con tro chuot
                        txtNoiDung = (EditText) et;
                        txtNoiDung.setText(luuNoiDung.toString() + arrLoaiVP[position2] + " ");
                        txtNoiDung.setSelection(txtNoiDung.getText().length()); // di chyen con tro chuot den cuoi
                    }
                    lastSelectLoaiVP=false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Danh ba

        spDanhBa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                txtSoDienThoai.setText("");
                if (parent.getId() == R.id.spDanhBa) {
//                    lastSelectDanhBa = position;
                    if (!lastSelectDanhBa)
                        txtSoDienThoai.setText(arrDanhBa[position]);
                    else lastSelectDanhBa=false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNoiDung.clearFocus();
                txtSoDienThoai.clearFocus();
                txtSoDienThoai.setText("");
                txtNoiDung.setText("");

            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 xulyThoat(v);

                // chú ý truyền tham số view vào thì mới chạy được
            }
        });


        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGui();
            }


        });

        btnDanhBa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyDanhBa();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyLuu();
            }
        });
    }



    private void xuLyLuu() {

        try {
            ViPhamHanhLang vp=new ViPhamHanhLang();
            if(rdDoi1.isChecked())
            { chonDoi=rdDoi1.getText().toString();}
            else if (rdDoi2.isChecked())
            {chonDoi=rdDoi2.getText().toString();}
            else {
                Toast.makeText(MainActivity.this, "Ban chua chon doi", Toast.LENGTH_LONG).show();
            }

            vp.setDoi(chonDoi);
            vp.setSoDienThoai(txtSoDienThoai.getText().toString());
            vp.setDuong(txtDuong.getText().toString());
            vp.setLoaiViPham(txtLoaiViPham.getText().toString());
            vp.setNoiDung(txtNoiDung.getText().toString());
            vp.setGio((txtGio.getText().toString()));

            vp.setNgay(formatngay.parse(txtNgay.getText().toString()));


            Toast.makeText(MainActivity.this,
                    vp.toString(),
                    Toast.LENGTH_LONG).show();

            dsViPhamHanhLang.add(vp);

        } catch (ParseException e) {

            // TODO Auto-generated catch block
            Toast.makeText(MainActivity.this,
                    "Loi",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }





    }

    private void xulyThoat(View v) {
        AlertDialog.Builder altdial = new AlertDialog.Builder(MainActivity.this);
        altdial.setMessage("Do you want to Quit this app ???").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = altdial.create();
        alert.setTitle("Dialog Header");
        alert.show();

    }


    private final int REQUEST_CODE=99;
    private void xuLyDanhBa() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE);
    }

    // Doạn code này lấy danh bạ và đưa số vào txtSo diện thaoị
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String num = "";
                        if (Integer.valueOf(hasNumber) == 1) {
                            Cursor numbers = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                            while (numbers.moveToNext()) {
                                num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                String namePhone = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                Toast.makeText(MainActivity.this, namePhone+":"+num, Toast.LENGTH_LONG).show();
                                txtSoDienThoai.setText(num.toString());
                            }
                        }
                    }
                    break;
                }
        }
    }


    private void xuLyGui() {

        try {
//                    final SmsManager sms = SmsManager.getDefault();
//                    sms.sendTextMessage("0984929868", null, "alo xin chao", null, null);

            final SmsManager sms = SmsManager.getDefault();
            Intent msgSent = new Intent("ACTION_MSG_SENT");
//        Intent msgSent=new Intent(Intent.ACTION_SEND);
            //Khai báo pendingintent để kiểm tra kết quả
            final PendingIntent pendingMsgSent =
                    PendingIntent.getBroadcast(this, 0, msgSent, 0);
            registerReceiver(new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    int result = getResultCode();
                    String msg = "Send OK";
                    if (result != Activity.RESULT_OK) {
                        msg = "Send failed";
                    }
                    Toast.makeText(MainActivity.this, msg,
                            Toast.LENGTH_LONG).show();
                }
            }, new IntentFilter("ACTION_MSG_SENT"));
            //Gọi hàm gửi tin nhắn đi
            sms.sendTextMessage(txtSoDienThoai.getText().toString(), null, txtGio.getText()+"."+txtNgay.getText()+":"+txtNoiDung.getText() + "",
                    pendingMsgSent, null);
            //finish();


            Toast.makeText(getApplicationContext(),
                    "Gui Thanh cong",
                    Toast.LENGTH_LONG).show();

                // Luu vao Array List

            ViPhamHanhLang vp=new ViPhamHanhLang();

            if(rdDoi1.isChecked()) chonDoi=rdDoi1.getText().toString();
            else if (rdDoi2.isChecked()) chonDoi=rdDoi2.getText().toString();
            else Toast.makeText(MainActivity.this,"Ban chua chon doi",Toast.LENGTH_LONG).show();

            vp.setDoi(chonDoi);
            vp.setSoDienThoai(txtSoDienThoai.getText().toString());
            vp.setDuong(txtDuong.getText().toString());
            vp.setLoaiViPham(txtLoaiViPham.getText().toString());
            vp.setNoiDung(txtNoiDung.getText().toString());
            vp.setNgay(formatngay.parse(txtNgay.getText().toString()));
            vp.setGio((txtGio.getText().toString()));
            dsViPhamHanhLang.add(vp);
// Kiem tra xacs nhan xem da add doi tuong duoc chua
            Toast.makeText(MainActivity.this,
                    vp.toString(),
                    Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }





    private void addControl() {
        txtSoDienThoai = (EditText) findViewById(R.id.txtSoDienThoai);
        spDanhBa = (Spinner) findViewById(R.id.spDanhBa);
        btnDanhBa = (ImageButton) findViewById(R.id.btnDanhBa);
        rdGroupDoi = (RadioGroup) findViewById(R.id.rdGroupDoi);
        rdDoi1= (RadioButton) findViewById(R.id.rdDoi1);
        rdDoi2= (RadioButton) findViewById(R.id.rdDoi2);

        txtNgay = (EditText) findViewById(R.id.txtNgay);
        txtGio= (EditText) findViewById(R.id.txtGio);
        btnNgay= (Button) findViewById(R.id.btnNgay);
        btnGio= (Button) findViewById(R.id.btnGio);

        txtNoiDung= (EditText) findViewById(R.id.txtNoiDung);
        txtLoaiViPham= (TextView) findViewById(R.id.txtLoaiVP);
        txtDuong= (TextView) findViewById(R.id.txtDuong);
        spDuong= (Spinner) findViewById(R.id.spDuong);
        spLoaiVP= (Spinner) findViewById(R.id.spLoai);
//Phần button lệnh
        btnGui= (Button) findViewById(R.id.btnGui);
        btnThoat= (Button) findViewById(R.id.btnThoat);
        btnThongKe= (Button) findViewById(R.id.btnThongKe);
        btnLuu= (Button) findViewById(R.id.btnLuu);
        btnClear= (Button) findViewById(R.id.btnClear);
        btnHuy= (Button) findViewById(R.id.btnHuy);


//Tạo dữ liệu cho Spiner DanhBa
        arrDanhBa=getResources().getStringArray(R.array.stDanhBa);
        adapterDanhba=new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                arrDanhBa);
        adapterDanhba.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spDanhBa.setAdapter(adapterDanhba);

        //Tạo dữ liệu cho Spiner Đường
        arrDuong=getResources().getStringArray(R.array.stDuong);
        adapterDuong=new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                arrDuong);
        adapterDuong.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spDuong.setAdapter(adapterDuong);

        //Tạo dữ liệu cho Spiner Loai Vi Pham
        arrLoaiVP=getResources().getStringArray(R.array.stLoaiViPham);
        adapterLoaiVP=new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                arrLoaiVP);
        adapterLoaiVP.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spLoaiVP.setAdapter(adapterLoaiVP);

        //Lay h va ngay hien tai hien thi len
        Calendar c = Calendar.getInstance();
        String ngayHienTai = formatngay.format(c.getTime());
        String gioHienTai = formatgio.format(c.getTime());
        txtNgay.setText(ngayHienTai);
        txtGio.setText(gioHienTai);


    }





}
