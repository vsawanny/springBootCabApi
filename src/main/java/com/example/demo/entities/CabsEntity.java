package com.example.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "CabTable", schema = "", catalog = "CabsDetailsDB")
public class CabEntity {
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private String cabId;

    @Basic
    @Column(name = "modelNumber", nullable = true, insertable = true, updatable = true, length = 14)
    private String modelNumber;

    public String getCabId() {
        return cabId;
    }

    public void setCabId(String cabId) {
        this.cabId = cabId;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Basic
    @Column(name = "driverName", nullable = true, insertable = true, updatable = true, length = 14)
    private String  driverName;


    @Basic
    @Column(name = "areaCode", nullable = true, insertable = true, updatable = true, length = 14)
    private String areaCode;

}
