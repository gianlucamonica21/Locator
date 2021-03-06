package com.gianlucamonica.locator.fragments.floor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gianlucamonica.locator.R;
import com.gianlucamonica.locator.myLocationManager.utils.indoorParams.IndoorParamName;
import com.gianlucamonica.locator.myLocationManager.utils.MyApp;
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

        databaseManager = new DatabaseManager();

        View v = inflater.inflate(R.layout.fragment_floor, container, false);
        s = v.findViewById(R.id.floorSpinner);

        // getting selected item from floor spinner
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BuildingFloor chosenFloor = getSelectedFloor();
                Log.i("chosen floor","chosen floor " + chosenFloor);
                mListener.onFragmentInteraction(chosenFloor, IndoorParamName.FLOOR); // comunico all'activity il floor scelto
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                BuildingFloor chosenFloor = getSelectedFloor();
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

    public void enableUI(boolean b){
        s.setEnabled(b);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
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
        void onFragmentInteraction(Object object, IndoorParamName tag);
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
            if(buildingFloors.size() == 0)
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

    public BuildingFloor getSelectedFloor(){
        String floor =  s.getSelectedItem().toString();
        if( floor.equals("Nessun piano")){
            return null;
        }
        for(int i=0; i<buildingFloors.size(); i++){
            if(buildingFloors.get(i).getName().equals(floor)){
                return buildingFloors.get(i);
            }
        }
        return null;
    }

}
