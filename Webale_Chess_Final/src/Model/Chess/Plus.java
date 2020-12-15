package Model.Chess;

import Model.MoveDirection;
import Model.Piece;
import Model.PieceColor;

//import java.util.ArrayList;

public class Plus extends Piece {
    private PieceType pieceType;
    private MoveDirection moveDirection;

    public Plus(int index, PieceColor pieceColor){
        super(index, pieceColor);
        pieceType = PieceType.PLUS;
    }


    @Override
    public PieceType getPieceType() {
        return this.pieceType;
    }



    public PieceType getType() {
        return this.pieceType;
    }

    @Override
    public Integer getIndex(){
        return this.index;
    }

    @Override
    public PieceColor getPieceColor(){
        return this.pieceColor;
    }

    @Override
    public MoveDirection getMoveDirection()
    {
        return this.moveDirection;
    }

    @Override
    public void setMoveDirection (MoveDirection moveDirection){
        this.moveDirection = moveDirection;

    }
}
