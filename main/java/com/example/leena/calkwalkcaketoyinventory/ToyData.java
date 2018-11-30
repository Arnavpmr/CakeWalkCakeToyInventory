package com.example.leena.calkwalkcaketoyinventory;

class ToyData {
    String ID;
    String name;
    String quantity;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public ToyData(String ID, String name, String quantity) {
        this.ID = ID;
        this.name = name;
        this.quantity = quantity;
    }
}
