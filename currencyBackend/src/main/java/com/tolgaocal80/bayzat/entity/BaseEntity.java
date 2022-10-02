package com.tolgaocal80.bayzat.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "OPERATION_TYPE")
    private String operationType;



    @PrePersist
    public void onPrePersist(){
        this.setUpdateDate(new Date());
        this.setActive(true);
        this.setCreateDate(new Date());
        this.setOperationType("SAVE");
    }


    @PreUpdate
    public void onPreUpdate(){
        this.setUpdateDate(new Date());
        this.setOperationType("UPDATE");
    }


    @PreRemove
    public void onPreRemove(){
        this.setUpdateDate(new Date());
        this.setOperationType("DELETE");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
