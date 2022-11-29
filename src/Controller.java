import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {
    private int count = 0 ;
    private int maxNumber = 0 ;
    private final FileWriter fileWriter ;
    private final Long startTime ;

    private final JLabel[] labels ;

    Controller ( FileWriter fileWriter , Long startTime , JLabel[] labels ){
        this.fileWriter = fileWriter ;
        this.startTime = startTime ;
        this.labels = labels ;
    }

    private double calculateTime(){
        return (( System.currentTimeMillis() - startTime )/10000.0)*10000.0 ;
    }

    public void update( int number ){
        if ( number == -1 ){
            try {
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            ++count ;
            maxNumber = Math.max(maxNumber,number) ;
            write(number);
            updateGUI();
        }
    }

    private void updateGUI(){
        labels[0].setText(Integer.toString(count));
        labels[1].setText(Integer.toString(maxNumber));
        labels[2].setText(String.format("%.2f", calculateTime())+" ms.") ;
    }

    private void write( int number ){
        try {
            fileWriter.write("\""+number+"\",") ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
