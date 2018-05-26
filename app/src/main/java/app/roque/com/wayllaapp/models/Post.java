package app.roque.com.wayllaapp.models;

public class Post {


    private String id;
    private String descripcion;
    private String image;
    private String fecha;
    private String username;
    private String avatar_user;
    private String hora;

    private long createdDate;
    private String ubicacion;
    private String user_uid;
    private long commentsCount;
    private long likesCount;
    private long watchersCount;

    public Post(){}

    public Post(String id, String descripcion, String image, String fecha, String username, String avatar_user, String hora, long createdDate, String ubicacion, String user_uid, long commentsCount, long likesCount, long watchersCount) {
        this.id = id;
        this.descripcion = descripcion;
        this.image = image;
        this.fecha = fecha;
        this.username = username;
        this.avatar_user = avatar_user;
        this.hora = hora;
        this.createdDate = createdDate;
        this.ubicacion = ubicacion;
        this.user_uid = user_uid;
        this.commentsCount = commentsCount;
        this.likesCount = likesCount;
        this.watchersCount = watchersCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAvatar_user() {
        return avatar_user;
    }

    public void setAvatar_user(String avatar_user) {
        this.avatar_user = avatar_user;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(long commentsCount) {
        this.commentsCount = commentsCount;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    public long getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(long watchersCount) {
        this.watchersCount = watchersCount;
    }


}
