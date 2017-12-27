
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class compileAndRun implements Runnable {

    Thread t;
    File filePath;
    NewApplication jframe;
    String answer = "";
    ArrayList<String> compilecommands = new ArrayList<>();          //for compilation commands
    ArrayList<String> runcommands = new ArrayList<>();             // for run commands
    String global;
    compileAndRun(File filePath, NewApplication p, String whichFile) {

        t = new Thread(this, "car");                         //   compile only ,flag =1 ; run only ,flag=2; compile and Run ,flag=3
        jframe = p;
        global=whichFile;
        String temp = filePath.getName();
        int yahaTak = temp.lastIndexOf(".");
        temp = temp.substring(0, yahaTak);
        runcommands.add("cmd");
        runcommands.add("/c");
        if (whichFile.equals("C")) {

            this.compilecommands.add("gcc");
            this.compilecommands.add(filePath.getName());
            this.compilecommands.add("-o");
            this.compilecommands.add(temp);
            System.out.println(filePath + " " + filePath.getName());

        } else if (whichFile.equals("C++")) {

            this.compilecommands.add("g++");
            this.compilecommands.add(filePath.getName());
            this.compilecommands.add("-o");
            this.compilecommands.add(temp);

        } else if (whichFile.equals("JAVA")) {

            this.compilecommands.add("javac");
            this.compilecommands.add(filePath.getName());
            this.runcommands.add("java");

        }
        else if(whichFile.equals("py"))
        {
//            this.compilecommands.add("python3");
//            this.compilecommands.add(filePath.getName());
            this.runcommands.add("python3");
            this.runcommands.add(filePath.getName());
        }
       // this.runcommands.add(temp);
        t.start();
    }

    @Override
    public void run() {

        if (jframe.flag == 1) {

            try {
                jframe.outputAndCompileLog.setText("Compilation Log :");
                jframe.consoleWindow.setText("Compiling....");
                this.compileKaro();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(compileAndRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (jframe.flag == 2) {

            jframe.outputAndCompileLog.setText("Output");
            jframe.consoleWindow.setText("");
            try {
                this.runKaro();
            } catch (InterruptedException ex) {
                Logger.getLogger(compileAndRun.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (jframe.flag == 3) {
            try {
                this.compileKaro();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(compileAndRun.class.getName()).log(Level.SEVERE, null, ex);
            }
            jframe.outputAndCompileLog.setText("Output");
            jframe.consoleWindow.setText("");
            try {
                this.runKaro();
            } catch (InterruptedException ex) {
                Logger.getLogger(compileAndRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void compileKaro() throws IOException, InterruptedException {
        
        if(!global.equals("py"))
        {
        ProcessBuilder processBuilder = new ProcessBuilder(compilecommands);
        processBuilder.directory(this.jframe.s.getParentFile());                        //set ProcessBuilder's current working drectory
        processBuilder.redirectErrorStream(true);                                      //Sets this process builder's redirectErrorStream property
        Process process = processBuilder.start();
        StringBuilder processOutput = new StringBuilder();                        //create an empty stringBuilder with a capacity = 16

        try (BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));) {   //no need to close BufferedReader ,it is autoclosable 
            String readLine;
            while ((readLine = processOutputReader.readLine()) != null) {                     //processOutputReader contains buffered output stream
                processOutput.append(readLine + System.lineSeparator());                   //lineSeparator() take system's lineSeparator  
            }

          //  process.waitFor();
            process.destroy();                       //to terminate the process
        }
        if (processOutput.toString().trim().length() != 0) {
            jframe.consoleWindow.setText(processOutput.toString().trim());           //trim() omits leading and trailing whitespace 
        } else {
            System.out.println(jframe.s);
            jframe.compiledCFilePaths.addElement(jframe.s);
            jframe.consoleWindow.setText("Compiled Successfully!!!!");
            jframe.latestCompiledFilePath = jframe.s;
        }
        jframe.flag = 1;
        
    }
        this.jframe.flagOngoing = 0;
        
}
    // Trying to make output process interactive

    /*  public static boolean isAlive(Process p) {
        try {
            p.exitValue();
            return false;
        } catch (IllegalThreadStateException e) {
            return true;
        }
    }
            
      public void runKaro() throws IOException {

        ProcessBuilder pb = new ProcessBuilder(runcommands);
        pb.directory(jframe.s.getParentFile());
        Process process = null;
        try {                                                                   
            process = pb.start();
        } catch (IOException ex) {
            Logger.getLogger(compileAndRun.class.getName()).log(Level.SEVERE, null, ex);
        }
        InputStream out = process.getInputStream();
        OutputStream in = process.getOutputStream();

        byte[] buffer = new byte[4000];
        while (isAlive(process)) {
            int no = out.available();
            if (no > 0) {
                int n = out.read(buffer, 0, Math.min(no, buffer.length));
                this.jframe.consoleWindow.setText(new String(buffer, 0, n));
            }
            int ni = System.in.available();
            if (ni > 0)
            {
                int n = System.in.read(buffer, 0, Math.min(ni, buffer.length));
                in.write(buffer, 0, n);
                in.flush();
            }

        }
    }
}*/
    public void runKaro() throws InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(runcommands);
        pb.directory(this.jframe.s.getParentFile());
        Process process = null;
        try {
            process = pb.start();
        } catch (IOException ex) {
            Logger.getLogger(compileAndRun.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String input = this.jframe.inputArea.getText();
        try {

            writer.write(input + "\n");

        } catch (IOException ex) {
            Logger.getLogger(compileAndRun.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(compileAndRun.class.getName()).log(Level.SEVERE, null, ex);
        }
        String line = "", output = "";
        try {
           // System.out.println(reader.readLine());
            while ((line = reader.readLine()) != null) {

                output = output + line;
                output = output + "\n";

            }
            this.answer = output;
            this.jframe.consoleWindow.setText(this.answer);
            System.out.println("xyz" + process.exitValue() + "abc");
            if(process.exitValue() == -1073741676 )
                    {
                        this.jframe.outputAndCompileLog.setText("Compilation log :");
                        this.jframe.consoleWindow.setText("Divide by Zero error.....");
                    }
            else if(process.exitValue() == -1073741819)
            {
                this.jframe.outputAndCompileLog.setText("Compilation log :");
                this.jframe.consoleWindow.setText("Segmentation Fault(core Dumped).....");
            }
            process.destroy();
            this.jframe.flag = 1;

        } catch (IOException ex) {

            Logger.getLogger(compileAndRun.class.getName()).log(Level.SEVERE, null, ex);

        }
        try {

            reader.close();

        } catch (IOException ex) {

            Logger.getLogger(compileAndRun.class.getName()).log(Level.SEVERE, null, ex);

        }
        try {

            writer.close();

            //System.out.println("Exit With value" + returnValue );
        } catch (IOException ex) {

            Logger.getLogger(compileAndRun.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
