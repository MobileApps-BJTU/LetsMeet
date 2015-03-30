package com.example.walter.letsmeet;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
    
    private CreateActivityFragment createActivityFragment;
    private MainFragment mainFragment;
    private EditText activityNameText;
    private EditText activityLocationText;
    private EditText activityDateText;
    private EditText activityNumberText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityNameText = (EditText) findViewById(R.id.edit_name);
        activityDateText = (EditText) findViewById(R.id.edit_date);
        activityLocationText = (EditText) findViewById(R.id.edit_location);
        activityNumberText = (EditText) findViewById(R.id.edit_number);
        createActivityFragment = new CreateActivityFragment();
        mainFragment = new MainFragment();


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
            editor.putInt(NUMBER, 5);
            editor.putInt(TYPE, 1);

            if (editor.commit()) {
                Toast.makeText(this, "数据写入成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "数据写入失败", Toast.LENGTH_LONG).show();
            }
        }

    }
    public void setActivityData(){

        String nameString = activityNameText.getText().toString();
        String dateString = activityDateText.getText().toString();
        String locationString = activityDateText.getText().toString();
        int numberString = Integer.valueOf(activityDateText.getText().toString());

        int temp = 1;
        if(activityNameText.getText().toString().length() <= 0){
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
            if (activityNames.containsValue(nameString)) {
                Toast.makeText(this, "活动名称已经存在", Toast.LENGTH_LONG).show();
            } else {
                while (activities.contains("acti_" + String.valueOf(temp))) {
                    temp++;
                }
                editor = savedActivities.edit();
                editor.putString("acti_" + String.valueOf(temp), nameString);
                editor.commit();
                activities.add("acti_" + String.valueOf(temp));
                activityNames.put("acti_" + String.valueOf(temp), nameString);
                savedAnActivity = getSharedPreferences("acti_" + String.valueOf(temp), MODE_PRIVATE);
                editor = savedAnActivity.edit();
                editor.putString(NAME, nameString);
                editor.putString(LOCATION, locationString);
                editor.putString(DATE, dateString);
                editor.putInt(NUMBER, numberString);
                editor.putInt(TYPE, 1);

                if (editor.commit()) {
                    Toast.makeText(this, "数据写入成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "数据写入失败", Toast.LENGTH_LONG).show();
                }
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
                .replace(R.id.fragment_holder, createActivityFragment)
                .addToBackStack(null)
                .commit();
    }
    
    @Override
 public void onClickLetsMeetButton(){

         setActivityData();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder,new BlankFragment()).addToBackStack(null)
                .commit();

   }
    public void onClickCancelCreateActivity(){


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
