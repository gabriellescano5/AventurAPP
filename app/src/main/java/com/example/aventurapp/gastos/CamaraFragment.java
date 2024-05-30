package com.example.aventurapp.gastos;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aventurapp.R;


public class CamaraFragment extends Fragment {
private ImageView imageView;
private TextView textViewNoImage;
private Bitmap imageBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camara,container,false);
        imageView = view.findViewById(R.id.imageView);
        textViewNoImage = view.findViewById(R.id.textViewNoImage);

//        Verifico si la imagen capturada está disponible
        if(imageBitmap != null){
            imageView.setImageBitmap(imageBitmap); // Muestra la imagen en la vista de imagen
            textViewNoImage = view.findViewById(R.id.textViewNoImage); //Oculto el mensaje de imagen no disponible
        } else {
//            Si no hay imagen, muestra el mensaje
            textViewNoImage.setVisibility(View.VISIBLE);
        }
        return  view;
    }
}