package com.example.android12;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.android12.SinhVien.SinhVienDAO;
import com.example.android12.SinhVien.SinhVienObject;


import HOANGVANHUY.Message;


public class MainActivity extends AppCompatActivity {
    SinhVienDAO sinhVienDAO;
    SinhVienObject getSV;
    ImageView back,imageTimetable,imageDocument,imageMessage,imageResult, btnReport,btnChat, btnEvaluate,imageStudyplane,imageSugsub,imageAnother,imgReport;
    Button btnLater1,btnLater2,btnLater3,btnLater4;
    ConstraintLayout logo,logo1;
    TextView txtUsername;
    private DBHelper helper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String username = getIntent().getStringExtra("hoTen");
        String role = getIntent().getStringExtra("role");
        int IDTK = Integer.parseInt(getIntent().getStringExtra("IDTK"));

        super.onCreate(savedInstanceState);
        if(role.equals("student")){
            setContentView(R.layout.activity_main_student);
            sinhVienDAO = new SinhVienDAO(this);
            getSV = sinhVienDAO.getSV(IDTK);
            getWidgetStudent();
            appStudent();
        }
        else{
            setContentView(R.layout.activity_main_teacher);
            getWidgetTeacher();
            appTeacher();
        }
        txtUsername.setText(username);
        getBack();

    }

   private void appTeacher() {
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));

                try {
                    startActivity(emailIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Handle case where no email app is available
                }
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentImplicit = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.haui.edu.vn/vn"));
                startActivity(intentImplicit);
            }
        });
        logo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentImplicit1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fit.haui.edu.vn/vn"));
                startActivity(intentImplicit1);
            }
        });
    }

    private void appStudent() {
        //
        helper = new DBHelper(this);
        helper.getReadableDatabase();
        clickInsert();
        Intent  intent=getIntent();
        Integer gSV=intent.getIntExtra("idSinhVien",1);
        String maSV=gSV.toString();

        imageMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMesage = new Intent(MainActivity.this, Message.class);
                startActivity(intentMesage);
            }
        });
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentImplicit = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.haui.edu.vn/vn"));
                startActivity(intentImplicit);
            }
        });
        logo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentImplicit1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fit.haui.edu.vn/vn"));
                startActivity(intentImplicit1);
            }
        });

    }

    private void getWidgetStudent() {
        back = findViewById(R.id.imageback);
        txtUsername = findViewById(R.id.txtUsername);
        imageMessage = findViewById(R.id.imageMessage);
        imageTimetable = findViewById(R.id.imageTimetable);
        imageDocument = findViewById(R.id.imageDocument);
        imageResult = findViewById(R.id.imageResult);
        imageStudyplane = findViewById(R.id.btnStudyplane);
        imageSugsub = findViewById(R.id.btnSuggestedsubjects);
        imageAnother = findViewById(R.id.imgAnother);
        imgReport = findViewById(R.id.imgReport);
        logo  = findViewById(R.id.logo);
        logo1  = findViewById(R.id.logo1);
    }

    private void getWidgetTeacher(){
        back = findViewById(R.id.imageback);
        txtUsername = findViewById(R.id.txtUsername);
        logo  = findViewById(R.id.logo);
        logo1  = findViewById(R.id.logo1);
        btnChat = findViewById(R.id.btnChat);
        btnEvaluate = findViewById(R.id.btnEvaluate);
        imageSugsub = findViewById(R.id.btnSuggestedsubjects);
        imageAnother = findViewById(R.id.imgAnother);
    }
    private void getBack(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder backRequest = new AlertDialog.Builder(MainActivity.this);
                backRequest.setTitle("Xác nhận đăng xuất");
                backRequest.setMessage("Bạn có chắc chắn muốn đăng xuất");
                backRequest.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent backLogin = new Intent(MainActivity.this,Login.class);
                        startActivity(backLogin);
                        finish();
                    }
                });
                backRequest.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                backRequest.create().show();
            }

        });
    }

    private void clickInsert() {
        String insert="INSERT INTO tblMonDangKy(maMonDangKy, tenMonDangKy, trangThai, diemTongKetChu, diemTongKetSo, kyHoc, danhGiaHP, tinChiLyThuyet, tinChiThucHanh, tinChi,phanTramTietNghi,idSinhVien,maLopMonHoc) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String insertDiemThanhPhan="INSERT INTO tblDiemThanhPhan(maDiemThanhPhan, diemTX1,diemTX2, diemGiuaKy,diemCuoiKy,maMonDangKy) VALUES (?, ?, ?, ?, ?, ?)";
        db=helper.getWritableDatabase();
        //sv05
        try{
            db.execSQL(insert,new String[]{"GT","Giải Tích","Đã Hoàn Thành","B+","9.1","1","Tốt","2.0","1.0","3.0","0%","5","LP001GT001"});
            db.execSQL(insert,new String[]{"THMLN","Triết Học Mác-Lênin","Học Lại","C","5.9","1","Trung Bình","3.0","0.0","3.0","25%","5","LP001THMLN006"});
            db.execSQL(insert,new String[]{"BR1","Bóng Rổ 1","Đã Hoàn Thành","D+","4.7","1","Trung Bình","2.0","1.0","3.0","10%","5","LP001GT004"});
            db.execSQL(insert,new String[]{"GTLVH","Giao Tiếp Liên Văn Hóa","Đã Hoàn Thành","B+","7.7","1","Tốt","2.0","1.0","3.0","0%","5","LP001GT001"});
            db.execSQL(insert,new String[]{"NMVKT","Nhập Môn Về Kĩ Thuật","Đã Hoàn Thành","A","8.5","1","Tốt","3.0","0.0","3.0","5%","5","LP001GT001"});
            db.execSQL(insert,new String[]{"TACNTTCB1","Tiếng Anh Công Nghệ Thông Tin Cơ Bản 1","Đã Hoàn Thành","B+","7.7","1","Tốt","3.0","0.0","3.0","10%","5","LP001GT002"});
            db.execSQL(insert,new String[]{"DSTT","Đại Số Tuyến Tính","Đã Hoàn Thành","B+","7.9","2","Tốt","2.0","1.0","3.0","5%","5","LP001GT007"});
            db.execSQL(insert,new String[]{"KTS","Kĩ Thuật Số","Đã Hoàn Thành","B+","7.7","2","Tốt","3.0","0.0","3.0","0%","5","LP001KTS003"});
            db.execSQL(insert,new String[]{"KTLT","Kĩ Thuật Lập Trình","Đã Hoàn Thành","A","8.7","2","Tốt","2.0","1.0","3.0","25%","5","LP001KTLT009"});
            db.execSQL(insert,new String[]{"KTCTMLN","Kinh Tế Chính Trị Mác-Lênin","Đã Hoàn Thành","C+","5.7","2","Trung Bình","3.0","0.0","3.0","10%","5","LP001KTCTMLN001"});
            db.execSQL(insert,new String[]{"MMT","Mạng Máy Tính","Đã Hoàn Thành","A","8.7","3","Tốt","3.0","0.0","3.0","0%","5","LP003MMT003"});
            db.execSQL(insert,new String[]{"PTTKPM","Phân Tích Thiết Kế Phần Mềm","Đã Hoàn Thành","C+","6.7","3","Tốt","2.0","1.0","3.0","5%","5","LP001PTTK009"});
            db.execSQL(insert,new String[]{"TTHCM","Tư Tưởng Hồ Chí Minh","Đã Hoàn Thành","D+","4.7","3","Trung Bình","3.0","0.0","3.0","10%","5","LP001TT001"});
            db.execSQL(insert,new String[]{"ANM","An Ninh Mạng","Đã Hoàn Thành","B","7","3","Tốt","2.0","1.0","3.0","5%","5","LP001AN001"});
            db.execSQL(insert,new String[]{"PTDA","Phát Triển Dự Áń","Đã Hoàn Thành","B+","7.7","3","Tốt","3.0","0.0","3.0","0%","5","LP001KSS003"});
            db.execSQL(insert,new String[]{"PT","Lập Trình PyThon","Đã Hoàn Thành","A","9.7","3","Tốt","2.0","1.0","3.0","25%","5","LP001KTLT009"});

            //sv04
            db.execSQL(insert,new String[]{"GT1","Giải Tích","Đã Hoàn Thành","B+","9.1","1","Tốt","2.0","1.0","3.0","0%","4","LP001GT001"});
            db.execSQL(insert,new String[]{"THMLN1","Triết Học Mác-Lênin","Học Lại","C","5.9","1","Trung Bình","3.0","0.0","3.0","25%","4","LP001THMLN006"});
            db.execSQL(insert,new String[]{"BR11","Bóng Rổ 1","Đã Hoàn Thành","D+","4.7","1","Trung Bình","2.0","1.0","3.0","10%","4","LP001GT004"});
            db.execSQL(insert,new String[]{"GTLVH1","Giao Tiếp Liên Văn Hóa","Đã Hoàn Thành","B+","7.7","1","Tốt","2.0","1.0","3.0","0%","4","LP001GT001"});
            db.execSQL(insert,new String[]{"NMVKT1","Nhập Môn Về Kĩ Thuật","Đã Hoàn Thành","A","8.5","1","Tốt","3.0","0.0","3.0","5%","4","LP001GT001"});
            db.execSQL(insert,new String[]{"TACNTTCB11","Tiếng Anh Công Nghệ Thông Tin Cơ Bản 1","Đã Hoàn Thành","B+","7.7","1","Tốt","3.0","0.0","3.0","10%","4","LP001GT002"});
            db.execSQL(insert,new String[]{"DSTT1","Đại Số Tuyến Tính","Đã Hoàn Thành","B+","7.9","2","Tốt","2.0","1.0","3.0","5%","4","LP001GT007"});
            db.execSQL(insert,new String[]{"KTS1","Kĩ Thuật Số","Đã Hoàn Thành","B+","7.7","2","Tốt","3.0","0.0","3.0","0%","4","LP001KTS003"});
            db.execSQL(insert,new String[]{"KTLT1","Kĩ Thuật Lập Trình","Đã Hoàn Thành","A","8.7","2","Tốt","2.0","1.0","3.0","25%","4","LP001KTLT009"});
            db.execSQL(insert,new String[]{"KTCTMLN1","Kinh Tế Chính Trị Mác-Lênin","Đã Hoàn Thành","C+","5.7","2","Trung Bình","3.0","0.0","3.0","10%","4","LP001KTCTMLN001"});
            db.execSQL(insert,new String[]{"MMT1","Mạng Máy Tính","Đã Hoàn Thành","A","8.7","3","Tốt","3.0","0.0","3.0","0%","4","LP003MMT003"});
            db.execSQL(insert,new String[]{"PTTKPM1","Phân Tích Thiết Kế Phần Mềm","Đã Hoàn Thành","C+","6.7","3","Tốt","2.0","1.0","3.0","5%","4","LP001PTTK009"});
            db.execSQL(insert,new String[]{"TTHCM1","Tư Tưởng Hồ Chí Minh","Đã Hoàn Thành","D+","4.7","3","Trung Bình","3.0","0.0","3.0","10%","4","LP001TT001"});
            db.execSQL(insert,new String[]{"ANM1","An Ninh Mạng","Đã Hoàn Thành","B","7","3","Tốt","2.0","1.0","3.0","5%","4","LP001AN001"});
            db.execSQL(insert,new String[]{"PTDA1","Phát Triển Dự Áń","Đã Hoàn Thành","B+","7.7","3","Tốt","3.0","0.0","3.0","0%","4","LP001KSS003"});
            db.execSQL(insert,new String[]{"PT1","Lập Trình PyThon","Đã Hoàn Thành","A","9.7","3","Tốt","2.0","1.0","3.0","25%","4","LP001KTLT009"});

            //sv01
            db.execSQL(insert,new String[]{"GT2","Giải Tích","Đã Hoàn Thành","B+","9.1","1","Tốt","2.0","1.0","3.0","0%","1","LP001GT001"});
            db.execSQL(insert,new String[]{"THMLN2","Triết Học Mác-Lênin","Học Lại","C","5.9","1","Trung Bình","3.0","0.0","3.0","25%","1","LP001THMLN006"});
            db.execSQL(insert,new String[]{"BR12","Bóng Rổ 1","Đã Hoàn Thành","D+","4.7","1","Trung Bình","2.0","1.0","3.0","10%","1","LP001GT004"});
            db.execSQL(insert,new String[]{"GTLVH2","Giao Tiếp Liên Văn Hóa","Đã Hoàn Thành","B+","7.7","1","Tốt","2.0","1.0","3.0","0%","1","LP001GT001"});
            db.execSQL(insert,new String[]{"NMVKT2","Nhập Môn Về Kĩ Thuật","Đã Hoàn Thành","A","8.5","1","Tốt","3.0","0.0","3.0","5%","1","LP001GT001"});
            db.execSQL(insert,new String[]{"TACNTTCB12","Tiếng Anh Công Nghệ Thông Tin Cơ Bản 1","Đã Hoàn Thành","B+","7.7","1","Tốt","3.0","0.0","3.0","10%","1","LP001GT002"});
            db.execSQL(insert,new String[]{"DSTT2","Đại Số Tuyến Tính","Đã Hoàn Thành","B+","7.9","2","Tốt","2.0","1.0","3.0","5%","1","LP001GT007"});
            db.execSQL(insert,new String[]{"KTS2","Kĩ Thuật Số","Đã Hoàn Thành","B+","7.7","2","Tốt","3.0","0.0","3.0","0%","1","LP001KTS003"});
            db.execSQL(insert,new String[]{"KTLT2","Kĩ Thuật Lập Trình","Đã Hoàn Thành","A","8.7","2","Tốt","2.0","1.0","3.0","25%","1","LP001KTLT009"});
            db.execSQL(insert,new String[]{"KTCTMLN2","Kinh Tế Chính Trị Mác-Lênin","Đã Hoàn Thành","C+","5.7","2","Trung Bình","3.0","0.0","3.0","10%","1","LP001KTCTMLN001"});
            db.execSQL(insert,new String[]{"MMT2","Mạng Máy Tính","Đã Hoàn Thành","A","8.7","3","Tốt","3.0","0.0","3.0","0%","1","LP003MMT003"});
            db.execSQL(insert,new String[]{"PTTKPM2","Phân Tích Thiết Kế Phần Mềm","Đã Hoàn Thành","C+","6.7","3","Tốt","2.0","1.0","3.0","5%","1","LP001PTTK009"});
            db.execSQL(insert,new String[]{"TTHCM2","Tư Tưởng Hồ Chí Minh","Đã Hoàn Thành","D+","4.7","3","Trung Bình","3.0","0.0","3.0","10%","1","LP001TT001"});
            db.execSQL(insert,new String[]{"ANM2","An Ninh Mạng","Đã Hoàn Thành","B","7","3","Tốt","2.0","1.0","3.0","5%","1","LP001AN001"});
            db.execSQL(insert,new String[]{"PTDA2","Phát Triển Dự Áń","Đã Hoàn Thành","B+","7.7","3","Tốt","3.0","0.0","3.0","0%","1","LP001KSS003"});
            db.execSQL(insert,new String[]{"PT2","Lập Trình PyThon","Đã Hoàn Thành","A","9.7","3","Tốt","2.0","1.0","3.0","25%","1","LP001KTLT009"});

            //sv02
            db.execSQL(insert,new String[]{"GT3","Giải Tích","Đã Hoàn Thành","B+","9.1","1","Tốt","2.0","1.0","3.0","0%","2","LP001GT001"});
            db.execSQL(insert,new String[]{"THMLN3","Triết Học Mác-Lênin","Học Lại","C","5.9","1","Trung Bình","3.0","0.0","3.0","25%","2","LP001THMLN006"});
            db.execSQL(insert,new String[]{"BR13","Bóng Rổ 1","Đã Hoàn Thành","D+","4.7","1","Trung Bình","2.0","1.0","3.0","10%","2","LP001GT004"});
            db.execSQL(insert,new String[]{"GTLVH3","Giao Tiếp Liên Văn Hóa","Đã Hoàn Thành","B+","7.7","1","Tốt","2.0","1.0","3.0","0%","2","LP001GT001"});
            db.execSQL(insert,new String[]{"NMVKT3","Nhập Môn Về Kĩ Thuật","Đã Hoàn Thành","A","8.5","1","Tốt","3.0","0.0","3.0","5%","2","LP001GT001"});
            db.execSQL(insert,new String[]{"TACNTTCB13","Tiếng Anh Công Nghệ Thông Tin Cơ Bản 1","Đã Hoàn Thành","B+","7.7","1","Tốt","3.0","0.0","3.0","10%","2","LP001GT002"});
            db.execSQL(insert,new String[]{"DSTT3","Đại Số Tuyến Tính","Đã Hoàn Thành","B+","7.9","2","Tốt","2.0","1.0","3.0","5%","2","LP001GT007"});
            db.execSQL(insert,new String[]{"KTS3","Kĩ Thuật Số","Đã Hoàn Thành","B+","7.7","2","Tốt","3.0","0.0","3.0","0%","2","LP001KTS003"});
            db.execSQL(insert,new String[]{"KTLT3","Kĩ Thuật Lập Trình","Đã Hoàn Thành","A","8.7","2","Tốt","2.0","1.0","3.0","25%","2","LP001KTLT009"});
            db.execSQL(insert,new String[]{"KTCTMLN3","Kinh Tế Chính Trị Mác-Lênin","Đã Hoàn Thành","C+","5.7","2","Trung Bình","3.0","0.0","3.0","10%","2","LP001KTCTMLN001"});
            db.execSQL(insert,new String[]{"MMT3","Mạng Máy Tính","Đã Hoàn Thành","A","8.7","3","Tốt","3.0","0.0","3.0","0%","2","LP003MMT003"});
            db.execSQL(insert,new String[]{"PTTKPM3","Phân Tích Thiết Kế Phần Mềm","Đã Hoàn Thành","C+","6.7","3","Tốt","2.0","1.0","3.0","5%","2","LP001PTTK009"});
            db.execSQL(insert,new String[]{"TTHCM3","Tư Tưởng Hồ Chí Minh","Đã Hoàn Thành","D+","4.7","3","Trung Bình","3.0","0.0","3.0","10%","2","LP001TT001"});
            db.execSQL(insert,new String[]{"ANM3","An Ninh Mạng","Đã Hoàn Thành","B","7","3","Tốt","2.0","1.0","3.0","5%","2","LP001AN001"});
            db.execSQL(insert,new String[]{"PTDA3","Phát Triển Dự Áń","Đã Hoàn Thành","B+","7.7","3","Tốt","3.0","0.0","3.0","0%","2","LP001KSS003"});
            db.execSQL(insert,new String[]{"PT3","Lập Trình PyThon","Đã Hoàn Thành","A","9.7","3","Tốt","2.0","1.0","3.0","25%","2","LP001KTLT009"});
///////////////
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPMMT","7.0","8.0","6.0","7.0","MMT"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPPTTKPM","4.0","9.0","5.0","7.0","PTTKPM"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPPTDA","6.0","10.0","9.0","7.0","PTDA"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPANM","9.0","10.0","10.0","4.0","ANM"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPTTHCM","10.0","10.0","9.0","7.0","TTHCM"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPPT","10.0","10.0","9.0","9.0","PT"});
            //====================
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPGT","7.0","9.0","5.0","7.0","GT"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPTHMLN","5.5","3.0","1.0","7.0","THMLN"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPBR1","7.0","9.0","9.0","7.0","BR1"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPGTLVH","9.0","9.0","6.0","7.0","GTLVH"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPNMVKT","7.0","9.0","5.0","9.0","NMVKT"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPTACNTTCB1","7.0","9.0","5.0","8.0","TACNTTCB1"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPDSTT","4.0","10.0","9.0","7.0","DSTT"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPKTS","9.0","10.0","10.0","5.0","KTS"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPKTLT","10.0","10.0","9.0","6,0","KTLT"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPKTCTMLN","10.0","10.0","9.0","7.0","KTCTMLN"});

            //
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPMMT1","7.0","8.0","6.0","7.0","MMT1"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPPTTKPM1","4.0","9.0","5.0","7.0","PTTKPM1"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPPTDA1","6.0","10.0","9.0","7.0","PTDA1"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPANM1","9.0","10.0","10.0","4.0","ANM1"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPTTHCM1","10.0","10.0","9.0","7.0","TTHCM1"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPPT1","10.0","10.0","9.0","9.0","PT1"});
            //====================
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPGT1","7.0","9.0","5.0","7.0","GT1"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPTHMLN1","5.5","3.0","1.0","7.0","THMLN1"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPBR11","7.0","9.0","9.0","7.0","BR11"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPGTLVH1","9.0","9.0","6.0","7.0","GTLVH1"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPNMVKT1","7.0","9.0","5.0","9.0","NMVKT1"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPTACNTTCB11","7.0","9.0","5.0","8.0","TACNTTCB11"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPDSTT1","4.0","10.0","9.0","7.0","DSTT1"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPKTS1","9.0","10.0","10.0","5.0","KTS1"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPKTLT1","10.0","10.0","9.0","6,0","KTLT1"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPKTCTMLN1","10.0","10.0","9.0","7.0","KTCTMLN1"});
            //
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPMMT2","7.0","8.0","6.0","7.0","MMT2"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPPTTKPM2","4.0","9.0","5.0","7.0","PTTKPM2"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPPTDA2","6.0","10.0","9.0","7.0","PTDA2"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPANM2","9.0","10.0","10.0","4.0","ANM2"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPTTHCM2","10.0","10.0","9.0","7.0","TTHCM2"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPPT2","10.0","10.0","9.0","9.0","PT2"});
            //====================
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPGT2","7.0","9.0","5.0","7.0","GT2"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPTHMLN2","5.5","3.0","1.0","7.0","THMLN2"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPBR12","7.0","9.0","9.0","7.0","BR12"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPGTLVH2","9.0","9.0","6.0","7.0","GTLVH2"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPNMVKT2","7.0","9.0","5.0","9.0","NMVKT2"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPTACNTTCB12","7.0","9.0","5.0","8.0","TACNTTCB12"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPDSTT2","4.0","10.0","9.0","7.0","DSTT2"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPKTS2","9.0","10.0","10.0","5.0","KTS2"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPKTLT2","10.0","10.0","9.0","6,0","KTLT2"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPKTCTMLN2","10.0","10.0","9.0","7.0","KTCTMLN2"});
            //
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPMMT3","7.0","8.0","6.0","7.0","MMT3"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPPTTKPM3","4.0","9.0","5.0","7.0","PTTKPM3"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPPTDA3","6.0","10.0","9.0","7.0","PTDA3"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPANM3","9.0","10.0","10.0","4.0","ANM3"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPTTHCM3","10.0","10.0","9.0","7.0","TTHCM3"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPPT3","10.0","10.0","9.0","9.0","PT3"});
            //====================
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPGT3","7.0","9.0","5.0","7.0","GT3"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPTHMLN3","5.5","3.0","1.0","7.0","THMLN3"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPBR13","7.0","9.0","9.0","7.0","BR13"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPGTLVH3","9.0","9.0","6.0","7.0","GTLVH3"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPNMVKT3","7.0","9.0","5.0","9.0","NMVKT3"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPTACNTTCB13","7.0","9.0","5.0","8.0","TACNTTCB13"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPDSTT3","4.0","10.0","9.0","7.0","DSTT3"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPKTS3","9.0","10.0","10.0","5.0","KTS3"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPKTLT3","10.0","10.0","9.0","6,0","KTLT3"});
            db.execSQL(insertDiemThanhPhan,new String[]{"DTPKTCTMLN3","10.0","10.0","9.0","7.0","KTCTMLN3"});
            //db.execSQL(insertDiemThanhPhan,new String[]{"DTPKTLT","10.0","10.0","9.0","KTLT"});

        }
        catch (Exception e){

        }
    }

}