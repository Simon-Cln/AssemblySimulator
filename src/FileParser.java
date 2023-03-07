import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class FileParser {

    // All the lines of data and instructions
    ArrayList<String> lines = new ArrayList<>();

    // All the lines of data
    ArrayList<String> dataLines = new ArrayList<>();
    // All the lines of instructions
    ArrayList<String> instructionsLines = new ArrayList<>();

    int fileNumber;

    FileParser(int fileNumber){
        this.fileNumber=fileNumber;
    };


    // Parse entire file
    public void getLines(){

        // Load the file
        FileInputStream stream = null;
        try {
            stream = new FileInputStream("src/MyFiles/file"+fileNumber);
        } catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }
        assert stream != null;
        
        // Read it and add to my array of lines if not commented or empty
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String strLine;
        ArrayList<String> lines = new ArrayList<>();
        try {
            while((strLine = reader.readLine()) != null){
                if(!strLine.isEmpty()){
                    if(strLine.charAt(0) != '!'){
                        // trim() allows elimination of whitespaces before and after the string/line
                        lines.add(strLine.trim());
                    }
                }
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        
        this.lines = lines;
    }


    // Get the lines in the data part and put them in dataLines
    public void getDataLines(){

        boolean dataPart = false;
        
        for(int i=0; i<lines.size()-1; i++){
            // If line contains "#data" and any variation of it, basically #data but case-insensitive, ie: #dAtA
            if(Pattern.compile(Pattern.quote(lines.get(i)), Pattern.CASE_INSENSITIVE).matcher("#data").find()){
                dataPart = true;
            }
            // If it is the data part, we add the next element of the array (should not add the #data)
            if(dataPart){
                if(!Pattern.compile(Pattern.quote(lines.get(i+1)), Pattern.CASE_INSENSITIVE).matcher("#code").find()){
                    this.dataLines.add(lines.get(i+1));
                }
                else{
                    dataPart = false;
                }
            }
        }

    }


    // Get the lines in the code part and put them in instructionsLines
    public void getInstructionsLines(){

        boolean instructionsPart = false;

        for(int i=0; i<lines.size()-1; i++){
            // If line contains "#code" and any variation of it, basically #code but case-insensitive => ie: #CODe
            if(Pattern.compile(Pattern.quote(lines.get(i)), Pattern.CASE_INSENSITIVE).matcher("#code").find()){
                instructionsPart = true;
            }
            // If it is the code part, we add the next element of the array (should not add the #code)
            if(instructionsPart){
                if(!Pattern.compile(Pattern.quote(lines.get(i+1)), Pattern.CASE_INSENSITIVE).matcher("#data").find()){
                    this.instructionsLines.add(lines.get(i+1));
                }
                else{
                    instructionsPart = false;
                }
            }
        }
    }


    // Hash/Map data correctly
    public void getData(){
        // Will serve as address
        int dataAddress = 0;
        for(String dataLine : dataLines){
            ++dataAddress;
            ArrayList<String> data = new ArrayList<>();
            // Splits the line in 2 when it encounters the first whitespace
            String[] arrOfStr = dataLine.split(" ", 2);
            for(int i=0; i<2; i++){
                data.add(arrOfStr[i]);
            }
            for(int i=0; i<dataAddress; i++){
                Memory.dataMap.put(dataAddress, data);
            }
        }
    }


    // Hash/Map instructions correctly
    public void getInstructions(){

        int whitespaceCounter = 0;
        boolean spaceIsPresent = false;

        // Might need to go static in a Memory class ? Dunno...
        int instructionAddress = 0;

        for(String instructionsLine : instructionsLines){

            // Map Starting at value 1, maybe might need to move it to 0? Dunno
            ++instructionAddress;

            // Values (reg, var, const) for the instructions
            ArrayList<String> values = new ArrayList<>();

            // Goes through the line and look for the number of whitespaces
            for(int i=0; i<instructionsLine.length(); i++){
                if(instructionsLine.charAt(i) == ' '){
                    ++whitespaceCounter;
                    spaceIsPresent = true;
                }
            }

            if(spaceIsPresent){
                // Splits the line in number of whitespaces+1
                String[] arrOfStr = instructionsLine.split(" ", whitespaceCounter+1);
                for(int i=0; i<whitespaceCounter+1; i++){
                    values.add(arrOfStr[i]);
                }
                Memory.instructionsMap.put(instructionAddress, values);
                // Resets
                whitespaceCounter = 0;
                spaceIsPresent = false;
            } else {
                values.add(instructionsLine);
                Memory.instructionsMap.put(instructionAddress, values);
            }
        }
    }

}
