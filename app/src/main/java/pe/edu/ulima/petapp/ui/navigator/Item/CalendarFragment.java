package pe.edu.ulima.petapp.ui.navigator.Item;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.controller.PetController;
import pe.edu.ulima.petapp.controller.UserController;
import pe.edu.ulima.petapp.dao.Actividad;
import pe.edu.ulima.petapp.dao.Pet;


public class CalendarFragment extends Fragment {

    private CaldroidFragment caldroidFragment;
    private ArrayList<Actividad> actividadArrayList;
    final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        caldroidFragment = new CaldroidFragment();
        actividadArrayList = new ArrayList<>();
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }

        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            caldroidFragment.setArguments(args);
        }
        getActivitiesParse();

        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                if(!actividadArrayList.isEmpty())
                    for (int i = 0; i < actividadArrayList.size(); i++)
                        if(formatter.format(actividadArrayList.get(i).getDate()).equals(formatter.format(date)))
                               showDialogActividad(actividadArrayList.get(i),i);
            }

            @Override
            public void onChangeMonth(int month, int year) {

            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                }
            }

        };

        caldroidFragment.setCaldroidListener(listener);
        return view;
    }

    private void showDialogActividad(Actividad actividad, final int position){
        Dialog dialog =null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.actividad_dialog, null);
        builder.setView(v);
        final String id = actividad.getiD();
        TextView txtMascota = (TextView)v.findViewById(R.id.txtActividadMascota);
        TextView txtFecha = (TextView)v.findViewById(R.id.txtActividadFecha);
        TextView txtHora = (TextView)v.findViewById(R.id.txtActividadHora);
        TextView txtTipo = (TextView)v.findViewById(R.id.txtActividadTipo);

        for (Pet tempo:PetController.getInstance().getPetArray()) {
            if(tempo.getPetId().equals(actividad.getPetID())){
                txtMascota.setText(tempo.getPetName());
                break;
            }
        }
        txtFecha.setText(formatter.format(actividad.getDate()));
        txtHora.setText(actividad.getTime());
        txtTipo.setText(actividad.getTipo());

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancelar Actividad", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelarEvento(id,position);
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void cancelarEvento(String id, final int position) {

        ParseObject delete = ParseObject.createWithoutData("Actividad",id);
        delete.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null) {
                    actividadArrayList.remove(position);
                    setCustomResourceForDates();
                    Toast.makeText(getActivity(), "Actividad Cancelada", Toast.LENGTH_SHORT).show();

                }else
                    Toast.makeText(getActivity(), "No se logró cancelar la actividad", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }
    }

    private void getActivitiesParse(){
        ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("_User");
        innerQuery.whereEqualTo("objectId", UserController.getInstance().getUser().getUserCode());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pet");
        query.whereMatchesQuery("user", innerQuery);
        ParseQuery<ParseObject> actividadQuery = ParseQuery.getQuery("Actividad");
        actividadQuery.whereMatchesQuery("petID", query);
        actividadQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if(e==null){
                for (int i = 0; i < scoreList.size(); i++) {
                    ParseObject temp = (ParseObject) scoreList.get(i).get("petID");
                    Actividad temporalActividad = new Actividad(scoreList.get(i).getDate("fecha"),
                            scoreList.get(i).get("hora").toString(), scoreList.get(i).get("tipo").toString()
                            , Integer.valueOf(scoreList.get(i).get("alerta").toString()), temp.getObjectId().toString(),
                            scoreList.get(i).getObjectId().toString(), 1);
                    actividadArrayList.add(temporalActividad);
                }
                setCustomResourceForDates();

            } else {
                e.printStackTrace();
            }
        }});

    }

    private void setCustomResourceForDates() {

        for (int i = 0; i <actividadArrayList.size() ; i++) {
            if (caldroidFragment != null) {
                caldroidFragment.setBackgroundResourceForDate(R.color.green,
                        actividadArrayList.get(i).getDate());
                caldroidFragment.setTextColorForDate(R.color.white,
                        actividadArrayList.get(i).getDate());
            }
        }
        caldroidFragment.refreshView();
    }
}
