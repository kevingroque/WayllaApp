package app.roque.com.wayllaapp.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.roque.com.wayllaapp.R;

public class InicioDetalleLomasFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    /*private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_account_edit
    };*/

    public InicioDetalleLomasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inicio_detalle_lomas, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager_lomas_inicio_detalle);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs_lomas_inicio_detalle);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        return view;
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0);
        tabLayout.getTabAt(1);
        tabLayout.getTabAt(2);
        tabLayout.getTabAt(3);
        //tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        //tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        //tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFrag(new GeneralInicioDetalleFragment(), "General");
        adapter.addFrag(new ProblemasInicioDetalleFragment(), "Problemas");
        adapter.addFrag(new ImportanciaInicioDetalleFragment(), "Importancia");
        adapter.addFrag(new ActividadesInicioDetalleFragment(), "Actividades");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
