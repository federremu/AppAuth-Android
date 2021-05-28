package vacunasUY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String message = intent.getStringExtra(TokenActivity.EXTRA_MESSAGE);

        //llamar a backend y devolver si es válido o no
        //String message ="";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:8080/vacunasUY-web/loginandroid?code="+message;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("respuesta", response.toString());
                try {
                    User user = new User(response.get("primer_nombre").toString(), response.get("primer_apellido").toString(),
                        response.get("numero_documento").toString(), response.get("primer_nombre").toString(), "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("respuesta", error.toString());
            }
        });
        queue.add(request);
    }
}
