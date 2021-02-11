package chess;

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import javax.swing.DefaultListModel;
/**
 *  
 * Autor: Kiala, Sara,Lionel, Isaura
 * Contem a logica de Inteligencia artificial do jogo xadrez
 *
 */
public class Agente_IA implements Serializable
{
    
    
    private Peca.Color iaCor;
    private int profundidade;
    public static  Move melhorMovimento = null;
    public static DefaultListModel<String> l1 ;

    static String jogada="";

    public Agente_IA()
    {
        
    }
    /**
     * Cria um novo objeto do Agente
     * @param color
     * @param depth
     */
    public Agente_IA(Peca.Color color, int depth) {
        this.iaCor = color;
        this.profundidade = depth;
        l1 = new DefaultListModel<>(); 
        l1.addElement("Start Game BLACK");
    }
    
    /**
     * Retorna a cor da peca de controle do agente
     * retorno da cor da pecas do agente
     */
    public Peca.Color getColor() 
    {
        return iaCor;
    }
    
   /**
     * Retorna um movimento para o agente fazer com base em um algoritmo min / max
     * com poda alfa-beta. Com base na explicação genérica de pseudocódigo de
     * algoritmo de http://ai-depot.com/articles/minimax-explained/
     * @param jogo estado atual do tabuleiro
     * @return melhor jogada
     */


    public Move getMove(QuadroXadrez jogo)
    {     
        //System.out.println("COR IA: "+iaCor);
        // se um movimento do quadro esta vazio, return null
        if (jogo == null)
            return null;
        // if não for a vez do AI, retorna null
        if (jogo.getVirar() != iaCor)
            return null;
        // inicializa melhor valor and melhor movimentos das variaveis
        int melhorValor = Integer.MIN_VALUE;
        
       
        
        // obtenha o melhor movimento para o IA (máximo) a partir dos movimentos disponíveis
        for (Move m : getMoves(jogo))
        {
            // obtenha o valor do movimento (min)
            int moveValor = min(jogo.tentaMover(m), profundidade - 1, melhorValor, Integer.MAX_VALUE);
            
            // se o valor for> do que bestValue, o movimento atual é melhor
            if (moveValor > melhorValor || melhorValor == Integer.MIN_VALUE) {
                melhorValor = moveValor;
                melhorMovimento = m;
            }
        }
         
        l1.addElement(mostrarMove(melhorMovimento.getPiece().getLocalizacao())+
                    mostrarMove(melhorMovimento.getMoveTo()));
        jogada="Black payed "+mostrarMove(melhorMovimento.getPiece().getLocalizacao())+
                    mostrarMove(melhorMovimento.getMoveTo());
        return melhorMovimento;
    }
    
   /**
     * Retorna o valor do melhor movimento do IA para o tabuleiro fornecido
     * estado do jogo @param após o último movimento do jogador
     * @param depth  atual da análise
     * @param alpha melhor max move
     * @param beta melhort min move
     * @return valor do movimento
     */
    private int max(QuadroXadrez game, int depth, int alpha, int beta) {
        // termina a pesquisa se o jogo acabou ou o limite de profundidade foi atingido
        if (depth == 0)
            return valorQuadroXadrez(game);

        List<Move> possiveisMovimentos = getMoves(game);
        // se nenhum movimento puder ser feito, o jogo terminou

          if (possiveisMovimentos.size() == 0)
                    return valorQuadroXadrez(game);

       // obtém o melhor movimento para o ai (max) a partir dos movimentos disponíveis
        for(Move m : possiveisMovimentos) {
          // obtém o valor do movimento
            int moveValor = min(game.tentaMover(m), depth - 1, alpha, beta);
            
           // veja se é melhor do que o melhor movimento anterior
            if (moveValor > alpha) {
                alpha = moveValor;
            }            
            // se o valor alfa (valor do melhor movimento encontrado para o IA por
            // este método) é maior que o valor beta (o melhor movimento para
            // o oponente até agora encontrado pelo método min que chamou isso
            // método) então sabemos que o método min não escolherá este
            // caminho e podemos parar a busca


            if (alpha >= beta)
                return alpha;
        }

        return alpha;
    }
    
