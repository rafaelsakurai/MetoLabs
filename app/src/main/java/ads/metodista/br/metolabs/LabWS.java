package ads.metodista.br.metolabs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

/**
 * Created by rafaelsakurai on 26/04/17.
 */

public class LabWS {

   public List<Lab> consultar(){
       List<Lab> labs = null;

       try {
           URL url = new URL("https://openws.herokuapp.com/MetoLabs?apiKey=???");
           URLConnection conn = url.openConnection();

           Scanner s = new Scanner(conn.getInputStream());
           String json = s.useDelimiter("\\A").next();
           s.close();

            Gson gson = new Gson();
           labs = gson.fromJson(json, new TypeToken<List<Lab>>(){}.getType());
       } catch (Exception e){

       }

       return  labs;
   }
}
