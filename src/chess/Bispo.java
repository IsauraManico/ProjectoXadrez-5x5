package chess;

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
/**
 * Autor: Kiala, Sara,Lionel, Isaura
 * A subclass of Peca
 * 
 * 
 */
public class Bispo extends Peca
{
       
    private final int imageNumber = 2;
    
   
    
    
    
    /**
     * Creates a new bishop
     * @param location localizacao of the piece
     * @param color color of the piece
     */
    public Bispo(Point location, Color color) {
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
    private Bispo(Point location, Color color, int moves) {
        this.numMoves = moves;
        this.color = color;
        this.localizacao = location;
    }
    
    /**
    * @return index of the piece's image in an array
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
     * Returns a copy of the bishop
     * @return a copy of the bishop
     */
    public Peca clone() {
        return new Bispo(new Point(this.localizacao.x, this.localizacao.y),
                this.color, this.numMoves);
    }
    
/**
     * Um método para obter todos os movimentos válidos para uma peça
     * @param embarca no tabuleiro para obter movimentos válidos para a peça.
     * @param checkKing se deve ou não verificar se o movimento coloca o próprio rei
     * em cheque. Necessário para prevenir recursões infinitas.
     * @return Lista contendo pontos de movimento válidos
     */
    public List<Move> getValidMoves(QuadroXadrez board, boolean checkKing) {       
        List<Move> moves = new ArrayList<Move>();

    // se nenhum quadro for fornecido, retorna uma lista vazia
        if (board == null)
            return moves;
        
        //adicione movimentos em linhas diagonais à lista
        addMovesInLine(board, moves, 1, 1);
        addMovesInLine(board, moves, -1, 1);
        //addMovesInLine(board, moves, 1, -1);
        //addMovesInLine(board, moves, -1, -1);

       // verifique se o movimento não coloca o próprio rei em cheque
        if (checkKing)
            for(int i = 0; i < moves.size(); i++)
                if (board.moveReiPodeEstarEmCheck(moves.get(i), this.color)) {
                   // se o movimento colocaria o rei em cheque, é inválido e
                    // é removido da lista
                    moves.remove(moves.get(i));
                   // iterador é diminuído devido ao tamanho da lista
                    // diminuindo.
                    i--;
                }
        return moves;
    }
    
    /**
     * Adiciona movimentos válidos em linha reta à lista
     * @param move a lista para adicionar
     * @param xi x direção da linha (-1/0/1)
     * @param yi y direção da linha (-1/0/1)
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
