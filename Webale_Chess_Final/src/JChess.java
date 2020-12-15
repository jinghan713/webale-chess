import Controller.ChessController;
import View.Panelling;
import View.Table;

import javax.swing.*;
import java.awt.*;

public class JChess {

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ChessController cc = new ChessController();
                cc.setClass(cc);
                cc.initialize();
                Panelling panels = new Panelling(cc);
                Table view = new Table(cc, panels);
                cc.setView(view);



                JFrame f = new JFrame("Webale Chess");
                f.setSize(900,700);
                f.setMinimumSize(new Dimension(900,700));
                f.setVisible(true);
                f.setLayout(new BorderLayout());
                f.add(cc.view.p, BorderLayout.CENTER);
                f.add(cc.view.statePanel, BorderLayout.WEST);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            }


        });
    }
}
