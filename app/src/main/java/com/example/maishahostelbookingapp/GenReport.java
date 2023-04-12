package com.example.maishahostelbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class GenReport extends AppCompatActivity {
    //firebase auth to check if user is authenticated.
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private DatabaseReference payRef;

    //creating a list of objects constants
    List<Constants> paymentUsers;
    //List all permission required
    public static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static int PERMISSION_ALL = 12;
    public static File pFile;
    private File payfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_report);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("Hostel DB").child("Booking Details");
        payRef = FirebaseDatabase.getInstance().getReference("Hostel DB").child("Booking Details");



        Button reportButton = findViewById(R.id.button_disable_report);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previewDisabledUsersReport();

            }
        });

        //
        paymentUsers = new ArrayList<>();

        //create files in charity care folder
        payfile = new File("C:\\Users\\User\\Downloads\\New folder");

        //check if they exist, if not create them(directory)
        if ( !payfile.exists()) {
            payfile.mkdirs();
        }
        pFile = new File(payfile, "PaymentUsers.pdf");

        //fetch payment and disabled users details;
        fetchPaymentUsers();
    }
    //function to fetch payment data from the database
    private void fetchPaymentUsers()
    {

        payRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

//creating an object and setting to displlay
                    Constants pays=new Constants();
                    pays.setName(snapshot.child("name").getValue().toString());
                    pays.setPhone(snapshot.child("phone").getValue().toString());
                    pays.setRoom(snapshot.child("room").getValue().toString());

                    //this just log details fetched from db(you can use Timber for logging
                    Log.d("Payment", "name: " + pays.getName());
                    Log.d("Payment", "phone: " + pays.getPhone());
                    Log.d("Payment", "room: " + pays.getRoom());

                    /* The error before was cause by giving incorrect data type
                    You were adding an object of type PaymentUsers yet the arraylist expects obejct of type DisabledUsers
                     */
                    paymentUsers.add(pays);


                }
                //create a pdf file and catch exception beacause file may not be created
                try {
                    createPaymentReport(paymentUsers);
                } catch (DocumentException | FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void createPaymentReport(  List<Constants> paymentUsersList) throws DocumentException, FileNotFoundException{
        BaseColor colorWhite = WebColors.getRGBColor("#ffffff");
        BaseColor white1 = WebColors.getRGBColor("#056FAA");
        BaseColor grayColor = WebColors.getRGBColor("#425066");
        Font white = new Font(Font.FontFamily.HELVETICA, 15.0f, Font.BOLD, colorWhite);
        FileOutputStream output = new FileOutputStream(pFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{6, 25, 20, 20});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        Chunk noText = new Chunk("No.", white);
        PdfPCell noCell = new PdfPCell(new Phrase(noText));
        noCell.setFixedHeight(50);
        noCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        noCell.setVerticalAlignment(Element.ALIGN_CENTER);

        Chunk nameText = new Chunk("name", white);
        PdfPCell nameCell = new PdfPCell(new Phrase(nameText));
        nameCell.setFixedHeight(50);
        nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        nameCell.setVerticalAlignment(Element.ALIGN_CENTER);

        Chunk phoneText = new Chunk("phone", white);
        PdfPCell phoneCell = new PdfPCell(new Phrase(phoneText));
        phoneCell.setFixedHeight(50);
        phoneCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        phoneCell.setVerticalAlignment(Element.ALIGN_CENTER);

        Chunk roomText = new Chunk("room", white);
        PdfPCell roomCell = new PdfPCell(new Phrase(roomText));
        roomCell.setFixedHeight(50);
        roomCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        roomCell.setVerticalAlignment(Element.ALIGN_CENTER);


        Chunk footerText = new Chunk("Moses Njoroge - Copyright @ 2020");
        PdfPCell footCell = new PdfPCell(new Phrase(footerText));
        footCell.setFixedHeight(70);
        footCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        footCell.setVerticalAlignment(Element.ALIGN_CENTER);
        footCell.setColspan(4);


        table.addCell(noCell);
        table.addCell(nameCell);
        table.addCell(phoneCell);
        table.addCell(roomCell);
        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();


        for (PdfPCell cell : cells) {
            cell.setBackgroundColor(grayColor);
        }
        for (int i = 0; i < paymentUsersList.size(); i++) {
            Constants pay = paymentUsersList.get(i);

            String id = String.valueOf(i + 1);
            String name = pay.getName();
            String phone = pay.getPhone();
            String room = pay.getRoom();


            table.addCell(id + ". ");
            table.addCell(name);
            table.addCell(phone);
            table.addCell(room);

        }

        PdfPTable footTable = new PdfPTable(new float[]{6, 25, 20, 20});
        footTable.setTotalWidth(PageSize.A4.getWidth());
        footTable.setWidthPercentage(100);
        footTable.addCell(footCell);

        PdfWriter.getInstance(document, output);
        document.open();
        Font g = new Font(Font.FontFamily.HELVETICA, 25.0f, Font.NORMAL, grayColor);
        document.add(new Paragraph(" How to generate real-time reports using Firebase\n\n", g));
        document.add(table);
        document.add(footTable);

        document.close();
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    //onstart method used to check if the user is registered or not
    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser ==null){
            SendUserToLoginActivity();
        }
        else{
            //checking if the user exists in the firebase database
            CheckUserExistence();
        }
    }

    private void CheckUserExistence()
    {
        //get the user id
        final String currentUserId = mAuth.getCurrentUser().getUid();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!dataSnapshot.hasChild(currentUserId)){
                    //user is authenticated but but his record is not present in real time firebase database
                    SendUserToStepTwoAuthentication();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SendUserToStepTwoAuthentication()
    {
        Intent steptwoIntent = new Intent(GenReport.this,AdminDashboard.class);
        steptwoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(steptwoIntent);
        finish();
    }

    private void SendUserToLoginActivity()
    {
        Intent loginIntent = new Intent(GenReport.this, AdminDashboard.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    public void previewDisabledUsersReport()
    {
        if (hasPermissions(this, PERMISSIONS)) {
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }
    }
