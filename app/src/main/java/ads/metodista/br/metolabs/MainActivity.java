package ads.metodista.br.metolabs;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private LabArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.labs);
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
                adapter = new LabArrayAdapter(getBaseContext(), 0, labs);
                lista.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            dialog.dismiss();
        }
    }
}
