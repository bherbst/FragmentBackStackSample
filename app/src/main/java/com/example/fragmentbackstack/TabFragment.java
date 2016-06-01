package com.example.fragmentbackstack;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Fragment for each of the bottom navigation tabs.
 *
 * @author bherbst
 */
public class TabFragment extends Fragment {
    private static final String ARG_KEY_NUMBER = "tab_number";

    public static TabFragment newInstance(int number) {
        Bundle args = new Bundle();
        args.putInt(ARG_KEY_NUMBER, number);

        TabFragment frag = new TabFragment();
        frag.setArguments(args);

        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView text = (TextView) view.findViewById(R.id.text);
        Button addChildButton = (Button) view.findViewById(R.id.add_child);

        int number = getArguments().getInt(ARG_KEY_NUMBER);
        text.setText("Tab " + number);
        addChildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addFragmentOnTop(new ChildFragment());
            }
        });
    }
}
