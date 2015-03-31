package com.example.walter.letsmeet;

import android.app.Activity;
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
 * {@link CreateActivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CreateActivityFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private EditText activityNameText;
    private EditText activityLocationText;
    private EditText activityDateText;
    private EditText activityNumberText;

    public CreateActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView =  inflater.inflate(R.layout.fragment_create_activity, container, false);

        activityNameText = (EditText) myView.findViewById(R.id.edit_name);
        activityDateText = (EditText) myView.findViewById(R.id.edit_date);
        activityLocationText = (EditText) myView.findViewById(R.id.edit_location);
        activityNumberText = (EditText) myView.findViewById(R.id.edit_number);

        Button letsMeet = (Button)myView.findViewById(R.id.button_create);
        letsMeet.setOnClickListener(letsMeetButtonListener);
        Button back = (Button)myView.findViewById(R.id.button_back);
        back.setOnClickListener(backButtonListener);

        return myView;
    }

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

    public View.OnClickListener letsMeetButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String name = activityNameText.getText().toString();
            String date = activityDateText.getText().toString();
            String location = activityLocationText.getText().toString();
            String number = activityNumberText.getText().toString();

            if (mListener!=null){
                mListener.onClickLetsMeetButton(name,date,location,number);
            }
        }
    };

    public View.OnClickListener backButtonListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if (mListener!=null){
                mListener.onClickCancelCreateActivity();
            }
        }
    };
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
        // TODO: Update argument type and name
        public void onClickLetsMeetButton(String name,String date,String location,String number);
        public void onClickCancelCreateActivity();

    }

}
