package trabalhoasteroidespoo2019_1;    //Guarda a Main e os estados do jogo "Menu, Jogo e GameOver"

/**
 *
 * @author Gianluca Bensabat Calvano (213083086)
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainGame extends StateBasedGame {   //Inicio da logica das janelas do jogo

    ///////////////VARIAVEIS DO MAINGAME///////////////
    public static final int MENU = 0, JOGO = 1, GAMEOVER = 2; //IDs dos estados de jogo (Game State)
    public static final float LARGURA_JANELA = 640.0f, ALTURA_JANELA = 480.0f;    //Resolucao da janela
    public static final String TITULO = "Trabalho_Asteroides_POO(2019-1)";  //Titulo da janela do jogo
    @SuppressWarnings("FieldMayBeFinal")
    private static boolean mostrar_fps = true;   //Nao mostrar ou mostrar o fps na tela

    ///////////////CONSTRUTOR DO MAINGAME///////////////
    public MainGame(String name) {
        super(name);
    }

    ///////////////METODOS DO SLICK2D DO MAINGAME///////////////
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {    //Incicializacao dos estados do jogo
        this.addState(new Menu(MENU));  //Estado do Menu responsavel pela janela inicial
        this.addState(new Jogo(JOGO));  //Estado do Jogo responsavel pela janela do jogo
        this.addState(new GameOver(GAMEOVER));  //Estado do Jogo responsavel pela janela de fim de jogo
    }

    ///////////////MAIN DO PROGRAMA///////////////
    public static void main(String[] args) {
        AppGameContainer janela;    //Variavel janela do tipo AppGameContainer
        try {
            janela = new AppGameContainer(new MainGame(TITULO));    //Janela recebe novo jogo com o titulo armazenado na variavel TITULO
            janela.setDisplayMode((int) LARGURA_JANELA, (int) ALTURA_JANELA, false); //Resolucao da janela e Fullscreen desativado
            //janela.setTargetFrameRate(60);  //FPS maximo de 60
            janela.setShowFPS(mostrar_fps);    //Exibir FPS
            janela.setAlwaysRender(true);
            janela.start(); //Inciar a janela do jogo
        } catch (SlickException ex) {
            Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
