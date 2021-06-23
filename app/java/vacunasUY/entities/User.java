package vacunasUY.entities;

public class User {
    private String nombre;
    private String apellido;
    private String cedula;
    private String pais;
    private String email;
    private String token;


    public User(String token) {
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public User setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getApellido() {
        return apellido;
    }

    public User setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public String getCedula() {
        return cedula;
    }

    public User setCedula(String cedula) {
        this.cedula = cedula;
        return this;
    }

    public String getPais() {
        return pais;
    }

    public User setPais(String pais) {
        this.pais = pais;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
