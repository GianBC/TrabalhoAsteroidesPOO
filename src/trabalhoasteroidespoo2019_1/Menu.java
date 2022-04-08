package trabalhoasteroidespoo2019_1;    //Guarda a Main e os estados do jogo "Menu, Jogo e GameOver"

/**
 *
 * @author Gianluca Bensabat Calvano (213083086)
 */
import trabalhoasteroidespoo2019_1.logicas.ManipuladorMenu; //Carrega o pacote onde estao as logicas do jogo
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {   //Estado responsavel por exibir a tela inicial

    ///////////////VARIAVEIS DO MENU///////////////
    private final int ID;  //ID do estado Menu
    //Classe responsavel por ler os movimentos do mouse, botoes do mouse e construir as imagens
    private ManipuladorMenu construir_menu = null;  //Mostra e captura as opcoes na tela
    private Boolean exibir_posicao_mouse;   //Responsavel por ativar ou desativar as coordenadas do mouse na tela

    ///////////////CONSTRUTOR DO MENU///////////////
    public Menu(int stateID) throws SlickException {
        this.ID = stateID;
    }

    ///////////////METODOS DO SLICK2D DO MENU///////////////
    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        //Inicializa a classe "ManipuladorMenu" como "construir_menu"
        this.construir_menu = new ManipuladorMenu();
        //Desativa o modo de exibir a posicao do mouse na tela
        this.exibir_posicao_mouse = false;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        if (this.exibir_posicao_mouse) {    //Se exibir a posicao do mouse ligado
            //Mostra na janela do Menu em 5% de altura e largura a posicao do mouse de forma atualizada
            g.drawString(this.construir_menu.toString(), MainGame.LARGURA_JANELA * 0.05f, MainGame.ALTURA_JANELA * 0.05f);
        }
        //Desenha as imagens referente a janela do Menu com a opcao 'true' para exibir a palavra "MENU"
        this.construir_menu.setDesenharMenu(true);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        //Le a opcao escolhida por meio do mouse ou teclado
        if (this.construir_menu.getEntrada().toLowerCase().equals("jogar")) {   //Se opcao jogar
            sbg.enterState(1);  //Vai para o estado responsavel pelo jogo
        } else if (this.construir_menu.getEntrada().toLowerCase().equals("sair")) { //Se opcao sair
            gc.exit();  //Encerra o programa
        }
    }

}
