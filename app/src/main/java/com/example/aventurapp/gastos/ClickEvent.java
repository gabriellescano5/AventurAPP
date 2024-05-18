package com.example.aventurapp.gastos;


//Interface para cuando se pulsa de manera prolongada para la eliminación por ejemplo
public interface ClickEvent {
    void OnClick(int pos);

    void OnLongPress(int pos);
}
