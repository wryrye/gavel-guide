package aperture.science.final_project_umbreon;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.Map;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Map m1 = new HashMap();
    TabFragment1 tab1;
    TabFragment2 tab2;
    TabFragment3 tab3;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        tab1 = new TabFragment1();
        tab2 = new TabFragment2();
        tab3 = new TabFragment3();
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                m1.put(0,tab1);
                return tab1;
            case 1:

                m1.put(1,tab2);
                return tab2;
            case 2:
                m1.put(2,tab3);

                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}