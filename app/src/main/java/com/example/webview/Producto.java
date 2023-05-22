package com.example.webview;

public class Producto {

    private int id;
    private String code;
    private String name;
    private String desciption;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDesciption(){
        return desciption;
    }

    public void setDesciption(String desciption){
        this.desciption = desciption;
    }
}
