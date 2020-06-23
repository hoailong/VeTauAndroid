package com.example.vetau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateUpdateActivity extends AppCompatActivity {

    EditText edtGaDi, edtGaDen, edtDonGia;
    RadioGroup rdgKhuHoi;
    RadioButton rdKhuHoiTrue, rdKhuHoiFalse;
    Button btnBack, btnSave;
    VeTau veTau;
    DBHelper dbHelper;
    int idEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update);
        init();
        Intent intent = getIntent();
        veTau = (VeTau) intent.getSerializableExtra("TICKET");
        if(veTau != null) {
            idEdit = veTau.getMa();
            edtGaDi.setText(veTau.getGaDi() + "");
            edtGaDen.setText(veTau.getGaDen() + "");
            if(veTau.getKhuHoi() == 1) {
                rdKhuHoiTrue.setChecked(true);
                edtDonGia.setText(veTau.getDonGia() / 0.95 / 2 + "");
            } else {
                rdKhuHoiFalse.setChecked(true);
                edtDonGia.setText(veTau.getDonGia() + "");
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String gaDen = edtGaDen.getText().toString().trim();
                    String gaDi = edtGaDi.getText().toString().trim();
                    int khuHoi = 0;
                    double donGia = Double.parseDouble(edtDonGia.getText().toString().trim());
                    if(rdKhuHoiTrue.isChecked()) {
                        khuHoi = 1;
                        donGia = donGia * 0.95 * 2;
                    }
                    if(idEdit == -1) {
                        long inserted = dbHelper.insertData(gaDen, gaDi, donGia, khuHoi);
                        if(inserted == -1){
                            showMsg("Insert Fail");
                        }else {
                            showMsg("Insert Success");
                            Intent intent = new Intent(CreateUpdateActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        long updated = dbHelper.updateData(idEdit, gaDen, gaDi, donGia, khuHoi);
                        if(updated == -1){
                            showMsg("Update Fail");
                        }else {
                            showMsg("Update Success");
                            Intent intent = new Intent(CreateUpdateActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        rdgKhuHoi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateUpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void init() {
        idEdit = -1;
        dbHelper = new DBHelper(this);
        edtGaDen = findViewById(R.id.edtGaDen);
        edtGaDi = findViewById(R.id.edtGaDi);
        edtDonGia = findViewById(R.id.edtDonGia);
        rdgKhuHoi = findViewById(R.id.rdKhuHoi);
        rdKhuHoiTrue = findViewById(R.id.rdKhuHoiTrue);
        rdKhuHoiFalse = findViewById(R.id.rdKhuHoiFalse);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
    }

    public boolean validate() {
        if (edtGaDi.getText().toString().trim() == "" || edtGaDen.getText().toString().trim() == "" || edtDonGia.getText().toString().trim() == "") {
            showMsg("Hãy nhập đầy đủ dữ liệu");
            return false;
        }
        try {
            Double.parseDouble(edtDonGia.getText().toString().trim());
        } catch(NumberFormatException nfe) {
            showMsg("Đơn giá không hợp lệ");
            return false;
        }
        return true;
    }

    public void showMsg(String msg) {
        Toast.makeText(CreateUpdateActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}