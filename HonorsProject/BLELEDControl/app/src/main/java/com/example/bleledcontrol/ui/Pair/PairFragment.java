package com.example.bleledcontrol.ui.Pair;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bleledcontrol.MainActivity;
import com.example.bleledcontrol.R;

import org.xml.sax.helpers.XMLReaderAdapter;

public class PairFragment extends Fragment {

    private PairViewModel pairViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pairViewModel =
                ViewModelProviders.of(this).get(PairViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pair, container, false);
        final TextView textView = root.findViewById(R.id.text_pair);
        pairViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        recyclerView = root.findViewById(R.id.recycler_pair);
        recyclerView.setHasFixedSize(true);

        return root;
    }
}