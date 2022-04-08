package trabalhoasteroidespoo2019_1.entidades;  //Guarda as entidades Nave Asteroide e Projetil

/**
 *
 * @author Gianluca Bensabat Calvano (213083086)
 */
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

public class AsteroidePequeno extends Asteroide {    //Classe filha responsavel pelo asteroide pequeno

    ///////////////VARIAVEIS DO ASTEROIDE PEQUENO///////////////
    //Carrega a imagem do asteroide
    private final Image IMAGEM_ASTEROIDE = new Image("resource/AsteroidePequeno.png");

    ///////////////CONSTRUTOR DO ASTEROIDE PEQUENO///////////////
    //Recebe a posicao e sentido iniciais do asteroide
    public AsteroidePequeno(float X, float Y, float sentido, int delta) throws SlickException {
        super(X, Y, sentido, delta);    //Passa para 'Asteroide' o X, Y e o delta
        super.setIMAGEM_ASTEROIDE(this.IMAGEM_ASTEROIDE);   //Passa para 'Asteroide' a imagem do asteroide pequeno
        super.setASTEROIDE_HITBOX(new Circle(X, Y, this.IMAGEM_ASTEROIDE.getWidth() / 2));
        //Passa para 'Asteroide' a hitbox circular do tamanho da imagem/2 para obter o raio do circulo
        super.setACELERACAO_ASTEROIDE(0.05f);   //Passa para 'Asteroide' a velocidade do asteroide pequeno
        super.setVida_asteroide(1); //Passa para 'Asteroide' a vida do asteroide pequeno
        super.setDano_ao_chocar(2); //Passa para 'Asteroide' a capacidade de causar dano do asteroide pequeno
    }

    ///////////////METODOS DO ASTEROIDE PEQUENO///////////////
    @Override
    public char getTipo_asteroide() {   //Retorna o tamanho do asteroide
        return 'P';
    }

}
