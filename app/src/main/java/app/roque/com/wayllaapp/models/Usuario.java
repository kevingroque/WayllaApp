package app.roque.com.wayllaapp.models;

/**
 * Created by keving on 15/05/2018.
 */

public class Usuario {

    private String uid;
    private String avatar;
    private int coins;
    private Double distancia_recorrida;
    private int nivel;
    private int puntaje;

    public Usuario(){}

    public Usuario(String uid, String avatar, int coins, Double distancia_recorrida, int nivel, int puntaje) {
        this.uid = uid;
        this.avatar = avatar;
        this.coins = coins;
        this.distancia_recorrida = distancia_recorrida;
        this.nivel = nivel;
        this.puntaje = puntaje;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Double getDistancia_recorrida() {
        return distancia_recorrida;
    }

    public void setDistancia_recorrida(Double distancia_recorrida) {
        this.distancia_recorrida = distancia_recorrida;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "uid='" + uid + '\'' +
                ", avatar='" + avatar + '\'' +
                ", coins=" + coins +
                ", distancia_recorrida=" + distancia_recorrida +
                ", nivel=" + nivel +
                ", puntaje=" + puntaje +
                '}';
    }
}
