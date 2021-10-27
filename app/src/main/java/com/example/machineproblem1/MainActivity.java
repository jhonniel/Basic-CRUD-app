package com.example.machineproblem1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.ListView;


import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Declaring of variable names
    DatePickerDialog picker;
    Button btnAdd, btnViewAll;
    EditText etLastName, etFirstName, etAddress, etGender, etBirthday, etContact;
    ListView lvStudentList;
    SearchView searchRecord;

    dbConnect ConnectDB;
    ArrayAdapter studentArray;

    public static int SID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Checks connection
        try {
            ConnectDB = new dbConnect(this);
            Toast.makeText(getApplicationContext(), "Database Successfully Connected!!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        btnAdd = findViewById(R.id.btnAdd);
        btnViewAll = findViewById(R.id.btnViewAll);
        etLastName = findViewById(R.id.etLastName);
        etFirstName = findViewById(R.id.etFirstName);
        etAddress = findViewById(R.id.etAddress);
        etGender = findViewById(R.id.etGender);
        etBirthday = findViewById(R.id.etBirthday);
        etContact = findViewById(R.id.etContact);
        lvStudentList = findViewById(R.id.lvStudentList);
        searchRecord = findViewById(R.id.svRecord);
        //get date input method
        BIRTH();
        //get database insertion method
        ADD_DATA();


        // btn for viewing
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view();
            }
        });


        lvStudentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Student clickID;

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickID = (Student) parent.getItemAtPosition(position);

                //shows ID of the selected row
                Toast.makeText(getApplicationContext(), "Clicked Value: " + clickID.getStudentID(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder confirmationAlert = new AlertDialog.Builder(MainActivity.this);
                //Pops up a dialog box and gives option if you want to update or delete
                confirmationAlert.setMessage("CHOOSE ACTION").setCancelable(false)
                        .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                btnAdd.setText("Update Record");
                                //update based on input
                                try {
                                    SQLiteDatabase db = getApplicationContext().openOrCreateDatabase("dbStudent.db", Context.MODE_PRIVATE, null);
                                    Cursor cursor = db.rawQuery("SELECT * FROM tblStudent WHERE ID=" + clickID.getStudentID(), null);
                                    while (cursor.moveToNext()) {
                                        int index1 = cursor.getColumnIndex("ID");
                                        int index2 = cursor.getColumnIndex("LastName");
                                        int index3 = cursor.getColumnIndex("FirstName");
                                        int index4 = cursor.getColumnIndex("Address");
                                        int index5 = cursor.getColumnIndex("Gender");
                                        int index6 = cursor.getColumnIndex("Birthday");
                                        int index7 = cursor.getColumnIndex("Contact");
                                        int index8 = cursor.getColumnIndex("Email");

                                        String LastName = cursor.getString(index2);
                                        String FirstName = cursor.getString(index3);
                                        String Age = cursor.getString(index4);
                                        String Gender = cursor.getString(index5);
                                        String Birthday = cursor.getString(index6);
                                        String Contact = cursor.getString(index7);
                                        String Email = cursor.getString(index8);


                                        etLastName.setText(LastName);
                                        etFirstName.setText(FirstName);
                                        etAddress.setText(Age);
                                        etGender.setText(Gender);
                                        etBirthday.setText(Birthday);
                                        etContact.setText(Contact);
                                        view();
                                    }
                                    SID = clickID.getStudentID();
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                        })
                        .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //delete the selected row
                                ConnectDB.DeleteRecord(clickID);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully!", Toast.LENGTH_LONG).show();
                                view();
                            }

                        });
                AlertDialog alert = confirmationAlert.create();
                alert.setTitle("OPTION");
                alert.show();
            }
        });

        searchRecord.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //shows row based on search input
                try {
                    studentArray.getFilter().filter(newText);
                    return false;
                } catch (Exception e) {
                    dbConnect ConnectDB = new dbConnect(MainActivity.this);
                    List<Student> viewAllRec = ConnectDB.viewAllData();

                    studentArray = new ArrayAdapter<Student>(MainActivity.this, android.R.layout.simple_list_item_1, viewAllRec);
                    lvStudentList.setAdapter(studentArray);
                    return false;
                }
            }
        });
    }


    public void ADD_DATA() {
        //insert method when btn clicked
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SID == 0) {
                    Student student = null;
                    try {
                        student = new Student(0, etLastName.getText().toString(),
                                etFirstName.getText().toString(),
                                etBirthday.getText().toString(),
                                etGender.getText().toString(),
                                etAddress.getText().toString(),
                                etContact.getText().toString());
                    } catch (Exception ex) {

                        Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    dbConnect ConnectDB = new dbConnect(MainActivity.this);
                    boolean checkStatus = ConnectDB.AddRecord(student);
                    Toast.makeText(MainActivity.this, "Data Inserted\n", Toast.LENGTH_LONG).show();
                    view();
                    etLastName.setText("");
                    etFirstName.setText("");
                    etBirthday.setText("");
                    etGender.setText("");
                    etAddress.setText("");
                    etContact.setText("");
                } else if (SID != 0) {
                    //if there is a selected row, btn update is clicked, SID !=0 then it will proceed to update the selected row
                    Student student = new Student(SID, etLastName.getText().toString(), etFirstName.getText().toString(), etBirthday.getText().toString(), etGender.getText().toString(), etAddress.getText().toString(), etContact.getText().toString());
                    ConnectDB.UpdateRecord(student);
                    Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_LONG).show();
                    SID = 0;
                    etLastName.setText("");
                    etFirstName.setText("");
                    etBirthday.setText("");
                    etGender.setText("");
                    etAddress.setText("");
                    etContact.setText("");
                    btnAdd.setText("Add Record");
                    view();
                }
            }


        });


    }

    public void view() {
        //view method, shows all the inputted data
        dbConnect ConnectDB = new dbConnect(MainActivity.this);
        List<Student> viewAllRec = ConnectDB.viewAllData();

        studentArray = new ArrayAdapter<Student>(MainActivity.this, android.R.layout.simple_list_item_1, viewAllRec);
        lvStudentList.setAdapter(studentArray);

    }

    public void BIRTH() {
        //datepicker pops when DOB input is clicked
        etBirthday.setInputType(InputType.TYPE_NULL);
        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            //fills the birthdate textview from the selected value on the date dialog
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                etBirthday.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


    }

}
