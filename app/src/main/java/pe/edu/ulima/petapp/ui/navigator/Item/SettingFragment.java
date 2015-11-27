package pe.edu.ulima.petapp.ui.navigator.Item;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.controller.PetController;
import pe.edu.ulima.petapp.controller.SmsController;
import pe.edu.ulima.petapp.controller.UserController;
import pe.edu.ulima.petapp.ui.LoginActivity;

public class SettingFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        TextView txtName = (TextView) view.findViewById(R.id.txtSettingName);
        TextView txtEmal = (TextView) view.findViewById(R.id.txtSettingEmail);
        TextView txtPets = (TextView) view.findViewById(R.id.txtSettingPets);
        Button _btn_logout = (Button) view.findViewById(R.id.btn_setting_logout);

        txtName.setText(UserController.getInstance().getUser().getUserName());
        txtEmal.setText(UserController.getInstance().getUser().getUserEmail());
        txtPets.setText(""+PetController.getInstance().getPetArray().size());

        _btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void limpiar(){
        UserController.getInstance().clearUserController();
        SmsController.getInstance().clearSmsController();
        PetController.getInstance().clearPetController();
    }
}
