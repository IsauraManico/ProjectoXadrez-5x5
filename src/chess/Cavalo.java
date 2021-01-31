package chess;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class Cavalo extends Peca
{
       
    private final int imageNumber = 1;
    
    
    /**
     * Autor: Kiala, Sara,Lionel, Isaura
     * cria um novo cavalo
     * @param ;localizacao da peca
     * @param cor da peca
     */
    
    
    public Cavalo()
    {
        
    }
    public Cavalo(Point location, Color color) {
        numMoves = 0;
        this.color = color;
        this.localizacao = location;
    }

    /**
     * Construtor privado usado para fazer cópias da peça
     * @param localização localizacao da peça
     * @param color cor da peça
     * @param move o número de movimentos feitos pela peça

     */
    private Cavalo(Point location, Color color, int moves) {
        this.numMoves = moves;
        this.color = color;
        this.localizacao = location;
    }
    
    /**
     * Retorna o índice da imagem do Peca em um array.
     * Pode ser usado para determinar o valor relativo da peça.
     * As peças têm os seguintes índices:
     * [0]: peão [1]: cavalo [2]: bispo [3]: torre [4]: ​​rainha [5]: rei
     * @return array index
     */
    public int getNumImagem() {
        return imageNumber;
    }

   /**
     * Retorna a imagem branca para esta peça
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
    
    //retorna a  copia do cavalo
    public Peca clone() {
        return new Cavalo(new Point(this.localizacao.x, this.localizacao.y),
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
        int x = localizacao.x;
        int y = localizacao.y;

        List<Move> moves = new ArrayList<Move>();

        // se nenhum quadro for fornecido, retorna uma lista vazia
        if (board == null)
            return moves;
        
       // verificar formas L  
        addIfValid(board, moves, new Point(x + 1, y + 2));
        addIfValid(board, moves, new Point(x - 1, y + 2));
        addIfValid(board, moves, new Point(x + 1, y - 2));
        addIfValid(board, moves, new Point(x - 1, y - 2));
       // addIfValid(board, moves, new Point(x + 2, y - 1));
       // addIfValid(board, moves, new Point(x + 2, y + 1));
       // addIfValid(board, moves, new Point(x - 2, y - 1));
       // addIfValid(board, moves, new Point(x - 2, y + 1));    

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
     * Verifica se um determinado movimento é válido
     * @param list list para adicionar o movimento
     * @param pt mova para verificar a validade de


     */
    private void addIfValid(QuadroXadrez board, List<Move> list, Point pt) {
        // se a localização é válida
        if(board.localizacaoValida(pt)) {
          // e a localizacao não contém peça da mesma cor
            Peca pc = board.getPieceAt(pt);
            if(pc == null || pc.getColor() != this.color) {
                // todo o movimento para a lista
                list.add(new Move(this, pt, pc));
            }
        }
    }
}
