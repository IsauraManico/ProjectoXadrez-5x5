package chess;

import java.io.Serializable;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;


public class QuadroXadrez implements Serializable, Cloneable 
{
    private QuadroXadrez estadoAnterior = null;
    private Peca.Color virar;
    private List<Peca> pecas = new ArrayList<Peca>();
    
    private Peca emCheck = null;
    private Peca ultimoMovido = null;
    private Agente_IA ai = null;
    
    /**
     * Sets an Agente_IA for the board
     * @param jogador:agente
     */
    public void setAi(Agente_IA agente) { //IA
        this.ai = agente;
    }
    
    /**
     * Returns the Agente_IA object for the board. 
     * @return the Agente_IA object of the board. Null if 2-player game.
     */
    public Agente_IA getAi() {
        return ai;
    }
    
    /**
     * If a king is in check, returns it. Null otherwise.
     * @return king in check
     */
    public Peca getPecaEmCheck() {
        return emCheck;
    }
    
    /**
     * Retorna a última peça movida;
     * @return última peça movida
     */


    public Peca getUltimoMoveDaPeca()
    {
        return ultimoMovido;
    }
    
   /**
     * Cria um novo objeto de tabuleiro
     * @param iniciarPecas verdadeiro para inicializar todas as pecas,
     * falso para deixar o tabuleiro vazio
     */
    public QuadroXadrez(boolean iniciarPecas)
    {
        virar = Peca.Color.branca;
        
        if (iniciarPecas)
        {        
           
            //pecas pretas.........................................................
            
            pecas.add(new Rainha(new Point(3, 0), Peca.Color.preta));//3
            pecas.add(new Rei(new Point(4, 0), Peca.Color.preta));//4
            pecas.add(new Bispo(new Point(2, 0), Peca.Color.preta));//2
            pecas.add(new Cavalo(new Point(1, 0), Peca.Color.preta));//1
            pecas.add(new Torre(new Point(0, 0), Peca.Color.preta));//0
            
            pecas.add(new Peao(new Point(0,1),Peca.Color.preta));
            pecas.add(new Peao(new Point(1, 1), Peca.Color.preta));
            pecas.add(new Peao(new Point(2, 1), Peca.Color.preta));
            pecas.add(new Peao(new Point(3, 1), Peca.Color.preta));
            pecas.add(new Peao(new Point(4, 1), Peca.Color.preta));
          

            //  pecas brancas.......................................................
         
            pecas.add(new Torre(new Point(0, 4), Peca.Color.branca));
            pecas.add(new Cavalo(new Point(1, 4), Peca.Color.branca));
            pecas.add(new Bispo(new Point(2, 4), Peca.Color.branca));
            pecas.add(new Rainha(new Point(3, 4), Peca.Color.branca));
            pecas.add(new Rei(new Point(4, 4), Peca.Color.branca));
            
              
            pecas.add(new Peao(new Point(0, 3), Peca.Color.branca));
            pecas.add(new Peao(new Point(1, 3), Peca.Color.branca));
            pecas.add(new Peao(new Point(2, 3), Peca.Color.branca));
            pecas.add(new Peao(new Point(3, 3), Peca.Color.branca));
            pecas.add(new Peao(new Point(4, 3), Peca.Color.branca));
           
            
        }
    }
    
    /**
     * Construtor privado usado para criar uma cópia profunda do quadro
     * @param vire a cor das pecas para mover a seguir
     * @param estadoAnterior estado anterior do tabuleiro
     * @param coloca todas as pecas no tabuleiro
     * @param lastMoved piece to move last
     * @param inCheck king in check
     * @param ai ai presente no quadro
     */


    private QuadroXadrez(Peca.Color turn, QuadroXadrez estadoAnterior, List<Peca> pecas,
            Peca lastMoved, Peca inCheck, Agente_IA ai) {
        this.virar = turn;
        if (inCheck != null)
            this.emCheck = inCheck.clone();
        if (lastMoved != null)
            this.ultimoMovido = lastMoved.clone();
        this.ai = ai;
        this.estadoAnterior = estadoAnterior;
        for(Peca p : pecas) {
            this.pecas.add(p.clone());
        }
    }
    
   /**
     * Retorna a lista de todas as pecas no tabuleiro
     * @return List <Piece> contendo todas as pecas no tabuleiro
     */
    public List<Peca> getPecas() {
        return pecas;
    }
    
   /**
     * Retorna a peça na localização especificada
     * @param p a localização especificada
     * @retorne a peça na localizacao. nulo se nenhuma peça for encontrada
     */
    public Peca getPieceAt(Point p) {
        for(Peca pc : pecas) {
            if(pc.getLocalizacao().x == p.x &&
               pc.getLocalizacao().y == p.y)
                return pc;
        }
        return null;
    }
    
