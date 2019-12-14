package deshpande.abhishek.finalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class SideSelectionActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button nextButton;
    private ConstraintLayout parentlayout;

    private int count = 0;

    public String TAG = "TAG";

    private int top_x, top_y, bottom_x, bottom_y, left_shoulder_x, left_shoulder_y, right_shoulder_x, right_shoulder_y, waist_left_x, waist_left_y, chest_left_x, chest_left_y, chest_right_x, chest_right_y, waist_right_x, waist_right_y, hand_left_x, hand_left_y, hand_right_x, hand_right_y;
    private double given_height = 1.8288;
    private double top_down_distance;
    private double formula;
    //Snackbar snackbar;
    private Canvas canvas;
    private Paint paint;
    private Paint paint2;
    private Bitmap newBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_selection);

        imageView = (ImageView) findViewById(R.id.sideselectionimageview);
        nextButton = findViewById(R.id.sideselectionnextbutton);
        parentlayout = (ConstraintLayout) findViewById(R.id.sideselectionparentlayout);

        String imgPath = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("imagePath", "");
        Log.d("TAG", "onCreate: path : " + imgPath);
        File imgFile = new File(imgPath);
        if (imgFile.exists()) {
            final Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            newBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            imageView.setImageBitmap(bitmap);
            canvas = new Canvas(newBitmap);
            paint = new Paint();
            paint2 = new Paint();
            paint.setColor(ContextCompat.getColor(this, R.color.selectDark));
            paint2.setColor(ContextCompat.getColor(this, R.color.selectLight));
            int[] co = new int[2];
            int cx = imageView.getTop();
            int cy = imageView.getLeft();
            Log.d("TAG", "onCreate: co : " + cx + "," + cy);

            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            Log.i("TAG", "touched down");
                            Log.i("TAG", "onTouch: (" + x + ", " + y + ")");
                            break;
                        case MotionEvent.ACTION_MOVE:
                            Log.i("TAG", "moving: (" + x + ", " + y + ")");
                            break;
                        case MotionEvent.ACTION_UP:
                            Log.i("TAG", "touched up at : " + x + ":" + y);

                            switch (count) {
                                case 0:

                                    //Snackbar.make(parentlayout, "Enter top point", Snackbar.LENGTH_SHORT);
                                    top_x = x;
                                    top_y = y;
                                    drawOnImage(x, y);
                                    count++;
                                    break;
                                case 1:
                                    //Snackbar.make(parentlayout, "Enter bottom point", Snackbar.LENGTH_SHORT);
                                    bottom_x = x;
                                    bottom_y = y;
                                    drawOnImage(x, y);
                                    count++;
                                    break;
                                case 2:
                                    //Snackbar.make(parentlayout, "Select shoulder point", Snackbar.LENGTH_SHORT);
                                    left_shoulder_x = x;
                                    left_shoulder_y = y;
                                    drawOnImage(x, y);
                                    count++;
                                    break;
                                case 3:
                                    //Snackbar.make(parentlayout, "Select chest front point", Snackbar.LENGTH_SHORT);
                                    chest_left_x = x;
                                    chest_left_y = y;
                                    drawOnImage(x, y);
                                    count++;
                                    break;
                                case 4:
                                    //Snackbar.make(parentlayout, "Select chest back point", Snackbar.LENGTH_SHORT);
                                    chest_right_x = x;
                                    chest_right_y = y;
                                    drawOnImage(x, y);
                                    count++;
                                    break;
                                case 5:
                                    //Snackbar.make(parentlayout, "Select waist front point", Snackbar.LENGTH_SHORT);
                                    waist_left_x = x;
                                    waist_left_y = y;
                                    drawOnImage(x, y);
                                    count++;
                                    break;
                                case 6:
                                    //Snackbar.make(parentlayout, "Select waist back point", Snackbar.LENGTH_SHORT);
                                    waist_right_x = x;
                                    waist_right_y = y;
                                    drawOnImage(x, y);
                                    count++;
                                    break;


                            }

                            break;
                    }

                    return true;
                }
            });
        }

//        final Bitmap bitmap = null;
    }

    private void drawOnImage(int x, int y) {
        canvas.drawCircle(x, y, 20, paint2);
        canvas.drawCircle(x, y, 10, paint);    // for circle dot
        imageView.setImageBitmap(newBitmap);
    }

    private Bitmap rotateImage(Bitmap img) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public void onNextButtonClick(View view) {

//        double shoulder_distance = getDistance(left_shoulder_x, left_shoulder_y, right_shoulder_x, right_shoulder_y);
        double chest_distance = getDistance(chest_left_x, chest_left_y, chest_right_x, chest_right_y);
        double waist_distance = getDistance(waist_left_x, waist_left_y, waist_right_x, waist_right_y);
        //      double left_hand_distance = getDistance(hand_left_x, hand_left_y, left_shoulder_x, right_shoulder_y);
        //    double right_hand_distance = getDistance(hand_right_x, hand_left_y, right_shoulder_x, right_shoulder_y);


        top_down_distance = getDistance(top_x, top_y, bottom_x, bottom_y);

        formula = given_height / top_down_distance;

        float actualChestDistance = getActualDistance(chest_distance);
        float actualWaistDistance = getActualDistance(waist_distance);

        float chest = calculateCircumference(actualChestDistance / 2, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getFloat("chestFront", 0) / 2);
        float waist = calculateCircumference(actualWaistDistance / 2, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getFloat("waistFront", 0) / 2);

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        editor.putFloat("chestSide", actualChestDistance);
        editor.putFloat("waistSide", actualWaistDistance);
        editor.putFloat("chest", chest);
        editor.putFloat("waist", waist);
        editor.putBoolean("hasData", true);
        editor.commit();


        Log.d(TAG, "chest length : " + actualChestDistance);
        Log.d(TAG, "waist length : " + actualWaistDistance);

        Intent i = new Intent(SideSelectionActivity.this, ProfileActivity.class);
        startActivity(i);
    }

    private float calculateCircumference(float r1, float r2) {

        r1 = (float) Math.pow(r1, 2);
        r2 = (float) Math.pow(r2, 2);
        float add_divide = (r1 + r2) / 2;
        float temp = (float) Math.sqrt(add_divide);
        float ans = temp * 2 * (float) Math.PI;

        return ans;
    }

    private float getActualDistance(double distance) {

        float length = (float) formula * (float) distance;

        return length;
    }

    private double getDistance(int x1, int y1, int x2, int y2) {

        int tmp1 = x2 - x1;
        int tmp2 = y2 - y1;

        double tmp3 = Math.pow(tmp1, 2);
        double tmp4 = Math.pow(tmp2, 2);

        double distance = Math.sqrt(tmp3 + tmp4);


        return distance;
    }

}
