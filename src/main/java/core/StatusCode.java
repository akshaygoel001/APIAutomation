package core;

public enum StatusCode {

    SUCCESS(200, "Request Succeeded"),
    CREATED(201,"Content Created Successfully"),
    NO_CONTENT(204,"No Content to send in the response body");

    public final int code;
    public final String message;

    StatusCode(int code, String message){
        this.code = code;
        this.message = message;
    }
}
