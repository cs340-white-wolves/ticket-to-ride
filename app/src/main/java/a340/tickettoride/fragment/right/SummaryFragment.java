package a340.tickettoride.fragment.right;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import a340.tickettoride.R;


public class SummaryFragment extends Fragment {
    private ViewPager mViewPager;
    private SummaryPageAdapter mPageAdapter;
    private TabLayout mTabLayout;

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_summary, container, false);

        mViewPager = inflate.findViewById(R.id.summary_container);
        mViewPager.setOffscreenPageLimit(4);

        setupViewPager(mViewPager);

        mTabLayout = inflate.findViewById(R.id.summary_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        return inflate;
    }

    private void setupViewPager (ViewPager viewPager) {
        mPageAdapter = new SummaryPageAdapter(getChildFragmentManager());
        mPageAdapter.addItem(new HandFragment(), "Hand");
        mPageAdapter.addItem(new PlayerInfoFragment(), "Players");
        mPageAdapter.addItem(new RoutesFragment(), "Routes");
        mPageAdapter.addItem(new ChatFragment(), "Chat");
        viewPager.setAdapter(mPageAdapter);
    }

}
