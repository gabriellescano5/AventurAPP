package com.example.aventurapp.gastos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GastosDAO {
        @Insert
    void insertGasto(GastoTabla gastoTabla);
        @Update
    void updateGasto(GastoTabla gastoTabla);

    @Query("DELETE FROM gasto where id=:id")
    abstract void delete(int id);

    @Query("select * from gasto")
    List<GastoTabla> getAll();

}
