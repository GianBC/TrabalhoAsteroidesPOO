package trabalhoasteroidespoo2019_1.logicas;

/**
 *
 * @author Gianluca Bensabat Calvano (213083086)
 */
import trabalhoasteroidespoo2019_1.MainGame;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class ManipuladorMenu {

    private final Input entrada;
    private final Image imagemJogar, imagemSair;
    private final Graphics grafico;

    public ManipuladorMenu() throws SlickException {
        this.imagemJogar = new Image("resource/Jogar.png");
        this.imagemSair = new Image("resource/Sair.png");
        this.grafico = new Graphics(0, 0);
        this.entrada = new Input(0);
    }

    public void setDesenharMenu(boolean seMenuInicial) throws SlickException {
        if (seMenuInicial) {
            grafico.drawString("MENU", MainGame.LARGURA_JANELA / 2, MainGame.ALTURA_JANELA * 0.05f);
        }
        imagemJogar.drawCentered((MainGame.LARGURA_JANELA / 2), 199);
        grafico.drawString("(ENTER)", 360, 189);
        imagemSair.drawCentered((MainGame.LARGURA_JANELA / 2), 281);
        grafico.drawString("(ESC)", 350, 270);
    }

    private String ComportamentoEntrada(Input entrada) throws SlickException {
        if ((entrada.getMouseX() > 285 && entrada.getMouseX() < 355)
                && (Math.abs(entrada.getMouseY()) < 290 && Math.abs(entrada.getMouseY()) > 267)) {
            return "jogar";
        } else if ((entrada.getMouseX() > 294 && entrada.getMouseX() < 344)
                && (Math.abs(entrada.getMouseY()) < 210 && Math.abs(entrada.getMouseY()) > 188)) {
            return "sair";
        } else {
            return "Botao esquerdo pressionada fora de uma opcao";
        }
    }

    public String getEntrada() throws SlickException {
        if (this.entrada.isKeyDown(Input.KEY_ENTER)) {
            return "jogar";
        } else if (entrada.isKeyDown(Input.KEY_ESCAPE)) {
            return "sair";
        } else if (entrada.isMouseButtonDown(0)) {
            return ComportamentoEntrada(this.entrada);
        } else {
            return "nenhuma entrada";
        }
    }

    @Override
    public String toString() {
        return "Posição X: " + entrada.getMouseX() + " Posição Y: " + Math.abs(entrada.getMouseY());
    }

}
