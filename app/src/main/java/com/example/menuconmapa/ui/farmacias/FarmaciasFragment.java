package com.example.menuconmapa.ui.farmacias;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.menuconmapa.R;
import com.example.menuconmapa.databinding.FragmentFarmaciasBinding;
import com.example.menuconmapa.model.Farmacia;

import java.util.ArrayList;

public class FarmaciasFragment extends Fragment {

    private FarmaciasViewModel mViewModel;
    private FragmentFarmaciasBinding binding;

    public static FarmaciasFragment newInstance() {
        return new FarmaciasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(FarmaciasViewModel.class);
        binding = FragmentFarmaciasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mViewModel.getMLista().observe(getViewLifecycleOwner(), new Observer<ArrayList<Farmacia>>() {
            @Override
            public void onChanged(ArrayList<Farmacia> farmacias) {
                FarmaciaAdapter adapter = new FarmaciaAdapter(farmacias, inflater);
                GridLayoutManager grid = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                binding.listaFarmacias.setAdapter(adapter);
                binding.listaFarmacias.setLayoutManager(grid);
            }
        });
        mViewModel.imprimirLista();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FarmaciasViewModel.class);
        // TODO: Use the ViewModel
    }

}