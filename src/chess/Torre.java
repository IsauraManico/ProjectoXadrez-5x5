package chess;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class Torre extends Peca{
       
    private final int imageNumber = 3;
    
    /**
     * Creates a new rook
     * @param location localizacao of the piece
     * @param color color of the piece
     */
    public Torre(Point location, Color color) {
        this.numMoves = 0;
        this.color = color;
        this.localizacao = location;
    }

    /**
     * Private constructor used for making copies of the piece
     * @param location localizacao of the piece
     * @param color color of the piece
     * @param moves the number of moves made by the piece
     */
    private Torre(Point location, Color color, int moves) {
        this.numMoves = moves;
        this.color = color;
        this.localizacao = location;
    }
    
    /**
     * Returns the index of the Peca's image in an array.
     *  Can be used for determining the relative value of the piece.
     *  Pieces have the following indices:
     *  [0]:pawn [1]:knight [2]:bishop [3]:rook [4]:queen [5]:king
     * @return array index
     */
    public int getNumImagem() {
        return imageNumber;
    }

    /**
     * Returns the white image for this piece
     * @return white image
     */
    public BufferedImage getWhiteImage() {
        return imagensBrancas[imageNumber];
    }
    
    /**
     * Returns the black image for this piece
     * @return black image
     */
    public BufferedImage getBlackImage() {
        return imagensPretas[imageNumber];
    }
    
    /**
     * Returns a copy of the rook
     * @return a copy of the rook
     */
    public Peca clone() {
        return new Torre(new Point(this.localizacao.x, this.localizacao.y),
                this.color, this.numMoves);
    }
    
    /**
     * A method to get all the valid moves for a piece
     * @param board the board to get valid moves on for the piece.
     * @param checkKing whether or not to check if the move puts own king
     *  in check. Necessary to prevent infinite recursion.
     * @return List containing valid move points
     */
    public List<Move> getValidMoves(QuadroXadrez board, boolean checkKing) {       
        List<Move> moves = new ArrayList<Move>();

        // if no board given, return empty list
        if (board == null)
            return moves;
        
        addMovesInLine(board, moves, 1, 0);
        addMovesInLine(board, moves, 0, 1);
        addMovesInLine(board, moves, -1, 0);
        addMovesInLine(board, moves, 0, -1);

        // check that move doesn't put own king in check
        if (checkKing)
            for(int i = 0; i < moves.size(); i++)
                if (board.moveReiPodeEstarEmCheck(moves.get(i), this.color)) {
                    // if move would put king it check, it is invalid and
                    // is removed from the list
                    moves.remove(moves.get(i));
                    // iterator is decremented due to the size of the list
                    // decreasing.
                    i--;
                }
        return moves;
    }
    
    /**
     * Adds valid moves in a straight line to the list
     * @param moves list to add to
     * @param xi x direction (-1/0/1)
     * @param yi y direction (-1/0/1)
     */
    private void addMovesInLine(QuadroXadrez board, List<Move> moves, int xi, int yi) {
        int x = localizacao.x;
        int y = localizacao.y;
        
        Point pt = new Point(x + xi, y + yi);
        Peca pc;
        
        while(board.localizacaoValida(pt)) {
            pc = board.getPieceAt(pt);
            if(pc == null) {
                moves.add(new Move(this, pt, pc));
            } else if(pc.getColor() != this.color) {
                moves.add(new Move(this, pt, pc));
                break;
            } else {
                break;
            }
            pt = new Point(pt.x + xi, pt.y + yi);
        }
    }
}
