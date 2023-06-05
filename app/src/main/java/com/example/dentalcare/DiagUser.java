package com.example.dentalcare;

public class DiagUser {
    String Fecha, Hora, RP1, RP2, RP3, RP4, RP5, nameImg, uid, key;

    public DiagUser() {

    }

    public DiagUser(String fecha, String hora, String RP1, String RP2, String RP3, String RP4, String RP5, String nameImg, String uid, String key) {
        Fecha = fecha;
        Hora = hora;
        this.RP1 = RP1;
        this.RP2 = RP2;
        this.RP3 = RP3;
        this.RP4 = RP4;
        this.RP5 = RP5;
        this.nameImg = nameImg;
        this.uid = uid;
        this.key = key;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getRP1() {
        return RP1;
    }

    public void setRP1(String RP1) {
        this.RP1 = RP1;
    }

    public String getRP2() {
        return RP2;
    }

    public void setRP2(String RP2) {
        this.RP2 = RP2;
    }

    public String getRP3() {
        return RP3;
    }

    public void setRP3(String RP3) {
        this.RP3 = RP3;
    }

    public String getRP4() {
        return RP4;
    }

    public void setRP4(String RP4) {
        this.RP4 = RP4;
    }

    public String getRP5() {
        return RP5;
    }

    public void setRP5(String RP5) {
        this.RP5 = RP5;
    }

    public String getNameImg() {
        return nameImg;
    }

    public void setNameImg(String nameImg) {
        this.nameImg = nameImg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
