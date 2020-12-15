package View;

import Controller.ChessController;
import Model.Chess.PieceType;
import Model.PieceColor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Panelling {

    public static ArrayList<JLabel> labels;
    public static ChessController cc;
    public static JLabel playerTurn;

    public Panelling(ChessController cc){
        this.cc = cc;

        labels = new ArrayList<JLabel>();
        playerTurn = new JLabel("Red turn!",JLabel.CENTER);
        playerTurn.setFont(new Font("Arial", Font.PLAIN, 20));
        playerTurn.setForeground(Color.RED.darker());

        for (int i = 0; i < 56; i++) {
            if (cc.board.get(i) == null) {
                JLabel label = new JLabel();
                label.setHorizontalAlignment(0);
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
                label.addMouseListener(new Table.MyMouseListener(i));
                labels.add(label);
            } else if (cc.board.get(i).getPieceType() == PieceType.TRIANGLE) {
                if (cc.board.get(i).getPieceColor() == PieceColor.BLUE) {
                    JLabel label = new JLabel(cc.icons.get(0),JLabel.CENTER);
                    label.setOpaque(true);
                    label.setBackground(Color.WHITE);
                    label.addMouseListener(new Table.MyMouseListener(i));
                    labels.add(label);
                } else if (cc.board.get(i).getPieceColor() == PieceColor.RED) {
                    JLabel label = new JLabel(cc.icons.get(5),JLabel.CENTER);
                    label.setOpaque(true);
                    label.setBackground(Color.WHITE);
                    label.addMouseListener(new Table.MyMouseListener(i));
                    labels.add(label);
                }
            } else if (cc.board.get(i).getPieceType() == PieceType.PLUS) {
                if (cc.board.get(i).getPieceColor() == PieceColor.BLUE) {
                    JLabel label = new JLabel(cc.icons.get(1),JLabel.CENTER);
                    label.setOpaque(true);
                    label.setBackground(Color.WHITE);
                    label.addMouseListener(new Table.MyMouseListener(i));
                    labels.add(label);
                } else if (cc.board.get(i).getPieceColor() == PieceColor.RED) {
                    JLabel label = new JLabel(cc.icons.get(6),JLabel.CENTER);
                    label.setOpaque(true);
                    label.setBackground(Color.WHITE);
                    label.addMouseListener(new Table.MyMouseListener(i));
                    labels.add(label);
                }
            } else if (cc.board.get(i).getPieceType() == PieceType.CHEVRON) {
                if (cc.board.get(i).getPieceColor() == PieceColor.BLUE) {
                    JLabel label = new JLabel(cc.icons.get(2),JLabel.CENTER);
                    label.setOpaque(true);
                    label.setBackground(Color.WHITE);
                    label.addMouseListener(new Table.MyMouseListener(i));
                    labels.add(label);
                } else if (cc.board.get(i).getPieceColor() == PieceColor.RED) {
                    JLabel label = new JLabel(cc.icons.get(7),JLabel.CENTER);
                    label.setOpaque(true);
                    label.setBackground(Color.WHITE);
                    label.addMouseListener(new Table.MyMouseListener(i));
                    labels.add(label);
                }
            } else if (cc.board.get(i).getPieceType() == PieceType.SUN) {
                if (cc.board.get(i).getPieceColor() == PieceColor.BLUE) {
                    JLabel label = new JLabel(cc.icons.get(3),JLabel.CENTER);
                    label.setOpaque(true);
                    label.setBackground(Color.WHITE);
                    label.addMouseListener(new Table.MyMouseListener(i));
                    labels.add(label);
                } else if (cc.board.get(i).getPieceColor() == PieceColor.RED) {
                    JLabel label = new JLabel(cc.icons.get(8),JLabel.CENTER);
                    label.setOpaque(true);
                    label.setBackground(Color.WHITE);
                    label.addMouseListener(new Table.MyMouseListener(i));
                    labels.add(label);
                }
            } else if (cc.board.get(i).getPieceType() == PieceType.ARROW) {
                if (cc.board.get(i).getPieceColor() == PieceColor.BLUE) {
                    JLabel label = new JLabel(cc.icons.get(4),JLabel.CENTER);
                    label.setOpaque(true);
                    label.setBackground(Color.WHITE);
                    label.addMouseListener(new Table.MyMouseListener(i));
                    labels.add(label);
                } else if (cc.board.get(i).getPieceColor() == PieceColor.RED) {
                    JLabel label = new JLabel(cc.icons.get(9),JLabel.CENTER);
                    label.setOpaque(true);
                    label.setBackground(Color.WHITE);
                    label.addMouseListener(new Table.MyMouseListener(i));
                    labels.add(label);
                }

            }
        }
    }
}
