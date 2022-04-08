package trabalhoasteroidespoo2019_1.entidades;  //Guarda as entidades Nave Asteroide e Projetil

/**
 *
 * @author Gianluca Bensabat Calvano (213083086)
 */
import trabalhoasteroidespoo2019_1.MainGame;    //Carrega o pacote onde estao as dimensoes da janela
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Rectangle;

@SuppressWarnings("FieldMayBeFinal")
public class Nave {

    ///////////////VARIAVEIS DA NAVE///////////////
    private final int MAX_DISPAROS; //Tamanho do vetor que guarda os disparos da nave
    private final Shape NAVE_HITBOX;    //Hitbox da nave
    private final Image IMAGEM_NAVE;    //Imagem da nave na tela
    private Projetil DISPAROS[];   //Vetor que armazena os disparos efetuados pela nave
    private Projetil disparo;   //Disparo efetuado ao pressionar a tecla
    private int vida_nave, dano_ao_chocar; //Vida da nave e sua capacidade de causar dano ao bater em outra entidade
    //Sentido em graus da nave, constante da inercia, ultimo sentido da nave antes de liberar a tecla para frente
    //posicao X e Y da nave e sua aceleracao
    private float rotacao, inercia, ultima_rotacao, X, Y, ACELERACAO_NAVE;

    ///////////////CONSTRUTOR DA NAVE///////////////
    public Nave() throws SlickException {
        this.MAX_DISPAROS = 30;   //Numero maximo de disparos simultaneos
        this.DISPAROS = new Projetil[this.MAX_DISPAROS];  //Vetor dos disparos com o tamanho do maximo de disparos simultaneos
        this.ACELERACAO_NAVE = 0.5f; //Aceleracao da nave
        this.IMAGEM_NAVE = new Image("resource/Nave.png");  //Imagem da nave para ser desenhada na tela
        //Desenha a hitbox retangular da nave na posicao X=0 e Y=0 com a largura e altura da imagem da nave
        this.NAVE_HITBOX = new Rectangle(0, 0, (this.IMAGEM_NAVE.getWidth() + 1), (this.IMAGEM_NAVE.getHeight() + 1));
        //Incializa a posicao da nave no centro da janela
        this.X = ((float) MainGame.LARGURA_JANELA / 2.0f) - ((float) IMAGEM_NAVE.getWidth() / 2.0f);
        this.Y = ((float) MainGame.ALTURA_JANELA / 2.0f) - ((float) IMAGEM_NAVE.getHeight() / 2.0f);
        this.vida_nave = 1;  //Vida da nave
        this.dano_ao_chocar = 1; //Capacidade de dano ao chocar da nave
        this.rotacao = 0.0f; //Sentido em graus inicial da nave
        this.inercia = 0.0f; //Inercia inicial da nave
    }

    ///////////////METODOS INTERNOS DA NAVE///////////////
    //Movimenta a nave e sua hibox para frente com o pressionar da tecla
    private void moverNaveParaFrente(int delta) throws SlickException {
        //https://stackoverflow.com/questions/15175746/slick2d-get-image-x-and-y
        this.inercia = this.inercia + 0.01f;    //Aumenta a inercia da nave em ,01 a cada chamada
        if (this.inercia >= this.ACELERACAO_NAVE) { //Mantem a inercia da nave no limite de velociade da nave
            this.inercia = this.ACELERACAO_NAVE;
        }
        //Muda as coordenadas da nave em funcao do sentido
        this.X += (float) (delta * this.inercia) * (float) Math.sin(Math.toRadians(this.rotacao));
        this.Y -= (float) (delta * this.inercia) * (float) Math.cos(Math.toRadians(this.rotacao));
        this.ultima_rotacao = this.rotacao; //armazena o ultimo sentido da nave
    }

    //Mantem o movimento da nave e sua hitbox por um tempo ao liberar a tecla para frente
    private void inerciaDaNave(int delta) throws SlickException {
        //Muda as coordenadas da nave em funcao do sentido
        this.X += (float) (delta * this.inercia) * (float) Math.sin(Math.toRadians(this.ultima_rotacao));
        this.Y -= (float) (delta * this.inercia) * (float) Math.cos(Math.toRadians(this.ultima_rotacao));
        if (this.inercia > 0.0000f) //Enquanto existir inercia
        {
            this.inercia = this.inercia - 0.0025f;  //diminui a inercia da nave em 0,0025 a cada chamada
        }
        if (this.inercia < 0.0000f) //Se inercia ficar negativa
        {
            this.inercia = 0.0000f; //Zera a inercia
        }
    }

    //Rotaciona a nave em seu proprio eixo para a direita ou esquerda
    private void rotacaoDaNave(char tecla, int delta) throws SlickException {
        if (tecla == 'D') { //Se pressionar tecla 'D' (direita)
            //Rotaciona a nave para direita aumentando os graus em 60% da aceleracao da nave * a constante delta
            this.IMAGEM_NAVE.setRotation(this.rotacao += (float) ((this.ACELERACAO_NAVE * 0.6f) * delta));
            if (this.rotacao > 360.0f) {   //Se antingir 360 graus
                this.rotacao = 0.0f;  //Zera o angulo
            }

        } else if (tecla == 'A') {  //Se pressionar tecla 'A' (esquerda)
            //Rotaciona a nave para esquerda diminuindo os graus em 60% da aceleracao da nave * a constante delta
            this.IMAGEM_NAVE.setRotation(this.rotacao -= (float) ((this.ACELERACAO_NAVE * 0.6f) * delta));
            if (this.rotacao < 0.0f) { //Se antingir 0 graus
                this.rotacao = 360.0f;    //reposiciona o angulo
            }
        }
    }

