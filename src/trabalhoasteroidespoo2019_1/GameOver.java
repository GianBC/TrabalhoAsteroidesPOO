package trabalhoasteroidespoo2019_1;    //Guarda a Main e os estados do jogo "Menu, Jogo e GameOver"

/**
 *
 * @author Gianluca Bensabat Calvano (213083086)
 */
import trabalhoasteroidespoo2019_1.logicas.ManipuladorMenu; //Carrega o pacote onde estao as logicas do jogo
import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver extends BasicGameState {   //Estado responsavel por exibir a janela de game over

    ///////////////VARIAVEIS DO GAMEOVER///////////////    
    private final int ID;  //ID do estado Menu
    private static int pontos = 0;    //Inicializa a variavel que vai exibir os pontos na tela de game over
    private ManipuladorMenu construir_menu = null;  //Mostra e captura as opcoes na tela
    private Font fnt;   //Fonte usada para para escrever o GameOver
    private TrueTypeFont fonte; //Fonte usada para para escrever o GameOver

    ///////////////CONSTRUTOR DO GAMEOVER///////////////
    public GameOver(int stateID) throws SlickException {
        this.ID = stateID;
    }

    ///////////////METODOS DO SLICK2D DO GAMEOVER///////////////
    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        //Inicializa a classe "ManipuladorMenu" como "construir_menu"
        this.construir_menu = new ManipuladorMenu();
        //Inicializa a fonte
        this.fnt = new Font("Arial black", Font.BOLD, 40);
        this.fonte = new TrueTypeFont(fnt, true);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        //Desenha as imagens referente a janela do Menu com a opcao 'false' para NAO exibir a palavra "MENU"
        this.construir_menu.setDesenharMenu(false);
        g.setColor(Color.red);  //Define a cor da fonte como vermelha
        g.setFont(this.fonte);  //Define a fonte que sera utilizada
        //Escreve a string "GameOver" em 33% de largura por 1% de altura
        g.drawString("GameOver", ((float) (gc.getWidth() * 0.33)), ((float) (gc.getHeight() * 0.01)));
        g.resetFont();  //Retorna para a fonte original do Slick2D
        g.setColor(Color.blue); //Define a cor da fonte como azul
        //Escreve a string "Pontos: " em 45% de largura por 20% de altura e exibe os pontos obtidos da classe Jogo
        g.drawString("Pontos: " + pontos, ((float) (gc.getWidth() * 0.45)), ((float) (gc.getHeight() * 0.20)));
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        //Le a opcao escolhida por meio do mouse ou teclado
        if (this.construir_menu.getEntrada().toLowerCase().equals("jogar")) {   //Se opcao jogar
            sbg.enterState(1);  //Vai para o estado responsavel pelo jogo
        } else if (this.construir_menu.getEntrada().toLowerCase().equals("sair")) { //Se opcao sair
            gc.exit();  //Encerra o programa
        }
    }

    ///////////////METODOS PROTECTED DO GAMEOVER///////////////
    //Obtem os pontos ao final do jogo como 'protected static' para permitir o acesso da classe Jogo
    //no mesmo pacote sem precisar declarar
    protected static void setPontosJogador(int pnts) {
        pontos = pnts;
    }

}
