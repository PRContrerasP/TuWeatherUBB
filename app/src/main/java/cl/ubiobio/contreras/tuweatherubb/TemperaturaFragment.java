package cl.ubiobio.contreras.tuweatherubb;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TemperaturaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TemperaturaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemperaturaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView textView;
    View rootView;
    private RequestQueue requestQueue;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TemperaturaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TemperaturaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TemperaturaFragment newInstance(String param1, String param2) {
        TemperaturaFragment fragment = new TemperaturaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requestQueue = Volley.newRequestQueue(getActivity());
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_temperatura, container, false);

        Calendar currentTime = Calendar.getInstance();
        int hora = currentTime.get(Calendar.HOUR_OF_DAY);
        if(hora>=18||hora<=7){
            textView = (TextView) rootView.findViewById(R.id.tv_nombre_ciudad);
            textView.setBackgroundResource(R.drawable.background_noche1);
            textView = (TextView) rootView.findViewById(R.id.tv_fecha_medicion);
            textView.setBackgroundResource(R.drawable.background_noche2);
            textView = (TextView) rootView.findViewById(R.id.tv_tx_temperatura_maxima);
            textView.setBackgroundResource(R.drawable.background_noche3);
            textView = (TextView) rootView.findViewById(R.id.tv_temperatura_maxima);
            textView.setBackgroundResource(R.drawable.background_noche4);
            textView = (TextView) rootView.findViewById(R.id.tv_tx_temperatura_minima);
            textView.setBackgroundResource(R.drawable.background_noche5);
            textView = (TextView) rootView.findViewById(R.id.tv_temperatura_minima);
            textView.setBackgroundResource(R.drawable.background_noche6);
            textView = (TextView) rootView.findViewById(R.id.tv_tx_temperatura_promedio);
            textView.setBackgroundResource(R.drawable.background_noche7);
            textView = (TextView) rootView.findViewById(R.id.tv_temperatura_promedio);
            textView.setBackgroundResource(R.drawable.background_noche8);
            textView = (TextView) rootView.findViewById(R.id.tv_tx_footer);
            textView.setBackgroundResource(R.drawable.background_noche9);
        }else{
            textView = (TextView) rootView.findViewById(R.id.tv_nombre_ciudad);
            textView.setBackgroundResource(R.drawable.background_dia1);
            textView = (TextView) rootView.findViewById(R.id.tv_fecha_medicion);
            textView.setBackgroundResource(R.drawable.background_dia2);
            textView = (TextView) rootView.findViewById(R.id.tv_tx_temperatura_maxima);
            textView.setBackgroundResource(R.drawable.background_dia3);
            textView = (TextView) rootView.findViewById(R.id.tv_temperatura_maxima);
            textView.setBackgroundResource(R.drawable.background_dia4);
            textView = (TextView) rootView.findViewById(R.id.tv_tx_temperatura_minima);
            textView.setBackgroundResource(R.drawable.background_dia5);
            textView = (TextView) rootView.findViewById(R.id.tv_temperatura_minima);
            textView.setBackgroundResource(R.drawable.background_dia6);
            textView = (TextView) rootView.findViewById(R.id.tv_tx_temperatura_promedio);
            textView.setBackgroundResource(R.drawable.background_dia7);
            textView = (TextView) rootView.findViewById(R.id.tv_temperatura_promedio);
            textView.setBackgroundResource(R.drawable.background_dia8);
            textView = (TextView) rootView.findViewById(R.id.tv_tx_footer);
            textView.setBackgroundResource(R.drawable.background_dia9);
        }
        jsonParse();
        return rootView;
    }

    private void jsonParse(){
        String url = "http://arrau.chillan.ubiobio.cl:8075/ubbiot/web/mediciones/medicionesporintervalofechas/OcTxZlaUKI/E1yGxKAcrg/19052018/26052018";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            int promedio = 0;
            String fechaview ="";
            String horaview ="";
            String maximo = "";
            String minimo ="";
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    JSONObject jsonaux =jsonArray.getJSONObject(0);
                    maximo = jsonaux.getString("valor");
                    minimo = jsonaux.getString("valor");
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        String fecha = data.getString("fecha");
                        String hora  = data.getString("hora");
                        String valor = data.getString("valor");
                        promedio+= Integer.parseInt(valor);
                        fechaview = fecha;
                        horaview = hora;
                        if(maximo.compareTo(valor)<0){
                            maximo=valor;
                        }
                        if(minimo.compareTo(valor)>0){
                            minimo=valor;
                        }
                    }
                    fechaview = "Última medición: "+fechaview +" "+horaview;
                    textView = (TextView) rootView.findViewById(R.id.tv_fecha_medicion);
                    textView.setText(fechaview);
                    textView = (TextView) rootView.findViewById(R.id.tv_temperatura_maxima);
                    textView.setText(maximo+"°C");
                    textView = (TextView) rootView.findViewById(R.id.tv_temperatura_minima);
                    textView.setText(minimo+"°C");
                    textView = (TextView) rootView.findViewById(R.id.tv_temperatura_promedio);
                    String temperatura = promedio/jsonArray.length()+"°C";
                    textView.setText(temperatura);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
