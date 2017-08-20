package com.trevorism.data;

import java.util.Date;

/**
 * @author tbrooks
 */
public class TestEntity {


    private long id;
    private Date date;
    private String application;
    private String service;
    private String version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", application='" + application + '\'' +
                ", service='" + service + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

}
