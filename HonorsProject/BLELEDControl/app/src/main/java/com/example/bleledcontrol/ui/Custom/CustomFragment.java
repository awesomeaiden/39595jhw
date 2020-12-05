package com.example.bleledcontrol.ui.Custom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bleledcontrol.R;

public class CustomFragment extends Fragment {

    private CustomViewModel customViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customViewModel =
                ViewModelProviders.of(this).get(CustomViewModel.class);
        View root = inflater.inflate(R.layout.fragment_custom, container, false);
        final TextView textView = root.findViewById(R.id.text_custom);
        customViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}