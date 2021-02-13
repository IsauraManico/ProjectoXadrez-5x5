package chess;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class Torre extends Peca{
       
    private final int imageNumber = 3;
    
   
    public Torre(Point location, Color color) {
        this.numMoves = 0;
        this.color = color;
        this.localizacao = location;
    }

   
    private Torre(Point location, Color color, int moves) {
        this.numMoves = moves;
        this.color = color;
        this.localizacao = location;
    }
   
    public int getNumImagem() {
        return imageNumber;
    }

   
    public BufferedImage getWhiteImage() {
        return imagensBrancas[imageNumber];
    }
    
  
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
