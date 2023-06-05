package com.example.dentalcare.modelo;

public class Usuario {
    private String Nombre;
    private String ApPaterno;
    private String ApMaterno;
    private String correo;
    private String contrasena;
    private String fechaNacimiento;
    private String uid;

    public  Usuario(){

    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApPaterno() {
        return ApPaterno;
    }

    public void setApPaterno(String apPaterno) {
        ApPaterno = apPaterno;
    }

    public String getApMaterno() {
        return ApMaterno;
    }

    public void setApMaterno(String apMaterno) {
        ApMaterno = apMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


}
