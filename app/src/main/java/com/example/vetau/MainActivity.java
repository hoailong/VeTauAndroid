package com.example.vetau;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<VeTau> listVeTau;
    DBHelper dbHelper;
    VeTauAdapter adapter;
    EditText edtSearch;
    ListView lvTicket;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtSearch = findViewById(R.id.edtSearch);
        lvTicket = findViewById(R.id.lvTicket);
        fab = findViewById(R.id.fabCreate);
        registerForContextMenu(lvTicket);
        dbHelper = new DBHelper(this);
        dbHelper.createDefaultData();
        listVeTau = dbHelper.getAllData();
        adapter = new VeTauAdapter(listVeTau, this);
        lvTicket.setAdapter(adapter);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateUpdateActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        int id = item.getItemId();
        if(id == R.id.mEdit) {
            VeTau veTau = listVeTau.get(position);
            Intent intent = new Intent(MainActivity.this, CreateUpdateActivity.class);
            intent.putExtra("TICKET", veTau);
            startActivity(intent);
        } else {
            showDeleteDialog(position);
        }
        return super.onContextItemSelected(item);
    }

    public void showDeleteDialog(final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirm");
        dialog.setMessage("Are you sure to delete?");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long deleted = dbHelper.deleteData(listVeTau.get(position).getMa());
                if(deleted == -1){
                    showMsg("Delete Fail");
                }else {
                    showMsg("Delete Success");
                    listVeTau.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    public void showMsg(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}