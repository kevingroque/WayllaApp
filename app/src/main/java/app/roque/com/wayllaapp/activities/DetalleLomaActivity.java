package app.roque.com.wayllaapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import app.roque.com.wayllaapp.R;
import app.roque.com.wayllaapp.fragments.GaleriaDetalleLomasFragment;
import app.roque.com.wayllaapp.fragments.InicioDetalleLomasFragment;
import app.roque.com.wayllaapp.fragments.PreguntasDetalleLomasFragment;
import app.roque.com.wayllaapp.fragments.RecursosDetalleLomasFragment;
import app.roque.com.wayllaapp.fragments.RutasDetalleLomasFragment;

public class DetalleLomaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_loma);

        showToolbar("", true);

        BottomBar bottomBar = (BottomBar)findViewById(R.id.bottom_bar_lomas_detalle);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId){
                    case R.id.tab_inicio_detalle:
                        InicioDetalleLomasFragment inicioDetalleLomasFragment = new InicioDetalleLomasFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_lomas_detalle, inicioDetalleLomasFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.tab_recursos_detalle:
                        RecursosDetalleLomasFragment recursosDetalleLomasFragment = new RecursosDetalleLomasFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_lomas_detalle, recursosDetalleLomasFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.tab_galeria_detalle:
                        GaleriaDetalleLomasFragment galeriaDetalleLomasFragment = new GaleriaDetalleLomasFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_lomas_detalle, galeriaDetalleLomasFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.tab_rutas_detalle:
                        RutasDetalleLomasFragment rutasDetalleLomasFragment = new RutasDetalleLomasFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_lomas_detalle, rutasDetalleLomasFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.tab_preguntas_detalle:
                        PreguntasDetalleLomasFragment preguntasDetalleLomasFragment = new PreguntasDetalleLomasFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_lomas_detalle, preguntasDetalleLomasFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                }
            }
        });
    }

    public void showToolbar(String titulo, Boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView title = (TextView)findViewById(R.id.toolbar_tittle);
        title.setText("Nombre de la loma");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
