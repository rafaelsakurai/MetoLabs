package ads.metodista.br.metolabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class LabArrayAdapter extends ArrayAdapter<Lab>{
    private final Context context;
    private List<Lab> labs;

    public LabArrayAdapter(Context context, int resource, List<Lab>objects){
        super(context,resource,objects);
        this.context = context;
        this.labs = objects;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //personaliza linha por linha
        View linha = inflater.inflate(R.layout.custom_list, parent,false);

        //Personaliza cada item do Spinner
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(context,
                        R.layout.spinner_item, context.getResources().getStringArray(R.array.status));
        Spinner spinner = ((Spinner) linha.findViewById(R.id.spinnerItem));
        spinner.setAdapter(spinnerAdapter);

        int pos = spinnerAdapter.getPosition(labs.get(position).getStatus());
        spinner.setSelection(pos);

        TextView textView = ((TextView) linha.findViewById(R.id.lblLab));
        textView.setText(labs.get(position).getNome());

        return linha;
    }

}
