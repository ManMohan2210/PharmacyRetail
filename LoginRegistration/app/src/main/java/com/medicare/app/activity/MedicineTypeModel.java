package com.medicare.app.activity;

/**
 * Created by satveer on 08-06-2017.
 */

public class MedicineTypeModel {

    String medicineID;
    String medicineName;
    String description;

public MedicineTypeModel(String id, String medicineName, String medicineDesc){

}

    public MedicineTypeModel(String medicineID, String medicineName, String description, String image) {
        this.medicineID = medicineID;
        this.medicineName = medicineName;
        this.description = description;

    }

    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
