package models;

import exceptions.InvalidInputException;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Student {

    private String name;
    private String redId; // A unique identifier for the student
    private String email;
    private double gpa;
    private int units;


    public final int MIN_UNITS = 0;
    public final int MAX_UNITS = 150;
    public final double MIN_GPA = 0.0;
    public final double MAX_GPA = 4.0;


    public Student(String name, String redId, String email,
                   double gpa, int units)
            throws InvalidInputException {
        // Calling setters so that rules can be enforced
        setName(name);
        setRedId(redId);
        setEmail(email);
        setGpa(gpa);
        setUnits(units);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        // A regex check can be done here to restrict the characters of a name.
        // This has been skipped for simplicity.
        this.name = name;
    }

    public String getRedId() {
        return redId;
    }

    public void setRedId(String redId) {
        // A regex check can be done here to restrict the characters of a Red
        // ID. This has been skipped for simplicity.
        this.redId = redId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // A regex check can be done here to restrict the characters of an
        // email address. This has been skipped for simplicity.
        this.email = email;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) throws InvalidInputException {
        if(gpa < MIN_GPA || gpa > MAX_GPA) {
            throw new InvalidInputException("GPA must be between " + MIN_GPA
                    + " and " + MAX_GPA + "!");
        }
        this.gpa = gpa;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) throws InvalidInputException {
        if(units < MIN_UNITS || units > MAX_UNITS) {
            throw new InvalidInputException("Units must be between "
                    + MIN_UNITS + "  and " + MAX_UNITS + "!");
        }
        this.units = units;
    }


    @Override
    public String toString() {
        return "Name: " + name + " :: Red ID: " + redId;
    }

    @Override
    public boolean equals(Object comparisonObject) {
        if(comparisonObject instanceof Student) {
            Student comparisonStudent = (Student) comparisonObject;

            return this.getName().equals(comparisonStudent.getName())
                    && this.getRedId().equals(comparisonStudent.getRedId())
                    && this.getEmail().equals(comparisonStudent.getEmail())
                    && this.getGpa() == comparisonStudent.getGpa()
                    && this.getUnits() == comparisonStudent.getUnits();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            digest.update(getName().getBytes());
            digest.update(getRedId().getBytes());
            digest.update(getEmail().getBytes());
            digest.update(ByteBuffer.allocate(4).putInt(getUnits()).array());
            digest.update(ByteBuffer.wrap(new byte[8]).putDouble(getGpa()));

            byte[] hashCodeBytes = digest.digest();
            byte[] truncatedBytes = {hashCodeBytes[0], hashCodeBytes[1],
                    hashCodeBytes[2], hashCodeBytes[3]};

            return ByteBuffer.wrap(truncatedBytes).getInt();
        } catch (NoSuchAlgorithmException e) {
            // We don't expect this exception as we have hardcoded a valid
            // algorithm name.
            e.printStackTrace();
        }
        return 0;
    }
}
