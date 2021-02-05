package chess;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import java.awt.Color;
import static java.awt.Color.gray;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Um component para jogar e implementar o xadrez
 * Implements MouseListener.
 * 
 */
public class PanelJogo extends JComponent implements MouseListener 
{
    
    private enum estadoDoJogo {Idle, Error, Started, Checkmate, Stalemate};
    private final int DIM = 8;
    estadoDoJogo estado = estadoDoJogo.Idle;
    boolean imagensCarregadas = false;
    Peca.Color corIA;
    
    private JList lista1 ,lista2;
    
     public static DefaultListModel<String> l1 ;  
    private DefaultListModel<String> l2 ;  
         
   static private JList<String> list ;
    private JList<String> list1 ;
    JScrollPane scrollPane ;
    
   
    QuadroXadrez jogoTabuleiro;
    
    
    
    // piece selected by the user
    Peca pecaSelecionada = null;
    // invalid piece selected by user
    Peca pecaInvalida = null;
    // a list of the possible moves for the selected piece
    List<Move> okMoves;
    
    // defines color variables for different elements of the game
    final Color invalidColor = new Color(255, 0, 0, 127);
    final Color selectionColor = new Color(255,255,0,127);
    final Color validMoveColor = new Color(0,255,0,127);
    final Color checkColor = new Color(127,0,255,127);
    final Color lastMovedColor = new Color(0,255,255,75);
    Color lightColor = new Color(255,255,255);
    final Color darkColor = new Color(255,0,0,255);
    
    

/**
     * Cria um novo componente BoardPanel
     * @param w largura em pixels
     * @param h altura em pixels
     */
    public PanelJogo(int w, int h) 
    {
        // set the size of the component
        this.setSize(w, h);
        // loads piece images from file
        carregamentoDeImagens();
        // inititalizes the game board for a 2-player game
        novoIAJogo();
        // adds a listener for mouse events
        
       
       l1 = new DefaultListModel<>();  
       l2 = new DefaultListModel<>();  
         
       list = new JList<>(l1);  
       list1 = new JList<>(l2);
          
       l1.addElement("NEW GAME");
        list.setBounds(600,30, 300,300);
        
        list1.setBounds(500,10, 300,175);
        l2.addElement("Isaura");
        
        //scrollPane = new JScrollPane(list);
       // scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
  
        
      // scrollPane.setViewportView(list);
      
           //this.add(scrollPane);
          //this.add(list);
          //this.add(list1);
         
      
        
        this.addMouseListener(this);
    }    
    
  /**
     * Configura um novo jogo para 2 jogadores no painel
     */

    public void novoJogo() 
    {
        //cria um novo tabuleiro
        jogoTabuleiro = new QuadroXadrez(true);
        estado = estadoDoJogo.Started;
        
        // resets variables
        pecaSelecionada = null;
        pecaInvalida = null;

       // desenha o tabuleiro recém-criado
        this.repaint();
    }
    
    /**
     * Configura um novo jogo para 1 jogador no painel
     */
    public void novoIAJogo()
    {       
        int profundidadeIA;
        
        
       // cria um JOptionPane para perguntar ao usuário sobre a dificuldade do agente (IA)
        Object nivel = JOptionPane.showInputDialog(this, "Seleciona o nivel do agente:", 
                "Novo 1-Jogador jogo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[] {"Facil", "Normal"},
                "Facil");
        
        // interpreta o resultado JOptionPane 
        if (nivel == null)
            return;
        else
            if (nivel.toString().equals("Facil"))
            {
                profundidadeIA = 2;
            }
            else 
            {
                 profundidadeIA = 3;
            }
           
        // cria um JOptionPane para pedir ao usuário a cor IA
        Object color = JOptionPane.showInputDialog(this, "Seleciona a cor do Agente:", 
                "Novo 1-Jogador jogo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[] { "Preta", "Branca" },
                "Preta");
        
        // interprets JOptionPane result
        if (color == null)
            return;
        else
            if (color.toString().equals("Branca"))
            {
                 corIA = Peca.Color.branca;
            }
            else
            {
                 corIA = Peca.Color.preta;
            }
        
        // cria um novo Jogo
        this.novoJogo();
        // then sets the ai for the board a profundidade do agente e setada aquiiiiii
        jogoTabuleiro.setAi(new Agente_IA(corIA, profundidadeIA));   //intelligence artificiallll
        
        // simula um evento de clique para solicitar ao IA para fazer o primeiro movimento
        if (corIA == Peca.Color.branca)
        {
            mousePressed(null);
        }
    }
    