    /**
     * Retorna o valor da melhor jogada do jogador para o tabuleiro fornecido
     * estado do jogo @param após o último movimento IA
     * @param depth  atual da análise
     * @param alpha best max move
     * @param beta best min move
     * @return valor do movimento


     */
    private int min(QuadroXadrez game, int depth, int alpha, int beta) {
        // fim da pesquisa se o jogo acabou ou o limite de profundidade atingido
        if (depth == 0)
            return valorQuadroXadrez(game);

        List<Move> possiblidadesMove = getMoves(game);

        // se nenhum movimento puder ser feito, o jogo terminou


        if (possiblidadesMove.size() == 0)
            return valorQuadroXadrez(game);

       // obtém a melhor jogada para o jogador (min) a partir das jogadas disponíveis


        for(Move m : possiblidadesMove) {
            int moveValue = max(game.tentaMover(m), depth - 1, alpha, beta);
            if (moveValue < beta) {
                beta = moveValue;
            }             
          // se o valor alfa (melhor movimento encontrado para o IA até agora pelo
            // método máximo que chamou este método) é maior que o beta
            // valor (melhor movimento encontrado para o oponente por este método) então
            // sabemos que o método max não vai escolher este caminho e nós
            // pode parar a pesquisa.

        if (alpha >= beta)
                        return beta;
                }       
                return beta;
            }

    /**
     * Retorna todos os movimentos possíveis para o tabuleiro.
     * @param jogo QuadroXadrez para obter movimentos para
     * @return lista de movimentos possíveis.
     */
    private List<Move> getMoves(QuadroXadrez game) {
        // inicializa um arraylist
        List<Move> moves = new ArrayList<Move>();
        
       //para cada peca
        for (Peca p : game.getPecas())
            // da cor que se move a seguir
            
            if (p.getColor() == game.getVirar())
                // adiciona todos os movimentos válidos à lista
                moves.addAll(p.getValidMoves(game, true));
        return moves;
    }
    
    /**
     * Calcula o valor da peca do agente.
     * @param gameBoard QuadroXadrez to evaluate
     * @return value of the QuadroXadrez
     */
    private int valorQuadroXadrez(QuadroXadrez gameBoard) {
        int valor = 0;
        int iaPecas = 0;
        int iaMoves = 0;
        int pecasDosJogadores = 0;
        int MovimentoJogador = 0;
        int iaCapturas = 0;
        int jogadoresCapturados = 0;
        
        // dê ao estado do tabuleiro um valor baseado no número de peças no
        // tabuleiro e o número de movimentos disponíveis


        for(Peca pc : gameBoard.getPecas())
            if(pc.getColor() == iaCor) {
                // contabiliza o número de peças ao tabuleiro
                iaPecas += valorDaPeca(pc);

                if (iaCor == gameBoard.getVirar())
                {
                    List<Move> moveValidos = pc.getValidMoves(gameBoard, true);
                    for(Move m : moveValidos) {
                       // contabiliza quantos movimentos podem ser feitos
                        iaMoves++;
                        if (m.getCaptured() != null) {
                            // conta para possíveis capturas
                            iaCapturas += valorDaPeca(m.getCaptured());
                        }
                    }
                }
            } else {
                pecasDosJogadores += valorDaPeca(pc);

                if (iaCor != gameBoard.getVirar())
                {
                    List<Move> validMoves = pc.getValidMoves(gameBoard, true);
                    for(Move m : validMoves) {
                        // contabiliza quantos movimentos podem ser feitos

                        MovimentoJogador++;
                        if (m.getCaptured() != null) {
                            // conta para possíveis capturas
                             jogadoresCapturados+= valorDaPeca(m.getCaptured());
                        }
                    }
                }
            }

        valor = (iaPecas - pecasDosJogadores) + (iaMoves - MovimentoJogador)
                + (iaCapturas -jogadoresCapturados);

        // se um lado não puder fazer movimentos válidos, o jogo acabou
        if (gameBoard.getVirar() == iaCor && iaMoves == 0)
            // se o AI não pode fazer nenhum movimento, ele perdeu. isto é mau.
            valor = Integer.MIN_VALUE; //chave do projeto
        else if (gameBoard.getVirar() != iaCor && MovimentoJogador == 0)
           // se o jogador não puder fazer mais movimentos, nós ganhamos. isso é bom.
            valor = Integer.MAX_VALUE;  //chave do projeto
        return valor;
    }
       
