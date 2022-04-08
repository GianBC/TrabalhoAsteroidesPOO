package trabalhoasteroidespoo2019_1.entidades;  //Guarda as entidades Nave Asteroide e Projetil

/**
 *
 * @author Gianluca Bensabat Calvano (213083086)
 */
import trabalhoasteroidespoo2019_1.MainGame;    //Carrega o pacote onde estao as dimensoes da janela
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

@SuppressWarnings("FieldMayBeFinal")
public class Asteroide {    //Classe pai responsavel pela base dos asteroides

    ///////////////VARIAVEIS DO ASTEROIDE///////////////
    private Shape ASTEROIDE_HITBOX; //Hitbox do asteroide
    private final float SENTIDO;   //Sentido em graus para onde o asteroide esta apontado
    private Image IMAGEM_ASTEROIDE; //Imagem que sera desenhada na tela do asteroide
    //Posicao X e Y do asteroide e sua velociadede
    private float X, Y, ACELERACAO_ASTEROIDE;
    //Quantidade de vida do asteroide e sua capacidade de causar dano ao bater em outra entidade
    private int vida_asteroide, dano_ao_chocar;
    private int DELTA;  //Delta do Slick2D para usar como referencia de velociadade

    ///////////////CONSTRUTOR DO ASTEROIDE///////////////
    @SuppressWarnings("SillyAssignment")
    public Asteroide(float X, float Y, float sentido, int DELTA) throws SlickException { //Construtor
        //Recebe os valores das classes filhas
        this.X = X;
        this.Y = Y;
        this.SENTIDO = sentido;
        this.DELTA = DELTA;
        this.ACELERACAO_ASTEROIDE = ACELERACAO_ASTEROIDE;
        this.IMAGEM_ASTEROIDE = IMAGEM_ASTEROIDE;
        this.ASTEROIDE_HITBOX = ASTEROIDE_HITBOX;
        this.vida_asteroide = vida_asteroide;
        this.dano_ao_chocar = dano_ao_chocar;
    }

    ///////////////METODOS EM PROTECTED DO ASTEROIDE PARA ACESSO DAS CLASSES FILHAS///////////////
    protected void setASTEROIDE_HITBOX(Shape ASTEROIDE_HITBOX) {    //Recebe a hitbox do asteroide
        this.ASTEROIDE_HITBOX = ASTEROIDE_HITBOX;
    }

    protected void setIMAGEM_ASTEROIDE(Image IMAGEM_ASTEROIDE) {    //Recebe a imagem do asteroide
        this.IMAGEM_ASTEROIDE = IMAGEM_ASTEROIDE;
    }

    protected void setACELERACAO_ASTEROIDE(float ACELERACAO_ASTEROIDE) {    //Recebe a aceleracao do asteroide
        this.ACELERACAO_ASTEROIDE = ACELERACAO_ASTEROIDE;
    }

    protected void setVida_asteroide(int vida_asteroide) {  //Recebe a vida do asteroide
        this.vida_asteroide = vida_asteroide;
    }

    protected void setDano_ao_chocar(int dano_ao_chocar) {  //Recebe a capacidade de dano que causa ao bater
        this.dano_ao_chocar = dano_ao_chocar;
    }

    ///////////////METODOS DO ASTEROIDE///////////////
    public Shape getAcao() throws SlickException {   //Faz a movimentacao da imagem e da hitbox do asteroide juntos
        //Movimenta na largura da tela o asteroide em funcao de sua direcao
        this.X += (float) (DELTA * this.ACELERACAO_ASTEROIDE) * (float) Math.sin(Math.toRadians(this.SENTIDO));
        //Movimenta na altura da tela o asteroide em funcao de sua direcao
        this.Y -= (float) (DELTA * this.ACELERACAO_ASTEROIDE) * (float) Math.cos(Math.toRadians(this.SENTIDO));
        this.IMAGEM_ASTEROIDE.draw(this.X, this.Y); //Desenha a imagem do asteroide na tela
        this.ASTEROIDE_HITBOX.setLocation(this.X, this.Y);  //Define a localizacao da hitbox do asteroide
        return this.ASTEROIDE_HITBOX;   //Retorna a hitbox do asteroide para 'CriadorAsteroides'
    }

    //Retorna se o asteroide foi destruido de acordo com o dano recebido
    public boolean getEstaDestruido(int dano) throws SlickException {
        //Diminui a vida do asteroide em funcao do dano recebido para entao checar se foi destruido
        this.vida_asteroide -= dano;
        return this.vida_asteroide <= 0;  //Retorna 'true' se o asteroide ficou com zero (0) ou menos de vida
    }

    //Retorna a capacidade do asteroide em causar dano em um impacto
    public int getCapacidadeDeDanoAoChocar() throws SlickException {
        return this.dano_ao_chocar;
    }

    public Shape getHitbox() {   //Retorna a hitbox do asteroide
        return this.ASTEROIDE_HITBOX;
    }

    public boolean getEstaForaDaTela() { //Retorna 'true' se o asteroide esta fora da tela
        return ((X > MainGame.LARGURA_JANELA + (IMAGEM_ASTEROIDE.getWidth() * 3))  //Se posicao a posicao X do asteroide e maior
                || //que a largura da janela + a largura da imagem do asteroide * 3
                (Y > MainGame.ALTURA_JANELA + (IMAGEM_ASTEROIDE.getHeight() * 3))  //Se posicao a posicao Y do asteroide e maior
                || //que a altura da janela + a altura da imagem do asteroide * 3
                (X < (-(IMAGEM_ASTEROIDE.getWidth() * 3)))   //Se posicao a posicao X do asteroide e menor que a largura
                || //da imagem do asteroide * 3
                (Y < (-(IMAGEM_ASTEROIDE.getHeight() * 3)))//Se posicao a posicao Y do asteroide e menor que a altura
                );  //da imagem do asteroide * 3
    }

    public float getPosX() { //Retorna a posicao X (largura) do asteroide
        return this.X;
    }

    public float getPosY() {    //Retorna a posicao Y (altura) do asteroide
        return this.Y;
    }

    public char getTipo_asteroide() {   //Retorna o tamanho do asteroide como uma letra (P, M, G)
        return 'A'; //Retorna 'A' por ser um asteroide base
    }

    public float getSENTIDO() { //Retorna o sentido em graus do asteroide
        return this.SENTIDO;
    }

    //Informacoes gerais do asteroide
    @Override
    public String toString() {
        return String.format("Asteroide/Tamanho:%c/Vida:%d/Forca:%d/Angulo:%.1f/X:%.1f/Y:%.1f/\n", getTipo_asteroide(), vida_asteroide, dano_ao_chocar, SENTIDO, X, Y);
    }

}
