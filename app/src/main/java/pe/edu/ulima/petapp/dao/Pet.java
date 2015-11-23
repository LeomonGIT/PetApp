package pe.edu.ulima.petapp.dao;

import com.parse.ParseFile;

import java.io.File;

/**
 * Created by Leonardo on 21/11/2015.
 */
public class Pet {

    private boolean gender;
    private String petType;
    private String petName;
    private String petAge;
    private ParseFile petImage;
    public Pet(boolean gender, String petType, String petName, String petAge) {
        this.gender = gender;
        this.petType = petType;
        this.petName = petName;
        this.petAge = petAge;
    }

    public Pet(boolean gender, String petType, String petName, String petAge, ParseFile petImage) {

        this.gender = gender;
        this.petType = petType;
        this.petName = petName;
        this.petAge = petAge;
        this.petImage = petImage;
    }
    @Override
    public String toString() {
        return "Pet{" +
                "gender=" + gender +
                ", petName='" + petName + '\'' +
                ", petAge='" + petAge + '\'' +
                ", petImage=" + petImage +
                '}';
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetAge() {
        return petAge;
    }

    public Pet() {
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public ParseFile getPetImage() {
        return petImage;
    }

    public void setPetImage(ParseFile petImage) {
        this.petImage = petImage;
    }


    public Pet(boolean gender, String petName, String petAge, ParseFile petImage) {
        this.gender = gender;
        this.petName = petName;
        this.petAge = petAge;
        this.petImage = petImage;
    }
}
