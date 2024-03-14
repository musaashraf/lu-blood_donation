package com.example.musa_ashraf.blood_donation.Fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.Toast;

import com.example.musa_ashraf.blood_donation.MainActivity;
import com.example.musa_ashraf.blood_donation.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private CircleImageView mProfileImage;
    //private ImageButton mProfileImage;
    private AlertDialog.Builder alertDialog;
    private EditText mProfileName, mProfileBloodgroup, mProfilePhone, mProfileAddress, mProfileLastDonationDate, mProfileEmail;
    private Button mEditInformation, mProfileSignout;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private FirebaseUser currentUser;
    private ProgressDialog mProgress;
    private StorageReference mStorageRef;
    private Uri mImageUri = null;
    private static final int GALLERY_REQUEST = 1;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mProfileImage = v.findViewById(R.id.profile_image);
        mProfileName = v.findViewById(R.id.profile_Username);
        mProfileBloodgroup = v.findViewById(R.id.profile_blood_group);
        mProfileAddress = v.findViewById(R.id.profile_areaAddress);
        mProfilePhone = v.findViewById(R.id.profile_phone_number);
        mProfileEmail = v.findViewById(R.id.profile_email);
        mProfileLastDonationDate = v.findViewById(R.id.profile_last_donation_date);
        mEditInformation = v.findViewById(R.id.profile_EditInformation);
        mProfileSignout = v.findViewById(R.id.profile_signout);

        mProgress = new ProgressDialog(getContext());
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Account").child(currentUser.getUid());
        mUserDatabase.keepSynced(true);


        if (mAuth.getCurrentUser() != null) {

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("Name").getValue().toString();
                    String bloodgroup = dataSnapshot.child("BloodGroup").getValue().toString();
                    String phone = dataSnapshot.child("Phone").getValue().toString();
                    String address = dataSnapshot.child("Address").getValue().toString();
                    String lastdonation = dataSnapshot.child("Last Donation").getValue().toString();
                    final String image = dataSnapshot.child("image").getValue().toString();
                    mProfileName.setText(name);
                    mProfileBloodgroup.setText(bloodgroup);
                    mProfileAddress.setText(address);
                    mProfileLastDonationDate.setText(lastdonation);
                    mProfilePhone.setText(phone);
                    mProfileEmail.setText(currentUser.getEmail());

                    if (!image.equals("null")) {
                        Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE).into(mProfileImage, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get().load(image).into(mProfileImage);

                            }
                        });
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            Toast.makeText(getContext(), "You are now SignOut", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(getActivity());*/
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST);

            }
        });


        mEditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
                /*
                EditProfileFragment fragment = new EditProfileFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,fragment);
                fragmentTransaction.commit();*/

            }

            private void startPosting() {
                mProgress.setMessage("Update Your Information ....");

                final String pName = mProfileName.getText().toString().trim();
                final String pPhone = mProfilePhone.getText().toString().trim();
                final String pAddress = mProfileAddress.getText().toString().trim();
                final String pLastDonation = mProfileLastDonationDate.getText().toString().trim();
                final String pBlood = mProfileBloodgroup.getText().toString().trim();


                if (!TextUtils.isEmpty(pName)&& !TextUtils.isEmpty(pPhone) && !TextUtils.isEmpty(pAddress) && !TextUtils.isEmpty(pLastDonation)
                        && !TextUtils.isEmpty(pBlood)){

                    if (mImageUri != null) {
                        mProgress.show();
                        final StorageReference filepath = mStorageRef.child("Blog_Image").child(mImageUri.getLastPathSegment());
                        filepath.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }
                                return filepath.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloadUri = task.getResult();

                                    mUserDatabase.child("image").setValue(downloadUri.toString());
                                    mUserDatabase.child("Name").setValue(pName).toString();
                                    mUserDatabase.child("Address").setValue(pAddress).toString();
                                    mUserDatabase.child("BloodGroup").setValue(pBlood).toString();
                                    mUserDatabase.child("Phone").setValue(pPhone).toString();
                                    mUserDatabase.child("Last Donation").setValue(pLastDonation).toString();
                                    Toast.makeText(getContext(), "Update Infomation", Toast.LENGTH_SHORT).show();
                                    mProgress.dismiss();
                                } else {
                                    Toast.makeText(getActivity(), "Upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    //mProgress.dismiss();
                                }
                            }
                        });
                    }
                    else{
                        if (!TextUtils.isEmpty(pName)&& !TextUtils.isEmpty(pPhone) && !TextUtils.isEmpty(pAddress) && !TextUtils.isEmpty(pLastDonation)
                                && !TextUtils.isEmpty(pBlood)) {
                            mUserDatabase.child("Name").setValue(pName).toString();
                            mUserDatabase.child("Address").setValue(pAddress).toString();
                            mUserDatabase.child("BloodGroup").setValue(pBlood).toString();
                            mUserDatabase.child("Phone").setValue(pPhone).toString();
                            mUserDatabase.child("Last Donation").setValue(pLastDonation).toString();
                            Toast.makeText(getContext(), "Change Successfully", Toast.LENGTH_SHORT).show();
                            mProgress.dismiss();
                        }
                    }

                }//end

            }
        });

        mProfileSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null) {

                    alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle(R.string.account_dialog_title);
                    alertDialog.setIcon(R.drawable.ic_info_black_24dp);
                    alertDialog.setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAuth.signOut();
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });

                    AlertDialog alertDialog1 = alertDialog.create();
                    alertDialog1.show();
                }
            }

        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            mProfileImage.setImageURI(mImageUri);

        }
    }
}

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            //CropImage.activity(imageUri)
            CropImage.activity(imageUri).setAspectRatio(1, 1)
                    .start(getActivity());
            //Toast.makeText(SettingActivity.this, "imageUri", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mProgress.setTitle("Uploading Image...");
                mProgress.setMessage("Please wait while we uplaoad and process the image");
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.show();

                final Uri resultUri = result.getUri();

                File thumb_filepath = new File(resultUri.getPath());//thumb image

                String current_user_id = currentUser.getUid();

                if (resultUri != null) {
                    final StorageReference thumbs_filepath = mStorageRef.child("profile_images").child("thumb").child(current_user_id + ".jpg");//thumb_image

                    final StorageReference filepath = mStorageRef.child("profile_images").child(current_user_id + ".jpg");
                    filepath.putFile(resultUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return filepath.getDownloadUrl();

                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                mUserDatabase.child("image").setValue(downloadUri.toString());
                                mProgress.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                //mProgress.dismiss();
                            }
                        }
                    });



                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        }
    }
}
*/