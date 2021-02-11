
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Escritor1 {

  public static void main(String[] args) throws IOException {

    try (BufferedReader in = new BufferedReader(new FileReader("../../Documentos/Escritor1.java"))) {
      //String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      //BufferedReader in = new BufferedReader(new StringReader(s));

      PrintWriter   out =new PrintWriter(new BufferedWriter(new FileWriter("../../Documentos/Escritor1.out")));

      int lineCount = 1;
        String s;
      while((s = in.readLine()) != null ) out.printf("%3d: %s\n", lineCount++, s);
      out.close();

    }
    catch(EOFException e) { System.err.println("... já está!"); }

  }

}