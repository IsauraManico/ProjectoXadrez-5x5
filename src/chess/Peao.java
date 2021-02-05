package chess;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class Peao extends Peca {
    
    private final int numImagem = 0;
    
    public boolean enPassantOk = false;
  /**
     * Cria um novo peão
     * @param localização localizacao da peça
     * @param color cor da peça
     */


    public Peao(Point location, Color color) {
        this.numMoves = 0;
        this.color = color;
        this.localizacao = location;
    }    
  /**
     * Construtor privado usado para fazer cópias da peça
     * @param localização localizacao da peça
     * @param color cor da peça
     * @param moves número de movimentos que a peça fez
     * @param captureableEnPassant se o peão pode ser capturado atualmente
     * "en passant" ou não
     */
    private Peao(Point location, Color color, int moves, boolean captureableEnPassant) {
        enPassantOk = captureableEnPassant;
        this.numMoves = moves;
        this.color = color;
        this.localizacao = location;
    }

   //retorna a copia do peao
    public Peca clone() {
        return new Peao(new Point(this.localizacao.x, this.localizacao.y),
                this.color, this.numMoves, this.enPassantOk);
    }
    
    /**
     * Retorna o índice da imagem do Peca em um array.
     * Pode ser usado para determinar o valor relativo da peça.
     * As peças têm os seguintes índices:
     * [0]: peão [1]: cavalo [2]: bispo [3]: torre [4]: ​​rainha [5]: rei
     * @return array index
             */
    public int getNumImagem() {
        return numImagem;
    }
    
   /**
     * Retorna a imagem branca para esta peça
     * @return white image
     */


    public BufferedImage getWhiteImage() {
        return imagensBrancas[numImagem];
    }
    
   /**
     * Retorna a imagem preta para esta peça
     * @return black image
     */


    public BufferedImage getBlackImage() {
        return imagensPretas[numImagem];
    }
    
    /**
     * Um método para obter todos os movimentos válidos para uma peça
     * @param embarca no tabuleiro para obter movimentos válidos para a peça.
     * @param checkRei se deve ou não verificar se o movimento coloca o próprio rei
     * em cheque. Necessário para prevenir recursões infinitas.
     * @return Lista contendo pontos de movimento válidos
     */
    
    public List<Move> getValidMoves(QuadroXadrez board, boolean checkRei) {
        List<Move> moves = new ArrayList<Move>();

        // se nenhum quadro for fornecido, retorna uma lista vazia
        if (board == null)
            return moves;

        // verifica movimentos onde o peão avança uma classificação
        advance(board, moves);
        // verifica os movimentos onde o peão captura outra peça
        capture(board, moves);
       // verifica os movimentos en passant
        enPassant(board, moves);

      // verifique se o movimento não coloca o próprio rei em cheque
        if (checkRei)
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
     * Adiciona jogadas em que o peão avança para a lista de jogadas
     * @param move a lista de jogadas
     */
    private void advance(QuadroXadrez board, List<Move> moves) {
        int x = localizacao.x;
        int y = localizacao.y;
        
        Peca pc;
        Point pt;
        int move;
        
        if (color == Color.branca)
            move = -1;
        else
            move = 1;
                
        pt = new Point(x, y + move);
        if (board.localizacaoValida(pt)) 
        {
            pc = board.getPieceAt(pt);            
            if(pc == null) {
                moves.add(new Move(this, pt, pc));     
                
                pt = new Point(x, y + move * 2);
                if (board.localizacaoValida(pt)) {
                    pc = board.getPieceAt(pt);
                    if(pc == null && numMoves == 0)
                        moves.add(new Move(this, pt, pc));
                }
            } 
        }
    }

    /**
     * Adiciona movimentos em que o peão captura à lista de movimentos
     * @param move a lista de jogadas
     */

    private void capture(QuadroXadrez board, List<Move> moves) {
        int x = localizacao.x;
        int y = localizacao.y;
        
        Peca pc;
        Point pt;
        int move;
        
        if (color == Color.branca)
            move = -1;
        else
            move = 1;
            
        pt = new Point(x - 1, y + move);
        if (board.localizacaoValida(pt)) {
            pc = board.getPieceAt(pt);            
            if (pc != null)
                if(this.color != pc.getColor())
                    moves.add(new Move(this, pt, pc));    
        }
        pt = new Point(x + 1, y + move);
        if (board.localizacaoValida(pt)) {
            pc = board.getPieceAt(pt);           
            if (pc != null)
                if(this.color != pc.getColor())
                    moves.add(new Move(this, pt, pc));       
        }
    }
    
  /**
     * Adiciona movimentos em que o peão captura "en passant" à lista de movimentos
     * @param move a lista de jogadas
     */
    private void enPassant(QuadroXadrez board, List<Move> moves) {
        int x = localizacao.x;
        int y = localizacao.y; 
        
        if (this.color == Color.branca && this.localizacao.y == 3) {
            if(canCaptureEnPassant(board, new Point(x - 1, y)))
                moves.add(new Move(this, new Point(x - 1, y - 1),
                        board.getPieceAt(new Point(x - 1, y))));
            if(canCaptureEnPassant(board, new Point(x + 1, y)))
                moves.add(new Move(this, new Point(x + 1, y - 1),
                        board.getPieceAt(new Point(x + 1, y)))); 
        }
        if (this.color == Color.preta && this.localizacao.y == 4) {
            if(canCaptureEnPassant(board, new Point(x - 1, y)))
                moves.add(new Move(this, new Point(x - 1, y + 1),
                        board.getPieceAt(new Point(x - 1, y))));
            if(canCaptureEnPassant(board, new Point(x + 1, y)))
                moves.add(new Move(this, new Point(x + 1, y + 1),
                        board.getPieceAt(new Point(x + 1, y))));            
        }
    }
    
    /**
     * Verifica se o peão pode capturar outro peão en passant
     * @param pt localizacao do outro peão
     * @return true se puder ser capturado
     */
    private boolean canCaptureEnPassant(QuadroXadrez board, Point pt) {
        Peca temp = board.getPieceAt(pt);
        if(temp != null)
            if (temp instanceof Peao && temp.getColor() !=  this.color)
                if (((Peao)temp).enPassantOk)
                    return true;
        return false;
    }
}
