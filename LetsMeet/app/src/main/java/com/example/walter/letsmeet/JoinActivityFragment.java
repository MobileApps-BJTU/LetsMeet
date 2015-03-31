package com.example.walter.letsmeet;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JoinActivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JoinActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinActivityFragment extends Fragment {
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "location";
    private static final String ARG_PARAM3 = "date";
    private static final String ARG_PARAM4 = "exist";
    private static final String ARG_PARAM5 = "number";
    private static final String ARG_PARAM6 = "type";
    private String name;
    private String location;
    private String date;
    private int exist;
    private int number;
    private int type;

    private OnFragmentInteractionListener mListener;
    private EditText TextView1;
    private EditText TextView2;
    private EditText TextView3;
    private EditText TextView4;
    private Button join;
    private Button cancel;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JoinActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JoinActivityFragment newInstance(String name, String location, String date,int exist, int number,int type) {
        JoinActivityFragment fragment = new JoinActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,name);
        args.putString(ARG_PARAM2,location);
        args.putString(ARG_PARAM3,date);
        args.putInt(ARG_PARAM4,exist);
        args.putInt(ARG_PARAM5,number);
        args.putInt(ARG_PARAM6,type);
        fragment.setArguments(args);
        return fragment;
    }

    public JoinActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            location = getArguments().getString(ARG_PARAM2);
            date = getArguments().getString(ARG_PARAM3);
            exist = getArguments().getInt(ARG_PARAM4);
            number = getArguments().getInt(ARG_PARAM5);
            type = getArguments().getInt(ARG_PARAM6);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView =  inflater.inflate(R.layout.fragment_join_activity, container, false);

        TextView1 = (EditText) myView.findViewById(R.id.edit_name);
        TextView2 = (EditText) myView.findViewById(R.id.edit_location);
        TextView3 = (EditText) myView.findViewById(R.id.edit_date);
        TextView4 = (EditText) myView.findViewById(R.id.edit_number);

        TextView1.setText(name);
        TextView2.setText(location);
        TextView3.setText(date);
        TextView4.setText(String.valueOf(exist) + "/" + String.valueOf(number));

        join = (Button)myView.findViewById(R.id.button_join);
        join.setOnClickListener(joinButtonListener);
        cancel = (Button)myView.findViewById(R.id.button_cancel);
        cancel.setOnClickListener(cancelButtonListener);
        if (type == 0){
            cancel.setClickable(false);
        }else if (type == 2){
            join.setClickable(false);
        }


        return myView;
    }

    public View.OnClickListener joinButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener!=null){
                mListener.clickJoinButton(name,exist);
            }
        }
    };
    public View.OnClickListener cancelButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener!=null){
                mListener.clickCancelButton(name,exist);
            }
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void clickJoinButton(String name,int exist);
        public void clickCancelButton(String name,int exist);
    }

}
