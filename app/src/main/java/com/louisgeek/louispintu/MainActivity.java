package com.louisgeek.louispintu;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    int w_count = 6;
    int h_count = 4;

    ImageView[][] mImageViews = new ImageView[w_count][h_count];
    ImageView nowNullImage;

    TextView[][] mTextViews = new TextView[w_count][h_count];
    TextView nowNullText;

    GridLayout idglimageviewgroup;

    GestureDetector mGestureDetector;

    boolean isAnimRunning=false;
    boolean isGameBegin=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idglimageviewgroup = (GridLayout) findViewById(R.id.id_gl_imageview_group);
        mGestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                int type = dealGesXY(e1.getX(), e1.getY(), e2.getX(), e2.getY());

                changeImageViewGes(type, true);
                Log.d(TAG, "onFling: type:" + type);
                return false;
            }
        });

        // initTxtData();
        initImageData();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // return super.onTouchEvent(event);
        return mGestureDetector.onTouchEvent(event);
    }

    private void initTxtData() {
        /**
         *初始化文字
         */
        //图片一张张放上去，GridLayout Z字路线   后变y
        for (int j = 0; j < h_count; j++) {
            //先变x，后变y
            for (int i = 0; i < w_count; i++) {
                TextView textView = new TextView(this);
                textView.setPadding(20, 20, 20, 20);
                textView.setText("A" + i + "B" + j);
                textView.setTag(new TxtBean(i, j, "A" + i, "B" + j));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isNear = isClickViewNearWhiteView4TextView(v);
                        //Toast.makeText(MainActivity.this, "is:"+is, Toast.LENGTH_SHORT).show();
                        if (isNear) {
                            Log.d(TAG, "onClick: true");
                            changeTextViewData(v);
                        }
                    }
                });
                idglimageviewgroup.addView(textView);
                //赋值
                mTextViews[i][j] = textView;
            }
        }
        //设置最后一个方块为空白
        setNullTextView(mTextViews[w_count - 1][h_count - 1]);
    }
    ///

    private void changeTextViewData(View v) {

        TxtBean old_click_TxtBean = (TxtBean) v.getTag();

        TxtBean nowNull_TxtBean = (TxtBean) nowNullText.getTag();
        TxtBean nowNull_TxtBean_temp = nowNull_TxtBean;//暂存

        ////
        nowNullText.setText(old_click_TxtBean.getData_x() + old_click_TxtBean.getData_y());
        //x y轴不变，其他数据变化
        nowNull_TxtBean.setData_x(old_click_TxtBean.getData_x());
        nowNull_TxtBean.setData_y(old_click_TxtBean.getData_y());

        ////
        setNullTextView((TextView) v);
        //x y轴不变，其他数据变化
        old_click_TxtBean.setData_x(nowNull_TxtBean_temp.getData_x());
        old_click_TxtBean.setData_y(nowNull_TxtBean_temp.getData_y());
    }

    /**
     * 设置空白方块
     *
     * @param textView
     */
    private void setNullTextView(TextView textView) {
        textView.setText("");
        nowNullText = textView;
    }

    /**
     * 判断 当前所点击的方块是否和空白方块相邻
     */
    private boolean isClickViewNearWhiteView4TextView(View view) {
        TxtBean txtBean = (TxtBean) view.getTag();
        int x = txtBean.getX();
        int y = txtBean.getY();
        Log.d(TAG, "isClickViewNearWhiteView: " + x + y);

        TxtBean nowNullImage_TxtBean = (TxtBean) nowNullText.getTag();
        int null_x = nowNullImage_TxtBean.getX();
        int null_y = nowNullImage_TxtBean.getY();

        Log.d(TAG, "isClickViewNearWhiteView: null_xy" + null_x + null_y);
        //nullView的左边
        if (null_x - x == 1 && null_y - y == 0) {
            Log.d(TAG, "nullView的左边");
            return true;
        }
        //nullView的右边
        if (x - null_x == 1 && null_y - y == 0) {
            Log.d(TAG, "nullView的右边");
            return true;
        }
        //nullView的上边
        if (null_y - y == 1 && null_x - x == 0) {
            Log.d(TAG, "nullView的上边");
            return true;
        }
        //nullView的下边
        if (y - null_y == 1 && null_x - x == 0) {
            Log.d(TAG, "nullView的下边");
            return true;
        }
        return false;
    }


    private void initImageData() {
        Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.game_696_464);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmapBigImage = bitmapDrawable.getBitmap();
        int childImage_w_h = bitmapBigImage.getWidth() / w_count;

        /**
         *初始化图片
         */
        //图片一张张放上去，GridLayout Z字路线   后变y
        for (int j = 0; j < h_count; j++) {
            //先变x，后变y
            for (int i = 0; i < w_count; i++) {
                Bitmap childImageBm = Bitmap.createBitmap(bitmapBigImage, i * childImage_w_h, j * childImage_w_h, childImage_w_h, childImage_w_h);
                ImageView childImageView = new ImageView(this);
                childImageView.setPadding(1, 1, 1, 1);
                childImageView.setImageBitmap(childImageBm);
                childImageView.setTag(new ImageDataBean(i, j, childImageBm,i,j));
                childImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isNear = isClickViewNearWhiteView(v);
                        Log.d(TAG, "onClick: xxx" + v.getTag());
                        //Toast.makeText(MainActivity.this, "is:"+is, Toast.LENGTH_SHORT).show();
                        if (isNear) {
                            Log.d(TAG, "onClick: changeImageView");
                            changeImageViewInner((ImageView) v, true);
                        }
                    }
                });
                idglimageviewgroup.addView(childImageView);
                //
                mImageViews[i][j] = childImageView;
            }
        }
        //设置最后一个方块为空白
        setNullImageView(mImageViews[w_count - 1][h_count - 1]);
        //打乱移动
        randomMore();
        isGameBegin=true;
    }

    private void changeImageViewGes(int type, boolean isAnim) {
        ImageView imageview = getTheViewNearNullImage(type);
        if (imageview != null) {
            changeImageViewInner(imageview, isAnim);
        }
    }

    private void changeImageViewInner(final ImageView imageView, boolean isHasAnim) {
        if (isAnimRunning){
            return;
        }
        if (!isHasAnim) {
            changeImageViewsData(imageView);
            return;
        }
        TranslateAnimation translateAnimation = null;
        if (imageView.getX() > nowNullImage.getX()) {
            //图案右滑到左
            Log.d(TAG, "changeImageView: 图案右滑到左");
            translateAnimation = new TranslateAnimation(0, -imageView.getWidth(), 0, 0);
        } else if (imageView.getX() < nowNullImage.getX()) {
            //图案左滑到右
            Log.d(TAG, "changeImageView: 图案左滑到右");
            translateAnimation = new TranslateAnimation(0, imageView.getWidth(), 0, 0);
        } else if (imageView.getY() > nowNullImage.getY()) {
            //图案下滑到上
            Log.d(TAG, "changeImageView: 图案下滑到上");
            translateAnimation = new TranslateAnimation(0, 0, 0, -imageView.getWidth());
        } else if (imageView.getY() < nowNullImage.getY()) {
            //图案上滑到下
            Log.d(TAG, "changeImageView: 图案上滑到下");
            translateAnimation = new TranslateAnimation(0, 0, 0, imageView.getWidth());
        }

        translateAnimation.setDuration(100);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimRunning=true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimRunning=false;
                imageView.clearAnimation();
                changeImageViewsData(imageView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(translateAnimation);
    }

    private void changeImageViewsData(ImageView imageView) {
        ImageDataBean old_click_ImageDataBean = (ImageDataBean) imageView.getTag();
        ImageDataBean nowNull_ImageDataBean = (ImageDataBean) nowNullImage.getTag();
        ImageDataBean nowNull_ImageDataBean_temp = nowNull_ImageDataBean;//暂存

        ////
        nowNullImage.setImageBitmap(old_click_ImageDataBean.getBitmap());
        //x y轴不变，其他数据变化
        nowNull_ImageDataBean.setBitmap(old_click_ImageDataBean.getBitmap());
        nowNull_ImageDataBean.setData_x(old_click_ImageDataBean.getData_x());
        nowNull_ImageDataBean.setData_y(old_click_ImageDataBean.getData_y());

        ////
        setNullImageView(imageView);
        //x y轴不变，其他数据变化
        old_click_ImageDataBean.setBitmap(nowNull_ImageDataBean_temp.getBitmap());
        old_click_ImageDataBean.setData_x(nowNull_ImageDataBean_temp.getData_x());
        old_click_ImageDataBean.setData_y(nowNull_ImageDataBean_temp.getData_y());
        //
        if (isGameBegin){
            dealIsGameOver();
        }

    }

    /**
     * 设置空白方块
     *
     * @param imageview
     */
    private void setNullImageView(ImageView imageview) {
        imageview.setImageBitmap(null);
        nowNullImage = imageview;
    }

    /**
     * 判断 当前所点击的方块是否和空白方块相邻
     */
    private boolean isClickViewNearWhiteView(View view) {
        ImageDataBean imageDataBean = (ImageDataBean) view.getTag();
        int x = imageDataBean.getX();
        int y = imageDataBean.getY();

        Log.d(TAG, "isClickViewNearWhiteView: " + x + y);

        ImageDataBean nowNullImage_imageDataBean = (ImageDataBean) nowNullImage.getTag();
        int null_x = nowNullImage_imageDataBean.getX();
        int null_y = nowNullImage_imageDataBean.getY();

        Log.d(TAG, "isClickViewNearWhiteView: null_xy" + null_x + null_y);

        //nullView的左边
        if (null_x - x == 1 && null_y - y == 0) {
            Log.d(TAG, "nullView的左边");
            return true;
        }
        //nullView的右边
        if (x - null_x == 1 && null_y - y == 0) {
            Log.d(TAG, "nullView的右边");
            return true;
        }
        //nullView的上边
        if (null_y - y == 1 && null_x - x == 0) {
            Log.d(TAG, "nullView的上边");
            return true;
        }
        //nullView的下边
        if (y - null_y == 1 && null_x - x == 0) {
            Log.d(TAG, "nullView的下边");
            return true;
        }
        return false;
    }


    private int dealGesXY(float start_X, float start_Y, float end_X, float end_Y) {
        boolean isLeftRight = Math.abs(start_X - end_X) > Math.abs(start_Y - end_Y) ? true : false;
        if (isLeftRight) {
            boolean isLeft = start_X - end_X > 0 ? true : false;
            if (isLeft) {
                return 1;
            } else {
                return 2;
            }
        } else {
            boolean isUp = start_Y - end_Y > 0 ? true : false;
            if (isUp) {
                return 3;
            } else {
                return 4;
            }
        }
    }

    private ImageView getTheViewNearNullImage(int type) {
        ImageDataBean imageDataBeanNowNullImage = (ImageDataBean) nowNullImage.getTag();
        int x = imageDataBeanNowNullImage.getX();
        int y = imageDataBeanNowNullImage.getY();
        switch (type) {
            //左(图案在白的右边)
            case 1:
                if (x + 1 <= (w_count - 1)) {
                    return mImageViews[x + 1][y];
                }
                break;
            //右
            case 2:
                if (x - 1 >= 0) {
                    return mImageViews[x - 1][y];
                }
                break;
            //上
            case 3:
                if (y + 1 <= (h_count - 1)) {
                    return mImageViews[x][y + 1];
                }
                break;
            //下
            case 4:
                if (y - 1 >= 0) {
                    return mImageViews[x][y - 1];
                }
                break;
        }

        return null;
    }

    private void randomMore() {
        for (int i = 0; i < 20; i++) {
            //Math.random():大于等于 0.0 且小于 1.0    随机1到5(取不到5)的变量
            int type = (int) (Math.random() * 4) + 1;
            Log.d(TAG, "randomMore: type:" + type);
           /* int type2= (int) (Math.random()*4+1);
            Log.d(TAG, "randomMore: type2:"+type2);*/
            changeImageViewGes(type, false);
        }
    }
    
    
    private  void dealIsGameOver(){
        boolean isGameOver=false;
        for (int i = 0; i < w_count; i++) {
            for (int j = 0; j < h_count; j++) {
                if (mImageViews[i][j]==nowNullImage){
                    continue;
                }
               ImageDataBean idb= (ImageDataBean) mImageViews[i][j].getTag();
                if(idb.isRightXY()){
                    isGameOver=true;
                    break;
                }
            }
        }
        if (isGameOver){
            Toast.makeText(MainActivity.this, "game over", Toast.LENGTH_SHORT).show();
        }
    }

}
