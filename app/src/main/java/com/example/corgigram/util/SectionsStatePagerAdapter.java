package com.example.corgigram.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SectionsStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragmentList = new ArrayList<>();
    private final HashMap<Fragment, Integer> mFragments = new HashMap<>();
    private final HashMap<String, Integer> mFragmentNumbers = new HashMap<>();
    private final HashMap<Integer, String> mFragmentNames = new HashMap<>();

    public SectionsStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String fragmentName) {
        mFragmentList.add(fragment);
        mFragments.put(fragment, mFragmentList.size() - 1);
        mFragmentNumbers.put(fragmentName, mFragmentList.size() - 1);
        mFragmentNames.put(mFragmentList.size() - 1, fragmentName);
    }

    /**
     * return fragment number with name @param if it exists, null otherwise
     *
     * @param fragmentName Name of fragment
     * @return fragment number
     */
    public Integer getFragmentNumber(String fragmentName) {
        if (mFragmentNumbers.containsKey(fragmentName)) {
            return mFragmentNumbers.get(fragmentName);
        }
        return null;
    }


    /**
     * return fragment number corresponding to fragment @param if it exists, null otherwise
     *
     * @param fragment Name of fragment
     * @return fragment number
     */
    public Integer getFragmentNumber(Fragment fragment) {
        if (mFragments.containsKey(fragment)) {
            return mFragments.get(fragment);
        }
        return null;
    }

    /**
     * return fragment name corresponding to fragment number @param if it exists, null otherwise
     *
     * @param i number of fragment
     * @return fragment name
     */
    public String getFragmentName(Integer i) {
        if (mFragmentNames.containsKey(i)) {
            return mFragmentNames.get(i);
        }
        return null;
    }
}
