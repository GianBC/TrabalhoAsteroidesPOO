package trabalhoasteroidespoo2019_1;    //Guarda a Main e os estados do jogo "Menu, Jogo e GameOver"

/**
 *
 * @author Gianluca Bensabat Calvano (213083086)
 */
import trabalhoasteroidespoo2019_1.logicas.*;   //Pacote que guarda as logicas para criar asteroides e checar colisoes
import trabalhoasteroidespoo2019_1.entidades.*; //Pacote que guarda as entidades como asteroides, nave e disparos
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Jogo extends BasicGameState {  //Estado responsavel pela acao do jogo

    ///////////////VARIAVEIS DO JOGO///////////////
    private final int ID, VIDAS_JOGADOR_DEFAULT = 3;    //ID do estado Jogo e quantidade padrao de vidas ou tentativas
    /*Booleano para autorizar ou proibir a thread que checa as colisoes 
    de funcionar nos intervalos de processamento da base do jogo
    como 'protected' e 'static' para permitir o acesso pela classe herdeira 'ChecadorDeColisoes'*/
    protected static boolean ThreadAutorizada = false;
    //Declara o delta que sera utilizado pela thread, os pontos do jogador e suas vidas
    private static int DeltaParaThread = 20, PontosJogador = 0, VidasJogador = 3;
    //Declara e inicializa a classe responsavel por gerar os asteroides
    private static CriadorAsteroides asteroide = new CriadorAsteroides();
    //Declara a classe responsavel pela nave e seus disparos
    private static Nave nave;
    //Opcoes para exibir a hitbox das entidades, pausar o jogo e reiniciar o jogo
    private boolean hitbox_visivel, pausa, reiniciar;
    //Declara a classe responsavel por detectar as colisoes entre entidades ou fora da tela
    private ChecadorDeColisoes colisor;
    //Declara a thread que sera responsavel por detectar as colisoes com o 'ChecadorDeColisoes'
    private Thread thread_colisor = null;

    ///////////////CONSTRUTOR DO JOGO///////////////
    public Jogo(int stateID) throws SlickException {    //Construtor da classe que inicializa as variaveis
        this.ID = stateID;
        asteroide = new CriadorAsteroides();
        nave = new Nave();
        PontosJogador = 0;
        VidasJogador = 3;
        ThreadAutorizada = false;
    }

    ///////////////METODOS DO SLICK2D DO JOGO///////////////
    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {  //Inicializa as variaveis novamente
        asteroide = new CriadorAsteroides();
        nave = new Nave();
        PontosJogador = 0;
        VidasJogador = 3;
        ThreadAutorizada = false;
        this.colisor = new ChecadorDeColisoes(ID);
        this.thread_colisor = new Thread(colisor);
        this.hitbox_visivel = false;
        this.pausa = false;
        this.reiniciar = false;
        this.thread_colisor.start();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setColor(Color.blue); //Define a cor da fonte como azul

        /*for (int i = 0; i < asteroide.getVetorAsteroides().length; i++) {
            if (asteroide.getVetorAsteroides()[i] != null)
                g.drawString(asteroide.getVetorAsteroides()[i].toString(), 20, 20 + i * 15);
        }*/
        /*for (int i=0; i<nave.getVetorDisparos().length;i++){
            if(nave.getVetorDisparos()[i]!=null)
                g.drawString(nave.getVetorDisparos()[i].toString(), 10, 10+i*15);
        }*/
        
        //Escreve o "Pausar: P" em 5% de largura e 0 de altura
        g.drawString("Pausar: P", ((float) (MainGame.LARGURA_JANELA * 0.05f)), 0);
        //Escreve o "VIDAS " em 40% de largura e 0 de altura
        g.drawString("VIDAS: " + VidasJogador, ((float) MainGame.LARGURA_JANELA * 0.4f), 0);
        //Escreve o "Pontos: " em 75% de largura e 0 de altura
        g.drawString("Pontos: " + PontosJogador, ((float) MainGame.LARGURA_JANELA * 0.75f), 0);

        if (this.hitbox_visivel) {  //Checa se a opcao de hitbox visivel esta ativa
            g.setColor(Color.red);  //Caso positivo desenha as hitbox de vermelho
        } else {
            g.setColor(Color.transparent);  //Caso negativo torna elas invisiveis
        }

        //Desautoriza a thread de checar navas colisoes para evitar conflitos durante o desenho da imagens
        ThreadAutorizada = false;

        g.draw(nave.getAcao()); //Desenha na tela o comportamento da nave
        //Percorre o vetor que guarda os disparos da nave em busca de disparos validos e os desenha na tela
        for (int i = 0; i < nave.getVetorDisparos().length; i++) {
            if (nave.getVetorDisparos()[i] != null) {
                g.draw(nave.getVetorDisparos()[i].getDisparoEfetuado());
            }
        }
        //Percorre o vetor que guarda os asteroides em busca de asteroides validos e os desenha na tela
        for (int i = 0; i < asteroide.getVetorAsteroides().length; i++) {
            if (asteroide.getVetorAsteroides()[i] != null) {
                g.draw(asteroide.getVetorAsteroides()[i].getAcao());
            }
        }

        ThreadAutorizada = true;    //Autoriza a thread a checar as colisoes
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        //Desautoriza a thread de checar as colisoes para evitar conflitos com a escrita dos vetores
        ThreadAutorizada = false;

        DeltaParaThread = delta;    //Passa o delta do Slick2D para uma variavel que sera usada pela thread
        nave.setAcao(gc.getInput(), delta); //passa para a Nave as leituras do teclado o delta do Slick2D
        asteroide.setSpawn(delta);  //Chama o CriadorAsteroides para criar asteroides de forma aleatoria
        this.pausa = gc.getInput().isKeyPressed(Input.KEY_P);   //Checa se a tecla de pausa 'P' foi pressionada
        this.reiniciar = gc.getInput().isKeyPressed(Input.KEY_F5);  //Checa se a tecla de reiniciar 'F5' foi pressionada
        if (VidasJogador <= 0) {    //Verifica se o jogador perdeu todas as vidas/tentativas
            GameOver.setPontosJogador(PontosJogador);   //Passa para o estado 'GameOver' o total de pontos do jogador
            sbg.enterState(2);  //Entra no estado do 'GameOver'
            VidasJogador = VIDAS_JOGADOR_DEFAULT;   //Reinicia o contador de vidas com o valor padrao(3)
            PontosJogador = 0;  //Zera os pontos do jogador para a nova rodada
        }
        if (this.pausa) {   //Se a tecla de pausar (P) foi pressionada
            sbg.enterState(0);  //Entra no estado 'Menu' e congela o estado 'Jogo'
        } else if (this.reiniciar) {    //Se a tecla de reiniciar (F5) foi pressionada
            VidasJogador--; //Remove uma vida do jogador
            gc.reinit();    //Reinicia o estado Jogo mantendo a vida e os pontos da jogada anterior
        }

        ThreadAutorizada = true;    //Autoriza a thread a checar as colisoes
    }

    ///////////////METODOS EM PROTECTED PARA ACESSO PELA CLASSE FILHA 'ChecadorDeColisoes' DO JOGO///////////////
    protected Nave getNave() throws SlickException {    //Retorna o objeto 'Nave' instanciado pelo 'Jogo'
        return nave;
    }

    //Retorna o objeto 'Asteroide' instanciado pelo 'Jogo'
    protected CriadorAsteroides getAsteroide() throws SlickException {
        return asteroide;
    }

    protected int getVidasJogador() {   //Retorna as vidas do jogador
        return VidasJogador;
    }

    protected void setVidasJogador(int vidas) { //Altera a quantidade atual de vidas da nave
        VidasJogador = vidas;
    }

    //Adiciona pontos ao jogador por destruir o asteroide de acordo com o tamanho
    protected void setAdicionarPonto(char tamanho) {
        switch (tamanho) {
            case 'P':   //Se asteroide pequeno recebe mais um (1) ponto
                PontosJogador += 1;
                break;
            case 'M':   //Se asteroide medio recebe mais dois (2) pontos
                PontosJogador += 2;
                break;
            case 'G':   //Se asteroide grande recebe mais tres (3) pontos
                PontosJogador += 3;
                break;
            default:
                break;
        }
    }

    //Responsavel por refazer o jogo em caso de destruicao da nave
    protected void setRefazerJogo() throws SlickException {
        asteroide = new CriadorAsteroides();
        nave = new Nave();
    }

    protected int getDelta() throws SlickException {    //Da acesso ao delta do Slick2D de forma atualizada
        return DeltaParaThread;
    }

}
