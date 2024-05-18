package com.example.aventurapp.gastos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//Interface con los métodos en Room para aplicar
@Dao
public interface GastoDAO {
    @Insert
    void insertGasto(GastoTabla gastoTabla);

    @Update
    void updateGasto(GastoTabla gastoTabla);

    @Query("DELETE FROM gasto where id=:id")
    abstract void delete(int id);

    @Query("SELECT * FROM gasto")
    List<GastoTabla> getAll();

    @Query("SELECT * FROM gasto WHERE id = :gastoId")
    GastoTabla getGastoById(int gastoId);
}
