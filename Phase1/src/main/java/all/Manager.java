package all;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Manager {
    private static final DistrictList districts = new DistrictList(); // the main district list the entire system is dependent on
    private static final ObservableList<District> cboDistrict = FXCollections.observableArrayList(); // an observable list for district combo box
    private static final ComboBox<District> districtComboBox = new ComboBox<>(cboDistrict); // combo box for Districts
    private static final ObservableList<Martyr> martyrs = FXCollections.observableArrayList(); // ObservableList for martyrs

    public static void readFromFile(String filePath) { // a method that reads the file
        try {
            Scanner sc = new Scanner(new File(filePath));
            String name, date, ageString, location, district; // declares string variables
            while (sc.hasNextLine()) {
                try {
                    String line = sc.nextLine();
                    if(line.startsWith("Name")){ // skips the line that starts with name
                        continue;
                    }
                    String[] info = line.split(",");
                    if (info.length >= 6) { // if the length is greater or equal then do
                        name = info[0];
                        date = (info[1].isEmpty()) ? "12/12/2003" : info[1];
                        ageString = (info[2].isEmpty()) ? "-1" : info[2];
                        location = (info[3].isEmpty()) ? "Unknown District" : info[3];
                        district = (info[4].isEmpty()) ? "Unknown Location" : info[4];
                        char gender = (info[5].isEmpty()) ? 'N' : info[5].charAt(0);
                        byte age = isValidAge(ageString);
                        Martyr martyr = new Martyr(name, date, age, location, district, gender);
                        District dis = new District(district);
                        Location loc = new Location(location);
                        if (districts.exists(dis)) { // checks if district is already in the list
                            districts.add(dis);
                        }
                        districts.addList(dis, loc); // adds the location the district
                        districts.getLocationNode(dis, loc).addListHead(martyr); // adds the martyr to the location
                    }
                } catch (ArrayIndexOutOfBoundsException | ClassCastException | NumberFormatException | NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
    } catch (FileNotFoundException ex) {
            System.out.println(ex + " ");
        }

    }
    public static DistrictList getDistricts(){
        return districts;
    } // getter method that returns the district list
    public static byte isValidAge(String age) { // checks if age is valid
        if(age.equals("?"))
            return -1;
        try {
            byte num = Byte.parseByte(age);
            if(num>=0 && num<125) { // if age is between 0 and 125
                return num; // returns number
            }else{
                return -1; // returns -1 means the age is invalid
            }
        } catch (NumberFormatException ex) {
            return -1; // returns -1 means the age is unknown
        }
    }
    public static boolean isValidDate(String date) { // checks if age is valid
        if (date == null) {
            return true;
        }
        try {
            String[] dates = date.split("/"); // splits the age by /
            int month = Integer.parseInt(dates[0]);
            int day = Integer.parseInt(dates[1]);
            int year = Integer.parseInt(dates[2]);

            return month < 1 || month > 12 || day < 1 || day > 31 || year < 1900 || year > 2024;
        }catch (NumberFormatException ex){
            return true;
        }
    }

    // getter static methods
    public static ObservableList<District> getCboDistrict() {
        return cboDistrict;
    }
    public static ObservableList<Martyr> getMartyrs() {
        return martyrs;
    }
    public static ComboBox<District> getDistrictComboBox() {
        return districtComboBox;
    }

}
