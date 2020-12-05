package com.example.bleledcontrol.ui.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PairViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PairViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is pair fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}