package com.example.menuconmapa.ui.farmacias;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.menuconmapa.MainActivity;
import com.example.menuconmapa.model.Farmacia;

import java.util.ArrayList;

public class FarmaciasViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<Farmacia>> mLista;

    public FarmaciasViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<Farmacia>> getMLista(){
        if(mLista == null){
            mLista = new MutableLiveData<>();
        }
        return mLista;
    }

    public void imprimirLista(){
        mLista.setValue(MainActivity.listaFarmacias);
    }

}