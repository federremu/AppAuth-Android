package vacunasUY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import net.openid.appauthdemo.LoginActivity;
import net.openid.appauthdemo.R;
import net.openid.appauthdemo.TokenActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vacunasUY.entities.Token;
import vacunasUY.entities.User;

import static net.openid.appauthdemo.TokenActivity.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    private Button misVacunasButton;
    private Button vacCercanos;
    public static User user;
    public static Token token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(user==null){
            user= new User("");
        }
        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        misVacunasButton= findViewById(R.id.misVacunasID);
        misVacunasButton= findViewById(R.id.vacCercanosID);

        SharedPreferences myPreferences
            = PreferenceManager.getDefaultSharedPreferences(this);

        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="http://10.0.2.2:8080/vacunasUY-web/loginandroid?code="+message;
        String url ="https://uyvacunas.web.elasticloud.uy/vacunasUY-web/loginandroid?code="+message;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("respuesta", response.toString());
                try {
                    user.setNombre(response.get("primer_nombre").toString()).setApellido(response.get("primer_apellido").toString()).setCedula(response.get("numero_documento").toString());
                    setContentView(R.layout.activity_main);
                    while (token==null){
                            Log.d("esperando", "por token");
                    }
                    user.setToken(token.getToken());
                    sendToken();
                } catch (JSONException e) {
                    //volvel al inicio, no es valido
                    Intent intent = new Intent(MainActivity.this, TokenActivity.class);
                    GlobalInfo.AUTORIZADO=false;
                    intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //volvel al inicio, no es valido
                Intent intent = new Intent(MainActivity.this, TokenActivity.class);
                GlobalInfo.AUTORIZADO=false;
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
                Log.d("respuesta", error.toString());
            }
        });
        queue.add(request);


    }

    public void verVacunatoriosCercanos(View view) {
        //muestra vacunatorios cercanos
        Intent intent = new Intent(MainActivity.this, MapaVacunatorios.class);
        startActivity(intent);
    }

    public void verMisCertificados(View view) {
        Intent intent = new Intent(MainActivity.this, CertificadoActivity.class);
        startActivity(intent);
    }


    public void sendToken() {
        if (user.getCedula()!=null){
            String url = "https://uyvacunas.web.elasticloud.uy/vacunasUY-web/rest/UsuarioREST/addToken?cedula="+user.getCedula()+"&token="+user.getToken();
// Optional Parameters to pass as POST request
            JSONObject params = new JSONObject();
/*            try {
                params.put("cedula", user.getCedula());
                params.put("token", user.getToken());
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            // Make request for JSONObject
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE:", response.toString());
                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Error: ", error.getMessage());
                }
            }) {

                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            // Adding request to request queue
            Volley.newRequestQueue(this).add(jsonObjReq);
        }
    }

    public void logOut(View view) {
        user.setToken("");
        sendToken();
        user= null;
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
