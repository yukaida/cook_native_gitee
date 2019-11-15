package com.ebanswers.kitchendiary.mvp.model;

/**
 * Create by dongli
 * Create date 2019-09-27
 * desc：
 */
public class EquipmentInfo {

    private String equipmentName;
    private boolean isChecked;

    public EquipmentInfo(String equipmentName, boolean isChecked) {
        this.equipmentName = equipmentName;
        this.isChecked = isChecked;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
