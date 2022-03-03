package rb.com.care.purge.model;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private String jwttoken;
    public HttpStatus status;
    public String message;
    public Object data;

    public JwtResponse(String jwttoken, HttpStatus status, String message, Object data) {
        this.jwttoken = jwttoken;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public void setJwttoken(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public String getToken() {
        return this.jwttoken;
    }
}