  /**
     * Um método para avaliar o valor de uma peça
     * peça @param pc para avaliar
     * @valor de retorno da peça
     */
    public static  int valorDaPeca(Peca pc) {
        return (int)Math.pow(pc.getNumImagem() + 1, 3) * 100;
    }
    
    

    public String mostrarMove(Point x)
    {
                        if(x.x == 0 && x.y == 0)
                        {
                            return "a5";
                            
                        }
                        else if(x.x  == 1 && x.y == 0)
                       {
                           return ("b5");
                          
                           
                       }
                        else if(x.x == 2 && x.y == 0)
                       {
                           return ("c5");
                       }
                        else if(x.x == 3 && x.y == 0)
                       {
                           return ("d5");
                       }
                        else if(x.x == 4 && x.y == 0)
                       {
                           return ("e5");
                       }
                       
                       //////////////////////////////////////////////////
                        else if(x.x == 0 && x.y == 1)
                        {
                            return ("a4");
                        }
                        else if(x.x  == 1 && x.y == 1)
                       {
                           return ("b4");
                       }
                        else if(x.x == 2 && x.y == 1)
                       {
                           return "c4";
                       }
                        else if(x.x == 3 && x.y == 1)
                       {
                           return ("d4");
                       }
                        else if(x.x== 4 && x.y == 1)   
                       {
                           return ("e4");
                       }
                       /////////////////////////////////////////
                        else if(x.x== 0 && x.y== 2)
                        {
                            return ("a3");
                        }
                        else if(x.x == 1 && x.y== 2)
                       {
                           return ("b3");
                       }
                        else if(x.x== 2 && x.y== 2)
                       {
                           return ("c3");
                       }
                        else if(x.x== 3 && x.y== 2)
                       {
                           return ("d3");
                       }
                        else if(x.x== 4 && x.y== 2)
                       {
                           return("e3");
                       }
                       
                       //////////////////////////////////////////////
                       
                        else if(x.x== 0 && x.y== 3)
                        {
                            return ("a2");
                        }
                        else if(x.x == 1 && x.y== 3)
                       {
                           return ("b2");
                       }
                        else if(x.x== 2 && x.y== 3)
                       {
                          return ("c2");
                       }
                        else if(x.x==3&& x.y== 3)
                       {
                           return ("d2");
                       }
                        else if(x.x== 4 && x.y== 3)
                       {
                           return ("e2");
                           
                       }
                       /////////////////////////////////////////
                       
                        else if(x.x == 0 && x.y == 4)
                        {
                            return ("a1");
                        }
                        else if(x.x  == 1 && x.y == 4)
                       {
                           return ("b1");
                       }
                        else if(x.x == 2 && x.y == 4)
                       {
                           return ("c1");
                       }
                        else if(x.x == 3 && x.y == 4)
                       {
                           return ("d1");
                       }
                        else if(x.x == 4 && x.y == 4)
                       {
                           return ("e1");
                       }
                        else
                            return "ForaTabuleiro";
                       
       
        
    }
    
}
