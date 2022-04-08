package trabalhoasteroidespoo2019_1.logicas;    //Pacote responsavel pelo comportamento do jogo 
//nas areas de colisao, menu e geracao de asteroides

/**
 *
 * @author Gianluca Bensabat Calvano (213083086)
 */
import trabalhoasteroidespoo2019_1.entidades.*; //Pacote que guarda as entidades como asteroides, nave e disparos
import trabalhoasteroidespoo2019_1.MainGame;    //Carrega o pacote onde estao as dimensoes da janela
import java.util.Random;
import org.newdawn.slick.SlickException;

@SuppressWarnings("FieldMayBeFinal")
public class CriadorAsteroides {

    ///////////////VARIAVEIS DO CRIADOR ASTEROIDES///////////////
    private static int MAX_ASTEROIDES;  //Quantidade maxima de asteroides na tela gerados pelo jogo
    private Asteroide ASTEROIDES[];    //Vetor que guarda todos os asteroides ativos no jogo
    private final Random GERADOR_ALEATORIO; //Gerador aleatorio para escolher o local de nascimento dos asteroides
    private int CHANCE_ASTEROIDE_PEQUENO, CHANCE_ASTEROIDE_MEDIO, divisoes_largura_janela, divisoes_altura_janela;
    private float recuo_spawn;  //Quantidade de distancia fora da tela para gerar os asteroides

    ///////////////CONSTRUTOR DO CRIADOR ASTEROIDES///////////////
    public CriadorAsteroides() {
        this.GERADOR_ALEATORIO = new Random();
        MAX_ASTEROIDES = 30; //Maximo de asteroides que podem ser criados pelo comando do jogo
        this.CHANCE_ASTEROIDE_MEDIO = 4; //Razao de 4/10(40%) de chance de aparecer um asteroide medio
        this.CHANCE_ASTEROIDE_PEQUENO = 4;  //Razao de 4/10(40%) de chance de aparecer um asteroide pequeno
        //Restando 2/10(20%) de chance de aparecer um asteroide grande
        ASTEROIDES = new Asteroide[(MAX_ASTEROIDES * 2)]; //Vetor com o dobro do maximo de asteroides
        //para comportar os asteroides gerados pelo jogador que nao entra no limite
        this.recuo_spawn = 40.0f; //Distancia para fora da tela
        this.divisoes_altura_janela = 10;   //Divisoes no eixo Y para criar as areas de nascimento
        this.divisoes_largura_janela = 15;  //Divisoes no eixo X para criar as areas de nascimento
    }

    ///////////////METODOS PRIVADOS DO CRIADOR ASTEROIDES///////////////
    //Geracao de um asteroide na parte superior da tela
    private Asteroide telaCima(int delta) throws SlickException {
        //Obtem as coordenadas no eixo X de cada uma das areas de nascimento
        float divisoes_nascimento = (float) MainGame.LARGURA_JANELA / this.divisoes_largura_janela;
        //Gera um numero aleatorio que corresponde com a area onde o asteroide ira nascer
        int setor_nascimento = this.GERADOR_ALEATORIO.nextInt(divisoes_largura_janela);
        //Gera um numero aleatorio referente a chance do tamanho do asteroide
        int tipo_asteroide = (this.GERADOR_ALEATORIO.nextInt(10) + 1);
        float posX = 0.0f, angulo;

        for (int i = 0; i < setor_nascimento; i++) {    //Soma as coordenadas obtidas na 'divisoes_nascimento'
            posX += divisoes_nascimento;                //ate alcancar o indice de 'setor_nascimento'
        }
        if (setor_nascimento == 0) {    //Define o angulo do asteroide de acordo com sua area de nascimento
            angulo = 135.0f;
        } else if (setor_nascimento == this.divisoes_largura_janela - 1) {
            angulo = 225.0f;
        } else {
            angulo = 180.0f;
        }

        if (tipo_asteroide <= this.CHANCE_ASTEROIDE_PEQUENO) {  //Se o resultado do numero aleatorio e menor ou igual a 4
            return new AsteroidePequeno(posX, -recuo_spawn, angulo, delta); //Cria um novo asteoirde pequeno
        } else if (tipo_asteroide <= (this.CHANCE_ASTEROIDE_PEQUENO + this.CHANCE_ASTEROIDE_MEDIO)) {   //Se resultado for de 5 ate 8 (inclusive)
            return new AsteroideMedio(posX, -recuo_spawn, angulo, delta);   //Cria um asteroide medio
        } else {    //Se resultado for 9 ou 10
            return new AsteroideGrande(posX, -recuo_spawn, angulo, delta);  //cria um asteroide grande
        }
    }

