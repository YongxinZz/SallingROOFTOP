package com.example.sallingrooftop;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BfriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BfriendFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static BfriendFragment newInstance() {
        return new BfriendFragment();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BfriendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BfriendFragment newInstance(String param1, String param2) {
        BfriendFragment fragment = new BfriendFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private TextView bestFriendTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private static final int SHOW = 0;
    private static final int HIDE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_bfriend, container, false);

        SharedPreferences myPrefsFile = this.getActivity().getSharedPreferences("MyPrefsFile", Activity.MODE_PRIVATE);
        String bestFriendName = myPrefsFile.getString("bestFriendName", null);

        TextView textView = rootView.findViewById(R.id.bestfriend_name);
        textView.setText(bestFriendName);

        return rootView;
    }
}