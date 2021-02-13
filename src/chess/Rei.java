package chess;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class Rei extends Peca{
       
    private final int imageNumber = 5;
    
    /**
     * Creates a new king
     * @param location localizacao of the piece
     * @param color color of the piece
     */
    public Rei(Point location, Color color) {
        numMoves = 0;
        this.color = color;
        this.localizacao = location;
    }

    /**
     * Private constructor used for making copies of the piece
     * @param location localizacao of the piece
     * @param color color of the piece
     * @param moves the number of moves made by the piece
     */
    private Rei(Point location, Color color, int moves) {
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
     * Returns a copy of the piece
     * @return a copy of the king
     */
    public Peca clone() {
        return new Rei(new Point(this.localizacao.x, this.localizacao.y),
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
        int x = localizacao.x;
        int y = localizacao.y;

        List<Move> moves = new ArrayList<Move>();

        // if no board given, return empty list
        if (board == null)
            return moves;

       //adicione movimentos ao redor do rei se eles forem válidos


        addSeForValido(board, moves, new Point(x - 1, y - 1));
        addSeForValido(board, moves, new Point(x, y - 1));
        addSeForValido(board, moves, new Point(x + 1, y - 1));
        addSeForValido(board, moves, new Point(x + 1, y));
        addSeForValido(board, moves, new Point(x + 1, y + 1));
        addSeForValido(board, moves, new Point(x, y + 1));
        addSeForValido(board, moves, new Point(x - 1, y + 1));
        addSeForValido(board, moves, new Point(x - 1, y));

        // castling
        if (this.numMoves == 0) {
            if (checkKing && this != board.getPecaEmCheck())
            {
                List<Peca> pieces = board.getPecas();
                List<Peca> okRooks = new ArrayList<Peca>();

                // finds rooks available for castling
                for(int i = 0; i < pieces.size(); i++)
                    if (pieces.get(i).getColor() == this.color &&
                        pieces.get(i) instanceof Torre &&
                        pieces.get(i).getNumberOfMoves() == 0)
                        okRooks.add(pieces.get(i));

                // for each eligible rook
                for(Peca p : okRooks) {
                    boolean canCastle = true;
                    // if on right side of board
                    if (p.getLocalizacao().x == 4) {
                        // if there are pieces between the king and the rook
                        for(int ix = this.localizacao.x + 1; ix < 4; ix++) {
                            if (board.getPieceAt(new Point(ix, y)) != null) {
                                // castling is not possible
                                canCastle = false;
                                break;
                            }
                        }
                        if (canCastle)
                            moves.add(new MovimentoCastelo(this, new Point(x + 2, y),
                                    p, new Point(x + 1, y)));
                    // if on left side of board
                    } else if (p.getLocalizacao().x == 0) {
                        // if there are pieces between the king and the rook
                        for(int ix = this.localizacao.x - 1; ix > 0; ix--) {
                            if (board.getPieceAt(new Point(ix, y)) != null) {
                                // castling is not possible
                                canCastle = false;
                                break;
                            }
                        }
                        if (canCastle)
                            moves.add(new MovimentoCastelo(this, new Point(x - 2, y),
                                    p, new Point(x - 1, y)));                    
                    }
                }
            }
        }

        // check move doesn't self in check
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
     * Checks if a given move is valid
     * @param list list to add the move to
     * @param pt move to check validity of
     */
    private void addSeForValido(QuadroXadrez board, List<Move> list, Point pt) {
        // if the localizacao is valid
        if(board.localizacaoValida(pt)) {
            // and the localizacao does not contain same color piece
            Peca pc = board.getPieceAt(pt);
            if(pc == null || pc.getColor() != this.color) {
                // all the move to the list
                list.add(new Move(this, pt, pc));
            }
        }
    }
}
