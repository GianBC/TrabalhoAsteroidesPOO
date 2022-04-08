package trabalhoasteroidespoo2019_1.entidades;  //Guarda as entidades Nave Asteroide e Projetil

/**
 *
 * @author Gianluca Bensabat Calvano (213083086)
 */
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

public class AsteroideGrande extends Asteroide { //Classe filha responsavel pelo asteroide grande

    ///////////////VARIAVEIS DO ASTEROIDE GRANDE///////////////
    //Carrega a imagem do asteroide
    private final Image IMAGEM_ASTEROIDE = new Image("resource/AsteroideGrande.png");

    ///////////////CONSTRUTOR DO ASTEROIDE GRANDE///////////////
    //Recebe a posicao e sentido iniciais do asteroide
    public AsteroideGrande(float X, float Y, float sentido, int delta) throws SlickException {
        super(X, Y, sentido, delta);    //Passa para 'Asteroide' o X, Y e o delta
        super.setIMAGEM_ASTEROIDE(this.IMAGEM_ASTEROIDE);   //Passa para 'Asteroide' a imagem do asteroide grande
        //Passa para 'Asteroide' a hitbox circular do tamanho da imagem/2 para obter o raio do circulo
        super.setASTEROIDE_HITBOX(new Circle(X, Y, this.IMAGEM_ASTEROIDE.getWidth() / 2));
        super.setACELERACAO_ASTEROIDE(0.0125f); //Passa para 'Asteroide' a velocidade do asteroide grande
        super.setVida_asteroide(3); //Passa para 'Asteroide' a vida do asteroide grande
        super.setDano_ao_chocar(6); //Passa para 'Asteroide' a capacidade de causar dano do asteroide grande
    }

    ///////////////METODOS DO ASTEROIDE GRANDE///////////////
    @Override
    public char getTipo_asteroide() {   //Retorna o tamanho do asteroide
        return 'G';
    }
}
