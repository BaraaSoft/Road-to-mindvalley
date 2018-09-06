package com.baraa.software.eventhorizon.roadtomindvalley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.baraa.software.eventhorizon.roadtomindvalley.pinboard.view.PinboardFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    PinboardFragment fragment;
    @BindView(R.id.toolbar)Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        fragment = PinboardFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment)
                .commit();
    }

}
