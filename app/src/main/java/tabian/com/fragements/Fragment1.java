package tabian.com.fragements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by User on 4/9/2017.
 */

public class Fragment1 extends Fragment {
    private static final String TAG = "Fragment1";

    private Button btnNavFrag2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment1_layout, container, false);
        btnNavFrag2 = (Button) view.findViewById(R.id.btnNavFrag2);
        Log.d(TAG, "onCreateView: started.");
        btnNavFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setViewPager(1);
            }
        });
        return view;
    }
}
