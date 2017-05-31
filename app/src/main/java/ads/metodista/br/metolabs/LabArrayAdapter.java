package ads.metodista.br.metolabs;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class LabArrayAdapter extends ArrayAdapter<Lab>{
    private final Context context;
    private List<Lab> labs;

    public LabArrayAdapter(Context context, int resource, List<Lab> objects){
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

        Lab lab = labs.get(position);

        TextView textView = ((TextView) linha.findViewById(R.id.lblLab));
        textView.setText(lab.getNome());

        //Personaliza cada item do Spinner
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(context,
                        R.layout.spinner_item, context.getResources().getStringArray(R.array.status));
        final Spinner spinner = ((Spinner) linha.findViewById(R.id.spinnerItem));
        spinner.setAdapter(spinnerAdapter);
        int pos = spinnerAdapter.getPosition(lab.getStatus());
        spinner.setSelection(pos);

        mudarCorPorStatus(linha, pos);//Muda a cor da linha conforme o status da sala

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Usa o nome do lab para procurar qual a linha da ListView será alterada.
                String nome = ((TextView)((View)parent.getParent()).findViewById(R.id.lblLab)).getText().toString();
                for(Lab lab : labs) {
                    if(lab.getNome().equals(nome)) {
                        String status = (String) spinner.getItemAtPosition(position);
                        //Se não escolheu o mesmo status.
                        if(!lab.getStatus().equals(status)) {
                            //Muda o status do lab e atualiza no Web Service.
                            lab.setStatus(status);

                            mudarCorPorStatus((View)parent.getParent(), position); //altera a cor da linha conforme o status

                            new AtualizarLab(lab).execute();
                        }
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return linha;
    }

    private void mudarCorPorStatus(View obj, int status){
        switch (status){
            case 0:
                obj.setBackgroundColor(Color.YELLOW); //Em aula
                break;
            case 1:
                obj.setBackgroundColor(Color.GREEN); //Livre
                break;
            case 2:
                obj.setBackgroundColor(Color.RED); //Fechado
                break;
        }
    }

    class AtualizarLab extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;
        private Lab lab;

        public AtualizarLab(Lab lab) {
            this.lab = lab;
        }


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            new LabWS().alterarStatus(lab);
            return "";
        }

        @Override
        protected void onPostExecute(String teste) {
        }
    }

}
