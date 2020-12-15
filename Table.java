package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controller.ChessController;


public class Table {

    public static Panelling panel;
    public static ArrayList<ImageIcon> icon;
    public BoardPanel p = new BoardPanel();
    public StatePanel statePanel;
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_DIMENSION2 = new Dimension(135, 600);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private final static Dimension LEFT_SIZE = new Dimension(100, 100);
    public static ChessController cc;

    public Table(ChessController cc, Panelling panels) {
        this.panel = panel;
        this.cc = cc;
        statePanel = new StatePanel(cc);
    }

    public Table(){

        }

    public static void infoBox(String infoMessage)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "Game Over! ", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void saveBox(String infoMessage)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "File Saved! ", JOptionPane.INFORMATION_MESSAGE);
    }

    private class BoardPanel1 extends JPanel{
        BoardPanel1(){
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();


        }
    }
//Setting the Chess Panels
    public class BoardPanel extends JPanel{
        BoardPanel(){
            super(new GridLayout(8,7,5,5));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setBackground(Color.BLACK);
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();

            for (int i = 0; i <56; i++){
                add(Panelling.labels.get(i));
            }
        }

    }


    public class StatePanel extends JPanel{


        private JButton newGameButton;
        private JButton loadGameButton;
        private JButton saveGameButton;
        public JLabel playerTurn;
        public ChessController controls;

        public StatePanel(ChessController controls)
        {
            newGameButton = new JButton();
            loadGameButton = new JButton();
            saveGameButton = new JButton();
            playerTurn = new JLabel();
            this.controls = controls;
            intitialize(controls);
            setPreferredSize(BOARD_PANEL_DIMENSION2);
        }

        public void setChessController(ChessController controls){
            this.controls = controls;
        }

        private void intitialize(ChessController sc) {
            setLayout(new GridLayout(4, 1));
            newGameButton = new JButton("New Game");
            newGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sc.newGame();
                }
            });
            add(newGameButton);

            saveGameButton = new JButton("Save Game");
            saveGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String path = "src\\GameFile\\";
                    String game = "Game";
                    String countGameFile = "CountGame.txt";
                    try {
                        File file = new File("src\\GameFile\\CountGame.txt");
                        Scanner sf = new Scanner(file);
                        Integer numGameSaved = sf.nextInt();
                        Integer recordCount = numGameSaved +1;
                        sf.close();
                        String totalGameSaved = numGameSaved.toString();
                        String text = ".txt";
                        String saveFileName = path + game + totalGameSaved + text;
                        String gameName = game + totalGameSaved +text;
                        File saveFile = new File(saveFileName);
                        controls.saveGame(saveFile,gameName);

                        file.delete();

                        String writeToNewFile = path + countGameFile;
                        FileWriter fw = new FileWriter(writeToNewFile);
                        fw.write(String.valueOf(recordCount));

                        fw.close();
                    }

                    catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });
            add(saveGameButton);

            loadGameButton = new JButton("Load Game");
            loadGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fl = new JFileChooser(new File("src\\GameFile"));
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                    fl.setFileFilter(filter);
                    fl.setDialogTitle("Choose a game to load");
                    fl.setDialogType(JFileChooser.OPEN_DIALOG);
                    int returnLoadFileValue = fl.showOpenDialog(null);
                    if (returnLoadFileValue == JFileChooser.APPROVE_OPTION) {
                        try {
                            File loadFileName = fl.getSelectedFile();


                            //Call loadGame function from StateController.java
                            controls.loadGame(loadFileName);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            add(loadGameButton);


            add(Panelling.playerTurn);
        }


        public void createLabel(){
            JLabel playerTurn = new JLabel();
        }



    }
    public static class MyMouseListener implements MouseListener{

        int index;

        public MyMouseListener(int index){
            this.index = index;
        }
        @Override
        public void mouseClicked(MouseEvent e) {


        }

        @Override
        public void mousePressed(MouseEvent e) {
            Toolkit.getDefaultToolkit().beep();
            System.out.print(index);
            cc.chessPressed(index);
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
