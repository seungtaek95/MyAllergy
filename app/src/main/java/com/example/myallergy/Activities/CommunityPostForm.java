package com.example.myallergy.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.myallergy.Fragments.FragCommunity;
import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.PostVO;
import com.example.myallergy.Retrofit2.WebEndPoint;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommunityPostForm extends AppCompatActivity {

    private final int CAMERA_CODE = 1111;
    private final int REQUEST_PERMISSION_CODE = 2222;
    private final int GALLERY_CODE = 1112;
    private Uri photoUri;
    private String currentPhotoPath, imageName, currentDate;
    private ImageView ivImage;
    private ImageButton btnCamera, btnGallery;
    private EditText title, content;
    private PostVO post;
    private Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_post_form);

        initializeViews();
    }

    private void initializeViews() {
        Intent intent = getIntent();
        Date date = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat timeForm = new SimpleDateFormat("hh:mm:ss");
        currentDate = dateForm.format(date)+" "+timeForm.format(date);
        title = findViewById(R.id.form_title);
        content = findViewById(R.id.form_content);
        btnComplete = findViewById(R.id.form_complete);
        post = new PostVO();

        buttonClickListener();
    }

    private void buttonClickListener() {
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebEndPoint endPoint = getEndPoint();

                endPoint.sendCommunity(post).enqueue(new Callback<PostVO>() {
                    @Override
                    public void onResponse(Call<PostVO> call, Response<PostVO> response) {
                        addText();
                        Log.e("@@@@@@@@@@@@@@@",response.body().getContent());
                    }

                    @Override
                    public void onFailure(Call<PostVO> call, Throwable t) {

                    }
                });

                Intent intent1 = new Intent(getApplicationContext(), FragCommunity.class);
                startActivity(intent1);
            }

        });
        /*        btnCamera = (ImageButton) findViewById(R.id.form_camera);
        btnGallery = (ImageButton) findViewById(R.id.form_gallery);
        ivImage = findViewById(R.id.form_image);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.form_camera:
                        int permissionCheck = ContextCompat.checkSelfPermission(CommunityPostForm.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE);

                        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
                            selectPhoto();
                        } else {
                            requestPermission();
                            if(ActivityCompat.shouldShowRequestPermissionRationale(CommunityPostForm.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                                Toast toast = Toast.makeText(CommunityPostForm.this,"기능 사용을 위한 권한 동의가 필요합니다.",Toast.LENGTH_SHORT);
                                toast.show();
                            } else {
                                Toast toast = Toast.makeText(CommunityPostForm.this,"기능 사용을 위한 권한 동의가 필요합니다.",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }

                        break;
                }
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.form_gallery:
                        int permissionCheck = ContextCompat.checkSelfPermission(CommunityPostForm.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE);

                        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
                            selectGallery();
                        } else {
                            requestPermission();
                            if(ActivityCompat.shouldShowRequestPermissionRationale(CommunityPostForm.this,
                                    Manifest.permission.READ_EXTERNAL_STORAGE)){
                                Toast toast = Toast.makeText(CommunityPostForm.this,"기능 사용을 위한 권한 동의가 필요합니다.",Toast.LENGTH_SHORT);
                                toast.show();
                            } else {
                                Toast toast = Toast.makeText(CommunityPostForm.this,"기능 사용을 위한 권한 동의가 필요합니다.",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }!!!

                        break;
                }
            }
        });*/
    }
    // 접근요청
/*
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION_CODE);
    }
    // 갤러리 사진 선택
    private void selectPhoto() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager()) != null){
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                }catch (IOException ex){

                }
                if(photoFile != null){
                    photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                    startActivityForResult(intent, CAMERA_CODE);
                }
            }
        }
    }

    private Bitmap resize(Context context, Uri uri, int resize){
        Bitmap resizeBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();

        try{
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri),null,options);

            int width = options.outWidth;
            int height = options.outHeight;
            int samplesize = 1;

            while (true){
                if(width/2 < resize || height/2 <resize)
                    break;
                width/=2;
                height/=2;
                samplesize*=2;
            }

            options.inSampleSize = samplesize;
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
            resizeBitmap = bitmap;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return resizeBitmap;
    }
    // 카메라로 찍은 사진파일 생
    private File createImageFile() throws IOException {
        File dir = new File(Environment.getExternalStorageDirectory()+"/path/");
        if(!dir.exists()){
            dir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageName = timeStamp+".png";

        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/path/"+imageName);
        currentPhotoPath = storageDir.getAbsolutePath();

        return storageDir;
    }
    // 사진을 이미지로 가져오
    private void getPictureForPhoto() {
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        ExifInterface exif = null;

        try {
            exif = new ExifInterface(currentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int exifOrientation, exifDegree;

        if(exif != null){
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        } else {
            exifDegree = 0;
        }
        ivImage.setImageBitmap(rotate(bitmap,exifDegree)); // 이미지 뷰에 비트맵
    }
    // 갤러리 선택
    private void selectGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent, GALLERY_CODE);
    }

    private void sendPicture(Uri imgUri) {
        String imagePath = getRealPathFromUri(imgUri);
        ExifInterface exif = null;
        try{
            exif = new ExifInterface(imagePath);
        } catch (IOException e){
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        ivImage.setImageBitmap(rotate(bitmap, exifDegree));
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        } else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        } else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }
        return 0;
    }

    private String getRealPathFromUri(Uri contentUri){
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }

    private Bitmap rotate(Bitmap src, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(),matrix,true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            switch (requestCode){
                case GALLERY_CODE:
                    sendPicture(data.getData());
                    break;

                case CAMERA_CODE:
                    getPictureForPhoto();
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case REQUEST_PERMISSION_CODE:
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    selectPhoto();
                }else{
                    Toast toast = Toast.makeText(CommunityPostForm.this,"기능 사용을 위한 권한 동의가 필요합니다.",Toast.LENGTH_SHORT);
                    toast.show();
                }

                break;
        }
    }
 */

    private WebEndPoint getEndPoint() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebEndPoint.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebEndPoint endPoint = retrofit.create(WebEndPoint.class);
        return endPoint;
    }

    private void addText() {
        post.setTitle(title.getText().toString());
        //post.setDate(currentDate);
        post.setContent(content.getText().toString());
    }

}
