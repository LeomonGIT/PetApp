package pe.edu.ulima.petapp.ui.navigator.Item.petProfile;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.controller.UserController;

public class RecyclerAdapterHeader extends RecyclerView.Adapter<RecyclerAdapterHeader.ViewHolder>{

    private Activity activity;

    public RecyclerAdapterHeader(Activity activity) {
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_header, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterHeader.ViewHolder viewHolder, int position) {

        if(UserController.getInstance().getUser().getUserName()!=null)
        viewHolder.name.setText(UserController.getInstance().getUser().getUserName());
        if(UserController.getInstance().getUser().getUserName()!=null)
        viewHolder.msj.setText(" ");
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView msj;
        private View container;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.txtName);
            msj = (TextView) view.findViewById(R.id.txtMensaje);
            container = view.findViewById(R.id.card_view_header);
        }
    }
}
