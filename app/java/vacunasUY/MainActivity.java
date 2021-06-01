package vacunasUY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import net.openid.appauthdemo.R;
import net.openid.appauthdemo.TokenActivity;

import org.json.JSONException;
import org.json.JSONObject;

import vacunasUY.entities.User;

import static net.openid.appauthdemo.TokenActivity.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    private Button misVacunasButton;
    private Button vacCercanos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        misVacunasButton= findViewById(R.id.misVacunasID);
        misVacunasButton= findViewById(R.id.vacCercanosID);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:8080/vacunasUY-web/loginandroid?code="+message;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("respuesta", response.toString());
                try {
                    User user = new User(response.get("primer_nombre").toString(), response.get("primer_apellido").toString(),
                        response.get("numero_documento").toString(), response.get("primer_nombre").toString(), "");

                    setContentView(R.layout.activity_main);
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
    }

    public void verMisCertificados(View view) {
        Intent intent = new Intent(MainActivity.this, CertificadoActivity.class);
        startActivity(intent);
    }
}
