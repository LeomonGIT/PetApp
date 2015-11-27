package pe.edu.ulima.petapp.ui.navigator.Item.inbox;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
                Sms temporalSmas= new Sms(body,number,""+0,"");
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
