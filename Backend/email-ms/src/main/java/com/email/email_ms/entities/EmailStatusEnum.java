package com.email.email_ms.entities;

public enum EmailStatusEnum {
    SENT("sent", 1),
    ERROR("error", 2);

    String status;
    long codigo;

    EmailStatusEnum(String status, long codigo){
        this.status = status;
        this.codigo = codigo;
    }

    public long getCodigo(){
        return codigo;
    }

    public String getName(){
        return status;
    }
}
