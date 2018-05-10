package com.tvz.model;

public class Role {
    private Long id;
    private String name;
    private String desc;
    private Long fkpr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getFkpr() {
        return fkpr;
    }

    public void setFkpr(Long fkpr) {
        this.fkpr = fkpr;
    }
}
