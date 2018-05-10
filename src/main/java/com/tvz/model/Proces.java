package com.tvz.model;

public class Proces {
    private Long id;
    private String name;
    private String desc;
    private Long fkdep;

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

    public Long getFkdep() {
        return fkdep;
    }

    public void setFkdep(Long fkdep) {
        this.fkdep = fkdep;
    }
}