    /**
     * Remove a peça do tabuleiro
     * @param p a peça a ser removida
     */


    public void removePeca(Peca p) {
        if (pecas.contains(p)) {
            pecas.remove(p);
            return;
        }
    }
    
   /**
     * Adiciona uma peça ao tabuleiro
     * @param p Peca para adicionar
     */
    public void addPeca(Peca p) {
        pecas.add(p);
    }
    /**
     * Remove a peça no ponto determinado
     * @param p Ponto para remover a peça de
     */


    public void removerPecaAt(Point p) {
        Peca temp = null;
        for(Peca pc : pecas) {
            if (pc.getLocalizacao().equals(p)) {
                temp = pc;
                break;
            }
        }
        if (temp != null)
            pecas.remove(temp);
    }
    
    /**
     * Returns the color that has the current virar
     * @return the color to move next
     */
    public Peca.Color getVirar() {
        return virar;
    }
    
  /**
     * Executa o movimento dado. Não verifica a validade. Use apenas movimentos de
     * os métodos getValidMoves () das peças.
     * @param m mova para executar
     * @param playerMove seja ou não feito diretamente por um jogador humano.
     * Determina se uma caixa de diálogo será exibida na promoção do peão.
     */
    public void mova(Move m, boolean playerMove) {
        this.estadoAnterior = this.clone();
        
        // implementando a regra en passant
        for(Peca pc : pecas)
            if (pc.getColor() == virar && pc instanceof Peao)
                ((Peao)pc).enPassantOk = false;
        
       // se o movimento for roque
        if (m instanceof MovimentoCastelo) {
            MovimentoCastelo c = (MovimentoCastelo)m;
            c.getPiece().moveTo(c.getMoveTo());
            c.getTorre().moveTo(c.getRookMoveTo());
        } else {
            if(m.getCaptured() != null);
                this.removePeca(m.getCaptured());
            
            // Implementando a regra en passant
            if (m.getPiece() instanceof Peao)
                if (Math.abs(m.getPiece().getLocalizacao().y - m.getMoveTo().y) == 2) //2
                    ((Peao)m.getPiece()).enPassantOk = true;                
            
            m.getPiece().moveTo(m.getMoveTo());    
            
            // promove o peão se alcançar a classificação final
            checkPromocaoDoPeao(m.getPiece(), playerMove);
        }
        
        this.ultimoMovido = m.getPiece();
        this.emCheck = reiEstaEmCheck();
        
      // muda a cor das pecas movendo-se a seguir
        virar = Peca.Color.values()[(virar.ordinal() + 1) % 2]; //Alternando as jogadas
    }
    /**
     * Verifica se a peça fornecida é um peão que precisa ser promovido.
     * Se for uma peça AI, automaticamente o promove para
     * Peça de peão @param para verificar
     * @param showDialog Se deve ou não perguntar ao usuário o que deve ser 
     * feito para promover o peão.
     * Se falso, é automaticamente promovido a Rainha.
     */
    private void checkPromocaoDoPeao(Peca peao, boolean showDialog) 
    {
        if(peao instanceof Peao && (peao.getLocalizacao().y == 0 ||
            peao.getLocalizacao().y == 4)) //7 seria 5 em tab 5*5
        {
           // System.out.println("Localizacao Peao:"+peao.getLocalizacao());
            Peca promovido;
            
           // se for ai, promove automaticamente a rainha
            if (!showDialog || (ai != null && ai.getColor() == peao.getColor())) {
                promovido = new Rainha(peao.getLocalizacao(), peao.getColor());
            } 
            else 
            { 
              //else da ao jogador uma opcao de escolha
                
                Object tipo = javax.swing.JOptionPane.showInputDialog(
                        null, "", 
                        "Escolha a promocao:",
                        javax.swing.JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[] { "Rainha", "Torre", "Bispo", "Cavalo" },
                        "Rainha");
                
                // será nulo se JOptionPane for cancelado ou fechado
                // padrão para rainha nesse caso
                if (tipo == null)
                    tipo = "Rainha";
                
                // interpret the JOptionPane result
                if (tipo.toString().equals("Rainha"))
                    promovido = new Rainha(peao.getLocalizacao(), peao.getColor());
                else if (tipo.toString().equals("Torre"))
                    promovido = new Torre(peao.getLocalizacao(), peao.getColor());
                else if (tipo.toString().equals("Bispo"))
                    promovido = new Bispo(peao.getLocalizacao(), peao.getColor());
                else
                    promovido = new Cavalo(peao.getLocalizacao(), peao.getColor());
            }

            // remove pawn and add promoted piece to board
            pecas.remove(peao);
            pecas.add(promovido);
        }
    }
    
