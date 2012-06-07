import java.io.*;
import java.util.*;

public class TabsToWhitespace {

    public static void main(String[] args) {
        try {
            // all methods throw IOException this is important
            // because if one part fails we don't want to move on
            writeFile(args[0], readFile(args[0]));
            File tempFile = new File("temp.text");
            File originalFile = new File(args[0]);
            if (!tempFile.renameTo(originalFile)) {
                System.out.println("rename failed");
            }            
        } catch (IOException ioe) {
            System.out.println("failed IOException");
        }
    }
    

    private static String readFile(String path) throws IOException {
        File file = new File(path);
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;
 
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
 
            // repeat until all lines are read
            while ((text = reader.readLine()) != null) {
                // replace tabs with 4 whitespaces
                String text_wo_tabs = text.replace("\t", "    ");
                // send it off to have trailing whitespace trimmed
                text_wo_tabs = trimEnd(text_wo_tabs);
                contents.append(text_wo_tabs);
                contents.append(System.getProperty("line.separator"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return contents.toString();
    }

    private static String trimEnd(String str) {
        int len = str.length();
        char[] val = str.toCharArray();
        while ((0 < len) && (val[len - 1] <= ' ')) {
            len--;
        }

        return (len < str.length()) ? str.substring(0, len) : str;
    }

    private static boolean writeFile(String path, String file_text) throws IOException {
        File file = new File("temp.text");
        boolean exist = file.createNewFile();
        if (!exist) {
            System.out.println("File already exists. Removing it");
            file.delete();
        }
        FileWriter fstream = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(file_text);
        out.close();
        System.out.println("File created successfully.");
        return true;
    }
}

