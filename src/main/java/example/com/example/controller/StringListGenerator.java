package example.com.example.controller;
import java.util.ArrayList;
import java.util.List;

public class StringListGenerator {
    public static List<String> listing() {
        // Set the length of a single page (adjust as needed)
        int pageLength = 2000; // This is a rough estimate

        // Number of pages
        int numPages = 3; // You can adjust this as needed

        // Generate a string that exceeds two pages
        String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        // Create a list of strings
        List<String> stringList = new ArrayList<>();

        // Generate the list
        for (int i = 0; i < numPages * pageLength / loremIpsum.length(); i++) {
            stringList.add(loremIpsum);
        }

        // Print the list size
        System.out.println("Number of strings in the list: " + stringList.size());
        return stringList;
    }
}
