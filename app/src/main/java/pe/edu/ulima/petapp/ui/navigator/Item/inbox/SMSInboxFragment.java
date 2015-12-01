package pe.edu.ulima.petapp.ui.navigator.Item.inbox;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.dao.Sms;

public class SMSInboxFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapterInbox recyclerAdapterInbox;
    ArrayList<Sms> smsArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_inbox, container, false);
        smsArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyle_view_inbox);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManagerHeader = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManagerHeader);
        readSms();
        return view;
    }

    private void readSms(){
        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        Cursor cur = getActivity().getContentResolver().query(uriSMSURI, null, null, null, null);
        while (cur.moveToNext()) {
            String body= cur.getString(cur.getColumnIndex("body"));
            String number = cur.getString(2);
            if(body.startsWith("petApp")){
                body=body.substring(7,body.length());
                Sms temporalSmas= new Sms(body,number,""+0,"");
                smsArrayList.add(temporalSmas);
            }else if(body.contains("lat:") && body.contains("long:")){
                String[] parts = body.split("\\:");
                String latitud = parts[2].substring(0, 8);
                String longitud = parts[3].substring(0, 8);
                double lat = Double.parseDouble(latitud);
                double longi = Double.parseDouble(longitud);
                LatLng gps = new LatLng(lat,longi);
                body="Fecha:"+parts[5]+":"+parts[6];
                Sms temporalSmas= new Sms(body,number,""+0,"",gps);
                smsArrayList.add(temporalSmas);
            }
        }
        mostrarInbox();
    }
    private void mostrarInbox(){
        recyclerAdapterInbox = new RecyclerAdapterInbox(getActivity(),smsArrayList);
        recyclerView.setAdapter(recyclerAdapterInbox);
    }
}
