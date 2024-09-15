package com.example.phase2;

import java.time.LocalDate;

public class Manager {
    private static final BSDistrictTree districtTree = new BSDistrictTree();

    public static BSDistrictTree getDistrictTree() {
        return districtTree;
    }
    public static LocalDate localDate(String s){

        String[] dateArr = s.split("/");
        int year = Integer.parseInt(dateArr[2]);
        int month = Integer.parseInt(dateArr[0]);
        int day = Integer.parseInt(dateArr[1]);
        return LocalDate.of(year, month, day);
    }
    public static String martyrDate(String s){
        System.out.println("date" + s);
        String[] dateArr = s.split("-");
        int year = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1]);
        int day = Integer.parseInt(dateArr[2]);
        String date = month + "/" + day + "/" + year;
        return date;
    }

    public static short isValidAge(String age) {
        switch (age.toLowerCase()) {
            case "":
            case "?":
                return -1;
            default:
                try {
                    short num = Short.parseShort(age);
                    if (num >= 0 && num <= 130) {
                        return num;
                    } else {
                        return -2;
                    }
                } catch (NumberFormatException ex) {
                    return -2;
                }
        }
    }
    public static boolean checkInput(String s) {
        if (s.isEmpty() || s.length() <= 2) {
            return false; // Empty or too short strings are invalid
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                return false; // If any character is a digit, the string is invalid
            }
        }

        return true; // If all characters are non-digits and the string is of sufficient length, it's valid
    }

}
