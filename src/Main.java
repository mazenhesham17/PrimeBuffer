import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    private static void createFile( String name ){
        File file = new File(name) ;
        try {
            file.createNewFile() ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JPanel generatePanel(JComponent jComponent1, JComponent jComponent2, JComponent jComponent3){
        JPanel jPanel = new JPanel() ;
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.Y_AXIS));

        jPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        jPanel.add(jComponent1) ;
        jPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        jPanel.add(jComponent2) ;
        jPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        jPanel.add(jComponent3) ;
        jPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        return jPanel ;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        JFrame jFrame = new JFrame("Prime Producer");
        Font labelFont = new Font("Arial", Font.PLAIN, 18) ;
        Font fieldFont = new Font("Arial", Font.PLAIN, 18) ;

        // First section
        JLabel numberSizeLabel = new JLabel("Number Size :") ;
        JLabel bufferSizeLabel = new JLabel("Buffer Size :") ;
        JLabel fileNameLabel = new JLabel("File Name :") ;

        numberSizeLabel.setFont(labelFont);
        bufferSizeLabel.setFont(labelFont);
        fileNameLabel.setFont(labelFont);


        JPanel firstPanel = generatePanel(
                numberSizeLabel,
                bufferSizeLabel,
                fileNameLabel
        ) ;

        // Second Section
        JTextField numberSizeField = new JTextField(10) ;
        JTextField bufferSizeField = new JTextField(10) ;
        JTextField fileNameField = new JTextField(15) ;

        numberSizeField.setFont(fieldFont);
        bufferSizeField.setFont(fieldFont);
        fileNameField.setFont(fieldFont);


        JPanel secondPanel = generatePanel(
                numberSizeField,
                bufferSizeField,
                fileNameField
        );

        JPanel inputSection = new JPanel() ;
        inputSection.add(firstPanel) ;
        inputSection.add(secondPanel) ;



        JButton producerButton = new JButton("Start Producer") ;

        producerButton.setFont(labelFont);



        // Third Section
        JLabel countLabel = new JLabel("Number of Primes :") ;
        JLabel maxLabel = new JLabel("Max Prime :") ;
        JLabel timeLabel = new JLabel("Time elapsed :") ;

        countLabel.setFont(labelFont);
        maxLabel.setFont(labelFont);
        timeLabel.setFont(labelFont);

        JPanel thirdPanel = generatePanel(
                countLabel,
                maxLabel,
                timeLabel
        );

        // Forth Section

        JLabel countValue = new JLabel("########") ;
        JLabel maxValue = new JLabel("########") ;
        JLabel timeValue = new JLabel("########") ;

        countValue.setFont(labelFont);
        maxValue.setFont(labelFont);
        timeValue.setFont(labelFont);

        JPanel forthPanel = generatePanel(
                countValue,
                maxValue,
                timeValue
        );

        JPanel outputSection = new JPanel();

        outputSection.add(thirdPanel) ;
        outputSection.add(forthPanel) ;


        JPanel mainSection = generatePanel(
                inputSection,
                producerButton,
                outputSection
        ) ;


        jFrame.add(mainSection) ;

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numberSize = Integer.parseInt(numberSizeField.getText()) ;
                int bufferSize = Integer.parseInt(bufferSizeField.getText()) ;
                String fileName = fileNameField.getText() ;
                createFile(fileName);
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(fileName);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Buffer buffer = new Buffer(bufferSize) ;
                JLabel[] labels = {
                        countValue,
                        maxValue,
                        timeValue
                } ;
                Controller controller = new Controller(fileWriter,System.currentTimeMillis(),labels) ;
                Thread producer = new Producer(buffer,numberSize) ;
                Thread consumer = new Consumer(buffer,controller) ;
                producer.start();
                consumer.start();
            }
        };

        producerButton.addActionListener(actionListener);

        jFrame.pack();
        jFrame.setSize(450, 350);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}