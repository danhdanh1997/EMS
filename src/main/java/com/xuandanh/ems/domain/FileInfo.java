package com.xuandanh.ems.domain;

import javax.persistence.*;

@Entity
@Table(name = "file_info")
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String url;

    public FileInfo() {

    }

    public FileInfo(String name,String url){
        this.name = name;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FileInfo(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
