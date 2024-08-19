package com.example.android12;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android12.SinhVien.SinhVienDAO;
import com.example.android12.SinhVien.SinhVienObject;
import com.example.android12.TaiKhoan.TaiKhoanDAO;
import com.example.android12.TaiKhoan.TaiKhoanObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Login extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;
    String DATABASE_NAME="CoVanVaDieuPhoiHP.db";


    Button btn_Login,btnSignup,ok_btn;

    EditText editUsername, editPassword;
    TextView txtMiss;

    TaiKhoanDAO taiKhoanDAO;
    CheckBox chkLuuThongTin;
    String tenThongTinDangNhap="login";
    private ImageButton cancelButton;

    //
    SinhVienDAO sinhVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        processCopy();
        taiKhoanDAO = new TaiKhoanDAO(this);
        //
        sinhVienDAO = new SinhVienDAO(this);
        //
        getID();
        app();
    }

    private void app() {
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLoginState();
                if(editUsername.length() == 0 || editPassword.length() == 0){
                    Toast.makeText(Login.this, "Yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    String username = editUsername.getText().toString().trim();
                    String password = editPassword.getText().toString().trim();
                    TaiKhoanObject user = taiKhoanDAO.Login(username,password);
                    if(user != null){
                        Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                        //
                        SinhVienObject svien=sinhVienDAO.getSV(user.getID());
                        //
                        Intent intentMain =  new Intent(Login.this,MainActivity.class);
                        intentMain.putExtra("IDTK",user.getID()+"");
                        intentMain.putExtra("hoTen",user.getHoTen());
                        intentMain.putExtra("role",user.getRole());
                        intentMain.putExtra("taiKhoan",user.getTaiKhoan());
                        intentMain.putExtra("idSinhVien",user.getID());
                        startActivity(intentMain);
                        finish();
                    }
                    else{
                        Toast.makeText(Login.this, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        txtMiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View alertCustomDialog = LayoutInflater.from(Login.this).inflate(R.layout.custom_miss,null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);

                alertDialog.setView(alertCustomDialog);
                cancelButton = (ImageButton) alertCustomDialog.findViewById(R.id.cancelID);
                ok_btn = (Button) alertCustomDialog.findViewById(R.id.ok_btn_id);
                final  AlertDialog dialog = alertDialog.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        Toast.makeText(Login.this, "Cố lên.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void sms(){
        Toast.makeText(Login.this, "Chức năng đang trong quá trình phát triển", Toast.LENGTH_SHORT).show();
    }
    private void getID(){
        btn_Login =  findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        editUsername = findViewById(R.id.editUsername) ;
        editPassword = findViewById(R.id.editPassword);
        txtMiss  = findViewById(R.id.txtMiss);
        chkLuuThongTin = findViewById(R.id.chkLuuThongTin);
    }
    private void saveLoginState(){
        SharedPreferences preferences=getSharedPreferences(tenThongTinDangNhap,MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("UserName", editUsername.getText().toString());
        editor.putString("PassWord", editPassword.getText().toString());
        editor.putBoolean("Save", chkLuuThongTin.isChecked());
        editor.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(tenThongTinDangNhap,MODE_PRIVATE);
        String userName = preferences.getString("UserName","");
        String pass = preferences.getString("PassWord","");
        boolean save = preferences.getBoolean("Save",false);
        if (save) {
            editUsername.setText(userName);
            editPassword.setText(pass);
            chkLuuThongTin.setChecked(save);
        }
    }

    //HÀM COPPY CƠ SỞ DỮ LIỆU TƯ THƯ MỤC ASSET VÀO BỘ NHỚ TRONG CỦA ĐIỆN THOẠI
    private void processCopy() {
//private app
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try{CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder",
                        Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;}
    public void CopyDataBaseFromAsset() {
// TODO Auto-generated method stub
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
// Path to the just created empty db
            String outFileName = getDatabasePath();
// if the path doesn't exist first, create it
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
// Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
// transfer bytes from the inputfile to the outputfile
// Truyền bytes dữ liệu từ input đến output
            int size = myInput.available();
            byte[] buffer = new byte[size];
            myInput.read(buffer);
            myOutput.write(buffer);
// Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}