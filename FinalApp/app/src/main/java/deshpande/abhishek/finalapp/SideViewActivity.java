package deshpande.abhishek.finalapp;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class SideViewActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private Uri imageUri;

    private Button captureBtn, bottomCaptureBtn, nextBtn;
    private FrameLayout frameLayout;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_view);

        nextBtn = findViewById(R.id.frontviewnextbutton);
        imageView = findViewById(R.id.frontviewimageview);

        frameLayout = findViewById(R.id.frontviewframelayout);
        frameLayout.setVisibility(View.INVISIBLE);

        captureBtn = findViewById(R.id.frontviewcapturebutton);
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA
                            , Manifest.permission.READ_EXTERNAL_STORAGE
                            , Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    launchCamera();
                }
            }
        });

        bottomCaptureBtn = findViewById(R.id.frontviewbottomcapturebutton);
        bottomCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA
                            , Manifest.permission.READ_EXTERNAL_STORAGE
                            , Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    launchCamera();
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SideViewActivity.this, SideSelectionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPreferences.getBoolean("hasSideImage", false)) {
//            frameLayout.setVisibility(View.INVISIBLE);
        } else {
//            frameLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void launchCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "image");
        values.put(MediaStore.Images.Media.DESCRIPTION, "image for processing");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(image);

                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;

                int resource = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resource > 0) {
                    int statusBarHeight = this.getResources().getDimensionPixelSize(resource);
                    Log.d("TAG", "onActivityResult: height ::: " + statusBarHeight);
                }
                height = height - 72;


                Bitmap b = image.createScaledBitmap(image, height, width, false);
                b = rotateImage(b);
                imageView.setImageBitmap(b);
                File imgFile = new File(getRealPathFromURI(imageUri));
                FileOutputStream fileOutputStream = new FileOutputStream(imgFile, false);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] content = stream.toByteArray();
                fileOutputStream.write(content);
                fileOutputStream.flush();
                fileOutputStream.close();

                Log.d("TAG", "onActivityResult: Image width:height ::: " + image.getWidth() + ":" + image.getHeight());
                Log.d("TAG", "onActivityResult: Screen width:height ::: " + width + ":" + height);
                Log.d("TAG", "onActivityResult: Image width:height ::: " + b.getWidth() + ":" + b.getHeight());

                String imagePath = getRealPathFromURI(imageUri);
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                editor.putString("imagePath", imagePath);
                editor.commit();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            ;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private Bitmap rotateImage(Bitmap img) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
}
