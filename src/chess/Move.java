package chess;

import java.awt.Point;

//Responsavel por controlar todo o movimento do tabuleiro

public class Move 
{
    private Peca toMove;
    private Point moveTo;
    private Peca toCapture;
    
     /**
     * Cria um novo objeto de movimento
     * @param para; mover a peça para mover
     * @param moveTo o local para onde mover
     * @param para capturar a peça capturada, nulo se nenhuma
     */
    public Move(Peca toMove, Point moveTo, Peca toCapture) 
    {
        this.toMove = toMove;
        this.moveTo = moveTo;
        this.toCapture = toCapture;
    }
    
    //Reorna o destino do movimento
    public Point getMoveTo() {
        return moveTo;
    }
    
    //Retorna a peca que esta sendo movida
    public Peca getPeca() {
        return toMove;
    }
    
  /**
     * Retorna a peça sendo capturada
     * @return null se o movimento não capturar uma peça
     */


    public Peca getCapturada()
    {
        return toCapture;
    }
}
