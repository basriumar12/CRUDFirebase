package com.basri.crud;

class Kegiatan {

    String kegiatan;
    String desk;
    private String key;


    public Kegiatan(){}
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Kegiatan(String kegiatan, String desk) {
        this.kegiatan = kegiatan;
        this.desk = desk;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }
}
