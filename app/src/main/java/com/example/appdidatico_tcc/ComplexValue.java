package com.example.appdidatico_tcc;

public class ComplexValue {
    double realPart, imaginaryPart;

    public ComplexValue(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public double getRealPart() {
        return realPart;
    }

    public void setRealPart(double realPart) {
        this.realPart = realPart;
    }

    public double getImaginaryPart() {
        return imaginaryPart;
    }

    public void setImaginaryPart(double imaginaryPart) {
        this.imaginaryPart = imaginaryPart;
    }

    /**
     * Transform a cartesian notation of a number into a polar notation.
     */
    public void transformToCartesian() {
        double aux_real = this.realPart;
        double aux_imaginary = this.imaginaryPart;

        this.setRealPart(aux_real * Math.cos(Math.toRadians(aux_imaginary)));
        this.setImaginaryPart(aux_real * Math.sin(Math.toRadians(aux_imaginary)));
    }

    /**
     * Transform a polar notation of a number into a cartesian notation.
     */
    public void transformToPolar() {
        double aux_real = this.realPart;
        double aux_imaginary = this.imaginaryPart;

        this.setRealPart(Math.sqrt(Math.pow(aux_real, 2) + Math.pow(aux_imaginary, 2)));
        this.setImaginaryPart(Math.toDegrees(Math.atan(aux_imaginary/aux_real)));
    }

}
