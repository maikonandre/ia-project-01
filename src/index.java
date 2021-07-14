
import controller.SalaController;
import view.MainApp;
import vo.SalaVO;

/**
 *
 * @author maikon
 */
public class index {
    public static void main(String[] args) {
        
        //Início da aplicação
        SalaController controllerSala = new SalaController();
        SalaVO[] salas = controllerSala.CarregarSalas();
        
        SalaVO salaInicial = controllerSala.FindById("A");
        SalaVO salaFinal = controllerSala.FindById("T");
        
        String rota = controllerSala.CalcularRota(salaInicial, salaFinal);
        
        System.out.println("Rota percorrida de " + salaInicial.getId() + " a " + salaFinal.getId() + ": " + rota);
        new MainApp().show();
    }
}
