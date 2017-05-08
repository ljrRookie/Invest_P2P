package com.ljr.invest_p2p.activity;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.common.BaseActivity;
import com.ljr.invest_p2p.util.BitmapUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;

import butterknife.OnClick;

/**
 * Created by LinJiaRong on 2017/5/2.
 * TODO：
 */

public class UserInfoActivity extends BaseActivity {
    private static final int PICTURE = 0001;
    private static final int CAMERA = 0002;
    @Bind(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.iv_title_setting)
    ImageView mIvTitleSetting;
    @Bind(R.id.iv_user_icon)
    ImageView mIvUserIcon;
    @Bind(R.id.tv_user_change)
    TextView mTvUserChange;
    @Bind(R.id.btn_logout)
    Button mBtnLogout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initTitle() {
        mIvTitleBack.setVisibility(View.VISIBLE);
        mTvTitle.setText("用户信息");
        mIvTitleSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_user_change, R.id.btn_logout, R.id.iv_title_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                this.removeCurrentActivity();
                break;
            case R.id.tv_user_change:
                String[] items = new String[]{"图库", "相机"};
                new AlertDialog.Builder(this)
                        .setTitle("来源：")
                        .setIcon(R.drawable.ic_action_name)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        //启动其他应用的activity：使用隐式意图
                                        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(picture, PICTURE);
                                        break;
                                    case 1:
                                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(camera, CAMERA);
                                        break;
                                }
                            }
                        }).setCancelable(false)
                        .show();
                break;
            case R.id.btn_logout:
                /**
                 * 1.将保存在sp中的数据清除
                 * 2.将本地保存的图片的file删除
                 * 3.销毁所有的activity
                 * 4.重新进入首页面
                 */
                SharedPreferences SP = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
                //清除数据必须提交，提交以后文件仍然存在，只是文件中的数据被清除
                SP.edit().clear().commit();
                File filesDir;
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//判断sd卡是否挂载
                    //路径1：storage/sdcard/Android/data/包名/files
                    filesDir = this.getExternalFilesDir("");

                }else{//手机内部存储
                    //路径：data/data/包名/files
                    filesDir = this.getFilesDir();

                }
                File file = new File(filesDir,"icon.png");
                if(file.exists()){
                    file.delete();//删除存储中的文件
                }
                this.removeAll();
                this.goToActivity(MainActivity.class,null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA && resultCode == RESULT_OK && data != null){
            //获取intent中的图片对象
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            //对获取到的bitmap进行压缩，裁剪
            bitmap = BitmapUtils.zoom(bitmap, mIvUserIcon.getWidth(), mIvUserIcon.getHeight());
            bitmap = BitmapUtils.circleBitmap(bitmap);
            //加载显示
            mIvUserIcon.setImageBitmap(bitmap);
            //上传服务器
            //......
            //保存本地
            saveImage(bitmap);
        }else if(requestCode == PICTURE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            /**
             * android各个不同的系统版本，对于获取外部存储上的资源，返回Uri对象都不可能各不一样
             * 所以要保证无论是哪个系统版本都能正确获取到图片资料的话就需要针对各种情况进行不同的处理
             * 返回的uri根据不同的版本有不同的返回形式
             * 在4.4.2之前返回的uri是:content://media/external/images/media/3951或者file://....
             *在4.4.2返回的是content://com.android.providers.media.documents/document/image
             */
            String pathResult = getPath(selectedImage);
            //存储-->内存
            Bitmap bitmap = BitmapFactory.decodeFile(pathResult);
            Bitmap zoom = BitmapUtils.zoom(bitmap, mIvUserIcon.getWidth(), mIvUserIcon.getHeight());
            Bitmap circleBitmap = BitmapUtils.circleBitmap(zoom);
            mIvUserIcon.setImageBitmap(circleBitmap);
            //上传服务器
            //......
            //保存本地
            saveImage(circleBitmap);
        }
    }

    /**
     * Bitmap:内存层面的图片对象
     * 存储 --》 内存
     *      BitmapFactory.decodeFile(String filePath);
     *      BitmapFactory.decodeStream(InputStream is);
     * 内存 --》 存储
     *      bitmap.compress(Bitmap.CompressFormat.PNG,100,OutputStream os);
     *
     *
     * @param bitmap  将bitmap保存到本地
     */

    private void saveImage(Bitmap bitmap) {
        File filesDir;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//判断sd卡是否挂载
            //路径1：storage/sdcard/Android/data/包名/files
            filesDir = this.getExternalFilesDir("");

        }else{//手机内部存储
            //路径：data/data/包名/files
            filesDir = this.getFilesDir();

        }
        FileOutputStream fos = null;
        File file = new File(filesDir, "icon.png");
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
    /**
     * 根据系统相册选择的文件获取路径
     *
     * @param uri
     */
    @SuppressLint("NewApi")
    private String getPath(Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        //高于4.4.2的版本
        if (sdkVersion >= 19) {
            Log.e("TAG", "uri auth: " + uri.getAuthority());
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(this, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(this, contentUri, selection, selectionArgs);
            } else if (isMedia(uri)) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = this.managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                return actualimagecursor.getString(actual_image_column_index);
            }


        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * uri路径查询字段
     *
     * @param context
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isMedia(Uri uri) {
        return "media".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
