package com.gianlucamonica.locator.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.activities.main.MainActivity;
import com.gianlucamonica.locator.myLocationManager.MyLocationManager;
import com.gianlucamonica.locator.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
import com.gianlucamonica.locator.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locator.myLocationManager.utils.db.building.BuildingDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BuildingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuildingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuildingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<Building> buildings;
    Spinner  s;

    private DatabaseManager databaseManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BuildingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuildingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuildingFragment newInstance(String param1, String param2) {
        BuildingFragment fragment = new BuildingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        databaseManager = new DatabaseManager(getActivity());
        buildings = new ArrayList<>();
        buildings = getBuildingsFromDb();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.building_fragment, container, false);

        populateSpinner(v);

        // getting selected item from spinner
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Building chosenBuilding = getSelectedBuilding();
                mListener.onFragmentInteraction(chosenBuilding,"building");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Building chosenBuilding = getSelectedBuilding();
            }
        });





        // start activity insert building
        Button newButton = (Button) v.findViewById(R.id.newBuildingButton);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),InsertBuildingActivity.class));
            }
        });

        return v;

    }

    public List<Building> getBuildingsFromDb(){
        BuildingDAO buildingDAO = databaseManager.getAppDatabase().getBuildingDAO();
        return buildingDAO.getBuildings();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri,"");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        buildings = getBuildingsFromDb();
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
        void onFragmentInteraction(Object object,String tag);
    }

    public void populateSpinner(View v){
        List<String> buildingsName= new ArrayList<>();
        for (int i=0; i < buildings.size(); i++){
            buildingsName.add(buildings.get(i).getName());
        }
        // spinner
        s = (Spinner) v.findViewById(R.id.spinner);
        // populate spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, buildingsName);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        s.setAdapter(arrayAdapter);
    }

    public Building getSelectedBuilding(){
        String building =  s.getSelectedItem().toString();
        for(int i=0; i<buildings.size(); i++){
            if(buildings.get(i).getName().equals(building)){
                return buildings.get(i);
            }
        }
        return null;
    }

    public void sendBuildingToFragment(final Building chosenBuilding,final  Fragment fragment){
        Bundle args = new Bundle();
        args.putSerializable("building", (Serializable) chosenBuilding);
        fragment.setArguments(args);
        Log.i("sending ", chosenBuilding.toString());
        getFragmentManager().beginTransaction().replace(R.id.scanLayout, fragment).commitAllowingStateLoss();
    }

}
