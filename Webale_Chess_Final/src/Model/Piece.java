package Model;
import Model.Chess.PieceType;

//import java.util.*;

public abstract class Piece {

    protected int index;
    protected PieceColor pieceColor;



    public Piece( int index, PieceColor pieceColor)
    {
        this.index = index;
        this.pieceColor = pieceColor;
    }

    public abstract PieceType getPieceType();

    public abstract Integer getIndex();

    public abstract PieceColor getPieceColor();


    public abstract void setMoveDirection(MoveDirection forward);

    public abstract MoveDirection getMoveDirection();
}