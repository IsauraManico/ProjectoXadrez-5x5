package chess;

import java.awt.Point;
import java.util.List;
import java.io.Serializable;
import java.awt.image.BufferedImage;

public abstract class Peca implements Serializable, Cloneable
{
    public static enum Color {branca, preta};

    // [0]:pawn [1]:knight [2]:bishop [3]:rook [4]:queen [5]:king
    protected static BufferedImage[] imagensBrancas;
    protected static BufferedImage[] imagensPretas;
    
    protected int numMoves;
    protected Color color;
    protected Point localizacao;
    
    /**
     * Returns the number of moves made by the piece
     * @return number of moves
     */
    public int getNumberOfMoves() {
        return numMoves;
    }
    
    /**
     * Gets the color of the piece
     * @return a java.awt.Color object representing the piece's color
     */
    public Color getColor() {
        return this.color;
    }
    
    /**
     * Moves the piece to a new localizacao. Use getValidMoves() to check if a 
     * point is valid.
     * @param p the Point to move to
     */
    public void moveTo(Point p) {
        this.localizacao = p;
        numMoves++;
    }
    
    /**
     * Returns the localizacao (Point) of the piece
     * @return the localizacao of the piece
     */
    public Point getLocalizacao() {
        return this.localizacao;
    }
    /**
     * Retorna o índice da imagem do Peca em um array.
     * Pode ser usado para determinar o valor relativo da peça.
     * As peças têm os seguintes índices:
     * [0]: peão [1]: cavalo [2]: bispo [3]: torre [4]: ​​rainha [5]: rei
     * @return array index
     */


    public abstract int getNumImagem();
    
    /**
     * Gets the white image of the piece
     * @return white image
     */
    public abstract BufferedImage getWhiteImage() ; 
    
    /**
     * Gets the black image of the piece
     * @return black image
     */
    public abstract BufferedImage getBlackImage() ;
    
   /**
     * Obtém uma lista dos movimentos válidos que a peça pode fazer
     * @return movimentos válidos
     */


    public abstract List<Move> getValidMoves(QuadroXadrez board, boolean checkKing);
    
    //Retorna a copia da peca
    @Override
    public abstract Peca clone();
    
    /**
     * Define a matriz de BufferedImages a ser usada para desenhar peças pretas.
     * Este método deve ser chamado antes de tentar desenhar peças.
     * @param images Matriz de imagens armazenadas em buffer. As imagens devem estar no seguinte
     * ordem: [0]: peão [1]: cavalo [2]: bispo [3]: torre [4]: ​​rainha [5]: rei
     */


    public static void setImagensBrancas(BufferedImage[] images) {
        imagensBrancas = images;
    }
  
  /**
     * Define a matriz de BufferedImages a ser usada para desenhar peças brancas.
     * Este método deve ser chamado antes de tentar desenhar peças.
     * @param images Matriz de imagens armazenadas em buffer. As imagens devem estar no seguinte
     * ordem: [0]: peão [1]: cavalo [2]: bispo [3]: torre [4]: ​​rainha [5]: rei
     */
    public static void setImagensPretas(BufferedImage[] images) {
        imagensPretas = images;
    }

}
