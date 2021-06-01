package vacunasUY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import net.openid.appauthdemo.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vacunasUY.util.ListElementCertificado;

public class CertificadoActivity extends AppCompatActivity {
    List<ListElementCertificado> elementCertificadoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificado);
        setContentView(R.layout.fragment_certificados);




        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:8080/vacunasUY-web/loginandroid?code";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                elementCertificadoList= new ArrayList<>();
                //llenar la lista de certrificados
                for(int i=0; i<10; i++){
                    elementCertificadoList.add(new ListElementCertificado("CoronaVac", "12/10/21", "14/10/22",
                        "1", "covid", "Sinovac"));
                }

                ListCertificadoAdapter listCertificadoAdapter = new ListCertificadoAdapter(elementCertificadoList, CertificadoActivity.this);
                RecyclerView recyclerView = findViewById(R.id.listRecyclerViewCertificados);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(CertificadoActivity.this));
                recyclerView.setAdapter(listCertificadoAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("respuesta", error.toString());
            }
        });
        queue.add(request);


    }



    public class ListCertificadoAdapter extends RecyclerView.Adapter<ListCertificadoAdapter.ViewHolder> {

        private List<ListElementCertificado> mCertificado;
        private LayoutInflater mInflater;
        private Context context;

        public ListCertificadoAdapter(List<ListElementCertificado> itemList, Context context) {

            this.mInflater = LayoutInflater.from(context);
            this.context = context;
            this.mCertificado = itemList;
        }

        @NonNull
        @Override
        public ListCertificadoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.list_element_certificados, null);
            return new ListCertificadoAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListCertificadoAdapter.ViewHolder holder, int position) {
            holder.bindData(mCertificado.get(position));

        }

        @Override
        public int getItemCount() {
            return mCertificado.size();
        }

        public void setItems(List<ListElementCertificado> items) {
            mCertificado = items;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView vacuna, fecha, inmunidad, dosis, enfermedad, laboratorio;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                vacuna = itemView.findViewById(R.id.vacunaId);
                fecha = itemView.findViewById(R.id.fechaActoVacunalId);
                inmunidad = itemView.findViewById(R.id.periodoInmuneId);
                dosis = itemView.findViewById(R.id.dosisId);
                enfermedad = itemView.findViewById(R.id.enfermedadId);
                laboratorio = itemView.findViewById(R.id.laboratorioId);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        //GlobalInfo.SELECTED_REGISTRO = elementRegistroList.get(position);
                        //BaseFragment.pushBackFragment(new HomeFragment());
                        //OpenActivity(getActivity(), DetalleRegistrosActivity.class);
                        //OpenFragment(new DetalleRegistrosFragment(), getActivity(), AppConfig.ID_FRAGMENT_HISTORICO_EVENTOS);


                        /*                        ListElementRegistro listElementRegistro = mRegistros.get(position);
                        Snackbar.make(v, "Click detected on item " + listElementRegistro.getNumRegistro(),
                                Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();*/
                    }
                });
            }

            void bindData(final ListElementCertificado item) {
                vacuna.setText(item.getVacuna());
                fecha.setText(item.getFecha());
                inmunidad.setText(item.getInmunidad());
                dosis.setText(item.getDosis());
                enfermedad.setText(item.getEnfermedad());
                laboratorio.setText(item.getLaboratorio());



            }

        }
    }
}