    /** 
     * Carrega o estado doquadro do arquivo
     */
    public void loadBoard() {
        // reset cariables
        pecaSelecionada = null;
        pecaInvalida = null;
        
        try {          
            // gets all the saved games in the /SAVES folder
            File directory = new File("SAVES");

            // creates the saves folder if it does not already exist
            if (!directory.exists())              
                directory.mkdir();

            File[] saves = directory.listFiles();
            
            // if no saves found
            if (saves.length == 0) {
                // inform the user
                JOptionPane.showMessageDialog(this,
                        "No saved games found", 
                        "Load game",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // allows the user to choose from the found saves
            Object response = JOptionPane.showInputDialog(this, "Select save file:", 
                "Load Game",
                JOptionPane.QUESTION_MESSAGE,
                null,
                saves,
                saves[0]);
            
            // if user cancels the prompt, exit method
            if (response == null)
                return;
            
            // get an ObjectInputStream from the file
            FileInputStream fis = new FileInputStream((File)response);
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            // read the board from the file
            this.jogoTabuleiro = (QuadroXadrez)ois.readObject();
            
            // close the streams
            ois.close();
            fis.close();
        } 
        catch (Exception e) 
        {
            // in case of an exception
            String message = "Could not load saved game. " +
                    "The save file may be corrupted or from an earlier version of ChessGame.\n\n" +
                    "Error details: " + e.getMessage();
            // inform the used with details on the exception
            JOptionPane.showMessageDialog(this,
                    message,
                    "Error!",
                    JOptionPane.ERROR_MESSAGE);            
        }
// verifique se o jogo carregado acabou, ajuste o estado de acordo
        if (jogoTabuleiro.gameOver()) {             
            if (jogoTabuleiro.getPecaEmCheck() == null)
            {
                estado = estadoDoJogo.Stalemate;
            }
            else
            {
                estado = estadoDoJogo.Checkmate; 
            }
        }
        
        // repinta para mostrar a mudanca
        this.repaint();
    }

   
    
    /**
     * Retorna o tabuleiro ao seu estado anterior

     */
    public void desfazer() {
       // redefine as variáveis ​​para os círculos auxiliares
        pecaSelecionada = null;
        pecaInvalida = null;
        okMoves = null;
               
        // se um jogo para dois jogadores
        if (jogoTabuleiro.getAi() == null)
           // pula um movimento para trás
            jogoTabuleiro = jogoTabuleiro.getEstadoAnterior();
        else
            // volta para o último movimento do jogador

            if (jogoTabuleiro.getVirar() != jogoTabuleiro.getAi().getColor())
                jogoTabuleiro = jogoTabuleiro.getEstadoAnterior().getEstadoAnterior();
            else
                jogoTabuleiro = jogoTabuleiro.getEstadoAnterior();
        
       
        //define o ultimo estado do jogador
        estado = estadoDoJogo.Started;
        
        this.repaint();      
    }
    
     /**
     * Salva o estado do quadro no Arquivo
     */
    public void saveBoard() {
        try {
            // pergunta ao usuario o nome do arquivo a ser salvo
            String nomeArquivo = JOptionPane.showInputDialog("Insira o nome do player white: ");
            String nomeArquivo1 = JOptionPane.showInputDialog("Insira o nome do player black: ");
            
            
           
           
            // se o nome do prompt nao foi preenchido sai
            if (nomeArquivo == null)
            {
                return;
            }
                

            // check that the saves folder exists
            File diretorio = new File("SALVOS");
            // creates folder if it doesn't exist
            if (!diretorio.exists())
                diretorio.mkdir();

           
             if(Arquivo.Write("SALVOS/"+nomeArquivo+".txt",l1.toString()+"\n"))
                     {
                         System.out.println("Salvo com sucesso");
                     }
              if(Arquivo.Write("SALVOS/"+nomeArquivo1+".txt",Agente_IA.l1.toString()+"\n"))
                     {
                         System.out.println(" arquivo 2 Salvo com sucesso");
                     }
            
            
        } catch (Exception e) {
            // in case of an exception
            String message = "Could not save game. " +
                    "Ensure that a valid filename was given.\n\n" +
                    "Error details: " + e.getMessage();
            // inform the user, give exception details
            JOptionPane.showMessageDialog(this,
                    message, "Error!",
                    JOptionPane.ERROR_MESSAGE);    
        }
    }  
    
   
    private void carregamentoDeImagens()
    {
        try {
           // inicializa duas matrizes de bufferedImages
            BufferedImage[] imagensBrancas = new BufferedImage[6];            
            BufferedImage[] imagensPretas = new BufferedImage[6];

            // se a pasta PIECES não existir, crie-a
            File directory = new File ("PIECES");
            
            if (!directory.exists()) {
                if (directory.mkdir()) {
                // o diretório estará vazio, então lance uma exceção
                throw new Exception("The PIECES directory did not exist. " +
                        "It has been created. Ensure that "
                        + "it contains the following files: \n" +
                        "WHITE_PAWN.PNG\n" +
                        "WHITE_KNIGHT.PNG\n" +
                        "WHITE_BISHOP.PNG\n" +
                        "WHITE_ROOK.PNG\n" +
                        "WHITE_QUEEN.PNG\n" +
                        "WHITE_KING.PNG\n" +
                        "BLACK_PAWN.PNG\n" +
                        "BLACK_KNIGHT.PNG\n" +
                        "BLACK_BISHOP.PNG\n" +
                        "BLACK_ROOK.PNG\n" +
                        "BLACK_QUEEN.PNG\n" +
                        "BLACK_KING.PNG");
                }
            }


            // Carregamento de todas as imagens Brancas
            imagensBrancas[0] = ImageIO.read(new File("PIECES/WHITE_PAWN.png"));
            imagensBrancas[1] = ImageIO.read(new File("PIECES/WHITE_KNIGHT.png"));
            imagensBrancas[2] = ImageIO.read(new File("PIECES/WHITE_BISHOP.png"));
            imagensBrancas[3] = ImageIO.read(new File("PIECES/WHITE_ROOK.png"));
            imagensBrancas[4] = ImageIO.read(new File("PIECES/WHITE_QUEEN.png"));
            imagensBrancas[5] = ImageIO.read(new File("PIECES/WHITE_KING.png"));
            
            // Carregamento de todas as imagens Pretas
            imagensPretas[0] = ImageIO.read(new File("PIECES/BLACK_PAWN.png"));
            imagensPretas[1] = ImageIO.read(new File("PIECES/BLACK_KNIGHT.png"));
            imagensPretas[2] = ImageIO.read(new File("PIECES/BLACK_BISHOP.png"));
            imagensPretas[3] = ImageIO.read(new File("PIECES/BLACK_ROOK.png"));
            imagensPretas[4] = ImageIO.read(new File("PIECES/BLACK_QUEEN.png"));
            imagensPretas[5] = ImageIO.read(new File("PIECES/BLACK_KING.png"));
            
          // definir as imagens brancas e pretas na classe Peca

            Peca.setImagensPretas(imagensPretas);
            Peca.setImagensBrancas(imagensBrancas);
            
           // imagens carregadas sem erros

            imagensCarregadas = true;
            
        } 
        catch (Exception e) 
        {
            estado = estadoDoJogo.Error;
            // create a message to inform the use about the error
            String message = "Nao foi possivel carregar as imagens da peca. " +
                    "Check that all 12 images exist in the PIECES folder " +
                    "and are accessible to the program.\n" +
                    "The program will not function properly until this is resolved.\n\n" +
                    "Error details: " + e.getMessage();
            // display the message
            JOptionPane.showMessageDialog(this, message, "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
  /**
     * Responde a um evento mousePressed
     * @param e
     */


    @Override
    public void mousePressed(MouseEvent e) 
    { 
        if (estado == estadoDoJogo.Started) 
        {
            pecaInvalida = null;
            // pega a largura e a altura
            
            int w = getWidth();
            int h = getHeight();
            
            //System.out.println("w"+this.getWidth());
            //System.out.println("h"+this.getHeight());

            // responder à ação do jogador
            if (jogoTabuleiro.getAi() == null || 
                jogoTabuleiro.getAi().getColor() != jogoTabuleiro.getVirar())
            { 
                // transforma as coordenadas do clique do mouse em coordenadas do tabuleiro
                Point boardPt = new Point(e.getPoint().x / (w / DIM),  
                        e.getPoint().y / (h / DIM)); 
                
                       // String res = "Jogador Black";
                        
                        //clicado=boardPt;
                        //this.mostrarMove(boardPt);
                      
                       
                       
                //  se nenhuma peça foi selecionada ainda
                if(pecaSelecionada == null)
                {
                   // selecione a peça que foi clicada
                    pecaSelecionada = jogoTabuleiro.getPieceAt(boardPt);
                    if (pecaSelecionada != null) 
                    {  
                      // obtém os movimentos disponíveis para a peça
                        okMoves = pecaSelecionada.getValidMoves(jogoTabuleiro, true);
                       // se a peça for da cor errada, marcar como inválido
                        if(pecaSelecionada.getColor() != jogoTabuleiro.getVirar()) 
                        {
                            okMoves = null;
                            pecaInvalida = pecaSelecionada;
                            pecaSelecionada = null;
                        }
                    }
                }
                else 
                {
                   // verifique se o jogador clicou no destino de um movimento
                    Move playerMove = MovimentoComDestino(boardPt);

                  // em caso afirmativo, execute esse movimento
                    if (playerMove != null) 
                    {
                        if(corIA == Peca.Color.branca){
                            l1.addElement("BLACK -> "+this.mostrarMove(playerMove.getPiece().getLocalizacao())
                            +" - "+this.mostrarMove(playerMove.getMoveTo()));
                        }
                        else{
                            l1.addElement("WHITE -> "+this.mostrarMove(playerMove.getPiece().getLocalizacao())
                            +" - "+this.mostrarMove(playerMove.getMoveTo()));
                        }
                        
                        
                        
                        jogoTabuleiro.mova(playerMove, true);
                        pecaSelecionada = null;
                        okMoves = null;
                    } else {
                   // caso contrário, ignore o clique e redefina as variáveis
                    
                        pecaSelecionada = null;
                        okMoves = null;
                    }
                }
            } 
            
            //>>>>>>>>>>>>>>Para o movimento do Agente>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            // process an ai move // movimento automatico do agente em >>>>>>>>>>>>>>>>  IA
            if (jogoTabuleiro.getAi() != null && 
                jogoTabuleiro.getAi().getColor() == jogoTabuleiro.getVirar()) { 
                
                // repinta imediatamente o quadro para mostrar
                // último movimento do jogador


               this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
                
                // get a move from the board's ai
                Move computerMove = jogoTabuleiro.getAi().getMove(jogoTabuleiro);

                if (computerMove != null) {
                    // if a move was returned, make move
                    jogoTabuleiro.mova(computerMove, false);
                }
            }
            //>>>>>>>>>>>>>>Verificando game over>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            // se um lado não pode fazer nenhum movimento válido
            if (jogoTabuleiro.gameOver()) {             
                // repintar o tabuleiro imediatamente, antes que JOptionPane seja mostrado.
                this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
                
            // se um rei não estiver em xeque, empate


                if (jogoTabuleiro.getPecaEmCheck() == null) {
                    estado = estadoDoJogo.Stalemate;
                    JOptionPane.showMessageDialog(this,
                            "Empate!!",
                            "",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                // if a king esta em  check, checkmate
                    estado = estadoDoJogo.Checkmate; 
                    JOptionPane.showMessageDialog(this,
                            "Checkmate!",
                            "",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                   
            }
            
            this.repaint(); // chama o paintComponent
        }       
    }
    //Onde sera pintado  o jogo
    @Override
    protected void paintComponent(Graphics gr)
    {
        int w = getWidth();
        int h = getHeight();

        // qudrado height and width
        int sW = w / DIM;
        int sH = h / DIM;
        
     
       //  Agente_IA a1 = new Agente_IA();
        //System.out.println("Valor peca:"+ Agente_IA.valorDaPeca(c));
        
      // cria um buffer fora da tela
        Image buffer = createImage(w, h);

       // obtém o contexto gráfico do buffer
        Graphics g = buffer.getGraphics();

       // desenha o tabuleiro para o buffer
        tabuleiroDesenho(g, sW, sH);        
        
        drawHelperCircles(g, sW, sH);
        
       // se as imagens foram carregadas, desenhe-as
        if (imagensCarregadas)
        {
            this.desenhoPecas(g, sW, sH);
        }
            //this.desenhoPecas(g, sW, sH); // colocando as pecas 

        // draw the contents of the buffer to the panel
        gr.drawImage(buffer, 0, 0, this);
        
        gr.drawString("a", 50, sH+370);
        gr.drawString("b", 140, sH+370);
        gr.drawString("c", 220, sH+370);
        gr.drawString("d", 300, sH+370);
        gr.drawString("e", 400, sH+370);
        ////////////////////////////////
         gr.drawString("1", 460, sH+330);
        gr.drawString("2", 460, sH+230);
        gr.drawString("3", 460, sH+150);
        gr.drawString("4", 460, sH+70);
        gr.drawString("5", 460, 50);
        
        
    }
           
    /**
     * Desenha círculos auxiliares para objetos gráficos
     * objeto de gráficos @param g para desenhar
     * @param sW largura do quadrado
     * @param sH altura do quadrado
     */


    public static String mostrarMove(Point x)
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
                            return "";
                       
       
        
    }
    private void drawHelperCircles(Graphics g, int sW, int sH) {
        // if a piece is selected
        if(pecaSelecionada != null) {     
          // desenha um círculo para essa peça
            Point p = pecaSelecionada.getLocalizacao();
            g.setColor(selectionColor);
            g.fillOval(p.x * sW, p.y * sH, sW, sH);
            
            // e todos os seus movimentos válidos
            g.setColor(validMoveColor);           
            for(Move m : okMoves) {
                Point pt = m.getMoveTo();
                g.fillOval(pt.x * sW, pt.y * sH, sW, sH);
            }
        }
       // se o usuário clicou em um pedaço da cor errada
        if(pecaInvalida != null) {
            // Desenha o crculo para esta peca
            
            Point p = pecaInvalida.getLocalizacao();
            g.setColor(invalidColor);
            g.fillOval(p.x * sW, p.y * sH, sW, sH);
        }
       // se um rei está em xeque
        if (jogoTabuleiro.getPecaEmCheck() != null) {
            // desenha um círculo para o rei
            Point p = jogoTabuleiro.getPecaEmCheck().getLocalizacao();
            g.setColor(checkColor);
            g.fillOval(p.x * sW, p.y * sH, sW, sH);
        }   
       // se uma peça foi movida
        if (jogoTabuleiro.getUltimoMoveDaPeca() != null) {
            // desenha um círculo para indicar a última peça movida
            Point p = jogoTabuleiro.getUltimoMoveDaPeca().getLocalizacao();
            g.setColor(lastMovedColor);
            g.fillOval(p.x * sW, p.y * sH, sW, sH);
        }
    }
    
    /**
     *

Desenha peças para o objeto gráfico
     * @param g Objeto gráfico para desenhar
     * @param sH altura de um quadrado
     * @param sW largura de um quadrado
     */
    private void desenhoPecas(Graphics g, int sW, int sH) {
        // para cada peca do tabuleiro
        for(Peca pc : jogoTabuleiro.getPecas()) {
            // if a peca e branca
            if(pc.getColor() == Peca.Color.branca) {
              // desenha sua imagem branca
                g.drawImage(pc.getWhiteImage(), pc.getLocalizacao().x * sW,
                        pc.getLocalizacao().y * sH, sW, sH, null);//sH-300 para subir em tabuleiro 5*5
            } else {
                // desenhe sua imagem preta
                g.drawImage(pc.getBlackImage(), pc.getLocalizacao().x * sW,
                       ( pc.getLocalizacao().y * sH), sW, sH, null);//+200 SH
            }
        }
    }
    
    /**
     * Desenha um tabuleiro vazio
     * @param g Objeto Graphics2D para desenhar
     * @param sW largura de um quadrado
     * @param sH altura de um quadrado


     */
    private void tabuleiroDesenho(Graphics g, int sW, int sH) {
       // desenha um fundo claro
        g.setColor(lightColor);
        g.fillRect(0, 0, sW * 5, sH *5); //8 8
        
        
        boolean dark = false;
        g.setColor(darkColor);
        // desenha quadrados vermelhos
        for(int y = 0; y < 5; y++) { //8
            for(int x = 0; x <4; x++) {
                if(dark) {
                    g.fillRect(x * sW, y * sH, sW, sH);
                }
                dark = !dark;
            }         
            dark = !dark;
        }
    }  
    
    /**
     *Obtém o movimento com um determinado ponto como destino na lista de movimentos
     * @param pt ponto a procurar
     * @return mover com destino no pt
     */
    private Move MovimentoComDestino(Point pt) 
    {
        Move i=null;
        for(Move m : okMoves){
            if(m.getMoveTo().equals(pt))
            {
                return m;
            }
            else
            {
                i=m;
                l1.addElement("Move Invalido ->"+this.mostrarMove(i.getPiece().
                        getLocalizacao()));
                 /*System.out.println("Move Invalido ->"+this.mostrarMove(i.getPiece().
                         getLocalizacao()));*/
                
            }
        } 
        
       
//        l1.addElement("Move Invalido ->"+this.mostrarMove(i.getPiece().getLocalizacao()));
        return null;
    }    
    
    public void mouseExited(MouseEvent e) { }    
   
    public void mouseEntered(MouseEvent e) { } 
    
    public void mouseReleased(MouseEvent e) { }
   
    public void mouseClicked(MouseEvent e) { }
}
