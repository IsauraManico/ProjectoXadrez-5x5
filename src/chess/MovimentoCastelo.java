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
     * @param rei king para mover
     * @param movendoRei point para mover o rei para
     * @param torre torre para mover
     * @param movendoTorre ponto para mover a torre para
     */
    public MovimentoCastelo(Peca rei, Point movendoRei, Peca torre, Point movendoTorre) {
        super(rei, movendoRei, null);
        this.moveRookTo = movendoTorre;
        this.torre = torre;
    }
    
    /**
     * Returns the point the torre involved in the castling will move to
     * @return where the torre will move
     */
    public Point getRookMoveTo() {
        return moveRookTo;
    }
    
    /**
     * Returns the torre involved in the castling
     * @return the torre
     */
    public Peca getTorre() {
        return torre;
    }
}
