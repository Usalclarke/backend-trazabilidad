package ar.com.trazabilidad.util;

public class ErrorResponse {

    private Boolean success;
    private Integer status;
    private String message;

    public ErrorResponse(Integer status, String message) {
        this.success = false;
        this.status = status;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
}
