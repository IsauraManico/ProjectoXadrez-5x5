package chess;

import java.awt.Point;

/**
 * Uma subclasse de movimento para permitir o roque
 * 
 */
public class MovimentoCastelo extends Move
{  
    
    private Peca torre;
    private Point moveRookTo;
    /**
     * Cria um novo objeto de castelo
     * @param rei para mover
     * @param movendoRei ponto para mover o rei para
     * @param torre torre para mover
     * @param movendoTorre ponto para mover a torre para
     */
    public MovimentoCastelo(Peca rei, Point movendoRei, Peca torre, Point movendoTorre) {
        super(rei, movendoRei, null);
        this.moveRookTo = movendoTorre;
        this.torre = torre;
    }
    
    /**
     * Returns o ponto que a torre invocada no movimento castelo se moverá
     * @return onde a torre se moverá
     */
    public Point getRookMoveTo() {
        return moveRookTo;
    }
    
    /**
     * Returns a torre invocada no movimento castelo
     * @return a torre
     */
    public Peca getTorre() {
        return torre;
    }
}
