package com.example.menuconmapa;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.menuconmapa.databinding.ActivityLoginBinding;
import com.example.menuconmapa.ui.broadcast.CambioUsb;

public class LoginActivity extends AppCompatActivity {
    private LoginActivityViewModel vm;
    private ActivityLoginBinding binding;
    private CambioUsb cambio;


    @Override
    protected void onResume() {
        super.onResume();registerReceiver(cambio,new IntentFilter("android.hardware.usb.action.USB_STATE"));
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(cambio);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);
        setContentView(binding.getRoot());
        cambio= new CambioUsb();
        vm.getMUsuario().observe(this, new Observer<String>() {



            @Override
            public void onChanged(String s) {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = binding.etUsuario.getText().toString();
                String password = binding.etClave.getText().toString();
                vm.verificarIngreso(usuario, password);
            }
        });
    }
}