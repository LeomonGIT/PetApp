package pe.edu.ulima.petapp.ui.navigator.Item.inbox;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.dao.Sms;
import pe.edu.ulima.petapp.ui.navigator.Item.inbox.map.MapsActivity;


public class RecyclerAdapterInbox extends RecyclerView.Adapter<RecyclerAdapterInbox.ViewHolder>{

    private List<Sms> sms;
    private Activity activity;

    public RecyclerAdapterInbox(Activity activity, List<Sms> sms) {
        this.sms = sms;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_inbox, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterInbox.ViewHolder viewHolder, int position) {

        final Sms smss = sms.get(position);
        viewHolder.number.setText(sms.get(position).getNumber());
        viewHolder.body.setText(sms.get(position).getBody());
        viewHolder.time.setText(sms.get(position).getTime());
        viewHolder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, MapsActivity.class);
                if(smss.getLatLng()!=null) {
                    i.putExtra("longitud", smss.getLatLng().longitude);
                    i.putExtra("latitud", smss.getLatLng().latitude);
                }
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != sms ? sms.size() : 0);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView body;
        private TextView number;
        private TextView time;
        private ImageButton map;
        private View container;

        public ViewHolder(View view) {
            super(view);
            map = (ImageButton) view.findViewById(R.id.btn_map);
            body = (TextView) view.findViewById(R.id.txtInboxBody);
            number = (TextView) view.findViewById(R.id.txtInboxNumber);
            time = (TextView) view.findViewById(R.id.txtInboxTime);
            container = view.findViewById(R.id.card_view);
        }
    }

}
