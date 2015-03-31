package com.example.walter.letsmeet;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {

    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "location";
    private static final String ARG_PARAM3 = "date";
    private static final String ARG_PARAM4 = "number";
    private String name;
    private String location;
    private String date;
    private int number;

    private OnFragmentInteractionListener mListener;
    private EditText myTextView1;
    private EditText myTextView2;
    private EditText myTextView3;
    private EditText myTextView4;
    private Button change;
    private Button delete;
    private Button ok;


    public static EditFragment newInstance(String name, String location, String date, int number) {
        EditFragment fragment = new EditFragment();

        Bundle args = new Bundle();

        args.putString(ARG_PARAM1,name);
        args.putString(ARG_PARAM2,location);
        args.putString(ARG_PARAM3,date);
        args.putInt(ARG_PARAM4,number);

        fragment.setArguments(args);

        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            location = getArguments().getString(ARG_PARAM2);
            date = getArguments().getString(ARG_PARAM3);
            number = getArguments().getInt(ARG_PARAM4);
        }
    }

    public EditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView =  inflater.inflate(R.layout.edit_activity, container, false);

        myTextView1 = (EditText) myView.findViewById(R.id.edit_name);
        myTextView2 = (EditText) myView.findViewById(R.id.edit_location);
         myTextView3 = (EditText) myView.findViewById(R.id.edit_date);
        myTextView4 = (EditText) myView.findViewById(R.id.edit_number);

        myTextView1.setText(name);
        myTextView2.setText(location);
        myTextView3.setText(date);
        myTextView4.setText(String.valueOf(number));

        change = (Button)myView.findViewById(R.id.button_change);
        change.setOnClickListener(changeButtonListener);
        delete = (Button)myView.findViewById(R.id.button_delete);
        delete.setOnClickListener(deleteButtonListener);
        ok = (Button)myView.findViewById(R.id.button_ok);
        ok.setOnClickListener(OKButtonListener);

        return myView;
    }

    public View.OnClickListener changeButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            myTextView1.setEnabled(true);
            myTextView2.setEnabled(true);
            myTextView3.setEnabled(true);
            myTextView4.setEnabled(true);
            change.setClickable(false);
            ok.setClickable(true);

        }
    };

    public View.OnClickListener deleteButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            if (mListener != null) {
                mListener.clickDeleteButton(name);
            }
        }
    };

    public View.OnClickListener OKButtonListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            String newName;
            newName = myTextView1.getText().toString();
            location = myTextView2.getText().toString();
            date = myTextView3.getText().toString();
            number = Integer.valueOf(myTextView4.getText().toString());

            if (mListener!=null){
                mListener.clickOKButton(name, newName, location, date, number);
            }
        }
    };


    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        public void clickOKButton(String oldName,String newName,String location,String date,int number);
        public void clickDeleteButton(String name);
    }
}
