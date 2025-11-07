package core;

public enum ContentType {

    APPLICATION_JSON("application/json");

    public final String type;

    ContentType(String type){
        this.type = type;
    }
}
