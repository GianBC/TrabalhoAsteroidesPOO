package trabalhoasteroidespoo2019_1.entidades;  //Guarda as entidades Nave Asteroide e Projetil

/**
 *
 * @author Gianluca Bensabat Calvano (213083086)
 */
import trabalhoasteroidespoo2019_1.MainGame;    //Carrega o pacote onde estao as dimensoes da janela
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

@SuppressWarnings("FieldMayBeFinal")
public class Projetil {

    ///////////////VARIAVEIS DO PROJETIL///////////////
    private final Shape DISPARO_HITBOX1;    //Hitbox do projetil
    private final Image IMAGEM_DISPARO; //Imagem do projetil para exibir na tela
    private final int DELTA;    //Variavel do Slick2D
    private int vida_projetil, dano_ao_chocar;  //Vida que o projetil possui e o dano que ele causa no impacto
    private final float ANGULO_DISPARO; //Angulo da nave no momento do disparo
    private float X, Y, velociade_disparo;  //Posicao da nave no momento do disparo e velocidade do disparo

    ///////////////CONSTRUTOR DO PROJETIL///////////////
    //Recebe de 'Nave' suas coordenadas, o seu angulo, a altura da imagem da nave e o delta do Slick2D
    public Projetil(float x, float y, float angulo_disparo, float altura_nave, int delta) throws SlickException {
        this.X = x + altura_nave / 2.0f;    //X recebe a posicao X da nave mais metade da altura da imagem da nave
        this.Y = y + altura_nave / 2.0f;    //Y recebe a posicao Y da nave mais metade da altura da imagem da nave
        this.DISPARO_HITBOX1 = new Circle(0, 0, 2.5f);  //Cria um circulo na posicao X e Y 0 e de raio 2,5
        this.IMAGEM_DISPARO = new Image("resource/Projetil.png");   //Carrega a imagem do disparo
        this.ANGULO_DISPARO = angulo_disparo;   //Recebe o angulo da nave no momento do disparo
        this.IMAGEM_DISPARO.setRotation(this.ANGULO_DISPARO);   //Rotaciona a imagem do disparo para alinhar com a nave
        this.DELTA = delta; //Delta do Slick2D
        this.velociade_disparo = 0.5f;  //Velocidade do disparo na tela
        this.vida_projetil = 1; //Vida do projetil
        this.dano_ao_chocar = 1;    //Quantidade de dano que o disparo causa no alvo
    }

    ///////////////METODOS DO PROJETIL///////////////
    //Movimenta a imagem do disparo na tela e sua hitbox
    public Shape getDisparoEfetuado() throws SlickException {
        this.X += (float) (velociade_disparo * DELTA) * (float) Math.sin(Math.toRadians(ANGULO_DISPARO));
        this.Y -= (float) (velociade_disparo * DELTA) * (float) Math.cos(Math.toRadians(ANGULO_DISPARO));
        IMAGEM_DISPARO.drawCentered(X, Y);  //Desenha a imagem do disparo de forma centralizada na tela
        DISPARO_HITBOX1.setLocation(X - 2, Y - 2);  //Posiciona a hitbox do disparo na frente da imagem
        return DISPARO_HITBOX1; //Retorna para 'Nave' a hitbox do projetil
    }

    //Retorna 'true' se o disparo tocou em uma das bordas da tela para que possa ser excluido
    public boolean getEstaForaDaTela() throws SlickException {
        return X > MainGame.LARGURA_JANELA || Y > MainGame.ALTURA_JANELA || X < 0 || Y < 0;
    }

    //Retorna a hitbox do disparo
    public Shape getHitbox() throws SlickException {
        return this.DISPARO_HITBOX1;
    }

    //Retorna a capacidade de dano do disparo
    public int getCapacidadeDeDanoAoChocar() throws SlickException {
        return this.dano_ao_chocar;
    }

    //Retorna 'true' caso o dano recebido pelo disparo tenha sido suficiente para zerar ou negativar a sua vida
    public boolean getEstaDestruido(int dano) throws SlickException {
        this.vida_projetil -= dano; //Retira da vida do disparo a quantidade de dano recebido pelo disparo
        return this.vida_projetil <= 0;
    }

    //Informacoes gerais do projetil
    @Override
    public String toString() {
        return String.format("Disparo/Vida:%d/Forca:%d/Angulo:%.1f/X:%.1f/Y:%.1f/\n", this.vida_projetil, this.dano_ao_chocar, this.ANGULO_DISPARO, this.X, this.Y);
    }
}
