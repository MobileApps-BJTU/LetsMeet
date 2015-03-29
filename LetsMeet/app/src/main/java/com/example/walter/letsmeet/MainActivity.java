package com.example.walter.letsmeet;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends ActionBarActivity implements MainFragment.OnFragmentInteractionListener,
        CreateActivityFragment.OnFragmentInteractionListener{

    private static final String ACTIVITY = "activity";
    private static final String NAME = "name";
    private static final String LOCATION = "location";
    private static final String DATE = "date";
    private static final String NUMBER = "number";
    private static final String TYPE = "type";

    private SharedPreferences savedActivities,savedAnActivity;
    private SharedPreferences.Editor editor;
    private ArrayList<String> activities;
    private Map<String,String> activityNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        savedActivities = getSharedPreferences(ACTIVITY,MODE_PRIVATE);

        activities = new ArrayList<String>(savedActivities.getAll().keySet());
        activityNames = new HashMap<String,String>();

        for (String s:activities){
            activityNames.put(s,savedActivities.getString(s,""));
        }

        setExample();

        getFragmentManager().beginTransaction()
                .add(R.id.fragment_holder,new MainFragment(getData()))
                .commit();
    }

    public ArrayList<Map<String,Object>> getData(){

        ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map;

        for (String s: activities){
            savedAnActivity = getSharedPreferences(s,MODE_PRIVATE);
            map = new HashMap<String,Object>();

            map.put(NAME,savedAnActivity.getString(NAME,""));
            map.put(LOCATION,savedAnActivity.getString(LOCATION,"地点待定"));
            map.put(DATE,savedAnActivity.getString(DATE,"时间待定"));
            map.put(NUMBER,savedAnActivity.getInt(NUMBER, 2));

            int judge = savedAnActivity.getInt(TYPE,5);
            if(judge == 0){
                map.put(TYPE,"WFY");
            }else if (judge == 1){
                map.put(TYPE,"ME");
            }else if (judge == 2){
                map.put(TYPE,"LM");
            }else {
                Toast.makeText(this,"该条类型未定，不予显示",Toast.LENGTH_LONG).show();
                continue;
            }

            list.add(map);
        }

        return list;
    }

    public void setExample(){

            String str = "第一个活动的例子";
        int temp = 1;
        if (activityNames.containsValue(str)){
            Toast.makeText(this,"活动名称已经存在",Toast.LENGTH_LONG).show();
        }else{
            while(activities.contains("acti_"+String.valueOf(temp))){
                temp++;
            }
            editor = savedActivities.edit();
            editor.putString("acti_"+String.valueOf(temp),str);
            editor.commit();
            activities.add("acti_" + String.valueOf(temp));
            activityNames.put("acti_" + String.valueOf(temp),str);
            savedAnActivity = getSharedPreferences("acti_"+String.valueOf(temp),MODE_PRIVATE);
            editor = savedAnActivity.edit();
            editor.putString(NAME,str);
            editor.putString(LOCATION,"西直门凯德mall");
            editor.putString(DATE,"四月3号");
            editor.putInt(NUMBER,5);
            editor.putInt(TYPE,1);

            if (editor.commit()){
                Toast.makeText(this,"数据写入成功",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"数据写入失败",Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickCreateButton() {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder,new CreateActivityFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void clickListItem() {

    }

    @Override
    public void onBackPressed(){

        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
