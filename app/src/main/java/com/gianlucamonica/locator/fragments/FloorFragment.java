package com.gianlucamonica.locator.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.buildingFloor.BuildingFloor;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FloorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FloorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FloorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private DatabaseManager databaseManager;
    private Spinner s;
    private List<BuildingFloor> buildingFloors;

    public FloorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FloorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FloorFragment newInstance(String param1, String param2) {
        FloorFragment fragment = new FloorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        databaseManager = new DatabaseManager(getActivity());

        View v = inflater.inflate(R.layout.fragment_floor, container, false);
        s = v.findViewById(R.id.floorSpinner);


        // Inflate the layout for this fragment
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // set floor
    public void setFloorByBuilding(Building building){

        try {
           buildingFloors = databaseManager.getAppDatabase().getBuildingFloorDAO().getBuildingsFloorsByIdBuilding(building.getId());
            Log.i("building Floors",buildingFloors.toString());
            List<String> floorsName = new ArrayList<>();

            for (int i=0; i < buildingFloors.size(); i++){
                floorsName.add(buildingFloors.get(i).getName());
            }
            floorsName.add("Nessun piano");

            // populate floor spinner
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, floorsName);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
            s.setAdapter(arrayAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void populateSpinner(View v){
        /*List<String> buildingsName= new ArrayList<>();
        for (int i=0; i < buildings.size(); i++){
            buildingsName.add(buildings.get(i).getName());
        }
        // getting building spinner
        s = (Spinner) v.findViewById(R.id.spinner);
        // populate scansSpinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, buildingsName);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        s.setAdapter(arrayAdapter);*/
    }
}