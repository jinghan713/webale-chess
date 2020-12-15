package Controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Model.MoveDirection;
import Model.Piece;
import Model.PieceColor;
import Model.Chess.Arrow;
import Model.Chess.Chevron;
import Model.Chess.PieceType;
import Model.Chess.Plus;
import Model.Chess.Sun;
import Model.Chess.Triangle;
import View.Table;


public class ChessController {
    public static ArrayList<Piece> board;
    public static Table view;

    private static ChessController controls = new ChessController();
    public static ArrayList<ImageIcon> icons = new ArrayList<ImageIcon>();
    private Integer step;
    private Integer redStep;
    private Integer blueStep;
    //private Integer currPieceIndex;
    private ArrayList<Integer> finalMoves;
    private Integer source;
    private Piece currentPiece;
    private Integer destination;
    private Integer swapRed;
    private Integer swapBlue;


    public ChessController() { 
        board = new ArrayList<Piece>(Collections.nCopies(56, null));
        this.step = 0;
        this.blueStep = 0;
        this.redStep = 0;
        //this.currPieceIndex = null;
        this.finalMoves = new ArrayList<Integer>();
        this.source = null;
        this.currentPiece = null;
        this.destination = null;
        this.swapRed = null;
        this.swapBlue = null;
    }
    public void setSource(Integer source)
    {
        this.source = source;
    }

    public Integer getSource()
    {
        return this.source;
    }

    public PieceColor getTurn()
    {
        if(step % 2 == 0)
        {
            return PieceColor.RED;
        }
        else
        {
            return PieceColor.BLUE;
        }
    }

    public Integer getRedStep()
    {
        return redStep;
    }

    public Integer getBlueStep()
    {
        return blueStep;
    }

    public void setStep(Integer step)
    {
        this.step = step;
    }

    public void addStep(PieceColor pieceColor)
    {
        if(pieceColor == PieceColor.RED)
        {
            this.redStep++;
        }
        else
        {
            this.blueStep++;
        }
        this.step++;
    }

    /*public void setCurrPieceIndex(Integer currPieceIndex)
    {
        this.currPieceIndex = currPieceIndex;
    }

    public Integer getCurrPieceIndex()
    {
        return this.currPieceIndex;
    }*/

    public void setFinalMoves(ArrayList<Integer> attackingPos)
    {
        this.finalMoves = attackingPos;
    }

    public ArrayList<Integer> getFinalMoves()
    {
        return this.finalMoves;
    }

    public boolean isClicked(){
        return source != null;
    }

    public void resetState()
    {
        controls.source = null;
        controls.finalMoves.clear();
        controls.currentPiece = null;
        controls.destination = null;
    }


