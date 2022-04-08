package trabalhoasteroidespoo2019_1.logicas;    //Pacote responsavel pelo comportamento do jogo 
//nas areas de colisao, menu e geracao de asteroides

/**
 *
 * @author Gianluca Bensabat Calvano (213083086)
 */
import trabalhoasteroidespoo2019_1.entidades.*; //Pacote que guarda as entidades como asteroides, nave e disparos
import trabalhoasteroidespoo2019_1.Jogo;    //Pacote do estado do 'Jogo'
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;

public class ChecadorDeColisoes extends Jogo implements Runnable {  //Herda de Jogo os metodos em protected

    ///////////////CONSTRUTOR DO CHECADOR DE COLISOES///////////////
    public ChecadorDeColisoes(int stateID) throws SlickException {  //Construtor da classe pai
        super(stateID);
    }

    ///////////////METODOS PRIVADOS DO CHECADOR DE COLISOES///////////////
    //Metodo responsavel por indentificar uma colisao entre a nave e um asteroide e
    //checar se o asteroide saiu da tela
    private int AsteroideForaTela_ColisaoAsteroideNave() throws SlickException {
        if (Jogo.ThreadAutorizada) {    //Aguarda a autorizacao dos metodos 'render' e 'update' de Jogo para evitar concorrencia
            int vidas = super.getVidasJogador();    //Recebe as vidas que o jogador possui
            int capacidade_dano_nave = super.getNave().getCapacidadeDeDanoAoChocar();   //Armazena a capacidade de dano da nave em impacto

            for (int i = 0; i < super.getAsteroide().getVetorAsteroides().length; i++) {    //Percorre todo o vetor de asteroides
                if (super.getAsteroide().getVetorAsteroides()[i] != null) { //Ao encontrar o primeiro asteroide
                    Asteroide asteroid = super.getAsteroide().getVetorAsteroides()[i];  //Cria uma variavel local do tipo Asteroide com o valor do objeto na posicao [i]
                    if (super.getNave().getHitbox().intersects(asteroid.getHitbox())) { //Se a hitbox da nave toca na hitbox do asteroide analisado

                        if (super.getNave().getEstaDestruido(asteroid.getCapacidadeDeDanoAoChocar())) {  //Se o dano causado na nave for maior que sua vida
                            super.setVidasJogador(vidas - 1);   //Jogador perde uma vida
                            super.setRefazerJogo(); //Partida recomeca
                            Jogo.ThreadAutorizada = false;  //Desautoriza a thread para aguardar as atualizacoes dos metodos 'render' e 'update'
                            return 1;   //Retorna para o metodo 'run'
                        } else if (asteroid.getEstaDestruido(capacidade_dano_nave)) {   //Se nave sobrevive e o asteroide fica sem pontos de vida
                            super.getAsteroide().getVetorAsteroides()[i] = null;    //Apaga o asteroide com vida zerada ou negativada do vetor
                            //Passa as coordenadas do asteroide destruido salvas localmente para o metodo 'setFilhoSpawn' responsavel por gerar dois novos asteroides no lugar do destruido de tamanho menor
                            super.getAsteroide().setFilhoSpawn(asteroid.getPosX(), asteroid.getPosY(), asteroid.getSENTIDO(), super.getDelta(), asteroid.getTipo_asteroide());
                            return 2;   //Retorna para o metodo 'run'
                        } else {    //Se nenhum dos dois foi destruido
                            super.getAsteroide().getVetorAsteroides()[i] = asteroid; //Asteroide do vetor e atualizado com o novo estado do asteroide pos colisao
                            return 3;   //Retorna para o metodo 'run'
                        }

                    } else if (asteroid.getEstaForaDaTela()) {  //Se nao houve nenhuma colisao e o asteroide esta fora da tela
                        super.getAsteroide().getVetorAsteroides()[i] = null;    //Apaga o asteroide correspondente do vetor
                        return 4;   //Retorna para o metodo 'run'
                    }
                }
            }
        }
        return 0;   //Retorna padrao
    }

