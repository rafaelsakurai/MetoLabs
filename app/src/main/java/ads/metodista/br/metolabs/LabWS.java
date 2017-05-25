package ads.metodista.br.metolabs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by rafaelsakurai on 26/04/17.
 */
public class LabWS {

    private static final String OPENWS_URL = "https://openws.org/api/collections/metolabs";

    public List<Lab> consultar() {
        List<Lab> labs = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(OPENWS_URL);
            conn = (HttpURLConnection) url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());
            Scanner s = new Scanner(is);
            String json = s.useDelimiter("\\A").next();
            s.close();

            Gson gson = new Gson();
            labs = gson.fromJson(json, new TypeToken<List<Lab>>(){}.getType());
       } catch (Exception e){
//            e.printStackTrace();
       } finally {
            conn.disconnect();
       }

       return labs;
    }

    public void alterarStatus(Lab lab) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(OPENWS_URL + "/" + lab.getId());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            Writer w = new BufferedWriter(new OutputStreamWriter(out));
            Gson gson = new Gson();
            String json = gson.toJson(lab);
            w.write(json);
            w.close();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Scanner s = new Scanner(in);
            String conteudo = s.nextLine();
            in.close();
        } catch (Exception e) {
//            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
    }
}