    public void saveGame(File fileName, String gameName) {

        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write(step + "\n");
            fw.write(redStep + "\n");
            fw.write(blueStep + "\n");

            for (Integer i = 0; i < controls.board.size(); i++) {
                if (controls.board.get(i) != null) {
                    fw.write(i +"\n");
                    fw.write(controls.board.get(i).getIndex() + "\n");
                    fw.write(controls.board.get(i).getPieceColor() + "\n");
                    fw.write(controls.board.get(i).getPieceType() + "\n");
                } else
                    continue;
            }
            fw.flush();
            fw.close();
            Table.saveBox("Game is successfully saved as " + gameName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame(File fileName) {
        Integer temp1;
        Integer temp2;
        String temp3;
        String temp4;

        try {
            Scanner sf = new Scanner(fileName);
            controls.setStep(sf.nextInt());
            redStep = sf.nextInt();
            blueStep = sf.nextInt();
            controls.board = new ArrayList<Piece>(Collections.nCopies(56, null));
            while (sf.hasNext()) {

                temp1 = sf.nextInt();
                temp2 = sf.nextInt();
                temp3 = sf.next();
                temp4 = sf.next();


                if (PieceType.valueOf(temp4) == PieceType.SUN) {
                    controls.board.set(temp1, new Sun(temp2, PieceColor.valueOf(temp3)));

                } else if (PieceType.valueOf(temp4) == PieceType.CHEVRON) {
                    controls.board.set(temp1, new Chevron(temp2, PieceColor.valueOf(temp3)));

                } else if (PieceType.valueOf(temp4) == PieceType.TRIANGLE) {
                    controls.board.set(temp1, new Triangle(temp2, PieceColor.valueOf(temp3)));

                } else if (PieceType.valueOf(temp4) == PieceType.PLUS) {
                    controls.board.set(temp1, new Plus(temp2, PieceColor.valueOf(temp3)));

                } else if (PieceType.valueOf(temp4) == PieceType.ARROW){
                    controls.board.set(temp1, new Arrow(temp2, PieceColor.valueOf(temp3)));

                }
            }
            sf.close();
            fileName.delete();

            controls.refreshPanel();
            controls.resetState();
            controls.changePlayerTurn();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setClass(ChessController cc){
        controls = cc;
    }
    public void setView(Table view){
        this.view = view;
    }
    //to initailize the chess
    public void initialize() {
        board = new ArrayList<Piece>(Collections.nCopies(56, null));
        setIcons();
        board.set(0, new Plus(0, PieceColor.BLUE)); 
        board.set(1, new Triangle(1, PieceColor.BLUE));
        board.set(2, new Chevron(2, PieceColor.BLUE));
        board.set(3, new Sun(3, PieceColor.BLUE));
        board.set(4, new Chevron(4, PieceColor.BLUE));
        board.set(5, new Triangle(5, PieceColor.BLUE)); 
        board.set(6, new Plus(6, PieceColor.BLUE));
        board.set(7, new Arrow(7, PieceColor.BLUE));
        board.set(9, new Arrow(9, PieceColor.BLUE));
        board.set(11,new Arrow(11, PieceColor.BLUE));
        board.set(13,new Arrow(13, PieceColor.BLUE));

        board.set(42, new Arrow(42, PieceColor.RED));
        board.set(44, new Arrow(44, PieceColor.RED));
        board.set(46, new Arrow(26, PieceColor.RED));
		board.set(48, new Arrow(48, PieceColor.RED));
        board.set(49, new Plus(49, PieceColor.RED));
        board.set(50, new Triangle(50, PieceColor.RED));
        board.set(51, new Chevron(51, PieceColor.RED));
        board.set(52, new Sun(52, PieceColor.RED));
        board.set(53, new Chevron(53, PieceColor.RED));
        board.set(54, new Triangle(54, PieceColor.RED));
        board.set(55, new Plus(55, PieceColor.RED));
    }
   

    public boolean isOnBoard(Integer index, Integer distance, Integer line) {
        Integer engageMove = index + distance;

        if (engageMove < 0 || engageMove > 55) {
            return false;
        } else {
            if (Math.abs(engageMove / 7 - index / 7) != line) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<Integer> SunValidMoves(Integer index) {
        ArrayList<Integer> pos = new ArrayList<Integer>();
        System.out.println(index);
        //up and down
        if (isOnBoard(index, -7, 1)) {
            if(board.get(index - 7) != null){
                if(board.get(index - 7).getPieceColor() != getTurn()) {
                    pos.add(index - 7);
                }
            }
            else
            {pos.add(index - 7);}
        }
        if (isOnBoard(index, +7, 1)) {
            if(board.get(index + 7) != null){
                if(board.get(index + 7).getPieceColor() != getTurn()) {
                    pos.add(index + 7);
                }
            }
            else
            {pos.add(index + 7);}
        }
        //left and right
        if (isOnBoard(index, +1, 0)) {
            if(board.get(index + 1) != null){
                if(board.get(index + 1).getPieceColor() != getTurn()) {
                    pos.add(index + 1);
                }
            }
            else
            {pos.add(index + 1);}
        }
        if (isOnBoard(index, -1, 0)) {
            if(board.get(index - 1) != null){
                if(board.get(index - 1).getPieceColor() != getTurn()) {
                    pos.add(index - 1);
                }
            }
            else
            {pos.add(index - 1);}
        }
        //diagonally
        if (isOnBoard(index, -6, 1)) {
            if(board.get(index -6) != null){
                if(board.get(index -6).getPieceColor() != getTurn()) {
                    pos.add(index -6);
                }
            }
            else
            {pos.add(index -6);}
        }
        if (isOnBoard(index, -8, 1)) {
            if(board.get(index -8) != null){
                if(board.get(index -8).getPieceColor() != getTurn()) {
                    pos.add(index -8);
                }
            }
            else
            {pos.add(index -8);}
        }
        if (isOnBoard(index, +6, 1)) {
            if(board.get(index +6) != null){
                if(board.get(index +6).getPieceColor() != getTurn()) {
                    pos.add(index +6);
                }
            }
            else
            {pos.add(index +6);}
        }
        if (isOnBoard(index, +8, 1)) {
            if(board.get(index +8) != null){
                if(board.get(index +8).getPieceColor() != getTurn()) {
                    pos.add(index +8);
                }
            }
            else
            {pos.add(index +8);}
        }

        return pos;
    }

    public ArrayList<Integer> ArrowValidMoves(Integer index) {
        System.out.println(index);
        ArrayList<Integer> pos = new ArrayList<Integer>();
        if (board.get(index).getPieceColor() == PieceColor.RED||board.get(index).getPieceColor() == PieceColor.BLUE) {
            if (index <= 6) {
                board.get(index).setMoveDirection(MoveDirection.BACKWARD);
            } else if (index >= 49) {
                board.get(index).setMoveDirection(MoveDirection.FORWARD);
            }
        }

        if ((board.get(index).getMoveDirection() == MoveDirection.FORWARD && board.get(index).getPieceColor() == PieceColor.RED) || (board.get(index).getMoveDirection() == MoveDirection.FORWARD && board.get(index).getPieceColor() == PieceColor.BLUE)) {
            for (int i = 1; i < 3; i++) {
                if (isOnBoard(index, -7 * i, i)) {
                    if(board.get(index - 7*i) != null && board.get(index - 7*i).getPieceColor() == getTurn()) {
                        break;
                    }
                    if(board.get(index - 7*i) != null && board.get(index - 7*i).getPieceColor() != getTurn()) {
                        pos.add(index - 7*i);
                        break;
                    }
                    else {
                        pos.add(index - 7*i);
                    }
                }
            }
        } else if ((board.get(index).getMoveDirection() == MoveDirection.BACKWARD && board.get(index).getPieceColor() == PieceColor.RED) || (board.get(index).getMoveDirection() == MoveDirection.BACKWARD && board.get(index).getPieceColor() == PieceColor.BLUE)) {
            for (int i = 1; i < 3; i++) {
                if (isOnBoard(index, 7 * i, i)) {
                    if(board.get(index + 7*i) != null && board.get(index + 7*i).getPieceColor() == getTurn()) {
                        break;
                    }
                    if(board.get(index + 7*i) != null && board.get(index + 7*i).getPieceColor() != getTurn()) {
                        pos.add(index + 7*i);
                        break;
                    }
                    else {
                        pos.add(index + 7*i);
                    }
                }
            }
        }

        return pos;
    }

    public ArrayList<Integer> ChevronValidMoves(Integer index){
        ArrayList<Integer> pos = new ArrayList<Integer>();
        
        //up and down
        if (isOnBoard(index, -15, 2)) {
            if(board.get(index - 15) != null){
                if(board.get(index - 15).getPieceColor() != getTurn()) {
                    pos.add(index - 15);
                }
            }
            else
            {pos.add(index - 15);}
        }
        if (isOnBoard(index, -13, 2)) {
            if(board.get(index - 13) != null){
                if(board.get(index - 13).getPieceColor() != getTurn()) {
                    pos.add(index - 13);
                }
            }
            else
            {pos.add(index - 13);}
        }
        if (isOnBoard(index, +15, 2)) {
            if(board.get(index + 15) != null){
                if(board.get(index + 15).getPieceColor() != getTurn()) {
                    pos.add(index + 15);
                }
            }   
            else
            {pos.add(index + 15);}
        }
        if (isOnBoard(index, +13, 2)) {
            if(board.get(index + 13) != null){
                if(board.get(index + 13).getPieceColor() != getTurn()) {
                    pos.add(index + 13);
                }
            }   
            else
            {pos.add(index + 13);}
        }

        //left and right
        if (isOnBoard(index, +9, 1)) {
            if(board.get(index + 9) != null){
                if(board.get(index + 9).getPieceColor() != getTurn()) {
                    pos.add(index + 9);
                }
            }
            else
            {pos.add(index + 9);}
        }
        if (isOnBoard(index, +5, 1)) {
            if(board.get(index + 5) != null){
                if(board.get(index + 5).getPieceColor() != getTurn()) {
                    pos.add(index + 5);
                }
            }
            else
            {pos.add(index + 5);}
        }
        if (isOnBoard(index, -9, 1)) {
            if(board.get(index - 9) != null){
                if(board.get(index - 9).getPieceColor() != getTurn()) {
                    pos.add(index - 9);
                }
            }
            else
            {pos.add(index - 9);}
        }
        if (isOnBoard(index, -5, 1)) {
            if(board.get(index - 5) != null){
                if(board.get(index - 5).getPieceColor() != getTurn()) {
                    pos.add(index - 5);
                }
            }
            else
            {pos.add(index - 5);}
        }

        return pos;
    }

    public ArrayList<Integer>TriangleValidMoves(Integer index){
        ArrayList<Integer>pos = new ArrayList<Integer>();
        //forward-right
        for (int i = 1; i < 7; i++) {
            if (isOnBoard(index, -6 * i, i)) {
                if(board.get(index -6*i) != null && board.get(index -6*i).getPieceColor() == getTurn()) {
                    break;
                }
                if(board.get(index -6*i) != null && board.get(index -6*i).getPieceColor() != getTurn()) {
                    pos.add(index -6*i);
                    break;
                }
                else {
                    pos.add(index -6*i);
                }
            }
        }

        //forward-left
        for (int i = 1; i < 7; i++) {
            if (isOnBoard(index, -8 * i, i)) {
                if(board.get(index -8*i) != null && board.get(index -8*i).getPieceColor() == getTurn()) {
                    break;
                }
                if(board.get(index -8*i) != null && board.get(index -8*i).getPieceColor() != getTurn()) {
                    pos.add(index -8*i);
                    break;
                }
                else {
                    pos.add(index -8*i);
                }
            }
        }

        //backwards-right
        for (int i = 1; i < 7; i++) {
            if (isOnBoard(index, 8 * i, i)) {
                if(board.get(index +8*i) != null && board.get(index +8*i).getPieceColor() == getTurn()) {
                    break;
                }
                if(board.get(index +8*i) != null && board.get(index +8*i).getPieceColor() != getTurn()) {
                    pos.add(index +8*i);
                    break;
                }
                else {
                    pos.add(index +8*i);
                }
            }
        }

        //backwards-left
        for (int i = 1; i < 7; i++) {
            if (isOnBoard(index, 6 * i, i)) {
                if(board.get(index +6*i) != null && board.get(index +6*i).getPieceColor() == getTurn()) {
                    break;
                }
                if(board.get(index +6*i) != null && board.get(index +6*i).getPieceColor() != getTurn()) {
                    pos.add(index +6*i);
                    break;
                }
                else {
                    pos.add(index +6*i);
                }
            }
        }

        return pos;
    }

    public ArrayList<Integer>PlusValidMoves(Integer index){
        ArrayList<Integer>pos = new ArrayList<Integer>();


        //right
        for(int i = 1; i <= 7; i++){
            if(isOnBoard(index,i,0)){
                if(board.get(index +i) != null && board.get(index +i).getPieceColor() == getTurn()) {
                    break;
                }
                if(board.get(index +i) != null && board.get(index +i).getPieceColor() != getTurn()) {
                    pos.add(index +i);
                    break;
                }
                else {
                    pos.add(index +i);
                }
            }
        }
        //left
        for(int i = -1; i >= -7; i--){
            if(isOnBoard(index,i,0)){
                if(board.get(index +i) != null && board.get(index +i).getPieceColor() == getTurn()) {
                    break;
                }
                if(board.get(index +i) != null && board.get(index +i).getPieceColor() != getTurn()) {
                    pos.add(index +i);
                    break;
                }
                else {
                    pos.add(index +i);
                }
            }
        }

        //forward
        for (int i = 1; i < 8; i++) {
            if (isOnBoard(index, -7 * i, i)) {
                if(board.get(index -7 * i) != null && board.get(index -7 * i).getPieceColor() == getTurn()) {
                    break;
                }
                if(board.get(index -7 * i) != null && board.get(index -7 * i).getPieceColor() != getTurn()) {
                    pos.add(index -7 * i);
                    break;
                }
                else {
                    pos.add(index -7 * i);
                }
            }
        }

        //backwards
        for (int i = 1; i < 8; i++) {
            if (isOnBoard(index, 7 * i, i)) {
                if(board.get(index +7 * i) != null && board.get(index +7 * i).getPieceColor() == getTurn()) {
                    break;
                }
                if(board.get(index +7 * i) != null && board.get(index +7 * i).getPieceColor() != getTurn()) {
                    pos.add(index +7 * i);
                    break;
                }
                else {
                    pos.add(index +7 * i);
                }
            }
        }

        return pos;
    }




    public boolean shouldSwapRed()
    {
        if(getRedStep() > 0)
        {
            if(getRedStep() % 2 == 0 ) //Every 2 Red turns swap Triangle with Plus
            {
                return this.swapRed % 2 != 0; //Swap Red
            }
        }
        else
        {
            return false;
        }
        return false;
    }

    public boolean shouldSwapBlue() 
    {
        if(getBlueStep() > 0)
        {
            if(getBlueStep() % 2 == 0) //Every 2 Blue turns swap Triangle with Plus
            {
                return this.swapBlue % 2 != 0; //Swap Blue
            }
        }
        else
        {
            return false;
        }
        return false;
    }

    public void swapRed(){
        if(getRedStep() % 2 == 0 ) {
            for(int i=0; i<board.size(); i++) //Loop the whole board
            {
                if(board.get(i) != null)
                {
                    if(board.get(i).getPieceType() == PieceType.TRIANGLE && board.get(i).getPieceColor() == PieceColor.RED) //if Red Triangle valid
                    {
                        board.set(i, new Plus(i, PieceColor.RED)); //Swap to Plus
                    }
                    else if(board.get(i).getPieceType() == PieceType.PLUS && board.get(i).getPieceColor() == PieceColor.RED) //if Red Plus valid
                    {
                        board.set(i, new Triangle(i, PieceColor.RED)); //Swap to Triangle
                    }
                }
            }
        }
    }

    public void swapBlue()
    {
        if(getBlueStep() % 2 == 0){
            for(int i=0; i<board.size(); i++) //Loop the whole board
            {
                if(board.get(i) != null)
                {
                    if(board.get(i).getPieceType() == PieceType.TRIANGLE && board.get(i).getPieceColor() == PieceColor.BLUE) //if Blue Triangle valid
                    {
                        board.set(i, new Plus(i, PieceColor.BLUE)); //Swap to Plus
                    }
                    else if(board.get(i).getPieceType() == PieceType.PLUS && board.get(i).getPieceColor() == PieceColor.BLUE) //if Blue Plus valid
                    {
                        board.set(i, new Triangle(i, PieceColor.BLUE)); //Swap to Triangle
                    }
                }
            }
        }
    }

    public void chessPressed(Integer index) {
        if (controls.source == null) {
            controls.source = index; //Declare index = source
            controls.currentPiece = controls.board.get(index); //currentPiece is the initial press of the chess piece
            System.out.println("Works1");

            if (controls.currentPiece == null) {
                resetValidPanel(); //To reset if no destination piece is selected
                resetState();
                System.out.println("Works2");
            } 
            else {
                if (controls.currentPiece.getPieceColor() != getTurn()) {
                    resetState();
                } else if (controls.currentPiece.getPieceType() == PieceType.TRIANGLE) {
                    resetValidPanel();
                    setFinalMoves(controls.TriangleValidMoves(index));
                    showValidPanel(finalMoves);
                    refreshPanel();

                } else if (controls.currentPiece.getPieceType() == PieceType.CHEVRON) {
                    resetValidPanel();
                    setFinalMoves(controls.ChevronValidMoves(index));
                    showValidPanel(finalMoves);
                    refreshPanel();

                } else if (controls.currentPiece.getPieceType() == PieceType.PLUS) {
                    resetValidPanel();
                    setFinalMoves(controls.PlusValidMoves(index));
                    showValidPanel(finalMoves);
                    refreshPanel();

                } else if (controls.currentPiece.getPieceType() == PieceType.SUN) {
                    resetValidPanel();
                    setFinalMoves(controls.SunValidMoves(index));
                    showValidPanel(finalMoves);
                    refreshPanel();

                } else if (controls.currentPiece.getPieceType() == PieceType.ARROW) {
                    resetValidPanel();
                    setFinalMoves(controls.ArrowValidMoves(index));
                    showValidPanel(finalMoves);
                    refreshPanel();

                }

            }
        } 
        else {
            controls.destination = index;
            System.out.println("Works3");

            if (controls.finalMoves.contains(controls.destination)) {
                resetValidPanel();
                controls.board.set(controls.source, null);
                controls.board.set(controls.destination, controls.currentPiece);
                controls.addStep(controls.currentPiece.getPieceColor());
                System.out.print("red: "+getRedStep());
                System.out.print("blue: "+getBlueStep());
                System.out.print("total: "+step);
                if(shouldSwapRed())
                {
                    swapRed();
                }
                else if(shouldSwapBlue())
                {
                    swapBlue();
                }
                this.swapRed = this.redStep;
                this.swapBlue = this.blueStep;
                resetState();

                if(isWin() == PieceColor.RED)
                {
                    System.out.println("Red Win");
                    Table.infoBox("Congratulations, Red Wins!");
                    controls.newGame();
                    rotateBoard();
                }
                else if(isWin() == PieceColor.BLUE)
                {
                    System.out.println("Blue Win");
                    Table.infoBox("Congratulations, Blue Wins!");
                    controls.newGame();
                    rotateBoard();
                }
                if (controls.getTurn() == PieceColor.BLUE){
                    reverseIcon();
                }
                else
                {
                    setIcons();
                }
                changePlayerTurn();
                rotateBoard();
                refreshPanel();
            }
            else{
                resetState();
            }

        }
    }

    public void refreshPanel(){
        view.p.removeAll();

        for(int i=0; i<56; i++)
        {
            if(controls.board.get(i) == null) {
                view.panel.labels.get(i).setIcon(null);
            } else if(controls.board.get(i).getPieceColor() == PieceColor.RED)
            {
                if(controls.board.get(i).getPieceType() == PieceType.ARROW)
                {
                    if(controls.board.get(i).getMoveDirection() == MoveDirection.FORWARD) {
                        view.panel.labels.get(i).setIcon(icons.get(9));
                    }
                    else
                    {
                        view.panel.labels.get(i).setIcon(new ImageIcon(rotateIcon(imageToBufferedImage(controls.icons.get(9)), -Math.PI/1)));
                    }
                }
                else if(controls.board.get(i).getPieceType() == PieceType.SUN)
                {
                    view.panel.labels.get(i).setIcon(icons.get(8));
                }
                else if(controls.board.get(i).getPieceType() == PieceType.TRIANGLE)
                {
                    view.panel.labels.get(i).setIcon(icons.get(5));
                }
                else if(controls.board.get(i).getPieceType() == PieceType.PLUS)
                {
                    view.panel.labels.get(i).setIcon(icons.get(6));
                }
                else if(controls.board.get(i).getPieceType() == PieceType.CHEVRON)
                {
                    if(controls.board.get(i).getMoveDirection() == MoveDirection.FORWARD) {
                        view.panel.labels.get(i).setIcon(icons.get(7));
                    }
                    else
                    {
                        view.panel.labels.get(i).setIcon(new ImageIcon(rotateIcon(imageToBufferedImage(controls.icons.get(7)), -Math.PI/1)));
                    }
                }
            }
            else if(controls.board.get(i).getPieceColor() == PieceColor.BLUE)
            {
                if(controls.board.get(i).getPieceType() == PieceType.ARROW)
                {
                    if(controls.board.get(i).getMoveDirection() == MoveDirection.FORWARD) {
                        view.panel.labels.get(i).setIcon(icons.get(4));
                    }
                    else
                    {
                        view.panel.labels.get(i).setIcon(new ImageIcon(rotateIcon(imageToBufferedImage(controls.icons.get(4)), -Math.PI/1)));
                    }
                }
                else if(controls.board.get(i).getPieceType() == PieceType.SUN)
                {
                    view.panel.labels.get(i).setIcon(icons.get(3));
                }
                else if(controls.board.get(i).getPieceType() == PieceType.TRIANGLE)
                {
                    view.panel.labels.get(i).setIcon(icons.get(0));
                }
                else if(controls.board.get(i).getPieceType() == PieceType.PLUS)
                {
                    view.panel.labels.get(i).setIcon(icons.get(1));
                }
                else if(controls.board.get(i).getPieceType() == PieceType.CHEVRON)
                {
                    if(controls.board.get(i).getMoveDirection() == MoveDirection.FORWARD) {
                        view.panel.labels.get(i).setIcon(icons.get(2));
                    }
                    else
                    {
                        view.panel.labels.get(i).setIcon(new ImageIcon(rotateIcon(imageToBufferedImage(controls.icons.get(2)), -Math.PI/1)));
                    }
                }
            }
        }
        for(JLabel label:view.panel.labels){
            view.p.add(label);
        }
        view.p.repaint();
        view.p.revalidate();

    }

    public void showValidPanel(ArrayList<Integer> finalMoves){

        view.panel.labels.get(controls.board.indexOf(controls.currentPiece)).setBackground(Color.GRAY);

        for(int i=0; i < finalMoves.size(); i++){
            view.panel.labels.get(finalMoves.get(i)).setBackground(Color.LIGHT_GRAY);
        }

    }

    public void resetValidPanel(){
        for(int i=0; i<56; i++){
            view.panel.labels.get(i).setBackground(Color.WHITE);
        }
    }

    public void changePlayerTurn () {
        if (controls.getTurn() == PieceColor.RED) {
            controls.view.panel.playerTurn.setText("Red turn!");
            controls.view.panel.playerTurn.setForeground(Color.RED.darker());
        } else if (controls.getTurn() == PieceColor.BLUE) {
            controls.view.panel.playerTurn.setText("Blue turn!");
            controls.view.panel.playerTurn.setForeground(Color.BLUE);
        }
        controls.view.p.revalidate();
    }


    public ArrayList<ImageIcon> getIcons(){
        ArrayList<ImageIcon> icon = new ArrayList<ImageIcon>();


        ImageIcon image1 = new ImageIcon(getClass().getResource("/View/ImageIcons/Blue_Triangle.png"));
        ImageIcon image2 = new ImageIcon(getClass().getResource("/View/ImageIcons/Blue_Plus.png"));
        ImageIcon image3 = new ImageIcon(getClass().getResource("/View/ImageIcons/Blue_Chevron.png"));
        ImageIcon image4 = new ImageIcon(getClass().getResource("/View/ImageIcons/Blue_Sun.png"));
        ImageIcon image5 = new ImageIcon(getClass().getResource("/View/ImageIcons/Blue_Arrow.png"));
        ImageIcon image6 = new ImageIcon(getClass().getResource("/View/ImageIcons/Red_Triangle.png"));
        ImageIcon image7 = new ImageIcon(getClass().getResource("/View/ImageIcons/Red_Plus.png"));
        ImageIcon image8 = new ImageIcon(getClass().getResource("/View/ImageIcons/Red_Chevron.png"));
        ImageIcon image9 = new ImageIcon(getClass().getResource("/View/ImageIcons/Red_Sun.png"));
        ImageIcon image10 = new ImageIcon(getClass().getResource("/View/ImageIcons/Red_Arrow.png"));

        icon.add(image1);
        icon.add(image2);
        icon.add(image3);
        icon.add(image4);
        icon.add(image5);
        icon.add(image6);
        icon.add(image7);
        icon.add(image8);
        icon.add(image9);
        icon.add(image10);

        return icon;
    }

    public void setIcons(){
        this.icons = getIcons();
    }

    public void newGame() {
        controls.initialize();
        controls.refreshPanel();
        controls.resetState();
        controls.setStep(0);
        controls.redStep = 0;
        controls.blueStep = 0;
        controls.swapRed = null;
        controls.swapBlue = null;
        controls.changePlayerTurn();
        controls.resetValidPanel();

    }

    public void rotateBoard(){
        Collections.reverse(controls.board);
    }

    public PieceColor isWin()
    {
        int red = 0;
        int blue = 0;
        for(int i=0; i<controls.board.size(); i++)
        {
            if(controls.board.get(i) != null){
                if(controls.board.get(i).getPieceColor() == PieceColor.RED)
                {
                    red++;
                    if(controls.board.get(i).getPieceType() != PieceType.SUN)
                    {
                        red--;
                    }
                    else if(controls.board.get(i).getPieceType() == PieceType.SUN)
                    {
                        red++;
                    }
                }
                else if(controls.board.get(i).getPieceColor() == PieceColor.BLUE)
                {
                    blue++;
                    if(controls.board.get(i).getPieceType() != PieceType.SUN)
                    {
                        blue--;
                    }
                    else if(controls.board.get(i).getPieceType() == PieceType.SUN)
                    {
                        blue++;
                    }
                }
            }
        }

        if(red == 0)
        {
            return PieceColor.BLUE;
        }
        else if(blue == 0)
        {
            return PieceColor.RED;
        }
        else
            return null;
    }

    public void reverseIcon()
    {
        BufferedImage temp[] = new BufferedImage[10];
        for(int i=0; i<getIcons().size(); i++)
        {
            temp[i] = imageToBufferedImage(getIcons().get(i));
            controls.icons.set(i, new ImageIcon(rotateIcon(temp[i], -Math.PI/1)));
        }
        System.out.println("2222");
    }

    public BufferedImage rotateIcon(BufferedImage image, double angle) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        double sin = Math.abs(Math.sin(angle));
        double cos = Math.abs(Math.cos(angle));
        int width = image.getWidth();
        int height = image.getHeight();
        int nwidth = (int)Math.floor((double)width * cos + (double)height * sin);
        int nheight = (int)Math.floor((double)height * cos + (double)width * sin);
        int transparency = image.getColorModel().getTransparency();
        BufferedImage newicon = gc.createCompatibleImage(nwidth, nheight, transparency);
        Graphics2D g = newicon.createGraphics();
        g.translate((nwidth - width) / 2, (nheight - height) / 2);
        g.rotate(angle, (double)(width / 2), (double)(height / 2));
        g.drawRenderedImage(image, (AffineTransform)null);
        return newicon;
    }

    public BufferedImage imageToBufferedImage(ImageIcon icon) {
        BufferedImage bi = new BufferedImage
                (icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        icon.paintIcon(null, g, 0,0);
        g.dispose();

        return bi;
    }
}