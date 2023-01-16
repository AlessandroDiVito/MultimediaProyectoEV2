package com.example.proyectoev2;

public class Model {
    private int id;
    private String titulo;
    private String autor;
    private String discografica;
    private byte[] imagen;

    public Model(int id, String titulo, String autor, String discografica, byte[] imagen) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.discografica = discografica;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDiscografica() {
        return discografica;
    }

    public void setDiscografica(String discografica) {
        this.discografica = discografica;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
