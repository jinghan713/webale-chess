package Model.Chess;

import Model.MoveDirection;
import Model.Piece;
import Model.PieceColor;


public class Triangle extends Piece {
    private PieceType pieceType;
    private MoveDirection moveDirection;

    public Triangle(int index, PieceColor pieceColor){
        super(index, pieceColor);
        pieceType = PieceType.TRIANGLE;
        moveDirection = MoveDirection.FORWARD;
    }


    @Override
    public PieceType getPieceType() {
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
