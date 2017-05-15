package ads.metodista.br.metolabs;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.labs);
       // String[] itens = getResources().getStringArray(R.array.labs); //vetor de strings
        new CarregarLabs().execute();


    }

     class CarregarLabs extends AsyncTask<String, Void, List<Lab>> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.show();
        }

        @Override
        protected List<Lab> doInBackground(String... params) {
            return new LabWS().consultar();
        }

        @Override
        protected void onPostExecute(List<Lab> labs) {
            if(labs != null) {
                String[] itens = new String[labs.size()];
                int i = 0;
                for (Lab l : labs){
                    itens[i++] = l.getNome();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                        android.R.layout.simple_list_item_1,itens);
                lista.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            dialog.dismiss();
        }
    }
}