    //Geracao de um asteroide na parte direita de tela
    private Asteroide telaDireita(int delta) throws SlickException {
        float divisoes_nascimento = (float) MainGame.ALTURA_JANELA / this.divisoes_altura_janela;
        int setor_nascimento = this.GERADOR_ALEATORIO.nextInt(divisoes_altura_janela);
        int tipo_asteroide = (this.GERADOR_ALEATORIO.nextInt(10) + 1);
        float posY = 0.0f, angulo;

        for (int i = 0; i < setor_nascimento; i++) {
            posY += divisoes_nascimento;
        }
        if (setor_nascimento == 0) {
            angulo = 225.0f;
        } else if (setor_nascimento == this.divisoes_altura_janela - 1) {
            angulo = 315.0f;
        } else {
            angulo = 270.0f;
        }

        if (tipo_asteroide <= this.CHANCE_ASTEROIDE_PEQUENO) {
            return new AsteroidePequeno((MainGame.LARGURA_JANELA + recuo_spawn), posY, angulo, delta);
        } else if (tipo_asteroide <= (this.CHANCE_ASTEROIDE_PEQUENO + this.CHANCE_ASTEROIDE_MEDIO)) {
            return new AsteroideMedio((MainGame.LARGURA_JANELA + recuo_spawn), posY, angulo, delta);
        } else {
            return new AsteroideGrande((MainGame.LARGURA_JANELA + recuo_spawn), posY, angulo, delta);
        }
    }

    //Geracao de um asteroide na parte inferior da tela
    private Asteroide telaBaixo(int delta) throws SlickException {
        float divisoes_nascimento = (float) MainGame.LARGURA_JANELA / this.divisoes_largura_janela;
        int setor_nascimento = this.GERADOR_ALEATORIO.nextInt(divisoes_largura_janela);
        int tipo_asteroide = (this.GERADOR_ALEATORIO.nextInt(10) + 1);
        float posX = MainGame.LARGURA_JANELA, angulo;

        for (int i = 0; i <= setor_nascimento; i++) {
            posX -= divisoes_nascimento;
        }
        if (setor_nascimento == 0) {
            angulo = 315.0f;
        } else if (setor_nascimento == this.divisoes_largura_janela - 1) {
            angulo = 45.0f;
        } else {
            angulo = 0.0f;
        }

        if (tipo_asteroide <= this.CHANCE_ASTEROIDE_PEQUENO) {
            return new AsteroidePequeno(posX, (MainGame.ALTURA_JANELA + recuo_spawn), angulo, delta);
        } else if (tipo_asteroide <= (this.CHANCE_ASTEROIDE_PEQUENO + this.CHANCE_ASTEROIDE_MEDIO)) {
            return new AsteroideMedio(posX, (MainGame.ALTURA_JANELA + recuo_spawn), angulo, delta);
        } else {
            return new AsteroideGrande(posX, (MainGame.ALTURA_JANELA + recuo_spawn), angulo, delta);
        }
    }

    //Geracao de um asteroide na parte esquerda da tela
    private Asteroide telaEsquerda(int delta) throws SlickException {
        float divisoes_nascimento = (float) MainGame.ALTURA_JANELA / this.divisoes_altura_janela;
        int setor_nascimento = this.GERADOR_ALEATORIO.nextInt(divisoes_altura_janela);
        int tipo_asteroide = (this.GERADOR_ALEATORIO.nextInt(10) + 1);
        float posY = MainGame.ALTURA_JANELA, angulo;

        for (int i = 0; i <= setor_nascimento; i++) {
            posY -= divisoes_nascimento;
        }
        if (setor_nascimento == 0) {
            angulo = 45.0f;
        } else if (setor_nascimento == this.divisoes_altura_janela - 1) {
            angulo = 135.0f;
        } else {
            angulo = 90.0f;
        }

        if (tipo_asteroide <= this.CHANCE_ASTEROIDE_PEQUENO) {
            return new AsteroidePequeno((-recuo_spawn), posY, angulo, delta);
        } else if (tipo_asteroide <= (this.CHANCE_ASTEROIDE_PEQUENO + this.CHANCE_ASTEROIDE_MEDIO)) {
            return new AsteroideMedio((-recuo_spawn), posY, angulo, delta);
        } else {
            return new AsteroideGrande((-recuo_spawn), posY, angulo, delta);
        }
    }

