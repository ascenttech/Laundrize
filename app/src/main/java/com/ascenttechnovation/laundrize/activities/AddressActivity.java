package com.ascenttechnovation.laundrize.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.ascenttechnovation.laundrize.R;
import com.ascenttechnovation.laundrize.adapters.ExpandableListAdapter;

public class AddressActivity extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        expListView = (ExpandableListView) findViewById(R.id.address_expandable_address_activity);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);

    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Select Your Address");
        listDataHeader.add("Add a New Address");


        List<String> sya = new ArrayList<String>();
        sya.add("JEET VASA");

        listDataChild.put(listDataHeader.get(0), sya);
        listDataChild.put(listDataHeader.get(1), sya);
    }
}