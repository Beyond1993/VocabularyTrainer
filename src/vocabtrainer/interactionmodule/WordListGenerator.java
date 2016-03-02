package vocabtrainer.interactionmodule;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WordListGenerator {
    private List<String> wordList = new ArrayList<String>();

    public String getRandomWord() {
        Random randGen = new Random();
        return wordList.get(randGen.nextInt(306));

    }

    public void readFile()
    {
        // The name of the file to open.
        String fileName = "wordlist.txt";
        
        int wordListID = 0;
        // This will reference one line at a time
        String line = null;


        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                wordList.add(line);
                wordListID++;
                //System.out.println(line);
               
            }   
            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    }
 }
    