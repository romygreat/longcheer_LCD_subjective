package com.longcheer_lcd_subjective;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class SetPictrture_Activity extends Activity {
    private ImageView picImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.setpcitrue);
        picImageView=findViewById(R.id.picture);
       picImageView.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               SetPictrture_Activity.this.finish();
               return true;
           }
       });
    }
    public void onClick(View view){
        Toast.makeText(getApplicationContext(), "长按退出", Toast.LENGTH_SHORT).show();
    }
}
