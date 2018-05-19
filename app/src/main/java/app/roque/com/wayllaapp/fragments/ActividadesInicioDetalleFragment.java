package app.roque.com.wayllaapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.roque.com.wayllaapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActividadesInicioDetalleFragment extends Fragment {


    public ActividadesInicioDetalleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_actividades_inicio_detalle, container, false);
    }

}
