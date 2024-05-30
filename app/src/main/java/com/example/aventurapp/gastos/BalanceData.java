package com.example.aventurapp.gastos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class BalanceData implements Parcelable {

    private long ingreso;
    private long gasto;
    private long balance;

    public BalanceData(long ingreso, long gasto, long balance) {
        this.ingreso = ingreso;
        this.gasto = gasto;
        this.balance = balance;
    }

    public long getIngreso() {
        return ingreso;
    }

    public void setIngreso(long ingreso) {
        this.ingreso = ingreso;
    }

    public long getGasto() {
        return gasto;
    }

    public void setGasto(long gasto) {
        this.gasto = gasto;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    //Implementación de Parcelable
    protected BalanceData(Parcel in){
        ingreso = in.readLong();
        gasto = in.readLong();
        balance = in.readLong();
    }

    public static final Creator<BalanceData> CREATOR = new Creator<BalanceData>() {
        @Override
        public BalanceData createFromParcel(Parcel parcel) {
            return new BalanceData(parcel);
        }

        @Override
        public BalanceData[] newArray(int i) {
            return new BalanceData[i];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(ingreso);
        parcel.writeLong(gasto);
        parcel.writeLong(balance);
    }
}