   /**
     * Retorna um novo objeto do tabuleiro, com o movimento executado
     * @param m mova para executar
     * @return new board
     */
    public QuadroXadrez tentaMover(Move m) {
        //cria uma copia do tabuleiro
        QuadroXadrez ajudante = this.clone();
        //m e objecto de instancia de MovimentoCastelo
        if (m instanceof MovimentoCastelo) {
           // cria uma cópia do movimento para o tabuleiro copiado
            MovimentoCastelo c = (MovimentoCastelo)m;
            Peca rei = ajudante.getPieceAt(c.getPiece().getLocalizacao());
            Peca torre = ajudante.getPieceAt(c.getTorre().getLocalizacao());
            
           // executa o movimento no tabuleiro copiado
            ajudante.mova(new MovimentoCastelo(rei, c.getMoveTo(),
                    torre, c.getRookMoveTo()), false);
        } else {       
           // cria uma cópia do movimento para o tabuleiro copiado
            Peca capture = null;
            if(m.getCaptured() != null)
                capture = ajudante.getPieceAt(m.getCaptured().getLocalizacao());

            Peca movendo = ajudante.getPieceAt(m.getPiece().getLocalizacao());

            // performs the move on the copied board
            ajudante.mova(new Move(movendo,
                    m.getMoveTo(), capture), false);
        }
        
        // returns the copied board with the move executed
        return ajudante;
    }  
    
    /**
     * Usado para descobrir se um rei está em xeque
     * @return um Peca (Rei) se um estiver em cheque, senão null
     */


    private Peca reiEstaEmCheck() {
       // passa por todas as pecas no tabuleiro
        for(Peca pc : pecas)
           // passa por todos os movimentos que podem ser feitos pela peça
            for(Move mv : pc.getValidMoves(this, false))
                // se um movimento resultasse na captura de um rei
                if (mv.getCaptured() instanceof Rei) {
                    // aquele rei está em xeque
                    this.emCheck = mv.getCaptured();
                    //return isto
                    return mv.getCaptured();
                }
        return null;
    }
    
   /**
     * Verifica se um movimento coloca um rei em xeque
     * @param m mova para verificar
     * @param corDoRei cor do rei para verificar
     * @return true se o movimento colocar o rei em xeque
     */
    public boolean moveReiPodeEstarEmCheck(Move m, Peca.Color corDoRei) {
       // cria uma cópia do tabuleiro

        QuadroXadrez ajudante = tentaMover(m);
        
        // passe por todas as pecas no tabuleiro

        for(Peca pc : ajudante.getPecas())
            // se a cor for diferente da cor
            // do rei que estamos procurando

            if (pc.color != corDoRei)
               // passar por todos os movimentos disponíveis

                for(Move mv : pc.getValidMoves(ajudante, false))
                  // se um movimento resultasse na captura de um rei

                    if (mv.getCaptured() instanceof Rei) 
                        return true;
        return false;
    }
    
    /**
     * Verifica se alguma das cores não pode fazer mais movimentos
     * @return true pode significar um xeque-mate ou um impasse.
     * Use kingInCheck () para determinar qual.
     */
    public boolean gameOver() {
        // cria um array para todos os movimentos que podem ser feitos por
        // peças pretas, peças brancas


        List<Move> whiteMoves = new ArrayList<Move>();
        List<Move> blackMoves = new ArrayList<Move>();
        
        // todos se movem para as matrizes de todas as peças
        for(Peca p : pecas)
        {
            if(p.getColor() == Peca.Color.branca)
            {
                whiteMoves.addAll(p.getValidMoves(this, true));
                
                // System.out.println("Movimentos Pecas Brancas:"+whiteMoves);
                
            }
               
            else
            {
                blackMoves.addAll(p.getValidMoves(this, true));
               // System.out.println("Movimentos Pecas Pretas:"+blackMoves);
            }
        }
        
       // se nenhum dos lados puder fazer movimentos válidos, o jogo acabou


        return (whiteMoves.size() == 0 || blackMoves.size() == 0);
    }
    
    /**
     *
     * @retorne uma cópia deste board
     */
    @Override
    public QuadroXadrez clone() {
        return new QuadroXadrez(virar, estadoAnterior, pecas, ultimoMovido, emCheck, ai);
    }
    
   /**
     * Obtém o último estado do tabuleiro.
     * @return estado do tabuleiro antes da última jogada
     */
    public QuadroXadrez getEstadoAnterior() {
        if(estadoAnterior != null)
            return estadoAnterior;
        return this;
    }
    
   /**
     * Verifica se uma posição está dentro dos limites do tabuleiro.
     * Não verifica se a posição está ocupada.
     * @param ponto ponto para verificar a validade de
     * @return true se válido, false se não
     */


    public boolean localizacaoValida(Point ponto) {
        return (ponto.x >= 0 && ponto.x <=4) && (ponto.y >= 0 && ponto.y <= 4); 
    }
}
