package me.ykrank.androidlifecycle_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * test AndroidLifeCycle with fragmentActivity and support fragment in onCreate
 */
public class MainActivity extends AppCompatActivity {
    static final String FRAGMENT_TAG = "me.ykrank.androidlifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MainActivity");

        setContentView(R.layout.activity_main);

        final RxjavaFragment mainFragment = new RxjavaFragment();
        getFragmentManager().beginTransaction().add(R.id.layout_fragment, mainFragment).commit();
    }
    
}
