package org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.UI;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.ADOPTER.CoustomAdopter_StudentList;
import org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.DATABASE.DBHelper;
import org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.MODEL.Student;
import org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.R;

import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity implements View.OnClickListener {
    ListView lvStudent;
    Button btnShowAllList,btnSearch;
    //ArrayAdapter<Student> studentArrayAdapter;
    CoustomAdopter_StudentList coustomAdopter_studentList;
    ArrayList<Student> studentArrayList;
    DBHelper dbHelper;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        lvStudent= (ListView) findViewById(R.id.lv_student);
        btnSearch= (Button) findViewById(R.id.btnSearch);
        btnShowAllList= (Button) findViewById(R.id.btn_showAllList);

     //********************************
        dbHelper=new DBHelper(StudentListActivity.this);
        studentArrayList=new ArrayList<>();
        studentArrayList=dbHelper.getAllStudent();
       // studentArrayAdapter=new ArrayAdapter<Student>(StudentListActivity.this, android.R.layout.simple_list_item_1,studentArrayList);
        //lvStudent.setAdapter(studentArrayAdapter);
        coustomAdopter_studentList=new CoustomAdopter_StudentList(StudentListActivity.this,studentArrayList);
        lvStudent.setAdapter(coustomAdopter_studentList);
        //********************************
        btnShowAllList.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

//****************************************Start*TextWacher***************************
        etSearch= (EditText) findViewById(R.id.et_Search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Toast.makeText(StudentListActivity.this, etSearch.getText().toString(), Toast.LENGTH_SHORT).show();

                studentArrayList=dbHelper.TextWacherftn("name",etSearch.getText().toString());
                if (etSearch.length() !=0){


                    if (studentArrayList.size() > 0) {
                        coustomAdopter_studentList = new CoustomAdopter_StudentList(StudentListActivity.this, studentArrayList);
                        lvStudent.setAdapter(coustomAdopter_studentList);
                        coustomAdopter_studentList.notifyDataSetChanged();


                    }

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

                /*Toast.makeText(StudentListActivity.this, etSearch.getText().toString(), Toast.LENGTH_SHORT).show();
            studentArrayList=dbHelper.selectStudentPerCriteria("name",etSearch.getText().toString());

                    if (etSearch.length() !=0){


                        if (studentArrayList.size() > 0) {
                            coustomAdopter_studentList = new CoustomAdopter_StudentList(StudentListActivity.this, studentArrayList);
                            lvStudent.setAdapter(coustomAdopter_studentList);
                            coustomAdopter_studentList.notifyDataSetChanged();


                        }

                    }*/



                }

        });

 //****************************************End*TextWacher***************************


    }

    @Override
    protected void onResume() {
        super.onResume();
        showAllStudent();
    }
    private void showAllStudent(){
        dbHelper=new DBHelper(StudentListActivity.this);
        studentArrayList=new ArrayList<>();
        studentArrayList=dbHelper.getAllStudent();
        // studentArrayAdapter=new ArrayAdapter<Student>(StudentListActivity.this, android.R.layout.simple_list_item_1,studentArrayList);
        //lvStudent.setAdapter(studentArrayAdapter);
        coustomAdopter_studentList=new CoustomAdopter_StudentList(StudentListActivity.this,studentArrayList);
        lvStudent.setAdapter(coustomAdopter_studentList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_showAllList:
                showAllStudent();


                break;


            case R.id.btnSearch:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                final Dialog searchDilog = new Dialog(StudentListActivity.this);
                searchDilog.setContentView(R.layout.dialog_search);
                searchDilog.setTitle("Search");
                final Spinner spinner = (Spinner) searchDilog.findViewById(R.id.sp_Criteria);
                final EditText etValue = (EditText) searchDilog.findViewById(R.id.et_Search);
                Button btnCancel = (Button) searchDilog.findViewById(R.id.btn_Cancel);
                Button btnOkay = (Button) searchDilog.findViewById(R.id.btn_Okay);
  //**************************************************************************************
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String field = getResources().getStringArray(R.array.search_field)[position];
                        //getResources().getStringArray(R.array.search_field) is give us the search_field which is the name ,course
                        //Total Fee ,Fee Not Paid  ,[position] is used to when some on select from spiner the value mean the value
                        //so we get the position of that value and give it to field
                        if (field.equals("Name")) {
                            if (!etValue.isEnabled()) {
                                etValue.setEnabled(true);
                            }

                            etValue.setHint("Name");
                        } else if (field.equals("Course")) {

                            if (!etValue.isEnabled()) {
                                etValue.setEnabled(true);
                            }
                            etValue.setHint("Course");
                        }
                        //else if (field.equals("Fee Paid") || (field.equals("Fee Not Paid"))) {
                        else if (field.equals("Fee Paid") ) {
                            etValue.setHint("Not Required");
                            etValue.setEnabled(false);
  //************************************************************total fee, feepaid************************

                            studentArrayList=dbHelper.selectStudentPerCriteria("Fee_Paid","");
                            if (studentArrayList.size() > 0) {
                                coustomAdopter_studentList = new CoustomAdopter_StudentList(StudentListActivity.this, studentArrayList);
                                lvStudent.setAdapter(coustomAdopter_studentList);
                                coustomAdopter_studentList.notifyDataSetChanged();
                               // Toast.makeText(StudentListActivity.this, "Record Found  =" + studentArrayList.size(), Toast.LENGTH_SHORT).show();
                                searchDilog.dismiss();
                            } else {
                                Toast.makeText(StudentListActivity.this, "No One has Fee Paid", Toast.LENGTH_SHORT).show();
                                //searchDilog.dismiss();
                            }


                        }
                        else if (field.equals("Fee Not Paid") ) {
                            etValue.setHint("Not Required");
                            etValue.setEnabled(false);
                            //************************************************************total fee, feepaid************************

                            studentArrayList=dbHelper.selectStudentPerCriteria("Fee_Not_Paid","");
                            if (studentArrayList.size() > 0) {
                                coustomAdopter_studentList = new CoustomAdopter_StudentList(StudentListActivity.this, studentArrayList);
                                lvStudent.setAdapter(coustomAdopter_studentList);
                                coustomAdopter_studentList.notifyDataSetChanged();
                                // Toast.makeText(StudentListActivity.this, "Record Found  =" + studentArrayList.size(), Toast.LENGTH_SHORT).show();
                                searchDilog.dismiss();
                            } else {
                                Toast.makeText(StudentListActivity.this, "No One has Fee Paid", Toast.LENGTH_SHORT).show();
                                //searchDilog.dismiss();
                            }


                        }
//***********************************************************END*total fee, feepaid************************


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchDilog.dismiss();
                    }
                });
                btnOkay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//***********************************************************************
                        //now we work on Okaybtn to send requiest database
                        String field = (String) spinner.getSelectedItem();//this take value from spiner
                        //mean the user select name,course,total fee, fee not paid etc
                        String value = etValue.getText().toString().trim();//this is used when the user give
                        //name like altaf, ajmal etc
                        studentArrayList = dbHelper.selectStudentPerCriteria(field, value);
                        if (studentArrayList.size() > 0) {
                            coustomAdopter_studentList = new CoustomAdopter_StudentList(StudentListActivity.this, studentArrayList);
                            lvStudent.setAdapter(coustomAdopter_studentList);
                            coustomAdopter_studentList.notifyDataSetChanged();
                            Toast.makeText(StudentListActivity.this, "Record Found  =" + studentArrayList.size(), Toast.LENGTH_SHORT).show();
                            searchDilog.dismiss();
                        } else {
                            Toast.makeText(StudentListActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                            searchDilog.dismiss();
                        }

                    }

//***********************************************************************
                });

                searchDilog.show();
                break;


        }
        }}