package pe.edu.ulima.petapp.ui.navigator.Item;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pe.edu.ulima.petapp.R;

public class SMSFragment extends Fragment {
    @InjectView(R.id.send)
    Button _btnSendSMS;
    @InjectView(R.id.mobileNumber)
    EditText _txtPhoneNo;
    @InjectView(R.id.smsBody)
    EditText _txtMessageBody;
    @InjectView(R.id.btn_call)
    Button _btn_call;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_sms, container, false);
        ButterKnife.inject(this, view);

        _btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateNumber())
                    return;
                String number = _txtPhoneNo.getText().toString();
                String sms = _txtMessageBody.getText().toString();
                //sms = "petApp " + sms;
                _btnSendSMS.setEnabled(false);
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, sms, null, null);
                    Toast.makeText(getActivity(), "SMS enviado correctamente.",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(),
                            "SMS no enviado, pruebe otra vez",
                            Toast.LENGTH_LONG).show();
                    _btnSendSMS.setEnabled(true);
                    e.printStackTrace();
                }
            }
        });

        _btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _btnSendSMS.setEnabled(false);
                if(!validateNumber()){
                    _btnSendSMS.setEnabled(true);
                    return;}
                String number = _txtPhoneNo.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
                startActivity(intent);
            }
        });
        return view;
    }

    private boolean validateNumber(){
        boolean valid;
        String number = _txtPhoneNo.getText().toString();
        if (number.isEmpty()) {
            _txtPhoneNo.setError("Ingrese un número a llamar");
            valid = false;
        } else {
            _txtPhoneNo.setError(null);
            valid=true;
        }

        return valid;
    }

    private void onError(){
        _btn_call.setEnabled(true);
        _btnSendSMS.setEnabled(true);
    }
}
