
import controller.SalaController;
import vo.SalaVO;

/**
 *
 * @author maikon
 */
public class index {
    static SalaVO[] salas = new SalaVO[21];
    
    public static void main(String[] args) {
        
        //Início da aplicação
        SalaController controllerSala = new SalaController();
        salas = controllerSala.CarregarSalas();
    }
}
