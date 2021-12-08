package com.corona.exam.model;

public enum Region {
    EUROPE("Europe"),
    NORTH_AMERICA("North America"),
    ASIA("Asia"),
    SOUTH_AMERICA("South America"),
    AFRICA("Africa"),
    OCEANIA("Oceania");

    private String regionName;

    Region(String regionName){
        this.regionName = regionName;
    }

    public String getRegionName(){
        return regionName;
    }
}
