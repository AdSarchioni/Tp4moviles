package com.example.menuconmapa.ui.farmacias;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.menuconmapa.R;
import com.example.menuconmapa.databinding.FragmentDetalleFarmaciaBinding;
import com.example.menuconmapa.model.Farmacia;

public class DetalleFarmacia extends Fragment {

    private DetalleFarmaciaViewModel mViewModel;
    private FragmentDetalleFarmaciaBinding binding;
    public static DetalleFarmacia newInstance() {
        return new DetalleFarmacia();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleFarmaciaBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(DetalleFarmaciaViewModel.class);
        View root = binding.getRoot();
        mViewModel.getMFarmacia().observe(getViewLifecycleOwner(), new Observer<Farmacia>() {
            @Override
            public void onChanged(Farmacia farmacia) {
                binding.tvNombreD.setText(farmacia.getNombre());
                binding.tvDireccionD.setText(farmacia.getDireccion());
                binding.tvInfoD.setText(farmacia.getInfo());
                binding.ivFotoD.setImageResource(farmacia.getFoto());
            }
        });
        mViewModel.recibeFarmacia(getArguments());
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetalleFarmaciaViewModel.class);
        // TODO: Use the ViewModel
    }

}