package com.tvz.model;

public class Gdpr {
    private Long id;
    private Long fkpr;
    private Long fkpd;
    private Long fkrole;
    private boolean needed;
    private boolean third;
    private Long[] applications;
    private String pdName;
    private String pdDesc;

    public String getPdName() {
        return pdName;
    }

    public void setPdName(String pdName) {
        this.pdName = pdName;
    }

    public String getPdDesc() {
        return pdDesc;
    }

    public void setPdDesc(String pdDesc) {
        this.pdDesc = pdDesc;
    }

    public Long[] getApplications() {
        return applications;
    }

    public void setApplications(Long[] applications) {
        this.applications = applications;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFkpr() {
        return fkpr;
    }

    public void setFkpr(Long fkpr) {
        this.fkpr = fkpr;
    }

    public Long getFkpd() {
        return fkpd;
    }

    public void setFkpd(Long fkpd) {
        this.fkpd = fkpd;
    }

    public Long getFkrole() {
        return fkrole;
    }

    public void setFkrole(Long fkrole) {
        this.fkrole = fkrole;
    }

    public boolean isNeeded() {
        return needed;
    }

    public void setNeeded(boolean needed) {
        this.needed = needed;
    }

    public boolean isThird() {
        return third;
    }

    public void setThird(boolean third) {
        this.third = third;
    }
}