    //Metodo responsavel por indentificar uma colisao entre o disparo e um asteroide e
    //checar se o disparo saiu da tela
    private int DisparoForaTela_ColisaoDisparoAsteroide_teste() throws SlickException {
        if (Jogo.ThreadAutorizada) {    //Aguarda a autorizacao dos metodos 'render' e 'update' de Jogo para evitar concorrencia
            Jogo.ThreadAutorizada = false;  //Assim que autorizado, remove sua propia autorizacao para garantir que espere a classe Jogo
            for (int i = 0; i < super.getNave().getVetorDisparos().length; i++) {   //Percorre todo o vetor de disparos
                if (super.getNave().getVetorDisparos()[i] != null) {    //Se encontra um disparo
                    Projetil dispar = super.getNave().getVetorDisparos()[i];    //Cria uma variavel local do tipo Projetil com o valor do objeto na posicao [i]

                    for (int s = 0; s < super.getAsteroide().getVetorAsteroides().length; s++) {    //Percorre todo o vetor de asteroides
                        if (super.getAsteroide().getVetorAsteroides()[s] != null) { //Se encontra um asteroide
                            Asteroide asteroid = super.getAsteroide().getVetorAsteroides()[s];   //Cria uma variavel local do tipo Asteroide com o valor do objeto na posicao [s]
                            int capacidade_dano_disparo = dispar.getCapacidadeDeDanoAoChocar(); //Armazena a capacidade de dano do disparo

                            if (asteroid.getHitbox().intersects(dispar.getHitbox())) {  //Se a hitbox do asteroide toca na hitbox do projetil
                                if (dispar.getEstaDestruido(asteroid.getCapacidadeDeDanoAoChocar())) {  //Se o projetil tem sua vida zerada ou negativada
                                    super.getNave().getVetorDisparos()[i] = null;   //Apaga o disparo correspondente do vetor
                                } else {    //Se o projetil nao foi destruido
                                    super.getNave().getVetorDisparos()[i] = dispar; //Atualiza o Projetil do vetor com o novo estado de vida do projetil
                                }
                                if (asteroid.getEstaDestruido(capacidade_dano_disparo)) {   //Se o asteroide tem sua vida zerada ou negativada
                                    getAsteroide().getVetorAsteroides()[s] = null;  //Apaga o asteroide correspondente do vetor
                                    //Passa as coordenadas do asteroide destruido salvas localmente para o metodo 'setFilhoSpawn' responsavel por gerar dois novos asteroides no lugar do destruido de tamanho menor
                                    super.getAsteroide().setFilhoSpawn(asteroid.getPosX(), asteroid.getPosY(), asteroid.getSENTIDO(), super.getDelta(), asteroid.getTipo_asteroide());
                                    //Chama o metodo da classe pai responsavel por adicionar pontos ao jogador de acordo com o tamanho do asteroide destruido
                                    super.setAdicionarPonto(asteroid.getTipo_asteroide());
                                    Jogo.ThreadAutorizada = false;  //Desautoriza a thread para aguardar as atualizacoes dos metodos 'render' e 'update'
                                    break;  //Sai do loop do vetor dos asteroides para buscar novos disparos no loop do vetor de Projetil
                                } else {    //Se o asteroide nao foi destruido
                                    super.getAsteroide().getVetorAsteroides()[s] = asteroid;    //Atualiza o Asteroide do vetor com o novo estado de vida do asteroide correspondente
                                    Jogo.ThreadAutorizada = false;  //Desautoriza a thread para aguardar as atualizacoes dos metodos 'render' e 'update'
                                    break;  //Sai do loop do vetor dos asteroides para buscar novos disparos no loop do vetor de Projetil
                                }
                            }
                        }
                    }   //Checa se o disparo ainda existe e se saiu da tela
                    if (super.getNave().getVetorDisparos()[i] != null && super.getNave().getVetorDisparos()[i].getEstaForaDaTela()) {
                        super.getNave().getVetorDisparos()[i] = null;   //Apaga o disparo do vetor
                        Jogo.ThreadAutorizada = false;  //Desautoriza a thread para aguardar as atualizacoes dos metodos 'render' e 'update'
                    }
                }
            }
        }
        Jogo.ThreadAutorizada = false;  //Desautoriza a thread para aguardar as atualizacoes dos metodos 'render' e 'update'
        return 0;   //Retorna padrao
    }

    ///////////////METODOS DO CHECADOR DE COLISOES///////////////
    //Metodo responsavel por executar as funcoes da thread
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        Thread.currentThread().setName("Thread_ChecadorDeColisoes"); //Da um nome especifico para a thread
        try {
            while (true) {  //Entra em um loop infinito para poder checar constantemente
                Thread.sleep(super.getDelta()); //Adormece a thread pelo tempo do delta do Slick2D para evitar ciclos desnecessarios
                this.AsteroideForaTela_ColisaoAsteroideNave();
                this.DisparoForaTela_ColisaoDisparoAsteroide_teste();
                Jogo.ThreadAutorizada = false;  //Desautoriza a thread para aguardar as atualizacoes dos metodos 'render' e 'update'
            }
        } catch (InterruptedException | SlickException ex) {
            System.err.println("ERRO: "+ex);
            Logger.getLogger(ChecadorDeColisoes.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Jogo.ThreadAutorizada = false;
        }
    }

}
