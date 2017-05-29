package org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.ADOPTER;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.DATABASE.DBHelper;
import org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.MODEL.Student;
import org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.R;
import org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.UI.StudentDetailActivity;

import java.util.ArrayList;

/**
 * Created by ALTAFHUSSAIN on 1/9/2017.
 */

public class CoustomAdopter_StudentList extends ArrayAdapter {

    ArrayList<Student> studentArrayList;
    DBHelper dbHelper;
    Context ctx;

    public CoustomAdopter_StudentList(Context context, ArrayList<Student> list ) {
        super(context, R.layout.coustom_adopter_studentlist, list);
        ctx=context;
        studentArrayList=list;
        dbHelper=new DBHelper(ctx);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        final Student s=studentArrayList.get(position);
        Toast.makeText(ctx, "student is ="+s, Toast.LENGTH_SHORT).show();

        LayoutInflater layoutInflater=LayoutInflater.from(ctx);
        View view=layoutInflater.inflate(R.layout.coustom_adopter_studentlist,parent,false);
        TextView tvName,tvCourse,tvTotalFee,tvFeePaid;
        ImageView ivEdit,ivDelete;
        tvName= (TextView) view.findViewById(R.id.tvName_rowStudent);
        tvCourse= (TextView) view.findViewById(R.id.tvCourse_rowStudent);
        tvTotalFee= (TextView) view.findViewById(R.id.tvTotalFee_rowStudent);
        tvFeePaid= (TextView) view.findViewById(R.id.tvFeePaid_rowStudent);
        ivDelete= (ImageView) view.findViewById(R.id.ivDelete_rowStudent);
        ivEdit= (ImageView) view.findViewById(R.id.ivEdit_rowStudent);

  //********************************************
        tvName.setText("Name :" +s.getName());
        tvCourse.setText("Course :"+s.getCourse());
        tvTotalFee.setText("Total Fee :"+s.getTotalFee());
        tvFeePaid.setText("Fee Paid :" +s.getFeePaid());
  //********************************************
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(ctx);
                builder.setMessage("are you want to Delete :"+s.getName());
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    //we need delete the student just from id
                        int studentID=s.getId();
                        int result=dbHelper.deleteStudent(String.valueOf(studentID));
                        if (result>0){
                            Toast.makeText(ctx, "Student are Successfully Delete", Toast.LENGTH_SHORT).show();
                        studentArrayList.remove(position);
                            notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(ctx, "Failed to Delete the student", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("NO",null);
                builder.show();

            }
        });
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("STUDENT",s);
                Intent intent=new Intent(ctx,StudentDetailActivity.class);
                intent.putExtras(bundle);
                ctx.startActivity(intent);

            }
        });
//***********************bg red*****************
        if (s.getTotalFee()  > s.getFeePaid())
        {
            view.setBackgroundColor(Color.DKGRAY);
        }
//*********************END**bg red*****************

        return view;
    }

}
