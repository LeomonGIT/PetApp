package pe.edu.ulima.petapp.ui.navigator.Item;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.controller.UserController;


public class AddPetFragment extends Fragment {
    private static final String TAG = "AddPetFragment";

    @InjectView(R.id.input_pet_name)
    EditText _nameText;
    @InjectView(R.id.input_pet_type) EditText _typeText;
    @InjectView(R.id.input_pet_age) EditText _ageText;
    @InjectView(R.id.btn_pet_register) Button _signupButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_add_pet, container, false);
        ButterKnife.inject(this,view);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        return view;
    }

    public void add() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onAddFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Agregando mascota...");
        progressDialog.show();



        registerParse();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        //onSignupSuccess();
                        // onSignupFailed();
                    }
                }, 3000);
    }

    public void onAddFailed() {
        Toast.makeText(getActivity(), "No se logro agregar la mascota", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String type = _typeText.getText().toString();
        String age = _ageText.getText().toString();

        if (name.isEmpty()) {
            _nameText.setError("No puede estar vacio");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (type.isEmpty()) {
            _typeText.setError("No puede estar vacio");
            valid = false;
        } else {
            _typeText.setError(null);
        }

        if (age.isEmpty()) {
            _ageText.setError("No puede estar vacio");
            valid = false;
        } else {
            _ageText.setError(null);
        }

        return valid;
    }

    private void registerParse(){
        String name = _nameText.getText().toString();
        String type = _typeText.getText().toString();
        String age = _ageText.getText().toString();
        ParseObject pet = new ParseObject("Pet");
        pet.put("petName", name);
        pet.put("petType", type);
        pet.put("petAge", age);
        pet.put("user", ParseObject.createWithoutData("_User", UserController.getInstance().toString()));
        pet.saveInBackground();
    }
}