    //Realiza o disparo da nave
    private void disparoDaNave(float X, float Y, float rotacao, int delta) throws SlickException {
        //Cria um disparo da classe 'Projetil' com as coordenadas da nave
        this.disparo = new Projetil(X, Y, rotacao, this.IMAGEM_NAVE.getHeight(), delta);
        for (int i = 0; i < this.DISPAROS.length; i++) {    //Percorre o vetor de disparos em busca de espaco
            if (this.DISPAROS[i] == null) { //Quando acha um espaco vazio
                this.DISPAROS[i] = this.disparo; //Insere o recem efetuado disparo no vetor
                return; //E encerra o metodo
            } else if (i == (this.DISPAROS.length - 1)) {   //Se o vetor estiver cheio
                //Escreve o erro na saida
                System.err.println("\nERRO na classe: Nave\nNo metodo: disparoDaNave");
                System.err.println("O vetor DISPAROS nao possui espaco!");
                this.DISPAROS = new Projetil[this.MAX_DISPAROS]; //E reinicia o vetor de disparos
                return;
            }
        }
    }

    //Verifica se a nave atingiu a borda da tela e a reposiciona
    private void bordaDaTela() throws SlickException {
        if (X > MainGame.LARGURA_JANELA + (IMAGEM_NAVE.getWidth() / 2)) {
            this.X = ((float) (IMAGEM_NAVE.getWidth() / 2) * (-1));
        } else if (X < (IMAGEM_NAVE.getWidth() / 2) * (-1)) {
            this.X = MainGame.LARGURA_JANELA + ((float) IMAGEM_NAVE.getWidth() / 2);
        }
        if (Y < (float) (IMAGEM_NAVE.getHeight() / 2) * (-1)) {
            this.Y = MainGame.ALTURA_JANELA + ((float) IMAGEM_NAVE.getHeight() / 2);
        } else if (Y > MainGame.ALTURA_JANELA + ((float) IMAGEM_NAVE.getHeight() / 2)) {
            this.Y = ((float) IMAGEM_NAVE.getHeight() / 2) * (-1);
        }
    }

    ///////////////METODOS DA NAVE///////////////
    //Leitura das teclas de acao da nave e sua posicao em relacao a borda da tela
    public void setAcao(Input teclado, int delta) throws SlickException {

        //Movimentacao da nave para frente se 'W'(frente) foi pressionado ou esta pressionado
        if (teclado.isKeyDown(Input.KEY_W) || teclado.isKeyPressed(Input.KEY_W)) {
            this.moverNaveParaFrente(delta);    //Chama o metodo privado responsavel por mover a nave passando o delta
        } else if (this.inercia > 0.0f) { //Se nao esta pressionado o 'W' e ainda tem inercia
            this.inerciaDaNave(delta);  //Chama o metodo privado responsavel pela inercia da nave passando o delta
        }

        //Rotacao da nave no propio eixo se foi pressionado ou esta pressionado a tecla para direita 'D' ou esquerda 'A'
        if (teclado.isKeyDown(Input.KEY_D) || teclado.isKeyPressed(Input.KEY_D)) {
            this.rotacaoDaNave('D', delta); //Chama o metodo privado responsavel pela rotacao da nave passando a tecla
        } else if (teclado.isKeyDown(Input.KEY_A) || teclado.isKeyPressed(Input.KEY_A)) {
            this.rotacaoDaNave('A', delta);
        }

        //Disparo da nave se foi pressionado a tecla espaco
        if (teclado.isKeyPressed(Input.KEY_SPACE)) {
            //Chama o metodo privado responsavel pelo disparo da nave passando as coordenadas da nave
            this.disparoDaNave(this.X, this.Y, this.rotacao, delta);
        }

        //Reposiciona a nave ao atingir as bordas da tela
        this.bordaDaTela();

    }

    //Posicionamento inical da nave e sua hitbox no centro da tela
    public Shape getAcao() throws SlickException {
        this.IMAGEM_NAVE.draw(this.X, this.Y);  //Desenha na tela a imagem da nave nas posicoes X e Y
        this.NAVE_HITBOX.setLocation(this.X, this.Y);   //Posiciona a hitbox da nave nas posicoes X e Y
        return this.NAVE_HITBOX;    //Retorna para Jogo a hitbox da nave
    }

    //Retorna a capacidade da nave em causar dano em um impacto
    public int getCapacidadeDeDanoAoChocar() throws SlickException {
        return this.dano_ao_chocar;
    }

    //Retorna a hitbox da nave
    public Shape getHitbox() throws SlickException {
        return this.NAVE_HITBOX;
    }

    //Recebe a quantidade de dano sofrido pela nave e retorna 'true' caso ela tenha sido destruida
    public boolean getEstaDestruido(int dano) throws SlickException {
        this.vida_nave -= dano; //Diminui a vida atual da nave do dano sofrido
        return this.vida_nave <= 0; //Retorna 'true' se a nave zerou ou negativou a sua vida
    }

    //Retorna um vetor com todos os disparos efetuados pela nave ou null como espacos vazios
    public Projetil[] getVetorDisparos() throws SlickException {
        return this.DISPAROS;
    }

    //Informacoes gerais da nave
    @Override
    public String toString() {
        return String.format("Nave/Vida:%d/Forca:%d/Angulo:%.1f/X:%.1f/Y:%.1f/\n", this.vida_nave, this.dano_ao_chocar, this.rotacao, this.X, this.Y);
    }

}