    ///////////////METODOS DO CRIADORASTEROIDES///////////////
    //Cria um novo asteroide de forma aleatoria a pedido do 'Jogo'
    public void setSpawn(int delt) throws SlickException {
        //Variaveis do delta, escolha aleatoria de um dos quatro lados da tela, quantidade de asteroides no vetor e indice do vetor
        int delta = delt, lado_tela = this.GERADOR_ALEATORIO.nextInt(4), contagem_asteroides = 0, indice_asteroides = 0;

        for (int i = 0; i < ASTEROIDES.length; i++) {  //percorre o vetor de asteroides para contarquantos asteroides existem na tela
            if (ASTEROIDES[i] != null) {   //Cada espaco nao vazio conta como mais um asteroide no vetor
                contagem_asteroides++;
            }
            if (contagem_asteroides >= MAX_ASTEROIDES) {    //Se a quantidade de asteroides for maior ou igual ao maximo nao cria nenhum novo asteroide
                return;
            }
        }

        //Localiza o primeiro indice vazio no vetor de asteroides
        while ((indice_asteroides < ASTEROIDES.length) && (ASTEROIDES[indice_asteroides] != null)) {
            indice_asteroides++;
        }

        switch (lado_tela) { //Escolhe o lado da tela com base no numero aleatorio de 0 ate 3 (inclusive)
            case 0: //Insere no vetor 'ASTEROIDES' o resultado do nascimento do novo asteroide
                ASTEROIDES[indice_asteroides] = (this.telaCima(delta));
                break;
            case 1:
                ASTEROIDES[indice_asteroides] = (this.telaDireita(delta));
                break;
            case 2:
                ASTEROIDES[indice_asteroides] = (this.telaBaixo(delta));
                break;
            case 3:
                ASTEROIDES[indice_asteroides] = (this.telaEsquerda(delta));
                break;
        }
    }

    //Cria dois novos asteroides por meio da destruicao de outro asteroide
    public void setFilhoSpawn(float x, float y, float angul, int delt, char ta) throws SlickException {
        float X = x, Y = y;
        int delta = delt, indice1_asteroides = -1, indice2_asteroides = -1; //Inicializa os indices do vetor como -1 para poder detectar um vetor cheio
        char tipo_asteroide = ta;

        for (int i = 0; i < ASTEROIDES.length; i++) {   //Percorre o vetor ate achar um espaco vago
            if (ASTEROIDES[i] == null) {
                indice1_asteroides = i;
                break;
            }
        }
        for (int i = (indice1_asteroides + 1); i < ASTEROIDES.length; i++) {
            if (ASTEROIDES[i] == null) {
                indice2_asteroides = i;
                break;
            }
        }
        if (indice1_asteroides == -1 || indice2_asteroides == -1) { //Caso um dos dois indices nao encontre espaco vago
            System.err.println("\nERRO na classe: CriadorAsteroides\nNo metodo: setFilhoSpawn");
            System.err.println("Erro no vetor ASTEROIDES: vetor nao possui espaco disponivel para dois(2) novos asteroides!");
            return;
        }
        switch (tipo_asteroide) {   //Cria dois asteroides novos na posicao do asteroide destruido em direcoes aleatorias
            case 'g':
            case 'G':
                ASTEROIDES[indice1_asteroides] = (new AsteroideMedio(X, Y, (float) GERADOR_ALEATORIO.nextInt(360), delta));
                ASTEROIDES[indice2_asteroides] = (new AsteroideMedio(X, Y, (float) GERADOR_ALEATORIO.nextInt(360), delta));
                break;
            case 'm':
            case 'M':
                ASTEROIDES[indice1_asteroides] = (new AsteroidePequeno(X, Y, (float) GERADOR_ALEATORIO.nextInt(360), delta));
                ASTEROIDES[indice2_asteroides] = (new AsteroidePequeno(X, Y, (float) GERADOR_ALEATORIO.nextInt(360), delta));
                break;
            case 'p':
            case 'P':
                break;
            default:
                System.err.println("\nERRO na classe: CriadorAsteroides\nNo metodo: setFilhoSpawn");
                System.err.println("Asteroide origem de tamanho nao reconhecido!");
                break;
        }
    }

    //retorna o vetor de asteroides
    public Asteroide[] getVetorAsteroides() {
        return ASTEROIDES;
    }

}
