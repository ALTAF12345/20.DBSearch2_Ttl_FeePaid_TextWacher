package org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.DATABASE.DBHelper;
import org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.MODEL.Student;
import org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.R;

public class MainActivity extends AppCompatActivity {
    EditText etName,etCourse,etTotalFee,etFeePaid;
    Button btnSave,btnShowALL;

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName= (EditText) findViewById(R.id.et_name);
        etCourse= (EditText) findViewById(R.id.et_course);
        etTotalFee= (EditText) findViewById(R.id.et_totalFee);
        etFeePaid= (EditText) findViewById(R.id.et_feePaid);

        btnSave= (Button) findViewById(R.id.btn_Save);
        btnShowALL= (Button) findViewById(R.id.btn_showAll);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper=new DBHelper(MainActivity.this);
                Student student=getValueFromMainFild();
                long result=dbHelper.saveStudent(student);
                if (result!=0){
                    Toast.makeText(MainActivity.this, "Student Sucsusfully added ="+result, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Student Fail to add", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnShowALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,StudentListActivity.class));

            }
        });
    }
    private Student getValueFromMainFild(){
        String name=etName.getText().toString().trim();
        String course=etCourse.getText().toString().trim();
        String totalFee=etTotalFee.getText().toString().trim();
        String feePaid=etFeePaid.getText().toString().trim();

        Student student=new Student(name,course,Integer.parseInt(totalFee),Integer.parseInt(feePaid));
        return  student;
    }
}
