package com.example.walter.letsmeet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity implements MainFragment.OnFragmentInteractionListener,
        CreateActivityFragment.OnFragmentInteractionListener,EditFragment.OnFragmentInteractionListener,JoinActivityFragment.OnFragmentInteractionListener{

    private static final String ACTIVITY = "activity";
    private static final String NAME = "name";
    private static final String LOCATION = "location";
    private static final String DATE = "date";
    private static final String EXIST = "exist";
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
                .add(R.id.fragment_holder,new MainFragment(getData()))//?????
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

            int j,k;
            String str;
            j = savedAnActivity.getInt(EXIST,1);
            k = savedAnActivity.getInt(NUMBER, 2);
            str = String.valueOf(j) + "/" + String.valueOf(k);
            map.put(NUMBER,str);

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

    public void setExample() {

        String str = "第一个活动的例子";
        int temp = 1;
        if (activityNames.containsValue(str)) {
            Toast.makeText(this, "活动名称已经存在", Toast.LENGTH_LONG).show();
        } else {
            while (activities.contains("acti_" + String.valueOf(temp))) {
                temp++;
            }
            editor = savedActivities.edit();
            editor.putString("acti_" + String.valueOf(temp), str);
            editor.commit();
            activities.add("acti_" + String.valueOf(temp));
            activityNames.put("acti_" + String.valueOf(temp), str);
            savedAnActivity = getSharedPreferences("acti_" + String.valueOf(temp), MODE_PRIVATE);
            editor = savedAnActivity.edit();
            editor.putString(NAME, str);
            editor.putString(LOCATION, "西直门凯德mall");
            editor.putString(DATE, "四月3号");
            editor.putInt(EXIST,3);
            editor.putInt(NUMBER, 5);
            editor.putInt(TYPE, 0);

            if (editor.commit()) {
                Toast.makeText(this, "数据写入成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "数据写入失败", Toast.LENGTH_LONG).show();
            }
        }

    }
    public void setActivityData(String name,String date,String location,int number){

        int temp = 1;

            if (activityNames.containsValue(name)) {
                Toast.makeText(this, "活动名称已经存在", Toast.LENGTH_LONG).show();
            } else {
                while (activities.contains("acti_" + String.valueOf(temp))) {
                    temp++;
                }
                editor = savedActivities.edit();
                editor.putString("acti_" + String.valueOf(temp), name);
                editor.commit();
                activities.add("acti_" + String.valueOf(temp));
                activityNames.put("acti_" + String.valueOf(temp), name);
                savedAnActivity = getSharedPreferences("acti_" + String.valueOf(temp), MODE_PRIVATE);
                editor = savedAnActivity.edit();
                editor.putString(NAME, name);
                editor.putString(LOCATION, location);
                editor.putString(DATE, date);
                editor.putInt(NUMBER, number);
                editor.putInt(EXIST,1);
                editor.putInt(TYPE, 1);

                if (editor.commit()) {
                    Toast.makeText(this, "数据写入成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "数据写入失败", Toast.LENGTH_LONG).show();
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
                .replace(R.id.fragment_holder, new CreateActivityFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
 public void onClickLetsMeetButton(String name,String date,String location,String number){

        if(name.length() <= 0){
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(MainActivity.this);

            // set dialog's message to display
            builder.setMessage(R.string.missingMessage);

            // provide an OK button that simply dismisses the dialog
            builder.setPositiveButton(R.string.OK, null);

            // create AlertDialog from the AlertDialog.Builder
            AlertDialog errorDialog = builder.create();
            errorDialog.show(); // display the modal dialog
        }
        else {

            setActivityData(name, date,location,Integer.valueOf(number));
            getFragmentManager().popBackStack();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder, new MainFragment(getData()))
                    .commit();
        }
   }

    public void onClickCancelCreateActivity(){
        onBackPressed();
    }


    @Override
    public void clickListItem(String str) {
        String key = "";
        for (String s:activities){
            if (activityNames.get(s).equals(str)){
                key = s;
            }
        }
        savedAnActivity = getSharedPreferences(key,MODE_PRIVATE);

        String actiName = savedAnActivity.getString(NAME, "");
        String location = savedAnActivity.getString(LOCATION, "");
        String date = savedAnActivity.getString(DATE, "");
        int exist = savedAnActivity.getInt(EXIST,1);
        int number = savedAnActivity.getInt(NUMBER, 2);
        int type = savedAnActivity.getInt(TYPE,5);

        if (type == 5){
            Toast.makeText(this,"活动种类未知",Toast.LENGTH_LONG).show();
        }else if (type == 0 || type == 2){
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder, new JoinActivityFragment().newInstance(actiName,location,date,exist,number,type))
                    .addToBackStack(null)
                    .commit();
        }else if (type == 1){
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder, new EditFragment().newInstance(actiName,location,date,number))
                    .addToBackStack(null)
                    .commit();
        }
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
    public void clickOKButton(String name, String newName,String location, String date, int number) {
        String key = "";
        for (String s:activities){
            if (activityNames.get(s).equals(name)){
                key = s;
            }
        }
        savedAnActivity = getSharedPreferences(key,MODE_PRIVATE);

        editor = savedAnActivity.edit();

        editor.putString(NAME,newName);
        editor.putString(LOCATION,location);
        editor.putString(DATE,date);
        editor.putInt(NUMBER,number);
        editor.putInt(TYPE,1);

        editor.commit();

        editor = savedActivities.edit();
        editor.putString(key, newName);
        editor.apply();

        activityNames.put(key,newName);

        getFragmentManager().popBackStack();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder, new MainFragment(getData()))
                .commit();
    }

    @Override
    public void clickDeleteButton(String name) {

        String key = "";
        for (String s:activities){
            if (activityNames.get(s).equals(name)){
                key = s;
            }
        }
        File file= new File("/data/data/"+getBaseContext().getPackageName().toString()+"/shared_prefs",key+".xml");

        if(file.exists()){
            file.delete();
        }

        editor = savedActivities.edit();
        editor.remove(key);
        editor.apply();

        activities.remove(key);
        activityNames.remove(key);

        getFragmentManager().popBackStack();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder, new MainFragment(getData()))
                .commit();
    }

    @Override
    public void clickJoinButton(String name,int exist) {

        String key = "";
        for (String s:activities){
            if (activityNames.get(s).equals(name)){
                key = s;
            }
        }
        savedAnActivity = getSharedPreferences(key,MODE_PRIVATE);
        editor = savedAnActivity.edit();
        editor.putInt(EXIST,exist+1);
        editor.putInt(TYPE,2);
        editor.apply();

        getFragmentManager().popBackStack();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder, new MainFragment(getData()))
                .commit();

    }

    @Override
    public void clickCancelButton(String name,int exist) {

        String key = "";
        for (String s:activities){
            if (activityNames.get(s).equals(name)){
                key = s;
            }
        }
        savedAnActivity = getSharedPreferences(key,MODE_PRIVATE);

        savedAnActivity = getSharedPreferences(key,MODE_PRIVATE);
        editor = savedAnActivity.edit();
        editor.putInt(EXIST,exist-1);
        editor.putInt(TYPE,0);
        editor.apply();

        getFragmentManager().popBackStack();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder, new MainFragment(getData()))
                .commit();
    }
}
