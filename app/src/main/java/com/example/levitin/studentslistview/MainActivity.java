package com.example.levitin.studentslistview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ArrayList<Student> students = new ArrayList<>();
    ListView studentsListView;
    ListStudent_BaseAdapter adapter;

    final static int codeResult = 1;



    private void fillStudents(){
        students.add(new Student("Ivan","Ivanov","IOT-0928", Student.Gender.MALE));
        students.add(new Student("Oleg","Olegov","IOT-0928", Student.Gender.MALE));
        students.add(new Student("Anna","Annova","IOT-0928", Student.Gender.FEMALE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillStudents();
        studentsListView = (ListView)findViewById(R.id.students_ListView);
        adapter = new ListStudent_BaseAdapter(this,students);
        studentsListView.setAdapter(adapter);
    }

    private Student findStudent(String lName,String fName) {
        for(Student st : students) {
            if(st.getLastName().equals(lName)&st.getFirstName().equals(fName))
                return st;
        }

        return null;
    }

    public void onRemoveStudentClick(View view){

        for( int i =0; i<studentsListView.getCount();++i){
            LinearLayout line= (LinearLayout)studentsListView.getChildAt(i);
            CheckBox chBox = line.findViewById(R.id.student_checkBox);
            if (chBox.isChecked()) {
                TextView lNameView =(TextView) line.findViewById(R.id.lastName);
                TextView fNameView =(TextView) line.findViewById(R.id.firstName);
                students.remove( findStudent( lNameView.getText().toString() ,fNameView.getText().toString() ) );
                adapter.notifyDataSetChanged();
            }
        }

    }

    public  void addStudentClick(View view){
        Intent intent = new Intent(this,addNewStudentActivity.class);
        startActivityForResult(intent,codeResult);
    }

    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent intent){
         if(requestCode==codeResult){
             if(resultCode==RESULT_OK){
                 Student st = (Student) intent.getSerializableExtra("student");
                 students.add(st);
              adapter.notifyDataSetChanged();

             }
      }
         else{
             super.onActivityResult(requestCode, resultCode, intent);
         }
     }
}
