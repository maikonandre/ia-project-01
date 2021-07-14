
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
        
        SalaVO salaInicial = controllerSala.FindById("T");
        SalaVO salaFinal = controllerSala.FindById("C");
        
        String rota = controllerSala.CalcularRota(salaInicial, salaFinal);
        
        System.out.println("Rota percorrida de " + salaInicial.getId() + " a " + salaFinal.getId() + ": " + rota);
    }
}
