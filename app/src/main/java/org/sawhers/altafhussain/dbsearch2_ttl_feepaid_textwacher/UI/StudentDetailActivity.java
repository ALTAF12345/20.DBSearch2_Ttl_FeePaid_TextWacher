package org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.DATABASE.DBHelper;
import org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.MODEL.Student;
import org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.R;

public class StudentDetailActivity extends AppCompatActivity {
    EditText etName,etCourse,etTotalFee,etFeePaid;
    Button btnUpdate;
    DBHelper dbHelper;
    int StudentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
 //**********************************************************
        etName= (EditText) findViewById(R.id.et_nameD);
        etCourse= (EditText) findViewById(R.id.et_courseD);
        etTotalFee= (EditText) findViewById(R.id.et_totalFeeD);
        etFeePaid= (EditText) findViewById(R.id.et_feePaidD);
        btnUpdate= (Button) findViewById(R.id.btn_UpddatD);

        dbHelper=new DBHelper(StudentDetailActivity.this);
  //*********************************************************
        Bundle bundle=getIntent().getExtras();
        final Student student= (Student) bundle.getSerializable("STUDENT");
        etName.setText(student.getName());
        etCourse.setText(student.getCourse());
        etTotalFee.setText(String.valueOf(student.getTotalFee()));
        etFeePaid.setText(String.valueOf(student.getFeePaid()));
        StudentID=student.getId();
//*******************************************************************
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Student student1=getvaluesFromFieldD();
                int result=dbHelper.upDateStudent(student1);
                if (result>0){
                    Toast.makeText(StudentDetailActivity.this, "Student Updates ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(StudentDetailActivity.this, "Failed to Update ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private Student getvaluesFromFieldD(){

        String totalFee=etTotalFee.getText().toString().trim();
        String feePaid=etFeePaid.getText().toString().trim();
        String name=etName.getText().toString().trim();
        String course=etCourse.getText().toString().trim();
        Student student=new Student(StudentID,name,course,Integer.parseInt(totalFee),Integer.parseInt(feePaid));
  //Student(int id, String name, String course, int totalFee, int feePaid)

        return student;
    }
}
