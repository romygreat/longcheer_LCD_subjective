package com.longcheer_lcd_subjective;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    private TextView content_TextView;
    private  int testCase=0;//颜色切换
    int id;
    private boolean brghtnessWide=true;
    private TextView PASSTextView;
    private TextView FAILTextView;
    private boolean IsClickPASSTextView=false;
    private boolean IsClickFAILTextView=false;
    SharedPreferences  sharedPreferences;
    private  String mCurrentCase=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        PASSTextView=findViewById(R.id.textView2);
        FAILTextView=findViewById(R.id.textView3);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences("TestResult", Context.MODE_PRIVATE); //私有数据
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                switch (testCase){
                    case 0:

                        Intent intent= new Intent(MainActivity.this,Fullscreen_Color_Activity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1= new Intent(MainActivity.this,Fullscreen_brokendots_Activity.class);
                        startActivity(intent1);break;
                }
                testCase++;
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        content_TextView=findViewById(R.id.content_TextView);
        content_TextView.setOnClickListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        PASSTextView.setBackgroundColor(Color.WHITE);
        FAILTextView.setBackgroundColor(Color.WHITE);
        id = item.getItemId();
        setIsClickValue();
        if (id == R.id.colorTest) {
            mCurrentCase="颜色";
            content_TextView.setText(getString(R.string.testcolor));
        } else if (id == R.id.picture_Repair) {
            mCurrentCase="坏点";
            content_TextView.setText("测试步骤：\n观察LCD的边缘是否有坏点，黑白框是否出现扭曲\n" +
                    "\n期望结果：\nLCD的边缘有相应的坏点，黑白框没有出现扭曲\n\n点击开始测试");
        } else if (id == R.id.brightness) {
            mCurrentCase="亮度";
            content_TextView.setText("-------点击亮度调节至最亮暗-------");
        }
        else if (id == R.id.yuhua) {
            mCurrentCase="羽化";
            content_TextView.setText("点击查看羽化图片");
        }
        else if (id == R.id.nav_share) {
        } else if (id == R.id.testResult) {
            content_TextView.setText("测试结果如下显示：\n"+
                    "颜色："+sharedPreferences.getString("颜色",null)+"\n"+"坏点："+
            sharedPreferences.getString("坏点",null)+"\n"+
                            "亮度："+sharedPreferences.getString("亮度",null)+"\n"+
                            "羽化："+sharedPreferences.getString("羽化",null)
            );
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onClick(View v) {
        setIsClickValue();
        switch (v.getId()){
            case R.id.content_TextView:
                switch (id){
                    case R.id.colorTest:
                        Intent intent= new Intent(MainActivity.this,Fullscreen_Color_Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.picture_Repair:
                        Intent intent1= new Intent(MainActivity.this,Fullscreen_brokendots_Activity.class);
                        intent1.putExtra("pic","pic");
                        startActivity(intent1);
                        Toast.makeText(this, "picture", Toast.LENGTH_SHORT).show();break;
                    case R.id.brightness:
                        if (brghtnessWide)
                        {   content_TextView.setText(getResources().getString(R.string.bright_min));
                            changeAppBrightness(MainActivity.this,0);brghtnessWide=false;}
                        else  {
                            content_TextView.setText(getString(R.string.bright_max));
                            changeAppBrightness(MainActivity.this,255);brghtnessWide=true;
                    }
                    break;
                    case R.id.yuhua:
                        Intent intent2= new Intent(MainActivity.this, SetPictrture_Activity.class);
                        intent2.putExtra("yuHua","yuHua");
                        startActivity(intent2);
                        break;
                }
                break;
        }
    }
    public void changeAppBrightness(Context context, int brightness) {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
    }
    public void result(View v){
        if(v.getId()==R.id.textView2){
                if(!IsClickFAILTextView)
                  v.setBackgroundColor(Color.GREEN);
                  IsClickPASSTextView=true;
                  dayin(getString(R.string.resulttips));
            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
            editor.putString(mCurrentCase, "PASS");
            editor.commit();//提交修改
          Log.i("share", "result: "+sharedPreferences.getString("颜色","null"));
          }
          else {
            if (!IsClickPASSTextView){
                dayin(getString(R.string.resulttips));
                IsClickFAILTextView=true;
              v.setBackgroundColor(Color.RED);
              //  sharedPreferences = getSharedPreferences("TestResult", Context.MODE_PRIVATE); //私有数据
                SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                editor.putString(mCurrentCase, "FAIL");
                editor.commit();//提交修改
            }
          }
    }
    private void dayin(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
    public void setIsClickValue(){
        IsClickFAILTextView=false;
        IsClickPASSTextView=false;
    }
}
