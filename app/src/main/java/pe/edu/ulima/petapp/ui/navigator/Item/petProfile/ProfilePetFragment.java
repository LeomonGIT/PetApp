package pe.edu.ulima.petapp.ui.navigator.Item.petProfile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.controller.PetController;
import pe.edu.ulima.petapp.controller.UserController;
import pe.edu.ulima.petapp.dao.Pet;

public class ProfilePetFragment extends Fragment {

    private RecyclerView recyclerView,recyclerViewHeader;
    private RecyclerAdapter adapter;
    private RecyclerAdapterHeader adapterHeader;
    private ArrayList<Pet> petArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_pet, container, false);
        petArrayList =  PetController.getInstance().getPetArray();;

        recyclerViewHeader = (RecyclerView) view.findViewById(R.id.recyle_view_header);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyle_view);
        recyclerViewHeader.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManagerHeader = new LinearLayoutManager(getActivity());
        recyclerViewHeader.setLayoutManager(layoutManagerHeader);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        if(petArrayList.isEmpty())
            setRecyclerViewDataFromParse();
        else
            mostrarPets();
        adapterHeader = new RecyclerAdapterHeader(getActivity());
        recyclerViewHeader.setAdapter(adapterHeader);
        return view;
    }

    private void setRecyclerViewDataFromParse() {
        ParseObject user = ParseObject.createWithoutData("_User",UserController.getInstance().getUser().getUserCode());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pet");
        query.whereEqualTo("user", user);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < scoreList.size(); i++) {
                        Pet temporalPet = new Pet(false, scoreList.get(i).get("petType").toString(), scoreList.get(i).get("petName").toString()
                                , scoreList.get(i).get("petAge").toString(), (ParseFile) scoreList.get(i).get("petImage"),scoreList.get(i).getObjectId().toString());
                        petArrayList.add(temporalPet);
                    }
                    PetController.getInstance().setPetArray(petArrayList);
                    mostrarPets();

                } else {
                   e.printStackTrace();
                }
            }
        });
    }

    private void mostrarPets(){
        adapter = new RecyclerAdapter(getActivity(), petArrayList);
        recyclerView.setAdapter(adapter);
    }
}
