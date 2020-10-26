package com.example.lemntelattendancechecker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lemntelattendancechecker.HelperClass.EmployeeHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddEmployee extends AppCompatActivity {

    EditText getID;
    EditText getname;
    CircleImageView circleImageView;

    private StorageReference storageReference;
    private ProgressDialog progressDialog;

    private static  final int chooseImage_Req = 000;
    private Uri selectedFile; //Uri Object
    private String username;
    private String imageRef;

    private String ID;
    private String name;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("Employees");

        getID = findViewById(R.id.lemntel_ID);
        getname = findViewById(R.id.fullname);
        circleImageView = findViewById(R.id.selectedPhoto);

    }


    public void saveEmployee(View view)
    {
         ID = getID.getText().toString().trim();
         name = getname.getText().toString().trim();

        if(TextUtils.isEmpty(ID)){
            getID.setError("field cannot be empty!!!");
            return;
        }
        if(TextUtils.isEmpty(name)){
            getname.setError("field cannot be empty!!!");
            return;
        }
        if(name.length() != 0 && ID.length() !=0 && name != null && ID !=null)
        {
            if(selectedFile != null)
            {
                progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                final StorageReference photoUpload = storageReference.child(ID + "/profile");
                photoUpload.putFile(selectedFile)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                        {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                            {
                                DatabaseReference addRef = FirebaseDatabase.getInstance().getReference("Employees");
                                EmployeeHelper helper = new EmployeeHelper(ID, name);
                                addRef.child(ID).setValue(helper)
                                        .addOnSuccessListener(new OnSuccessListener<Void>()
                                        {
                                            @Override
                                            public void onSuccess(Void aVoid)
                                            {
                                                photoUpload.getDownloadUrl()
                                                        .addOnSuccessListener(new OnSuccessListener<Uri>()
                                                        {
                                                            @Override
                                                            public void onSuccess(Uri uri)
                                                            {
                                                                imageRef = uri.toString().trim();
                                                                DatabaseReference photoRef = FirebaseDatabase.getInstance().getReference("Employees/" + ID);
                                                                photoRef.child("photoUrl").setValue(imageRef);
                                                                Toast.makeText(AddEmployee.this, "employee is successfully added", Toast.LENGTH_SHORT).show();

                                                            }
                                                        });
                                                progressDialog.dismiss();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(AddEmployee.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        progressDialog.setMessage((int)progress + "% Uploaded...");
                    }
                });
            }

        }

    }

//    private void uploadFile(){
//        if(selectedFile != null){
//            progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//
//            final StorageReference profilePicUpload = storageReference.child("Employees");
//            profilePicUpload.putFile(selectedFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
//                    storageReference.child(username +"/profile").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            imageRef = uri.toString().trim();
//                            DatabaseReference addPhotoRef = FirebaseDatabase.getInstance().getReference("Registered_Users/" + username);
//                            addPhotoRef.child("profilePhotoURL").setValue(imageRef);
//                            Toast.makeText(AddEmployee.this, "File Uploaded ", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//
//                    progressDialog.dismiss();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    progressDialog.dismiss();
//                    Toast.makeText(AddEmployee.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    finish();
//
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                    double progress = (100.0 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
//                    progressDialog.setMessage((int)progress + "% Uploaded...");
//                }
//            })
//            ;
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == chooseImage_Req && resultCode ==RESULT_OK && data != null && data.getData() != null){
            selectedFile = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedFile);
                circleImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private  void fileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*"); // see images only
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, chooseImage_Req);
    }

    public void btnSelectphoto(View view) {
        fileChooser();
    }
}