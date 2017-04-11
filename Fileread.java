import java.io.*;
import java.lang.*;
import java.util.*;
/**
 * Open and read a file, and return the lines in the file as a list
 * of Strings.
 * (Demonstrates Java FileReader, BufferedReader, and Java5.)
 */
class Fileread{
  //File f = new File("querie.txt");
public List<String> readFile(String filename)
{
  List<String> records = new ArrayList<String>();
  try
  {
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line;
    while ((line = reader.readLine()) != null)
    {
      records.add(line);
    }
    reader.close();
    return records;
  }
  catch (Exception e)
  {
    System.err.format("Exception occurred trying to read '%s'.", filename);
    e.printStackTrace();
    return null;
  }
}
}






















// import java.io.*;
// class FileRead 
// {
//  public static void main(String args[])
//   {
//   try{
//   // Open the file that is the first 
//   // command line parameter
//   File f = new File("querie.txt");
//   FileInputStream fstream = new FileInputStream(f);
//   // Get the object of DataInputStream
//   DataInputStream in = new DataInputStream(fstream);
//   BufferedReader br = new BufferedReader(new InputStreamReader(in));
//   String strLine;
//   //Read File Line By Line
//   while ((strLine = br.readLine()) != null)   {
//   // Print the content on the console
//   String[] arr = str.split(" ");
//   for(int i=0 ; i<str.length() ; i++){
//   arr[i] = in.readLine();
//   }
// }
//   //Close the input stream
//   in.close();
//     }catch (Exception e){//Catch exception if any
//   System.err.println("Error: " + e.getMessage());
//   }
//   }
// }