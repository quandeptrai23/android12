package com.example.android12;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

import com.example.android12.SinhVien.SinhVienDAO;
import com.example.android12.SinhVien.SinhVienObject;

import LEVANCUONG.ReportingAdvisor;
import LEVANCUONG.StudyPlan;
import LEVANCUONG.SuggestedSubjects;


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
        btnEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intentWarning  = new Intent(MainActivity.this, ReportingAdvisor.class);
                startActivity(intentWarning);
            }
        });
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
        imageAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sms();
            }
        });
        imageSugsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sms();
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
//        clickInsert();
        Intent  intent=getIntent();
        Integer gSV=intent.getIntExtra("idSinhVien",1);
        String maSV=gSV.toString();
//        clickResult(maSV);
        //Timetable
//        imageTimetable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentTimetbable = new Intent(MainActivity.this, Timetable.class);
//                startActivity(intentTimetbable);
//            }
//        });
        //document
//        imageDocument.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentDocument = new Intent(MainActivity.this, Document.class);
//                startActivity(intentDocument);
//            }
//        });
        //mesage to teacher
//        imageMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentMesage = new Intent(MainActivity.this, Message.class);
//                startActivity(intentMesage);
//            }
//        });
        //Study plane
        imageStudyplane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStudyplane = new Intent(MainActivity.this, StudyPlan.class);
                intentStudyplane.putExtra("IDSV",getSV.getID()+"");
                startActivity(intentStudyplane);
            }

        });
        imageSugsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSugsub = new Intent(MainActivity.this, SuggestedSubjects.class);
                intentSugsub.putExtra("IDSV",getSV.getID()+"");
                startActivity(intentSugsub);
            }
        });
        imageAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sms();
            }
        });
        imgReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Chức năng đang trong quá trình phát triển", Toast.LENGTH_SHORT).show();
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
    //Thông báo tính năng đang trong quá trình phát triển
    private void Sms() {
        Toast.makeText(MainActivity.this, "Tính năng đang trong quá trình phát triển", Toast.LENGTH_SHORT).show();
    }

}