package com.example.walter.letsmeet;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MainFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;
    private SimpleAdapter mAdapter;
    private ArrayList<Map<String,Object>> allInformation;
    private TextView nameTextView;

    public MainFragment() {
        // Required empty public constructor
    }
    public MainFragment(ArrayList<Map<String,Object>> information){

        allInformation = information;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_main, container, false);

        mAdapter = new SimpleAdapter(getActivity(),allInformation,R.layout.list_items,
                new String[]{"name","location","date","number","type"},
                new int[]{R.id.actiNameTextView,R.id.actiLocatTextView,R.id.actiDateTextView,R.id.actiPeopleTextView,R.id.actiTypeTextView });

        setListAdapter(mAdapter);

        Button createButton = (Button)myView.findViewById(R.id.CreateButton);
        createButton.setOnClickListener(createButtonListener);
        // Inflate the layout for this fragment
        return myView;
    }

    @Override
    // TODO: Rename method, update argument and hook method into UI event
    public void onListItemClick(ListView l,View v,int position,long id) {

        nameTextView = (TextView)v.findViewById(R.id.actiNameTextView);
        String str = nameTextView.getText().toString();
        if (mListener != null) {
            mListener.clickListItem(str);
        }
    }

    public OnClickListener createButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener!=null){
                mListener.onClickCreateButton();
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
        // TODO: Update argument type and name
        public void onClickCreateButton();
        public void clickListItem(String str);
    }

}
